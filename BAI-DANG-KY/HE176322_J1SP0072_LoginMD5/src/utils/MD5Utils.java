package utils;

import constants.Constants;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * Bam mat khau bang MD5.
 *
 * @author HE176322
 */
public final class MD5Utils {

    // constructor private: chi goi ham static
    private MD5Utils() {
    }

    // bam chuoi thanh 32 ky tu hex
    public static String hash(String text) throws Exception {
        MessageDigest messageDigest = MessageDigest.getInstance(Constants.HASH_ALGORITHM);
        byte[] digestArray = messageDigest.digest(text.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexText = new StringBuilder();
        // moi byte doi thanh 2 chu so hex (0x07 -> "07")
        for (byte digestByte : digestArray) {
            hexText.append(String.format("%02x", digestByte));
        }
        return hexText.toString();
    }
}
