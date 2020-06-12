package fr.helmdefense.utils.yaml;

public class YAMLException extends RuntimeException {
	private static final long serialVersionUID = 3222715424530756155L;

	public YAMLException() {
		super("YAML failed to load/write!");
	}

	public YAMLException(String message) {
		super(message);
	}
	
	public YAMLException(Throwable cause) {
		super("YAML failed to load/write!", cause);
	}
	
	public YAMLException(String message, Throwable cause) {
		super(message, cause);
	}
}