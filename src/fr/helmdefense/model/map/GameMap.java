package fr.helmdefense.model.map;

public class GameMap {
private int[][] tiles;
	
	public static final int WIDTH = 16;
	public static final int HEIGHT = 11;
	
	public GameMap(int[][] tiles) {
		this.tiles = tiles;
	}
	
	public int getTile(int x, int y) {
		if (x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT)
			throw new OutOfMapException();
		return this.tiles[y][x];
	}
}