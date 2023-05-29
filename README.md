KnightsTour
===========

The [knight's tour](https://en.wikipedia.org/wiki/Knight%27s_tour) refers to
the problem of finding a sequence of moves of a knight on a chessboard in which 
the knight visits every square exactly once. 

This repository contains a Java program that finds all the possible knight's tours
from a single starting position that the user defines using standard chessboard
position notion (e.g. "e4"). The chessboard is 5x5, as anything larger than that will
take a considerable amount of time to compute because the program is using a brute force approach.

As solutions are found, they are printed out. Each integer is the order in which the knight
visited that space, starting from 1. At the end, the total number of solutions found is printed.

Below is a table documenting my personal findings of number of solutions for a given board size
and average time for 5 computations (not including printing each solution). Starting position
is always in the top left corner of any board size.

| Board Size | Solutions | Time (ms) |
|------------|-----------|-----------|
| 1x1        |1          |0          |
| 2x2        |0          |0          |
| 3x3        |0          |0          |
| 4x4        |0          |0          |
| 5x5        |304        |35         |
| 6x6        |524486     |340,574    |