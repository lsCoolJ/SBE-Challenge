package com.assessment.nav;

import java.util.List;

public class Grid {
	private static final char RED_LUIGI = 'm';
	private static final char SPIKY_TURTLE = 'b';
	private static final char GENERIC_PRINCESS = 'p';
	private static final char CLEAR_PATH = '-';
	private static final char HAZARD = '*';
	
	private char[][] grid;
	private int gridSize;
	private boolean[][] visited;
	private Location m;
	private Location b;
	private Location p;
	
	public Grid(String fileText) {
		if(fileText == null || (fileText = fileText.trim()).length() == 0) {
			throw new IllegalArgumentException("ERROR: empty file.");
		}
		
		String[] rows = fileText.split("[\r]?\n");
		//Get the size of the X by X grid since it's the first line of the file.
		int fileGridSize = Integer.parseInt(rows[0]);
		this.gridSize = fileGridSize;
		
		this.grid = new char[gridSize][gridSize];
		this.visited = new boolean[gridSize][gridSize];
		
		for(int rowCount = 1; rowCount <= gridSize; rowCount++) {
			//Throw error if the length of the row string is not the same as the grid size.
			if(rows[rowCount].length() != gridSize) {
				throw new IllegalArgumentException("line " + (rowCount) 
						+ " is length " + rows[rowCount].length() 
						+ " but should be " + gridSize 
						+ ". Please update file.");
			}
			
			//Go through each char to build the level's grid.
			for(int colCount = 0; colCount < gridSize; colCount++) {
				if(rows[rowCount].charAt(colCount) == 'm') 
					grid[rowCount][colCount] = RED_LUIGI;
				else if(rows[rowCount].charAt(colCount) == 'b') 
					grid[rowCount][colCount] = SPIKY_TURTLE;
				else if(rows[rowCount].charAt(colCount) == 'p') 
					grid[rowCount][colCount] = GENERIC_PRINCESS;
				else if(rows[rowCount].charAt(colCount) == '*') 
					grid[rowCount][colCount] = HAZARD;
				else grid[rowCount][colCount] = CLEAR_PATH;
			}
		}
	}
	
	public int getGridSize() {
		return gridSize;
	}
	
	public void setVisited(int row, int col, boolean value) {
		this.visited[row][col] = value;
	}
	
	public boolean isValidLocation(int row, int col) {
		if(row < 0 || row >= this.gridSize || col < 0 || col >= this.gridSize)
			return false;
		return true;
	}
	
	public void printSolution(List<Location> path) {
		
	}
	
	public String toString() {
		StringBuilder result = new StringBuilder(gridSize * (gridSize + 1));
		for(int row = 0; row < gridSize; row++) {
			for(int col = 0; col < gridSize; col++) {
				result.append(grid[row][col]);
			}
			result.append('\n');
		}
		return result.toString();
	}
}
