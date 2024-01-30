package de.tu_darmstadt.informatik.fop.main.dorfromantik.parser;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import de.tu_darmstadt.informatik.fop.main.dorfromantik.constants.Constants;
import de.tu_darmstadt.informatik.fop.main.dorfromantik.model.GameData;

/**
 * Parser to load a config file.
 */
public class ConfigParser {
	/**
	 * Properties object.
	 */
	private Properties properties = new Properties();
	/**
	 * model class
	 */
	private GameData gameData = new GameData();
	
	/**
	 * default value for tile data.
	 */
	private final String defaultTileData = "0 0 0 0;0 1 0 0;0 1 1 0;0 1 0 1;0 1 1 1;1 1 1 1;0 2 0 0;0 2 2 0;0 2 0 2;0 2 2 2;2 2 2 2;0 3 0 0;0 3 3 0;0 3 0 3;0 3 3 3;3 3 3 3;1 2 2 2;1 3 3 3;2 1 1 1;2 3 3 3;3 1 1 1;3 2 2 2;1 2 2 1;1 3 3 1;2 3 3 2;1 2 1 2;1 3 1 3;3 2 3 2;1 0 2 0;1 0 3 0;2 0 3 0;3 1 2 1;0 1 3 1;0 1 2 1;0 2 3 2;1 2 3 2;1 2 0 2;0 3 2 3;1 3 2 3;1 3 0 3;0 2 3 0;0 3 2 0;0 1 3 0;0 3 1 0;0 2 1 0;0 1 2 0;1 2 3 1;1 3 2 1;1 0 3 1;1 3 0 1;1 0 2 1;1 2 0 1;2 1 3 2;2 3 1 2;2 0 3 2;2 3 0 2;2 0 1 2;2 1 0 2;3 1 2 3;3 2 1 3;3 0 2 3;3 2 0 3;3 0 1 3;3 1 0 3;2 1 0 3;2 0 1 3;0 1 2 3;3 1 0 2;2 1 3 0;3 1 2 0;4 0 4 0;4 4 0 0;4 0 0 0;";
	
	public ConfigParser() {
	}
	
	/**
	 * Parse a config file at the given path.
	 * @param path of the config file.
	 */
	public void parseConfigFromPath(String path) {
		properties = new Properties();
		gameData = new GameData();
		try (InputStream input = new FileInputStream(path)) {
            // load a properties file
			properties.load(input);

            // get the property value and print it out
			List<String> startingTiles = Arrays.asList(properties.getProperty("startingTiles", "64,69").split(","));
			List<Integer> startingTilesIndex = startingTiles.stream().map(Integer::parseInt).collect(Collectors.toList());
			gameData.setStartingTiles(startingTilesIndex);
			
			// difficulty 1
			gameData.setDifficulty1StartIndex(Integer.valueOf(properties.getProperty("difficulty1.startIndex", "0")));
			gameData.setDifficulty1EndIndex(Integer.valueOf(properties.getProperty("difficulty1.endIndex", "15")));
			gameData.setDifficulty1Propability(Integer.valueOf(properties.getProperty("difficulty1.propability", "50")));

			// difficulty 2
			gameData.setDifficulty2StartIndex(Integer.valueOf(properties.getProperty("difficulty2.startIndex", "16")));
			gameData.setDifficulty2EndIndex(Integer.valueOf(properties.getProperty("difficulty2.endIndex", "27")));
			gameData.setDifficulty2Propability(Integer.valueOf(properties.getProperty("difficulty2.propability", "25")));

			// difficulty 3
			gameData.setDifficulty3StartIndex(Integer.valueOf(properties.getProperty("difficulty3.startIndex", "28")));
			gameData.setDifficulty3EndIndex(Integer.valueOf(properties.getProperty("difficulty3.endIndex", "63")));
			gameData.setDifficulty3Propability(Integer.valueOf(properties.getProperty("difficulty3.propability", "15")));

			// difficulty 4
			gameData.setDifficulty4StartIndex(Integer.valueOf(properties.getProperty("difficulty4.startIndex", "64")));
			gameData.setDifficulty4EndIndex(Integer.valueOf(properties.getProperty("difficulty4.endIndex", "69")));
			gameData.setDifficulty4Propability(Integer.valueOf(properties.getProperty("difficulty4.propability", "10")));

            String tileData = properties.getProperty("tileDataSet", this.defaultTileData);
            List<String> tileDataSet = Arrays.asList(tileData.split(";"));
            System.out.println(tileDataSet);
            gameData.setTileData(tileDataSet);
            
            System.out.println("GameData valid?: "+this.validateData());
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
	}
	
	public GameData getGameData() {
		return this.gameData;
	}
	
	/**
	 * Validates the loaded data if it fits to the required format.
	 * @return true if the data is valid.
	 */
	public boolean validateData() {
		// If these values don't have the correct format the game can't run.
		assert gameData.getStartingTiles().size() == 2;
		assert gameData.getTileData().size() == Constants.Base.TileCount;
		for (String tileData: gameData.getTileData()) {
			// example string "0 0 0 0"
			assert tileData.length() == 7;
		}
		assert gameData.getDifficulty1StartIndex() >= 0 &&
				gameData.getDifficulty1StartIndex() <= gameData.getDifficulty1EndIndex() &&
				gameData.getDifficulty1EndIndex() <= Constants.Base.TileCount;
		assert gameData.getDifficulty2StartIndex() >= 0 &&
				gameData.getDifficulty2StartIndex() <= gameData.getDifficulty2EndIndex() &&
				gameData.getDifficulty2EndIndex() <= Constants.Base.TileCount;
		assert gameData.getDifficulty3StartIndex() >= 0 &&
				gameData.getDifficulty3StartIndex() <= gameData.getDifficulty3EndIndex() &&
				gameData.getDifficulty3EndIndex() <= Constants.Base.TileCount;
		assert gameData.getDifficulty4StartIndex() >= 0 &&
				gameData.getDifficulty4StartIndex() <= gameData.getDifficulty4EndIndex() &&
				gameData.getDifficulty4EndIndex() <= Constants.Base.TileCount;
		
		return true;
	}
	
	
	
}
