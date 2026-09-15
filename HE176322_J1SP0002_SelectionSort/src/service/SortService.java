package service;

import dto.SortRequestDTO;
import dto.SortResponseDTO;
import java.util.Random;
import model.NumberArray;

/**
 * Service: generates the random array and sorts it with selection sort.
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
        selectionSort(array);
        response.setSorted(array.toString());
        return response;
    }

    // selection sort ascending, in place - the brief's algorithm
    private void selectionSort(NumberArray array) {
        int size = array.getSize();
        // i is the first position of the unsorted part
        for (int i = 0; i < size - 1; i++) {
            int minIndex = i;
            // find the minimum of the unsorted part
            for (int j = i + 1; j < size; j++) {
                // strictly smaller: on a tie keep the first one found
                if (array.getValue(j) < array.getValue(minIndex)) {
                    minIndex = j;
                }
            }
            // bring the minimum to the front of the unsorted part
            if (minIndex != i) {
                array.swap(i, minIndex);
            }
        }
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
