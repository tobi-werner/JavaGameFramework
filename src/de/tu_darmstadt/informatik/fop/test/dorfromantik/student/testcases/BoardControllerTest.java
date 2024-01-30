package de.tu_darmstadt.informatik.fop.test.dorfromantik.student.testcases;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.tu_darmstadt.informatik.fop.main.dorfromantik.constants.Constants;
import de.tu_darmstadt.informatik.fop.main.dorfromantik.entity.Tile;
import de.tu_darmstadt.informatik.fop.test.dorfromantik.adapter.DorfromantikTestAdapterMinimal;

/**
 * This class tests the UserInput in the {@link GameplayState} as well as the {@link BoardController}.
 */
public class BoardControllerTest {
	
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
	public final void placeTileByMouse() {
		adapter.initializeGame();
		adapter.changeToState(Constants.Base.GameScene);
		adapter.handleMouseLeftClick(400, 200);
		assertEquals("Es sollten das Start Tile und das platzierte Tile sichtbar sein", 2, adapter.getTilesOnBoard().size());
	}
	
	@Test
	public final void moveBoardWithWASD() {
		adapter.initializeGame();
		adapter.changeToState(Constants.Base.GameScene);
		
		assertEquals(1, adapter.getTilesOnBoard().size());
		Tile startTile = adapter.getTilesOnBoard().get(0);
		
		// A = links
		int i = 0;
		for (i = 0; i < 50; i++) {
			adapter.handleKeyDownA();
		}
		assertEquals("Das Starttile sollte sich 50 Pixel nach rechts bewegt haben", (int) Constants.Base.windowMiddle.x+i, (int) startTile.getPosition().x);
	}
	
	
}
