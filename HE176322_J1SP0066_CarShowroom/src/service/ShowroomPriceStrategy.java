package service;

import constants.Car;
import constants.Color;
import constants.Constants;
import java.util.ArrayList;
import java.util.Collections;
import model.CarOrder;

/**
 * CONCRETE STRATEGY: the brief's price rule.
 *
 * @author HE176322
 */
public class ShowroomPriceStrategy implements IPriceStrategy {

    // Returns the lowest price accepted for the order's car and colour.
    @Override
    public double getAskingPrice(CarOrder order) {
        Car car = order.getCar();
        ArrayList<Double> priceList = car.getPrices();

        // unpainted: $100 off the cheapest paint job
        if (order.getColor() == Color.NO_COLOR) {
            return Collections.min(priceList) - Constants.NO_COLOR_DISCOUNT;
        }

        // painted: the price at the same position as the colour
        return priceList.get(car.getColors().indexOf(order.getColor()));
    }
}
