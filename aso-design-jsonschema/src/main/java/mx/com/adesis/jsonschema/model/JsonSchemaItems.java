package mx.com.adesis.jsonschema.model;

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
}
