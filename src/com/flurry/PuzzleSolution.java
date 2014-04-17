package com.flurry;

/**
 * Holds a candidate solution for an N dimensional Sudoku solution
 * 
 * @author danielpang
 *
 */
public class PuzzleSolution {
	private int[][] solution = null;    // candidate solution
	private int referenceLength = 0;    // length of a side

	public PuzzleSolution() {
	}

	public PuzzleSolution(int[][] solution) {
		setSolution(solution);
	}
	
	public void setSolution(int[][] solution) {
		this.solution = solution;
		this.referenceLength = solution.length;
	}
	
	public int[][] getSolution() {
		return solution;
	}

	public void setReferenceLength(int referenceLength) {
		this.referenceLength = referenceLength;
	}

	public int getReferenceLength() {
		return this.referenceLength;
	}
}
