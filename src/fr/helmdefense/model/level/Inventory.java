package fr.helmdefense.model.level;

import fr.helmdefense.model.entities.Entity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

public class Inventory {
	
	private ObservableMap<Class<? extends Entity>, Integer> content;
	
	public Inventory() {
		this.content = FXCollections.observableHashMap();
	}
	
	public void addEntity(Class<? extends Entity> ent, int amount) {
		content.put(ent, content.getOrDefault(ent, 0) + amount);
	}
	
	public void addEntity(Class<? extends Entity> ent) {
		addEntity(ent, 1);
	}
	
	public boolean removeEntity(Class<? extends Entity> ent) {
		if(hasEntity(ent)) {
			content.put(ent, content.getOrDefault(ent, 0) - 1);
			return true;
		}
		return false;
	}

	public boolean hasEntity(Class<? extends Entity> ent) {
		return content.getOrDefault(ent, 0) > 0;
	}

	public ObservableMap<Class<? extends Entity>, Integer> getContent() {
		return content;
	}
	
	
}
