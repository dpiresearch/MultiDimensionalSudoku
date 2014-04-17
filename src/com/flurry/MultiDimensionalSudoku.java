package com.flurry;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This class will validate if the file passed in holds a valid solution for any given N x N puzzle
 * 
 * The solution is passed in via a comma separated file
 * 
 * File would hold something like this (note no header needed):
 * 
 * 1,4,2,3
 * 2,3,1,4
 * 4,2,3,1
 * 3,1,4,2
 * 
 * Usage: java com.flurry.MultiDimensionalSudoku <filename>
 * 
 * Example: java com.flurry.MultiDimensionalSudoku ./sample4x4.txt
 * 
 * Normally would use log4j or slf4j Logger, but for expediency, using System.out.println for now
 * 
 * @author danielpang
 *
 */
public class MultiDimensionalSudoku {
	
	private static final String COLUMN_DELIMITER = ",";
	
	/**
	 * Accepts a filename that holds the puzzle solution
	 * 
	 * @param args - accepts one argument, which is the filename that has the solution
	 * 
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		// read in files
		if (args.length != 1 ||  args[0] == null || args[0].length() == 0) {
			System.out.println("No solution file specified");
			return;
		}
		
		MultiDimensionalSudoku fs = new MultiDimensionalSudoku();
		System.out.println("Reading file: "+args[0]);
		BufferedReader br = fs.readFile(args[0]);
		
		if (br == null) {
			System.out.println("No file contents found");
			return;
		}

		Boolean result = fs.validatePuzzle(br);
		System.out.println("Result is "+result);
		
	}
	
	/**
	 * Read the file specified by the path and return a reader
	 * 
	 * @param path - path where file can be found
	 * @return     - BufferedReader pointing to the solution file
	 * @throws FileNotFoundException
	 */
	public BufferedReader readFile(String path) throws FileNotFoundException {
		
		if (isEmpty(path)) return null;
		
		File inputFile = new File(path);
		FileInputStream fsinput = null;
		BufferedReader br = null;
		
		try {
			fsinput = new FileInputStream(inputFile);
			DataInputStream in = new DataInputStream(fsinput);
			br = new BufferedReader(new InputStreamReader(in));
		} catch (FileNotFoundException e) {
			System.out.println("File "+path+" not found");
			e.printStackTrace();
		}
		return br;
	}

	/**
	 * Start validating the puzzle by validating the sizes and the 
	 * puzzle values themselves.
	 * 
	 * @param br - buffered reader pointing to the solution file
	 * @return true if everything validates, false otherwise
	 */
	public boolean validatePuzzle(BufferedReader br) {
		if (br == null) return false;
		
		PuzzleSolution puzzleSolution = new PuzzleSolution();
		
		try {
			if (!validateSizes(br, puzzleSolution)) {
				return false;
			}
			MultiDimensionalSudokuValidator validator = new MultiDimensionalSudokuValidator();
			return validator.validateNumbers(puzzleSolution);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Check the dimensions of the solution  
	 *   - make sure all columns are even
	 *   - it's a perfect square and
	 *   - # cols = # rows
	 *   
	 * Read the solution into the Puzzle Solution object
	 * If it becomes too big, will have to refactor to use the filesystem 
	 * or distributed store
	 * 
	 * @param br             - buffered reader that contains the solution
	 * @param puzzleSolution - object containing the puzzle solution
	 * @return true if validation passes, false otherwise
	 * @throws IOException
	 */
	private boolean validateSizes(BufferedReader br, PuzzleSolution puzzleSolution)
			throws IOException {
		
		if (br == null) return false;
		
		int referenceLength = 0;				// length of a side of the puzzle
		int lineNum = 0;						// line number index when parsing the file
		int[][] sPuzzle = new int[1][1];		// temp puzzle solution in memory

		
		String strLine;    // holds a line to be processed.
		// loop through each line
		while ((strLine = br.readLine()) != null) {
			if (isEmpty(strLine)) {
				System.out.println("We have an empty line at " + lineNum);
				return false;
			}
			String[] items = strLine.split(COLUMN_DELIMITER);
			if (items.length <= 1) // we got a bogus file
				return false;
			
			// check the number of columns in the first line
			// to make sure it's a perfect square
			// we'll use this as the reference length of a side
			if (lineNum == 0) { // first line
				if (isPerfectSquare(items.length)) {
					referenceLength = items.length;
					// allocate the array
					sPuzzle = new int[referenceLength][referenceLength];
					puzzleSolution.setSolution(sPuzzle);
				} else {
					System.out.println("Not a perfect square - the number of columns we got is " + items.length);
					return false;
				}
			}
			
			if (!checkColumnSizes(items, referenceLength, lineNum)) return false;
			
			if (!readAndValidateRow(items, puzzleSolution, referenceLength, lineNum)) return false;
			
			lineNum++;
		}
		
		if (lineNum != referenceLength) {
			System.out.println("Rows and columns lengths don't match");
			System.out.println("We have " + referenceLength + " columns and " + lineNum + " rows");
			return false; // number of columns and rows don't match
		}
		return true;
	}

	/**
	 * For each row, check that the cell value is valid (between 1 and N)
	 * 
	 * @param items           - array of values for each line of the solution
	 * @param puzzleSolution  - object to hold the solution
	 * @param referenceLength - the length we use to iterate through the columns.  Also used to compare solution values for validity
	 * @param lineNum         - the row we're validating
	 * @return                - true if the row validates, otherwise false
	 */
	private boolean readAndValidateRow(String[] items, PuzzleSolution puzzleSolution, int referenceLength, int lineNum) {
		// fill in the array
		for (int columnIndex = 0; columnIndex < items.length; columnIndex++) {
			int cellValue = 0;
			try {
				cellValue = Integer.parseInt(items[columnIndex]);
				puzzleSolution.getSolution()[lineNum][columnIndex] = cellValue;
			} catch (NumberFormatException nfe) {
				System.out.println("Can't parse value at row " + (lineNum + 1) + " and column " + columnIndex);
				return false;
			}
			// check cell value is something between 1 and referenceLength inclusive
			if (!(cellValue > 0 && cellValue <= referenceLength)) { 
				System.out.println("Invalid value " + cellValue + " found in row " + (lineNum + 1) + " and column " + columnIndex);
				return false;
			}
		}
		return true;
	}

	/**
	 * Check that each row has the same number of columns
	 * 
	 * @param items           - array of values for each line of the solution
	 * @param referenceLength - the length we use to iterate through the columns.  Also used to compare solution values for validity
	 * @param lineNum         - the row we're validating
	 * @return                - true if the number of columns checks out, otherwise false
	 */
	private boolean checkColumnSizes(String[] items, int referenceLength, int lineNum) {
		int colNum;
		// make sure each row has the same number of columns
		colNum = items.length;
		if (colNum != referenceLength) {
			System.out.println("Row "+(lineNum+1)+ " has a different number of columns " + colNum + " than the first row " + referenceLength);
			return false;
		}
		return true;
	}

	//
	// START UTILITY FUNCTIONS - can pull this into a separate file later
	//
	/**
	 * Check to see that our solution is a perfect square
	 * 
	 * One would qualify, but we're not accepting 1 x 1 solutions
	 * @param number     - length of a side - usually the number of columns in the first line
	 * @return
	 */
	private boolean isPerfectSquare(int number) {
		if (number == 0 || number == 1) return false;
		if(Math.round(Math.sqrt(number))==Math.sqrt(number)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Check if string is empty.
	 * There are apache commons or spring utilities that also handle this.
	 * 
	 * @param path
	 * @return
	 */
	private boolean isEmpty(String path) {
		return path == null || path.length() == 0;
	}
	//
	// END UTILITY FUNCTIONS
	//
}
