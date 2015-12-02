package mx.com.adesis.test.jsonschema;

import lombok.extern.slf4j.Slf4j;
import mx.com.adesis.jsonschema.model.JsonSchema;
import mx.com.adesis.jsonschema.model.JsonSchemaBuilder;
import mx.com.adesis.jsonschema.model.JsonSchemaProperty;

import org.junit.Test;

@Slf4j
public class JsonSchemaParserTest {

	private String jsonSchemaAsString = "{"
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
			+ "		\"version2\": {"
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
			+ "				\"type\": \"string\","
			+ "				\"description\": \"Descripción del Item\""
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

	@Test
	public void jsonSchemaParserTest() {

		log.info("inicia jsonSchemaParserTest --------------------------------");
		JsonSchema jsonSchema = new JsonSchemaBuilder().build(jsonSchemaAsString);

		log.info("\n{}", jsonSchema);

		if (jsonSchema.hasSchema())
			log.info("{}", jsonSchema.getSchema().getKey() + " = "
					+ jsonSchema.getSchema().getValue());

		if (jsonSchema.hasType())
			log.info("{}", jsonSchema.getType().getKey() + " = "
					+ jsonSchema.getType().getValue());

		if (jsonSchema.hasId())
			log.info("{}", jsonSchema.getId().getKey() + " = " + jsonSchema.getId().getValue());

		if (jsonSchema.hasDescription())
			log.info("{}", jsonSchema.getDescription().getKey() + " = "
					+ jsonSchema.getDescription().getValue());

		if (jsonSchema.hasProperties()) {
			log.info("{}", "Properties");

			for (JsonSchemaProperty jsp : jsonSchema.getProperties().getValue().getProperties()) {
				log.info("{}", jsp.getName());
				log.info("{}", "\tdefinition: " + jsp.getDefinition());
			}
		}

		log.info("finaliza jsonSchemaParserTest --------------------------------");
	}
}
