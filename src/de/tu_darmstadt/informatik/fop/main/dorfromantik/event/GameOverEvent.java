package de.tu_darmstadt.informatik.fop.main.dorfromantik.event;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import de.tu_darmstadt.informatik.fop.main.dorfromantik.controller.ScoreManager;
import eea.engine.event.Event;

/**
 * Gameover event. This event triggers if there are no tiles left.
 */
public class GameOverEvent extends Event {

	public GameOverEvent() {
		super("GameOverEvent");
	}

	@Override
	protected boolean performAction(GameContainer gc, StateBasedGame sb,
			int delta) {
		return ScoreManager.getInstance().getTilesLeft() == 0;
	}

}