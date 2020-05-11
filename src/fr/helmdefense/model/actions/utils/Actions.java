package fr.helmdefense.model.actions.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.helmdefense.model.actions.Action;
import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.ActionListener;

public class Actions {
	private static List<ActionListener> registeredListeners = new ArrayList<ActionListener>();
	
	private Actions() {}
	
	public static void trigger(Action action, List<ActionListener> listeners) {
		for (ActionListener a : listeners) {
			Method[] actions = a.getClass().getMethods();
			
			for (Method m : actions) {
				if (m.isAnnotationPresent(ActionHandler.class)
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
	
	public static void trigger(Action action) {
		trigger(action, registeredListeners);
	}
	
	public static void registerListener(ActionListener... listeners) {
		registeredListeners.addAll(Arrays.asList(listeners));
	}
}