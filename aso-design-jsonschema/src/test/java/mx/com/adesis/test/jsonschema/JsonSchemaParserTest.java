package mx.com.adesis.test.jsonschema;

import lombok.extern.slf4j.Slf4j;
import mx.com.adesis.jsonschema.JsonSchema;
import mx.com.adesis.jsonschema.JsonSchemaBuilder;
import mx.com.adesis.jsonschema.JsonSchemaItemPropertyDefinition;
import mx.com.adesis.jsonschema.JsonSchemaProperty;

import org.junit.Test;

@Slf4j
public class JsonSchemaParserTest {

	private String jsonSchemaAsString = "{"
			+ "	\"type\": \"object\","
			+ "	\"$schema\": \"#un-schema\","
			+ "	\"id\": \"ContractDocument\","
			+ "	\"description\": \"Contrato de cliente con BBVA\","
			+ "	\"required\": [ \"documentFile\", \"options\", \"options2\" ],"
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

			for (JsonSchemaProperty jsp : jsonSchema.getProperties().getValue().getList()) {
				log.info("\t{}", jsp.getName());
				log.info("\t{}", "\tdefinition: " + jsp.getDefinition());

				if (jsp.getDefinition().hasType()) {
					log.info("\t\ttype: {}", jsp.getDefinition().getType().getValue().getEnumValue());

					if (jsp.getDefinition().getType().getValue().getEnumValue().equals("array")) {
						log.info("\t\tEs array");

						if (jsp.getDefinition().hasItems()) {

							JsonSchemaItemPropertyDefinition item = jsp.getDefinition().getItems().getValue();
							if (item.hasType())
								log.info("\t\t\tEl tipo del array es: {}", item.getType().getValue().getEnumValue());
						}
						continue;
					}
				}

				if (jsp.getDefinition().isEnumType()) {
					if (jsp.getDefinition().getEnumType().getKey().equals("enum")) {
						log.info("\t\tEs enum");

						for (String l : jsp.getDefinition().getEnumType().getValue()) {
							log.info("\t\t\tValor del enum: {}", l);
						}
					}
				}
			}
		}

		log.info("finaliza jsonSchemaParserTest --------------------------------");
	}
}
