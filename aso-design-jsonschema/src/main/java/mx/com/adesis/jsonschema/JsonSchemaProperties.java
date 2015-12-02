package mx.com.adesis.jsonschema;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
public class JsonSchemaProperties {
	private @Setter(AccessLevel.PACKAGE) List<JsonSchemaProperty> properties;

	JsonSchemaProperties() {
		this.properties = new ArrayList<JsonSchemaProperty>();
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("\n\t\tJsonSchemaProperties(\n");

		int i = 0;
		for (JsonSchemaProperty jsp : properties) {
			sb.append("\t\t\t[" + (i++) + "] = ").append(jsp).append("\n");
			sb.append("\t\t\t\t").append(jsp.getDefinition()).append("\n");
		}
		sb.append("\t\t)\n").
				append("\t");

		return sb.toString();
	}
}
