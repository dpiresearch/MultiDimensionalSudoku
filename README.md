## Multidimensional Sudoku puzzle solution validator
* * *
### Overview

This code validates if a Sudoku solution passed in via a csv file is valid.

### Video Overview

http://screencast.com/t/ED5l9Q7loE

### Details

Usage: 
    Run it in your favorite IDE with the proper arguments or run it on the terminal via:

    java com.flurry.MultiDimensionalSudoku <filename>
 
    Example: java com.flurry.MultiDimensionalSudoku ./sample4x4.txt

#### Solution file format

The solution file should contain a grid that holds the cell values of the solution

Each row is a comma separated list of values:

Example contents:

```1,4,2,3
2,3,1,4
4,2,3,1
3,1,4,2
```
	
