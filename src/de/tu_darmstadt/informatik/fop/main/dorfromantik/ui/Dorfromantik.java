package de.tu_darmstadt.informatik.fop.main.dorfromantik.ui;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import de.tu_darmstadt.informatik.fop.main.dorfromantik.constants.Constants;
import eea.engine.entity.StateBasedEntityManager;

/**
 *
 * Diese Klasse startet das Spiel "Dorfromantik". Es enthaelt
 * zwei State's für das Menue und das eigentliche Spiel.
 */
public class Dorfromantik extends StateBasedGame {
	/** Wenn false werden bilder geladen. Muss auf true gesetzt werden, wenn Test diese Klasse verwenden */
    private boolean debug = false;
	
    public Dorfromantik(boolean debug)
    {
        super("Dorfromantik");
        this.debug = debug;
    }
 
    public static void main(String[] args) throws SlickException
    {
    	// Setze den library Pfad abhaengig vom Betriebssystem
    	if (System.getProperty("os.name").toLowerCase().contains("windows")) {
    		System.setProperty("org.lwjgl.librarypath",System.getProperty("user.dir") + "/native/windows");
    	} else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
    		System.setProperty("org.lwjgl.librarypath",System.getProperty("user.dir") + "/native/macosx");
    	} else {
    		System.setProperty("org.lwjgl.librarypath",System.getProperty("user.dir") + "/native/" +System.getProperty("os.name").toLowerCase());
    	}
    	// Setze dieses StateBasedGame in einen App Container (oder Fenster)
    	AppGameContainer app = new AppGameContainer(new Dorfromantik(false));
        // Lege die Einstellungen des Fensters fest und starte das Fenster
        // (nicht aber im Vollbildmodus)
        app.setDisplayMode((int)Constants.Base.windowSize.x, (int)Constants.Base.windowSize.y, false);
        app.setResizable(false);
        app.start();
    }

	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		// Fuege dem StateBasedGame die States hinzu 
		// (der zuerst hinzugefuegte State wird als erster State gestartet)
		addState(new MainMenuState(debug));
        addState(new GameplayState(debug));
        addState(new GameOverState(debug));
        
        // Fuege dem StateBasedEntityManager die States hinzu
        StateBasedEntityManager.getInstance().addState(Constants.Base.MenuScene);
        StateBasedEntityManager.getInstance().addState(Constants.Base.GameScene);
        StateBasedEntityManager.getInstance().addState(Constants.Base.GameOverScene);
		
	}
}