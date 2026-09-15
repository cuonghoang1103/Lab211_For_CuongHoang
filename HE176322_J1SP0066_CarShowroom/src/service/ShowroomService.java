package service;

import constants.Car;
import constants.Color;
import constants.Message;
import dto.CarRequestDTO;
import exceptions.ExceptionCar;
import model.CarOrder;
import utils.Validation;

/**
 * SERVICE and Strategy CONTEXT: the one business rule of the program - may this customer
 * buy this car today?
 *
 * @author HE176322
 */
public class ShowroomService {

    // The price rule, chosen by whoever creates this service.
    private PriceStrategy priceStrategy;

    // Creates the service with the price rule it must use.
    public ShowroomService(PriceStrategy priceStrategy) {
        this.priceStrategy = priceStrategy;
    }

    // The brief's checkCar: validates the customer's request and returns the Car when it
    // matches, or throws ExceptionCar with the reason.
    public Car checkCar(CarRequestDTO requestDTO) throws ExceptionCar {
        Car car = requestDTO.getCar();
        Color color = requestDTO.getColor();
        // the name typed is not a car of the showroom
        if (car == null) {
            throw new ExceptionCar(Message.CAR_BREAK);
        }
        // not a colour at all, or a real colour this car is never painted in
        if (color == null || !isPaintable(car, color)) {
            throw new ExceptionCar(Message.COLOR_NOT_EXIST);
        }
        double price = Validation.checkPrice(requestDTO.getPrice());
        CarOrder order = new CarOrder(car, color, requestDTO.getDay(), price);
        // ">=" not "==": the brief adds options on top of the car's price
        if (order.getPrice() < priceStrategy.getAskingPrice(order)) {
            throw new ExceptionCar(Message.PRICE_NOT_ENOUGH);
        }
        // not a day at all, or a day this car is not sold on
        if (order.getDay() == null || !car.getDaySells().contains(order.getDay())) {
            throw new ExceptionCar(Message.CANT_SELL_TODAY);
        }
        return car;
    }

    // Tells whether the car can be delivered in this colour: every car can be left
    // unpainted, otherwise the colour must be one of the car's.
    private boolean isPaintable(Car car, Color color) {
        return color == Color.NO_COLOR || car.getColors().contains(color);
    }
}
