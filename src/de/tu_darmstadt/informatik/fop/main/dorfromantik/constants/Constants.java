package de.tu_darmstadt.informatik.fop.main.dorfromantik.constants;

import org.newdawn.slick.geom.Vector2f;

public class Constants {

	public static class Base {
		public final static Vector2f windowSize = new Vector2f(800, 600);
		public final static Vector2f windowMiddle = new Vector2f(windowSize.x/2, windowSize.y/2);
		
		public final static Vector2f zeroVector = new Vector2f(0, 0);
		
		public final static int MenuScene = 0;
		public final static int GameScene = 1;
		public final static int GameOverScene = 2;
		
		// Path where the user scores are stored.
		public final static String scoreFilePath = "data/scores.txt";

		// Height of the spritesheet image in tiles.
		public final static int TextureGridHeight = 10;
		// Width of the spritesheet image in tiles.
		public final static int TextureGridWidth = 10;
		// Total count of tiles in the spritesheet
		public final static int TileCount = 73;
	}
	
	public static class GamePlay {
		// the count of tiles which the board can display
		public final static Vector2f boardDimension = new Vector2f(500, 500);
		
		public final static Vector2f tileSize = new Vector2f(100, 100);
		// the size of the board in pixel
		public final static Vector2f boardSize = new Vector2f(boardDimension.x*tileSize.x, boardDimension.y*tileSize.y);
		
		// the initial count of tiles
		public final static int initialCountOfTiles = 50;
		// the count of tiles which gets added when the player achieves a perfect match
		public final static int countOfTilesToAddPerfectMatch = 2;
		
		public final static int pointsCorrectEdge = 10;
		public final static int pointsAllEdgesCorrect= 40;
	}
	
}
