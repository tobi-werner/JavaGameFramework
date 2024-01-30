package de.tu_darmstadt.informatik.fop.test.dorfromantik.tutor.testcases;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.newdawn.slick.geom.Vector2f;

import de.tu_darmstadt.informatik.fop.main.dorfromantik.controller.TextureManager;
import de.tu_darmstadt.informatik.fop.main.dorfromantik.entity.Board;
import de.tu_darmstadt.informatik.fop.main.dorfromantik.entity.PlaceholderTile;
import de.tu_darmstadt.informatik.fop.main.dorfromantik.entity.Tile;
import de.tu_darmstadt.informatik.fop.main.dorfromantik.model.TileData;

public class BoardTestExtended {

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

	/***** STUFE 2 *****/
	@Test
	public final void testGetNeighbourIndizes() {
		// Top, Right, Bottom, Left
		List<Vector2f> expected = new ArrayList<>();
		expected.add(new Vector2f(1, 0));
		expected.add(new Vector2f(2, 1));
		expected.add(new Vector2f(1, 2));
		expected.add(new Vector2f(0, 1));
		
		assertEquals(expected, board.getNeighbourIndizes(new Vector2f(1,1)));
	}
	
	@Test
	public final void testGetCountOfMatchingEdgesForIndex() {
		assertEquals(0, board.getCountOfMatchingEdgesForIndex(new Vector2f(1,1)));
		
		// Place matching neighbour tile
		TileData data = new TileData(0, 0, 0, 0);
		Tile tile1 = new Tile("", 0, 0, true);
		tile1.setTileData(data);
		board.setTileAtIndex(new Vector2f(1,0), tile1);
		
		assertEquals(1, board.getCountOfMatchingEdgesForIndex(new Vector2f(1,1)));
		
		// Place non matching neighbour tile
		TileData data2 = new TileData(5, 5, 5, 5);
		Tile tile2 = new Tile("", 0, 0, true);
		tile2.setTileData(data2);
		board.setTileAtIndex(new Vector2f(1,2), tile2);
		
		assertEquals(1, board.getCountOfMatchingEdgesForIndex(new Vector2f(1,1)));
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public final void testGetNeighbors() {
		assertEquals(new Tile[4], board.getNeighbors(new Vector2f(1,1)));
		
		// Place matching neighbour tile
		TileData data = new TileData(0, 0, 0, 0);
		Tile tile1 = new Tile("", 0, 0, true);
		tile1.setTileData(data);
		board.setTileAtIndex(new Vector2f(1,0), tile1);
		
		assertEquals(new Tile[] {tile1, null, null, null}, board.getNeighbors(new Vector2f(1,1)));
	}
	
	@Test
	public final void testGetPlaceholderTiles() {
		assertEquals(4, board.getPlaceholderTiles().size());
	}
		
	
}
