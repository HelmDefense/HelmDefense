package fr.helmdefense.model.entities.utils;

import java.util.List;
import java.util.Map;

import fr.helmdefense.model.actions.Action;
import fr.helmdefense.model.actions.utils.Actions;
import fr.helmdefense.model.entities.abilities.Ability;

public class EntityData {
	private String name;
	private String path;
	private Map<Tier, Statistic> stats;
	private List<? extends Ability> abilities;
	
	public EntityData(String name, String path, Map<Tier, Statistic> stats, List<? extends Ability> abilities) {
		this.name = name;
		this.path = path;
		this.stats = stats;
		this.abilities = abilities;
		Actions.registerListeners(abilities);
	}
	
	public final String getName() {
		return this.name;
	}
	
	public final String getPath() {
		return this.path;
	}
	
	public final Statistic getStats(Tier tier) {
		return this.stats.get(tier);
	}
	
	public final void triggerAbilities(Action action) {
		Actions.trigger(action, this.abilities);
	}

	@Override
	public String toString() {
		return "EntityData [name=" + name + ", path=" + path + ", stats=" + stats + ", abilities=" + abilities + "]";
	}
}