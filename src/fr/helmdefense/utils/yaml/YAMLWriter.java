package fr.helmdefense.utils.yaml;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import fr.helmdefense.controller.Controller.Gamemode;
import fr.helmdefense.controller.Controls;
import fr.helmdefense.model.level.Difficulty;

public class YAMLWriter {
	private YAMLWriter() {}
	
	public static void saveOptions(Difficulty difficulty, double speedness, Gamemode gamemode) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("difficulty", difficulty.name());
		data.put("speedness", speedness);
		data.put("gamemode", gamemode.name());
		
		Map<String, String> controls = new HashMap<String, String>();
		for (Controls control : Controls.values()) {
			if (control != Controls.UNKNOWN)
				controls.put(control.name(), control.getKey().name());
		}
		data.put("controls", controls);
		
		try {
			FileWriter writer = new FileWriter(Paths.get(YAML.DATA_FOLDER, "options.yml").toFile());
			YAML.get().dump(data, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}