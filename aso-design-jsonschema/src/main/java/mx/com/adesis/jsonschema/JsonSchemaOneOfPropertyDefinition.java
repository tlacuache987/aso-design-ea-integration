package mx.com.adesis.jsonschema;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
public class JsonSchemaOneOfPropertyDefinition {
	private @Setter(AccessLevel.PACKAGE) JsonSchemaKeyValuePair<String> ref;
	private @Setter(AccessLevel.PACKAGE) JsonSchemaKeyValuePair<String> format;

	public boolean hasRef() {
		return ref != null && ref.getValue() != null
				&& !"".equals(ref.getValue()) ? true : false;
	}

	public boolean hasFormat() {
		return format != null && format.getValue() != null
				&& !"".equals(format.getValue()) ? true : false;
	}

	@Override
	public String toString() {

		final StringBuilder sb = new StringBuilder();

		final List<String> attributes = new ArrayList<String>();

		if (hasRef()) {
			sb.append("ref = ").append(this.ref);
			attributes.add(sb.toString());
			sb.setLength(0);
		}

		if (hasFormat()) {
			sb.append("format = ").append(this.format);
			attributes.add(sb.toString());
			sb.setLength(0);
		}

		sb.append("JsonSchemaOneOfPropertyDefinition(");

		for (int i = 0; i < attributes.size(); i++) {
			sb.append(attributes.get(i));
			if (i < attributes.size() - 1)
				sb.append(", ");
		}
		sb.append(")");

		return sb.toString();
	}
}
