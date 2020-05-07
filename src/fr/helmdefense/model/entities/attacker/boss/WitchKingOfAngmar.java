package fr.helmdefense.model.entities.attacker.boss;

import fr.helmdefense.model.entities.Entity;

public class WitchKingOfAngmar extends Entity {
	private int nazgulHp;
	public WitchKingOfAngmar(int x, int y) {
		super(x, y, 75000, 500, 256, 2, 128, 640);
		this.nazgulHp = 20000;
	}

}
