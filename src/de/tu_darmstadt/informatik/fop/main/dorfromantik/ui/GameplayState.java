package de.tu_darmstadt.informatik.fop.main.dorfromantik.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import de.tu_darmstadt.informatik.fop.main.dorfromantik.action.AnimatedChangeStateInitAction;
import de.tu_darmstadt.informatik.fop.main.dorfromantik.animation.RotateByCubicAnimation;
import de.tu_darmstadt.informatik.fop.main.dorfromantik.constants.Constants;
import de.tu_darmstadt.informatik.fop.main.dorfromantik.controller.BoardController;
import de.tu_darmstadt.informatik.fop.main.dorfromantik.controller.FeedbackManager;
import de.tu_darmstadt.informatik.fop.main.dorfromantik.controller.ScoreManager;
import de.tu_darmstadt.informatik.fop.main.dorfromantik.controller.TextureManager;
import de.tu_darmstadt.informatik.fop.main.dorfromantik.entity.PlaceholderTile;
import de.tu_darmstadt.informatik.fop.main.dorfromantik.entity.Tile;
import de.tu_darmstadt.informatik.fop.main.dorfromantik.event.GameOverEvent;
import de.tu_darmstadt.informatik.fop.main.dorfromantik.factory.TileFactory;
import de.tu_darmstadt.informatik.fop.main.dorfromantik.parser.ConfigParser;
import eea.engine.action.Action;
import eea.engine.component.Component;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import eea.engine.event.basicevents.KeyDownEvent;
import eea.engine.event.basicevents.KeyPressedEvent;
import eea.engine.event.basicevents.MouseClickedEvent;

/**
 *
 * Diese Klasse repraesentiert das Spielfenster.
 */
public class GameplayState extends BasicGameState {
	/**
	 * The boardController which handles user input.
	 */
	private BoardController boardController;
	/**
	 * Handles tile generation.
	 */
	private TileFactory tileFactory;
	
	/**
	 * The preview tile which lets the user see the next tile.
	 */
	private Tile previewTile;
	
	/**
	 * should images be rendered?
	 */
	private boolean debug;
	
	/**
	 * Background Color
	 */
	private Color backgroundColor;
	
	public BoardController getBoardController() {
		return this.boardController;
	}
    
    GameplayState(boolean debug) {
    	super();
    	this.debug = debug;
    	System.out.println(String.format("Lade Bilder: %s", !this.debug));
    	TextureManager.getInstance().setDebug(debug);
    	// Load textures
    	TextureManager.getInstance().loadTileset("assets/Dorfromantik-Tileset.png");
    	
    	// Parse config file
    	ConfigParser parser = new ConfigParser();
    	parser.parseConfigFromPath("assets/config.properties");
    	parser.validateData();
    	
    	// Initialize
    	tileFactory = new TileFactory(parser.getGameData(), debug);
    	boardController = new BoardController(this.debug);
    	
    }
    
    /**
     * Wird vor dem (erstmaligen) Starten dieses States ausgefuehrt
     */
    @Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
    	// Set world clipping
    	if (!debug) container.getGraphics().setWorldClip(0, 0, Constants.Base.windowSize.x, Constants.Base.windowSize.y);
    	
    	// choose random background color
    	List<Color> colors = new ArrayList<>();
    	colors.add(new Color(242, 188, 184));
    	colors.add(new Color(240, 198, 150));
    	colors.add(new Color(211, 202, 212));
    	colors.add(new Color(176, 208, 213));
    	this.backgroundColor = colors.get(new Random().nextInt(colors.size()));
    	
    	// generate the starting tile and add it to the scene.
    	Tile startTile = tileFactory.generateStartingTile();
    	boolean tilePlaced = boardController.addTileAtIndex(new Vector2f(Constants.GamePlay.boardDimension.x/2, Constants.GamePlay.boardDimension.y/2), startTile, false);
    	if (tilePlaced) StateBasedEntityManager.getInstance().addEntity(Constants.Base.GameScene, startTile);
    	
    	// add the board to the scene.
    	boardController.centerBoardtoScreen(false);
    	StateBasedEntityManager.getInstance().addEntity(Constants.Base.GameScene, boardController.getBoard());
    	
    	// register events.
    	registerWASDBoardMovement();
    	registerKeyPressedEvents();
    	registerMouseClickEvent();
    	registerGameOverEventsAndActions();
    	
    	// generate initial preview tile.
    	tileFactory.generatePreviewTile();
    	previewTile = tileFactory.getPreviewTile();
    	previewTile.setPosition(new Vector2f(750, 550));
    	StateBasedEntityManager.getInstance().addEntity(Constants.Base.GameScene, previewTile);
    	
    	// update hover texture on placeholder tiles
		for (PlaceholderTile p: boardController.getBoard().getPlaceholderTiles()) {
			p.setPeviewTileTexture(tileFactory.getPreviewTile().getTexture());
		}
    	
    }

    /**
     * Wird vor dem Frame ausgefuehrt
     */
    @Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		// StatedBasedEntityManager soll alle Entities aktualisieren
    	StateBasedEntityManager.getInstance().updateEntities(container, game, delta);
	}
    
    /**
     * Wird mit dem Frame ausgefuehrt
     */
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		// render backbround color.
		g.setColor(this.backgroundColor);
		g.fillRect(0, 0, Constants.Base.windowSize.x, Constants.Base.windowSize.y);

		// StatedBasedEntityManager soll alle Entities rendern
		StateBasedEntityManager.getInstance().renderEntities(container, game, g);
		
		// render text
		g.setColor(Color.black);
		g.drawString("Score: "+String.valueOf(ScoreManager.getInstance().getScore()), 700, 450);
		g.drawString("Tiles: "+String.valueOf(ScoreManager.getInstance().getTilesLeft()), 700, 475);
		
		String tutorial = "Steuerung: W,A,S,D Bewegung, R Rotation, C Zentrierung";
		g.drawString(tutorial, 20, 580);
		
		// render feedback messages.
		int distance = 15;
		List<String> feedback = FeedbackManager.getInstance().getFeedbackMessages();
		for (int i=0; i< feedback.size(); i++) {
			g.drawString(feedback.get(i), 20, Constants.Base.windowSize.y-50-i*distance);
		}
		FeedbackManager.getInstance().removeFeedbackMessage();
		
	}
	
	/**
	 * Register game over event.
	 */
	private void registerGameOverEventsAndActions() {
		Action saveScore = new Action() {
			@Override
			public void update(GameContainer gc, StateBasedGame sb, int delta,
					Component event) {
				ScoreManager.getInstance().saveScore(Constants.Base.scoreFilePath);
			}    		
    	};
    	
    	Entity gameOverListener = new Entity("gameOverListener");
    	GameOverEvent gameOver = new GameOverEvent();
    	gameOver.addAction(saveScore);
    	gameOver.addAction(new AnimatedChangeStateInitAction(Constants.Base.GameOverScene, new FadeOutTransition(), new FadeInTransition()));
    	gameOverListener.addComponent(gameOver);    	
    	StateBasedEntityManager.getInstance().addEntity(Constants.Base.GameScene, gameOverListener);
	}
	
	/**
	 * Register mouse clicked events.
	 */
	private void registerMouseClickEvent() {
		// Bei Mausklick soll Wassertropfen erscheinen
    	Entity mouse_Clicked_Listener = new Entity("Mouse_Clicked_Listener");
    	MouseClickedEvent mouse_Clicked = new MouseClickedEvent();
    	mouse_Clicked.addAction(new Action() {
			@Override
			public void update(GameContainer gc, StateBasedGame sb, int delta,
					Component event) {
				// get the mouse position.
				Vector2f mousePosition = new Vector2f(gc.getInput().getMouseX(),gc.getInput().getMouseY());
				// calculate index
				Vector2f index = boardController.getIndexFromScreenPosition(mousePosition);
				
				// get the preview tile and place it
				Tile tile = tileFactory.getPreviewTile();
				Boolean tilePlaced = boardController.addTileAtIndex(index, tile, true);
				
				// if placement was successful update score and generate new previewTile.
				if (tilePlaced) {
					ScoreManager.getInstance().updateScore(boardController.getBoard(), index);
					
					previewTile = tileFactory.generatePreviewTile();
					previewTile.setPosition(new Vector2f(750, 550));
					StateBasedEntityManager.getInstance().addEntity(Constants.Base.GameScene, previewTile);
					
					// update hover texture on placeholder tiles
					for (PlaceholderTile p: boardController.getBoard().getPlaceholderTiles()) {
						p.setPeviewTileTexture(tileFactory.getPreviewTile().getTexture());
						p.setRotation(previewTile.getRotation());
					}
				}
			}    		
    	});
    	mouse_Clicked_Listener.addComponent(mouse_Clicked);
    	StateBasedEntityManager.getInstance().addEntity(Constants.Base.GameScene, mouse_Clicked_Listener);  
	}
	
	/**
	 * Register W,A,S,D KeyBoard events.
	 */
	private void registerWASDBoardMovement() {
		String[] entityNames = new String[] {"W_Listener", "A_Listener", "S_Listener", "D_Listener"};
		Vector2f[] movementDirections = new Vector2f[] {new Vector2f(0, 1), new Vector2f(1, 0), new Vector2f(0, -1), new Vector2f(-1, 0)};
		int[] keyIds = new int[] {Input.KEY_W, Input.KEY_A, Input.KEY_S, Input.KEY_D};
		
		for (int i=0; i<entityNames.length; i++) {
			Vector2f movement = movementDirections[i];
			Entity entity = new Entity(entityNames[i]);
			KeyDownEvent event = new KeyDownEvent(keyIds[i]);
			event.addAction(new Action() {
				@Override
				public void update(GameContainer arg0, StateBasedGame arg1, int arg2, Component arg3) {
					boardController.updateBoardPosition(movement, false);
				}
			});
			entity.addComponent(event);
			StateBasedEntityManager.getInstance().addEntity(Constants.Base.GameScene, entity);
		}
	}
	
	/**
	 * Register C, R Keys events.
	 */
	private void registerKeyPressedEvents() {

    	Entity r_Listener = new Entity("R_Listener");
    	KeyPressedEvent r_pressed = new KeyPressedEvent(Input.KEY_R);
    	r_pressed.addAction(new Action() {
    		@Override
			public void update(GameContainer gc, StateBasedGame sb, int delta,
					Component event) {
    			Tile previewTile = tileFactory.getPreviewTile();
    			boolean success = previewTile.runAnimation(new RotateByCubicAnimation(0.5f, 90));
    			if (success) {
    				// Animation can run. Rotate tiles and rotate tiledata of preview tile
    				previewTile.rotateTileData();
    				
    				for (PlaceholderTile p: boardController.getBoard().getPlaceholderTiles()) {
    					if (p.isHovered()) p.runAnimation(new RotateByCubicAnimation(0.5f, 90));
    					else p.setRotation(p.getRotation()+90);
    				}
    			}
    		}
    	});
    	r_Listener.addComponent(r_pressed);    	
    	StateBasedEntityManager.getInstance().addEntity(Constants.Base.GameScene, r_Listener);
    	
    	Entity c_Listener = new Entity("C_Listener");
    	KeyPressedEvent c_pressed = new KeyPressedEvent(Input.KEY_C);
    	c_pressed.addAction(new Action() {
    		@Override
			public void update(GameContainer gc, StateBasedGame sb, int delta,
					Component event) {
    			
    			boardController.centerBoardtoScreen(true);
    		}
    	});
    	c_Listener.addComponent(c_pressed);    	
    	StateBasedEntityManager.getInstance().addEntity(Constants.Base.GameScene, c_Listener);
	}

	@Override
	public int getID() {
		return Constants.Base.GameScene;
	}
}
