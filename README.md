KnightsTour
===========

The [knight's tour](https://en.wikipedia.org/wiki/Knight%27s_tour) refers to
the problem of finding a sequence of moves of a knight on a chessboard in which 
the knight visits every square exactly once. 

This repository contains a Java program that finds all the possible knight's tours
from every possible starting position on the chessboard using a brute force approach.
I have ported the code over to C using the Win32 API for creating and managing threads, in hopes
of decreased compute time, but testing has revealed the Java program is faster.

Once all of the valid solutions starting from a specific space are computed, a 2D array representation of 
the chessboard is printed out, with that space containing an integer representing the number of valid
solutions that exist from that starting space.

For optimization, only the solutions in the top left quarter of the board are being computed because
the number of solutions is horizontally and vertically symmetric, and can be applied to those respective
spaces without duplicate computations.

Below is a table documenting the of number of solutions for a given board size
and calculated timings for computations. Before the quarter board optimization, the program computed the solutions
on every single space, and I have left the timings as reference.

| Board Size | Solutions | Quarter Brute Force Time (ms) | Full Brute Force Time (ms) |
|------------|-----------|-------------------------------|----------------------------|
| 1x1        |1          |0                              |0                           |
| 2x2        |0          |0                              |0                           |
| 3x3        |0          |0                              |0                           |
| 4x4        |0          |0                              |0                           |
| 5x5        |1728       |68                             |110                         |
| 6x6        |6637920    |452288                         |1450994                     |

5x5 Solutions per Starting Space  
[304, 0, 56, 0, 304]  
[0, 56, 0, 56, 0]  
[56, 0, 64, 0, 56]  
[0, 56, 0, 56, 0]  
[304, 0, 56, 0, 304]  

6x6 Solutions per Starting Space  
[524486, 289050, 115837, 115837, 289050, 524486]  
[289050, 173402, 49578, 49578, 173402, 289050]  
[115837, 49578, 52662, 52662, 49578, 115837]  
[115837, 49578, 52662, 52662, 49578, 115837]  
[289050, 173402, 49578, 49578, 173402, 289050]  
[524486, 289050, 115837, 115837, 289050, 524486]  