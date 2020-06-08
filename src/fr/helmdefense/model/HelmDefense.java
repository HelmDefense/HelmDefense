package fr.helmdefense.model;

import java.util.HashMap;
import java.util.Map;

import fr.helmdefense.model.entities.living.LivingEntityType;
import fr.helmdefense.model.entities.living.special.Hero;
import fr.helmdefense.model.map.GameMap;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;

public class HelmDefense {
	private ReadOnlyIntegerWrapper starsProperty;
	private Map<LivingEntityType, Hero> heroes;
	
	public HelmDefense() {
		this.starsProperty = new ReadOnlyIntegerWrapper(7);
		this.heroes = new HashMap<LivingEntityType, Hero>();
		for (LivingEntityType hero : LivingEntityType.HEROES)
			this.heroes.put(hero, new Hero(hero, GameMap.WIDTH / 2, GameMap.HEIGHT / 2));
	}
	
	public final int getStars() {
		return this.starsProperty.get();
	}
	
	private final void setStars(int stars) {
		this.starsProperty.set(stars);
	}
	
	public final boolean removeStar() {
		if (this.getStars() == 0)
			return false;
		this.setStars(this.getStars() - 1);
		return true;
	}
	
	public final ReadOnlyIntegerProperty starsProperty() {
		return this.starsProperty.getReadOnlyProperty();
	}
	
	public Hero getHero(LivingEntityType type) {
		return this.heroes.get(type);
	}
}