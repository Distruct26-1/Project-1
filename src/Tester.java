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

import java.util.Arrays;
import java.util.function.Function;

public class Tester<T extends Sorter> {

    static int outliers = 10;
    private Function<int[], T> constructor;
    public Integer[] bestCases;
    public Integer[] worstCases;

    public Tester(Function<int[], T> constructor) {
        bestCases = new Integer[10];
        worstCases = new Integer[10];

        Arrays.fill(bestCases, Integer.MAX_VALUE);
        Arrays.fill(worstCases, Integer.MIN_VALUE);

        this.constructor = constructor;
    }

    /**
     * Sorts using the instance's associated algorithm.
     * @param list the list to be sorted
     * @return the Sorter object post-sort
     */
    private T benchmark(int[] list) {
        T sorter = constructor.apply(list);
        
        return sorter;
    }

    public static void main(String[] args) {
    
    }

    /**
     * Creates an array of consecutive integers, from 1 to size.
     * Intended as a base case for the permutation generator.
     * @param size the size of the array (also the largest element)
     * @return array of consecutive integers
     */
    private static int[] generateArray(int size) {
        int[] output = new int[size];
        for(int index=0; index < size; index++) {
            output[index] = index + 1;
        }
        return output;
    }

    private static int[] permute(int iteration, int[] list) {
        return list;
    }
}