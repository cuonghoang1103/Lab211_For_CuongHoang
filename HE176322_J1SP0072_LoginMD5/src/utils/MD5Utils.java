package utils;

import constants.Constants;
import constants.Message;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5 hashing, as the brief requires ("Password use the MD5 encryption"). Called only by
 * main (checklist 1.1: "mã hóa thực hiện ở Main"), so a plain password never goes further
 * than main.
 *
 * @author HE176322
 */
public final class MD5Utils {

    // Private constructor: every method is called through the class name.
    private MD5Utils() {
    }

    // Hashes a text with MD5 and writes the 16-byte digest as 32 lower-case hex digits.
    public static String hash(String text) {
        MessageDigest digester = null;
        byte[] digestArray = null;
        StringBuilder hexBuilder = new StringBuilder();

        // getInstance throws a checked exception for an unknown algorithm name
        try {
            digester = MessageDigest.getInstance(Constants.HASH_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            // cannot happen: every Java platform must provide MD5; never fall back to
            // storing the plain password
            throw new IllegalStateException(Message.HASH_MISSING, e);
        }

        // UTF-8 fixed, so the same password hashes the same on every machine
        digestArray = digester.digest(text.getBytes(StandardCharsets.UTF_8));

        // two hex digits for every byte of the digest ("%02x": 0x07 -> "07")
        for (byte digestByte : digestArray) {
            hexBuilder.append(String.format(Constants.HEX_FORMAT, digestByte));
        }

        return hexBuilder.toString();
    }
}
