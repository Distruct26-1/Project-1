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

import java.util.function.Function;

public class Tester {

    static int outlierCount = 10;
    private Function<int[], Integer> algorithm;
    public String algorithmName;
    public Result[] bestCases;
    public Result[] worstCases;
    public double average;

    public Tester(Function<int[], Integer> algorithm, String algorithmName) {
        bestCases = new Result[outlierCount];
        worstCases = new Result[outlierCount];
        average = 0;

        this.algorithm = algorithm;
        this.algorithmName = algorithmName;
    }

    /**
     * Sorts using the instance's associated algorithm.
     * Updates the running average, and if the results 
     * are particularly good or bad, stores the data in
     * a list of outliers.
     * @param list the list to be sorted
     * @return the comparisons used by the algorithm
     */
    private int benchmark(int[] list, int numPrevBenchmarks) {
        int comparisons = algorithm.apply(list.clone());
        return comparisons;
    }

    public static void main(String[] args) {
        // In this function is some minimal code to demonstrate
        // the way that the different parts of the codebase are
        // intended to come together. 
        int[] integerList = generateArray(3);
        Integer permutationIndex = 1;

        Sorter sorter = new Sorter();

        Tester[] testers = {
            new Tester(sorter::shakerSort, "Shaker sort"),
            new Tester(sorter::quickSort, "Quick sort"),
        };

        for(Tester tester : testers) {
            permute(permutationIndex, integerList);

            int comparisons = tester.benchmark(integerList, permutationIndex);
            String niceList = printArray(integerList);
            String name = tester.algorithmName;
            System.out.printf("Sorted list %s in %d comparisons with %s!\n",
                niceList, comparisons, name);
        }
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

    /**
     * Prints the array as a space-separated string.
     * Useful for debugging mostly, I think.
     * @param array array to be converted
     * @return stringified version of array
     */
    public static String printArray(int[] array) {
        String output = "";
        for(int num : array) {
            output += String.valueOf(num);
        }
        return output;
    }

    private static int[] permute(int iteration, int[] list) {
        iteration++;
        return list;
    }
}

/**
 * Container to bundle the results of a sort together,
 * as per the instructions. AlgorithmName isn't included
 * as a parameter, because Result objects are supposed to
 * be stored *within* Tester objects, which already have an
 * associated algorithm type.
 */
class Result implements Comparable<Result> {
    int[] list;
    int operations;

    private Result(int[] list, int operations) {
        this.list = list;
        this.operations = operations;
    }

    @Override
    public int compareTo(Result other) {
        return Integer.compare(this.operations, other.operations);
    }
}