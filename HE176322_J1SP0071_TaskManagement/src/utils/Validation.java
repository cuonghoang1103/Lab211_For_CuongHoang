package utils;

import constants.Constants;
import constants.Message;
import constants.TaskType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Shared checks for what the user typed. A utility: no object, no keyboard, no print - it
 * turns one typed line into a clean value, or throws the message of the broken rule.
 *
 * @author HE176322
 */
public final class Validation {

    // Private constructor: every method is called through the class name.
    private Validation() {
    }

    // Returns the text without surrounding spaces.
    public static String getText(String input) {
        // a missing line is treated like an empty one
        if (input == null) {
            return "";
        }

        return input.trim();
    }

    // Converts a menu choice and checks it lies in [min, max].
    public static int getChoice(String input, int min, int max) throws Exception {
        int choice = 0;

        // parse first, so a letter gives the "number" message
        try {
            choice = Integer.parseInt(getText(input));
        } catch (NumberFormatException e) {
            // letters or an empty line: not a number at all
            throw new Exception(Message.INVALID_NUMBER);
        }

        // then check the range, so 9 gives the "range" message
        if ((choice < min) || (choice > max)) {
            throw new Exception(String.format(Message.INVALID_RANGE, min, max));
        }

        return choice;
    }

    // Returns the text when it is not blank.
    public static String getRequired(String input, String error) throws Exception {
        String text = getText(input);

        // blank text is refused
        if (text.isEmpty()) {
            throw new Exception(error);
        }

        return text;
    }

    // The brief: "Check the TaskTypeID must exist (1-4)".
    public static TaskType getTaskType(String input) throws Exception {
        String text = getRequired(input, Message.TYPE_EMPTY);
        TaskType taskType = null;
        int typeId = 0;

        // Integer (wrapper class) decides whether the text is a number
        try {
            typeId = Integer.parseInt(text);
        } catch (NumberFormatException e) {
            // "Code" instead of 1
            throw new Exception(Message.TYPE_NOT_NUMBER);
        }

        // look the number up among the four fixed types
        taskType = TaskType.findById(typeId);

        // a number, but not 1..4
        if (taskType == null) {
            throw new Exception(String.format(Message.TYPE_NOT_EXIST, typeId,
                    TaskType.getFirstId(), TaskType.getLastId()));
        }

        return taskType;
    }

    // The brief: "valid date in the format dd-MM-yyyy", with SimpleDateFormat.
    public static Date getDate(String input) throws Exception {
        String text = getRequired(input, Message.DATE_EMPTY);
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
        Date date = null;

        // strict: 31-02-2003 is refused instead of becoming 03-03-2003
        dateFormat.setLenient(false);

        // parse() throws for text that is not a date at all
        try {
            date = dateFormat.parse(text);
        } catch (ParseException e) {
            // "abc", "31-02-2003"
            throw new Exception(Message.DATE_INVALID);
        }

        // the round trip rejects "1-2-2015" and trailing rubbish
        if (!dateFormat.format(date).equals(text)) {
            throw new Exception(Message.DATE_INVALID);
        }

        return date;
    }

    // The brief: plan times go from 8.0 to 17.5 in half hours (8.0, 8.5, 9.0 ...
    public static double getPlanTime(String input, String label) throws Exception {
        String text = getRequired(input, String.format(Message.PLAN_EMPTY, label));
        double time = 0;
        double steps = 0;

        // Double (wrapper class) decides whether the text is a number; the
        // brief asks to catch NullPointerException too: parseDouble(null)
        // throws it (unlike Integer.parseInt(null))
        try {
            time = Double.parseDouble(text);
        } catch (NumberFormatException | NullPointerException e) {
            // letters, "9,5"
            throw new Exception(String.format(Message.PLAN_NOT_NUMBER, label));
        }

        // written as !(inside) so that NaN is refused too
        if (!((time >= Constants.PLAN_MIN) && (time <= Constants.PLAN_MAX))) {
            throw new Exception(String.format(Message.PLAN_OUT_OF_RANGE, label));
        }

        // the time counted in half hours: a whole number for 8.0, 8.5, 9.0 ...
        steps = time * Constants.STEPS_PER_HOUR;

        // 9.7 * 2 = 19.4 is not a whole number of half hours
        if (Math.abs(steps - Math.rint(steps)) > Constants.EPSILON) {
            throw new Exception(String.format(Message.PLAN_NOT_HALF, label));
        }

        return time;
    }

    // The brief: "Plan From must be less than Plan To".
    public static void checkPlanOrder(double planFrom, double planTo) throws Exception {
        // equal times are refused too: "less than"
        if (planFrom >= planTo) {
            throw new Exception(Message.PLAN_ORDER);
        }
    }

    // Converts the ID typed on the delete screen.
    public static int getId(String input) throws Exception {
        String text = getRequired(input, Message.ID_EMPTY);

        // Integer (wrapper class) decides whether the text is a number
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            // letters or a decimal
            throw new Exception(Message.ID_NOT_NUMBER);
        }
    }
}
