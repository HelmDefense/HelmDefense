package fr.helmdefense.model.entities.abilities;

import fr.helmdefense.model.entities.utils.Tier;

public abstract class Ability {
	private Tier unlock;
	
	public Ability(Tier unlock) {
		this.unlock = unlock;
	}
	
	public Tier getUnlock() {
		return this.unlock;
	}
	
	public boolean isUnlocked(Tier actual) {
		return this.unlock.compareTo(actual) <= 0;
	}
}