package fr.helmdefense.controller;

import javafx.scene.input.KeyCode;

public enum Controls {
	// Miscelaneous controls
	TOGGLE_PAUSE(KeyCode.ESCAPE, "Pause / Play"),
	
	// Hero controls
	HERO_UP(KeyCode.UP, "Haut (héros)"),
	HERO_DOWN(KeyCode.DOWN, "Bas (héros)"),
	HERO_LEFT(KeyCode.LEFT, "Gauche (héros)"),
	HERO_RIGHT(KeyCode.RIGHT, "Droite (héros)"),
	HERO_POWER(KeyCode.ENTER, "Activer le pouvoir (héros)"),
	
	// Default
	UNKNOWN(null, "");
	
	private KeyCode key;
	private String name;
	
	private Controls(KeyCode key, String name) {
		this.key = key;
		this.name = name;
	}
	
	public KeyCode getKey() {
		return this.key;
	}
	
	public void setKey(KeyCode key) {
		this.key = key;
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
}