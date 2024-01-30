package de.tu_darmstadt.informatik.fop.main.dorfromantik.ui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import de.tu_darmstadt.informatik.fop.main.dorfromantik.action.AnimatedChangeStateInitAction;
import de.tu_darmstadt.informatik.fop.main.dorfromantik.constants.Constants;
import de.tu_darmstadt.informatik.fop.main.dorfromantik.controller.ScoreManager;
import eea.engine.action.Action;
import eea.engine.component.Component;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import eea.engine.event.ANDEvent;
import eea.engine.event.basicevents.MouseClickedEvent;
import eea.engine.event.basicevents.MouseEnteredEvent;

public class GameOverState extends BasicGameState {

	// distances of texts.
	private final int distance = 100;
    private final int start_Position = 180;
    // should textures be displayed.
    private boolean debug = false;
    
    GameOverState(boolean debug) {
    	super();
    	this.debug = debug;
    }
    
    /**
     * Wird vor dem (erstmaligen) Starten dieses State's ausgefuehrt
     */
    @Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
    	// Hintergrund laden
    	Entity background = new Entity("menu");	// Entitaet fuer Hintergrund
    	background.setPosition(new Vector2f(400,300));	// Startposition des Hintergrunds
    	if (!debug) background.addComponent(new ImageRenderComponent(new Image("/assets/menu.png"))); // Bildkomponente
    	    	
    	// Hintergrund-Entitaet an StateBasedEntityManager uebergeben
    	StateBasedEntityManager.getInstance().addEntity(Constants.Base.GameOverScene, background);
    
    	/* Replay Button*/
    	Entity replayEntity = new Entity("Erneut Spielen");
    	
    	// Setze Position und Bildkomponente
    	replayEntity.setPosition(new Vector2f(218, 190));
    	replayEntity.setScale(0.28f);
    	if (!debug) replayEntity.addComponent(new ImageRenderComponent(new Image("assets/entry.png")));
    	
    	// Reset Game
    	// This is done here to prevent the score from reseting when transitioning to this state
    	Action resetGame = new Action() {
			@Override
			public void update(GameContainer gc, StateBasedGame sb, int delta,
					Component event) {
				ScoreManager.getInstance().reset();
			}    		
    	};
    	
    	// Erstelle das Ausloese-Event und die zugehoerige Action
    	ANDEvent mainEvents_q = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
    	Action quit_Action = new AnimatedChangeStateInitAction(Constants.Base.GameScene, new FadeOutTransition(), new FadeInTransition());
    	mainEvents_q.addAction(resetGame);
    	mainEvents_q.addAction(quit_Action);
    	replayEntity.addComponent(mainEvents_q);
    	
    	// Fuege die Entity zum StateBasedEntityManager hinzu
    	StateBasedEntityManager.getInstance().addEntity(Constants.Base.GameOverScene, replayEntity);
    	
    	/* Back to Menu Button */
    	String backToMenu = "Zurück zum Menü";
    	Entity backToMenuEntity = new Entity(backToMenu);
    	
    	// Setze Position und Bildkomponente
    	backToMenuEntity.setPosition(new Vector2f(218, 290));
    	backToMenuEntity.setScale(0.28f);
    	if (!debug) backToMenuEntity.addComponent(new ImageRenderComponent(new Image("assets/entry.png")));
    	
    	// Erstelle das Ausloese-Event und die zugehoerige Action
    	ANDEvent mainEvents = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
    	Action backToMenuAction = new AnimatedChangeStateInitAction(Constants.Base.MenuScene, new FadeOutTransition(), new FadeInTransition());
    	mainEvents.addAction(backToMenuAction);
    	backToMenuEntity.addComponent(mainEvents);
    	
    	// Fuege die Entity zum StateBasedEntityManager hinzu
    	StateBasedEntityManager.getInstance().addEntity(Constants.Base.GameOverScene, backToMenuEntity);
    }

    /**
     * Wird vor dem Frame ausgefuehrt
     */
    @Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
    	StateBasedEntityManager.getInstance().updateEntities(container, game, delta);
	}
    
    /**
     * Wird mit dem Frame ausgefuehrt
     */
	@Override
	public void render(GameContainer container, StateBasedGame game, 
												Graphics g) throws SlickException {
		StateBasedEntityManager.getInstance().renderEntities(container, game, g);
		
		// render text
		int counter = 0;
		
		g.setColor(Color.white);
		
		g.drawString("Game Over", 110, 50);
		
		g.drawString("Erneut Spielen", 110, start_Position+counter*distance); counter++;
		g.drawString("Zurück zum Menü", 110, start_Position+counter*distance); counter++;

		g.drawString(String.format("Score: %d", ScoreManager.getInstance().getScore()), 600, 280);
		g.drawString(String.format("Highscore: %d", ScoreManager.getInstance().getHighScore()), 600, 300);

	}

	@Override
	public int getID() {
		return Constants.Base.GameOverScene;
	}
}