package service;

import constants.Constants;
import constants.Message;
import dto.CountryRequestDTO;
import dto.CountryResponseDTO;
import model.Country;
import model.EastAsiaCountries;
import repository.ManageEastAsiaCountries;

/**
 * SERVICE and Strategy CONTEXT: sits between the controller and the repository
 * (Guide.xlsx: "Controller Services Repository Model").
 *
 * @author HE176322
 */
public class CountryService {

    // Where the countries are stored.
    private ManageEastAsiaCountries manageCountries;
    // The order to sort with, chosen by whoever creates this service.
    private SortStrategy sortStrategy;

    // Creates the service with its repository and its sort order.
    public CountryService(ManageEastAsiaCountries manageCountries, SortStrategy sortStrategy) {
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

    // Function 1 (addCountryInformation): builds the model from the request and stores
    // it.
    public void addCountryInformation(CountryRequestDTO requestDTO) throws Exception {
        // the brief: "Total area must be greater than 0" - the rule is kept
        // here too, so no caller can store a bad area
        if (requestDTO.getTotalArea() <= Constants.MIN_AREA) {
            throw new Exception(Message.INVALID_AREA);
        }
        EastAsiaCountries country = new EastAsiaCountries(requestDTO.getCountryCode(),
                requestDTO.getCountryName(), requestDTO.getTotalArea(),
                requestDTO.getCountryTerrain());
        manageCountries.addCountryInformation(country);
    }

    // Function 2 (getRecentlyEnteredInformation): the country entered last.
    public CountryResponseDTO getRecentlyEnteredInformation() throws Exception {
        return toResponse(manageCountries.getRecentlyEnteredInformation());
    }

    // Function 3 (searchInformationByName): the countries whose name contains the text.
    public CountryResponseDTO[] searchInformationByName(CountryRequestDTO requestDTO)
            throws Exception {
        return toResponses(manageCountries.searchInformationByName(
                requestDTO.getSearchName()));
    }

    // Function 4 (sortInformationByAscendingOrder): every country, sorted by the strategy
    // (name A to Z).
    public CountryResponseDTO[] sortInformationByAscendingOrder() throws Exception {
        EastAsiaCountries[] countries = manageCountries.getAllCountries();
        sortStrategy.sort(countries);
        return toResponses(countries);
    }

    // Copies one country into the DTO the view may see.
    private CountryResponseDTO toResponse(Country country) {
        return new CountryResponseDTO(country.display());
    }

    // Copies an array of countries into rows, keeping the order.
    private CountryResponseDTO[] toResponses(EastAsiaCountries[] countries) {
        CountryResponseDTO[] rows = new CountryResponseDTO[countries.length];
        // one row per country, same position
        for (int i = 0; i < countries.length; i++) {
            rows[i] = toResponse(countries[i]);
        }
        return rows;
    }
}
