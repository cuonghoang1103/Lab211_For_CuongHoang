package service;

import dto.SortRequestDTO;
import dto.SortResponseDTO;
import java.util.Random;
import model.NumberArray;
import repository.NumberRepository;

/**
 * SERVICE and Strategy CONTEXT: generates the random numbers, keeps them in the repository
 * and sorts them with whatever ISortStrategy it was given. Called only by the controller;
 * no print, no keyboard.
 *
 * @author HE176322
 */
public class SortService {

    // The algorithm to sort with, chosen by whoever creates this service.
    private ISortStrategy sortStrategy;

    // Keeps the array the service works on (Service -> Repository -> Model).
    private NumberRepository numberRepository;

    // Source of random numbers; one object reused for every element.
    private Random random;

    // Creates the service with the algorithm it must use and an empty repository.
    public SortService(ISortStrategy sortStrategy) {
        this.sortStrategy = sortStrategy;
        numberRepository = new NumberRepository();
        random = new Random();
    }

    // The brief's Function 2: generates the array, keeps it in the repository, and returns
    // it as text before and after the sort.
    public SortResponseDTO sortRandomArray(SortRequestDTO requestDTO) {
        SortResponseDTO responseDTO = new SortResponseDTO();
        NumberArray numberArray = null;

        // keep the random numbers in the repository, then work on the array it holds
        numberRepository.saveNumberArray(generateValueArray(requestDTO.getSize()));
        numberArray = numberRepository.getNumberArray();

        // take the text BEFORE sorting, sort with the strategy, then take the text again
        responseDTO.setUnsortedArray(numberArray.toString());
        sortStrategy.sort(numberArray);
        responseDTO.setSortedArray(numberArray.toString());
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
}
