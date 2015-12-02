package mx.com.adesis.jsonschema;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
public class JsonSchemaPropertyDefinition {
	private @Setter(AccessLevel.PACKAGE) JsonSchemaKeyValuePair<JsonSchemaPropertyType> type; //ya
	private @Setter(AccessLevel.PACKAGE) JsonSchemaKeyValuePair<String> ref; //ya
	private @Setter(AccessLevel.PACKAGE) JsonSchemaKeyValuePair<List<String>> enumType; //ya
	private @Setter(AccessLevel.PACKAGE) JsonSchemaKeyValuePair<Boolean> readonly; //ya
	private @Setter(AccessLevel.PACKAGE) JsonSchemaKeyValuePair<Boolean> required; //ya
	private @Setter(AccessLevel.PACKAGE) JsonSchemaKeyValuePair<String> description; //ya
	private @Setter(AccessLevel.PACKAGE) JsonSchemaKeyValuePair<String> format; //ya
	private @Setter(AccessLevel.PACKAGE) List<JsonSchemaKeyValuePair<String>> oneOfProperties;
	//private @Setter(AccessLevel.PACKAGE) JsonSchemaKeyValuePair<List<JsonSchemaArrayItemDefinition>> items;
	private @Setter(AccessLevel.PACKAGE) JsonSchemaKeyValuePair<JsonSchemaItems> items; //ya
	private @Setter(AccessLevel.PACKAGE) JsonSchemaKeyValuePair<Integer> minItems;
	private @Setter(AccessLevel.PACKAGE) JsonSchemaKeyValuePair<Boolean> uniqueItems;

	public boolean hasType() {
		return type != null ? true : false;
	}

	public boolean hasRef() {
		return ref != null ? true : false;
	}

	public boolean isEnumType() {
		return enumType != null && enumType.getValue() != null && enumType.getValue().size() > 0 ? true
				: false;
	}

	public boolean isReadonly() {
		return readonly != null && readonly.getValue() != null && readonly.getValue() ? true
				: false;
	}

	public boolean isRequired() {
		return required != null && required.getValue() != null && required.getValue() ? true
				: false;
	}

	public boolean hasDescription() {
		return description != null && description.getValue() != null
				&& !"".equals(description.getValue()) ? true : false;
	}

	public boolean hasFormat() {
		return format != null && format.getValue() != null && !"".equals(format.getValue()) ? true
				: false;
	}

	public boolean isOneOf() {
		return oneOfProperties != null && oneOfProperties.size() > 0 ? true : false;
	}

	public boolean hasItems() {
		return items != null && items.getValue() != null && items.getValue() != null ? true : false;
	}

	public boolean hasMinItems() {
		return minItems != null && minItems.getValue() != null && minItems.getValue() >= 0 ? true
				: false;
	}

	public boolean hasUniqueItems() {
		return uniqueItems != null && uniqueItems.getValue() != null && uniqueItems.getValue() ? true
				: false;
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

		if (hasRef()) {
			sb.append("$ref = ").append(this.ref);
			attributes.add(sb.toString());
			sb.setLength(0);
		}

		if (isEnumType()) {
			sb.append("enum = ").append(this.enumType);
			attributes.add(sb.toString());
			sb.setLength(0);
		}

		if (isReadonly()) {
			sb.append("readonly = ").append(this.readonly);
			attributes.add(sb.toString());
			sb.setLength(0);
		}

		if (isRequired()) {
			sb.append("required = ").append(this.required);
			attributes.add(sb.toString());
			sb.setLength(0);
		}

		if (hasDescription()) {
			sb.append("description = ").append(this.description);
			attributes.add(sb.toString());
			sb.setLength(0);
		}

		if (hasFormat()) {
			sb.append("format = ").append(this.format);
			attributes.add(sb.toString());
			sb.setLength(0);
		}

		if (hasItems()) {
			sb.append("items = ").append(this.items);
			attributes.add(sb.toString());
			sb.setLength(0);
		}

		if (hasMinItems()) {
			sb.append("minItems = ").append(this.minItems);
			attributes.add(sb.toString());
			sb.setLength(0);
		}

		if (hasUniqueItems()) {
			sb.append("uniqueItems = ").append(this.uniqueItems);
			attributes.add(sb.toString());
			sb.setLength(0);
		}

		sb.append("JsonSchemaPropertyDefinition(");

		for (int i = 0; i < attributes.size(); i++) {
			sb.append(attributes.get(i));
			if (i < attributes.size() - 1)
				sb.append(", ");
		}
		sb.append(")");

		return sb.toString();
	}
}
