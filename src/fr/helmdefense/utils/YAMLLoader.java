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

import fr.helmdefense.model.entities.EntityType;
import fr.helmdefense.model.entities.abilities.Ability;
import fr.helmdefense.model.entities.utils.Attribute;
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
	
	public static GameMap loadMap(YAMLData lvl) {
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
				.map(w -> new Wave(w.getString("name"), w.getInt("reward"), w.get("entities")))
				.collect(Collectors.toList());
	}
	
	public static void loadEntityData() {
		YAMLData data = load(Paths.get(DATA_FOLDER, "entities.yml").toString());
		
		parseEntityData(data, "defenders", "heroes");
		parseEntityData(data, "attackers", "bosses");
		parseEntityData(data, "projectiles", null);
	}

	private static void parseEntityData(YAMLData data, String section, String subsection) {
		YAMLData d = data.getData(section);
		for (String path : d.getPaths()) {
			if (! path.equals(subsection)) {
				constructEntityData(section, path, d.getData(path), subsection != null);
			}
		}
		if (subsection != null) {
			d = d.getData(subsection);
			for (String path : d.getPaths()) {
				constructEntityData(section + "." + subsection, path, d.getData(path), false);
			}
		}
	}
	
	private static void constructEntityData(String path, String name, YAMLData data, boolean tiers) {
		Map<Tier, Statistic> stats = new HashMap<Tier, Statistic>();
		
		if (tiers)
			for (Tier tier : Tier.values())
				constructStats(stats, tier, data.getData("tier" + tier.getNumberTier()));
		else
			constructStats(stats, Tier.TIER_1, data.getData("stats"));
		
		EntityType.getFromName(name).setData(new EntityData(
				data.getString("name"),
				path + "." + name,
				new Size(data.getDouble("size.width"), data.getDouble("size.height")),
				stats,
				abilities(data)
		));
	}
	
	private static void constructStats(Map<Tier, Statistic> stats, Tier tier, YAMLData data) {
		Statistic stat = new Statistic();
		
		if (data != null) {
			for (String path : data.getPaths()) {
				try {
					stat.register(Attribute.valueOf(path.toUpperCase()), data.getDouble(path));
				} catch (IllegalArgumentException e) {}
			}
		}
		
		stats.put(tier, stat);
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
		
		try {
			list.set(0, Tier.valueOf(list.get(0).toString().toUpperCase()));
		} catch (IllegalArgumentException | IndexOutOfBoundsException e) {
			list.add(0, Tier.DEFAULT);
		}
		
		try {
			list.set(1, Tier.Specification.valueOf(list.get(1).toString().toUpperCase()));
		} catch (IllegalArgumentException | IndexOutOfBoundsException e) {
			list.add(1, Tier.Specification.DEFAULT);
		}
		
		return list;
	}
	
	public static YAMLData load(String file) {
		try {
			return new YAMLData(YAML.load(new FileReader(file)));
		} catch (FileNotFoundException e) {
			throw new YAMLLoadException("Cannot find file \"" + file + "\"!", e);
		}
	}
}