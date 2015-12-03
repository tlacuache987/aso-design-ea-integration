package mx.com.adesis.jsonschema;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
public class JsonSchemaKeyValuePair<T extends Object> {
	private @Setter(AccessLevel.PACKAGE) String key;
	private @Setter(AccessLevel.PACKAGE) T value;

	@Override
	public String toString() {

		final StringBuilder sb = new StringBuilder();
		sb.append("[key: ").append(this.key).append(", value: ").append(this.value)
				.append("]");

		return sb.toString();
	}
}
