package de.tu_darmstadt.informatik.fop.test.dorfromantik.student.testcases;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.tu_darmstadt.informatik.fop.main.dorfromantik.constants.Constants;
import de.tu_darmstadt.informatik.fop.test.dorfromantik.adapter.DorfromantikTestAdapterMinimal;

public class MenuStateTest {

	DorfromantikTestAdapterMinimal adapter;
	
	@Before
	public void setUp() {
		adapter = new DorfromantikTestAdapterMinimal();
	}
	
	@After
	public void finish() {
		adapter.stopGame();
	}
	
	@Test
	public void testChangeStateWithKeys() {
		// initialize the game in MAINMENUSTATE
		adapter.initializeGame();

		assertTrue("The game has not been started in MAINMENUSTATE, current state id = "+adapter.getStateBasedGame().getCurrentStateID(), 
				Constants.Base.MenuScene == adapter.getStateBasedGame().getCurrentStateID());

		// go into GAMEPLAYSTATE
		adapter.handleKeyPressN();
		// the transition needs time
		adapter.runGame(1000);
		
		assertTrue("The game is not in GAMEPLAYSTATE, current state id = "+adapter.getStateBasedGame().getCurrentStateID(), 
				Constants.Base.GameScene == adapter.getStateBasedGame().getCurrentStateID());
	}

	
}
