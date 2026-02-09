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
        list = mergeSortRecursive(list);
        return comparisons;
    }

    private int[] mergeSortRecursive(int[] list) {
        // System.out.printf("Rec. Sorting list %s\n", Tester.printArray(list));
        // Base case
        if (list.length <= 1)
            return list;

        int midpoint = list.length / 2;
        int[] left = new int[midpoint];
        // int leftPos = 0;
        int[] right = new int[list.length - midpoint];
        // int rightPos = 0;

        // split list into two halves
        for (int i=0; i < list.length; i++) {
            if (i < midpoint) {
                left[i] = list[i];
            } else {
                right[i - midpoint] = list[i];
            }
        }

        // recursively sort each half
        left = mergeSortRecursive(left);
        right = mergeSortRecursive(right);

        // merge sorted sublists
        return merge(left, right);
    }

    private int[] merge(int[] left, int[] right) {
        int outputLength = left.length + right.length;
        int[] output = new int[outputLength];
        int outputPos = 0;

        int leftPos = 0;
        int rightPos = 0;

        // merge lists together
        while (leftPos < left.length && rightPos < right.length) {
            if (lessThan(left[leftPos], right[rightPos])) {
                output[outputPos++] = left[leftPos++];
            } else {
                output[outputPos++] = right[rightPos++];
            }
        }

        // when one list runs out, empty other list
        // directly into output
        while (leftPos < left.length) {
            output[outputPos++] = left[leftPos++];
        }
        while (rightPos < right.length) {
            output[outputPos++] = right[rightPos++];
        }
        System.out.printf("Merged lists %s and %s into list %s\n",
            Tester.printArray(left), Tester.printArray(right), Tester.printArray(output)
        );
        return output;
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
        if(a > b) return true;
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
        if(a < b) return true;
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