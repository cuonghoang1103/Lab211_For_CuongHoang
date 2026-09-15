package service;

import constants.Constants;
import dto.SearchRequestDTO;
import dto.SearchResponseDTO;
import java.util.Random;
import model.NumberArray;

/**
 * Service: generates the random array and searches it with linear search.
 *
 * @author HE176322
 */
public class SearchService {

    // source of random numbers, reused for every element
    private Random random = new Random();

    // generate the array, search it, return the result for the view
    public SearchResponseDTO searchRandomArray(SearchRequestDTO requestDTO) {
        NumberArray array = generateArray(requestDTO.getSize());
        int index = linearSearch(array, requestDTO.getSearchValue());
        SearchResponseDTO response = new SearchResponseDTO();
        response.setArray(array.toString());
        response.setSearchValue(requestDTO.getSearchValue());
        response.setIndex(index);
        return response;
    }

    // linear search: index of the first match, or NOT_FOUND
    private int linearSearch(NumberArray array, int value) {
        // look at every element in order, left to right
        for (int i = 0; i < array.getSize(); i++) {
            // the first match ends the search
            if (array.getValue(i) == value) {
                return i;
            }
        }
        return Constants.NOT_FOUND;
    }

    // random numbers in [0, size), like the brief's example
    private NumberArray generateArray(int size) {
        int[] values = new int[size];
        // fill every position with a random number
        for (int i = 0; i < size; i++) {
            values[i] = random.nextInt(size);
        }
        return new NumberArray(values);
    }
}
