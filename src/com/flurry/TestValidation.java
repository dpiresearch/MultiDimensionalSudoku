package com.flurry;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test valid and invalid multidimensional Sudoku solutions
 * 
 * @author danielpang
 *
 */
public class TestValidation {

	@Before
	public void initialize() {
		
	}

	@Test
	public void testRowValidationSuccess() {
		int[][] testSolution = {
				{1,4,2,3},
				{2,3,1,4},
				{4,2,3,1},
				{3,1,4,2}
		};
		
		PuzzleSolution solution = new PuzzleSolution(testSolution);
		
		MultiDimensionalSudokuValidator validator = new MultiDimensionalSudokuValidator();
		
		boolean result = validator.validateRows(solution);
		Assert.assertTrue(result);
	}

	@Test
	public void testRowValidationFail() {
		int[][] testSolution = {
				{1,4,2,4},
				{2,3,1,4},
				{4,2,3,1},
				{3,1,4,2}
		};
		
		PuzzleSolution solution = new PuzzleSolution(testSolution);
		
		MultiDimensionalSudokuValidator validator = new MultiDimensionalSudokuValidator();
		
		boolean result = validator.validateRows(solution);
		Assert.assertFalse(result);
	}

	@Test
	public void testColumnValidationSuccess() {
		int[][] testSolution = {
				{1,4,2,3},
				{2,3,1,4},
				{4,2,3,1},
				{3,1,4,2}
		};
		
		PuzzleSolution solution = new PuzzleSolution(testSolution);
		
		MultiDimensionalSudokuValidator validator = new MultiDimensionalSudokuValidator();
		
		boolean result = validator.validateColumns(solution);
		Assert.assertTrue(result);
	}

	@Test
	public void testColumnValidationFail() {
		int[][] testSolution = {
				{1,4,2,4},
				{2,3,1,4},
				{4,2,3,1},
				{3,1,4,2}
		};
		
		PuzzleSolution solution = new PuzzleSolution(testSolution);
		
		MultiDimensionalSudokuValidator validator = new MultiDimensionalSudokuValidator();
		
		boolean result = validator.validateColumns(solution);
		Assert.assertFalse(result);
	}

	@Test
	public void testSquareValidationSuccess() {
		int[][] testSolution = {
				{1,4,2,3},
				{2,3,1,4},
				{4,2,3,1},
				{3,1,4,2}
		};
		
		PuzzleSolution solution = new PuzzleSolution(testSolution);
		
		MultiDimensionalSudokuValidator validator = new MultiDimensionalSudokuValidator();
		
		boolean result = validator.validateSquare(solution);
		Assert.assertTrue(result);
	}


	@Test
	public void testSquareValidationFail() {
		int[][] testSolution = {
				{1,4,2,3},
				{2,3,1,4},
				{4,2,3,1},
				{3,1,4,1}
		};
		
		PuzzleSolution solution = new PuzzleSolution(testSolution);
		
		MultiDimensionalSudokuValidator validator = new MultiDimensionalSudokuValidator();
		
		boolean result = validator.validateSquare(solution);
		Assert.assertFalse(result);
	}

}
