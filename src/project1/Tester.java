/**
 * Programming Project 1 - Spring 2026
 * CSIS 2430 Section 2
 * 
 * Group 3 (The Penguins):
 * Agnes Braz Franco
 * Christian Taylor
 * Dallin Yauney
 * Kathleen Monahan
 */
package project1;

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
	public long comparisons;
	public int permutations;

	public Tester(Function<int[], Integer> algorithm, String algorithmName) {
		this.algorithm = algorithm;
		this.algorithmName = algorithmName;
		
		resetState();
	}

	private void resetState() {
		bestCases = new Result[outlierCount];
		worstCases = new Result[outlierCount];
		comparisons = 0;
		permutations = 0;
	}

	/**
	 * The driver for the testing. Doesn't do much aside from parse CLI args and
	 * call testForArray
	 * 
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
					System.err.println("ERROR: invalid argument. Not an integer");
					return;
				}

				// Handle if arg is too small
				if (nextArg < 1) {
					System.err.println("ERROR: invalid argument. Integers must be positive.");
					return;
				}

				arrayLengths.add(nextArg);
			}
		} else {
			// default array lengths.
			arrayLengths = new ArrayList<Integer>(Arrays.asList(4, 6, 8));
		}

		// only one sorter is needed throughout. Algorithms
		// are expected to reset their own comparison counter.
		Sorter sorter = new Sorter();

		// list of all testers to be iterated through.
		Tester[] testers = {
			new Tester(sorter::shakerSort, "Shaker Sort"),
			new Tester(sorter::quickSort, "Quick Sort"),
			new Tester(sorter::mergeSort, "Merge Sort"),
			new Tester(sorter::heapSort, "Heap Sort"),
		};

		// enable CSV mode by default if more than 5 array lengths specified
		boolean csv = (args.length > 5);

		// Print the header for the CSV, if relevant
		if(csv) {
			System.out.printf("length,");

			String[] suffixes = {"Average", "Best", "Worst"};
			for(String suffix : suffixes) {
				for(Tester tester : testers) {
					System.out.printf("%s - %s,", tester.algorithmName, suffix);
				}
			}

			System.out.printf("\n");
		}

		for (int arrayLength : arrayLengths) {
			int[] consecutiveArray = generateArray(arrayLength);
			testForBaseArray(consecutiveArray, testers, csv);
		}
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
	 * Tests for an array, printing the results
	 * 
	 * @param integerList original list to be permuted. Doesn't have
	 * 			to be sorted
	 * @param testers list of testers to be called
	 * @param csv whether to print in CSV mode
	 */
	private static void testForBaseArray(int[] integerList, Tester[] testers, boolean csv) {
		// Reset all testers
		for(Tester tester : testers) {
			tester.resetState();
		}

		// go through every permutation, benchmarking each one along the way
		permute(integerList, integerList.length, testers);

		// If printing as CSV
		if(csv) {
			System.out.printf("%d,", integerList.length);
			// average
			for (Tester tester : testers)
				System.out.printf("%.3f,", tester.average());

			// best
			for (Tester tester : testers)
				System.out.printf("%d,", tester.bestCases[outlierCount-1].comparisons);

			// worst
			for (Tester tester : testers)
				System.out.printf("%d,", tester.worstCases[outlierCount-1].comparisons);

			System.out.printf("\n");
		} else {
			// print out results at the end
			System.out.printf("- - - Results for list with length %d - - -\n", integerList.length);
			for (Tester tester : testers) {
				System.out.printf(" - - - - - - %s - - - - - -\n", tester.algorithmName);
				System.out.printf("  Average comparisons: %.3f\n\n", tester.average());
				System.out.printf("  Best cases:  %s\n", printArray(tester.bestCases));
				System.out.printf("  Worst cases: %s\n", printArray(tester.worstCases));
			}
		}
	}

	private double average() {
		return (double)comparisons / permutations;
	}

	/**
	 * Heaps algorithm to create permutations
	 * 
	 * @param list               generated array to permute
	 * @param size               current size for recursion
	 * @param testers            Array of Tester objects
	 */
	private static void permute(int[] list, int size, Tester[] testers) {
		// base case since Heap's algorithm is based on recursion
		if (size == 1) {
			// no further work needs to be done. test on all sorters. this is
			// so that we don't need to store all permutations. I will still
			// be able to have data to do a graph for the report based on this.
			for (Tester tester : testers) {
				tester.benchmark(list);
			}
			return;
		}

		// Heap's algorithm
		for (int i = 0; i < size; i++) {
			permute(list, size - 1, testers);

			if (size % 2 == 1) {
				Sorter.swap(list, 0, size - 1);
			} else {
				Sorter.swap(list, i, size - 1);
			}
		}
	}

	/**
	 * Sorts using the instance's associated algorithm. Updates the running average,
	 * and if the results are particularly good or bad, stores the data in a list of
	 * outliers.
	 * 
	 * @param list	the list to be sorted
	 */
	private void benchmark(int[] list) {
		int comparisons = algorithm.apply(list.clone());
		Result result = new Result(list, comparisons);

		sortIntoList(bestCases, result, (result2, index) -> bestCases[index].compareTo(result2) > 0);
		sortIntoList(worstCases, result, (result2, index) -> worstCases[index].compareTo(result2) < 0);

		this.comparisons += comparisons;
		this.permutations += 1;
	}

	/**
	 * helper function to sort a new result into a list, if it's small/big enough to
	 * belong in the list. Works for both ascending and descending lists.
	 * 
	 * @param list       the list to insert result into
	 * @param result     the result to be inserted into
	 * @param comparison lambda determining how the items should be sorted. This is
	 *                   what makes it work for best/worst cases at the same time.
	 */
	private void sortIntoList(Result[] list, Result result, BiFunction<Result, Integer, Boolean> comparison) {
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
	 * Formats the results as comparisons and their
	 * associated arrays as a block.
	 * 
	 * @param array array to be converted
	 * @return stringified version of array
	 */
	public static String printArray(Result[] array) {
		String output = "\n";
		for (Result result : array) {
			if (result != null) {
				output += "   [";
				for (int i =0; i<result.list.length; i++) {
					output += result.list[i]; 
					if (i<result.list.length-1) output += ", "; 
				}
				output += "] == "; 
				output += String.valueOf(result.comparisons);
				output += " comparisons \n";
			}
		}
	
		return output;
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
	int comparisons;

	public Result(int[] list, int comparisons) {
		this.list = list;
		this.comparisons = comparisons;
	}

	@Override
	public int compareTo(Result other) {
		return Integer.compare(this.comparisons, other.comparisons);
	}
}