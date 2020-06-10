package fr.helmdefense.model.map;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fr.helmdefense.model.entities.utils.coords.Location;

public class Graph {
	private Set<Cell> cells;
	private Cell start;
	
	public Graph(GameMap map, Location start) {
		this.cells = new HashSet<Cell>();
		for (int y = 0; y < GameMap.HEIGHT; y++)
			for (int x = 0; x < GameMap.WIDTH; x++)
				this.cells.add(new Cell(this, new Location(x, y), map.getTile(x, y)));
		this.start = this.getCellAt(start);
	}
	
	public void reset() {
		for (Cell cell : this.cells)
			cell.setMarked(false);
	}
	
	public Cell getCellAt(Location loc) {
		return this.cells.stream()
				.filter(cell -> cell.getLoc().equalsCenter(loc))
				.findAny()
				.orElse(null);
	}
	
	public Cell getStart() {
		return this.start;
	}
	
	public void bfs() {
		this.reset();
		List<Cell> queue = new ArrayList<Cell>();
		Cell c, d;
		this.start.setMarked(true);
		queue.add(this.start);
		
		while (! queue.isEmpty()) {
			c = queue.remove(0);
			while ((d = c.getUnmarkedNeighbor()) != null) {
				d.setMarked(true);
				d.setNext(c);
				queue.add(d);
			}
		}
	}
}