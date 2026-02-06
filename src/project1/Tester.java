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
package project1;

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
	 * Sorts using the instance's associated algorithm. Updates the running average,
	 * and if the results are particularly good or bad, stores the data in a list of
	 * outliers.
	 * 
	 * @param list the list to be sorted
	 * @return the comparisons used by the algorithm
	 */
	private int benchmark(int[] list, int numPrevBenchmarks) {
		int comparisons = algorithm.apply(list.clone());
		average = (average * numPrevBenchmarks + comparisons) / (numPrevBenchmarks + 1);

		// TODO: still need to create result and check if it belongs in
		// bestCase/worstCase arrays

		return comparisons;
	}

	public static void main(String[] args) {
		// In this function is some minimal code to demonstrate
		// the way that the different parts of the codebase are
		// intended to come together.
		int[] integerList = generateArray(3);

		Sorter sorter = new Sorter();
		// just testing my first sorting method first
		Tester[] testers = { new Tester(sorter::shakerSort, "Shaker sort"), };

		int[] counter = { 0 };
		
		//I could get rid of the loop as the permute method does the work. 
		//I just have it printing the average as nothing else is quite ready yet for testing

		permute(integerList, integerList.length, testers, counter);

		System.out.println(testers[0].algorithmName + "average" + testers[0].average);
	}

	/**
	 * Creates an array of consecutive integers, from 1 to size. Intended as a base
	 * case for the permutation generator.
	 * 
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
	 * Prints the array as a space-separated string. Useful for debugging mostly, I
	 * think.
	 * 
	 * @param array array to be converted
	 * @return stringified version of array
	 */
	private static String printArray(int[] array) {
		String output = "";
		for (int num : array) {
			output += String.valueOf(num);
		}
		return output;
	}

	/**
	 * Heaps algorithm to create permutations
	 * 
	 * @param list               generated array to permute
	 * @param size               current size for recursion
	 * @param testers            Array of Tester objects
	 * @param permutationCounter counter keeps track of what permutation we are on
	 *                           for averaging
	 */

	private static void permute(int[] list, int size, Tester[] testers, int[] permutationCounter) {
		// base case since Heap's algorithm is based on recursion
		if (size == 1) {
			// no further work needs to be done. test on all sorters. this is so that we
			// don't need
			// to store all permutations. I will still be able to have data to do a graph
			// for the report
			// based on this.
			for (Tester tester : testers) {
				tester.benchmark(list.clone(), permutationCounter[0]);
			}
			permutationCounter[0]++;
			return;
		}

		// Heap's algorithm
		for (int i = 0; i < size; i++) {
			permute(list, size - 1, testers, permutationCounter);

			if (size % 2 == 1) {
				swap(list, 0, size - 1);
			} else {
				swap(list, i, size - 1);
			}
		}
	}

	private static void swap(int[] array, int i, int j) {
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}
}

/**
 * Container to bundle the results of a sort together, as per the instructions.
 * AlgorithmName isn't included as a parameter, because Result objects are
 * supposed to be stored *within* Tester objects, which already have an
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