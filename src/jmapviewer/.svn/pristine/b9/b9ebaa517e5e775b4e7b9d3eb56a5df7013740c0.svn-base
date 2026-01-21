// License: GPL. For details, see Readme.txt file.
package org.openstreetmap.gui.jmapviewer.tilesources;

import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.FeatureAdapter;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.JMapViewerRuntimeException;
import org.openstreetmap.gui.jmapviewer.interfaces.ICoordinate;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Tile source for the Bing Maps REST Imagery API.
 * @see <a href="https://msdn.microsoft.com/en-us/library/bb259689.aspx">MSDN (1)</a>
 *  and <a href="https://msdn.microsoft.com/en-us/library/ff701724.aspx">MSDN (2)</a>
 */
public class BingAerialTileSource extends TMSTileSource {

    private static final Logger LOG = FeatureAdapter.getLogger(BingAerialTileSource.class);

    /** Setting key for Bing metadata API URL. Must contain {@link #API_KEY_PLACEHOLDER} */
    public static final String METADATA_API_SETTING = "jmapviewer.bing.metadata-api-url";
    /** Setting key for Bing API key */
    public static final String API_KEY_SETTING = "jmapviewer.bing.api-key";
    /** Placeholder to specify Bing API key in metadata API URL */
    public static final String API_KEY_PLACEHOLDER = "{apikey}";
    /** Placeholder to specify Bing API layer in metadata API URL */
    private static final String API_KEY_LAYER = "{layer}";

    /** Bing Metadata API URL */
    private static final String METADATA_API_URL =
            "https://dev.virtualearth.net/REST/v1/Imagery/Metadata/{layer}?include=ImageryProviders&output=xml&key=" + API_KEY_PLACEHOLDER;
    /** Original Bing API key created by Potlatch2 developers in 2010 */
    private static final String API_KEY = "Arzdiw4nlOJzRwOz__qailc8NiR31Tt51dN2D7cm57NrnceZnCpgOkmJhNpGoppU";

    private static final Pattern PATTERN_SUBDOMAIN = Pattern.compile("\\{subdomain}");
    private static final Pattern PATTERN_QUADKEY = Pattern.compile("\\{quadkey}");
    private static final Pattern PATTERN_CULTURE = Pattern.compile("\\{culture}");

    private volatile Future<List<Attribution>> attributions; // volatile is required for getAttribution(), see below.
    private String imageUrlTemplate;
    private int imageryZoomMax = Integer.MIN_VALUE;
    private String[] subdomains;
    private String brandLogoUri;
    private String layer = "Aerial";

    /**
     * Constructs a new {@code BingAerialTileSource}.
     */
    public BingAerialTileSource() {
        super(new TileSourceInfo("Bing", null, "Bing"));
        minZoom = 1;
    }

    /**
     * Constructs a new {@code BingAerialTileSource}.
     * @param info imagery info
     */
    public BingAerialTileSource(TileSourceInfo info) {
        super(info);
    }

    protected static class Attribution {
        private String attributionText;
        private int minZoom;
        private int maxZoom;
        private Coordinate min;
        private Coordinate max;
    }

    @Override
    public String getTileUrl(int zoom, int tilex, int tiley) throws IOException {
        // make sure that attribution is loaded. otherwise subdomains is null.
        if (getAttribution() == null)
            throw new IOException("Attribution is not loaded yet");

        int t = (zoom + tilex + tiley) % subdomains.length;
        String subdomain = subdomains[t];

        String url = imageUrlTemplate;
        url = PATTERN_SUBDOMAIN.matcher(url).replaceAll(subdomain);
        url = PATTERN_QUADKEY.matcher(url).replaceAll(computeQuadTree(zoom, tilex, tiley));

        return url;
    }

    /**
     * Set the layer for this Bing tile source
     * @param layer The layer to use. See
     *              <a href="https://learn.microsoft.com/en-us/bingmaps/rest-services/imagery/get-imagery-metadata#template-parameters">
     *                  get-imagery-metadata
     *              </a> for valid layers.
     * @since JMapViewer 2.18
     */
    protected void setLayer(String layer) {
        this.layer = layer;
    }

    protected URL getAttributionUrl() throws MalformedURLException {
        try {
            return new URI(FeatureAdapter.getSetting(METADATA_API_SETTING, METADATA_API_URL)
                    .replace(API_KEY_PLACEHOLDER, getKey())
                    .replace(API_KEY_LAYER, this.layer)).toURL();
        } catch (URISyntaxException e) {
            MalformedURLException malformedURLException = new MalformedURLException(e.getMessage());
            malformedURLException.initCause(e);
            throw malformedURLException;
        }
    }

    /**
     * Get the API key for Bing imagery
     * Order of preference is as follows:
     * <ol>
     *     <li>Custom API key provided by {@link FeatureAdapter#getSetting(String, String)} via {@link #API_KEY_SETTING}</li>
     *     <li>API key provided by {@link FeatureAdapter#retrieveApiKey(String)}</li>
     *     <li>The hardcoded API key. This should not be used whenever possible.</li>
     * </ol>
     * @return The API key to use
     */
    private String getKey() {
        // Preference order for key
        // 1. Custom API key
        // 2. Remote API key
        // 3. Hardcoded API key
        String key = FeatureAdapter.getSetting(API_KEY_SETTING, API_KEY);
        if (API_KEY.equals(key)) { // If the API key has not been customized, we try to retrieve the API key
            try {
                String rkey = FeatureAdapter.retrieveApiKey(this.getId());
                if (rkey != null)
                    key = rkey;
            } catch (IOException ioException) {
                FeatureAdapter.getLogger(this.getClass()).log(Level.WARNING, "Failed to retrieve api key", ioException);
            }
        }
        return key;
    }

    protected List<Attribution> parseAttributionText(InputSource xml) throws IOException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            factory.setXIncludeAware(false);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xml);

            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xpath = xPathFactory.newXPath();
            setImageUrlTemplate(xpath.compile("//ImageryMetadata/ImageUrl/text()").evaluate(document));
            setImageryZoomMax(Integer.parseInt(xpath.compile("//ImageryMetadata/ZoomMax/text()").evaluate(document)));

            NodeList subdomainTxt = (NodeList) xpath.compile("//ImageryMetadata/ImageUrlSubdomains/string/text()")
                    .evaluate(document, XPathConstants.NODESET);
            setSubdomains(subdomainTxt);

            brandLogoUri = xpath.compile("/Response/BrandLogoUri/text()").evaluate(document);

            XPathExpression attributionXpath = xpath.compile("Attribution/text()");
            XPathExpression coverageAreaXpath = xpath.compile("CoverageArea");
            XPathExpression zoomMinXpath = xpath.compile("ZoomMin/text()");
            XPathExpression zoomMaxXpath = xpath.compile("ZoomMax/text()");
            XPathExpression southLatXpath = xpath.compile("BoundingBox/SouthLatitude/text()");
            XPathExpression westLonXpath = xpath.compile("BoundingBox/WestLongitude/text()");
            XPathExpression northLatXpath = xpath.compile("BoundingBox/NorthLatitude/text()");
            XPathExpression eastLonXpath = xpath.compile("BoundingBox/EastLongitude/text()");

            NodeList imageryProviderNodes = (NodeList) xpath.compile("//ImageryMetadata/ImageryProvider")
                    .evaluate(document, XPathConstants.NODESET);
            List<Attribution> attributionsList = new ArrayList<>(imageryProviderNodes.getLength());
            for (int i = 0; i < imageryProviderNodes.getLength(); i++) {
                Node providerNode = imageryProviderNodes.item(i);

                String attribution = attributionXpath.evaluate(providerNode);

                NodeList coverageAreaNodes = (NodeList) coverageAreaXpath.evaluate(providerNode, XPathConstants.NODESET);
                for (int j = 0; j < coverageAreaNodes.getLength(); j++) {
                    Node areaNode = coverageAreaNodes.item(j);
                    Attribution attr = new Attribution();
                    attr.attributionText = attribution;

                    attr.maxZoom = Integer.parseInt(zoomMaxXpath.evaluate(areaNode));
                    attr.minZoom = Integer.parseInt(zoomMinXpath.evaluate(areaNode));

                    double southLat = Double.parseDouble(southLatXpath.evaluate(areaNode));
                    double northLat = Double.parseDouble(northLatXpath.evaluate(areaNode));
                    double westLon = Double.parseDouble(westLonXpath.evaluate(areaNode));
                    double eastLon = Double.parseDouble(eastLonXpath.evaluate(areaNode));
                    attr.min = new Coordinate(southLat, westLon);
                    attr.max = new Coordinate(northLat, eastLon);

                    attributionsList.add(attr);
                }
            }

            return attributionsList;
        } catch (SAXException | ParserConfigurationException | XPathExpressionException | NumberFormatException e) {
            LOG.log(Level.SEVERE, "Could not parse Bing aerials attribution metadata.", e);
        }
        return null;
    }

    @Override
    public int getMaxZoom() {
        if (imageryZoomMax != Integer.MIN_VALUE)
            return imageryZoomMax;
        else
            return 22;
    }

    @Override
    public boolean requiresAttribution() {
        return true;
    }

    @Override
    public String getAttributionLinkURL() {
        // Terms of Use URL to comply with Bing Terms of Use
        // (the requirement is that we have such a link at the bottom of the window)
        return "https://www.microsoft.com/maps/assets/docs/terms.aspx";
    }

    @Override
    public Image getAttributionImage() {
        try {
            final URL imageResource = JMapViewer.class.getResource("images/bing_maps.png");
            if (imageResource != null) {
                return FeatureAdapter.readImage(imageResource);
            } else {
                // Some Linux distributions (like Debian) will remove Bing logo from sources, so get it at runtime
                for (int i = 0; i < 5 && (getAttribution() == null || getAttribution().isEmpty()); i++) {
                    // Makes sure attribution is loaded
                    if (JMapViewer.debug) {
                        LOG.log(Level.FINE, "Bing attribution attempt {0}", (i + 1));
                    }
                }
                if (brandLogoUri != null && !brandLogoUri.isEmpty()) {
                    LOG.log(Level.FINE, "Reading Bing logo from {0}", brandLogoUri);
                    return FeatureAdapter.readImage(new URI(brandLogoUri));
                }
            }
        } catch (URISyntaxException | IOException e) {
            LOG.log(Level.SEVERE, String.format("Error while retrieving Bing logo: %s", e.getMessage()));
        }
        return null;
    }

    @Override
    public String getAttributionImageURL() {
        return "https://opengeodata.org/microsoft-imagery-details";
    }

    @Override
    public String getTermsOfUseText() {
        return null;
    }

    @Override
    public String getTermsOfUseURL() {
        return "https://opengeodata.org/microsoft-imagery-details";
    }

    protected Callable<List<Attribution>> getAttributionLoaderCallable() {
        return () -> {
            int waitTimeSec = 1;
            while (true) {
                try {
                    InputSource xml = new InputSource(getAttributionUrl().openStream());
                    List<Attribution> r = parseAttributionText(xml);
                    LOG.log(Level.FINE, "Successfully loaded Bing attribution data.");
                    return r;
                } catch (IllegalArgumentException | IOException ex) {
                    LOG.log(Level.SEVERE, String.format("Could not connect to Bing API. Will retry in %d seconds.", waitTimeSec));
                    LOG.log(Level.FINE, ex.getMessage(), ex);
                    Thread.sleep(TimeUnit.SECONDS.toMillis(waitTimeSec));
                    waitTimeSec *= 2;
                }
            }
        };
    }

    /**
     * Get the attribution data that is currently loaded
     * @return The list of {@link Attribution} data or {@code null}, if no attribution data has been loaded yet.
     */
    protected List<Attribution> getAttribution() {
        if (attributions == null) {
            // see http://www.cs.umd.edu/~pugh/java/memoryModel/DoubleCheckedLocking.html
            synchronized (BingAerialTileSource.class) {
                if (attributions == null) {
                  final FutureTask<List<Attribution>> loader = new FutureTask<>(getAttributionLoaderCallable());
                  new Thread(loader, "bing-attribution-loader").start();
                  attributions = loader;
                }
            }
        }
        if (attributions.isDone()) {
            try {
                return attributions.get();
            } catch (ExecutionException ex) {
                throw new JMapViewerRuntimeException(ex);
            } catch (InterruptedException ign) {
                LOG.log(Level.SEVERE, "InterruptedException: {0}", ign.getMessage());
                LOG.log(Level.FINE, ign.getMessage(), ign);
                Thread.currentThread().interrupt();
            }
        }
        return null;
    }

    @Override
    public String getAttributionText(int zoom, ICoordinate topLeft, ICoordinate botRight) {
        try {
            final List<Attribution> data = getAttribution();
            if (data == null)
                return "Error loading Bing attribution data";
            return data.stream()
                    .filter(attr -> zoom <= attr.maxZoom && zoom >= attr.minZoom)
                    .filter(attr -> topLeft.getLon() < attr.max.getLon() && botRight.getLon() > attr.min.getLon())
                    .filter(attr -> topLeft.getLat() > attr.min.getLat() && botRight.getLat() < attr.max.getLat())
                    .map(attr -> attr.attributionText)
                    .collect(Collectors.joining(" "));
        } catch (RuntimeException e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
        }
        return "Error loading Bing attribution data";
    }

    private void setImageUrlTemplate(String template) {
        String noHttpTemplate = template.replace("http://ecn.{subdomain}.tiles.virtualearth.net/",
                "https://ecn.{subdomain}.tiles.virtualearth.net/");
        this.imageUrlTemplate = PATTERN_CULTURE.matcher(noHttpTemplate).replaceAll(Locale.getDefault().toString());
    }

    private void setImageryZoomMax(int maxZoom) {
        imageryZoomMax = maxZoom;
    }

    private void setSubdomains(NodeList subdomainTxt) {
        subdomains = new String[subdomainTxt.getLength()];
        for (int i = 0; i < subdomainTxt.getLength(); i++) {
            subdomains[i] = subdomainTxt.item(i).getNodeValue();
        }
    }

    private static String computeQuadTree(int zoom, int tilex, int tiley) {
        StringBuilder k = new StringBuilder();
        for (int i = zoom; i > 0; i--) {
            char digit = 48;
            int mask = 1 << (i - 1);
            if ((tilex & mask) != 0) {
                digit += (char) 1;
            }
            if ((tiley & mask) != 0) {
                digit += (char) 2;
            }
            k.append(digit);
        }
        return k.toString();
    }
}
