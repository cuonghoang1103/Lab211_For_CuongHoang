package service;

import dto.SortRequestDTO;
import dto.SortResponseDTO;
import java.util.Random;
import model.NumberArray;
import repository.NumberRepository;

/**
 * SERVICE: the business of the program - generates the random numbers, keeps them in the
 * repository and sorts them with selection sort. Called only by the controller; no print,
 * no keyboard.
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
    // it as text before and after the selection sort.
    public SortResponseDTO sortRandomArray(SortRequestDTO requestDTO) {
        SortResponseDTO responseDTO = new SortResponseDTO();
        NumberArray numberArray = null;

        // keep the random numbers in the repository, then work on the array it holds
        numberRepository.saveNumberArray(generateValueArray(requestDTO.getSize()));
        numberArray = numberRepository.getNumberArray();

        // take the text BEFORE sorting, sort, then take the text again
        responseDTO.setUnsortedArray(numberArray.toString());
        sortBySelection(numberArray);
        responseDTO.setSortedArray(numberArray.toString());
        return responseDTO;
    }

    // The brief's selection sort, ascending and in place: find the minimum of the unsorted
    // part and swap it with the first element of that part, until one number is left.
    private void sortBySelection(NumberArray numberArray) {
        int size = numberArray.getSize();

        // i is the first position of the unsorted part
        for (int i = 0; i < (size - 1); i++) {
            int minIndex = i;

            // find the minimum of the unsorted part
            for (int j = i + 1; j < size; j++) {
                // strictly smaller: on a tie keep the first one found
                if (numberArray.getValue(j) < numberArray.getValue(minIndex)) {
                    minIndex = j;
                }
            }

            // bring the minimum to the front of the unsorted part
            if (minIndex != i) {
                numberArray.swap(i, minIndex);
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
