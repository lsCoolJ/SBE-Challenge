package com.assessment.nav;

public class Location {
	int x;
	int y;
	Location prev;
	
	public Location(int x, int y) {
		this.x = x;
		this.y = y;
		this.prev = null;
	}
	
	public Location(int x, int y, Location prev) {
		this.x = x;
		this.y = y;
		this.prev = prev;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Location getPrev() {
		return prev;
	}
}
