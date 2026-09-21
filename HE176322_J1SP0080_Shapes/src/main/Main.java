package main;

import controller.ShapeController;

/**
 * MAIN: the work flow - the brief's program "requires no input; it constructs a fixed set
 * of sample shapes and prints a report", so main only calls the controller once.
 *
 * @author HE176322
 */
public final class Main {

    // Private constructor: Main only has static methods (checklist 3.4).
    private Main() {
    }

    // Starts the program: one call builds the shapes and prints the whole report.
    public static void main(String[] args) {
        ShapeController controller = new ShapeController();

        // the only flow: the controller is called once, the report is printed once
        controller.displayShapes();
    }
}
