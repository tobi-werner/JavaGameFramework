package de.tu_darmstadt.informatik.fop.main.dorfromantik.controller;

import java.util.List;

import org.newdawn.slick.geom.Vector2f;

import de.tu_darmstadt.informatik.fop.main.dorfromantik.animation.MoveByCubicAnimation;
import de.tu_darmstadt.informatik.fop.main.dorfromantik.animation.ScaleUpEaseOutBounceAnimation;
import de.tu_darmstadt.informatik.fop.main.dorfromantik.constants.Constants;
import de.tu_darmstadt.informatik.fop.main.dorfromantik.entity.Board;
import de.tu_darmstadt.informatik.fop.main.dorfromantik.entity.PlaceholderTile;
import de.tu_darmstadt.informatik.fop.main.dorfromantik.entity.Tile;
import eea.engine.entity.StateBasedEntityManager;

public class BoardController {
	/**
	 * The board which this controller handles.
	 */
	private Board board;
	
	/**
	 * Should textures be displayed?
	 */
	private boolean debug;
	
	/**
	 * Constructor. Initializes a new Board.
	 */
	public BoardController(boolean debug) {
		super();
		this.board = new Board();
		this.debug = debug;
	}
	
	/**
	 * @return the board.
	 */
	public Board getBoard() {
		return board;
	}

	/**
	 * Sets the board.
	 * @param board the new board
	 */
	public void setBoard(Board board) {
		this.board = board;
	}
	
	/**
	 * Updates the boards position so that the starting tile is in the center of the screen.
	 */
	public void centerBoardtoScreen(boolean animated) {
		float deltaX = -Constants.GamePlay.boardSize.x/2 + Constants.Base.windowMiddle.x - this.board.getPosition().x;
		float deltaY = -Constants.GamePlay.boardSize.y/2  + Constants.Base.windowMiddle.y - this.board.getPosition().y;
		
		this.updateBoardPosition(new Vector2f(deltaX, deltaY), animated);
	}
	
	/**a
	 * Generates a new random Tile at index.
	 * @param index the index of the new tile
	 * @param placedByPlayer if true the player placed the tile and the count of tiles left should decrease.
	 * @return true, if the tile was placed successfully.
	 */
	public boolean addTileAtIndex(Vector2f index, Tile tile, boolean placedByPlayer) {
		// if the index is not valid return
		if (placedByPlayer && !this.board.validTileAtIndex(index)) {
			FeedbackManager.getInstance().addFeedbackMessage("Kachel kann an dieser Position nicht platziert werden.");
			return false;
		}
		// if there are no more moves left return
		if (ScoreManager.getInstance().getTilesLeft() == 0 && placedByPlayer) return false;
		
		System.out.println(index);
		
		// update placeholder tiles. Must be called before the placing of the new
		// tile because the existing placeholder tile must be removed before.
		this.recalculatePlaceholderTilesAtIndex(index, placedByPlayer);
		
		// transform the index to a position corresponding to the board coordinate system
		Vector2f tile_position = this.board.transformIndexToScreenCoordinate(index);
		
		
		// get generated tile and place it in the gamestate
		this.board.setTileAtIndex(index, tile);
		tile.setIndex(index);
		tile.setPosition(tile_position);
		if (placedByPlayer) {
			//tile.runScaleAnimation();
			tile.runAnimation(new ScaleUpEaseOutBounceAnimation(1f));
		}

		// if a tile was placed by the player decrease the tiles-left counter
		if (placedByPlayer) {
			ScoreManager.getInstance().reduceTilesLeftCounter();
		}
		
		return true;
	}

	/**
	 * Transforms a position into an index.
	 * @param position the position to transform
	 * @return the index of the position
	 */
	public Vector2f getIndexFromScreenPosition(Vector2f position) {
		return this.board.transformScreenCoordinateToIndex(position);
	}
	
	/**
	 * Removes necessary existing placeholder tiles and adds new ones for the specified index.
	 * @param index placeholder tiles are placed around this index if there is no other tile.
	 * @param animated should the placement of placeholderTiles be animated?
	 */
	public void recalculatePlaceholderTilesAtIndex(Vector2f index, boolean animated) {
		// remove existing placeholder (if one exists).
		Tile placeholder = this.board.getTileAtIndex(index);
		if (placeholder != null && placeholder.getID().equals("placeholder")) {
			StateBasedEntityManager.getInstance().removeEntity(Constants.Base.GameScene, placeholder);
			this.board.removeTileAtIndex(index);
		}
		// Place new placeholder tiles at neighbouring indizes where no tile is present.
		List<Vector2f> neighborIndizes = this.board.getNeighbourIndizes(index);
		for (Vector2f neighborIndex : neighborIndizes) {
			if (this.board.getMap()[(int) neighborIndex.x][(int) neighborIndex.y] == null) {
				PlaceholderTile newPlaceholder = new PlaceholderTile(this.debug);
				this.board.setTileAtIndex(neighborIndex, newPlaceholder);
				Vector2f tile_position = this.board.transformIndexToScreenCoordinate(neighborIndex);
				newPlaceholder.setIndex(index);
				newPlaceholder.setPosition(tile_position);
				StateBasedEntityManager.getInstance().addEntity(Constants.Base.GameScene, newPlaceholder);
				
				if (animated) newPlaceholder.runAnimation(new ScaleUpEaseOutBounceAnimation(2f));
			}
		}	
	}

	/**
	 * Move the board and all its tiles based on the given delta.
	 * @param delta the vector by which the board should be moved
	 */
	public void updateBoardPosition(Vector2f delta, boolean animated) {
		// update board position
		if (animated) {
			this.board.runAnimation(new MoveByCubicAnimation(0.5f, delta));
		} else {
			this.board.setPosition(new Vector2f(this.board.getPosition().x + delta.x, this.board.getPosition().y + delta.y));
		}
		
		for (int row = 0; row < Constants.GamePlay.boardDimension.x; row++) {
			for (int col = 0; col < Constants.GamePlay.boardDimension.y; col++) {
				if (this.board.getMap()[row][col] == null) {
					continue;
				}
				// update tile position
				Tile tile = this.board.getMap()[row][col];
				if (animated) {
					tile.runAnimation(new MoveByCubicAnimation(0.5f, delta));
				} else {
					tile.setPosition(new Vector2f(tile.getPosition().x + delta.x, tile.getPosition().y + delta.y));
				}
			}
		}
	}
	
}
