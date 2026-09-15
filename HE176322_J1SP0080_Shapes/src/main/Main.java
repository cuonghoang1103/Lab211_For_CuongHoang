package main;

import controller.ShapeController;

/**
 * MAIN: the work flow - the brief's program "requires no input; it constructs a fixed set
 * of sample shapes and prints a report", so main only calls the controller once.
 *
 * @author HE176322
 */
public class Main {

    // Starts the program: one call prints the whole report.
    public static void main(String[] args) {
        ShapeController controller = new ShapeController();
        controller.displayShapes();
    }
}
