/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package detectprojv2j.algorithms.dpi;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataFormatImpl;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageInputStream;
import org.w3c.dom.NodeList;

/**
 *
 * @author Tomáš
 */
public class DPI {

        public static void getDPI(final File f, int [] h_size, int [] v_size) 
        {
                try 
                {
                        //Create input stream and get image rs
                        ImageInputStream is = ImageIO.createImageInputStream(f);
                        Iterator<ImageReader> ir = ImageIO.getImageReaders(is);

                        //Process all image readers
                        if (ir.hasNext()) 
                        {
                                //Get current image reader
                                ImageReader r = ir.next();
                                r.setInput(is);

                                //Get image meta-data
                                IIOMetadata metadata = r.getImageMetadata(0);
                                IIOMetadataNode metadata_tree = (IIOMetadataNode) metadata.getAsTree(IIOMetadataFormatImpl.standardMetadataFormatName);
                                IIOMetadataNode dimension = (IIOMetadataNode) metadata_tree.getElementsByTagName("Dimension").item(0);
                               
                                //Horizontal and vertical pixel size
                                h_size[0] = getPixelSize(dimension, "HorizontalPixelSize");
                                v_size[0] = getPixelSize(dimension, "VerticalPixelSize");

                                // TODO: Convert pixelsPerMM to DPI left as an exercise to the r.. ;-)  
                                //System.out.println("horizontalPixelSizeMM: " + h_size[0]);
                                //System.out.println("verticalPixelSizeMM: " + v_size[0]);
                        } 
                        
                        //Throw exception
                        else 
                        {
                                 throw new IOException ("IOException: " + "can not read the file" +  f.getName());                                              
                        }
                        
                } 
                
                //Exception reading the file
                catch (Exception e) 
                {
                        e.printStackTrace();
                }
        }


        private static int getPixelSize(final IIOMetadataNode dim, final String element) 
        {
                // Get horizontal/vertical size of the pixel              
                final NodeList pixels = dim.getElementsByTagName(element);
                
                if (pixels.getLength() > 0)
                {
                        //Get pixel size and convert to integer
                        final IIOMetadataNode  pixel_size_met = (IIOMetadataNode) pixels.item(0);
                        final int pixel_size = Integer.parseInt(pixel_size_met.getAttribute("value"));
                        
                        return pixel_size;
                }
                        
               return -1;
        }
}
