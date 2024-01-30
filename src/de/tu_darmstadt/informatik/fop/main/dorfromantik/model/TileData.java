package de.tu_darmstadt.informatik.fop.main.dorfromantik.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Holds the Edge data of a tile
 */
public class TileData implements Cloneable {
	private int top = 0;
	private int right = 0;
	private int bottom = 0;
	private int left = 0;
	
	public TileData(int top, int right, int bottom, int left) {
		this.top = top;
		this.right = right;
		this.bottom = bottom;
		this.left = left;
	}
	
	/**
	 * Initializes with the given String. The String must have the format "x x x x" where x is a number.
	 * @param data the string.
	 */
	public TileData(String data) {
		// the checks if the data string has the correct format happens in the parser class
		List<Integer> values = Arrays.asList(data.split(" ")).stream().map(Integer::parseInt).collect(Collectors.toList());
		this.top = values.get(0);
		this.right = values.get(1);
		this.bottom = values.get(2);
		this.left = values.get(3);
	}
	
	/**
	 * Rotates the tile data.
	 */
	public void rotateTileData() {
		int buffer = getTop();
		this.setTop(getLeft());
		this.setLeft(getBottom());
		this.setBottom(getRight());
		this.setRight(buffer);
	}
	
	public int getTop() {
		return top;
	}

	public int getRight() {
		return right;
	}

	public int getBottom() {
		return bottom;
	}

	public int getLeft() {
		return left;
	}
	
	public void setTop(int top) {
		this.top = top;
	}

	public void setRight(int right) {
		this.right = right;
	}

	public void setBottom(int bottom) {
		this.bottom = bottom;
	}

	public void setLeft(int left) {
		this.left = left;
	}
	
	public TileData clone() {
		return new TileData(getTop(), getRight(), getBottom(), getLeft());
	}
	
	@Override
	public boolean equals(Object o) {
	    if (o == this)
	        return true;
	    if (!(o instanceof TileData))
	        return false;
	    TileData other = (TileData)o;
	    return this.getBottom() == other.getBottom() && this.getLeft() == other.getLeft() && this.getTop() == other.getTop() && this.getRight() == other.getRight();
	}
	
	@Override
	public String toString() {
		return String.format("T: %d, R: %d, B: %d, L: %d", this.getTop(), this.getRight(), this.getBottom(), this.getLeft());
	}
}
