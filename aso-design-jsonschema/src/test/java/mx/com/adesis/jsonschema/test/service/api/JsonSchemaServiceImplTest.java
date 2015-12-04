package mx.com.adesis.jsonschema.test.service.api;

import lombok.extern.slf4j.Slf4j;
import mx.com.adesis.asodesign.eaintegration.api.IModel;
import mx.com.adesis.jsonschema.JsonSchema;
import mx.com.adesis.jsonschema.service.api.IJsonSchemaService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Slf4j
@ContextConfiguration({ "classpath:/spring/jsonschema/asodesign-jsonschema-service-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class JsonSchemaServiceImplTest {

	@Autowired
	private IJsonSchemaService jsonSchemaService;

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
			+ "		\"product\": {"
			+ "			\"oneOf\": ["
			+ "				{ \"$ref\": \"account\" },"
			+ "				{ \"$ref\": \"card\" },"
			+ "				{ \"$ref\": \"investments\" },"
			+ "				{ \"$ref\": \"loan\" },"
			+ "				{ \"$ref\": \"scrow\" },"
			+ "				{ \"$ref\": \"heritage\" }"
			+ "			]"
			+ "		},"
			+ "		\"server\": {"
			+ "			\"type\": \"string\","
			+ "			\"oneOf\": ["
			+ "				{ \"format\": \"host-name\" },"
			+ "				{ \"format\": \"ipv4\" },"
			+ "				{ \"format\": \"ipv6\" }"
			+ "			]"
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

	@Before
	public void setUp() {
		Assert.assertNotNull(jsonSchemaService);
	}

	@Test
	public void testA() {
		log.info("inicia testA ---------------------------------");

		IModel model = jsonSchemaService.getModelFormJsonSchema(jsonSchemaAsString);
		JsonSchema jsonSchema = jsonSchemaService.getJsonSchema(jsonSchemaAsString);

		System.out.println("model: " + model);
		System.out.println("jsonSchema: " + jsonSchema);

		log.info("finaliza testA ---------------------------------");
		log.info("");
	}
}
