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

    /**
     * "Runner" for mergesort. Calls mergeSortRecursive.
     * Implementation for mergeSort inspired by Wikipedia.
     * @param list list to be sorted
     * @return number of comparisons
     */
    public int mergeSort(int[] list) {
        comparisons = 0;
        int[] sorted = mergeSortRecursive(list);
		transferArray(sorted, list);
        return comparisons;
    }

    /**
     * Primary method for mergeSort. Splits
     * its input in two, and recurses on each half.
     * Returns `list` if it's small, and zips each half
     * back together.
     * @param list the list to sort
     * @return the sorted version of `list`
     */
    private int[] mergeSortRecursive(int[] list) {
        // Base case
        if (list.length <= 1) return list;

        int midpoint = list.length / 2;
        int[] left = new int[midpoint];
        int[] right = new int[list.length - midpoint];

        // split list into two halves
        for (int i = 0; i < list.length; i++) {
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

    /**
     * acts as a "zipper" to merge sorted lists
     * from mergeSortRecursive.
     * @param left first sorted list to merge
     * @param right second sorted list to merge
     * @return the sorted combination of left and right
     */
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
        return output;
    }

	private void transferArray(int[] from, int[] to) {
		for (int i=0; i < from.length; i++) {
			to[i] = from[i];
		}
	}

/**
	 * Heap sort initially turn the array into a max heap
	 * and then begins to organize the order based off of said max heap
	 * 
	 * Modified from GeeksforGeeks (Heap Sort)
	 * https://www.geeksforgeeks.org/dsa/heap-sort/
	 * 
	 * @param list array to be sorted
	 * @return a sorted array
	 * @author Cristian
	 */
	
	//This method will help the heapSort turn the array into a heap
	private void heapify(int[] holder, int length, int index) {
		
		
		int top = index;
		int left = 2*index +1;
		int right = 2*index +2;
		
		//checks if the left or right variable is larger than the parent
		if(left < length && greaterThan(holder[left], holder[top])) {
			top = left;
		}
		if(right < length && greaterThan(holder[right], holder[top])) {
			top = right;
		}
		
		//Checks if the parent changed and will recall itself if so
		if(top != index) {
			int temp = holder[index];
			holder[index] = holder[top];
			holder[top] = temp;
			heapify(holder, length, top);
		}
		
	}
	
	// Main function to do heap sort
    public int heapSort(int[] arr) {
        int n = arr.length;
        comparisons = 0;

        // Build heap (rearrange vector)
        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(arr, n, i);

        // One by one extract an element from heap
        for (int i = n - 1; i > 0; i--) {

            // Move current root to end
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            // Call max heapify on the reduced heap
            heapify(arr, i, 0);
            
        }
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
