package de.tu_darmstadt.informatik.fop.test.dorfromantik.student.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.newdawn.slick.geom.Vector2f;

import de.tu_darmstadt.informatik.fop.main.dorfromantik.constants.Constants;
import de.tu_darmstadt.informatik.fop.main.dorfromantik.entity.Tile;
import de.tu_darmstadt.informatik.fop.test.dorfromantik.adapter.DorfromantikTestAdapterStage1;

public class PreviewTileTest {
	
	DorfromantikTestAdapterStage1  adapter;
	
	@Before
	public void setUp() {
		adapter = new DorfromantikTestAdapterStage1();
	}
	
	@After
	public void finish() {
		adapter.stopGame();
	}
	
	@Test
	public final void testRotationOfPreviewTile() {
		adapter.initializeGame();
		adapter.changeToState(Constants.Base.GameScene);
		
		Tile previewTile = adapter.getPreviewTile();
		
		assertEquals(0, previewTile.getRotation(), 0.1);
		
		adapter.handleKeyPressR();
		// TODO: runGame does not work?!
		for (int i = 0; i < 100000; i++) {
			adapter.runGame(1000);
		}
		assertEquals(90, previewTile.getRotation(), 0.1);
	}
	
	@Test
	public final void testPlacingPreviewTile() {
		adapter.initializeGame();
		adapter.changeToState(Constants.Base.GameScene);
		assertEquals("Es sollte nur das Start Tile sichtbar sein", 1, adapter.getTilesOnBoard().size());
		
		Tile previewTile = adapter.getPreviewTile();
		// Top
		adapter.handleMouseLeftClick(400, 200);

		for (Tile t : adapter.getTilesOnBoard()) {
			System.out.println("TILE: "+t);
		}
		
		Tile placedTile = adapter.getTileAtIndex(new Vector2f(250, 249));
		assertEquals(previewTile, placedTile);
		// After the tile is placed a new preview tile needs to be generated
		assertNotEquals(previewTile, adapter.getPreviewTile());
	}
}