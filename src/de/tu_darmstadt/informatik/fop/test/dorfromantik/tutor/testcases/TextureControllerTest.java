package de.tu_darmstadt.informatik.fop.test.dorfromantik.tutor.testcases;

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
	
	@Test
	public final void testLoadTileset() {
//		
//		if (System.getProperty("os.name").toLowerCase().contains("windows")) {
//    		System.setProperty("org.lwjgl.librarypath",System.getProperty("user.dir") + "/native/windows");
//    	} else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
//    		System.setProperty("org.lwjgl.librarypath",System.getProperty("user.dir") + "/native/macosx");
//    	} else {
//    		System.setProperty("org.lwjgl.librarypath",System.getProperty("user.dir") + "/native/" +System.getProperty("os.name").toLowerCase());
//    	}
//		
//		TextureController.getInstance().setDebug(false);
//		boolean imagesLoaded = TextureController.getInstance().loadTileset("assets/Dorfromantik-Tileset.png");
//		assertTrue("assets/Dorfromantik-Tileset.png sollte erfolgreich geladen sein", imagesLoaded);
//		
//		//adapter.initializeGame();
//		
//		Image tile = TextureController.getInstance().getSprite(0, 0);
//		assertNotNull("Es sollte ein Teil Bild zurückgegeben werden", tile);
//		assertEquals("Das Teil Bild sollte die Breite wie TileSize haben", (int) Constants.GamePlay.tileSize.x, tile.getWidth());
//		assertEquals("Das Teil Bild sollte die Höhe wie TileSize haben", (int) Constants.GamePlay.tileSize.y, tile.getHeight());
//		
	}
	
}
