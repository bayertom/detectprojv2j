// License: GPL. For details, see Readme.txt file.
package org.openstreetmap.gui.jmapviewer;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Unit tests of {@link TileRange} class.
 */
class TileRangeTest {

    /**
     * Unit test of {@link TileRange#size}.
     */
    @Test
    void testSize() {
        assertEquals(16, new TileRange(
                new TileXY(3, 3), 
                new TileXY(6, 6), 10).size());
    }

    /**
     * Ensure that something exceptional happens when an integer overflow happens
     */
    @Test
    void testSizeTooLarge() {
        final TileRange allZ16 = new TileRange(new TileXY(0, 0), new TileXY(1 << 16, 1 << 16), 16);
        assertThrows(ArithmeticException.class, allZ16::size);
    }
}
