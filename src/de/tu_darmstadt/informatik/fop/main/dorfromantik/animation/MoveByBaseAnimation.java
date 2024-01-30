package de.tu_darmstadt.informatik.fop.main.dorfromantik.animation;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

/**
 * This class defines the base animation for an entity to move by a vector.
 */
public class MoveByBaseAnimation extends Animation {

	/**
	 * The starting position of the entity the animation runs on.
	 */
	private Vector2f oldPosition;
	/**
	 * the movement vector.
	 */
	private Vector2f deltaMovement;
	
	/**
	 * Initializes the animation.
	 * @param duration the duration of the animation
	 * @param deltaMovement the movement vector
	 */
	public MoveByBaseAnimation(float duration, Vector2f deltaMovement) {
		super("MoveAnimation", duration);
		this.deltaMovement = deltaMovement;
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) {
		// update progress etc.
		super.update(gc, sb, delta);
		
		// calculate movement in this update call
		float movementX = this.deltaMovement.x*this.function(getProgress());
		float movementY = this.deltaMovement.y*this.function(getProgress());

		// update position of entity to the new position
		Vector2f position = new Vector2f(this.oldPosition.x+movementX, this.oldPosition.y+movementY);
	    this.getOwnerEntity().setPosition(position);
	    
	    // set final position.
	    // The animation may finish not at the exact position depending on the used function
	    if (this.isComplete()) {
	    	Vector2f finalPosition = new Vector2f(oldPosition.x+this.deltaMovement.x, oldPosition.y+this.deltaMovement.y);
			this.getOwnerEntity().setPosition(finalPosition);
	    }
		
	}
	
	@Override
	public void initAnimation() {
		super.initAnimation();
		this.oldPosition = this.getOwnerEntity().getPosition();
	}


	@Override
	public float function(float x) {
		// Default animation is linear.
		return x;
	}
	
	/**
	 * sets the delta movement by which the entity is moved.
	 * @param newDeltaMovement delta movement
	 */
	public void setDeltaMovement(Vector2f newDeltaMovement) {
		this.deltaMovement = newDeltaMovement;
	}
	
}
