package mx.com.adesis.jsonschema;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.ToString;

@Data
@ToString(exclude = { "definition" })
public class JsonSchemaProperty {
	private @Setter(AccessLevel.PACKAGE) String name;
	private @Setter(AccessLevel.PACKAGE) JsonSchemaPropertyDefinition definition;

	JsonSchemaProperty(String name) {
		this.name = name;
		this.definition = new JsonSchemaPropertyDefinition();
	}
}
