package service;

import dto.SortRequestDTO;
import dto.SortResponseDTO;
import java.util.Random;
import model.NumberArray;

/**
 * SERVICE and Strategy CONTEXT: generates the random array and sorts it with whatever
 * SortStrategy it was given.
 *
 * @author HE176322
 */
public class SortService {

    // The algorithm to sort with, chosen by whoever creates this service.
    private SortStrategy sortStrategy;
    // Source of random numbers; one object reused for every element.
    private Random random = new Random();

    // Creates the service with the algorithm it must use.
    public SortService(SortStrategy sortStrategy) {
        this.sortStrategy = sortStrategy;
    }

    // The whole job of the program: generate the array, remember it as text, sort it, and
    // return both texts for the view.
    public SortResponseDTO sortRandomArray(SortRequestDTO requestDTO) {
        NumberArray array = generateArray(requestDTO.getSize());
        SortResponseDTO response = new SortResponseDTO();
        response.setUnsorted(array.toString());
        sortStrategy.sort(array);
        response.setSorted(array.toString());
        return response;
    }

    // Generates an array of random integers "in number range input": each element is in
    // [0, size), exactly like the brief's example (size 10 gives numbers from 0 to 9).
    private NumberArray generateArray(int size) {
        int[] values = new int[size];
        // fill every position with a random number from 0 to size - 1
        for (int i = 0; i < size; i++) {
            values[i] = random.nextInt(size);
        }
        return new NumberArray(values);
    }
}
