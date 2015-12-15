package mx.com.adesis.jsonschema.test.service.api;

import lombok.extern.slf4j.Slf4j;
import mx.com.adesis.jsonschema.JsonSchema;
import mx.com.adesis.jsonschema.service.api.IJsonSchemaService;
import mx.com.adesis.jsonschema.test.JsonSchemaString;

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

	@Before
	public void setUp() {
		Assert.assertNotNull(jsonSchemaService);
	}

	@Test
	public void testA() {
		log.info("inicia testA ---------------------------------");

		JsonSchema jsonSchema = jsonSchemaService.getJsonSchema(JsonSchemaString.JSON_SCHEMA_CHAFA_DE_JORGE);

		System.out.println("jsonSchema: " + jsonSchema);

		log.info("finaliza testA ---------------------------------");
		log.info("");
	}
}
