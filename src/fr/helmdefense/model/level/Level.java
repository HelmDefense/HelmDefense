package fr.helmdefense.model.level;

import java.util.List;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.ActionListener;
import fr.helmdefense.model.actions.entity.EntitySpawnAction;
import fr.helmdefense.model.actions.game.GameNewWaveAction;
import fr.helmdefense.model.actions.game.GameTickAction;
import fr.helmdefense.model.actions.utils.Actions;
import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.EntitySide;
import fr.helmdefense.model.entities.living.LivingEntityType;
import fr.helmdefense.model.map.GameMap;
import fr.helmdefense.utils.YAMLLoader;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class Level implements ActionListener {
	private String name;
	private GameMap map;
	private ObservableList<Entity> entities;
	private List<Wave> waves;
	private int currentWave;
	private GameLoop gameloop;
	private Inventory inv;
	private ReadOnlyIntegerWrapper purseProperty;
	private Difficulty difficulty;
	
	public Level(String name, GameMap map, List<Wave> waves, int startMoney) {
		this.name = name;
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
		this.currentWave = -1;
		this.gameloop = new GameLoop(ticks -> {
			Actions.trigger(new GameTickAction(this, ticks));
		});
		this.inv = new Inventory();
		this.purseProperty = new ReadOnlyIntegerWrapper(startMoney);
		this.setDifficulty(Difficulty.DEFAULT);
	}
	
	public void startLoop() {
		this.gameloop.start();
		
		Actions.registerListeners(this);
	}
	
	@ActionHandler
	public void onTick(GameTickAction action) {
		if (this.currentWave != -1) {
			if (! this.waves.get(this.currentWave).isEnded())
				return;
		}
		else {
			if (action.getTicks() == Wave.TICKS_BEFORE_FIRST_WAVE)
				this.startWave(null, this.waves.get(0));
			return;
		}
		
		if (action.getTicks() - this.waves.get(this.currentWave).getEndTick() == Wave.TICKS_BETWEEN_EACH_WAVE) {
			this.startWave(this.waves.get(this.currentWave), this.currentWave + 1 < this.waves.size() ? this.waves.get(this.currentWave + 1) : null);
		}
	}
	
	private void startWave(Wave o, Wave n) {
		GameNewWaveAction wave = new GameNewWaveAction(this, o, n);
		
		if (n != null) {
			n.start(this);
			this.currentWave++;
		}
		else {
			Actions.unregisterListeners(this);
		}
		
		Actions.trigger(wave);
	}
	
	public String getName() {
		return this.name;
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
	
	public boolean overdrawn() {
		return this.getPurse() < 0;
	}
	
	public int getPurse() {
		return this.purseProperty.get();
	}
	
	/**
	 * @return	false if overdrawn, true if money
	 * 			have been debited successfully
	 */
	public boolean debit(int value) {
		if (this.getPurse() - value < 0 || value < 0)
			return false;
		this.purseProperty.setValue(this.getPurse() - value);
		return true;
	}
	
	public void earnCoins(int value) {
		if (value < 0)
			return;
		this.purseProperty.setValue(this.getPurse() + value);
	}
	
	public ReadOnlyIntegerProperty purseProperty() {
		return this.purseProperty.getReadOnlyProperty();
	}
	
	public Difficulty getDifficulty() {
		return this.difficulty;
	}
	
	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty == null ? Difficulty.DEFAULT : difficulty;
		for (LivingEntityType type : LivingEntityType.values())
			if (type.getSide() == EntitySide.ATTACKER)
				type.getData().setTier(this.difficulty.getTier());
	}
	
	@Override
	public String toString() {
		return "Level [name=" + name + ", map=" + map + ", entities=" + entities + ", waves=" + waves + ", gameloop="
				+ gameloop + ", inv=" + inv + ", purseProperty=" + purseProperty + "]";
	}

	public static Level load(String name) {
		return YAMLLoader.loadLevel(name);
	}
}