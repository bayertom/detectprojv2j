// License: GPL. For details, see Readme.txt file.
package org.openstreetmap.gui.jmapviewer.tilesources;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

/**
 * Tests for TemplatedTMSTileSource
 */
public class TemplatedTMSTileSourceTest {

    private static final Collection<String> TMS_IMAGERIES = Collections.singleton("http://imagico.de/map/osmim_tiles.php?layer=S2A_R136_N41_20150831T093006&z={zoom}&x={x}&y={-y}"
            /*
             *  generate for example with:
             *  $ curl https://josm.openstreetmap.de/maps | \
             *    xmlstarlet sel -N 'josm=http://josm.openstreetmap.de/maps-1.0' -t -v "//josm:entry[josm:type='tms']/josm:url" -n  | \
             *    sed -e 's/\&amp;/\&/g' -e 's/^/"/' -e 's/$/",/'
             */);

    /**
     * triple of:
     *  * baseUrl
     *  * expected tile url for zoom=4, x=5, y=6
     *  * expected tile url for zoom=3, x=2, y=1
     */
    private final Collection<String[]> TEST_DATA = Arrays.asList(new String[][] {
        /*
         * generate with main method below once TMS_IMAGERIES is filled in
         */
        new String[] {
                "http://imagico.de/map/osmim_tiles.php?layer=S2A_R136_N41_20150831T093006&z={zoom}&x={x}&y={-y}", 
                "http://imagico.de/map/osmim_tiles.php?layer=S2A_R136_N41_20150831T093006&z=4&x=5&y=9", 
                "http://imagico.de/map/osmim_tiles.php?layer=S2A_R136_N41_20150831T093006&z=3&x=2&y=6"
                }
    });

    /**
     * Check standard template
     */
    @Test
    void testGetTileUrl() {
        checkGetTileUrl(
                "http://localhost/{z}/{x}/{y}",
                "http://localhost/4/5/6",
                "http://localhost/3/1/2"
                );
    }

    /**
     * Check template with positive zoom index
     */
    @Test
    void testGetTileUrl_positive_zoom() {
        checkGetTileUrl(
                "http://localhost/{zoom+5}/{x}/{y}",
                "http://localhost/9/5/6",
                "http://localhost/8/1/2"
                );
    }

    /**
     * Check template with negative zoom index
     */
    @Test
    void testGetTileUrl_negative_zoom() {
        checkGetTileUrl(
                "http://localhost/{zoom-5}/{x}/{y}",
                "http://localhost/-1/5/6",
                "http://localhost/-2/1/2"
                );
    }

    /**
     * Check template with inverse zoom index
     */
    @Test
    void testGetTileUrl_inverse_negative_zoom() {
        checkGetTileUrl(
                "http://localhost/{5-zoom}/{x}/{y}",
                "http://localhost/1/5/6",
                "http://localhost/2/1/2"
                );
    }

    /**
     * Check template with inverse zoom index and negative zoom index
     */
    @Test
    void testGetTileUrl_both_offsets() {
        checkGetTileUrl(
                "http://localhost/{10-zoom-5}/{x}/{y}",
                "http://localhost/1/5/6",
                "http://localhost/2/1/2"
                );
    }

    /**
     * Test template with switch
     */
    @Test
    void testGetTileUrl_apiKey() {
        System.setProperty("id1.api-key", "wololo");
        TileSourceInfo testImageryTMS = new TileSourceInfo("test imagery", "http://localhost/{zoom}/{x}/{y}?token={apikey}&foo=bar", "id1");
        TemplatedTMSTileSource ts = new TemplatedTMSTileSource(testImageryTMS);
        assertEquals("http://localhost/4/5/6?token=wololo&foo=bar", ts.getTileUrl(4, 5, 6));
    }

    /**
     * Test template with switch
     */
    @Test
    void testGetTileUrl_switch() {
        TileSourceInfo testImageryTMS = new TileSourceInfo("test imagery", "http://{switch:a,b,c}.localhost/{10-zoom-5}/{x}/{y}", "id1");
        TemplatedTMSTileSource ts = new TemplatedTMSTileSource(testImageryTMS);
        assertTrue(
                Stream.of(
                        "http://a.localhost/4/2/3",
                        "http://b.localhost/4/2/3",
                        "http://c.localhost/4/2/3"
                        )
                .anyMatch(Predicate.isEqual(ts.getTileUrl(1, 2, 3)))
                );

        assertTrue(
                Stream.of(
                        "http://a.localhost/3/3/4",
                        "http://b.localhost/3/3/4",
                        "http://c.localhost/3/3/4"
                        )
                .anyMatch(Predicate.isEqual(ts.getTileUrl(2, 3, 4)))
                );
        assertTrue(
                Stream.of(
                        "http://a.localhost/2/4/5",
                        "http://b.localhost/2/4/5",
                        "http://c.localhost/2/4/5"
                        )
                .anyMatch(Predicate.isEqual(ts.getTileUrl(3, 4, 5)))
                );
        assertTrue(
                Stream.of(
                        "http://a.localhost/1/5/6",
                        "http://b.localhost/1/5/6",
                        "http://c.localhost/1/5/6"
                        )
                .anyMatch(Predicate.isEqual(ts.getTileUrl(4, 5, 6)))
                );
    }

    @Test
    void testGetTileUrl_yahoo() {
        checkGetTileUrl(
                "http://localhost/{z}/{x}/{!y}",
                "http://localhost/4/5/1",
                "http://localhost/3/1/1"
                );

    }

    @Test
    void testGetTileUrl_negative_y() {
        checkGetTileUrl(
                "http://localhost/{z}/{x}/{-y}",
                "http://localhost/4/5/9",
                "http://localhost/3/1/5"
                );

    }

    @Test
    void testGetTileUrl_quad() {
        checkGetTileUrl(
                "http://localhost/{quad}",
                "http://localhost/0321",
                "http://localhost/021"
                );

    }

    private void checkGetTileUrl(String url, String expected123, String expected312) {
        TileSourceInfo testImageryTMS = new TileSourceInfo("test imagery", url, "id1");
        TemplatedTMSTileSource ts = new TemplatedTMSTileSource(testImageryTMS);
        assertEquals(expected123, ts.getTileUrl(4, 5, 6));
        assertEquals(expected312, ts.getTileUrl(3, 1, 2));
    }

    /**
     * Tests all entries in TEST_DATA. This test will fail if {switch:...} template is used
     */
    @Test
    void testAllUrls() {
        for (String[] test: TEST_DATA) {
            TileSourceInfo testImageryTMS = new TileSourceInfo("test imagery", test[0], "id1");
            TemplatedTMSTileSource ts = new TemplatedTMSTileSource(testImageryTMS);
            assertEquals(test[1], ts.getTileUrl(4, 5, 6));
            assertEquals(test[2], ts.getTileUrl(3, 2, 1));
        }
    }

    public static void main(String[] args) {
        for (String url: TMS_IMAGERIES) {
            TileSourceInfo testImageryTMS = new TileSourceInfo("test imagery", url, "id1");
            TemplatedTMSTileSource ts = new TemplatedTMSTileSource(testImageryTMS);
            System.out.println(MessageFormat.format("new String[]{\"{0}\", \"{1}\", \"{2}\"},",
                    url, ts.getTileUrl(4, 5, 6), ts.getTileUrl(3, 2, 1)));
        }
    }
}
