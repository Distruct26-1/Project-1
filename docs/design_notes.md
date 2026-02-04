# Design notes

This project is split into two main classes: Sorter and Tester. Sorter contains the logic for the actual sorting methods, and Tester is responsible for the rest of the logic.

## Sorter structure

It's good for Sorter to be stateful, because it allows for methods take care of the comparison counter, which is important for consistency. I would say the overhead for this abstraction is minimal.

Sorter objects don't need to live a long time, though. Just the length of the individual sorts. It's possible to delete and create new Sorter objects for each individual sort, but I lean towards having a single long-running Sorter, with each algorithm resetting the comparison counter for itself.

**Note** that the sorting algorithms don't clone their inputs, so passing a list to two consecutive sorting algorithms will supply a fully sorted list to the second one. In order to avoid having the sorts modify the lists, it's recommended to clone them before passing them to the Sorter.

## Tester

### Permutation generator

The primary limitation in running the algorithm for more than 4, 6, or 8 -length items is that the number of permutations grows exponentially. Running the test suite on a 10-length array is ~100x longer than an 8-length array, and the gap is even more from 10 to 12.
I suspect I can run up to a 12-length array if I leave it for a day or two, but beyond that is sure to be infeasible.  
The same more-than-exponential growth applies to the memory of the program as well, if you were to keep every permutation of integers the entire time. However, it appears to be possible to run the benchmark for an array of size `n` in merely linear space by using **Heap's Algorithm**, which creates the next permutation based on the previous one, and a counter.

It seems like the permutation generator included in the Java standard library doesn't use this method (Python's does), so the cost of ensuring linear space is a custom implementation of this function

Following this logic, I'm trying not to keep track of *every* result, but rather just the best/worst for each algorithm, as well as their averages.

### Function objects

Everything else in my plan for Tester is pretty straightforward, but while planning this out I was pleased to see that java supports functions as first class objects, meaning I can put the raw function in a list, and call it later. I like this because it reduces the logic of calling the different methods to a single list, making it lean and modifiable at the same time.
