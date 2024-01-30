package de.tu_darmstadt.informatik.fop.main.dorfromantik.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.newdawn.slick.geom.Vector2f;

import de.tu_darmstadt.informatik.fop.main.dorfromantik.constants.Constants;
import de.tu_darmstadt.informatik.fop.main.dorfromantik.entity.Board;
import de.tu_darmstadt.informatik.fop.main.dorfromantik.entity.Tile;

/**
 * Handles Scores and Tiles left Counter.
 */
public class ScoreManager {
	/**
	 * Singleton
	 */
	private static final ScoreManager score = new ScoreManager();

	private int currentScore = 0;
	/**
	 * count of tiles which the player can place.
	 */
	private int tilesLeft = Constants.GamePlay.initialCountOfTiles;
	
	/**
	 * List of scores of games which the player has played in the past.
	 */
	private ArrayList<Integer> listOfScores = new ArrayList<>();
	
	public ScoreManager() {
		super();
	}
	
	public static ScoreManager getInstance() {
		return score;
	}
	
	/**
	 * @return the highscore, 0 if no game was played yet.
	 */
	public int getHighScore() {
		if (listOfScores == null || listOfScores.size() == 0) return 0;
		return Collections.max(this.listOfScores);
	}
	
	/**
	 * @return score.
	 */
	public int getScore() {
		return this.currentScore;
	}
	
	/**
	 * Returns the scores for the n latest games played.
	 * @param n count of scores to return.
	 * @return list of scores.
	 */
	public List<Integer> getNLatestScores(int n) {
		if (this.listOfScores == null || this.listOfScores.isEmpty() || n == 0) return new ArrayList<>();
		if (this.listOfScores.size() <= n) return this.listOfScores;
		
		return this.listOfScores.subList(this.listOfScores.size()-n, this.listOfScores.size());
	}
	
	/**
	 * Returns the top n scores.
	 * @param n count of scores to return.
	 * @return list of scores.
	 */
	public List<Integer> getNMaxScores(int n) {
		if (this.listOfScores == null || this.listOfScores.isEmpty() || n == 0) return new ArrayList<>();

		List<Integer> copyScores = new ArrayList<>(this.listOfScores);
		Collections.sort(copyScores, Collections.reverseOrder());
		
		if (copyScores.size() <= n) return copyScores;
		
		return copyScores.subList(0, n);
	}
	
	/**
	 * Saves the current score to a text file at the given path.
	 * @param path Path of the text file.
	 */
	public void saveScore(String path) {
		System.out.println("Score"+getScore());
		this.listOfScores.add(this.getScore());
		
		try(FileWriter writer = new FileWriter(path, true);
			    BufferedWriter bwriter = new BufferedWriter(writer);
			    PrintWriter file = new PrintWriter(bwriter))
		{
			System.out.println("Save Score: "+this.getScore());
			System.out.println("Save Score: "+ScoreManager.getInstance().getScore());
		    file.println(String.valueOf(this.getScore()));
		} catch (IOException e) {
		    //exception handling left as an exercise for the reader
		}
	}
	
	/**
	 * Loads a list of scores from a text file at the path into the listOfScores.
	 * @param path Path file of the text file.
	 */
	public void loadScores(String path) {
		ArrayList<Integer> listOfScores = new ArrayList<>();
		try(BufferedReader reader = new BufferedReader(new FileReader(path))) {
			String line;
			while ((line = reader.readLine()) != null) {
				try {
			        listOfScores.add(Integer.valueOf(line));
			    } catch (NumberFormatException nfe) {
			        continue;
			    }
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.listOfScores = listOfScores;
	}
	
	/**
	 * Updates the score according to the tiles at the given index.
	 * @param board the board.
	 * @param index the index.
	 */
	public void updateScore(Board board, Vector2f index) {
		int countOfMatchingEdges = board.getCountOfMatchingEdgesForIndex(index);
		this.currentScore += countOfMatchingEdges * Constants.GamePlay.pointsCorrectEdge;
		// Neighbours of neighbours are checked for perfect points too. (hence the 2).
		this.currentScore += this.calculateNeighbouringPoints(board, index, 2);
	}
	
	/**
	 * recursive function which handles the neighbor score calculation.
	 * @param board the board.
	 * @param index the index. 
	 * @param depth how many neighboring tiles should be visited recursively?
	 * @return score for neighboring tiles
	 */
	private int calculateNeighbouringPoints(Board board, Vector2f index, int depth) {
		if (depth == 0) return 0;
		
		int score = 0;
		
		int countOfMatchingEdges = board.getCountOfMatchingEdgesForIndex(index);
		if (countOfMatchingEdges == 4) {
			score += Constants.GamePlay.pointsAllEdgesCorrect;
			this.addTilesToTilesLeft(Constants.GamePlay.countOfTilesToAddPerfectMatch);
			// display message
			FeedbackManager.getInstance().addFeedbackMessage("Perfekt! +"+Constants.GamePlay.countOfTilesToAddPerfectMatch+" neue Kacheln erhalten");
		}
		// visit neighbour tiles recursively.
		for (Tile neighbour: board.getNeighbors(index)) {
			if (neighbour == null) continue;
			score += this.calculateNeighbouringPoints(board, neighbour.getIndex(), depth-1);
		}
		return score;
	}
	
	/**
	 * @return tiles left.
	 */
	public int getTilesLeft() {
		return tilesLeft;
	}

	/**
	 * Adds count to the count of tiles left.
	 * @param count
	 */
	public void addTilesToTilesLeft(int count) {
		this.tilesLeft+=count;
	}
	
	/**
	 * reduces tiles left counter by one.
	 */
	public void reduceTilesLeftCounter() {
		this.tilesLeft-=1;
		if (this.tilesLeft == 5) FeedbackManager.getInstance().addFeedbackMessage("Noch 5 Kacheln übrig");
	}
	
	/**
	 * Resets the tile counter and the score.
	 */
	public void reset() {
		this.currentScore = 0;
		this.tilesLeft = Constants.GamePlay.initialCountOfTiles;
	}
	
	/**
	 * For testing purposes. Sets the list of scores to the given list.
	 * @param scores list of scores.
	 */
	public void setListOfScores(ArrayList<Integer> scores) {
		this.listOfScores = scores;
	}
	
	/**
	 * For testing purposes.
	 * @return List of scores.
	 */
	public List<Integer> getListOfScores() {
		return this.listOfScores;
	}
}
