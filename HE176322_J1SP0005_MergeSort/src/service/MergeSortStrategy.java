package service;

import model.NumberArray;

/**
 * CONCRETE STRATEGY: merge sort, exactly as the brief describes it.
 *
 * @author HE176322
 */
public class MergeSortStrategy implements SortStrategy {

    // The array sort() is working on; the recursive calls all share it.
    private NumberArray array;

    // Sorts the array ascending with merge sort, starting with the whole range 0 ..
    @Override
    public void sort(NumberArray array) {
        this.array = array;
        mergeSort(0, array.getSize() - 1);
    }

    // Merge sort of the range left ..
    private void mergeSort(int left, int right) {
        // one element (or none): already sorted, the recursion stops here
        if (left >= right) {
            return;
        }
        int mid = (left + right) / 2;
        mergeSort(left, mid);
        mergeSort(mid + 1, right);
        merge(left, right);
    }

    // Merges the two sorted halves left ..
    private void merge(int left, int right) {
        int mid = (left + right) / 2;
        int[] merged = new int[right - left + 1];
        int i = left;
        int j = mid + 1;
        int k = 0;
        // both halves still have numbers: take the smaller of the two fronts
        while (i <= mid && j <= right) {
            // "<=" takes the LEFT number on a tie, which keeps the sort stable
            if (array.getValue(i) <= array.getValue(j)) {
                merged[k] = array.getValue(i);
                i++;
            } else {
                // the right front is smaller: it goes first
                merged[k] = array.getValue(j);
                j++;
            }
            k++;
        }
        // the right half ran out first: copy what is left of the left half
        while (i <= mid) {
            merged[k] = array.getValue(i);
            i++;
            k++;
        }
        // the left half ran out first: copy what is left of the right half
        while (j <= right) {
            merged[k] = array.getValue(j);
            j++;
            k++;
        }
        // write the merged numbers back over positions left .. right
        for (k = 0; k < merged.length; k++) {
            array.setValue(left + k, merged[k]);
        }
    }
}
