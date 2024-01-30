package de.tu_darmstadt.informatik.fop.main.dorfromantik.animation;

/**
 * Implements a scale up ease out bounce animation.
 */
public class ScaleUpEaseOutBounceAnimation extends ScaleUpBaseAnimation {
	
	public ScaleUpEaseOutBounceAnimation(float duration) {
		super(duration);
	}

	/**
	 * Implements an ease out bounce animation.
	 */
	@Override
	public float function(float x) {
		// https://easings.net/
		float n1 = 7.5625f;
		float d1 = 2.75f;

		if (x < 1 / d1) {
		    return n1 * x * x;
		} else if (x < 2 / d1) {
		    return n1 * (x -= 1.5 / d1) * x + 0.75f;
		} else if (x < 2.5 / d1) {
		    return n1 * (x -= 2.25 / d1) * x + 0.9375f;
		} else {
		    return n1 * (x -= 2.625 / d1) * x + 0.984375f;
		}
	}
}

