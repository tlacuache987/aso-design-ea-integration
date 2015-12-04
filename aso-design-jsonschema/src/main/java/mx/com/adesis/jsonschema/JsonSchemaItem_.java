package mx.com.adesis.jsonschema;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.ToString;

@Data
@ToString(exclude = { "definition" })
public class JsonSchemaItem_ {
	private @Setter(AccessLevel.PACKAGE) String name;
	private @Setter(AccessLevel.PACKAGE) JsonSchemaItemPropertyDefinition definition;

	JsonSchemaItem_(String name) {
		this.name = name;
		this.definition = new JsonSchemaItemPropertyDefinition();
	}
}
