package mx.com.adesis.asodesign.eamodeler;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import mx.com.adesis.asodesign.eaintegration.api.IAttribute;
import mx.com.adesis.asodesign.eaintegration.enums.AttributeType;
import mx.com.adesis.asodesign.eamodeler.model.Model;
import mx.com.adesis.asodesign.eamodeler.model.ModelAttribute;

import org.junit.Ignore;
import org.junit.Test;

public class EAModelInteractionTest {
	
	public static String EAP_FILE = "C:\\proyectos\\proyecto_ASO_multicanal\\diseño\\enterpsise_architect\\aso-arquitect\\design-template-aso.eap"; 
	public static String EAP_FILE_TEMPLATE = "C:\\proyectos\\proyecto_ASO_multicanal\\diseño\\fuentes_descargados\\repo_git\\aso-design\\Diagrams\\design-template.eap";
	
	@Test
	@Ignore
	public void testCreateAttributeFromNewElement(){
		
		Model model = new Model();
		model.setName("SystemUser");
		model.setDescription("Usuario del Sistema");
		
		EAModelInteraction modifyModel = new EAModelInteraction();
				
		ModelAttribute modelAttribute = new ModelAttribute();
		modelAttribute.setName("nameJOG2");
		modelAttribute.setAttributeType(AttributeType.STRING);
		modelAttribute.setDescription("atributo de nombre");
		modelAttribute.setRequired(true);
		
		ModelAttribute secondModelAttribute = new ModelAttribute();
		secondModelAttribute.setName("registrationDate");
		secondModelAttribute.setAttributeType(AttributeType.STRING);
		secondModelAttribute.setDescription("atributo de fecha de alta");
		secondModelAttribute.setFormat("date-time");
		secondModelAttribute.setRequired(false);
		
		List<IAttribute> modelAttributeList = new ArrayList<IAttribute>();
		modelAttributeList.add(modelAttribute);
		modelAttributeList.add(secondModelAttribute);
		
		model.setAttributes(modelAttributeList);
		
		modifyModel.workOnNewEntity(EAP_FILE_TEMPLATE, model, "{98F20947-C2FF-40bb-B815-7F1972040190}");
		
	}
		
	
}
