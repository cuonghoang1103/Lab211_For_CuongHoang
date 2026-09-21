package service;

import dto.SortRequestDTO;
import dto.SortResponseDTO;
import java.util.Random;
import model.NumberArray;
import repository.NumberRepository;

/**
 * SERVICE: the business of the program - generates the random numbers, keeps them in the
 * repository and sorts them with bubble sort. Called only by the controller; no print, no
 * keyboard.
 *
 * @author HE176322
 */
public class SortService {

    // Keeps the array the service works on (Service -> Repository -> Model).
    private NumberRepository numberRepository;

    // Source of random numbers; one object reused for every element.
    private Random random;

    // Creates the service with an empty repository.
    public SortService() {
        numberRepository = new NumberRepository();
        random = new Random();
    }

    // The brief's Function 2: generates the array, keeps it in the repository, and returns
    // it as text before and after the bubble sort.
    public SortResponseDTO sortRandomArray(SortRequestDTO requestDTO) {
        SortResponseDTO responseDTO = new SortResponseDTO();
        NumberArray numberArray = null;

        // keep the random numbers in the repository, then work on the array it holds
        numberRepository.saveNumberArray(generateValueArray(requestDTO.getSize()));
        numberArray = numberRepository.getNumberArray();

        // take the text BEFORE sorting, sort, then take the text again
        responseDTO.setUnsortedArray(numberArray.toString());
        sortByBubble(numberArray);
        responseDTO.setSortedArray(numberArray.toString());
        return responseDTO;
    }

    // The brief's bubble sort, ascending and in place: compare each pair of neighbours and
    // swap them when they are in the wrong order; a pass without any swap ends the sort.
    private void sortByBubble(NumberArray numberArray) {
        int size = numberArray.getSize();

        // pass i moves the biggest remaining number to position size - 1 - i
        for (int i = 0; i < (size - 1); i++) {
            boolean swapped = false;

            // the last i numbers are already in place, so stop before them
            for (int j = 0; j < (size - 1 - i); j++) {
                // wrong order: the bigger number moves one step right
                if (numberArray.getValue(j) > numberArray.getValue(j + 1)) {
                    numberArray.swap(j, j + 1);
                    swapped = true;
                }
            }

            // no swap in a whole pass: the array is already sorted
            if (!swapped) {
                return;
            }
        }
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
