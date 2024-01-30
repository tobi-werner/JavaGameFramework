package de.tu_darmstadt.informatik.fop.test.dorfromantik.tutor.testcases;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.tu_darmstadt.informatik.fop.test.dorfromantik.adapter.DorfromantikTestAdapterMinimal;

public class TileMapTest {

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
	public final void testStartTile() {
		adapter.initializeGame();
		assertEquals("Es sollte nur das Start Tile und das Vorschau Tile existieren", 1, adapter.getTilesOnBoard().size());
	}
		
}
