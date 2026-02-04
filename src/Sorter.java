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
    protected int[] list;

    /**
     * wrapper around >, that increments comparisons
     * @param a first num
     * @param b num to compare to
     * @return whether a > b
     */
    protected boolean greaterThan(int a, int b) {
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
    protected boolean lessThan(int a, int b) {
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
    protected void swap(int[] list, int a, int b) {
        int thirdHand = list[b];
        list[b] = list[a];
        list[a] = thirdHand;
    }

    /**
     * actions that should be desirable on every sorting
     * implementation; is called by every subclass w/ `super`.
     *
     * Note that `unsorted` is copied, so passing an unsorted list
     * to several `Sorter`s is safe.
     * @param unsorted list to be sorted
     */
    protected Sorter(int[] unsorted) {
        comparisons = 0;
        list = unsorted.clone();
        sort(list);
    }

    /**
     * useless function. Exists to be overwritten by subclasses,
     * but for the constructor to call `sort` on their instantiation,
     * there must be a base method to fall back on.
     * @param list the list to ostensibly be sorted
     * @return the input list
     */
    protected int[] sort(int[] list) {
        return list;
    }
}