package service;

import constants.Message;
import dto.SortRequestDTO;
import dto.SortResponseDTO;
import java.util.Arrays;
import model.NumberArray;
import repository.NumberRepository;

/**
 * SERVICE: keeps the typed array (through the repository) and sorts copies of it
 * ascending or descending with bubble sort. Called only by the controller; no print, no
 * keyboard.
 *
 * @author HE176322
 */
public class SortService {

    // Where the array typed in option 1 is kept (Service -> Repository -> Model).
    private NumberRepository numberRepository;

    // Creates the service with an empty repository.
    public SortService() {
        numberRepository = new NumberRepository();
    }

    // Option 1: stores the typed array, replacing any earlier one.
    public void saveArray(SortRequestDTO requestDTO) {
        numberRepository.saveNumberArray(requestDTO.getElementArray());
    }

    // Option 2: the stored array sorted ascending.
    public SortResponseDTO getAscending() throws Exception {
        SortResponseDTO responseDTO = new SortResponseDTO();

        // sort a copy, so the array the user typed stays as it was typed
        responseDTO.setSortedArray(sortAscending(copyNumberArray()));
        responseDTO.setAscending(true);
        return responseDTO;
    }

    // Option 3: the stored array sorted descending.
    public SortResponseDTO getDescending() throws Exception {
        SortResponseDTO responseDTO = new SortResponseDTO();

        // sort a copy, so the array the user typed stays as it was typed
        responseDTO.setSortedArray(sortDescending(copyNumberArray()));
        responseDTO.setAscending(false);
        return responseDTO;
    }

    // Returns a COPY of the stored elements, so sorting for display never changes the
    // array the user typed.
    private int[] copyNumberArray() throws Exception {
        NumberArray numberArray = numberRepository.getNumberArray();

        // option 2 or 3 before option 1: there is nothing to sort
        if (numberArray.getSize() == 0) {
            throw new Exception(Message.ARRAY_EMPTY);
        }

        return Arrays.copyOf(numberArray.getValueArray(), numberArray.getSize());
    }

    // The brief's Function 2 with Format 1, from the start to the end: each pass moves the
    // biggest remaining number to the end.
    // brief: int[] sortAscending(int[] arrayNeedSort)
    private int[] sortAscending(int[] needSortArray) {
        NumberArray numberArray = new NumberArray(needSortArray);
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

            // no swap in a whole pass: already sorted
            if (!swapped) {
                break;
            }
        }

        return numberArray.getValueArray();
    }

    // The brief's Function 3 in the direction of Format 2, from the end to the start: each
    // pass moves the biggest remaining number to the start.
    // brief: int[] sortDescending(int[] arrayNeedSort)
    private int[] sortDescending(int[] needSortArray) {
        NumberArray numberArray = new NumberArray(needSortArray);
        int size = numberArray.getSize();

        // pass i puts the biggest remaining number at position i
        for (int i = 0; i < (size - 1); i++) {
            boolean swapped = false;

            // walk from the end back to i + 1; positions before i are done
            for (int j = size - 1; j > i; j--) {
                // the right number is bigger: it moves one step to the left
                if (numberArray.getValue(j) > numberArray.getValue(j - 1)) {
                    numberArray.swap(j, j - 1);
                    swapped = true;
                }
            }

            // no swap in a whole pass: already sorted
            if (!swapped) {
                break;
            }
        }

        return numberArray.getValueArray();
    }
}
