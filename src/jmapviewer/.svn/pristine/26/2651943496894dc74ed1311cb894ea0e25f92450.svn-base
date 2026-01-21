// License: GPL. For details, see Readme.txt file.
package org.openstreetmap.gui.jmapviewer.tilesources;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openstreetmap.gui.jmapviewer.FeatureAdapter;
import org.openstreetmap.gui.jmapviewer.interfaces.TemplatedTileSource;

/**
 * Handles templated TMS Tile Source. Templated means, that some patterns within
 * URL gets substituted.
 * <p>
 * Supported parameters
 * <ul>
 * <li>{zoom} - substituted with zoom level</li>
 * <li>{z} - as above</li>
 * <li>{NUMBER-zoom} - substituted with result of equation "NUMBER - zoom",
 *                  eg. {20-zoom} for zoom level 15 will result in 5 in this place</li>
 * <li>{zoom+number} - substituted with result of equation "zoom + number",
 *                 eg. {zoom+5} for zoom level 15 will result in 20.</li>
 * <li>{x} - substituted with X tile number</li>
 * <li>{y} - substituted with Y tile number</li>
 * <li>{!y} - substituted with Yahoo Y tile number</li>
 * <li>{-y} - substituted with reversed Y tile number</li>
 * <li>{quad} - Microsoft style quadkey</li>
 * <li>{apikey} - substituted with API key retrieved for the imagery id</li>
 * <li>{switch:VAL_A,VAL_B,VAL_C,...} - substituted with one of VAL_A, VAL_B, VAL_C. Usually
 *                                  used to specify many tile servers</li>
 * <li>{header:(HEADER_NAME,HEADER_VALUE)} - sets the headers to be sent to tile server</li>
 * </ul>
 */
public class TemplatedTMSTileSource extends TMSTileSource implements TemplatedTileSource {

    // CHECKSTYLE.OFF: SingleSpaceSeparator
    private static final String COOKIE_HEADER   = "Cookie";
    private static final Pattern PATTERN_ZOOM    = Pattern.compile("\\{(?:(\\d+)-)?z(?:oom)?([+-]\\d+)?}");
    private static final Pattern PATTERN_X       = Pattern.compile("\\{x}");
    private static final Pattern PATTERN_Y       = Pattern.compile("\\{y}");
    private static final Pattern PATTERN_Y_YAHOO = Pattern.compile("\\{!y}");
    private static final Pattern PATTERN_NEG_Y   = Pattern.compile("\\{-y}");
    private static final Pattern PATTERN_QUAD    = Pattern.compile("\\{quad}");
    private static final Pattern PATTERN_SWITCH  = Pattern.compile("\\{switch:([^}]+)}");
    private static final Pattern PATTERN_HEADER  = Pattern.compile("\\{header\\(([^,]+),([^}]+)\\)}");
    private static final Pattern PATTERN_API_KEY = Pattern.compile("\\{apikey}");
    private static final Pattern PATTERN_PARAM  = Pattern.compile("\\{((?:\\d+-)?z(?:oom)?(:?[+-]\\d+)?|x|y|!y|-y|quad|switch:([^}]+))}");

    // CHECKSTYLE.ON: SingleSpaceSeparator

    private static final Pattern[] ALL_PATTERNS = {
            PATTERN_HEADER, PATTERN_ZOOM, PATTERN_X, PATTERN_Y, PATTERN_Y_YAHOO, PATTERN_NEG_Y, PATTERN_SWITCH, PATTERN_API_KEY,
            PATTERN_QUAD
    };

    private Random rand;
    private String[] randomParts;
    private final Map<String, String> headers = new HashMap<>();
    private boolean inverse_zoom;
    private int zoom_offset;

    /**
     * Creates Templated TMS Tile Source based on ImageryInfo
     * @param info imagery info
     */
    public TemplatedTMSTileSource(TileSourceInfo info) {
        super(info);
        String cookies = info.getCookies();
        if (cookies != null && !cookies.isEmpty()) {
            headers.put(COOKIE_HEADER, cookies);
        }
        handleTemplate(info.getId());
    }

    private void replacePattern(BiConsumer<Matcher, StringBuffer> replaceAction, Pattern... patterns) {
        for (Pattern p : patterns) {
            StringBuffer output = new StringBuffer();
            Matcher m = p.matcher(baseUrl);
            while (m.find()) {
                replaceAction.accept(m, output);
            }
            m.appendTail(output);
            baseUrl = output.toString();
        }
    }

    private void handleTemplate(String imageryId) {
        // Capturing group pattern on switch values
        Matcher m = PATTERN_SWITCH.matcher(baseUrl);
        if (m.find()) {
            rand = new Random();
            randomParts = m.group(1).split(",", -1);
        }
        // Capturing group pattern on header values
        replacePattern((matcher, output) -> {
            headers.put(matcher.group(1), matcher.group(2));
            matcher.appendReplacement(output, "");
        }, PATTERN_HEADER);
        // Capturing group pattern on API key values
        if (imageryId != null) {
            replacePattern((matcher, output) -> {
                try {
                    matcher.appendReplacement(output, FeatureAdapter.retrieveApiKey(imageryId));
                } catch (IOException | RuntimeException e) {
                    throw new IllegalArgumentException(e);
                }
            }, PATTERN_API_KEY);
        }
        // Capturing group pattern on zoom values
        m = PATTERN_ZOOM.matcher(baseUrl);
        if (m.find()) {
            if (m.group(1) != null) {
                inverse_zoom = true;
                zoom_offset = Integer.parseInt(m.group(1));
            }
            if (m.group(2) != null) {
                String ofs = m.group(2);
                if (ofs.startsWith("+"))
                    ofs = ofs.substring(1);
                zoom_offset += Integer.parseInt(ofs);
            }
        }
    }

    @Override
    public Map<String, String> getHeaders() {
        return headers;
    }

    @Override
    public String getTileUrl(int zoom, int tilex, int tiley) {
        StringBuffer url = new StringBuffer(baseUrl.length());
        Matcher matcher = PATTERN_PARAM.matcher(baseUrl);
        while (matcher.find()) {
            final String replacement;
            switch (matcher.group(1)) {
            case "z": // PATTERN_ZOOM
            case "zoom":
                replacement = Integer.toString((inverse_zoom ? -zoom : zoom) + zoom_offset);
                break;
            case "x": // PATTERN_X
                replacement = Integer.toString(tilex);
                break;
            case "y": // PATTERN_Y
                replacement = Integer.toString(tiley);
                break;
            case "!y": // PATTERN_Y_YAHOO
                replacement = Integer.toString((int) Math.pow(2, zoom - 1d) - 1 - tiley);
                break;
            case "-y": // PATTERN_NEG_Y
                replacement = Integer.toString((int) Math.pow(2, zoom)-1-tiley);
                break;
            case "switch:":
                replacement = getRandomPart(randomParts);
                break;
            case "quad": // PATTERN_QUAD
                replacement = xyzToQuadKey(tilex, tiley, zoom);
                break;
            default:
                // handle switch/zoom here, as group will contain parameters and switch will not work
                if (PATTERN_ZOOM.matcher("{" + matcher.group(1) + "}").matches()) {
                    replacement = Integer.toString((inverse_zoom ? -zoom : zoom) + zoom_offset);
                } else if (PATTERN_SWITCH.matcher("{" + matcher.group(1) + "}").matches()) {
                    replacement = getRandomPart(randomParts);
                } else {
                    replacement = '{' + matcher.group(1) + '}';
                }
            }
            matcher.appendReplacement(url, replacement);
        }
        matcher.appendTail(url);
        return url.toString().replace(" ", "%20");
    }

    /**
     * Convert an x y z coordinate to a quadkey
     * @param x The x coordinate
     * @param y The y coordinate
     * @param z The z coordinate
     * @return The quadkey
     */
    private static String xyzToQuadKey(int x, int y, int z) {
        final char[] string = new char[z];
        for (int i = z; i > 0; i--) {
            char digit = '0';
            final int mask = 1 << (i - 1);
            if ((x & mask) != 0) {
                digit++;
            }
            if ((y & mask) != 0) {
                digit += 2;
            }
            string[z - i] = digit;
        }
        return String.valueOf(string);
    }

    protected String getRandomPart(final String[] parts) {
        return parts[rand.nextInt(parts.length)];
    }

    /**
     * Checks if url is acceptable by this Tile Source
     * @param url URL to check
     */
    public static void checkUrl(String url) {
        assert url != null && !"".equals(url) : "URL cannot be null or empty";
        Matcher m = Pattern.compile("\\{[^}]*}").matcher(url);
        while (m.find()) {
            boolean isSupportedPattern = Arrays.stream(ALL_PATTERNS).anyMatch(pattern -> pattern.matcher(m.group()).matches());
            if (!isSupportedPattern) {
                throw new IllegalArgumentException(
                        m.group() + " is not a valid TMS argument. Please check this server URL:\n" + url);
            }
        }
    }
}
