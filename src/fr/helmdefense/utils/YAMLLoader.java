package fr.helmdefense.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.yaml.snakeyaml.Yaml;

import fr.helmdefense.model.entities.utils.Location;
import fr.helmdefense.model.entities.utils.Statistic;
import fr.helmdefense.model.level.Level;
import fr.helmdefense.model.level.Wave;
import fr.helmdefense.model.map.GameMap;

public class YAMLLoader {
	private static YAMLData entities = null;
	private static final Yaml YAML = new Yaml();
	
	public static final String SAVES_FOLDER = "saves";
	public static final String DATA_FOLDER = "game_data";
	
	private YAMLLoader() {}
	
	public static Level loadLevel(String name) {
		YAMLData lvl = load(Paths.get(SAVES_FOLDER, name, "data.yml").toString());
		return new Level(loadMap(lvl), loadWaves(lvl));
	}
	
	private static GameMap loadMap(YAMLData lvl) {
		List<List<Integer>> map = lvl.get("map");
		int[][] tiles = new int[map.size()][map.get(0).size()];
		for (int i = 0; i < map.size(); i++)
			for (int j = 0; j < map.get(i).size(); j++)
				tiles[i][j] = map.get(i).get(j);
		
		return new GameMap(tiles, locList(lvl.get("spawns")), locList(lvl.get("targets")));
	}
	
	private static List<Location> locList(List<Map<String, Integer>> locs) {
		return locs.stream()
				.map(loc -> new Location(loc.get("x"), loc.get("y")))
				.collect(Collectors.toList());
	}
	
	private static List<Wave> loadWaves(YAMLData lvl) {
		List<Map<?, ?>> waves = lvl.get("waves");
		return waves.stream()
				.map(w -> new YAMLData(w))
				.map(w -> new Wave(w.getInt("duration"), w.getInt("reward"), w.get("entities")))
				.collect(Collectors.toList());
	}

	public static Statistic loadStats(String name) {
		checkEntities();
		
		YAMLData s = entities.getData(name);
		if (s == null)
			throw new YAMLLoadException("Incorrect format for entity!");
		
		return new Statistic(
				s.getInt("hp"),
				s.getInt("dmg"),
				s.getDouble("mvt-spd"),
				s.getDouble("atk-spd"),
				s.getDouble("atk-range"),
				s.getDouble("dist-range")
		);
	}
	
	public static YAMLData getEntities() {
		checkEntities();
		return entities;
	}
	
	private static void checkEntities() {
		if (entities == null)
			entities = load(Paths.get(DATA_FOLDER, "entities.yml").toString());
	}
	
	public static YAMLData load(String file) {
		try {
			return new YAMLData(YAML.load(new FileReader(file)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new YAMLLoadException("Cannot find file \"" + file + "\"!", e);
		}
	}
}