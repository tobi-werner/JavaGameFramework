package de.tu_darmstadt.informatik.fop.main.dorfromantik.animation;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.component.Component;

/**
 * This class defines the base for all animation components.
 */
public abstract class Animation extends Component {

	/**
	 * The duration of the animation in seconds.
	 */
	private float duration = 0;
	
	/**
	 * The system timestamp when the animation starts in milliseconds.
	 */
	private long startTimestampMillis = 0;
	
	/**
	 * How far the animation has progressed. Ranges between 0 and 1.
	 */
	private float progress = 0;

	/**
	 * Is the animation finished.
	 */
	private boolean isComplete = false;
	
	/**
	 * Initialize Animation with duration.
	 * @param componentID the id of the animation
	 * @param duration the duration of the animation in seconds
	 */
	public Animation(String componentID, float duration) {
		super(componentID);
		this.duration = duration;
		// if the duration is invalid the animation is immediately finished.
		if (this.duration <= 0) {
			this.isComplete = true;
		}
	}
	
	/**
	 * The animation function which is used.
	 * @param x parameter
	 * @return result
	 */
	public abstract float function(float x);

	/**
	 * calculates the current state of the entity using the defined animation
	 */
	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) {
		if (this.isComplete) return;
		
		// calculate the progress
		float d = System.currentTimeMillis() - this.startTimestampMillis;
		this.progress = d/(this.duration*1000);

		// animation is finished
		if (this.progress >= 1.0f) {
			this.isComplete = true;
		}
	}
	
	/**
	 * Sets up the animation. This is used to set the starting scale or the starting position.
	 */
	public void initAnimation() {
		this.startTimestampMillis = System.currentTimeMillis();
	}
	
	/**
	 * @return True if animation is complete.
	 */
	public boolean isComplete() {
		return isComplete;
	}

	/**
	 * @return the progress of the animation.
	 */
	public float getProgress() {
		return progress;
	}

}
