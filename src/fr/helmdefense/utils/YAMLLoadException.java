package fr.helmdefense.utils;

public class YAMLLoadException extends RuntimeException {
	private static final long serialVersionUID = 3222715424530756155L;

	public YAMLLoadException() {
		super("YAML failed to load!");
	}

	public YAMLLoadException(String message) {
		super(message);
	}
}