package mx.com.adesis.jsonschema;

import java.util.List;

import lombok.Data;

@Data
public class JsonSchema {
	private JsonSchemaKeyValuePair<String> schema;
	private JsonSchemaKeyValuePair<String> type;
	private JsonSchemaKeyValuePair<String> id;
	private JsonSchemaKeyValuePair<String> description;
	private JsonSchemaKeyValuePair<JsonSchemaProperties> properties;
	private JsonSchemaKeyValuePair<List<String>> required;
	private JsonSchemaKeyValuePair<JsonSchemaDependencies> dependencies;

	public boolean hasSchema() {
		return schema != null ? true : false;
	}

	public boolean hasType() {
		return type != null ? true : false;
	}

	public boolean hasId() {
		return id != null ? true : false;
	}

	public boolean hasDescription() {
		return description != null ? true : false;
	}

	public boolean hasProperties() {
		return properties != null && properties.getValue() != null
				&& properties.getValue().getProperties() != null
				&& properties.getValue().getProperties().size() > 0 ? true : false;
	}

	public boolean hasRequired() {
		return required != null && required.getValue() != null && required.getValue().size() > 0 ? true
				: false;
	}

	public boolean hasDependencies() {
		return dependencies != null ? true : false;
	}

	JsonSchema() {
	}

	@Override
	public String toString() {

		final StringBuilder sb = new StringBuilder();
		sb.append("JsonSchema(\n").
				append("\tschema = ").append(this.schema).append("\n").
				append("\ttype = ").append(this.type).append("\n").
				append("\tid = ").append(this.id).append("\n").
				append("\tdescription = ").append(this.description).append("\n").
				append("\tproperties = ").append(this.properties).append("\n").
				append("\trequired = ").append(this.required).append("\n").
				append("\tdependencies = ").append(this.dependencies).append("\n").
				append(")");

		return sb.toString();
	}
}
