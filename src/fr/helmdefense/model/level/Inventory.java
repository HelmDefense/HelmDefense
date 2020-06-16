package fr.helmdefense.model.level;

import fr.helmdefense.model.entities.living.LivingEntityType;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

public class Inventory {
	private ObservableMap<LivingEntityType, IntegerProperty> content;
	
	public Inventory() {
		this.content = FXCollections.observableHashMap();
	}
	
	public void addEntity(LivingEntityType ent, int amount) {
		if (amount <= 0)
			return;
		if (this.content.containsKey(ent))
			this.content.get(ent).set(this.content.get(ent).get() + amount);
		else
			this.content.put(ent, new SimpleIntegerProperty(amount));
	}
	
	public void addEntity(LivingEntityType ent) {
		this.addEntity(ent, 1);
	}
	
	public boolean removeEntity(LivingEntityType ent) {
		if (this.content.containsKey(ent))
			this.content.get(ent).set(this.content.get(ent).get() - 1);
		
		if (this.hasEntity(ent))
			return true;
		this.content.remove(ent);
		return false;
	}

	public boolean hasEntity(LivingEntityType ent) {
		return this.content.containsKey(ent) ? this.content.get(ent).get() > 0 : false;
	}

	public ObservableMap<LivingEntityType, IntegerProperty> getContent() {
		return this.content;
	}
	
	@Override
	public String toString() {
		return "Inventory [content=" + content + "]";
	}
	
}
