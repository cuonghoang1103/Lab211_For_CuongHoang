package utils;

import constants.Constants;
import constants.Message;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5 of a text, the hash employee.dat stores (MD5 of 123456 is e10adc39...). Main hashes
 * the typed password before it goes anywhere; login compares two hashes, since there is
 * no way back from a hash.
 *
 * @author HE176322
 */
public final class MD5Utils {

    // Private constructor: every method is called through the class name.
    private MD5Utils() {
    }

    // Returns the MD5 of the text as 32 lowercase hex digits.
    public static String hash(String text) {
        StringBuilder hex = new StringBuilder();

        // getInstance throws a checked exception for an unknown algorithm name
        try {
            byte[] digestArray = MessageDigest.getInstance(Constants.MD5_ALGORITHM)
                    .digest(text.getBytes(StandardCharsets.UTF_8));

            // two hex digits per byte: 10 must be "0a", not "a"
            for (byte digestByte : digestArray) {
                hex.append(String.format(Constants.HEX_FORMAT, digestByte));
            }
        } catch (NoSuchAlgorithmException e) {
            // every JVM must provide MD5, so a real machine never gets here
            throw new IllegalStateException(Message.MD5_ERROR, e);
        }

        return hex.toString();
    }
}
