package mx.com.adesis.asodesign.eamodeler;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import mx.com.adesis.asodesign.eaintegration.model.api.IModel;
import mx.com.adesis.asodesign.eaintegration.model.api.impl.Model;
import mx.com.adesis.asodesign.eaintegration.model.attribute.api.IAttribute;
import mx.com.adesis.asodesign.eaintegration.model.attribute.api.impl.ModelArrayAttribute;
import mx.com.adesis.asodesign.eaintegration.model.attribute.api.impl.ModelEnumAttribute;
import mx.com.adesis.asodesign.eaintegration.model.attribute.api.impl.ModelInterfaceAttribute;
import mx.com.adesis.asodesign.eaintegration.model.attribute.api.impl.ModelObjectAttribute;
import mx.com.adesis.asodesign.eaintegration.model.enums.AttributeType;
import mx.com.adesis.asodesign.eaintegration.service.api.IModelService;
import mx.com.adesis.asodesign.eamodeler.execution.CustomResourceLoader;
import mx.com.adesis.asodesign.eamodeler.modeltojson.ModelToJsonUtilsTest;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sparx.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Slf4j
@ContextConfiguration({ "classpath:/spring/eamodeler/asodesign-eamodeler-service-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class EAModelInteractionTest {
	
	public static String JSON_FILE = "C:\\Temp\\raml\\schemas\\Participation-fake.raml";
	public static String EAP_FILE = "C:\\proyectos\\proyecto_ASO_multicanal\\diseño\\enterpsise_architect\\aso-arquitect\\design-template-aso.eap";
	public static String EAP_FILE_TEMPLATE = "C:\\Temp\\design-template.eap";
	
	@Autowired
	IModelService modelService;
	
	@Autowired
	CustomResourceLoader customResourceLoader;

	@Test
	@Ignore
	public void testCreateNewElements() {

		Model model = new Model();
		model.setName("SystemUser");
		model.setDescription("Usuario del Sistema");
		model.setSchema("http://json-schema.org/draft-04/schema#");
		model.setType("object");

		EAModelInteraction modifyModel = new EAModelInteraction();

		ModelObjectAttribute modelAttribute = new ModelObjectAttribute();
		modelAttribute.setName("nameJOG2");
		modelAttribute.setAttributeType(AttributeType.STRING);
		modelAttribute.setDescription("atributo de nombre");
		modelAttribute.setRequired(true);

		ModelObjectAttribute secondModelAttribute = new ModelObjectAttribute();
		secondModelAttribute.setName("registrationDate");
		secondModelAttribute.setAttributeType(AttributeType.DATE);
		secondModelAttribute.setDescription("atributo de fecha de alta");
		secondModelAttribute.setFormat("date-time"); //falta agregar como tag value todos (required y readonly son lo sunicos tagvalues)

		ModelArrayAttribute thirdModelAttribute = new ModelArrayAttribute();
		thirdModelAttribute.setName("observations");
		thirdModelAttribute.setAttributeType(AttributeType.STRING);
		thirdModelAttribute.setDescription("atributo de observaciones");
		thirdModelAttribute.setMinItems(1);
		thirdModelAttribute.setUniqueItems(true);

		ModelObjectAttribute fourthModelAttribute = new ModelObjectAttribute();
		fourthModelAttribute.setName("contact1");
		fourthModelAttribute.setSubtype("Contract");
		fourthModelAttribute.setAttributeType(AttributeType.OBJECT);
		fourthModelAttribute.setDescription("contrato relacionado");
		fourthModelAttribute.setRequired(true);
		fourthModelAttribute.setReadOnly(true);

		ModelEnumAttribute fifthModelAttribute = new ModelEnumAttribute();
		fifthModelAttribute.setName("operationType");
		fifthModelAttribute.setDescription("tipo de operación"); //checar codiifcaciones, por que hay pedos
		fifthModelAttribute.setSubtype("OperationType");
		List<String> enumValues = new ArrayList<String>();
		enumValues.add(0, "TRASPASO");
		enumValues.add(1, "CONSULTA");
		fifthModelAttribute.setEnumValues(enumValues);

		ModelArrayAttribute sixthModelAttribute = new ModelArrayAttribute();
		sixthModelAttribute.setName("participants");
		sixthModelAttribute.setAttributeType(AttributeType.OBJECT);
		sixthModelAttribute.setDescription("lista de participantes");
		sixthModelAttribute.setSubtype("Participant");

		ModelObjectAttribute seventhModelAttribute = new ModelObjectAttribute();
		seventhModelAttribute.setName("server");
		seventhModelAttribute.setAttributeType(AttributeType.OBJECT);
		seventhModelAttribute.setSubtype("Server");
		List<String> allowedValues = new ArrayList<String>();
		allowedValues.add("host-name");
		allowedValues.add("ipv4");
		seventhModelAttribute.setAllowedValues(allowedValues);

		ModelInterfaceAttribute eighthModelAttribute = new ModelInterfaceAttribute();
		eighthModelAttribute.setName("storage");
		eighthModelAttribute.setSubtype("Storage");
		List<String> resources = new ArrayList<String>();
		resources.add("diskDevice");
		resources.add("ipv4");
		eighthModelAttribute.setResources(resources);

		List<IAttribute> modelAttributeList = new ArrayList<IAttribute>();
		modelAttributeList.add(modelAttribute);
		modelAttributeList.add(secondModelAttribute);
		modelAttributeList.add(thirdModelAttribute);
		modelAttributeList.add(fourthModelAttribute);
		modelAttributeList.add(fifthModelAttribute);
		modelAttributeList.add(sixthModelAttribute);
		modelAttributeList.add(seventhModelAttribute);
		modelAttributeList.add(eighthModelAttribute);

		model.setAttributes(modelAttributeList);

		//Segunda entidad
		Model secondModel = new Model();
		secondModel.setName("SystemRole");
		secondModel.setDescription("Roles del Sistema");

		ModelObjectAttribute secondModelfirstAttribute = new ModelObjectAttribute();
		secondModelfirstAttribute.setName("id");
		secondModelfirstAttribute.setAttributeType(AttributeType.INTEGER);
		secondModelfirstAttribute.setDescription("clave del rol");
		secondModelfirstAttribute.setRequired(true);

		List<IAttribute> secondModelAttributeList = new ArrayList<IAttribute>();
		secondModelAttributeList.add(secondModelfirstAttribute);

		secondModel.setAttributes(secondModelAttributeList);

		List<IModel> models = new ArrayList<IModel>();
		models.add(model);
		models.add(secondModel);

		modifyModel.workOnEntityList(EAP_FILE_TEMPLATE, models, "{98F20947-C2FF-40bb-B815-7F1972040190}");

	}
	
	@Test
	@Ignore
	public void integrationTestCreateNewElements() {
		
		Resource resource = customResourceLoader.getResource("file:" + JSON_FILE);
		
		
		IModel model = null;
		try{
			InputStream is = resource.getInputStream();
			
			model = modelService.getModel(is);
		}
		catch(Exception e){
			throw new RuntimeException(e);
		}
		
		List models = new ArrayList<IModel>();
		models.add(model);
		
		log.debug("model: " + model);
		
		EAModelInteraction modifyModel = new EAModelInteraction();
		modifyModel.workOnEntityList(EAP_FILE_TEMPLATE, models, "{98F20947-C2FF-40bb-B815-7F1972040190}");
		
	}
	
	@Test
	@Ignore
	public void testGetDuplicateElements() {
		EAModelInteraction modifyModel = new EAModelInteraction();
		List<String> duplicateElements = modifyModel.getDuplicateEntitiesNames(EAP_FILE);
		for (String elementName : duplicateElements) {
			log.debug(elementName);
		}
	}
	
	

}
