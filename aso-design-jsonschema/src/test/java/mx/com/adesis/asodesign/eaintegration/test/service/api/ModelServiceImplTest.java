package mx.com.adesis.asodesign.eaintegration.test.service.api;

import lombok.extern.slf4j.Slf4j;
import mx.com.adesis.asodesign.eaintegration.model.api.IModel;
import mx.com.adesis.asodesign.eaintegration.service.api.IModelService;
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
public class ModelServiceImplTest {

	@Autowired
	private IModelService modelService;

	@Before
	public void setUp() {
		Assert.assertNotNull(modelService);
	}

	@Test
	public void testA() {
		log.info("inicia testA ---------------------------------");

		IModel model = modelService.getModel(JsonSchemaString.JSON_SCHEMA_AS_STRING);

		System.out.println("model: " + model);

		log.info("finaliza testA ---------------------------------");
		log.info("");
	}
}
