package fr.helmdefense.model.level;

import fr.helmdefense.model.entities.utils.Tier;

public enum Difficulty {
	EASY(Tier.TIER_1, "Facile"),
	NORMAL(Tier.TIER_2, "Normal"),
	HARD(Tier.TIER_3, "Difficile");
	
	private Tier tier;
	private String name;
	
	public static final Difficulty DEFAULT = EASY;
	
	private Difficulty(Tier tier, String name) {
		this.tier = tier;
		this.name = name;
	}
	
	public Tier getTier() {
		return this.tier;
	}
	
	public String getName() {
		return this.name;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}