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
import java.util.function.BiFunction;
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
     * @param numPrevBenchmarks number of previous benchmarks
     *      (1 if it's the second benchmark, 23 if it's the 24th...)
     * @return the comparisons used by the algorithm
     */
    private int benchmark(int[] list, int numPrevBenchmarks) {
        int comparisons = algorithm.apply(list.clone());
        Result result = new Result(list, comparisons);

        sortIntoList(
            bestCases,
            result,
            (result2, index) -> bestCases[index].compareTo(result2) > 0
        );
        sortIntoList(
            worstCases,
            result,
            (result2, index) -> worstCases[index].compareTo(result2) < 0
        );

        average = average + (comparisons - average) / numPrevBenchmarks;
        return comparisons;
    }

    /**
     * helper function to sort a new result into a list,
     * if it's small/big enough to belong in the list.
     * Works for both ascending and descending lists.
     * @param list the list to insert result into
     * @param result the result to be inserted into
     * @param comparison lambda determining how the items should
     *       be sorted. This is what makes it work for best/worst
     *       cases at the same time.
     */
    private void sortIntoList(
        Result[] list,
        Result result,
        BiFunction<Result, Integer, Boolean> comparison
    ) {
        if (list[0] == null || comparison.apply(result, 0)) {
            list[0] = result;
            for (int next = 1; next < outlierCount; next++) {
                if (list[next] == null || comparison.apply(result, next)) {
                    // swap result with next item
                    Result thirdHand = list[next - 1];
                    list[next - 1] = list[next];
                    list[next] = thirdHand;
                } else {
                    // if result is no longer bigger/smaller than
                    // the next one, it has been sorted.
                    break;
                }
            }
        }
    }

    /**
     * The driver for the testing. Doesn't do much aside from
     * parse CLI args and call testForArray
     * @param args CLI args (integers only)
     */
    public static void main(String[] args) {
        ArrayList<Integer> arrayLengths;
        if (args.length > 0) {
            // parse integers from args, use that
            arrayLengths = new ArrayList<>();
            for (String arg : args) {
                Integer nextArg;

                try {
                    // Handle if arg isn't integer
                    nextArg = Integer.valueOf(arg);
                } catch (Exception e) {
                    System.err.println(
                        "ERROR: invalid argument. Not an integer"
                    );
                    return;
                }

                // Handle if arg is too small
                if (nextArg < 1) {
                    System.err.println(
                        "ERROR: invalid argument. Integers must be positive."
                    );
                    return;
                }

                arrayLengths.add(nextArg);
            }
        } else {
            // default array lengths.
            arrayLengths = new ArrayList<Integer>(Arrays.asList(4, 6, 8));
        }

        for (int arrayLength : arrayLengths) {
            int[] consecutiveArray = generateArray(arrayLength);
            testForBaseArray(consecutiveArray);
        }
    }

    /**
     * The bulk of the testing. Creates a tester for each algorithm,
     * and runs `benchmark` on each of them for every permutation of
     * the given integerList.
     * @param integerList original list to be permuted. does not have
     *      to be consecutive integers.
     */
    private static void testForBaseArray(int[] integerList) {
        int permutationIndex = 1;

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
        while (permutationIndex <= totalRunNums) {
            permute(permutationIndex, integerList);
            for (Tester tester : testers) {
                // for the current permutation, sorts it
                // using every sorting algorithm
                tester.benchmark(integerList, permutationIndex);
            }
            permutationIndex++;
        }

        // print out results at the end
        for (Tester tester : testers) {
            System.out.printf(
                "\n\n- - - - - - %s - - - - - -\n",
                tester.algorithmName
            );
            System.out.printf("Average comparisons: %s\n", tester.average);
            System.out.printf(
                "Best cases:  %s\n",
                printArray(tester.bestCases)
            );
            System.out.printf(
                "Worst cases: %s\n",
                printArray(tester.worstCases)
            );
        }
    }

    /**
     * Basic factorial function to calculate the
     * number of permutations. Recursive.
     * @param num input to factorial function
     * @return num!
     */
    private static int factorial(int num) {
        if (num == 1) return 1;
        if (num == 2) return 2;
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
        for (int index = 0; index < size; index++) {
            output[index] = index + 1;
        }
        return output;
    }

    /**
     * Prints the comparisons portion of the
     * array as a space-separated string.
     * @param array array to be converted
     * @return stringified version of array
     */
    public static String printArray(Result[] array) {
        String output = "[ ";
        for (Result result : array) {
            output += String.valueOf(result.operations);
            output += " ";
        }
        output += "]";
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

    Result(int[] list, int operations) {
        this.list = list;
        this.operations = operations;
    }

    @Override
    public int compareTo(Result other) {
        return Integer.compare(this.operations, other.operations);
    }
}
