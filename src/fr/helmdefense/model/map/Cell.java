package fr.helmdefense.model.map;

import fr.helmdefense.model.entities.utils.coords.Location;

public class Cell {
	private Graph graph;
	private Location loc;
	private int paths;
	private boolean mark;
	private Cell next;
	
	public Cell(Graph graph, Location loc, int paths) {
		this.graph = graph;
		this.loc = loc;
		this.paths = paths;
		this.mark = false;
		this.next = null;
	}
	
	public Cell getUnmarkedNeighbor() {
		Cell c;
		for (Dir d : Dir.list(this.paths))
			if ((c = this.graph.getCellAt(d.n(this.loc))) != null && ! c.isMarked())
				return c;
		return null;
	}
	
	public Location getLoc() {
		return this.loc.copy();
	}
	
	public void setMarked(boolean mark) {
		this.mark = mark;
	}
	
	public boolean isMarked() {
		return this.mark;
	}
	
	public Cell getNext() {
		return this.next;
	}
	
	public void setNext(Cell c) {
		this.next = c;
	}
}