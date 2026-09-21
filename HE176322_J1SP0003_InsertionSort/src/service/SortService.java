package service;

import dto.SortRequestDTO;
import dto.SortResponseDTO;
import java.util.Random;
import model.NumberArray;
import repository.NumberRepository;

/**
 * SERVICE: the business of the program - generates the random numbers, keeps them in the
 * repository and sorts them with insertion sort. Called only by the controller; no print,
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
    // it as text before and after the insertion sort.
    public SortResponseDTO sortRandomArray(SortRequestDTO requestDTO) {
        SortResponseDTO responseDTO = new SortResponseDTO();
        NumberArray numberArray = null;

        // keep the random numbers in the repository, then work on the array it holds
        numberRepository.saveNumberArray(generateValueArray(requestDTO.getSize()));
        numberArray = numberRepository.getNumberArray();

        // take the text BEFORE sorting, sort, then take the text again
        responseDTO.setUnsortedArray(numberArray.toString());
        sortByInsertion(numberArray);
        responseDTO.setSortedArray(numberArray.toString());
        return responseDTO;
    }

    // The brief's insertion sort, "shifting instead of swapping", ascending and in place:
    // takes the first number of the unsorted part and inserts it into the sorted part.
    private void sortByInsertion(NumberArray numberArray) {
        int size = numberArray.getSize();

        // position 0 alone is already sorted, so start at 1
        for (int i = 1; i < size; i++) {
            int key = numberArray.getValue(i);
            int j = i - 1;

            // shift the bigger numbers of the sorted part one step right; j >= 0 is checked
            // first, so getValue(-1) is never read
            while ((j >= 0) && (numberArray.getValue(j) > key)) {
                numberArray.setValue(j + 1, numberArray.getValue(j));
                j--;
            }

            // write the key once, into the gap just right of the first number <= key
            numberArray.setValue(j + 1, key);
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
