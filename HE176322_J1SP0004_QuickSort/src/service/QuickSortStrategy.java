package service;

import model.NumberArray;

/**
 * CONCRETE STRATEGY: quick sort, exactly as the brief describes it.
 *
 * @author HE176322
 */
public class QuickSortStrategy implements SortStrategy {

    // The array sort() is working on; the recursive calls all share it.
    private NumberArray array;

    // Sorts the array ascending with quick sort, starting with the whole range 0 ..
    @Override
    public void sort(NumberArray array) {
        this.array = array;
        quickSort(0, array.getSize() - 1);
    }

    // Quick sort of the range low ..
    private void quickSort(int low, int high) {
        int i = low;
        int j = high;
        // the pivot is kept as a VALUE: the swaps below may move the middle
        // element itself, so remembering its index would go wrong
        int pivot = array.getValue((low + high) / 2);
        // partition until i and j cross
        while (i <= j) {
            // skip the numbers already on the correct (left) side
            while (array.getValue(i) < pivot) {
                i++;
            }
            // skip the numbers already on the correct (right) side
            while (array.getValue(j) > pivot) {
                j--;
            }
            // a big number on the left and a small one on the right: swap them
            if (i <= j) {
                array.swap(i, j);
                i++;
                j--;
            }
        }
        // the left part low .. j still has two or more numbers to sort
        if (low < j) {
            quickSort(low, j);
        }
        // the right part i .. high still has two or more numbers to sort
        if (i < high) {
            quickSort(i, high);
        }
    }
}
