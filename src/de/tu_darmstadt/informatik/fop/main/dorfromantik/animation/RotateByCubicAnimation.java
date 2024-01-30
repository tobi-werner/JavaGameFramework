package de.tu_darmstadt.informatik.fop.main.dorfromantik.animation;

/**
 * Implements a cubic rotation function.
 */
public class RotateByCubicAnimation extends RotateByBaseAnimation {

	public RotateByCubicAnimation(float duration, float deltaRotation) {
		super(duration, deltaRotation);
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
