package fr.helmdefense.model.entities.abilities;

import fr.helmdefense.model.actions.ActionListener;
import fr.helmdefense.model.entities.utils.Tier;

public abstract class Ability implements ActionListener {
	private Tier unlock;
	private Tier.Specification tierSpecification;
	
	public Ability(Tier unlock, Tier.Specification tierSpecification) {
		this.unlock = unlock;
		this.tierSpecification = tierSpecification;
	}
	
	public Tier getUnlock() {
		return this.unlock;
	}
	
	public boolean isUnlocked(Tier actual, Tier.Specification actualSpecification) {
		return this.unlock.compareTo(actual) <= 0
				&& (this.tierSpecification == Tier.Specification.DEFAULT || this.tierSpecification == actualSpecification);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [unlock=" + unlock + ", tierSpecification=" + tierSpecification + "]";
	}
}