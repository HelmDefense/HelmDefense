package fr.helmdefense.model.level;

import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.map.GameMap;
import fr.helmdefense.utils.YAMLLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Level {
	private GameMap map;
	private ObservableList<Entity> entities;
	
	public Level(GameMap map) {
		this.map = map;
		this.entities = FXCollections.observableArrayList();
	}
	
	public GameMap getMap() {
		return this.map;
	}
	
	public ObservableList<Entity> getEntities() {
		return this.entities;
	}
	
	public static Level load(String name) {
		return YAMLLoader.loadLevel(name);
	}
}