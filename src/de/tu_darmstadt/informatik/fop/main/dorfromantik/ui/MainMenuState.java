package de.tu_darmstadt.informatik.fop.main.dorfromantik.ui;

import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
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
import eea.engine.action.basicactions.QuitAction;
import eea.engine.component.Component;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import eea.engine.event.ANDEvent;
import eea.engine.event.basicevents.KeyPressedEvent;
import eea.engine.event.basicevents.MouseClickedEvent;
import eea.engine.event.basicevents.MouseEnteredEvent;

/**
 * Diese Klasse repraesentiert das Menuefenster, indem ein neues
 * Spiel gestartet werden kann und das gesamte Spiel beendet 
 * werden kann.
 */
public class MainMenuState extends BasicGameState {

	private final int distance = 100;
    private final int start_Position = 180;
    /**
     * Should textures be displayed?
     */
    private boolean debug = false;
    
    MainMenuState(boolean debug) {
    	super();
    	this.debug = debug;
    	
    	ScoreManager.getInstance().loadScores(Constants.Base.scoreFilePath);
    	System.out.println(String.format("HighScore: %d", ScoreManager.getInstance().getHighScore()));
    	
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
    	StateBasedEntityManager.getInstance().addEntity(Constants.Base.MenuScene, background);
    	
    	/* Neues Spiel starten-Entitaet */
    	String new_Game = "Neues Spiel starten";
    	Entity new_Game_Entity = new Entity(new_Game);
    	
    	// Setze Position und Bildkomponente
    	new_Game_Entity.setPosition(new Vector2f(218, 190));
    	new_Game_Entity.setScale(0.28f);
    	if (!debug) new_Game_Entity.addComponent(new ImageRenderComponent(new Image("assets/entry.png")));
    	
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
    	ANDEvent mainEvents = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
    	Action new_Game_Action = new AnimatedChangeStateInitAction(Constants.Base.GameScene, new FadeOutTransition(), new FadeInTransition());
    	mainEvents.addAction(resetGame);
    	mainEvents.addAction(new_Game_Action);
    	new_Game_Entity.addComponent(mainEvents);
    	
    	// Fuege die Entity zum StateBasedEntityManager hinzu
    	StateBasedEntityManager.getInstance().addEntity(Constants.Base.MenuScene, new_Game_Entity);
    	
    	/* Beenden-Entitaet */
    	Entity quit_Entity = new Entity("Beenden");
    	
    	// Setze Position und Bildkomponente
    	quit_Entity.setPosition(new Vector2f(218, 290));
    	quit_Entity.setScale(0.28f);
    	if (!debug) quit_Entity.addComponent(new ImageRenderComponent(new Image("assets/entry.png")));
    	
    	// Erstelle das Ausloese-Event und die zugehoerige Action
    	ANDEvent mainEvents_q = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
    	Action quit_Action = new QuitAction();
    	mainEvents_q.addAction(quit_Action);
    	quit_Entity.addComponent(mainEvents_q);
    	
    	// Fuege die Entity zum StateBasedEntityManager hinzu
    	StateBasedEntityManager.getInstance().addEntity(Constants.Base.MenuScene, quit_Entity);
    	
    	// N Listener um neues Spiel zu starten
    	Entity N_Listener = new Entity("N_Listener");
    	KeyPressedEvent N_pressed = new KeyPressedEvent(Input.KEY_N);
    	N_pressed.addAction(new_Game_Action);
    	N_Listener.addComponent(N_pressed);    	
    	StateBasedEntityManager.getInstance().addEntity(Constants.Base.MenuScene, N_Listener);

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
		
		g.drawString("Neues Spiel", 110, start_Position+counter*distance); counter++;
		g.drawString("Beenden", 110, start_Position+counter*distance); counter++;
		
		// render Top scores.
		List<Integer> scores = ScoreManager.getInstance().getNMaxScores(10);
		g.drawString("Best Games:", 600, 200);
		for (int i=0; i<scores.size(); i++) {
			String text = "%d";
			if (scores.get(i) == ScoreManager.getInstance().getHighScore()) {
				text = "*%d*";
			}
			g.drawString(String.format(text, scores.get(i)), 600, 200+(i+1)*distance/5);
		}
		// render scores of last games.
		g.drawString("Last Games:", 500, 200);
		List<Integer> scoresLatest = ScoreManager.getInstance().getNLatestScores(10);
		for (int i=0; i<scoresLatest.size(); i++) {
			String text = "%d";
			if (scoresLatest.get(i) == ScoreManager.getInstance().getHighScore()) {
				text = "*%d*";
			}
			// order to list the latest game at the top
			g.drawString(String.format(text, scoresLatest.get(i)), 500, 200+(10-i)*distance/5);
		}
	}

	@Override
	public int getID() {
		return Constants.Base.MenuScene;
	}
	
}
