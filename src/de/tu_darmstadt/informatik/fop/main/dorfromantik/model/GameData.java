package de.tu_darmstadt.informatik.fop.main.dorfromantik.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds data of the config file.
 */
public class GameData {

	/**
	 * Indices of starting tiles.
	 */
	private List<Integer> startingTiles = new ArrayList<>();
	/**
	 * Indices and Propability of the first group of tiles.
	 */
	private int difficulty1StartIndex = 0;
	private int difficulty1EndIndex = 0;
	private int difficulty1Propability = 0;
	
	/**
	 * Indices and Propability of the second group of tiles.
	 */
	private int difficulty2StartIndex = 0;
	private int difficulty2EndIndex = 0;
	private int difficulty2Propability = 0;
	
	/**
	 * Indices and Propability of the third group of tiles.
	 */
	private int difficulty3StartIndex = 0;
	private int difficulty3EndIndex = 0;
	private int difficulty3Propability = 0;
	
	/**
	 * Indices and Propability of the fourth group of tiles.
	 */
	private int difficulty4StartIndex = 0;
	private int difficulty4EndIndex = 0;
	private int difficulty4Propability = 0;
	
	private List<String> tileData = new ArrayList<>();

	public List<Integer> getStartingTiles() {
		return startingTiles;
	}

	public void setStartingTiles(List<Integer> startingTiles) {
		this.startingTiles = startingTiles;
	}

	public int getDifficulty1StartIndex() {
		return difficulty1StartIndex;
	}

	public void setDifficulty1StartIndex(int difficulty1StartIndex) {
		this.difficulty1StartIndex = difficulty1StartIndex;
	}

	public int getDifficulty1EndIndex() {
		return difficulty1EndIndex;
	}

	public void setDifficulty1EndIndex(int difficulty1EndIndex) {
		this.difficulty1EndIndex = difficulty1EndIndex;
	}

	public int getDifficulty1Propability() {
		return difficulty1Propability;
	}

	public void setDifficulty1Propability(int difficulty1Propability) {
		this.difficulty1Propability = difficulty1Propability;
	}

	public int getDifficulty2StartIndex() {
		return difficulty2StartIndex;
	}

	public void setDifficulty2StartIndex(int difficulty2StartIndex) {
		this.difficulty2StartIndex = difficulty2StartIndex;
	}

	public int getDifficulty2EndIndex() {
		return difficulty2EndIndex;
	}

	public void setDifficulty2EndIndex(int difficulty2EndIndex) {
		this.difficulty2EndIndex = difficulty2EndIndex;
	}

	public int getDifficulty2Propability() {
		return difficulty2Propability;
	}

	public void setDifficulty2Propability(int difficulty2Propability) {
		this.difficulty2Propability = difficulty2Propability;
	}

	public int getDifficulty3StartIndex() {
		return difficulty3StartIndex;
	}

	public void setDifficulty3StartIndex(int difficulty3StartIndex) {
		this.difficulty3StartIndex = difficulty3StartIndex;
	}

	public int getDifficulty3EndIndex() {
		return difficulty3EndIndex;
	}

	public void setDifficulty3EndIndex(int difficulty3EndIndex) {
		this.difficulty3EndIndex = difficulty3EndIndex;
	}

	public int getDifficulty3Propability() {
		return difficulty3Propability;
	}

	public void setDifficulty3Propability(int difficulty3Propability) {
		this.difficulty3Propability = difficulty3Propability;
	}

	public int getDifficulty4StartIndex() {
		return difficulty4StartIndex;
	}

	public void setDifficulty4StartIndex(int difficulty4StartIndex) {
		this.difficulty4StartIndex = difficulty4StartIndex;
	}

	public int getDifficulty4EndIndex() {
		return difficulty4EndIndex;
	}

	public void setDifficulty4EndIndex(int difficulty4EndIndex) {
		this.difficulty4EndIndex = difficulty4EndIndex;
	}

	public int getDifficulty4Propability() {
		return difficulty4Propability;
	}

	public void setDifficulty4Propability(int difficulty4Propability) {
		this.difficulty4Propability = difficulty4Propability;
	}

	public List<String> getTileData() {
		return tileData;
	}

	public void setTileData(List<String> tileData) {
		this.tileData = tileData;
	}
}
