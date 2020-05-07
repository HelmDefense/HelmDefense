package fr.helmdefense.model.level;

import fr.helmdefense.model.map.GameMap;
import fr.helmdefense.utils.YAMLLoader;

public class Level {
	private GameMap map;
	
	public Level(GameMap map) {
		this.map = map;
	}
	
	public GameMap getMap() {
		return this.map;
	}
	
	public static Level load(String name) {
		return YAMLLoader.loadLevel(name);
	}
}