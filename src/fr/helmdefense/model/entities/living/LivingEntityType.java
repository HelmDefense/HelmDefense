package fr.helmdefense.model.entities.living;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
	ARAGORN(EntitySide.DEFENDER, false),
	LEGOLAS(EntitySide.DEFENDER, false),
	GIMLI(EntitySide.DEFENDER, false),
	
	//Door
	DOOR(EntitySide.DEFENDER),
	
	/*** Attackers ***/
	// Classic
	ORC_WARRIOR(EntitySide.ATTACKER),
	ORC_BOMBER(EntitySide.ATTACKER),
	GOBLIN(EntitySide.ATTACKER),
	URUK_HAI(EntitySide.ATTACKER),
	TROLL(EntitySide.ATTACKER),
	
	// Bosses
	ISENGARD_TOWER(EntitySide.ATTACKER, false),
	MORDOR_TOWER(EntitySide.ATTACKER, false),
	GROND(EntitySide.ATTACKER, false),
	SARUMAN(EntitySide.ATTACKER, false),
	ANGMAR_WITCH_KING(EntitySide.ATTACKER, false),
	NAZGUL(EntitySide.ATTACKER, false);
	
	private EntitySide side;
	private boolean classic;
	private EntityData data;

	public static final LivingEntityType[] CLASSIC_DEFENDERS = get(EntitySide.DEFENDER, true);
	public static final LivingEntityType[] CLASSIC_ATTACKERS = get(EntitySide.ATTACKER, true);
	public static final LivingEntityType[] HEROES = get(EntitySide.DEFENDER, false);
	public static final LivingEntityType[] BOSSES = get(EntitySide.ATTACKER, false);
	
	private LivingEntityType(EntitySide side) {
		this(side, true);
	}
	
	private LivingEntityType(EntitySide side, boolean classic) {
		this.side = side;
		this.classic = classic;
	}
	
	public EntitySide getSide() {
		return this.side;
	}
	
	public boolean isClassic() {
		return this.classic;
	}
	
	@Override
	public void setData(EntityData data) {
		this.data = data;
	}

	@Override
	public EntityData getData() {
		return this.data;
	}
	
	@Override
	public String toString() {
		return this.data.getName();
	}
	
	public static LivingEntityType[] get(EntitySide side) {
		List<LivingEntityType> list = Arrays.stream(values())
				.filter(type -> type.getSide() == side)
				.collect(Collectors.toList());
		return list.toArray(new LivingEntityType[list.size()]);
	}
	
	public static LivingEntityType[] get(EntitySide side, boolean classic) {
		List<LivingEntityType> list = Arrays.stream(values())
				.filter(type -> type.getSide() == side && type.isClassic() == classic)
				.collect(Collectors.toList());
		return list.toArray(new LivingEntityType[list.size()]);
	}
}