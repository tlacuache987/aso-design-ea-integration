package mx.com.adesis.jsonschema.util;

public class StringUtils {
	public static String toUpperCamelCase(String string) {
		return string.substring(0, 1).toUpperCase() + string.substring(1);
	}
}
