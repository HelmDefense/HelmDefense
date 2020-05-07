package fr.helmdefense.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import fr.helmdefense.model.entities.utils.Statistic;
import fr.helmdefense.model.level.Level;
import fr.helmdefense.model.map.GameMap;

public class YAMLLoader {
	private static Map<String, ?> stats = null;
	private static final Yaml YAML = new Yaml();
	
	public static final String SAVES_FOLDER = "saves";
	public static final String DATA_FOLDER = "game_data";
	
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

	public static Statistic loadStats(String name) {
		if (stats == null)
			loadStats();
		
		Object obj = path(stats, name);
		if (obj == null || ! (obj instanceof Map))
			throw new YAMLLoadException("Incorrect format for entity!");
		Map<?, ?> s = (Map<?, ?>) obj;
		
		try {
			return new Statistic(
					toInt(s.get("hp")),
					toInt(s.get("dmg")),
					toDouble(s.get("mvt-spd")),
					toDouble(s.get("atk-spd")),
					toDouble(s.get("atk-range")),
					toDouble(s.get("dist-range"))
			);
		} catch (ClassCastException e) {
			throw new YAMLLoadException("Incorrect format for entity!", e);
		}
	}
	
	private static void loadStats() {
		try {
			stats = YAML.load(new FileReader(Paths.get(DATA_FOLDER, "entity_stats.yml").toString()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new YAMLLoadException("Cannot find \"entity_stats.yml\" file in folder \"" + DATA_FOLDER + "\"");
		} catch (Exception e) {
			e.printStackTrace();
			throw new YAMLLoadException();
		}
	}
	
	public static Object path(Map<?, ?> data, String path, Object def) {
		List<String> layers = new ArrayList<String>(Arrays.asList(path.split("\\.")));
		String wanted = layers.remove(layers.size() - 1);
		Map<?, ?> actual = data;
		for (String layer : layers) {
			Object o = actual.get(layer);
			if (o != null && o instanceof Map)
				actual = (Map<?, ?>) o;
			else
				return def;
		}
		
		return actual.containsKey(wanted) ? actual.get(wanted) : def;
	}
	
	public static Object path(Map<?, ?> data, String path) {
		return path(data, path, null);
	}
	
	public static int toInt(Object obj, int def) {
		return obj == null ? def : ((Number) obj).intValue();
	}
	
	public static int toInt(Object obj) {
		return toInt(obj, -1);
	}
	
	public static double toDouble(Object obj, double def) {
		return obj == null ? def : ((Number) obj).doubleValue();
	}
	
	public static double toDouble(Object obj) {
		return toDouble(obj, -1);
	}
}