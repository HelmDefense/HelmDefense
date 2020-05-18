package fr.helmdefense.model.level;

import java.util.function.Consumer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

public class GameLoop {
	private Timeline tl;
	
	public static final double TPS = 10;
	
	public GameLoop(Consumer<Long> action) {
		this.tl = new Timeline(new KeyFrame(Duration.seconds(1 / TPS), new Loop(action)));
		this.tl.setCycleCount(Timeline.INDEFINITE);
	}
	
	public void start() {
		this.tl.play();
	}
	
	private class Loop implements EventHandler<ActionEvent> {
		private Consumer<Long> action;
		private long ticks;
		
		public Loop(Consumer<Long> action) {
			this.action = action;
			this.ticks = 0;
		}
		
		@Override
		public void handle(ActionEvent event) {
			action.accept(++this.ticks);
		}
	}
}