package mx.com.adesis.asodesign.eamodeler.modeltojson;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import mx.com.adesis.asodesign.eaintegration.model.api.IModel;
import mx.com.adesis.asodesign.eaintegration.model.api.impl.Model;
import mx.com.adesis.asodesign.eaintegration.model.attribute.api.IAttribute;
import mx.com.adesis.asodesign.eaintegration.model.attribute.api.impl.ModelObjectAttribute;
import mx.com.adesis.asodesign.eaintegration.model.enums.AttributeType;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Slf4j
@ContextConfiguration({ "classpath:/spring/eamodeler/asodesign-eamodeler-service-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class ModelToJsonUtilsTest {
	
	@Autowired
	ModelToJsonUtils modelToJsonUtils;
	
	@Test
	@Ignore
	public void test() {
		
		IModel model = new Model();
		model.setName("Persona");
		model.setSchema("http://json-schema.org/draft-04/schema");
		model.setType("object");
		model.setDescription("Persona asociada");
		
		ModelObjectAttribute fourthModelAttribute = new ModelObjectAttribute();
		fourthModelAttribute.setName("contact1");
		fourthModelAttribute.setSubtype("Contract");
		fourthModelAttribute.setAttributeType(AttributeType.OBJECT);
		fourthModelAttribute.setDescription("contrato relacionado");
		fourthModelAttribute.setRequired(true);
		fourthModelAttribute.setReadOnly(true);
		
		List<IAttribute> modelAttributeList = new ArrayList<IAttribute>();
		modelAttributeList.add(fourthModelAttribute);
		
		model.setAttributes(modelAttributeList);
		
		modelToJsonUtils.parseJson(model);
	}
		

}
