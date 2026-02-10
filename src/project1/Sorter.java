package project1;

/**
 * Programming Project 1 - Spring 2026 CSIS 2430 Section 2
 * 
 * Group 3: Agnes Braz Franco Christian Taylor Dallin Yauney Kathleen Monahan
 */

public class Sorter {

	public int comparisons;

	/**
	 * Shaker sort is like two bubble sorts, going from left to right and back to
	 * left, checking each value is greater or less then itself, swapping where
	 * applicable.
	 * 
	 * Modified from GeeksforGeeks (Cocktail Sort)
	 * https://www.geeksforgeeks.org/cocktail-sort/
	 * 
	 * @param list array to be sorted
	 * @return the number of comparisons that were done to sort this array
	 * @author KatM
	 */

	public int shakerSort(int[] list) {
		boolean swap = true;
		int begin = 0;
		int end = list.length - 1;
		comparisons = 0;

		while (swap) {
			swap = false;
			for (int i = begin; i < end; i++)
				if (greaterThan(list[i], list[i + 1])) {
					int temp = list[i];
					list[i] = list[i + 1];
					list[i + 1] = temp;
					swap = true;
				}

			if (!swap)
				break;
			swap = false;
			end--;

			for (int i = end; i > begin; i--) {
				if (lessThan(list[i], list[i - 1])) {
					int temp = list[i];
					list[i] = list[i - 1];
					list[i - 1] = temp;
					swap = true;
				}
			}
			begin++;
		}
		return comparisons;
	}
	
	/**
	 * Quicksort using the first element as pivot. 
	 * Partitions array so elements smaller than the chosen pivot are on the lhs
	 * and elements larger are on the rhs. Recursively sorts each partition. 
	 * 
	 * Modified from Sedgewick's algorithm for quicksort. 
	 * Using a fixed pivot and unshuffled array which can in some cases
	 * result in quadratic results on sorted data but for the scope of
	 * this assignment, it was more important to have consistancy. 
	 * 
	 * @param list array to be sorted
	 * @return number of comparisons
	 * @author KatM
	 */
	public int quickSort(int[] list) {
		comparisons = 0;
		sort(list, 0, list.length - 1);

		return comparisons;
	}

	private void sort(int[] list, int lo, int hi) {
		if (hi <= lo)
			return; // just ends
		int j = partition(list, lo, hi);
		sort(list, lo, j - 1); // sort left
		sort(list, j + 1, hi); // sort right

	}

	private int partition(int[] list, int lo, int hi) {
		int i = lo;
		int j = hi + 1;
		int v = list[lo];

		while (true) {
			while (lessThan(list[++i], v)) {
				if (i == hi)
					break;
			}
			while (lessThan(v, list[--j])) {
				if (j == lo)
					break;
			}
			if (i >= j) {
				break;
			}
			swap(list, i, j);

		}
		swap(list, lo, j);
		return j;
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
	 * 
	 * @param a first num
	 * @param b num to compare to
	 * @return whether a > b
	 */
	private boolean greaterThan(int a, int b) {
		comparisons++;
		if (a > b)
			return true;
		return false;
	}

	/**
	 * wrapper around <, that increments comparisons
	 * 
	 * @param a first num
	 * @param b num to compare to
	 * @return whether a < b
	 */
	private boolean lessThan(int a, int b) {
		comparisons++;
		if (a < b)
			return true;
		return false;
	}

	/**
	 * uses a "third hand" to swap indicies a and b within a list.
	 * 
	 * @param list list to index into
	 * @param a    index of first number to be swapped
	 * @param b    index of second number to be swapped
	 */
	private void swap(int[] list, int a, int b) {
		int thirdHand = list[b];
		list[b] = list[a];
		list[a] = thirdHand;
	}
}