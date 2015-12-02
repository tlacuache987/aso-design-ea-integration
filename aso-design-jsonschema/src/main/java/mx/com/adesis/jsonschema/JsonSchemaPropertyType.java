package mx.com.adesis.jsonschema;

public enum JsonSchemaPropertyType {
	ARRAY("array"), BOOLEAN("boolean"), INTEGER("integer"), NUMBER("number"), NULL("null"), OBJECT(
			"object"), STRING("string");

	private String type;

	private JsonSchemaPropertyType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	@Override
	public String toString() {
		return this.type;
	}
}
