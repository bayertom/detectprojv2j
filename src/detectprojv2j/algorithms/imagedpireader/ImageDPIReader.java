// Description: Read DPI of the image

// Copyright (c) 2024 - 2025
// Tomas Bayer
// Charles University in Prague, Faculty of Science
// bayertom@natur.cuni.cz

// This library is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published
// by the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with this library. If not, see <http://www.gnu.org/licenses/>.

package detectprojv2j.algorithms.imagedpireader;

import static detectprojv2j.consts.Consts.MM_PER_INCH;
import detectprojv2j.structures.tile.DPI;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Optional;

/**
 * Read image DPI from PNG or JPEG using only JDK ImageIO + DOM metadata.
 *
 * Search order: 1) Standard ("javax_imageio_1.0"): Dimension /
 * Horizontal/VerticalPixelSize (mm/pixel) 2) PNG native
 * ("javax_imageio_png_1.0"): pHYs (pixels per meter; unit must be "meter") 3)
 * JPEG native ("javax_imageio_jpeg_image_1.0"): app0JFIF (Xdensity/Ydensity +
 * resUnits)
 *
 * If DPI metadata is absent or unknown, returns Optional.empty(). NOTE: Some
 * JPEGs store DPI only in EXIF; the built-in reader often doesn't expose EXIF
 * here. For EXIF-only files, use an external library (e.g.,
 * metadata-extractor).
 */
public class ImageDPIReader {

        private static final double inches_per_meter = 1000.0/MM_PER_INCH;              //Constant for transformation

        private static final String std_tree = "javax_imageio_1.0";;                    //Metadata, standard tree name
        private static final String png_tree = "javax_imageio_png_1.0";;                //Metadata, png tree name
        private static final String jpeg_tree = "javax_imageio_jpeg_image_1.0";;        //Metadata, jpg tree name


        public static DPI readDPI(final File file) throws IOException {
                //Read DPI from file
                
                //Throw exception
                if (file == null || !file.isFile()) {
                        throw new IOException("File does not exist: " + file);
                }

                //Create input stream
                try (ImageInputStream iis = ImageIO.createImageInputStream(file)) {
                        
                        //Stream can not be opened
                        if (iis == null) {
                                throw new IOException("Could not open image stream: " + file.getAbsolutePath());
                        }

                        //Read the image stream
                        ImageReader reader = firstReaderFor(iis);
                        
                        
                        //No stream can be read
                        if (reader == null) {
                                throw new IOException("No ImageReader found for: " + file.getAbsolutePath());
                        }

                        try {
                                // We need metadata → ignoreMetadata must be false.
                                reader.setInput(iis, /*seekForwardOnly*/ true, /*ignoreMetadata*/ false);

                                IIOMetadata md = reader.getImageMetadata(0);
                                if (md == null) {
                                        return null;
                                }

                                //Try each strategy in order; return on first hit.
                                DPI dpi;

                                //Read from standard metadata tree
                                dpi = readDpiFromStandard(md);
                                if (dpi != null) {
                                        return dpi;
                                }

                                //Read from PNG tree
                                dpi = readDpiFromPng(md);
                                if (dpi != null) {
                                        return dpi;
                                }

                                //Read from JPG tree
                                dpi = readDpiFromJpeg(md);
                                if (dpi != null) {
                                        return dpi;
                                }

                                return null;
                        } 
                        
                        finally {
                                reader.dispose();
                        }
                }
        }

 
        private static DPI readDpiFromStandard(IIOMetadata md) {
                //1) STANDARD METADATA ("javax_imageio_1.0"): mm/pixel tp DPI
                try {
                        //Analyze tree
                        Node root = md.getAsTree(std_tree);
                        Node dimension = firstChild(root, "Dimension");
                        
                        //No information about DPI
                        if (dimension == null) {
                                return null;
                        }

                        //Values are millimeters per pixel (mm/pixel).
                        Double mmPerPixelX = getChildAttributeAsDouble(dimension, "HorizontalPixelSize", "value");
                        Double mmPerPixelY = getChildAttributeAsDouble(dimension, "VerticalPixelSize", "value");

                        //No data
                        if (mmPerPixelX == null || mmPerPixelX <= 0) {
                                return null;
                        }

                        //Compute dpi
                        double x_dpi = MM_PER_INCH / mmPerPixelX;
                        double y_dpi = (mmPerPixelY != null && mmPerPixelY > 0) ? (MM_PER_INCH / mmPerPixelY) : x_dpi;

                        return new DPI(x_dpi, y_dpi);
                        
                } 
                
                //Unable to read DPI
                catch (IllegalArgumentException e) {
                        return null;
                }
        }

    
        private static DPI readDpiFromPng(IIOMetadata md) {
                //PNG pHYs ("javax_imageio_png_1.0"): pixels/meter to DPI
                try {
                        //Analyze tree
                        Node root = md.getAsTree(png_tree);
                        Node pHYs = findChildNode(root, "pHYs");
                        
                        //No information about DPI
                        if (pHYs == null) {
                                return null;
                        }

                        // "meter" or "unknown"
                        String unit = getAttribute(pHYs, "unitSpecifier");
                        if (!"meter".equalsIgnoreCase(unit)) {
                                return null;
                        }

                        //Values are millimeters per pixel (mm/pixel).
                        Double xPpm = getAttributeAsDouble(pHYs, "pixelsPerUnitXAxis");
                        Double yPpm = getAttributeAsDouble(pHYs, "pixelsPerUnitYAxis");
                        
                        //No data
                        if (xPpm == null || yPpm == null || xPpm <= 0 || yPpm <= 0) {
                                return null;
                        }

                        double x_dpi = xPpm / inches_per_meter;
                        double y_dpi = yPpm / inches_per_meter;

                        return new DPI(x_dpi, y_dpi);
                        
                } 
                
                //Unable to read DPI from PNG
                catch (IllegalArgumentException e) {
                        return null;
                }
        }


        private static DPI readDpiFromJpeg(IIOMetadata md) {
                //JPEG JFIF ("javax_imageio_jpeg_image_1.0"): units+density to DPI
                try {
                        //Analyze tree
                        Node root = md.getAsTree(jpeg_tree);
                        Node app0JFIF = findChildNode(root, "app0JFIF");
                        
                        //No information about DPI
                        if (app0JFIF == null) {
                                return null;
                        }

                        //Get density
                        Integer resUnits = getAttributeAsString(app0JFIF, "resUnits"); // 0=none, 1=inch, 2=cm
                        Integer x_den = getAttributeAsString(app0JFIF, "Xdensity");
                        Integer y_den = getAttributeAsString(app0JFIF, "Ydensity");
                        if (resUnits == null || x_den == null || y_den == null) {
                                return null;
                        }

                        return switch (resUnits) {
                                case 1 ->
                                        //Dots per inch
                                        new DPI((double)x_den, (double)y_den);               
                                
                                case 2 ->
                                        //Dots per cm → inch
                                        new DPI(x_den * MM_PER_INCH / 10, y_den * MM_PER_INCH / 10); 
                                default ->
                                        // 0 or unexpected, unknown units
                                        null;                             
                        };     
                } 
                
                //Unable to read DPI from JPG
                catch (IllegalArgumentException e) {
                        return null;
                }
        }

    
        private static ImageReader firstReaderFor(ImageInputStream iis) {
                //ImageReader that can read the given stream, or null
                Iterator<ImageReader> it = ImageIO.getImageReaders(iis);
                return it.hasNext() ? it.next() : null;
        }

 
        private static Node firstChild(Node parent, String name) {
                //Return first child node
                if (parent == null) {
                        return null;
                }
                
                //Use DFS strategy
                NodeList children = parent.getChildNodes();
                for (int i = 0; i < children.getLength(); i++) {
                        Node n = children.item(i);
                        if (name.equals(n.getNodeName())) {
                                return n;
                        }
                }
                
                return null;
        }

    
        private static Node findChildNode(Node parent, String name) {
                //Depth-first search for the first child with the given name, or * null.
                if (parent == null) {
                        return null;
                }
                
                //Non-recursive solution
                if (name.equals(parent.getNodeName())) {
                        return parent;
                }
                
                //Start DFS
                NodeList children = parent.getChildNodes();
                for (int i = 0; i < children.getLength(); i++) {
                        Node found = findChildNode(children.item(i), name);
                        if (found != null) {
                                return found;
                        }
                }
                
                return null;
        }

        
        private static String getAttribute(Node n, String key) {
                //Get string attribute value stored at node n
                if (n == null) {
                        return null;
                }
                
                NamedNodeMap at = n.getAttributes();
                Node a = (at != null) ? at.getNamedItem(key) : null;
                return (a != null) ? a.getNodeValue() : null;
        }


        private static Integer getAttributeAsString(Node n, String key) {
                //Get integer attribute value stored at node n
                try {
                        String s = getAttribute(n, key);
                        return (s == null) ? null : Integer.valueOf(s);
                } 
                
                //Throw exception
                catch (NumberFormatException e) {
                        return null;
                }
        }


        private static Double getAttributeAsDouble(Node n, String key) {
                //Get double attribute value stored at node n
                try {
                        String s = getAttribute(n, key);
                        return (s == null) ? null : Double.valueOf(s);
                } 
                
                //Throw exception
                catch (NumberFormatException e) {
                        return null;
                }
        }


        private static Double getChildAttributeAsDouble(Node parent, String childName, String attrName) {
                //Read a child element attribute as double (or null).
                Node child = firstChild(parent, childName);
                return (child == null) ? null : getAttributeAsDouble(child, attrName);
        }
        
        
        public static void main(String[] args) throws IOException {

                //Test
                String file_name = "e:\\Tomas\\Java\\detectprojv2j\\test\\Africa\\12 Delisle_Lotter_Africa_cleaned2.jpg";
                File file = new File(file_name);


                //Compute resolution
                DPI dpi = ImageDPIReader.readDPI(file);

                BufferedImage img = ImageIO.read(file);
                String size = (img != null) ? (img.getWidth() + "x" + img.getHeight() + " px") : "unknown size";

                System.out.println("DPI : " + (dpi != null ? dpi.x_dpi  : "not present / unspecified"));
        }
}

