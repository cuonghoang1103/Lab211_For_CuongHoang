package service;

import model.NumberArray;

/**
 * CONCRETE STRATEGY: merge sort, exactly as the brief describes it - divide the array until
 * every part holds one element, then repeatedly merge the sorted parts into one.
 *
 * @author HE176322
 */
public class MergeSortStrategy implements ISortStrategy {

    // The array sort() is working on; the recursive calls all share it.
    private NumberArray workingArray;

    // Sorts the whole array ascending with merge sort: the range 0 .. size - 1.
    @Override
    public void sort(NumberArray numberArray) {
        workingArray = numberArray;
        sortByMerge(0, workingArray.getSize() - 1);
    }

    // The brief's merge sort of the range left .. right: split it in the middle, sort both
    // halves the same way (recursion), then merge the two sorted halves.
    private void sortByMerge(int left, int right) {
        int mid = (left + right) / 2;

        // one element (or none): already sorted, the recursion stops here
        if (left >= right) {
            return;
        }

        // divide: sort the left half, then the right half, then merge them
        sortByMerge(left, mid);
        sortByMerge(mid + 1, right);
        merge(left, right);
    }

    // Merges the two sorted halves left .. mid and mid + 1 .. right into one sorted range;
    // mid is computed again with the same formula as sortByMerge, so the cut is the same.
    private void merge(int left, int right) {
        int mid = (left + right) / 2;
        int[] mergedArray = new int[right - left + 1];
        int i = left;
        int j = mid + 1;
        int k = 0;

        // both halves still have numbers: take the smaller of the two fronts
        while ((i <= mid) && (j <= right)) {
            // "<=" takes the LEFT number on a tie, which keeps the sort stable
            if (workingArray.getValue(i) <= workingArray.getValue(j)) {
                mergedArray[k] = workingArray.getValue(i);
                i++;
            } else {
                // the right front is smaller: it goes first
                mergedArray[k] = workingArray.getValue(j);
                j++;
            }

            // one more number is in the merged array
            k++;
        }

        // the right half ran out first: copy what is left of the left half
        while (i <= mid) {
            mergedArray[k] = workingArray.getValue(i);
            i++;
            k++;
        }

        // the left half ran out first: copy what is left of the right half
        while (j <= right) {
            mergedArray[k] = workingArray.getValue(j);
            j++;
            k++;
        }

        // write the merged numbers back over positions left .. right
        for (k = 0; k < mergedArray.length; k++) {
            workingArray.setValue(left + k, mergedArray[k]);
        }
    }
}
