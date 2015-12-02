package mx.com.adesis.jsonschema;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
public class JsonSchemaItems {
	private @Setter(AccessLevel.PACKAGE) List<JsonSchemaItem> items;

	JsonSchemaItems() {
		this.items = new ArrayList<JsonSchemaItem>();
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("\n\t\tJsonSchemaProperties(\n");

		int i = 0;
		for (JsonSchemaItem it : items) {
			sb.append("\t\t\t[" + (i++) + "] = ").append(it).append("\n");
			sb.append("\t\t\t\t").append(it.getDefinition()).append("\n");
		}
		sb.append("\t\t)\n").
				append("\t");

		return sb.toString();
	}
}
