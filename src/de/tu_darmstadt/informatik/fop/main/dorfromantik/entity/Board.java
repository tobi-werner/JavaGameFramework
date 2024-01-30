package de.tu_darmstadt.informatik.fop.main.dorfromantik.entity;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.geom.Vector2f;

import de.tu_darmstadt.informatik.fop.main.dorfromantik.constants.Constants;

/**
 * Handles the board logic.
 */
public class Board extends BaseEntity {

	private Vector2f boardSize;
	/**
	 * Stores all board tiles.
	 */
	private Tile[][] map;
	
	/**
	 * Constants for neighbor calculations.
	 */
	private final int INDEX_TOP = 0;
	private final int INDEX_RIGHT = 1;
	private final int INDEX_BOTTOM = 2;
	private final int INDEX_LEFT = 3;

	
	/**
	 * Initializes the map with the size defined in constants.
	 * Throws if the board dimension is not divisible by 2
	 */
	public Board() {
		this(Constants.GamePlay.boardDimension.x, Constants.GamePlay.boardDimension.y);
	}
	
	/**
	 * Initializes the map with the given size. Throws an IllegalArgumentException if rows and cols are not divisible by 2.
	 * @param rows count of rows.
	 * @param cols count of columns.
	 */
	public Board(float rows, float cols) {
		super("board", true);
		
		this.boardSize = new Vector2f(rows, cols);
		
		if(rows%2 != 0 || cols%2 != 0) {
	        throw new IllegalArgumentException(String.format("Die BoardDimension muss durch 2 Teilbar sein: %f, %f", boardSize.x, boardSize.y));
	    }
		
		this.map = new Tile[(int) rows][(int) cols];
	}
	
	/**
	 * @return the TileMap.
	 */
	public Tile[][] getMap() {
		return map;
	}
	
	/**
	 * Returns the tile at the given index. Returns null if the index is invalid.
	 * @param index index of the tile
	 * @return the tile at index. null if index invalid
	 */
	public Tile getTileAtIndex(Vector2f index) {
		if (index.x < 0 || index.y < 0 || index.x >= boardSize.x || index.y >= boardSize.y) {
			return null;
		}
		return map[(int) index.x][(int) index.y];
	}
	
	/**
	 * Sets a given tile at the index.
	 * @param index
	 * @param tile
	 * @return True if tile placed successfully.
	 */
	public boolean setTileAtIndex(Vector2f index, Tile tile) {
		if (index.x < 0 || index.y < 0 || index.x >= boardSize.x || index.y >= boardSize.y) {
			return false;
		}
		if (tile != null) tile.setIndex(index);
		this.map[(int) index.x][(int) index.y] = tile;
		return true;
	}

	/**
	 * @return the board offset on the screen.
	 */
	private Vector2f getBoardOffset() {
		return new Vector2f(this.getPosition().x, this.getPosition().y);
	}

	/**
	 * Transforms the given position to its corresponding index.
	 * @param position the position.
	 * @return the index corresponding to the position.
	 */
	public Vector2f transformScreenCoordinateToIndex(Vector2f position) {
		float x = position.x - this.getBoardOffset().x;
		float y = position.y - this.getBoardOffset().y;
		return new Vector2f(Math.round(x / Constants.GamePlay.tileSize.x), Math.round(y / Constants.GamePlay.tileSize.y));
	}

	/**
	 * Transforms the given index to its corresponding position.
	 * @param index the index.
	 * @return the position corresponding to the index.
	 */
	public Vector2f transformIndexToScreenCoordinate(Vector2f index) {
		return new Vector2f(this.getBoardOffset().x + index.x * Constants.GamePlay.tileSize.x,
				this.getBoardOffset().y + index.y * Constants.GamePlay.tileSize.y);
	}
	
	/**
	 * Checks if a tile can be placed at the given index.
	 * @param index the index.
	 * @return true if a tile can be placed at this index.
	 */
	public boolean validTileAtIndex(Vector2f index) {
		return this.getTileAtIndex(index) != null && this.getTileAtIndex(index).getID().equals("placeholder");
	}
	
	/**
	 * Sets the tile to null at the given index.
	 * @param index the index.
	 */
	public void removeTileAtIndex(Vector2f index) {
		this.setTileAtIndex(index, null);
	}
	
	/*** SCORE (Stufe)	 */
	
	/**
	 * Return the neighbouring indices of an index. An Element is null if there is no neighbor.
	 * @param index the index.
	 * @return List of indices.
	 */
	public List<Vector2f> getNeighbourIndizes(Vector2f index) {
		List<Vector2f> neighbourIndizes = new ArrayList<>();
		// Top
		if (index.y-1 >= 0) {
			neighbourIndizes.add(new Vector2f(index.x, index.y-1));
		} else {
			neighbourIndizes.add(null);
		}
		// Right
		if (index.x+1 < boardSize.x) {
			neighbourIndizes.add(new Vector2f(index.x+1, index.y));
		} else {
			neighbourIndizes.add(null);
		}
		// Bottom
		if (index.y+1 < boardSize.y) {
			neighbourIndizes.add(new Vector2f(index.x, index.y+1));
		} else {
			// Position of index is important (Top, Right, Down, Left)
			neighbourIndizes.add(null);
		}
		// Left
		if (index.x-1 >= 0) {
			neighbourIndizes.add(new Vector2f(index.x-1, index.y));
		} else {
			neighbourIndizes.add(null);
		}
		return neighbourIndizes;
	}
	
	/**
	 * Calculates the count of matching edges for an index.
	 * @param index the index.
	 * @return count of matching edges for the index.
	 */
	public int getCountOfMatchingEdgesForIndex(Vector2f index) {
		int count = 0;
		// Top, Right, Bottom, Left
		Tile tile = this.getTileAtIndex(index);
		if (tile == null) return count;
		
		Tile[] neighbours = this.getNeighbors(index);
		
		if (neighbours[INDEX_TOP] != null && tile.getTop() == neighbours[INDEX_TOP].getBottom()) count++;
		if (neighbours[INDEX_RIGHT] != null && tile.getRight() == neighbours[INDEX_RIGHT].getLeft()) count++;
		if (neighbours[INDEX_BOTTOM] != null && tile.getBottom() == neighbours[INDEX_BOTTOM].getTop()) count++;
		if (neighbours[INDEX_LEFT] != null && tile.getLeft() == neighbours[INDEX_LEFT].getRight()) count++;
		return count;
	}
	
	/**
	 * Returns the list of neighbors. An Element is null if there is no neighbor.
	 * @param index the index.
	 * @return List of neighboring tiles.
	 */
	public Tile[] getNeighbors(Vector2f index) {
		Tile[] neighbors = new Tile[4];
		
		// TODO: use getNeighbourIndizes
		List<Vector2f> neighbouringIndizes = this.getNeighbourIndizes(index);
		for (int i = 0; i < neighbouringIndizes.size(); i++) {
			Vector2f neighbourIndex = neighbouringIndizes.get(i);
			if (neighbourIndex == null) {
				neighbors[i] = null;
				continue;
			}
			Tile neighborTile = this.map[(int) neighbourIndex.x][(int) neighbourIndex.y];
			if (neighborTile == null || neighborTile.getID().equals("placeholder")) {
				neighbors[i] = null;
				continue;
			}
			neighbors[i] = neighborTile;
		}
		return neighbors;
	}

	/**
	 * Returns all placeholder tiles.
	 * @return List of PlaceholderTiles.
	 */
	public List<PlaceholderTile> getPlaceholderTiles() {
		List<PlaceholderTile> placeholderTiles = new ArrayList<>();
		for (int row = 0; row < this.getMap().length; row++) {
			for (int col = 0; col < this.getMap()[row].length; col++) {
				Tile t = this.getTileAtIndex(new Vector2f(row, col));
				if (t != null && t.getID() == "placeholder")
					placeholderTiles.add((PlaceholderTile) t);
			}
	    }
		return placeholderTiles;
	}
	
	@Override
	public boolean equals(Object o) {
	    if (o == this)
	        return true;
	    if (!(o instanceof Board))
	        return false;
	    Board other = (Board)o;
	    if ((other.getMap().length != this.getMap().length) || (other.getMap()[0].length != this.getMap()[0].length))
	    	return false;
	    // board size is the same
	    for (int row = 0; row < this.getMap().length; row++) {
			for (int col = 0; col < this.getMap()[row].length; col++) {
				if (!(this.getTileAtIndex(new Vector2f(row, col)).equals(other.getTileAtIndex(new Vector2f(row, col))))) {
					return false;
				}
			}
	    }
	    return true;
	}

}