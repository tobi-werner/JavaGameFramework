package de.tu_darmstadt.informatik.fop.test.dorfromantik.tutor.testcases;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.newdawn.slick.geom.Vector2f;

import de.tu_darmstadt.informatik.fop.main.dorfromantik.constants.Constants;
import de.tu_darmstadt.informatik.fop.main.dorfromantik.controller.ScoreManager;
import de.tu_darmstadt.informatik.fop.main.dorfromantik.controller.TextureManager;
import de.tu_darmstadt.informatik.fop.main.dorfromantik.entity.Board;
import de.tu_darmstadt.informatik.fop.main.dorfromantik.entity.Tile;
import de.tu_darmstadt.informatik.fop.main.dorfromantik.model.TileData;

public class ScoreManagerTest {

	ArrayList<Integer> scores = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9,10));
	
	@Rule
	public TemporaryFolder folder= new TemporaryFolder();
	
	private String path;
	
	@Before
	public final void setup() {
		path = folder.getRoot().getAbsolutePath()+"/scores.txt";
	}
	
	@Test
	public final void testGetHighScore() {
		ScoreManager.getInstance().setListOfScores(new ArrayList<Integer>());
		assertEquals("Highscore sollte 0 sein, da nichts geladen wurde", 0, ScoreManager.getInstance().getHighScore());
		
		ScoreManager.getInstance().setListOfScores(scores);
		assertEquals("Highscore sollte 10 sein", Collections.max(scores), (Integer) ScoreManager.getInstance().getHighScore());
	}
	
	@Test
	public final void testGetNLatestScores() {
		ScoreManager.getInstance().setListOfScores(new ArrayList<Integer>());
		assertEquals("Liste sollte leer sein, da scores nicht geladen wurden", new ArrayList<Integer>(), ScoreManager.getInstance().getNLatestScores(5));
		
		ScoreManager.getInstance().setListOfScores(scores);
		
		assertEquals("Liste sollte leer sein, da n=0", new ArrayList<Integer>(), ScoreManager.getInstance().getNLatestScores(0));
		assertEquals("Lists sollte scores entsprechen", scores, ScoreManager.getInstance().getNLatestScores(100));
		assertEquals("Liste sollte, 6,7,8,9,10 sein", scores.subList(5, 10), ScoreManager.getInstance().getNLatestScores(5));
	}
	
	@Test
	public final void testGetNMaxScores() {
		ScoreManager.getInstance().setListOfScores(new ArrayList<Integer>());
		assertEquals("Liste sollte leer sein, da scores nicht geladen wurden", new ArrayList<Integer>(), ScoreManager.getInstance().getNLatestScores(5));
		
		ScoreManager.getInstance().setListOfScores(scores);
		
		assertEquals("Liste sollte leer sein, da n=0", new ArrayList<Integer>(), ScoreManager.getInstance().getNMaxScores(0));
		List<Integer> copyScores = new ArrayList<>(this.scores);
		Collections.sort(copyScores, Collections.reverseOrder());
		assertEquals("Lists sollte scores absteigend sortiert entsprechen", copyScores, ScoreManager.getInstance().getNMaxScores(100));
		assertEquals("Liste sollte, 10,9,8,7,6 sein", copyScores.subList(0, 5), ScoreManager.getInstance().getNMaxScores(5));
	}
	
	@Test
	public final void testSaveScore() {
		ScoreManager.getInstance().reset();
		ScoreManager.getInstance().saveScore(path);
		
		// reset score list
		ScoreManager.getInstance().setListOfScores(new ArrayList<Integer>());
		assertEquals("Scoreliste sollte leer sein", new ArrayList<Integer>(), ScoreManager.getInstance().getNLatestScores(10));
		
		// load scores again
		ScoreManager.getInstance().loadScores(path);
		List<Integer> expected = new ArrayList<>(Arrays.asList(0));
		assertEquals("Scoreliste sollte 1-10 enthalten", expected, ScoreManager.getInstance().getListOfScores());
	
	}
	
	@Test
	public final void testLoadScores() {

		try {
			File tempFile = folder.newFile("scores.txt");
			FileWriter writer = new FileWriter(tempFile);
			for (int i = 1; i <= 10; i++) {
				writer.write(i+"\n");
			}
			writer.write("This should be ignored when reading the file");
		    writer.close();
		} catch (IOException e) {}
	      
		
		ScoreManager.getInstance().setListOfScores(new ArrayList<Integer>());
		assertEquals("ScoreListe sollte leer sein, da nichts geladen ist", new ArrayList<Integer>(), ScoreManager.getInstance().getNLatestScores(10));
		
		ScoreManager.getInstance().loadScores(path);
		assertEquals("Highscore sollte 10 sein", 10, ScoreManager.getInstance().getHighScore());

		assertEquals("ScoreList stimmt nicht überein", scores, ScoreManager.getInstance().getNLatestScores(10));
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
		
		// Bottom Tile does not fit
		Tile tile3 = new Tile("", 0, 0, true);
		tile3.setTileData(new TileData(5,5,5,5));
		board.setTileAtIndex(new Vector2f(1,2), tile3);

		ScoreManager.getInstance().updateScore(board, new Vector2f(1,2));
		assertEquals("Score sollte 10 sein.", Constants.GamePlay.pointsCorrectEdge, ScoreManager.getInstance().getScore());

	}
	
	@Test
	public final void testUpdateScoreBonus() {
		TextureManager.getInstance().setDebug(true);
		Board board = new Board(4,4);
		// Bottom Tile fits
		Tile tile = new Tile("", 0, 0, true);
		tile.setTileData(new TileData(1, 1, 1, 1));
		board.setTileAtIndex(new Vector2f(1,2), tile);
		// Top Tile fits
		Tile tile2 = new Tile("", 0, 0, true);
		tile2.setTileData(new TileData(1, 1, 1, 1));
		board.setTileAtIndex(new Vector2f(1,0), tile2);
		// Left Tile fits
		Tile tile3 = new Tile("", 0, 0, true);
		tile3.setTileData(new TileData(1, 1, 1, 1));
		board.setTileAtIndex(new Vector2f(0,1), tile3);
		// Right Tile fits
		Tile tile4 = new Tile("", 0, 0, true);
		tile4.setTileData(new TileData(1, 1, 1, 1));
		board.setTileAtIndex(new Vector2f(2,1), tile4);
		// Center Tile
		Tile center = new Tile("", 0, 0, true);
		center.setTileData(new TileData(1, 1, 1, 1));
		board.setTileAtIndex(new Vector2f(1,1), center);
		
		ScoreManager.getInstance().reset();
		assertEquals("Score sollte 0 sein", 0, ScoreManager.getInstance().getScore());
		ScoreManager.getInstance().updateScore(board, new Vector2f(1,1));
		int expected = 4*Constants.GamePlay.pointsCorrectEdge+Constants.GamePlay.pointsAllEdgesCorrect;
		assertEquals("Score sollte 80 sein.", expected, ScoreManager.getInstance().getScore());
	}
	
	@Test
	public final void testUpdateScoreBonusMatchingNeighbours() {
		TextureManager.getInstance().setDebug(true);
		Board board = new Board(6,6);
		
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				Tile tile = new Tile("", 0, 0, true);
				tile.setTileData(new TileData(1, 1, 1, 1));
				board.setTileAtIndex(new Vector2f(i, j), tile);
			}
		}
		
		ScoreManager.getInstance().reset();
		assertEquals("Score sollte 0 sein", 0, ScoreManager.getInstance().getScore());
		ScoreManager.getInstance().updateScore(board, new Vector2f(2,2));
		// Perfect matching edges
		int expected = 4*Constants.GamePlay.pointsCorrectEdge+Constants.GamePlay.pointsAllEdgesCorrect;
		// Perfect matching edges on all neighboring tiles
		expected+= 4*Constants.GamePlay.pointsAllEdgesCorrect;
		assertEquals("Score sollte 80 sein.", expected, ScoreManager.getInstance().getScore());
	}
	
	@Test
	public final void testTilesLeftCounter() {
		ScoreManager.getInstance().reset();
		assertEquals("tiles Left counter sollte zurückgesetzt sein.", Constants.GamePlay.initialCountOfTiles, ScoreManager.getInstance().getTilesLeft());
		ScoreManager.getInstance().addTilesToTilesLeft(1);
		assertEquals("tiles Left counter sollte Initial+1 sein", Constants.GamePlay.initialCountOfTiles+1, ScoreManager.getInstance().getTilesLeft());
		ScoreManager.getInstance().reduceTilesLeftCounter();
		assertEquals("tiles Left counter sollte Initial sein", Constants.GamePlay.initialCountOfTiles, ScoreManager.getInstance().getTilesLeft());
		ScoreManager.getInstance().addTilesToTilesLeft(10);
		assertEquals("tiles Left counter sollte Initial+10 sein", Constants.GamePlay.initialCountOfTiles+10, ScoreManager.getInstance().getTilesLeft());
		
	}
	
	@Test
	public final void testReset() {
		ScoreManager.getInstance().reset();
		assertEquals("Score sollte 0 sein", 0, ScoreManager.getInstance().getScore());
		assertEquals("tiles Left counter sollte zurückgesetzt sein.", Constants.GamePlay.initialCountOfTiles, ScoreManager.getInstance().getTilesLeft());
	}
}
