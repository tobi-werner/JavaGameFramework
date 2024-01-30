package de.tu_darmstadt.informatik.fop.test.dorfromantik.student.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.newdawn.slick.geom.Vector2f;

import de.tu_darmstadt.informatik.fop.main.dorfromantik.constants.Constants;
import de.tu_darmstadt.informatik.fop.main.dorfromantik.controller.TextureManager;
import de.tu_darmstadt.informatik.fop.main.dorfromantik.entity.Board;
import de.tu_darmstadt.informatik.fop.main.dorfromantik.entity.PlaceholderTile;
import de.tu_darmstadt.informatik.fop.main.dorfromantik.entity.Tile;
import de.tu_darmstadt.informatik.fop.main.dorfromantik.model.TileData;

public class BoardTest {

	private Board board;
	private Tile tile;
	private List<PlaceholderTile> placeholderTiles = new ArrayList<>();
	
	@Before
	public void setup() {
		TextureManager.getInstance().setDebug(true);
		board = new Board(4,4);
		TileData data = new TileData(0, 1, 2, 3);
		tile = new Tile("", 0, 0, true);
		tile.setTileData(data);
		board.setTileAtIndex(new Vector2f(1,1), tile);
		
		for (Vector2f index : Arrays.asList(new Vector2f(0, 1), new Vector2f(1, 0), new Vector2f(2, 1), new Vector2f(1, 2))) {
			PlaceholderTile p = new PlaceholderTile(true);
			board.setTileAtIndex(index, p);
			this.placeholderTiles.add(p);
		}
	}
	
	@Test
	public final void testConstructor() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
	        new Board(3, 3);
	    });
		
		String expectedMessage = "Die BoardDimension muss durch 2 Teilbar sein"; // Variables are removed
	    String actualMessage = exception.getMessage();

	    assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	public final void testGetTileAtIndex() {
		assertNull(board.getTileAtIndex(new Vector2f(0, -1)));
		assertNull(board.getTileAtIndex(new Vector2f(-1, 0)));
		
		assertEquals("The returned tile does not match", tile, board.getTileAtIndex(new Vector2f(1,1)));
	}
	
	@Test
	public final void testSetTileAtIndex() {
		assertFalse(board.setTileAtIndex(new Vector2f(0, -1), tile));
		assertFalse(board.setTileAtIndex(new Vector2f(-1, 0), tile));
		
		assertTrue(board.setTileAtIndex(new Vector2f(0, 1), tile));
	}
	
	@Test
	public final void testTransformScreenCoordinateToIndex() {
		Vector2f index = board.transformScreenCoordinateToIndex(Constants.Base.zeroVector);
		assertEquals(Constants.Base.zeroVector, index);
		
		Vector2f index2 = board.transformScreenCoordinateToIndex(new Vector2f(Constants.GamePlay.tileSize.x+10, Constants.GamePlay.tileSize.y+10));
		assertEquals(new Vector2f(1,1), index2);
	}
	
	@Test
	public final void testTransformIndexToScreenCoordinate() {
		Vector2f position = board.transformScreenCoordinateToIndex(Constants.Base.zeroVector);
		assertEquals(Constants.Base.zeroVector, position);
		
		Vector2f position2 = board.transformScreenCoordinateToIndex(new Vector2f(Constants.GamePlay.tileSize.x-10, Constants.GamePlay.tileSize.y-10));
		assertEquals(new Vector2f(1,1), position2);		
	}
	
	@Test
	public final void testValidTileAtIndex() {
		// Out of bounds
		assertFalse(board.validTileAtIndex(new Vector2f(0, -1)));
		assertFalse(board.validTileAtIndex(new Vector2f(-1, 0)));
		assertFalse(board.validTileAtIndex(new Vector2f(4, 2)));
		assertFalse(board.validTileAtIndex(new Vector2f(2, 4)));
		
		// Valide
		assertTrue(board.validTileAtIndex(new Vector2f(0,1)));
		assertTrue(board.validTileAtIndex(new Vector2f(2,1)));
		assertTrue(board.validTileAtIndex(new Vector2f(1,0)));
		assertTrue(board.validTileAtIndex(new Vector2f(1,2)));
	}
	
	@Test
	public final void testRemoveTileAtIndex() {
		Vector2f index = new Vector2f(1,1);
		assertEquals(board.getTileAtIndex(index), tile);
		board.removeTileAtIndex(index);
		assertNull(board.getTileAtIndex(index));
	}
	
}
