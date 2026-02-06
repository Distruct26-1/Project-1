/**
 * Programming Project 1 - Spring 2026
 * CSIS 2430 Section 2
 *
 * Group 3:
 * Agnes Braz Franco
 * Christian Taylor
 * Dallin Yauney
 * Kathleen Monahan
 */

public class Sorter {

    public int comparisons;

    public int shakerSort(int[] list) {
        comparisons = 0;
        return comparisons;
    }

    public int quickSort(int[] list) {
        comparisons = 0;
        return comparisons;
    }

    public int mergeSort(int[] list) {
        comparisons = 0;
        return comparisons;
    }

    public int heapSort(int[] list) {
        comparisons = 0;
        return comparisons;
    }

    /**
     * wrapper around >, that increments comparisons
     * @param a first num
     * @param b num to compare to
     * @return whether a > b
     */
    private boolean greaterThan(int a, int b) {
        comparisons++;
        if (a > b) return true;
        return false;
    }

    /**
     * wrapper around <, that increments comparisons
     * @param a first num
     * @param b num to compare to
     * @return whether a < b
     */
    private boolean lessThan(int a, int b) {
        comparisons++;
        if (a < b) return true;
        return false;
    }

    /**
     * uses a "third hand" to swap indicies a and b
     * within a list.
     * @param list list to index into
     * @param a index of first number to be swapped
     * @param b index of second number to be swapped
     */
    private void swap(int[] list, int a, int b) {
        int thirdHand = list[b];
        list[b] = list[a];
        list[a] = thirdHand;
    }
}
