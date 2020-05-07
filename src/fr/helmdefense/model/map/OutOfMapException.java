package fr.helmdefense.model.map;

public class OutOfMapException extends RuntimeException {
	private static final long serialVersionUID = -2318317737117442443L;
	
	public OutOfMapException() {
		super("Out of map!");
	}
	
	public OutOfMapException(String message) {
		super(message);
	}
}