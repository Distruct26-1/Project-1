package project1;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests each sorting method to ensure the methods are working properly Unsorted
 * normal arrays, empty arrays, sorted arrays, and arrays with duplicate
 * elements are tested. Comparison counting is tested.
 * 
 * @author KatM
 */
public class SorterTest {

	private Sorter sorter;

	@BeforeEach
	public void setUp() {
		sorter = new Sorter();
	}

	// ======== Shaker Sort Tests ========
	@Test
	public void testShakerSort_unsorted_normal() {
		int[] array = { 3, 1, 2, 0 };
		sorter.shakerSort(array);
		assertArrayEquals(new int[] { 0, 1, 2, 3 }, array);
	}

	@Test
	public void testShakerSort_sorted() {
		int[] array = { 0, 1, 2, 3 };
		sorter.shakerSort(array);
		assertArrayEquals(new int[] { 0, 1, 2, 3 }, array);
	}

	@Test
	public void testShakerSort_empty() {
		int[] array = {};
		sorter.shakerSort(array);
		assertArrayEquals(new int[] {}, array);
	}

	@Test
	public void testShakerSort_duplicates() {
		int[] array = { 3, 2, 2, 1, 0, 2 };
		sorter.shakerSort(array);
		assertArrayEquals(new int[] { 0, 1, 2, 2, 2, 3 }, array);
	}

	@Test
	public void testShakerSort_comparison() {
		int[] array = { 3, 2, 0, 1 };
		int comparisons = sorter.shakerSort(array);
		assertTrue(comparisons > 0, "Comparison should be greater than zero");
	}

	// ======== Quick Sort Tests ========
	@Test
	public void testQuickSort_normal_unsorted() {
		int[] array = { 3, 1, 2, 0 };
		sorter.quickSort(array);
		assertArrayEquals(new int[] { 0, 1, 2, 3 }, array);
	}

	@Test
	public void testQuickSort_sorted() {
		int[] array = { 0, 1, 2, 3 };
		sorter.quickSort(array);
		assertArrayEquals(new int[] { 0, 1, 2, 3 }, array);
	}

	@Test
	public void testQuickSort_empty() {
		int[] array = {};
		sorter.quickSort(array);
		assertArrayEquals(new int[] {}, array);
	}

	@Test
	public void testQuickSort_duplicates() {
		int[] array = { 3, 2, 2, 1, 0, 2 };
		sorter.quickSort(array);
		assertArrayEquals(new int[] { 0, 1, 2, 2, 2, 3 }, array);
	}

	@Test
	public void testQuickSort_comparison() {
		int[] array = { 3, 2, 0, 1 };
		int comparisons = sorter.quickSort(array);
		assertTrue(comparisons > 0, "Comparison should be greater than zero");
	}
	// ======== Merge Sort Tests ========

	@Test
	public void testMergeSort_unsorted_normal() {
		int[] array = { 3, 1, 2, 0 };
		sorter.mergeSort(array);
		assertArrayEquals(new int[] { 0, 1, 2, 3 }, array);
	}

	@Test
	public void testMergeSort_sorted() {
		int[] array = { 0, 1, 2, 3 };
		sorter.mergeSort(array);
		assertArrayEquals(new int[] { 0, 1, 2, 3 }, array);
	}

	@Test
	public void testMergeSort_empty() {
		int[] array = {};
		sorter.mergeSort(array);
		assertArrayEquals(new int[] {}, array);
	}

	@Test
	public void testMergeSort_duplicates() {
		int[] array = { 3, 2, 2, 1, 0, 2 };
		sorter.mergeSort(array);
		assertArrayEquals(new int[] { 0, 1, 2, 2, 2, 3 }, array);
	}

	@Test
	public void testMergeSort_comparison() {
		int[] array = { 3, 2, 0, 1 };
		int comparisons = sorter.mergeSort(array);
		assertTrue(comparisons > 0, "Comparison should be greater than zero");
	}

	// ======== Heap Sort Tests ========

	@Test
	public void testHeapSort_unsorted_normal() {
		int[] array = { 3, 1, 2, 0 };
		sorter.heapSort(array);
		assertArrayEquals(new int[] { 0, 1, 2, 3 }, array);
	}

	@Test
	public void testHeapSort_sorted() {
		int[] array = { 0, 1, 2, 3 };
		sorter.heapSort(array);
		assertArrayEquals(new int[] { 0, 1, 2, 3 }, array);
	}

	@Test
	public void testHeapSort_empty() {
		int[] array = {};
		sorter.heapSort(array);
		assertArrayEquals(new int[] {}, array);
	}

	@Test
	public void testHeapSort_duplicates() {
		int[] array = { 3, 2, 2, 1, 0, 2 };
		sorter.heapSort(array);
		assertArrayEquals(new int[] { 0, 1, 2, 2, 2, 3 }, array);
	}

	@Test
	public void testHeapSort_comparison() {
		int[] array = { 3, 2, 0, 1 };
		int comparisons = sorter.heapSort(array);
		assertTrue(comparisons > 0, "Comparison should be greater than zero");
	}
}
