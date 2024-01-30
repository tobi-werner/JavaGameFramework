package de.tu_darmstadt.informatik.fop.test.dorfromantik.adapter;

import java.util.List;
import java.util.stream.Collectors;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.tu_darmstadt.informatik.fop.main.dorfromantik.constants.Constants;
import de.tu_darmstadt.informatik.fop.main.dorfromantik.entity.Tile;
import de.tu_darmstadt.informatik.fop.main.dorfromantik.ui.Dorfromantik;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import eea.engine.test.TestAppGameContainer;


public class DorfromantikTestAdapterMinimal {

	Dorfromantik dorfromantik; 						// erbt von StateBasedGame
	TestAppGameContainer app;			// spezielle Variante des AppGameContainer,
										// welche keine UI erzeugt (nur für Tests!)
	
	/**
	 * Verwenden Sie diesen Konstruktor zur Initialisierung von allem,
	 * was sie benoetigen
	 * 
	 * Use this constructor to set up everything you need.
	 */
	public DorfromantikTestAdapterMinimal() {
		super();
		dorfromantik = null;
		// Map.getInstance().resetToDefault();
	}
	
	/* *************************************************** 
	 * ********* initialize, run, stop the game **********
	 * *************************************************** */
	
	public StateBasedGame getStateBasedGame() {
		return dorfromantik;
	}
	
	/**
	 * Diese Methode initialisiert das Spiel im Debug-Modus, d.h. es wird
	 * ein AppGameContainer gestartet, der keine Fenster erzeugt und aktualisiert.
	 * 
	 * Sie müssen diese Methode erweitern
	 */
	public void initializeGame() {
		
		// Setze den library Pfad abhaengig vom Betriebssystem
		if (System.getProperty("os.name").toLowerCase().contains("windows")) {
    		System.setProperty("org.lwjgl.librarypath",System.getProperty("user.dir") + "/native/windows");
    	} else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
    		System.setProperty("org.lwjgl.librarypath",System.getProperty("user.dir") + "/native/macosx");
    	} else {
    		System.setProperty("org.lwjgl.librarypath",System.getProperty("user.dir") + "/native/" +System.getProperty("os.name").toLowerCase());
    	}
    	// Initialisiere das Spiel im Debug-Modus (ohne UI-Ausgabe)
		dorfromantik = new Dorfromantik(true);
		//dorfromantik.enterState(Constants.Base.GameScene);
		
		// Initialisiere die statische Klasse Map
		try {
			// Map.getInstance().resetToDefault();
			app = new TestAppGameContainer(dorfromantik);
			app.start(0);
			System.out.println("Starte Testcontainer");
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Stoppe das im Hintergrund laufende Spiel
	 */
	public void stopGame() {
		if (app != null) {
			app.exit();
			app.destroy();
		}
		StateBasedEntityManager.getInstance().clearAllStates();
		dorfromantik = null;
	}
	
	/**
	 * Run the game for a specified duration.
	 * Laesst das Spiel fuer eine angegebene Zeit laufen
	 * 
	 * @param ms duration of runtime of the game
	 */
	public void runGame(int ms) {
		if (dorfromantik != null && app != null) {
			try {
				app.updateGame(ms);
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
	}
	
	/* ********************** Input ********************** */
	
	/**
	 * This Method should emulate the key down event.
	 * This should make the playertank shoot.
	 * 
	 * Diese Methode emuliert das Druecken beliebiger Tasten.
	 * (Dies soll es ermoeglichen, das Schiessen des Spielerpanzers
	 * zu testen)
	 * 
	 * @param updatetime : Zeitdauer bis update-Aufruf
	 * @param input : z.B. Input.KEY_K, Input.KEY_L
	 */
	public void handleKeyDown(int updatetime, Integer input) {
		if (dorfromantik != null && app != null) {
			app.getTestInput().setKeyDown(input);
			try {
				app.updateGame(updatetime);
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
		app.getTestInput().clearDownKeys();
	}
	
	/**
	 * This Method should emulate the key pressed event.
	 * This should make the playertank shoot.
	 * 
	 * Diese Methode emuliert das Druecken beliebiger Tasten.
	 * (Dies soll es ermoeglichen, das Schiessen des Spielerpanzers
	 * zu testen)
	 * 
	 * @param updatetime : Zeitdauer bis update-Aufruf
	 * @param input : z.B. Input.KEY_K, Input.KEY_L
	 */
	public void handleKeyPressed(int updatetime, Integer input) {
		if (dorfromantik != null && app != null) {
			app.getTestInput().setKeyPressed(input);
			try {
				app.updateGame(updatetime);
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
		app.getTestInput().clearPressedKeys();
	}
	
	public void moveMouse(Integer mouseX, Integer mouseY) {
		if (dorfromantik != null && app != null) {
			System.out.println("MausX: "+app.getTestInput().getMouseX());
			System.out.println("MausY: "+app.getTestInput().getMouseY());
			app.getTestInput().setMouseX(mouseX);
			app.getTestInput().setMouseY(mouseY);
			System.out.println("MausX: "+app.getTestInput().getMouseX());
			System.out.println("MausY: "+app.getTestInput().getMouseY());
		}
	}
	
	public void handleMouseClick(int updatetime, Integer input, Integer mouseX, Integer mouseY) {
		if (dorfromantik != null && app != null) {
			this.moveMouse(mouseX, mouseY);
			app.getTestInput().setMouseButtonPressed(input);
			try {
				app.updateGame(updatetime);
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
		app.getTestInput().clearMouseButtonPressed();
	}
	
	public void changeToState(int state) {
		this.dorfromantik.enterState(state);
	}
	
	

	public void handleKeyDownW() {
		handleKeyDown(100, Input.KEY_W);
	}

	public void handleKeyDownA() {
		handleKeyDown(100, Input.KEY_A);
	}

	public void handleKeyDownS() {
		handleKeyDown(100, Input.KEY_S);
	}
	
	public void handleKeyDownD() {
		handleKeyDown(100, Input.KEY_D);
	}
	
	public void handleKeyPressR() {
		handleKeyPressed(100, Input.KEY_R);
	}

	public void handleKeyPressC() {
		handleKeyPressed(100, Input.KEY_C);
	}
	
	public void handleKeyPressN() {
		handleKeyPressed(100, Input.KEY_N);
	}
	
	public void handleKeyPressESC() {
		handleKeyPressed(100, Input.KEY_ESCAPE);
	}
	
	public void handleMouseLeftClick(Integer mouseX, Integer mouseY) {
		this.handleMouseClick(1000, Input.MOUSE_LEFT_BUTTON, mouseX, mouseY);
	}
	
	/* ********************** Map Data ********************** */
	public List<Tile> getTilesOnBoard() {
		List<Entity> entities = StateBasedEntityManager.getInstance().getEntitiesByState(Constants.Base.GameScene);
		List<Tile> tiles = entities.stream()
				.filter(Tile.class::isInstance)
				.map(Tile.class::cast)
				.filter(tile -> tile.getIndex() != null && tile.getID() != "placeholder")
				.collect(Collectors.toList());
		return tiles;
	}
	
	public Tile getTileAtIndex(Vector2f index) {
		return getTilesOnBoard().stream().filter(tile -> tile.getIndex().equals(index)).collect(Collectors.toList()).get(0);
	}	
	
}