package fr.helmdefense.model.level;

import java.util.List;

import fr.helmdefense.model.actions.entity.EntitySpawnAction;
import fr.helmdefense.model.actions.game.GameTickAction;
import fr.helmdefense.model.actions.utils.Actions;
import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.map.GameMap;
import fr.helmdefense.utils.YAMLLoader;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class Level {
	private GameMap map;
	private ObservableList<Entity> entities;
	private List<Wave> waves;
	private GameLoop gameloop;
	private Inventory inv;
	
	public Level(GameMap map, List<Wave> waves) {
		this.map = map;
		this.entities = FXCollections.observableArrayList();
		ListChangeListener<Entity> lcl = c -> {
			while (c.next())
				if (c.wasAdded())
					for (Entity e : c.getAddedSubList())
						e.triggerAbilities(new EntitySpawnAction(e));
		};
		this.entities.addListener(lcl);
		this.waves = waves;
		this.gameloop = new GameLoop(ticks -> {
			Actions.trigger(new GameTickAction(this, ticks));
		});
		this.inv = new Inventory();
	}
	
	public void startLoop() {
		this.gameloop.start();
	}
	
	public GameMap getMap() {
		return this.map;
	}
	
	public ObservableList<Entity> getEntities() {
		return this.entities;
	}
	
	public List<Wave> getWaves() {
		return this.waves;
	}
	
	public Inventory getInv() {
		return this.inv;
	}
	
	@Override
	public String toString() {
		return "Level [map=" + map + ", entities=" + entities + ", waves=" + waves + ", gameloop=" + gameloop + ", inv="
				+ inv + "]";
	}

	public static Level load(String name) {
		return YAMLLoader.loadLevel(name);
	}
}