package de.tu_darmstadt.informatik.fop.main.dorfromantik.factory;

import java.util.Random;

import de.tu_darmstadt.informatik.fop.main.dorfromantik.constants.Constants;
import de.tu_darmstadt.informatik.fop.main.dorfromantik.entity.PlaceholderTile;
import de.tu_darmstadt.informatik.fop.main.dorfromantik.entity.Tile;
import de.tu_darmstadt.informatik.fop.main.dorfromantik.model.GameData;
import de.tu_darmstadt.informatik.fop.main.dorfromantik.model.TileData;

/**
 * Creates Tiles.
 */
public class TileFactory {
	/**
	 * the next tile which the player can place on the board.
	 */
	private Tile previewTile;
	/**
	 * Should textures be displayed?
	 */
	private boolean debug;
	
	/**
	 * holds data loaded of the config file.
	 */
	private GameData gameData;

	public TileFactory(boolean debug) {
		super();
		this.debug = debug;
	}

	public TileFactory(GameData gameData, boolean debug) {
		super();
		
		this.debug = debug;
		this.gameData = gameData;
		this.generatePreviewTile();
	}

	/**
	 * Generate a new PlaceholderTile.
	 * @return new PlaceholderTile.
	 */
	public PlaceholderTile getNewPlaceholderTile() {
		return new PlaceholderTile(this.debug);
	}
	
	/**
	 * Returns a new tile with texture and tile data.
	 * @param id represents the order of the textures in the texture atlas
	 * @return new tile
	 */
	public Tile generateTileWithId(int id) {		
		if (id >= Constants.Base.TileCount || id < 0) return null;
		int row = id % Constants.Base.TextureGridWidth;
		int col = Math.round(id/Constants.Base.TextureGridHeight);
		Tile tile = new Tile(String.format("Tile#%d", id), row, col, this.debug);
		if (this.gameData != null && this.gameData.getTileData().size() > 0)
			tile.setTileData(new TileData(gameData.getTileData().get(col*Constants.Base.TextureGridWidth+row))); 
		return tile;
	}
	
	/**
	 * Generates a new tile with random texture and tile data.
	 * @param min range for ID (Set to -1 for selecting between all tiles)
	 * @param max range for ID (Set to -1 for selecting between all tiles)
	 * @return random tile
	 */
	public Tile generateRandomTile(int min, int max) {
		Random r = new Random();
		int selectedTileIndex;
		if (min == -1 || max == -1) {
			selectedTileIndex = r.nextInt(Constants.Base.TileCount);
		}
		else {
			selectedTileIndex = r.nextInt(max-min) + min;
		}
		return generateTileWithId(selectedTileIndex);
	}
	
	public Tile generateStartingTile() {
		return generateRandomTile(gameData.getStartingTiles().get(0), gameData.getStartingTiles().get(1));
	}
	
	/**
	 * Generates a new Preview Tile.
	 * @return the newly generated preview tile
	 */
	public Tile generatePreviewTile() {
		// Get config data.
		int p1 = gameData.getDifficulty1Propability();
		int p2 = gameData.getDifficulty2Propability();
		int p3 = gameData.getDifficulty3Propability();
		
		// select random tiles based on the difficulty of the config.
		Random r = new Random();
		int tileId = r.nextInt(100);
		
		if (tileId < p1) {
			this.previewTile = generateRandomTile(gameData.getDifficulty1StartIndex(), gameData.getDifficulty1EndIndex());
		}
		else if (tileId < p1+p2) {
			this.previewTile = generateRandomTile(gameData.getDifficulty2StartIndex(), gameData.getDifficulty2EndIndex());
		}
		else if (tileId < p1+p2+p3) {
			this.previewTile = generateRandomTile(gameData.getDifficulty3StartIndex(), gameData.getDifficulty3EndIndex());
		}
		else {
			this.previewTile = generateRandomTile(gameData.getDifficulty4StartIndex(), gameData.getDifficulty4EndIndex());
		}

		return this.previewTile;
	}

	/**
	 * @return preview tile.
	 */
	public Tile getPreviewTile() {
		return previewTile;
	}
	
}
