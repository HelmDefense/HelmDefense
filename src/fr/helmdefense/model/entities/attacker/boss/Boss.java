package fr.helmdefense.model.entities.attacker.boss;

import fr.helmdefense.model.entities.Entity;

public class Boss extends Entity {

	public Boss(int x, int y, int hp, int dmg, double mvtSpd, double atkSpd, double atkRange, double shootRange) {
		super(x, y, hp, dmg, mvtSpd, atkSpd, atkRange, shootRange);
	}

}