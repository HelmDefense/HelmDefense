package fr.helmdefense.model.entities;

public enum EntitySide {
	ATTACKER,
	DEFENDER;
	
	public boolean isEnemy(EntitySide other) {
		return this != other;
	}
}