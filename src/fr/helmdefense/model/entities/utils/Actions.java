package fr.helmdefense.model.entities.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import fr.helmdefense.model.entities.abilities.Ability;
import fr.helmdefense.model.entities.abilities.AbilityAction;
import fr.helmdefense.model.entities.abilities.actions.Action;

public class Actions {
	private Actions() {}
	
	public static void trigger(Action action, List<Ability> abilities) {
		for (Ability a : abilities) {
			Method[] actions = a.getClass().getMethods();
			
			for (Method m : actions) {
				if (m.isAnnotationPresent(AbilityAction.class)
						&& m.getParameterCount() == 1
						&& m.getParameterTypes()[0] == action.getClass()) {
					try {
						m.invoke(a, action);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}