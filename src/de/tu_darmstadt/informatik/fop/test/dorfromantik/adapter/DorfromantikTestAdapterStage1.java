package de.tu_darmstadt.informatik.fop.test.dorfromantik.adapter;

import java.util.List;
import java.util.stream.Collectors;

import de.tu_darmstadt.informatik.fop.main.dorfromantik.constants.Constants;
import de.tu_darmstadt.informatik.fop.main.dorfromantik.entity.PlaceholderTile;
import de.tu_darmstadt.informatik.fop.main.dorfromantik.entity.Tile;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;

public class DorfromantikTestAdapterStage1 extends DorfromantikTestAdapterMinimal {

	public DorfromantikTestAdapterStage1() {
		super();
	}
	
	
	public Tile getPreviewTile() {
		List<Entity> entities = StateBasedEntityManager.getInstance().getEntitiesByState(Constants.Base.GameScene);
		List<Tile> tiles = entities.stream()
				.filter(Tile.class::isInstance)
				.map(Tile.class::cast)
				.filter(tile -> tile.getIndex() == null)
				.collect(Collectors.toList());
		return tiles.get(0);
	}
	
	public List<PlaceholderTile> getPlaceholderTiles() {
		List<Entity> entities = StateBasedEntityManager.getInstance().getEntitiesByState(Constants.Base.GameScene);
		List<PlaceholderTile> tiles = entities.stream()
				.filter(PlaceholderTile.class::isInstance)
				.map(PlaceholderTile.class::cast)
				.collect(Collectors.toList());
		return tiles;
	}
	
}
