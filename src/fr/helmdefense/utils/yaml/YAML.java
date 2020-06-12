package fr.helmdefense.utils.yaml;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

public class YAML {
	private static Yaml yaml;
	
	public static final String SAVES_FOLDER = "saves";
	public static final String DATA_FOLDER = "game_data";
	
	private YAML() {}
	
	static Yaml get() {
		if (yaml == null) {
			DumperOptions options = new DumperOptions();
		    options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
		    yaml = new Yaml(options);
		}
		
		return yaml;
	}
}