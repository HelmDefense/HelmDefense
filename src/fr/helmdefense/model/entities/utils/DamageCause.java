package fr.helmdefense.model.entities.utils;

import fr.helmdefense.model.entities.living.LivingEntity;

public interface DamageCause {
	public void attack(LivingEntity victim);
}