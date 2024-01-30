package de.tu_darmstadt.informatik.fop.test.dorfromantik.student.testcases;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;
import org.newdawn.slick.geom.Vector2f;

import de.tu_darmstadt.informatik.fop.main.dorfromantik.constants.Constants;
import de.tu_darmstadt.informatik.fop.main.dorfromantik.controller.ScoreManager;
import de.tu_darmstadt.informatik.fop.main.dorfromantik.controller.TextureManager;
import de.tu_darmstadt.informatik.fop.main.dorfromantik.entity.Board;
import de.tu_darmstadt.informatik.fop.main.dorfromantik.entity.Tile;
import de.tu_darmstadt.informatik.fop.main.dorfromantik.model.TileData;

public class ScoreManagerTest {

	ArrayList<Integer> scores = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9,10));
	
	@Test
	public final void testGetHighScore() {
		ScoreManager.getInstance().setListOfScores(new ArrayList<Integer>());
		assertEquals("Highscore sollte 0 sein, da nichts geladen wurde", 0, ScoreManager.getInstance().getHighScore());
		
		ScoreManager.getInstance().setListOfScores(scores);
		assertEquals("Highscore sollte 10 sein", Collections.max(scores), (Integer) ScoreManager.getInstance().getHighScore());
	}
	
	@Test
	public final void testUpdateScore() {
		TextureManager.getInstance().setDebug(true);
		Board board = new Board(4,4);
		Tile tile = new Tile("", 0, 0, true);
		tile.setTileData(new TileData(1, 1, 1, 1));
		board.setTileAtIndex(new Vector2f(1,1), tile);
		// Top Tile fits
		Tile tile2 = new Tile("", 0, 0, true);
		tile2.setTileData(new TileData(1, 1, 1, 1));
		board.setTileAtIndex(new Vector2f(1,0), tile2);
		
		ScoreManager.getInstance().reset();
		assertEquals("Score sollte 0 sein", 0, ScoreManager.getInstance().getScore());
		ScoreManager.getInstance().updateScore(board, new Vector2f(1,1));
		assertEquals("Score sollte 10 sein.", Constants.GamePlay.pointsCorrectEdge, ScoreManager.getInstance().getScore());
	}
	
	@Test
	public final void testTilesLeftCounter() {
		ScoreManager.getInstance().reset();
		assertEquals("tiles Left counter sollte zurückgesetzt sein.", Constants.GamePlay.initialCountOfTiles, ScoreManager.getInstance().getTilesLeft());
		ScoreManager.getInstance().addTilesToTilesLeft(1);
		assertEquals("tiles Left counter sollte Initial+1 sein", Constants.GamePlay.initialCountOfTiles+1, ScoreManager.getInstance().getTilesLeft());
	}
	
	@Test
	public final void testReset() {
		ScoreManager.getInstance().reset();
		assertEquals("Score sollte 0 sein", 0, ScoreManager.getInstance().getScore());
		assertEquals("tiles Left counter sollte zurückgesetzt sein.", Constants.GamePlay.initialCountOfTiles, ScoreManager.getInstance().getTilesLeft());
	}
}
