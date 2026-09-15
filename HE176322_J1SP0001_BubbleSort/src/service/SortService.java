package service;

import dto.SortRequestDTO;
import dto.SortResponseDTO;
import java.util.Random;
import model.NumberArray;

/**
 * Service: generates the random array and sorts it with bubble sort.
 *
 * @author HE176322
 */
public class SortService {

    // source of random numbers, reused for every element
    private Random random = new Random();

    // generate the array, keep its unsorted text, sort it, return both texts
    public SortResponseDTO sortRandomArray(SortRequestDTO requestDTO) {
        NumberArray array = generateArray(requestDTO.getSize());
        SortResponseDTO response = new SortResponseDTO();
        response.setUnsorted(array.toString());
        bubbleSort(array);
        response.setSorted(array.toString());
        return response;
    }

    // bubble sort ascending, in place - the brief's algorithm
    private void bubbleSort(NumberArray array) {
        int size = array.getSize();
        // pass i moves the biggest remaining number to position size-1-i
        for (int i = 0; i < size - 1; i++) {
            boolean swapped = false;
            // the last i numbers are already in place, so stop before them
            for (int j = 0; j < size - 1 - i; j++) {
                // wrong order: the bigger number moves one step right
                if (array.getValue(j) > array.getValue(j + 1)) {
                    array.swap(j, j + 1);
                    swapped = true;
                }
            }
            // no swap in a whole pass: the array is already sorted
            if (!swapped) {
                return;
            }
        }
    }

    // random numbers in [0, size), like the brief's example (size 10 -> 0..9)
    private NumberArray generateArray(int size) {
        int[] values = new int[size];
        // fill every position with a random number
        for (int i = 0; i < size; i++) {
            values[i] = random.nextInt(size);
        }
        return new NumberArray(values);
    }
}
