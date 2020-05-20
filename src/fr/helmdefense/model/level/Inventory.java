package fr.helmdefense.model.level;

import fr.helmdefense.model.entities.Entity;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

public class Inventory {
	private ObservableMap<Class<? extends Entity>, IntegerProperty> content;
	
	public Inventory() {
		this.content = FXCollections.observableHashMap();
	}
	
	public void addEntity(Class<? extends Entity> ent, int amount) {
		if (this.content.containsKey(ent))
			this.content.get(ent).set(this.content.get(ent).get() + amount);
		else
			this.content.put(ent, new SimpleIntegerProperty(amount));
	}
	
	public void addEntity(Class<? extends Entity> ent) {
		this.addEntity(ent, 1);
	}
	
	public boolean removeEntity(Class<? extends Entity> ent) {
		if (this.content.containsKey(ent))
			this.content.get(ent).set(this.content.get(ent).get() - 1);
		
		if (this.hasEntity(ent))
			return true;
		this.content.remove(ent);
		return false;
	}

	public boolean hasEntity(Class<? extends Entity> ent) {
		return this.content.containsKey(ent) ? this.content.get(ent).get() > 0 : false;
	}

	public ObservableMap<Class<? extends Entity>, IntegerProperty> getContent() {
		return this.content;
	}
	
	@Override
	public String toString() {
		return "Inventory [content=" + content + "]";
	}
	
}
