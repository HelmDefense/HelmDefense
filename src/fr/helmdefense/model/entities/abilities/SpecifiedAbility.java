package fr.helmdefense.model.entities.abilities;

import fr.helmdefense.model.entities.utils.Tier;

public abstract class SpecifiedAbility extends Ability {
	private Tier.Specification specification;
	
	public SpecifiedAbility(Tier unlock, Tier.Specification specification) {
		super(unlock);
		this.specification = specification;
	}
	
	public Tier.Specification getSpecification() {
		return this.specification;
	}
	
	@Override
	public boolean isUnlocked(Tier actual) {
		return this.isUnlocked(actual, Tier.Specification.DEFAULT);
	}
	
	public boolean isUnlocked(Tier actual, Tier.Specification specification) {
		return this.isUnlocked(actual) && this.specification == specification;
	}
}