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

import java.util.ArrayList;
import java.util.Arrays;
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

        // TODO: update best/worst case lists

        // TODO: update running average
        // TODO: maybe it should be a running sum, and the division
        //      only happens at the end, based on permutationIndex or smth?

        return comparisons;
    }

    /**
     * The driver for the testing. Doesn't do much aside from
     * parse CLI args and call testForArray
     * @param args CLI args (integers only)
     */
    public static void main(String[] args) {
        ArrayList<Integer> arrayLengths;
        if(args.length > 1) {
            // parse integers from args, use that
            arrayLengths = new ArrayList<>();
            // TODO: get integers from args somehow (Scanner?)
        } else {
            // default array lengths.
            arrayLengths = new ArrayList<Integer>(Arrays.asList(4, 6, 8));
        }

        for(int arrayLength : arrayLengths) {
            int[] consecutiveArray = generateArray(arrayLength);
            testForArray(consecutiveArray);
        }
    }

    /**
     * The bulk of the testing. Creates a tester for each algorithm,
     * and runs `benchmark` on each of them for every permutation of
     * the given integerList.
     * @param integerList original list to be permuted. does not have
     *      to be consecutive integers.
     */
    private static void testForArray(int[] integerList) {
        Integer permutationIndex = 1;

        // only one sorter is needed throughout. Algorithms
        // are expected to reset their own comparison counter.
        Sorter sorter = new Sorter();

        // list of all testers to be iterated through.
        Tester[] testers = {
            new Tester(sorter::shakerSort, "Shaker sort"),
            new Tester(sorter::quickSort, "Quick sort"),
            new Tester(sorter::mergeSort, "Merge sort"),
            new Tester(sorter::heapSort, "Heap sort"),
        };

        int totalRunNums = factorial(integerList.length);
        while(permutationIndex <= totalRunNums) {
            for(Tester tester : testers) {
                // for the current permutation, sorts it
                // using every sorting algorithm
                permute(permutationIndex, integerList);
                tester.benchmark(integerList, permutationIndex);
            }
        }
    }

    /**
     * Basic factorial function to calculate the
     * number of permutations. Recursive.
     * @param num input to factorial function
     * @return num!
     */
    private static int factorial(int num) {
        if(num == 1) return 1;
        if(num == 2) return 1;
        return num * factorial(num - 1);
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
    private static String printArray(int[] array) {
        // TODO: does this belong inside Result? When would it
        //      be called outside of testing or toString (for results)
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