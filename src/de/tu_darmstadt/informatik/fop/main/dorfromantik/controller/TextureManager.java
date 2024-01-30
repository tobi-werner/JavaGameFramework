package de.tu_darmstadt.informatik.fop.main.dorfromantik.controller;

import java.io.IOException;
import java.net.URL;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.util.ResourceLoader;

import de.tu_darmstadt.informatik.fop.main.dorfromantik.constants.Constants;

/**
 * Handles textures.
 */
public class TextureManager {
	
	private SpriteSheet tileset = null;
	
	/**
	 * singleton.
	 */
	private static final TextureManager controller = new TextureManager();
	
	/**
	 * should textures be displayed?
	 */
	private boolean debug = false;
	
	public void setDebug(boolean debug) {
		this.debug = debug;
	}
		
	public static TextureManager getInstance() {
		return controller;
	}
	
	private TextureManager() {
		super();
	}
	
	/**
	 * Loads an Image into the spritesheet.
	 * @param path the path of the image file.
	 * @return true if successful.
	 */
	public boolean loadTileset(String path) {
		if (debug) return false;
		
		URL u = ResourceLoader.getResource(path);
		try {
			this.tileset = new SpriteSheet(u, (int) Constants.GamePlay.tileSize.x, (int) Constants.GamePlay.tileSize.y);
			return true;
		} catch (SlickException | IOException e) {
			System.out.print("Fehler beim laden des Spritesheets");
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Return a subimage of the loaded spritesheet. Null if debug=true.
	 * @param row in the spritesheet.
	 * @param col in the spritesheet.
	 * @return Image if debug=false.
	 */
	public Image getSprite(int row, int col) {
		if (this.debug) return null;
		if (this.tileset == null) return null;
		return this.tileset.getSprite(row, col).getScaledCopy((int) Constants.GamePlay.tileSize.x, (int) Constants.GamePlay.tileSize.y);
	}
	
	
}
