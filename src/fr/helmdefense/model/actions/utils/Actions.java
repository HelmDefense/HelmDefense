package fr.helmdefense.model.actions.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.helmdefense.model.actions.Action;
import fr.helmdefense.model.actions.ActionHandler;
import fr.helmdefense.model.actions.ActionListener;

/**
 * Util that provides methods to (un)register listeners
 * and trigger actions.
 * 
 * @author	indyteo
 * @see		Action
 * @see		ActionHandler
 * @see		AcionListener
 */
public class Actions {
	private static List<ActionListener> registeredListeners = new ArrayList<ActionListener>();
	
	private Actions() {}
	
	/**
	 * Trigger an action in the given listeners.
	 * 
	 * @param action
	 * 			The action to trigger.
	 * @param listeners
	 * 			The listeners that will handle action.
	 */
	public static void trigger(Action action, List<? extends ActionListener> listeners) {
		List<ActionListener> list = new ArrayList<ActionListener>(listeners);
		for (ActionListener a : list) {
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
	
	/**
	 * Trigger an action in all registered listeners.
	 * 
	 * @param action
	 * 			The action to trigger.
	 * @see		Actions#registerListeners(ActionListener...)
	 * @see		Actions#trigger(Action, List)
	 */
	public static void trigger(Action action) {
		trigger(action, registeredListeners);
	}
	
	/**
	 * Register the given listeners.
	 * 
	 * <p>Note: The registered listeners are used
	 * when {@link Actions#trigger(Action)} is call.
	 * 
	 * @param listeners
	 * 			The listeners to register.
	 */
	public static void registerListeners(ActionListener... listeners) {
		registerListeners(Arrays.asList(listeners));
	}
	
	/**
	 * Register the given listeners.
	 * 
	 * <p>Note: The registered listeners are used
	 * when {@link Actions#trigger(Action)} is call.
	 * 
	 * @param listeners
	 * 			The listeners to register.
	 */
	public static void registerListeners(List<? extends ActionListener> listeners) {
		registeredListeners.addAll(listeners);
	}
	
	/**
	 * Unregister the given listeners.
	 * 
	 * <p>Note: The registered listeners are used
	 * when {@link Actions#trigger(Action)} is call.
	 * 
	 * @param listeners
	 * 			The listeners to unregister.
	 */
	public static void unregisterListeners(ActionListener... listeners) {
		unregisterListeners(Arrays.asList(listeners));
	}
	
	/**
	 * Unregister the given listeners.
	 * 
	 * <p>Note: The registered listeners are used
	 * when {@link Actions#trigger(Action)} is call.
	 * 
	 * @param listeners
	 * 			The listeners to unregister.
	 */
	public static void unregisterListeners(List<? extends ActionListener> listeners) {
		registeredListeners.removeAll(listeners);
	}
}