package mx.com.adesis.jsonschema;

public enum JsonSchemaPropertyType {
	ARRAY("array"), BOOLEAN("boolean"), INTEGER("integer"), NUMBER("number"), OBJECT(
			"object"), STRING("string");

	private String enumValue;

	private JsonSchemaPropertyType(String enumValue) {
		this.enumValue = enumValue;
	}

	public String getEnumValue() {
		return enumValue;
	}

	@Override
	public String toString() {
		return this.enumValue;
	}
}
