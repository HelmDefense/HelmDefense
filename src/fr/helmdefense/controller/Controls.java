package fr.helmdefense.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.input.KeyCode;

public enum Controls {
	// Miscelaneous controls
	TOGGLE_PAUSE(ControlsGroup.MISCELANEOUS, KeyCode.ESCAPE, "Pause / Play"),
	
	// Hero controls
	HERO_UP(ControlsGroup.HERO, KeyCode.UP, "Haut (héros)"),
	HERO_DOWN(ControlsGroup.HERO, KeyCode.DOWN, "Bas (héros)"),
	HERO_LEFT(ControlsGroup.HERO, KeyCode.LEFT, "Gauche (héros)"),
	HERO_RIGHT(ControlsGroup.HERO, KeyCode.RIGHT, "Droite (héros)"),
	HERO_POWER(ControlsGroup.HERO, KeyCode.ENTER, "Activer le pouvoir (héros)"),
	
	// Default
	UNKNOWN(null, null, "");
	
	private ControlsGroup group;
	private ObjectProperty<KeyCode> keyProperty;
	private String name;
	
	private Controls(ControlsGroup group, KeyCode key, String name) {
		this.group = group;
		this.keyProperty = new SimpleObjectProperty<KeyCode>(key);
		this.name = name;
	}
	
	public KeyCode getKey() {
		return this.keyProperty.get();
	}
	
	public void setKey(KeyCode key) {
		this.keyProperty.set(key);
	}
	
	public ObjectProperty<KeyCode> keyProperty() {
		return this.keyProperty;
	}
	
	public ControlsGroup getGroup() {
		return this.group;
	}
	
	public String getName() {
		return this.name;
	}
	
	public static Controls fromKeyCode(KeyCode key) {
		for (Controls control : values())
			if (control.getKey() == key)
				return control;
		return UNKNOWN;
	}
	
	public static Controls[] inGroup(ControlsGroup group) {
		List<Controls> controls = Arrays.stream(values())
				.filter(control -> control.getGroup() == group)
				.collect(Collectors.toList());
		return controls.toArray(new Controls[controls.size()]);
	}
	
	public enum ControlsGroup {
		MISCELANEOUS("Contrôles divers"),
		HERO("Contrôles du héros");
		
		private String name;
		
		private ControlsGroup(String name) {
			this.name = name;
		}
		
		public String getName() {
			return this.name;
		}
	}
}