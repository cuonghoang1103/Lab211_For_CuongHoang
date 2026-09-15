package controller;

import constants.Message;
import dto.CountryRequestDTO;
import dto.CountryResponseDTO;
import repository.ManageEastAsiaCountries;
import service.CountryService;
import service.NameAscendingSortStrategy;
import view.CountryView;

/**
 * CONTROLLER: receives a request from main, asks the service, and hands the result to the
 * view.
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

    // Pre-check of option 1: refuses before the questions when the list is full (like
    // checkExistDoctor in the Guide sample P0055).
    public void checkFull() throws Exception {
        countryService.checkFull();
    }

    // Option 1: stores the country, then "Successful".
    public void addCountryInformation(CountryRequestDTO requestDTO) throws Exception {
        countryService.addCountryInformation(requestDTO);
        countryView.showMessage(Message.SUCCESSFUL);
    }

    // Option 2: shows the country entered last.
    public void getRecentlyEnteredInformation() throws Exception {
        CountryResponseDTO row = countryService.getRecentlyEnteredInformation();
        countryView.setCountries(new CountryResponseDTO[]{row});
        countryView.display();
    }

    // Option 3: shows the countries whose name contains the text.
    public void searchInformationByName(CountryRequestDTO requestDTO) throws Exception {
        countryView.setCountries(countryService.searchInformationByName(requestDTO));
        countryView.display();
    }

    // Option 4: shows every country sorted by name.
    public void sortInformationByAscendingOrder() throws Exception {
        countryView.setCountries(countryService.sortInformationByAscendingOrder());
        countryView.display();
    }
}
