package de.tu_darmstadt.informatik.fop.test.dorfromantik.student.testcases;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.newdawn.slick.geom.Vector2f;

import de.tu_darmstadt.informatik.fop.main.dorfromantik.constants.Constants;
import de.tu_darmstadt.informatik.fop.main.dorfromantik.entity.Tile;
import de.tu_darmstadt.informatik.fop.test.dorfromantik.adapter.DorfromantikTestAdapterMinimal;

public class TileTest {

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
	public final void testStartTileIndex() {
		adapter.initializeGame();
		assertEquals("Es sollte nur das Start Tile existieren", 1, adapter.getTilesOnBoard().size());
		Tile startTile = adapter.getTilesOnBoard().get(0);
		
		Vector2f expectedIndex = new Vector2f(Constants.GamePlay.boardDimension.x/2, Constants.GamePlay.boardDimension.x/2);
		
		assertEquals("Das StartTile sollte den korrekten Index haben", expectedIndex, startTile.getIndex());
	}
		
}
