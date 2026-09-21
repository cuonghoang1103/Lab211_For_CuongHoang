package utils;

import constants.Constants;
import java.util.Random;

/**
 * Generates the random captcha of each login. A utility: no object, no field.
 *
 * @author HE176322
 */
public final class CaptchaUtils {

    // Private constructor: every method is called through the class name.
    private CaptchaUtils() {
    }

    // The brief's generateCaptcha: random positions in the alphabet, turned into chars.
    public static String generateCaptcha() {
        Random random = new Random();
        StringBuilder captcha = new StringBuilder();
        int position = 0;

        // one random character per turn, until the captcha is long enough
        for (int i = 0; i < Constants.CAPTCHA_LENGTH; i++) {
            position = random.nextInt(Constants.CAPTCHA_ALPHABET.length());
            captcha.append(Constants.CAPTCHA_ALPHABET.charAt(position));
        }

        return captcha.toString();
    }
}
