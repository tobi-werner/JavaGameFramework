package de.tu_darmstadt.informatik.fop.main.dorfromantik.action;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.Transition;

import eea.engine.action.Action;
import eea.engine.component.Component;
import eea.engine.entity.StateBasedEntityManager;

public class AnimatedChangeStateInitAction implements Action {
/**
   * The internal state
   */
  private final int state;
  private final Transition leaveTransition;
  private final Transition enterTransition;

  /**
   * Creates a new ChangeStateAction for the new initial state
   * 
   * @param newState
   *          the ID of the state to be assumed when this action is evaluated
   *          using the
   *          {@link eea.engine.action.Action#update(GameContainer, StateBasedGame, int, Component)}
   *          method.
   */
  public AnimatedChangeStateInitAction(int newState, Transition leaveTransition, Transition enterTransition) {
    state = newState;
    this.leaveTransition = leaveTransition;
    this.enterTransition = enterTransition;
  }

  /**
   * ChangeStateInitAction overrides the
   * {@link eea.engine.action.Action#update(GameContainer, StateBasedGame, int, Component)}
   * method of the {@link eea.engine.action.Action} interface. It ensures that
   * the game enters the new state passed to the constructor and, if the game
   * was paused, resumes the game play.
   * 
   * More concretely, on receiving an update request, the following actions will
   * be taken to put the game state in a "clear state":
   * 
   * <ol>
   * <li>All entities are cleared from the target state,
   * "to get an empty playing field".</li>
   * <li>The list of recorded pressed "normal" keys is cleared.</li>
   * <li>The list of recorded pressed "control" keys is cleared.</li>
   * <li>The {@link org.newdawn.slick.GameContainer} is re-initialized.</li>
   * <li>If the game was paused, it is resumed.</li>
   * </ol>
   * 
   * @param gc
   *          the {@link org.newdawn.slick.GameContainer} object that handles
   *          the game loop, recording of the frame rate, and managing the input
   *          system
   * 
   * @param sb
   *          the {@link org.newdawn.slick.state.StateBasedGame} that isolates
   *          different stages of the game (e.g., menu, in-game, high scores
   *          etc.) into different states so they can be easily managed and
   *          maintained.
   * 
   * @param delta
   *          the time passed in nanoseconds (ns) since the start of the event,
   *          used to interpolate the current target position
   * 
   * @param event
   *          the event that the action is registered for
   */
  @Override
  public void update(GameContainer gc, StateBasedGame sb, int delta,
      Component event) {

    // enter the new "init" state
    sb.enterState(this.state, this.leaveTransition, this.enterTransition);

    // clear the play area by removing all entities from the state
    StateBasedEntityManager.getInstance().clearEntitiesFromState(state);

    // try to clean up all recorded keys and then re-init the game
    try {
      gc.getInput().clearKeyPressedRecord();
      gc.getInput().clearControlPressedRecord();
      gc.getInput().clearMousePressedRecord();
//	      gc.reinit();
      sb.init(gc);
    } catch (SlickException e) {
      e.printStackTrace();
    }

    // if the game was paused, resume it
    if (gc.isPaused())
      gc.resume();
  }
}

