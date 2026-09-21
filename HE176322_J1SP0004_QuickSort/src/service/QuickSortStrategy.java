package service;

import model.NumberArray;

/**
 * CONCRETE STRATEGY: quick sort, exactly as the brief describes it - the pivot is the value
 * of the middle element, i moves forward and j moves backward until they cross.
 *
 * @author HE176322
 */
public class QuickSortStrategy implements ISortStrategy {

    // The array sort() is working on; the recursive calls all share it.
    private NumberArray workingArray;

    // Sorts the whole array ascending with quick sort: the range 0 .. size - 1.
    @Override
    public void sort(NumberArray numberArray) {
        workingArray = numberArray;
        sortByQuick(0, workingArray.getSize() - 1);
    }

    // The brief's quick sort of the range low .. high. The pivot is the VALUE of the middle
    // element (a swap may move that element itself, so its index would go wrong); after the
    // partition both parts are sorted the same way (recursion).
    private void sortByQuick(int low, int high) {
        int i = low;
        int j = high;
        int pivot = workingArray.getValue((low + high) / 2);

        // partition until i and j cross
        while (i <= j) {
            // skip the numbers already on the correct (left) side
            while (workingArray.getValue(i) < pivot) {
                i++;
            }

            // skip the numbers already on the correct (right) side
            while (workingArray.getValue(j) > pivot) {
                j--;
            }

            // a big number on the left and a small one on the right: swap them
            if (i <= j) {
                workingArray.swap(i, j);
                i++;
                j--;
            }
        }

        // the left part low .. j still has two or more numbers to sort
        if (low < j) {
            sortByQuick(low, j);
        }

        // the right part i .. high still has two or more numbers to sort
        if (i < high) {
            sortByQuick(i, high);
        }
    }
}
