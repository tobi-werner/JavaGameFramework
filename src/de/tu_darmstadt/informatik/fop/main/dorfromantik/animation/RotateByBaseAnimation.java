package de.tu_darmstadt.informatik.fop.main.dorfromantik.animation;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

/**
 * This class defines the base animation to rotate an entity
 */
public class RotateByBaseAnimation extends Animation {

	/**
	 * the starting rotation.
	 */
	private float oldRotation;
	/**
	 * the delta rotation.
	 */
	private float deltaRotation;
	
	/**
	 * Initializes the animation.
	 * @param duration the duration of the animation
	 * @param deltaRotation the delta rotation.
	 */
	public RotateByBaseAnimation(float duration, float deltaRotation) {
		super("RotateAnimation", duration);
		this.deltaRotation = deltaRotation;
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) {
		// update progress etc.
		super.update(gc, sb, delta);
		
		// calculate rotation in this update call.
		float rotation = this.deltaRotation*this.function(getProgress());
	    this.getOwnerEntity().setRotation(this.oldRotation+rotation);
	    
	    // set final rotation.
	    // The animation may finish not at the exact rotation depending on the used function.
	    if (this.isComplete()) {
	    	float finalRotation = this.oldRotation+this.deltaRotation;
			this.getOwnerEntity().setRotation(finalRotation);
	    }
	    
	}
	
	@Override
	public void initAnimation() {
		super.initAnimation();
		this.oldRotation = this.getOwnerEntity().getRotation();
	}

	@Override
	public float function(float x) {
		// Default animation is linear.
		return x;
	}

}
