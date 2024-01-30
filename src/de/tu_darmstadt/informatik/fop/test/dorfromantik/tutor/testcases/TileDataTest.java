package de.tu_darmstadt.informatik.fop.test.dorfromantik.tutor.testcases;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import de.tu_darmstadt.informatik.fop.main.dorfromantik.controller.TextureManager;
import de.tu_darmstadt.informatik.fop.main.dorfromantik.entity.Tile;
import de.tu_darmstadt.informatik.fop.main.dorfromantik.model.TileData;

public class TileDataTest {

	Tile tile;
	
	@Before
	public void setup() {
		TextureManager.getInstance().setDebug(true);
		
		TileData data = new TileData(0, 1, 2, 3);
		tile = new Tile("", 0, 0, true);
		tile.setTileData(data);
	}
	
	@Test
	public final void testGetTileData() {
		assertEquals(0, tile.getTop());
		assertEquals(1, tile.getRight());
		assertEquals(2, tile.getBottom());
		assertEquals(3, tile.getLeft());
	}
	
	@Test
	public final void rotateTileData() {
		assertEquals(0, tile.getTop());
		assertEquals(1, tile.getRight());
		assertEquals(2, tile.getBottom());
		assertEquals(3, tile.getLeft());
		
		tile.rotateTileData();
		
		assertEquals(3, tile.getTop());
		assertEquals(0, tile.getRight());
		assertEquals(1, tile.getBottom());
		assertEquals(2, tile.getLeft());
	}
}
