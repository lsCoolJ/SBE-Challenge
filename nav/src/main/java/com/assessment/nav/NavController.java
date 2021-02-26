package com.assessment.nav;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import javax.management.openmbean.CompositeType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class NavController implements CommandLineRunner {
	private static final int[][] MOVES = { {0,1}, {1,0}, {0,-1}, {-1,0} };
	private static Logger LOG = LoggerFactory.getLogger(NavController.class);
	
//	@Override //Run using command line as input
//	public void run(String... args) throws Exception {
//		Scanner scanner = new Scanner(System.in);
//		System.out.println("Enter grid size X by X: ");
//		int gridSize = scanner.nextInt();
//		System.out.println("Next, enter the " + gridSize + " lines constructing the grid: ");
//		List<String> grid = new ArrayList<String>();
//		for(int i = 0; i <= gridSize; i++) {
//			String curLine = scanner.nextLine();
//			if(curLine.length() > gridSize) System.out.println("Error in grid size.");
//			grid.add(curLine);
//		}
//		
//		scanner.close();
//		
//		System.out.println("Scanner closed and got all the strings. Here they are: ");
//		for(String s : grid) System.out.println(s);
//	}
	
	@Override //Run using txt file as input
	public void run(String... args) throws FileNotFoundException {
		File level = new File("src/main/resources/levels/lvlone.txt");
		String fileText = "";
		try (Scanner input = new Scanner(level)) {
			while(input.hasNextLine()) {
				fileText += input.nextLine() + "\n";
			}
		} catch(FileNotFoundException fnfe) {}
		
		
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
			
			if(grid.isMario(current)) continue;
			else if(grid.isBowser(current)) grid.clearVisited();
			else if(grid.isPeach(current)) {
				if(grid.wasVisited(grid.getB())) {
					printSolution(current);
					break;
				}
			}
			
			for(int[] move : MOVES) {
				Location l = new Location(current.getX() + move[0], current.getY() + move[1], current);
				next.add(l);
				grid.setVisited(current, true);
			}
		}
	}
	
	public void printSolution(Location current) {
		Location iter = current;
		
		while(iter != null) {
			
			iter = iter.prev;
		}
	}
}