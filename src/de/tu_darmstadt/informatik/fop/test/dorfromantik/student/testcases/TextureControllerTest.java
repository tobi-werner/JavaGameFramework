package de.tu_darmstadt.informatik.fop.test.dorfromantik.student.testcases;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.newdawn.slick.Image;

import de.tu_darmstadt.informatik.fop.main.dorfromantik.controller.TextureManager;
import de.tu_darmstadt.informatik.fop.test.dorfromantik.adapter.DorfromantikTestAdapterMinimal;

public class TextureControllerTest {
	
	DorfromantikTestAdapterMinimal  adapter;
	
	@Before
	public void setUp() {
		adapter = new DorfromantikTestAdapterMinimal();
	}
	
	@After
	public void finish() {
		adapter.stopGame();
	}
	
	@Test
	public final void testDebug() {
		TextureManager.getInstance().setDebug(true);
		
		boolean imagesLoaded = TextureManager.getInstance().loadTileset("");
		assertFalse("Es sollten keine Bilder geladen werden, wenn debug=true", imagesLoaded);
		
		boolean imagesLoaded2 = TextureManager.getInstance().loadTileset("assets/Dorfromantik-Tileset.png");
		assertFalse("Es sollten keine Bilder geladen werden, wenn debug=true", imagesLoaded2);
		
		Image tile = TextureManager.getInstance().getSprite(0, 0);
		assertNull("Es sollte null zurückgegeben werden, da kein Spritesheet geladen wurde", tile);
		
	}
	
}
