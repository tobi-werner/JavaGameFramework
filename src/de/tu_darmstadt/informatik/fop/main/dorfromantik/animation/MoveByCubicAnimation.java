package de.tu_darmstadt.informatik.fop.main.dorfromantik.animation;

import org.newdawn.slick.geom.Vector2f;

/**
 * Implements a cubic move by animation
 */
public class MoveByCubicAnimation extends MoveByBaseAnimation {

	/**
	 * @param duration the duration of the animation
	 * @param deltaMovement the movement vector
	 */
	public MoveByCubicAnimation(float duration, Vector2f deltaMovement) {
		super(duration, deltaMovement);
	}
	
	/**
	 * Implements a cubic function.
	 */
	@Override
	public float function(float x) {
		// https://easings.net/
		return (float) (x < 0.5 ? 4 * x * x * x : 1 - Math.pow(-2 * x + 2, 3) / 2);
	}

}
