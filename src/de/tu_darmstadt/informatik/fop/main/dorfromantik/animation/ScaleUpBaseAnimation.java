package de.tu_darmstadt.informatik.fop.main.dorfromantik.animation;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

/**
 * This class defines the base animation to scale up an entity
 */
public class ScaleUpBaseAnimation extends Animation {
	
	/**
	 * Initializes the animation.
	 * @param duration the duration of the animation
	 */
	public ScaleUpBaseAnimation(float duration) {
		super("ScaleAnimation", duration);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) {
		// update progress etc.
		super.update(gc, sb, delta);

		// calculate new scale.
		this.getOwnerEntity().setScale(this.function(this.getProgress()));
		
		// set final scale.
	    // The animation may finish not at the exact scale depending on the used function.
		if (this.isComplete()) {
			this.getOwnerEntity().setScale(1.0f);
		}
	}

	@Override
	public void initAnimation() {
		super.initAnimation();
		this.getOwnerEntity().setScale(0);
	}


	@Override
	public float function(float x) {
		// default is linear
		return x;
	}

}
