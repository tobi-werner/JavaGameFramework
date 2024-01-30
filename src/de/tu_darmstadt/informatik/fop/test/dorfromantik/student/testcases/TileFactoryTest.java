package de.tu_darmstadt.informatik.fop.test.dorfromantik.student.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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
}
