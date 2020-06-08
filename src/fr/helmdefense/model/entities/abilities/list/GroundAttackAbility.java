package fr.helmdefense.model.entities.abilities.list;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.entity.EntityDirectAttackAction;
import fr.helmdefense.model.actions.entity.EntityMoveAction;
import fr.helmdefense.model.actions.entity.EntitySpawnAction;
import fr.helmdefense.model.actions.entity.living.LivingEntityDeathAction;
import fr.helmdefense.model.entities.living.LivingEntity;
import fr.helmdefense.model.entities.living.LivingEntityType;
import fr.helmdefense.model.entities.utils.Attribute;
import fr.helmdefense.model.entities.utils.AttributeModifier;
import fr.helmdefense.model.entities.utils.AttributeModifier.Operation;
import fr.helmdefense.model.entities.utils.Tier;

public class GroundAttackAbility extends AreaAttackAbility {
	private int numberOfGoblins;
	private int fireDuration;
	private double radius;
	private LivingEntity entity;
	private ArrayList<Integer> numberOfGoblinsList;
	private ArrayList<Double> radiusList;
	private List<LivingEntity> goblinsList;
	private Map<LivingEntity, Long > map;
	
	public GroundAttackAbility(Tier unlock, Tier.Specification tierSpecification, Integer fireDuration, ArrayList<Integer> numberOfGoblinsList, ArrayList<Double> radiusList) {
		super(unlock, tierSpecification);
		this.radius = -1.5d;
		this.radiusList = new ArrayList<Double>(radiusList);
		this.numberOfGoblinsList = new ArrayList<Integer>(numberOfGoblinsList);
		this.map = new HashMap<LivingEntity, Long>();
		this.fireDuration = fireDuration;
	}
	
	@ActionHandler
	private void onSpawn(EntitySpawnAction action) {
		if ( action.getEntity() instanceof LivingEntity) {
			this.entity = (LivingEntity)action.getEntity();
			int tier = this.entity.data().getTier().getNumberTier();
			this.radius = radiusList.get(tier - 1);
			this.numberOfGoblins = this.numberOfGoblinsList.get(tier - 1);
			
			for (int i = 0; i < this.numberOfGoblins; i++) 
				this.goblinsList.add(new LivingEntity(LivingEntityType.GOBLIN, action.getSpawn()));
			this.goblinsList.forEach(e -> {
				e.addFlags(LivingEntity.IMMOBILE);
				e.spawn(this.entity.getLevel());
			});
		}
	}
	
	@ActionHandler
	public void onMove(EntityMoveAction action) {
		this.goblinsList.forEach(e -> e.teleport(action.getTo()));
	}
		
	@ActionHandler
	public void OnAttack(EntityDirectAttackAction action) {
		long ticks = this.entity.getLevel().getTicks();
		if ( this.entity.getModifier(this.getClass().getSimpleName()) != null)
			this.entity.getModifier(this.getClass().getSimpleName()).setStart(ticks);
		else
			this.entity.getModifiers().add(new AttributeModifier(this.getClass().getSimpleName(), Attribute.MVT_SPD, Operation.MULTIPLY, -1, ticks, 5));
		
		LivingEntity victim = action.getVictim();
		this.areaAttackAbility(victim.getLoc(), this.entity, this.radius, this.entity.getType().getSide(), e -> {
			e.addFlags(LivingEntity.FIRE);
			map.put(e, ticks);
		}, victim);
		
		map.entrySet().removeIf(e -> e.getValue() == this.fireDuration);
	}
	
	@ActionHandler
	public void onDeath(LivingEntityDeathAction action) {
		this.goblinsList.forEach(e -> {
			e.removeFlags(LivingEntity.IMMOBILE);
		});
	}
}
