package kraken.plugin.api;

import java.io.ByteArrayOutputStream;
import java.util.regex.Pattern;

/**
 * Text utilities.
 */
public final class Text {

    private Text() {
    }

    /**
     * Filters special characters used by the RuneScape client.
     */
    public static byte[] filterSpecialChars(byte[] bin) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        for (int i = 0; i < bin.length; i++) {
            if (bin[i] == -62) {
                bos.write(' ');
            } else if (bin[i] == -96) {
                // nothing
            } else {
                bos.write(bin[i]);
            }
        }
        return bos.toByteArray();
    }

    /**
     * Attempts to find a regular expression inside of a pattern.
     *
     * @param regex The regular expression to find.
     * @param pattern The pattern to search within.
     * @return If the regular expression was found.
     */
    public static boolean regexFind(String regex, String pattern) {
        Pattern p = Pattern.compile(regex);
        return p.matcher(pattern).find();
    }
}
