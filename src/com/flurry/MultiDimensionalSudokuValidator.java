package com.flurry;

/**
 * Validator class for N dimensional Sudoku solutions
 * 
 * This class is used to validate Sudoku solutions passed in as a PuzzleSolution object
 * 
 * @author danielpang
 *
 */
public class MultiDimensionalSudokuValidator {
	
	public boolean[] alreadyUsed;   // our array to keep track of used numbers

	public MultiDimensionalSudokuValidator() {
	}

	/**
	 * Entry point to start validation, calls methods to validate rows, columns, and squares
	 * @param puzzleSolution - holds the solution
	 * @return               - true if solution validates, otherwise false
	 */
	public boolean validateNumbers(PuzzleSolution puzzleSolution) {
		if (puzzleSolution == null || puzzleSolution.getSolution() == null || puzzleSolution.getReferenceLength() <= 1) return false;

		System.out.println("start validating the puzzle");

		if (!validateRows(puzzleSolution) || 
			!validateColumns(puzzleSolution) || 
			!validateSquare(puzzleSolution)) return false;
		return true;
	}		
	
	/**
	 * Validate rows in the puzzle
	 * @param puzzleSolution - holds the solution
	 * @return               - true if rows validates, otherwise false
	 */
	boolean validateRows(PuzzleSolution puzzleSolution) {
		
		if (puzzleSolution == null || puzzleSolution.getSolution() == null || puzzleSolution.getReferenceLength() <= 1) return false;
		
		int referenceLength = puzzleSolution.getReferenceLength();
		
		// go through the rows
		System.out.println("start validating rows");
		
		for (int row = 0; row < referenceLength; row++) {
			resetAlreadyUsedList(referenceLength);
			for (int col = 0; col < referenceLength; col++) {
				if (isAlreadyUsed(puzzleSolution.getSolution()[row][col])) {
					System.out.println("FALSE: Detected used digit at " + row + " and " + col);
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Validate all columns in the solution
	 * @param puzzleSolution - holds the solution
	 * @return               - true if columns validates, otherwise false
	 */
	boolean validateColumns(PuzzleSolution puzzleSolution) {
		
		if (puzzleSolution == null || puzzleSolution.getSolution() == null || puzzleSolution.getReferenceLength() <= 1) return false;
		int referenceLength = puzzleSolution.getReferenceLength();

		// go through the columns
		System.out.println("start validating columns");

		for (int col = 0; col < referenceLength; col++) {
			resetAlreadyUsedList(referenceLength);
			for (int row = 0; row < referenceLength; row++) {
				if (isAlreadyUsed(puzzleSolution.getSolution()[row][col])) {
					System.out.println("FALSE: Detected used digit at "+row+" and "+col);
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Validates the subsquares in the solution.  Splits the solution into sub squares
	 * and calls validateSubSquare to validate individual subsquares
	 * @param puzzleSolution - holds the solution
	 * @return               - true if solution validates, otherwise false
	 */
	boolean validateSquare(PuzzleSolution puzzleSolution) {
		if (puzzleSolution == null || puzzleSolution.getSolution() == null || puzzleSolution.getReferenceLength() <= 1) return false;
		int referenceLength = puzzleSolution.getReferenceLength();

		// go through the subsquares
		System.out.println("start validating squares");
		int subSquareSize = (int) Math.sqrt(referenceLength);
		for (int row = 0; row < referenceLength; row += subSquareSize) {
			for (int col = 0; col < puzzleSolution.getSolution()[row].length; col += subSquareSize) {
				System.out.println("validating subSquare at " + row + " and " + col);
				if (!validateSubSquare(puzzleSolution, row,col, subSquareSize)) return false;
			}
		}
		return true;
	}

	private boolean validateSubSquare(PuzzleSolution puzzleSolution, int row, int col, int size) {
		if (puzzleSolution == null || puzzleSolution.getSolution() == null || puzzleSolution.getReferenceLength() <= 1) return false;
		int referenceLength = puzzleSolution.getReferenceLength();

		resetAlreadyUsedList(referenceLength);
		for (int subRow = row; subRow < row + size; subRow++) {
			for (int subCol = col; subCol < col + size; subCol++) {
				if (isAlreadyUsed(puzzleSolution.getSolution()[subRow][subCol])) {
					System.out.println("FALSE: Detected used digit at "+subRow+" and "+subCol);
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Reset the alreadyUsed array before we perform validation
	 * @param referenceLength
	 */
	public void resetAlreadyUsedList(int referenceLength) {
		alreadyUsed = new boolean[referenceLength];
	}
	
	/**
	 * Check if digit already used.
	 * 
	 * @param value  - cell value to check.  Should not be already used if solution is valid
	 * @return       - true if already used (invalid solution), false otherwise
	 */
	public boolean isAlreadyUsed(int value) {
		if (value == 0) {
			System.out.println("digit "+value+" is a 0");
			return true;
		}
		if (alreadyUsed[value-1]) {
			System.out.println("Digit "+value+" is already used");
			return true;
		}
		else {
			alreadyUsed[value-1] = true;
			return false;
		}
	}
}