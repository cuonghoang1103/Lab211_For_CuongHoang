package view;

import constants.Constants;
import constants.Message;
import dto.CountryResponseDTO;

/**
 * VIEW: prints the countries table - the place where what the model's display() returned
 * finally reaches the screen.
 *
 * @author HE176322
 */
public class CountryView {

    // The rows to display, handed over by the controller.
    private CountryResponseDTO[] countries;

    // Receives the rows the next display() call will print.
    public void setCountries(CountryResponseDTO[] countries) {
        this.countries = countries;
    }

    // Prints the header, then one line per country.
    public void display() {
        System.out.println(String.format(Constants.HEADER_FORMAT, Message.LABEL_ID,
                Message.LABEL_NAME, Message.LABEL_AREA, Message.LABEL_TERRAIN));
        // one line per country, text built by the model's display()
        for (CountryResponseDTO country : countries) {
            System.out.println(country.getInformation());
        }
    }

    // Prints a one-line result such as "Successful".
    public void showMessage(String message) {
        System.out.println(message);
    }
}
