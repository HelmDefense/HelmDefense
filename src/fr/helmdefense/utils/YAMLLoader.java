package fr.helmdefense.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.yaml.snakeyaml.Yaml;

import fr.helmdefense.model.entities.Entity;
import fr.helmdefense.model.entities.abilities.Ability;
import fr.helmdefense.model.entities.utils.EntityData;
import fr.helmdefense.model.entities.utils.Statistic;
import fr.helmdefense.model.entities.utils.Tier;
import fr.helmdefense.model.entities.utils.coords.Hitbox.Size;
import fr.helmdefense.model.entities.utils.coords.Location;
import fr.helmdefense.model.level.Level;
import fr.helmdefense.model.level.Wave;
import fr.helmdefense.model.map.GameMap;

public class YAMLLoader {
	private static final Yaml YAML = new Yaml();
	
	public static final String SAVES_FOLDER = "saves";
	public static final String DATA_FOLDER = "game_data";
	
	private YAMLLoader() {}
	
	public static Level loadLevel(String name) {
		YAMLData lvl = load(Paths.get(SAVES_FOLDER, name, "data.yml").toString());
		return new Level(lvl.getString("name"), loadMap(lvl), loadWaves(lvl), lvl.getInt("start-money"));
	}
	
	private static GameMap loadMap(YAMLData lvl) {
		List<List<Integer>> map = lvl.get("map");
		int[][] tiles = new int[map.size()][map.get(0).size()];
		for (int i = 0; i < map.size(); i++)
			for (int j = 0; j < map.get(i).size(); j++)
				tiles[i][j] = map.get(i).get(j);
		
		return new GameMap(tiles, locList(lvl.get("spawns")), new Location(lvl.getDouble("target.x"), lvl.getDouble("target.y")));
	}
	
	private static List<Location> locList(List<Map<String, Double>> locs) {
		return locs.stream()
				.map(loc -> new Location(loc.get("x"), loc.get("y")))
				.collect(Collectors.toList());
	}
	
	private static List<Wave> loadWaves(YAMLData lvl) {
		List<Map<?, ?>> waves = lvl.get("waves");
		return waves.stream()
				.map(w -> new YAMLData(w))
				.map(w -> new Wave(w.getInt("reward"), w.get("entities")))
				.collect(Collectors.toList());
	}
	
	public static Map<Class<? extends Entity>, EntityData> loadEntityData() {
		Map<Class<? extends Entity>, EntityData> map = new HashMap<Class<? extends Entity>, EntityData>();
		YAMLData data = load(Paths.get(DATA_FOLDER, "entities.yml").toString());
		
		parseEntityData(map, data, "defenders", "heros");
		parseEntityData(map, data, "attackers", "bosses");
		parseEntityData(map, data, "projectiles", null);
		
		return map;
	}

	private static void parseEntityData(Map<Class<? extends Entity>, EntityData> map, YAMLData data, String section, String subsection) {
		YAMLData d = data.getData(section);
		for (String path : d.getPaths()) {
			if (! path.equals(subsection)) {
				constructEntityData(map, section + "." + path, d.getData(path), subsection != null);
			}
		}
		if (subsection != null) {
			d = d.getData(subsection);
			for (String path : d.getPaths()) {
				constructEntityData(map, section + "." + subsection + "." + path, d.getData(path), false);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private static void constructEntityData(Map<Class<? extends Entity>, EntityData> map, String path, YAMLData data, boolean tiers) {
		Map<Tier, Statistic> stats = new HashMap<Tier, Statistic>();
		
		if (tiers) {
			constructStats(stats, Tier.TIER_1, data.getData("tier1"));
			constructStats(stats, Tier.TIER_2, data.getData("tier2"));
			constructStats(stats, Tier.TIER_3, data.getData("tier3"));
		}
		else
			constructStats(stats, Tier.TIER_0, data);
		
		try {
			map.put(
					(Class<? extends Entity>) Class.forName(data.getString("class")),
					new EntityData(
							data.getString("name"),
							path,
							new Size(data.getDouble("size.width"), data.getDouble("size.height")),
							stats,
							abilities(data)
					)
			);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}
	
	private static void constructStats(Map<Tier, Statistic> stats, Tier tier, YAMLData data) {
		stats.put(tier, new Statistic(
				data.getInt("hp"),
				data.getInt("dmg"),
				data.getDouble("mvt-spd"),
				data.getDouble("atk-spd"),
				data.getDouble("atk-range"),
				data.getDouble("dist-range"),
				data.getInt("cost"),
				data.getInt("reward")
		));
	}
	
	@SuppressWarnings("unchecked")
	private static Map<Class<? extends Ability>, List<Object>> abilities(YAMLData data) {
		Map<String, List<?>> abilities = data.get("abilities");
		if (abilities == null)
			return new HashMap<Class<? extends Ability>, List<Object>>();
		
		return abilities.entrySet().stream()
				.collect(Collectors.toMap(e -> {
					try {
						return (Class<? extends Ability>) Class.forName("fr.helmdefense.model.entities.abilities.list." + e.getKey());
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
						return null;
					}
				}, e -> abilitiesParams(e.getValue())));
	}
	
	private static List<Object> abilitiesParams(List<?> data) {
		List<Object> list = new ArrayList<Object>(data);
		list.set(0, Tier.valueOf((String) list.get(0)));
		
		return list;
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