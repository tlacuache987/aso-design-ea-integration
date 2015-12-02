package mx.com.adesis.jsonschema;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
public class JsonSchemaItemPropertyDefinition {
	private @Setter(AccessLevel.PACKAGE) JsonSchemaKeyValuePair<JsonSchemaPropertyType> type;

	public boolean hasType() {
		return type != null ? true : false;
	}

	@Override
	public String toString() {

		final StringBuilder sb = new StringBuilder();

		final List<String> attributes = new ArrayList<String>();

		if (hasType()) {
			sb.append("type = ").append(this.type);
			attributes.add(sb.toString());
			sb.setLength(0);
		}

		sb.append("JsonSchemaItemPropertyDefinition(");

		for (int i = 0; i < attributes.size(); i++) {
			sb.append(attributes.get(i));
			if (i < attributes.size() - 1)
				sb.append(", ");
		}
		sb.append(")");

		return sb.toString();
	}
}
