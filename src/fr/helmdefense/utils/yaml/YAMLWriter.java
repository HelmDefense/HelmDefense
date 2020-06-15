package fr.helmdefense.utils.yaml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import fr.helmdefense.controller.Controller.Gamemode;
import fr.helmdefense.controller.Controls;
import fr.helmdefense.model.level.Difficulty;

public class YAMLWriter {
	public static final String STORAGE = System.getProperty("user.home") + System.getProperty("file.separator") + ".HelmDefense";
	
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
			FileWriter writer = new FileWriter(STORAGE + "/" + YAML.DATA_FOLDER + "/options.yml");
			YAML.get().dump(data, writer);
		} catch (IOException e) {
			throw new YAMLException("Failed to save options in file \"" + STORAGE + "/" + YAML.DATA_FOLDER + "/options.yml\"", e);
		}
	}
	
	static void saveDefaultOptions() throws IOException {
		File options = new File(STORAGE, YAML.DATA_FOLDER + "/options.yml");
		if (! options.getParentFile().exists())
			options.getParentFile().mkdirs();
		
		InputStream defaultOptions = YAMLLoader.class.getClassLoader().getResourceAsStream(YAML.DATA_FOLDER + "/options.yml");
		if (! options.exists())
			Files.copy(defaultOptions, options.toPath());
		else {
			PrintWriter optionsFile = new PrintWriter(options);
			int read;
			while ((read = defaultOptions.read()) != -1)
				optionsFile.write(read);
			defaultOptions.close();
			optionsFile.close();
		}
	}
}