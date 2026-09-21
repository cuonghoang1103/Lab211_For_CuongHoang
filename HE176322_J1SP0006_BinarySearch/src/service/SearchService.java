package service;

import dto.SearchRequestDTO;
import dto.SearchResponseDTO;
import java.util.Arrays;
import java.util.Random;
import model.NumberArray;
import repository.NumberRepository;

/**
 * SERVICE and Strategy CONTEXT: generates the random numbers, keeps them in the repository,
 * sorts them and searches them with whatever ISearchStrategy it was given. Called only by
 * the controller; no print, no keyboard.
 *
 * @author HE176322
 */
public class SearchService {

    // The algorithm to search with, chosen by whoever creates this service.
    private ISearchStrategy searchStrategy;

    // Keeps the array the service works on (Service -> Repository -> Model).
    private NumberRepository numberRepository;

    // Source of random numbers; one object reused for every element.
    private Random random;

    // Creates the service with the algorithm it must use and an empty repository.
    public SearchService(ISearchStrategy searchStrategy) {
        this.searchStrategy = searchStrategy;
        numberRepository = new NumberRepository();
        random = new Random();
    }

    // The brief's Function 2, in the brief's order: generates the array, keeps it in the
    // repository, sorts it, then searches it, and returns what the view shows.
    public SearchResponseDTO searchRandomArray(SearchRequestDTO requestDTO) {
        SearchResponseDTO responseDTO = new SearchResponseDTO();
        NumberArray numberArray = null;

        // keep the random numbers in the repository, then work on the array it holds
        numberRepository.saveNumberArray(generateValueArray(requestDTO.getSize()));
        numberArray = numberRepository.getNumberArray();

        // the brief's step "Sort array": binary search only works on a sorted array
        sortArray(numberArray);

        // the sorted array, the value, and the index the strategy finds
        responseDTO.setSortedArray(numberArray.toString());
        responseDTO.setSearchValue(requestDTO.getSearchValue());
        responseDTO.setIndex(searchStrategy.search(numberArray, requestDTO.getSearchValue()));
        return responseDTO;
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

    // Sorts the array ascending, in place - the precondition of binary search (the brief's
    // step "Sort array").
    private void sortArray(NumberArray numberArray) {
        Arrays.sort(numberArray.getValueArray());
    }
}
