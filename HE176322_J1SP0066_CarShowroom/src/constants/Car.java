package constants;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * The brief's Enum Car: the three cars of the showroom with their sale information
 * (colours, prices, days).
 *
 * @author HE176322
 */
public enum Car {

    // AUDI: WHITE/YELLOW/ORANGE, 5500/3000/4500 $, FRIDAY SUNDAY MONDAY.
    AUDI(new Color[]{Color.WHITE, Color.YELLOW, Color.ORANGE},
            new Double[]{5500.0, 3000.0, 4500.0},
            new Day[]{Day.FRIDAY, Day.SUNDAY, Day.MONDAY}),
    // MERCEDES: GREEN/BLUE/PURPLE, 5000/6000/8500 $, TUE SAT WED.
    MERCEDES(new Color[]{Color.GREEN, Color.BLUE, Color.PURPLE},
            new Double[]{5000.0, 6000.0, 8500.0},
            new Day[]{Day.TUESDAY, Day.SATURDAY, Day.WEDNESDAY}),
    // BMW: PINK/RED/BROWN, 2500/3000/3500 $, MONDAY SUNDAY THURSDAY.
    BMW(new Color[]{Color.PINK, Color.RED, Color.BROWN},
            new Double[]{2500.0, 3000.0, 3500.0},
            new Day[]{Day.MONDAY, Day.SUNDAY, Day.THURSDAY});

    // Colours this car is painted in.
    private final ArrayList<Color> colors;
    // Price of each colour, same position as in colors.
    private final ArrayList<Double> prices;
    // Days of the week this car is sold on.
    private final ArrayList<Day> daySells;

    // Creates one car constant (enum constructors are always private).
    Car(Color[] colors, Double[] prices, Day[] daySells) {
        this.colors = new ArrayList<>(Arrays.asList(colors));
        this.prices = new ArrayList<>(Arrays.asList(prices));
        this.daySells = new ArrayList<>(Arrays.asList(daySells));
    }

    // The brief's getColors: the list of colours of this car.
    public ArrayList<Color> getColors() {
        return new ArrayList<>(colors);
    }

    // The brief's getPrices: the list of prices of this car (a copy).
    public ArrayList<Double> getPrices() {
        return new ArrayList<>(prices);
    }

    // The brief's getDaySells: the days this car is sold on (a copy).
    public ArrayList<Day> getDaySells() {
        return new ArrayList<>(daySells);
    }

    // The brief's lookup "Car getCar(String car)": null when the showroom has no such car
    //.
    public static Car getCar(String car) {
        // nothing typed: no car
        if (car == null) {
            return null;
        }
        // compare the text with the name of every car
        for (Car candidate : values()) {
            // found the car the user meant
            if (candidate.name().equalsIgnoreCase(car.trim())) {
                return candidate;
            }
        }
        return null;
    }
}
