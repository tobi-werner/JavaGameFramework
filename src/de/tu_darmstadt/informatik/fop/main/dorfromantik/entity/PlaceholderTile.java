/**
 * 
 */
package de.tu_darmstadt.informatik.fop.main.dorfromantik.entity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import de.tu_darmstadt.informatik.fop.main.dorfromantik.controller.TextureManager;
import de.tu_darmstadt.informatik.fop.main.dorfromantik.event.MouseLeftEvent;
import eea.engine.action.Action;
import eea.engine.component.Component;
import eea.engine.event.basicevents.MouseEnteredEvent;

/**
 * This tile is used to indicate where a player can place a tile.
 */
public class PlaceholderTile extends Tile {
	
	/**
	 * 0 = Not hovering, 1 = hovering.
	 * avoid changing texture every frame.
	 */
	private int state = 0;
	/**
	 * Texture of the previewTile.
	 */
	private Image previewTileTexture;
	
	/**
	 * Constructor.
	 * @param debug if true no texture is displayed.
	 */
	public PlaceholderTile(boolean debug) {
		super("placeholder", 0, 0, debug);

		this.resetPlaceholderTileTexture();
		
		this.registerHoverEnterEvent();
		this.registerHoverLeaveEvent();
	}
	
	/**
	 * @return true if the placeholderTile is hovered by the mouse.
	 */
	public boolean isHovered() {
		return state == 1;
	}
	
	public void setPeviewTileTexture(Image newTexture) {
		this.previewTileTexture = newTexture;
	}
	
	/**
	 * Registers mouse events for hovering.
	 */
	public void registerHoverEnterEvent() {
		MouseEnteredEvent event = new MouseEnteredEvent();
    	Action changeTextureAction = new Action() {

			@Override
			public void update(GameContainer arg0, StateBasedGame arg1, int arg2, Component arg3) {
				if (state == 1) return;
				state = 1;
				// update texture and change opacity.
				Image texture = previewTileTexture;
				texture.setAlpha((float) 0.75);
				setTexture(texture);
			}
    		
    	};
    	event.addAction(changeTextureAction);
    	this.addComponent(event);
	}
	
	/**
	 * Registers mouse events for hovering.
	 */
	public void registerHoverLeaveEvent() {
		MouseLeftEvent event = new MouseLeftEvent();
    	Action resetTextureAction = new Action() {

			@Override
			public void update(GameContainer arg0, StateBasedGame arg1, int arg2, Component arg3) {
				if (state == 0) return;
				state = 0;
				resetPlaceholderTileTexture();
			}
    		
    	};
    	event.addAction(resetTextureAction);
    	this.addComponent(event);
	}
	
	/**
	 * Resets the placeholderTexture as well as the opacity.
	 */
	public void resetPlaceholderTileTexture() {
		Image texture = TextureManager.getInstance().getSprite(3, 7);
		if (texture == null) return;
		texture.setAlpha((float) 0.5);
		this.setTexture(texture);
	}

}
