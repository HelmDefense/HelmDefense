package fr.helmdefense.model.level;

import java.util.function.Consumer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

public class GameLoop {
	private Timeline tl;
	private Loop loop;
	
	public static final double TPS = 10;
	
	public GameLoop(Consumer<Long> action) {
		this.loop = new Loop(action);
		this.tl = new Timeline(new KeyFrame(Duration.seconds(1 / TPS), this.loop));
		this.tl.setCycleCount(Timeline.INDEFINITE);
	}
	
	public long getTicks() {
		return this.loop.getTicks();
	}
	
	public boolean isPaused() {
		return this.loop.isPaused();
	}
	
	public void pause() {
		this.loop.setPaused(true);
	}
	
	public void resume() {
		this.loop.setPaused(false);
	}
	
	public void togglePause() {
		this.loop.setPaused(! this.loop.isPaused());
	}
	
	public void start() {
		this.tl.play();
	}
	
	public void stop() {
		this.tl.stop();
	}
	
	private class Loop implements EventHandler<ActionEvent> {
		private Consumer<Long> action;
		private long ticks;
		private boolean paused;
		
		public Loop(Consumer<Long> action) {
			this.action = action;
			this.ticks = 0;
			this.paused = false;
		}
		
		public long getTicks() {
			return this.ticks;
		}
		
		public boolean isPaused() {
			return this.paused;
		}
		
		public void setPaused(boolean paused) {
			this.paused = paused;
		}
		
		@Override
		public void handle(ActionEvent event) {
			if (! this.isPaused())
				this.action.accept(++this.ticks);
		}
	}
}