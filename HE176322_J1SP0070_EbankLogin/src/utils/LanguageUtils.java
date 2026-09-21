package utils;

import constants.Constants;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Reads the translated texts from the files Language_vi.properties / Language_en.properties.
 * A utility: no object, no field - the language is always passed in.
 *
 * @author HE176322
 */
public final class LanguageUtils {

    // Private constructor: every method is called through the class name.
    private LanguageUtils() {
    }

    // Returns the text of a key in the given language (ResourceBundle picks the file).
    public static String getText(Locale locale, String key) {
        ResourceBundle bundle = ResourceBundle.getBundle(Constants.BUNDLE_NAME, locale);

        return bundle.getString(key);
    }
}
