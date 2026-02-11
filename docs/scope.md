# Project scope

This project tries to be efficient and extensible. Several decisions have been made with the goal of allowing it to be run at a larger scale - whether that's with larger lists or more algorithms.

## Requirements

The ultimate requirements for this project are listed on Canvas, but they are paraphrased here.

### Functionality

User should be able to supply a lengh of an array (or lengths of arrays). Arrays of consecutive positive integers, starting at one, are constructed, as are every permutation of these arrays. These arrays are then sorted by a variety of sorting algorithms, defined in `/docs/project_plan.md`, and the number of comparisons each algorithm uses to sort each permutation are used to update the average, best cases, and worst cases for each algorithm. Once the benchmarking is done, these results are shown to the user.

### Documentation

There should be a project plan, `/docs/project_plan.md`, which contains a list of tasks to be split up and assigned. It also tracks their status, and a pointer to their completion if marked as done.

There should be a scope document, `/docs/scope.md`, which defines the functionality, limitations, and high-level direction/philosophy of the project.

There should be design notes, `/docs/design_notes.md`, which notes details on the methods of implementation for some algorithms. Readers of the code should be able to find notes on the reason things are structured/implemented as they are when they are confused inside this document.

There should be a list of member roles, `/docs/contributions.md`, which documents the group members and what their high-level responsibilities are.

## Guidelines

This project seeks to use as little memory as possible. As such, it doesn't keep track of every permutation - it calculates those recursively, one at a time. Similarly, it doesn't track the results of every run. It has a list of the best/worst performing runs for each algorithm, and keeps a running tally of the average. Because it doesn't track *every run*, however, some statistics are **not** planned, such as the median or other percentiles.

Because the memory used by this project is basically linear based on the size of the unsorted lists, it could scale to lists that are thousands or even millions of integers long. Such benchmarks would take until the heat death of the universe to complete, but the limitation isn't on the memory side of things. This hopefully allows for a few double-digit list runs, maybe over the weekend or something, to test our estimates once we've made them.

This project seeks to be extensible. Adding another sorting algorithm, while not currently planned, should be easy. I think having extensible code more or less guarantees code that's easier to change, and that minimizes duplication, which are actual desirable traits, and extensible code is a sort of litmus test for code quality. Additionally, extensible code encourages experimentation ("out of curiosity, I wonder how these compare against radix sort" or "I wonder if I could add bogo sort to the test suite").

## What it avoids

This project will not solve global warming. This project will not cure cancer. This project won't even find the median time for a sorting algorithm. This project performs the bare minimum required by the docs, except when we feel like implementing nice-to-haves, such as code quality, output formatting, and command line arguments.
