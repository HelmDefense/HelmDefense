package fr.helmdefense.utils.yaml;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class YAMLData {
	private Map<?, ?> data;
	
	public YAMLData(Map<?, ?> data) {
		this.data = data;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(String path, T def) {
		if (! (this.data instanceof Map) || path == null)
			return def;
		
		String[] layers = path.split("\\.");
		String wanted = layers[layers.length - 1];
		Map<?, ?> actual = this.data;
		for (int i = 0; i < layers.length - 1; i++) {
			Object o = actual.get(layers[i]);
			if (o == null)
				return def;
			if (o instanceof List) {
				List<?> list = (List<?>) actual;
				try {
					Object obj = list.get(Integer.parseInt(layers[i + 1]));
					if (i == layers.length - 2)
						return (T) obj;
					i++;
					actual = (Map<?, ?>) obj;
				} catch (NumberFormatException | IndexOutOfBoundsException e) {
					return def;
				}
			}
			else if (o instanceof Map)
				actual = (Map<?, ?>) o;
			else
				return def;
		}
		
		return actual.containsKey(wanted) ? (T) actual.get(wanted) : def;
	}
	
	public <T> T get(String path) {
		return this.get(path, null);
	}
	
	public int getInt(String path, int def) {
		Object obj = this.get(path);
		try {
			return obj == null ? def : ((Number) obj).intValue();
		} catch (ClassCastException e) {
			throw new YAMLException("Cannot return data in specified format!", e);
		}
	}
	
	public int getInt(String path) {
		return this.getInt(path, -1);
	}
	
	public long getLong(String path, long def) {
		Object obj = this.get(path);
		try {
			return obj == null ? def : ((Number) obj).longValue();
		} catch (ClassCastException e) {
			throw new YAMLException("Cannot return data in specified format!", e);
		}
	}
	
	public long getLong(String path) {
		return this.getLong(path, -1);
	}
	
	public float getFloat(String path, float def) {
		Object obj = this.get(path);
		try {
			return obj == null ? def : ((Number) obj).floatValue();
		} catch (ClassCastException e) {
			throw new YAMLException("Cannot return data in specified format!", e);
		}
	}
	
	public float getFloat(String path) {
		return this.getFloat(path, -1);
	}
	
	public double getDouble(String path, double def) {
		Object obj = this.get(path);
		try {
			return obj == null ? def : ((Number) obj).doubleValue();
		} catch (ClassCastException e) {
			throw new YAMLException("Cannot return data in specified format!", e);
		}
	}
	
	public double getDouble(String path) {
		return this.getDouble(path, -1);
	}
	
	public char getChar(String path, char def) {
		Object obj = this.get(path);
		return obj == null ? def : obj.toString().charAt(0);
	}
	
	public char getChar(String path) {
		return this.getChar(path, (char) 0);
	}
	
	public String getString(String path, String def) {
		Object obj = this.get(path);
		return obj == null ? def : obj.toString();
	}
	
	public String getString(String path) {
		return this.getString(path, "");
	}
	
	public YAMLData getData(String path, YAMLData def) {
		try {
			Map<?, ?> data = this.get(path);
			return data == null ? def : new YAMLData(data);
		} catch (ClassCastException e) {
			throw new YAMLException("Cannot return data in specified format!", e);
		}
	}
	
	public YAMLData getData(String path) {
		return this.getData(path, null);
	}
	
	@SuppressWarnings("unchecked")
	public Set<String> getPaths() {
		return (Set<String>) this.data.keySet();
	}
	
	@Override
	public String toString() {
		return this.data.toString();
	}
}