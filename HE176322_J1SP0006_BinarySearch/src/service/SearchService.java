package service;

import dto.SearchRequestDTO;
import dto.SearchResponseDTO;
import java.util.Arrays;
import java.util.Random;
import model.NumberArray;

/**
 * SERVICE and Strategy CONTEXT: generates the random array, sorts it, and searches it
 * with whatever SearchStrategy it was given.
 *
 * @author HE176322
 */
public class SearchService {

    // The algorithm to search with, chosen by whoever creates this service.
    private SearchStrategy searchStrategy;
    // Source of random numbers; one object reused for every element.
    private Random random = new Random();

    // Creates the service with the algorithm it must use.
    public SearchService(SearchStrategy searchStrategy) {
        this.searchStrategy = searchStrategy;
    }

    // The whole job of the program, in the brief's order: generate the array, sort it,
    // then search it, and return what the view shows.
    public SearchResponseDTO searchRandomArray(SearchRequestDTO requestDTO) {
        NumberArray array = generateArray(requestDTO.getSize());
        sortArray(array);
        int index = searchStrategy.search(array, requestDTO.getSearchValue());
        SearchResponseDTO response = new SearchResponseDTO();
        response.setSortedArray(array.toString());
        response.setSearchValue(requestDTO.getSearchValue());
        response.setIndex(index);
        return response;
    }

    // Generates an array of random integers "in number range input": each element is in
    // [0, size), like the brief's screen (size 10 gives numbers from 0 to 9).
    private NumberArray generateArray(int size) {
        int[] values = new int[size];
        // fill every position with a random number from 0 to size - 1
        for (int i = 0; i < size; i++) {
            values[i] = random.nextInt(size);
        }
        return new NumberArray(values);
    }

    // Sorts the array ascending - the precondition of binary search (the brief's step
    // "Sort array").
    private void sortArray(NumberArray array) {
        Arrays.sort(array.getValues());
    }
}
