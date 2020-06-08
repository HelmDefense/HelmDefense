package fr.helmdefense.model.level;

import java.util.function.Consumer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
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
	
	public boolean isPlaying() {
		return this.loop.isPlaying();
	}
	
	public void pause() {
		this.loop.setPlaying(false);
	}
	
	public void resume() {
		this.loop.setPlaying(true);
	}
	
	public void togglePause() {
		this.loop.setPlaying(! this.loop.isPlaying());
	}
	
	public void start() {
		this.tl.play();
	}
	
	public void stop() {
		this.tl.stop();
	}
	
	public final double getSpeedness() {
		return this.tl.getRate();
	}
	
	public final void setSpeedness(double speedness) {
		this.tl.setRate(speedness);
	}
	
	public final DoubleProperty speednessProperty() {
		return this.tl.rateProperty();
	}
	
	public void step() {
		this.loop.step();
	}
	
	private class Loop implements EventHandler<ActionEvent> {
		private Consumer<Long> action;
		private long ticks;
		private boolean playing;
		
		public Loop(Consumer<Long> action) {
			this.action = action;
			this.ticks = 0;
			this.playing = true;
		}
		
		public long getTicks() {
			return this.ticks;
		}
		
		public boolean isPlaying() {
			return this.playing;
		}
		
		public void setPlaying(boolean playing) {
			this.playing = playing;
		}
		
		@Override
		public void handle(ActionEvent event) {
			if (this.isPlaying())
				this.step();
		}
		
		public void step() {
			this.action.accept(++this.ticks);
		}
	}
}