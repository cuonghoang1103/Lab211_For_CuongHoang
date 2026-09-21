package service;

import constants.Constants;
import dto.SearchRequestDTO;
import dto.SearchResponseDTO;
import java.util.Random;
import model.NumberArray;
import repository.NumberRepository;

/**
 * SERVICE: the business of the program - generates the random numbers, keeps them in the
 * repository and searches them with linear search. Called only by the controller; no
 * print, no keyboard.
 *
 * @author HE176322
 */
public class SearchService {

    // Keeps the array the service works on (Service -> Repository -> Model).
    private NumberRepository numberRepository;

    // Source of random numbers; one object reused for every element.
    private Random random;

    // Creates the service with an empty repository.
    public SearchService() {
        numberRepository = new NumberRepository();
        random = new Random();
    }

    // The brief's Function 2: generates the array, keeps it in the repository, and returns
    // it as text together with the value searched and the index where it was found.
    public SearchResponseDTO searchRandomArray(SearchRequestDTO requestDTO) {
        SearchResponseDTO responseDTO = new SearchResponseDTO();
        NumberArray numberArray = null;

        // keep the random numbers in the repository, then work on the array it holds
        numberRepository.saveNumberArray(generateValueArray(requestDTO.getSize()));
        numberArray = numberRepository.getNumberArray();

        // the array as generated (never sorted), the value, and its first index
        responseDTO.setNumberArray(numberArray.toString());
        responseDTO.setSearchValue(requestDTO.getSearchValue());
        responseDTO.setIndex(searchByLinear(numberArray, requestDTO.getSearchValue()));
        return responseDTO;
    }

    // The brief's linear search: checks every element, one at a time and in sequence;
    // returns the index of the first match, or Constants.NOT_FOUND.
    private int searchByLinear(NumberArray numberArray, int searchValue) {
        // look at every element in order, left to right
        for (int i = 0; i < numberArray.getSize(); i++) {
            // the first match ends the search
            if (numberArray.getValue(i) == searchValue) {
                return i;
            }
        }

        // every element was checked and none matched
        return Constants.NOT_FOUND;
    }

    // Generates "random integer in number range input": each number is in [0, size), like
    // the brief's screen (size 10 gives numbers from 0 to 9).
    private int[] generateValueArray(int size) {
        int[] valueArray = new int[size];

        // fill every position with a random number from 0 to size - 1
        for (int i = 0; i < size; i++) {
            valueArray[i] = random.nextInt(size);
        }

        return valueArray;
    }
}
