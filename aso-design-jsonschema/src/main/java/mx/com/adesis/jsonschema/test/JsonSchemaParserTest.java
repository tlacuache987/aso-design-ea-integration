package mx.com.adesis.jsonschema.test;

import mx.com.adesis.jsonschema.JsonSchema;
import mx.com.adesis.jsonschema.JsonSchemaBuilder;
import mx.com.adesis.jsonschema.JsonSchemaProperty;

public class JsonSchemaParserTest {
	public static void main(String[] args) {

		String jsonSchemaAsString = "{"
				+ "	\"type\": \"object\","
				+ "	\"$schema\": \"#un-schema\","
				+ "	\"id\": \"ContractDocument\","
				+ "	\"description\": \"Contrato de cliente con BBVA\","
				+ "	\"properties\": {"
				+ "		\"version\": {"
				+ "			\"type\": \"string\","
				+ "			\"description\": \"Versión del contrato\","
				+ "			\"readonly\": true"
				+ "		},"
				+ "		\"sexo\": {"
				+ "			\"enum\": [ \"MASCULINO\", \"FEMENINO\" ],"
				+ "			\"description\": \"Sexo del contrato\","
				+ "			\"readonly\": false"
				+ "		},"
				+ "		\"options\": {"
				+ "			\"type\": \"array\","
				+ "			\"minItems\": 1,"
				+ "			\"items\": {"
				+ "				\"type\": \"string\""
				+ "			},"
				+ "			\"uniqueItems\": true"
				+ "		},"
				+ "		\"documentFile\": {"
				+ "			\"$ref\": \"documentFile\","
				+ "			\"description\": \"Datos del archivo del contrato\","
				+ "			\"readonly\": true"
				+ "		}"
				+ "	}"
				+ "}";

		JsonSchema jsonSchema = new JsonSchemaBuilder().build(jsonSchemaAsString);

		System.out.println(jsonSchema);

		System.out.println();
		System.out.println();
		System.out.println("-------------------------------");
		System.out.println();
		System.out.println();

		if (jsonSchema.hasSchema())
			System.out.println(jsonSchema.getSchema().getKey() + " = "
					+ jsonSchema.getSchema().getValue());

		if (jsonSchema.hasType())
			System.out.println(jsonSchema.getType().getKey() + " = "
					+ jsonSchema.getType().getValue());

		if (jsonSchema.hasId())
			System.out.println(jsonSchema.getId().getKey() + " = " + jsonSchema.getId().getValue());

		if (jsonSchema.hasDescription())
			System.out.println(jsonSchema.getDescription().getKey() + " = "
					+ jsonSchema.getDescription().getValue());

		if (jsonSchema.hasProperties()) {
			System.out.println("Properties");

			for (JsonSchemaProperty jsp : jsonSchema.getProperties().getValue().getProperties()) {
				System.out.println(jsp.getName() + "\n\tdefinition: " + jsp.getDefinition());
			}
		}

	}
}
