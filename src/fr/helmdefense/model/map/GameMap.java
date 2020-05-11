package fr.helmdefense.model.map;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import fr.helmdefense.model.entities.utils.Location;

public class GameMap {
	private int[][] tiles;
	private List<Location> spawns;
	private List<Location> targets;
	
	public static final int WIDTH = 16;
	public static final int HEIGHT = 11;
	
	public GameMap(int[][] tiles, List<Location> spawns, List<Location> targets) {
		this.tiles = tiles;
		this.spawns = spawns;
		this.targets = targets;
	}
	
	public int getTile(int x, int y) {
		if (x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT)
			throw new OutOfMapException();
		return this.tiles[y][x];
	}
	
	public Location getSpawn() {
		return this.spawns.get(new Random().nextInt(this.spawns.size()));
	}
	
	public List<Location> getTargets() {
		return this.targets;
	}

	@Override
	public String toString() {
		return "GameMap [tiles=" + Arrays.deepToString(tiles) + ", spawns=" + spawns + ", targets=" + targets + "]";
	}
}