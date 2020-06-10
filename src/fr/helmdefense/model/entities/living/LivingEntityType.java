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
	ARAGORN(EntitySide.DEFENDER, SubType.HERO),
	LEGOLAS(EntitySide.DEFENDER, SubType.HERO),
	GIMLI(EntitySide.DEFENDER, SubType.HERO),
	
	//Door
	DOOR(EntitySide.DEFENDER, SubType.SPECIAL),
	
	/*** Attackers ***/
	// Classic
	ORC_WARRIOR(EntitySide.ATTACKER),
	ORC_BOMBER(EntitySide.ATTACKER),
	GOBLIN(EntitySide.ATTACKER),
	URUK_HAI(EntitySide.ATTACKER),
	TROLL(EntitySide.ATTACKER),
	
	// Bosses
	ISENGARD_TOWER(EntitySide.ATTACKER, SubType.BOSS),
	MORDOR_TOWER(EntitySide.ATTACKER, SubType.BOSS),
	GROND(EntitySide.ATTACKER, SubType.BOSS),
	SARUMAN(EntitySide.ATTACKER, SubType.BOSS),
	ANGMAR_WITCH_KING(EntitySide.ATTACKER, SubType.BOSS),
	NAZGUL(EntitySide.ATTACKER, SubType.BOSS);
	
	private EntitySide side;
	private SubType subType;
	private EntityData data;

	public static final LivingEntityType[] CLASSIC_DEFENDERS = get(EntitySide.DEFENDER, SubType.CLASSIC);
	public static final LivingEntityType[] CLASSIC_ATTACKERS = get(EntitySide.ATTACKER, SubType.CLASSIC);
	public static final LivingEntityType[] HEROES = get(EntitySide.DEFENDER, SubType.HERO);
	public static final LivingEntityType[] BOSSES = get(EntitySide.ATTACKER, SubType.BOSS);
	
	private LivingEntityType(EntitySide side) {
		this(side, SubType.CLASSIC);
	}
	
	private LivingEntityType(EntitySide side, SubType subType) {
		this.side = side;
		this.subType = subType;
	}
	
	public EntitySide getSide() {
		return this.side;
	}
	
	public SubType getSubType() {
		return this.subType;
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
	
	public static LivingEntityType[] get(EntitySide side, SubType subType) {
		List<LivingEntityType> list = Arrays.stream(values())
				.filter(type -> type.getSide() == side && type.getSubType() == subType)
				.collect(Collectors.toList());
		return list.toArray(new LivingEntityType[list.size()]);
	}
	
	public enum SubType {
		CLASSIC,
		HERO,
		BOSS,
		SPECIAL;
	}
}