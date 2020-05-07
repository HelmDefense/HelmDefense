package fr.helmdefense.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.List;

import org.yaml.snakeyaml.Yaml;

import fr.helmdefense.model.level.Level;
import fr.helmdefense.model.map.GameMap;

public class YAMLLoader {
	private static final Yaml YAML = new Yaml();
	
	public static final String SAVES_FOLDER = "saves";
	
	private YAMLLoader() {}
	
	public static Level loadLevel(String name) {
		return new Level(loadMap(name));
	}
	
	private static GameMap loadMap(String name) {
		try {
			List<List<Integer>> map = YAML.load(new FileReader(Paths.get(SAVES_FOLDER, name, "map.yml").toString()));
			int[][] tiles = new int[map.size()][map.get(0).size()];
			for (int i = 0; i < map.size(); i++)
				for (int j = 0; j < map.get(i).size(); j++)
					tiles[i][j] = map.get(i).get(j);
			
			return new GameMap(tiles);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new YAMLLoadException("Cannot find map file for level \"" + name + "\"");
		} catch (Exception e) {
			e.printStackTrace();
			throw new YAMLLoadException();
		}
	}
}