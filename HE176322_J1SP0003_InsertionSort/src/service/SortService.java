package service;

import dto.SortRequestDTO;
import dto.SortResponseDTO;
import java.util.Random;
import model.NumberArray;

/**
 * Service: generates the random array and sorts it with insertion sort.
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
        insertionSort(array);
        response.setSorted(array.toString());
        return response;
    }

    // insertion sort by shifting, in place - the brief's algorithm
    private void insertionSort(NumberArray array) {
        int size = array.getSize();
        // position 0 alone is already sorted, so start at 1
        for (int i = 1; i < size; i++) {
            int key = array.getValue(i);
            int j = i - 1;
            // shift bigger numbers of the sorted part one step right
            while (j >= 0 && array.getValue(j) > key) {
                array.setValue(j + 1, array.getValue(j));
                j--;
            }
            array.setValue(j + 1, key);
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
