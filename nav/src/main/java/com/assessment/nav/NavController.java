package com.assessment.nav;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class NavController implements CommandLineRunner {
	private static final int[][] MOVES = { {0,1}, {-1,0}, {0,-1}, {1,0} };
	private static final String[] DIRECTIONS = {"RIGHT", "UP", "LEFT", "DOWN"};
	private static final String LEVELFILE = "lvlsix.txt";
	
	@Override
	public void run(String... args) throws Exception {
		runLevelFile();
		runLevelStdin();
	}
	
	/*
	 * This function takes input from STDIN to run this app.
	 */
	public void runLevelStdin() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("RUNNING LEVEL VIA STDIN AS INPUT!");
		System.out.println("Enter grid size X by X: ");
		int gridSize = scanner.nextInt();
		System.out.println("Next, enter the " + gridSize + " lines constructing the grid: ");
		String fileText = String.valueOf(gridSize);
		for(int i = 0; i <= gridSize; ++i) {
			String curLine = scanner.nextLine();
			if(curLine.length() > gridSize) {
				System.out.println("Error in grid size.");
				continue;
			}
			fileText += curLine + '\n';
		}
		scanner.close();
		
		navigateGrid(fileText);
	}
	
	/*
	 * This function takes input from a file to run this app.
	 */
	public void runLevelFile() throws FileNotFoundException {
		System.out.println("RUNNING LEVEL VIA FILE AS INPUT!");
		File level = new File("src/main/resources/levels/" + LEVELFILE);
		String fileText = "";
		try (Scanner input = new Scanner(level)) {
			while(input.hasNextLine()) {
				fileText += input.nextLine() + "\n";
			}
			input.close();
		} catch(FileNotFoundException fnfe) {}
		
		navigateGrid(fileText);
	}
	
	/*
	 * This function is the main piece of this app. It takes a string and 
	 * uses it to generate a Grid object and then navigates through the 
	 * grid (which is the level) with m as the origin, goes to b, then 
	 * goes to p and prints the solution.
	 */
	private void navigateGrid(String fileText) {
		Grid grid = new Grid(fileText);
		
		LinkedList<Location> next = new LinkedList<Location>();
		Location origin = grid.getM();
		next.add(origin);
		
		while(!next.isEmpty()) {
			Location current = next.remove();
			
			if(!grid.isValidLocation(current) || grid.wasVisited(current)) 
				continue;
			if(grid.isHazard(current)) {
				grid.setVisited(current, true);
				continue;
			}
			
			if(grid.isBowser(current)) {
				grid.clearVisited();
				grid.setVisited(current, true);
				next.clear();
			} else if(grid.isPeach(current)) {
				if(grid.wasVisited(grid.getB())) {
					printSolution(current);
					break;
				}
			}
			
			for(int[] move : MOVES) {
				Location l = new Location(current.getX() + move[0], current.getY() + move[1], current);
				
				next.add(l);
			}
			grid.setVisited(current, true);
		}
	}
	
	/*
	 * This function finds the direction from the prev location to the current 
	 * location using the private findDirection method. Then it reverses the 
	 * string list since it's technically backwards and just prints it out.
	 * @param current is the last location, which should always be the location
	 *  of p if the level doesn't have any errors.
	 */
	private void printSolution(Location current) {
		Location iter = current;
		List<String> path = new ArrayList<String>();
		while(iter != null && iter.prev != null) {
			path.add(findDirection(iter, iter.prev));
			iter = iter.prev;
		}
		
		Collections.reverse(path);
		StringBuilder solution = new StringBuilder();
		for(String s : path) solution.append(s).append(", ");
		solution.setLength(solution.length()-2);
		
		System.out.println("The solution to the given grid is: " + solution.toString());
	}
	
	//DIRECTIONS = {"RIGHT", "UP", "LEFT", "DOWN"}
	private String findDirection(Location a, Location b) {
		if(a == null || b == null) return "";
		if(a.getX() - b.getX() == 0 && a.getY() - b.getY() > 0) return DIRECTIONS[0];
		else if(a.getX() - b.getX() < 0 && a.getY() - b.getY() == 0) return DIRECTIONS[1];
		else if(a.getX() - b.getX() == 0 && a.getY() - b.getY() < 0) return DIRECTIONS[2];
		else if(a.getX() - b.getX() > 0 && a.getY() - b.getY() == 0) return DIRECTIONS[3];
		else return "UNKOWN";
	}
}