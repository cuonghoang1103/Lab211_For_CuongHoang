package service;

import dto.ArrayRequestDTO;
import dto.SortResponseDTO;
import model.NumberArray;
import repository.ArrayRepository;

/**
 * Service: keeps the typed array (through the repository) and sorts copies of it
 * ascending or descending with bubble sort.
 *
 * @author HE176322
 */
public class SortService {

    // where the array typed in option 1 is kept
    private ArrayRepository arrayRepository;

    // creates the service with its repository
    public SortService() {
        this.arrayRepository = new ArrayRepository();
    }

    // option 1: store the typed array
    public void saveArray(ArrayRequestDTO requestDTO) {
        arrayRepository.saveArray(requestDTO);
    }

    // option 2: the stored array sorted ascending
    public SortResponseDTO getAscending() throws Exception {
        return new SortResponseDTO(sortAscending(arrayRepository.getValues()));
    }

    // option 3: the stored array sorted descending
    public SortResponseDTO getDescending() throws Exception {
        return new SortResponseDTO(sortDescending(arrayRepository.getValues()));
    }

    // brief: int[] sortAscending(int[]) - format 1, from the start to the end
    private int[] sortAscending(int[] arrayNeedSort) {
        NumberArray array = new NumberArray(arrayNeedSort);
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
            // no swap in a whole pass: already sorted
            if (!swapped) {
                break;
            }
        }
        return array.getValues();
    }

    // brief: int[] sortDescending(int[]) - format 2, from the end to the start
    private int[] sortDescending(int[] arrayNeedSort) {
        NumberArray array = new NumberArray(arrayNeedSort);
        int size = array.getSize();
        // pass i puts the biggest remaining number at position i
        for (int i = 0; i < size - 1; i++) {
            boolean swapped = false;
            // walk from the end back to i + 1; positions before i are done
            for (int j = size - 1; j > i; j--) {
                // the right number is bigger: it moves one step to the left
                if (array.getValue(j) > array.getValue(j - 1)) {
                    array.swap(j, j - 1);
                    swapped = true;
                }
            }
            // no swap in a whole pass: already sorted
            if (!swapped) {
                break;
            }
        }
        return array.getValues();
    }
}
