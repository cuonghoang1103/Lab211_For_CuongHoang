package utils;

import constants.Constants;
import constants.Message;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5 hashing, as the brief requires ("Password use the MD5 encryption").
 *
 * @author HE176322
 */
public final class MD5Utils {

    // Private constructor: every method is called through the class name.
    private MD5Utils() {
    }

    // Hashes a text with MD5 and writes the 16-byte digest as 32 lower-case hex digits.
    public static String hash(String text) {
        // MessageDigest.getInstance throws a checked exception for an
        // unknown algorithm name
        try {
            MessageDigest digester = MessageDigest.getInstance(Constants.HASH_ALGORITHM);
            // UTF-8 fixed, so the same password hashes the same on every machine
            byte[] digest = digester.digest(text.getBytes(StandardCharsets.UTF_8));
            StringBuilder hex = new StringBuilder();
            // two hex digits for every byte of the digest
            for (byte b : digest) {
                hex.append(String.format(Constants.HEX_FORMAT, b));
            }
            return hex.toString();
        } catch (NoSuchAlgorithmException e) {
            // cannot happen: every Java platform must provide MD5; never fall
            // back to storing the plain password
            throw new IllegalStateException(Message.HASH_MISSING, e);
        }
    }
}
