package project1;

/**
 * Programming Project 1 - Spring 2026 CSIS 2430 Section 2
 * 
 * Group 3: Agnes Braz Franco Christian Taylor Dallin Yauney Kathleen Monahan
 */

public class Sorter {

	
	public int comparisons;
	
	/**
	 * Shaker sort is like two bubble sorts, going from left to right and back to left,
	 * checking each value is greater or less then itself, swapping where applicable. 
	 * @param list array to be sorted
	 * @return the number of comparisons that were done to sort this array
	 * @author KatM
	 */

	public int shakerSort(int[] list) {
		boolean swap = true;
		int begin = 0;
		int end = list.length - 1;

		while (swap) {
			swap = false;
			for (int i = begin; i < end; i++) {
				if (list[i] > list[i + 1]) {
					int temp = list[i];
					list[i] = list[i + 1];
					list[i + 1] = temp;
					swap = true;
				}
			}

			if (!swap)
				break;
			swap = false;
			end--;

			for (int i = end; i > begin; i--) {
				if (list[i] < list[i - 1]) {
					int temp = list[i];
					list[i] = list[i - 1];
					list[i - 1] = temp;
					swap = true;
				}
			}
			begin++;
		}

		return 0; // Return 0 for now, add counting later

	}

	private boolean swap(int[] list, int i) {
		boolean swap;
		int temp = list[i];
		list[i] = list[i + 1];
		list[i + 1] = temp;
		swap = true;
		return swap;
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