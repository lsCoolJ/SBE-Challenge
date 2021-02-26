package com.assessment.nav;

import java.util.Arrays;
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
				if(rows[rowCount].charAt(colCount) == 'm') {
					setMario(rowCount, colCount);
					grid[rowCount][colCount] = RED_LUIGI;
				} else if(rows[rowCount].charAt(colCount) == 'b') {
					setBowser(rowCount,colCount);
					grid[rowCount][colCount] = SPIKY_TURTLE;
				} else if(rows[rowCount].charAt(colCount) == 'p') {
					setPeach(rowCount,colCount);
					grid[rowCount][colCount] = GENERIC_PRINCESS;
				} else if(rows[rowCount].charAt(colCount) == '*') 
					grid[rowCount][colCount] = HAZARD;
				else grid[rowCount][colCount] = CLEAR_PATH;
			}
		}
	}
	
	public int getGridSize() {
		return gridSize;
	}
	
	public void setMario(int x, int y) {
		this.m = new Location(x,y);
	}
	public Location getM() {
		return m;
	}
	public boolean isMario(Location l) {
		return l.getX() == m.getX() && l.getY() == m.getY();
	}
	
	public void setBowser(int x, int y) {
		this.b = new Location(x,y);
	}
	public boolean isBowser(Location l) {
		return l.getX() == b.getX() && l.getY() == b.getY();
	}
	public Location getB() {
		return b;
	}
	
	public void setPeach(int x, int y) {
		this.p = new Location(x,y);
	}
	public boolean isPeach(Location l) {
		return l.getX() == p.getX() && l.getY() == p.getY();
	}
	
	public boolean isHazard(Location l) {
		return grid[l.getX()][l.getY()] == HAZARD;
	}
	
	public boolean wasVisited(Location l) {
		return visited[l.getX()][l.getY()];
	}
	public void setVisited(Location l, boolean value) {
		this.visited[l.getX()][l.getY()] = value;
	}
	public void clearVisited() {
		for(int i = 0; i < visited.length; i++)
			Arrays.fill(visited[i], false);
	}
	
	public boolean isValidLocation(Location l) {
		if(l.getX() < 0 || l.getX() >= this.gridSize || l.getY() < 0 || l.getY() >= this.gridSize)
			return false;
		return true;
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