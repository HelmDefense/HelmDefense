package fr.helmdefense.model;

import java.util.HashMap;
import java.util.Map;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.ActionListener;
import fr.helmdefense.model.actions.game.GameWinAction;
import fr.helmdefense.model.actions.utils.Actions;
import fr.helmdefense.model.entities.living.LivingEntityType;
import fr.helmdefense.model.entities.living.special.Hero;
import fr.helmdefense.model.level.Level;
import fr.helmdefense.model.map.GameMap;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;

public class HelmDefense implements ActionListener {
	private ReadOnlyIntegerWrapper starsProperty;
	private Map<LivingEntityType, Hero> heroes;
	
	public HelmDefense() {
		this.starsProperty = new ReadOnlyIntegerWrapper();
		this.heroes = new HashMap<LivingEntityType, Hero>();
		for (LivingEntityType hero : LivingEntityType.HEROES)
			this.heroes.put(hero, new Hero(hero, GameMap.WIDTH / 2, GameMap.HEIGHT / 2));
	}
	
	public void startLevel(Level level) {
		Actions.registerListeners(this);
		level.startLoop();
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
	
	@ActionHandler
	public void onWin(GameWinAction action) {
		int stars;
		if (action.getLvl().getAliveDoors() == 0)
			stars = 1;
		else if (action.getLvl().getAliveDoors() == action.getLvl().getTotalDoors())
			stars = 3;
		else
			stars = 2;
		
		this.setStars(this.getStars() + stars);
	}
}