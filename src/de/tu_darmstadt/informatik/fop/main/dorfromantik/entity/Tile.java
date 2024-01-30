package de.tu_darmstadt.informatik.fop.main.dorfromantik.entity;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

import de.tu_darmstadt.informatik.fop.main.dorfromantik.controller.TextureManager;
import de.tu_darmstadt.informatik.fop.main.dorfromantik.model.TileData;

/**
 * Represents a tile.
 */
public class Tile extends BaseEntity {
	
	/**
	 * index of the tile.
	 */
	private Vector2f index;
	/**
	 * Edge data of the tile.
	 */
	private TileData tiledata;

	public Tile(String entityID, int spriteSheetRow, int spriteSheetCol, boolean debug) {
		super(entityID, debug);
		
		Image texture = TextureManager.getInstance().getSprite(spriteSheetRow, spriteSheetCol);
		this.setTexture(texture);	
	}
	
	/**
	 * Rotate the edge data.
	 */
	public void rotateTileData() {
		// rotate tile data
		this.tiledata.rotateTileData();
	}
	
	public void setIndex(Vector2f index) {
		this.index = index;
	}
	
	public Vector2f getIndex() {
		return this.index;
	}
	
	/**
	 * @return Edge data in order: Top, Right, Bottom, Left
	 */
	public int[] getEdges() {
		return new int[] {getTop(), getRight(), getBottom(), getLeft()};
	}
	
	public int getTop() {
		return tiledata.getTop();
	}

	public int getRight() {
		return tiledata.getRight();
	}

	public int getBottom() {
		return tiledata.getBottom();
	}

	public int getLeft() {
		return tiledata.getLeft();
	}

	public void setTileData(TileData newTileData) {
		this.tiledata = newTileData;
	}
	
	@Override
	public boolean equals(Object o) {
	    if (o == this)
	        return true;
	    if (!(o instanceof Tile))
	        return false;
	    Tile other = (Tile)o;
	    
	    return this.getIndex().equals(other.getIndex())
	    		&& (this.tiledata == null ? other.tiledata == null : this.tiledata.equals(other.tiledata))
	    		&& this.getRotation() == other.getRotation()
	    		&& this.getSize() == other.getSize();
	}
	
	@Override
	public String toString() {
		if (this.tiledata == null) {
			return String.format("Id: %s, Index: %s, Rotation: %f", this.getID(), this.getIndex().toString(), this.getRotation());
		}
		return String.format("Id: %s, Index: %s, Rotation: %f, tileData: %s", this.getID(), this.getIndex().toString(), this.getRotation(), this.tiledata.toString());
	}

}
