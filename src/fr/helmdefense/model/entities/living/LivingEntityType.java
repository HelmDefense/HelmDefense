package fr.helmdefense.model.entities.living;

import fr.helmdefense.model.entities.EntitySide;
import fr.helmdefense.model.entities.EntityType;
import fr.helmdefense.model.entities.utils.EntityData;

public enum LivingEntityType implements EntityType {
	/*** Defenders ***/
	// Classic
	HUMAN_WARRIOR(EntitySide.DEFENDER),
	ARCHER(EntitySide.DEFENDER),
	ELVEN(EntitySide.DEFENDER),
	CATAPULT(EntitySide.DEFENDER),
	
	// Heroes
	ARAGORN(EntitySide.DEFENDER),
	LEGOLAS(EntitySide.DEFENDER),
	GIMLI(EntitySide.DEFENDER),
	
	/*** Attackers ***/
	// Classic
	ORC_WARRIOR(EntitySide.ATTACKER),
	ORC_BOMBER(EntitySide.ATTACKER),
	GOBLIN(EntitySide.ATTACKER),
	URUK_HAI(EntitySide.ATTACKER),
	TROLL(EntitySide.ATTACKER),
	
	// Bosses
	ISENGARD_TOWER(EntitySide.ATTACKER),
	MORDOR_TOWER(EntitySide.ATTACKER),
	GROND(EntitySide.ATTACKER),
	SARUMAN(EntitySide.ATTACKER),
	ANGMAR_WITCH_KING(EntitySide.ATTACKER),
	NAZGUL(EntitySide.ATTACKER);
	
	private EntitySide side;
	private EntityData data;
	
	private LivingEntityType(EntitySide side) {
		this.side = side;
	}
	
	public EntitySide getSide() {
		return this.side;
	}
	
	@Override
	public void setData(EntityData data) {
		this.data = data;
	}

	@Override
	public EntityData getData() {
		return this.data;
	}
}