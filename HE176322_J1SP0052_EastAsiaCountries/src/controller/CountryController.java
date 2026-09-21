package controller;

import dto.CountryRequestDTO;
import dto.CountryResponseDTO;
import repository.ManageEastAsiaCountries;
import service.CountryService;
import service.NameAscendingSortStrategy;
import view.CountryView;

/**
 * CONTROLLER: receives a request from main, asks the service, and hands the answer to the
 * view - one render per menu option. No Scanner, no print, no model.
 *
 * @author HE176322
 */
public class CountryController {

    // Business logic, between this controller and the repository.
    private CountryService countryService;

    // Where the results are printed.
    private CountryView countryView;

    // Creates the controller: the service gets the repository and the name-ascending
    // sort.
    public CountryController() {
        countryService = new CountryService(new ManageEastAsiaCountries(),
                new NameAscendingSortStrategy());
        countryView = new CountryView();
    }

    // Check only, before the questions of option 1: throws when the list is full, renders
    // nothing (like checkExistDoctor in the Guide sample P0055).
    public void checkFull() throws Exception {
        countryService.checkFull();
    }

    // Option 1: stores the country, then the view prints "Successful" - once.
    public void addCountryInformation(CountryRequestDTO requestDTO) throws Exception {
        CountryResponseDTO responseDTO = countryService.addCountryInformation(requestDTO);

        // hand the answer to the view, then render it - once for the whole flow
        countryView.setResponseDTO(responseDTO);
        countryView.display();
    }

    // Option 2: the view prints the country entered last - once.
    public void getRecentlyEnteredInformation() throws Exception {
        CountryResponseDTO responseDTO = countryService.getRecentlyEnteredInformation();

        // hand the answer to the view, then render it - once for the whole flow
        countryView.setResponseDTO(responseDTO);
        countryView.display();
    }

    // Option 3: the view prints the countries whose name contains the text - once.
    public void searchInformationByName(CountryRequestDTO requestDTO) throws Exception {
        CountryResponseDTO responseDTO = countryService.searchInformationByName(requestDTO);

        // hand the answer to the view, then render it - once for the whole flow
        countryView.setResponseDTO(responseDTO);
        countryView.display();
    }

    // Option 4: the view prints every country sorted by name - once.
    public void sortInformationByAscendingOrder() throws Exception {
        CountryResponseDTO responseDTO = countryService.sortInformationByAscendingOrder();

        // hand the answer to the view, then render it - once for the whole flow
        countryView.setResponseDTO(responseDTO);
        countryView.display();
    }
}
