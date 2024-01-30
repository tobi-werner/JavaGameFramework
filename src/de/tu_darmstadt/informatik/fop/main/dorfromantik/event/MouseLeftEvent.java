package de.tu_darmstadt.informatik.fop.main.dorfromantik.event;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;
import eea.engine.event.basicevents.MouseEnteredEvent;

/**
 * This event represents cases where the mouse position does not match the
 * area occupied by the entity owning this event
 */
public class MouseLeftEvent extends MouseEnteredEvent {

  /**
   * creates a new MouseLeft event
   */
  public MouseLeftEvent() {
    super();
  }

  @Override
  protected boolean performAction(GameContainer gc, StateBasedGame sb, int delta) {
    return !super.performAction(gc, sb, delta);
  }

}
