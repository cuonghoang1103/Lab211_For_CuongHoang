package service;

import constants.Car;
import constants.Color;
import constants.Day;
import constants.Message;
import dto.CarRequestDTO;
import exceptions.CarException;
import model.CarOrder;
import repository.CarOrderRepository;
import utils.Validation;

/**
 * SERVICE and Strategy CONTEXT: the one business rule of the program - may this customer
 * buy this car today? It records every sale in the repository.
 *
 * @author HE176322
 */
public class ShowroomService {

    // The price rule, chosen by whoever creates this service.
    private IPriceStrategy priceStrategy;

    // The record of the orders the showroom has sold.
    private CarOrderRepository carOrderRepository;

    // Creates the service with the price rule it must use and an empty record of sales.
    public ShowroomService(IPriceStrategy priceStrategy) {
        this.priceStrategy = priceStrategy;
        carOrderRepository = new CarOrderRepository();
    }

    // The brief's checkCar: validates the customer's request and returns the Car when it
    // matches, or throws CarException (the brief's ExceptionCar) with the reason.
    public Car checkCar(CarRequestDTO requestDTO) throws CarException {
        Car car = requestDTO.getCar();
        Color color = requestDTO.getColor();
        Day day = requestDTO.getDay();
        double price = 0;
        CarOrder order = null;

        // the name typed is not a car of the showroom
        if (car == null) {
            throw new CarException(Message.CAR_BREAK);
        }

        // not a colour at all, or a real colour this car is never painted in
        if ((color == null) || !isPaintable(car, color)) {
            throw new CarException(Message.COLOR_NOT_EXIST);
        }

        // the price part of checkCar: a number greater than zero, else CarException
        price = Validation.checkPrice(requestDTO.getPrice());
        order = new CarOrder(car, color, day, price);

        // ">=" not "==": the brief adds options on top of the car's price
        if (order.getPrice() < priceStrategy.getAskingPrice(order)) {
            throw new CarException(Message.PRICE_NOT_ENOUGH);
        }

        // not a day at all, or a day this car is not sold on
        if ((day == null) || !car.getDaySells().contains(day)) {
            throw new CarException(Message.CANT_SELL_TODAY);
        }

        // every check passed: the showroom records the sale
        carOrderRepository.addOrder(order);
        return car;
    }

    // Tells whether the car can be delivered in this colour: every car can be left
    // unpainted, otherwise the colour must be one of the car's.
    private boolean isPaintable(Car car, Color color) {
        return (color == Color.NO_COLOR) || car.getColors().contains(color);
    }
}
