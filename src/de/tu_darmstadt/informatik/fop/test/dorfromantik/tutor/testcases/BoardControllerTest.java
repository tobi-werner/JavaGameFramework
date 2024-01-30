package de.tu_darmstadt.informatik.fop.test.dorfromantik.tutor.testcases;

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
		
		// Invalid Position, only neighbouring tiles allowed
		adapter.handleMouseLeftClick(800, 200);
		assertEquals("Es sollte kein weiteres Tile hinzugefügt sein", 2, adapter.getTilesOnBoard().size());
		
		//Invalid position, tile exists at position
		adapter.handleMouseLeftClick(400, 200);
		assertEquals("Es sollte kein weiteres Tile hinzugefügt sein, da bereits eins an der Stelle existiert", 2, adapter.getTilesOnBoard().size());
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
	
		// D = rechts
		for (i = 0; i < 50; i++) {
			adapter.handleKeyDownD();
		}
		assertEquals("Das Starttile sollte sich 50 Pixel nach links bewegt haben", (int) Constants.Base.windowMiddle.x, (int) startTile.getPosition().x);

		// W = oben
		i = 0;
		for (i = 0; i < 50; i++) {
			adapter.handleKeyDownW();
		}
		assertEquals("Das Starttile sollte sich 50 Pixel nach oben bewegt haben", (int) Constants.Base.windowMiddle.y+i, (int) startTile.getPosition().y);
	
		// S = unten
		for (i = 0; i < 50; i++) {
			adapter.handleKeyDownS();
		}

		assertEquals("Das Starttile sollte sich 50 Pixel nach unten bewegt haben", (int) Constants.Base.windowMiddle.y, (int) startTile.getPosition().y);

	}
	
	@Test
	public final void moveBoardToCenter() {
		adapter.initializeGame();
		adapter.changeToState(Constants.Base.GameScene);
		
		assertEquals(1, adapter.getTilesOnBoard().size());
		Tile startTile = adapter.getTilesOnBoard().get(0);
		// move board to bottom left
		int i = 0;
		for (i = 0; i < 50; i++) {
			adapter.handleKeyDownA();
			adapter.handleKeyDownS();
		}
		assertEquals("Das Starttile sollte sich 50 Pixel nach links bewegt haben", (int) Constants.Base.windowMiddle.x+i, (int) startTile.getPosition().x);
		assertEquals("Das Starttile sollte sich 50 Pixel nach unten bewegt haben", (int) Constants.Base.windowMiddle.y-i, (int) startTile.getPosition().y);
		
		adapter.handleKeyPressC();
		// Animations are calculated using system time
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {}
		adapter.runGame(1000);
		
		assertEquals("Das Starttile sollte sich in der Bildschirmmitte befinden", Constants.Base.windowMiddle, startTile.getPosition());
		
	}
	
	
}
