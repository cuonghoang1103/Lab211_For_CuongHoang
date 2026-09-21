package service;

import constants.Constants;
import constants.Message;
import dto.CountryRequestDTO;
import dto.CountryResponseDTO;
import java.util.ArrayList;
import model.Country;
import model.EastAsiaCountries;
import repository.ManageEastAsiaCountries;

/**
 * SERVICE and Strategy CONTEXT: sits between the controller and the repository
 * (Guide.xlsx: "Controller Services Repository Model"). Every option gets back one
 * CountryResponseDTO - the whole answer the view prints.
 *
 * @author HE176322
 */
public class CountryService {

    // Where the countries are stored.
    private ManageEastAsiaCountries manageCountries;

    // The order to sort with, chosen by whoever creates this service.
    private ISortStrategy sortStrategy;

    // Creates the service with its repository and its sort order.
    public CountryService(ManageEastAsiaCountries manageCountries, ISortStrategy sortStrategy) {
        this.manageCountries = manageCountries;
        this.sortStrategy = sortStrategy;
    }

    // Refuses option 1 BEFORE the four questions when 11 countries are already stored
    // (asking four answers and then throwing them away would be rude).
    public void checkFull() throws Exception {
        // every place is taken
        if (manageCountries.isFull()) {
            throw new Exception(String.format(Message.LIST_FULL, Constants.MAX_COUNTRIES));
        }
    }

    // Function 1 (addCountryInformation): builds the model from the request, stores it and
    // answers "Successful".
    public CountryResponseDTO addCountryInformation(CountryRequestDTO requestDTO)
            throws Exception {
        EastAsiaCountries country = new EastAsiaCountries(requestDTO.getCountryCode(),
                requestDTO.getCountryName(), requestDTO.getTotalArea(),
                requestDTO.getCountryTerrain());
        CountryResponseDTO responseDTO = new CountryResponseDTO();

        // the brief: "Total area must be greater than 0" - the rule is kept
        // here too, so no caller can store a bad area
        if (country.getTotalArea() <= Constants.MIN_AREA) {
            throw new Exception(Message.INVALID_AREA);
        }

        // Service -> Repository -> Model: store it, then answer with one line
        manageCountries.addCountryInformation(country);
        responseDTO.setMessage(Message.SUCCESSFUL);
        return responseDTO;
    }

    // Function 2 (getRecentlyEnteredInformation): a table of one row - the country entered
    // last.
    public CountryResponseDTO getRecentlyEnteredInformation() throws Exception {
        EastAsiaCountries country = manageCountries.getRecentlyEnteredInformation();

        // the same table as options 3 and 4, with a single row
        return toResponse(new EastAsiaCountries[]{country});
    }

    // Function 3 (searchInformationByName): the countries whose name contains the text.
    public CountryResponseDTO searchInformationByName(CountryRequestDTO requestDTO)
            throws Exception {
        return toResponse(manageCountries.searchInformationByName(requestDTO.getSearchName()));
    }

    // Function 4 (sortInformationByAscendingOrder): every country, sorted by the strategy
    // (name A to Z).
    public CountryResponseDTO sortInformationByAscendingOrder() throws Exception {
        EastAsiaCountries[] countryArray = manageCountries.getAllCountries();

        // sort the copy, then turn it into rows
        sortStrategy.sort(countryArray);
        return toResponse(countryArray);
    }

    // Copies the countries into the answer the view may see: one row per country, same
    // order.
    private CountryResponseDTO toResponse(EastAsiaCountries[] countryArray) {
        CountryResponseDTO responseDTO = new CountryResponseDTO();
        ArrayList<String> rowList = new ArrayList<>();

        // the loop variable has the parent type Country, yet display() runs the
        // EastAsiaCountries version (polymorphism)
        for (Country country : countryArray) {
            rowList.add(country.display());
        }

        responseDTO.setRowList(rowList);
        return responseDTO;
    }
}
