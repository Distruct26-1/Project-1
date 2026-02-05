# Design notes

This project is split into two main classes: Sorter and Tester. Sorter contains the logic for the actual sorting methods, and Tester is responsible for the rest of the logic.

## Sorter

### Non-static

It's good for Sorter to be stateful, because it allows for methods take care of the comparison counter, which is important for consistency. I would say the overhead for this abstraction is minimal.

Sorter objects don't need to live a long time, though. Just the length of the individual sorts. It's possible to delete and create new Sorter objects for each individual sort, but I lean towards having a single long-running Sorter, with each algorithm resetting the comparison counter for itself.

### Single file (not subclasses)

It's possible to have a subclass for each sorting algorithm, but I decided not to go this route. The justification for this is pretty subjective: I think that it is valuable to avoid unnecessary abstractions (valuable in the same sense as the *Principle of least privilege*), meaning that if I *can* avoid an abstraction by doing something similar, I should try to. I consider superclasses to be an abstraction that is, in this case, avoidable by keeping every algorithm in a single class.

If you look through the commit history, you can actually see that I almost went with the subclass structure, but changed my mind. I don't think it makes a big difference in this case, but I just couldn't justify the extra smidge of complexity I believe is added by subclassing.

### Return type

There are three things that need to be tracked in the case of a best/worst case run: the input list, the number of comparisons, and the algorithm that produced this combination. Why, then, do the sorting algorithms only return an `int`?

The part of the code that does the tracking is the Tester instances. They receive the combination count from the sort, obviously, and the method that supplies the sorting algorithm its list is the same one that logs the run in the best/worst case lists, so it already knows the associated unsorted array. Finally, this method takes place in an instance that only tests a single algorithm, and its name is stored in a class attribute.

### Could the sorts be wrong?!?

The sorting algorithms don't verify their own results, nor does the tester running them (it doesn't even *see* the resulting sorted list). The reason for this is pretty much that I think we can get away with it.

The number of actions in a round of benchmarking is huge, and even though verifying that a sorting algorithm is running correctly is `O(n)`, compared to the sorts themselves which are `O(n log(n))` at best, I want the project to be as slim as can be reasonably achieved.

Not to mention that the algorithms don't need to be verified *every time* - once you know the algorithm is correct, it's unnecessary to keep testing it on *every benchmark*. Our plan is to have a test suite that checks a sorting algorithm's correctness, which is run separately from the comparison counter. Such an implementation is possible, because the sorts don't clone their inputs, so passing one an array and checking if it's sorted afterwards is totally doable outside the scope of the standard benchmarker.

### Other notes

Keep in mind that the **sorting algorithms don't clone their inputs**, so passing a list to two consecutive sorting algorithms will supply a fully sorted list to the second one, and is thus considered **unsafe**. In order to avoid having the sorts modify the lists, it's recommended to clone them before passing them to the Sorter.

## Tester

### Permutation generator

The primary limitation in running the algorithm for more than 4, 6, or 8 -length items is that the number of permutations grows exponentially. Running the test suite on a 10-length array is ~100x longer than an 8-length array, and the gap is even more from 10 to 12.
I suspect I can run up to a 12-length array if I leave it for a day or two, but beyond that is sure to be infeasible.  
The same more-than-exponential growth applies to the memory of the program as well, if you were to keep every permutation of integers the entire time. However, it appears to be possible to run the benchmark for an array of size `n` in merely linear space by using **Heap's Algorithm**, which creates the next permutation based on the previous one, and a counter.

It seems like the permutation generator included in the Java standard library doesn't use this method (Python's does), so the cost of ensuring linear space is a custom implementation of this function

Following this logic, I'm trying not to keep track of *every* result, but rather just the best/worst for each algorithm, as well as their averages.

### Tester instances

In order to keep track of the statistics for every sorting algorithm individually, we use instances of the Tester class to track each algorithm. Thus, every Tester instance has its own list of the best/worst cases for its algorithm, as well as a running average and name of the algorithm. It also uses function references as a way to call its associated sorting function.

This means that creating a new sorting algorithm to be tracked would be as easy as writing it in the `Sorter.java` file, and adding a new Tester instance to a list with the name and a reference to the sorting function.

### Function objects

As mentioned above, this project uses function references, aka methods as objects. This is through the double colon syntax (aka `sorter::bogoSort`) and is important because there's no other way to describe a function for one part of code, only for another part of code to eventually call it. This is what we do when creating a Tester object: it creates some lists to track the best/worst cases of some function, but it doesn't know how to use the function to sort & benchmark unless we can describe the function without calling it. Calling the function would only give the return type, usually `int`, to the Tester object, which isn't what we want.
