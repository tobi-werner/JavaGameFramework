package de.tu_darmstadt.informatik.fop.main.dorfromantik.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import de.tu_darmstadt.informatik.fop.main.dorfromantik.animation.Animation;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;

/**
 * Extends the entity class to include texture and animation methods.
 */
public class BaseEntity extends Entity {
	
	/**
	 * Component which renders the texture of the entity.
	 */
	private ImageRenderComponent component;
	/**
	 * Image which holds the texture of the entity.
	 */
	private Image texture;
	/**
	 * display texture?
	 */
	private boolean debug;
	
	/**
	 * List of current running animations on this entity.
	 */
	private List<Animation> currentRunningAnimations;
	
	public BaseEntity(String entityID, boolean debug) {
		super(entityID);
		this.debug = debug;
		
		this.currentRunningAnimations = new ArrayList<>();
	}

	/*** ANIMATIONS ***/
	/**
	 * runs the given animation if no other animation of the same type runs currently.
	 * @param animation the animation.
	 * @return true if animation can be run.
	 */
	public boolean runAnimation(Animation animation) {
		// allow only one animation of one type at the time
		animation.setOwnerEntity(this);
		boolean animationOfTypeExists = this.currentRunningAnimations.stream().anyMatch(a -> a.getId() == animation.getId());
		if (animationOfTypeExists) return false;
		animation.initAnimation();
		this.currentRunningAnimations.add(animation);
		return true;
	}
	
	/**
	 * update all animations.
	 */
	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) {
		super.update(gc, sb, delta);

		for (Iterator<Animation> iterator = this.currentRunningAnimations.iterator(); iterator.hasNext(); ) {
		    Animation animation = iterator.next();
		    animation.update(gc, sb, delta);
		    if (animation.isComplete()) {
				iterator.remove();
			}
		}
	}
	
	/**** TEXTURES ****/
	public void setTexture(Image newTexture) {
		// dont load images
		if (debug) return;
		
		this.texture = newTexture;
		// remove component if one exists
		if (this.component != null) this.removeComponent(component);
		// update component to new image
		component = new ImageRenderComponent(newTexture);
		this.addComponent(component);
	}
	
	public Image getTexture() {
		if (texture == null) return null;
		return this.texture.copy();
	}
	
	
}
