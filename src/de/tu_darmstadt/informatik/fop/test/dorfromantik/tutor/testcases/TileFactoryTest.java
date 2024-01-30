package de.tu_darmstadt.informatik.fop.test.dorfromantik.tutor.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.tu_darmstadt.informatik.fop.main.dorfromantik.constants.Constants;
import de.tu_darmstadt.informatik.fop.main.dorfromantik.controller.TextureManager;
import de.tu_darmstadt.informatik.fop.main.dorfromantik.entity.Tile;
import de.tu_darmstadt.informatik.fop.main.dorfromantik.factory.TileFactory;

public class TileFactoryTest {

	@Test
	public final void testGenerateTileWithId() {
		TextureManager.getInstance().setDebug(true);
		TileFactory factory = new TileFactory(true);
		Tile tile = factory.generateTileWithId(0);
		assertNotNull("tile sollte nicht null sein", tile);
		assertEquals("TileId sollte Tile#0 sein", "Tile#0", tile.getID());
		
		assertNull("Es kann kein Tile mit id -1 geben", factory.generateTileWithId(-1));
		assertNull("Es gibt keine Kachel mit Textur "+Constants.Base.TileCount+1, factory.generateTileWithId(Constants.Base.TileCount+1));
	}
	
	@Test
	public final void testGenerateRandomTile() {
		TextureManager.getInstance().setDebug(true);
		TileFactory factory = new TileFactory(true);
		Tile tile = factory.generateRandomTile(-1, -1);
		// Generiere 100 zufällige Tiles. Die Ids müssen zwischen 0 und 47 liegen.
		for (int i = 0; i < 100; i++) {
			int tileId = Integer.valueOf(tile.getID().split("#")[1]);
			assertTrue(tileId >= 0 && tileId <= Constants.Base.TileCount);
		}
		
	}
}
