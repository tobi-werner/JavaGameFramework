package de.tu_darmstadt.informatik.fop.test.dorfromantik.tutor.testcases;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.tu_darmstadt.informatik.fop.main.dorfromantik.constants.Constants;
import de.tu_darmstadt.informatik.fop.test.dorfromantik.adapter.DorfromantikTestAdapterStage1;

public class PlaceholderTileTest {
	
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
	public final void testGenerationOfPlaceholderTiles() {
		adapter.initializeGame();
		adapter.changeToState(Constants.Base.GameScene);
		assertEquals("Es sollte nur das Start Tile sichtbar sein", 1, adapter.getTilesOnBoard().size());

		assertEquals("Es sollte 4 Placeholder Tiles geben", 4, adapter.getPlaceholderTiles().size());
		// Move mouse to top Placeholder tile
		adapter.handleMouseLeftClick(400, 200);
		
		assertEquals("Es sollte 6 Placeholder Tiles geben", 6, adapter.getPlaceholderTiles().size());
		
		adapter.handleMouseLeftClick(300, 200);
		assertEquals("Es sollte 7 Placeholder Tiles geben", 7, adapter.getPlaceholderTiles().size());
		
	}
	
}