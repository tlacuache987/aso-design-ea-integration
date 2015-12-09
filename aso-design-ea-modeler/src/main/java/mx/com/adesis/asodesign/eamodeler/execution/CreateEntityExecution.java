package mx.com.adesis.asodesign.eamodeler.execution;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;

import mx.com.adesis.asodesign.eaintegration.model.api.IModel;
import mx.com.adesis.asodesign.eaintegration.model.api.impl.Model;
import mx.com.adesis.asodesign.eaintegration.model.attribute.api.IAttribute;
import mx.com.adesis.asodesign.eaintegration.model.attribute.api.impl.ModelArrayAttribute;
import mx.com.adesis.asodesign.eaintegration.model.attribute.api.impl.ModelEnumAttribute;
import mx.com.adesis.asodesign.eaintegration.model.attribute.api.impl.ObjectAttribute;
import mx.com.adesis.asodesign.eaintegration.model.enums.AttributeType;
import mx.com.adesis.asodesign.eamodeler.EAModelInteraction;
import mx.com.adesis.asodesign.eamodeler.ui.ExecutionUI;


public class CreateEntityExecution implements IExecution {

	
	public String toString() {
		return "CreateEntityExecution";
	}

	public String getDescription() {
		return "Crea una entidad en EA apartir de un JSON Schema";
	}

	public void runExample(File projectFile, ExecutionUI uiFrame) {
		DefaultListModel outputList = uiFrame.getOutputListModel();
		outputList.addElement("Comienza la ejecuci�n de la implementacion CreateEntityExecution... espere un momento");
		try {
			createAttributeFromNewElement(projectFile.getAbsolutePath());
			outputList.addElement("Ejecuci�n terminada");
		} catch (Exception e) {
			outputList.addElement("ERROR: " + e.getMessage());
		}
	}
	
	private void createAttributeFromNewElement(String projectFileWithPath) {
		
		Model model = new Model();
		model.setName("SystemUser");
		model.setDescription("Usuario del Sistema");
		
		EAModelInteraction modifyModel = new EAModelInteraction();
				
		ObjectAttribute modelAttribute = new ObjectAttribute();
		modelAttribute.setName("nameJOG2");
		modelAttribute.setAttributeType(AttributeType.STRING);
		modelAttribute.setDescription("atributo de nombre");
		modelAttribute.setRequired(true);
		
		ObjectAttribute secondModelAttribute = new ObjectAttribute();
		secondModelAttribute.setName("registrationDate");
		secondModelAttribute.setAttributeType(AttributeType.STRING);
		secondModelAttribute.setDescription("atributo de fecha de alta");
		secondModelAttribute.setFormat("date-time");
				
		ModelArrayAttribute thirdModelAttribute = new ModelArrayAttribute();
		thirdModelAttribute.setName("observations");
		thirdModelAttribute.setAttributeType(AttributeType.STRING);
		thirdModelAttribute.setDescription("atributo de observaciones");
				
		ObjectAttribute fourthModelAttribute = new ObjectAttribute();
		fourthModelAttribute.setName("contact1");
		fourthModelAttribute.setSubtype("Contract");
		fourthModelAttribute.setAttributeType(AttributeType.OBJECT);
		fourthModelAttribute.setDescription("contrato relacionado");
		fourthModelAttribute.setRequired(true);
		fourthModelAttribute.setReadOnly(true);
				
		ModelEnumAttribute fifthModelAttribute = new ModelEnumAttribute();
		fifthModelAttribute.setName("operationType");
		fifthModelAttribute.setDescription("tipo de operaci�n");
		List<String> enumValues = new ArrayList<String>();
		enumValues.add(0, "TRASPASO");
		enumValues.add(1, "CONSULTA");
		fifthModelAttribute.setEnumValues(enumValues);
		
		ModelArrayAttribute sixthModelAttribute = new ModelArrayAttribute();
		sixthModelAttribute.setName("participants");
		sixthModelAttribute.setAttributeType(AttributeType.OBJECT);
		sixthModelAttribute.setDescription("lista de participantes");
		sixthModelAttribute.setSubtype("Participant");
				
		List<IAttribute> modelAttributeList = new ArrayList<IAttribute>();
		modelAttributeList.add(modelAttribute);
		modelAttributeList.add(secondModelAttribute);
		modelAttributeList.add(thirdModelAttribute);
		modelAttributeList.add(fourthModelAttribute);
		modelAttributeList.add(fifthModelAttribute);
		modelAttributeList.add(sixthModelAttribute);
		
		model.setAttributes(modelAttributeList);
		
		//Segunda entidad
		Model secondModel = new Model();
		secondModel.setName("SystemRole");
		secondModel.setDescription("Roles del Sistema");
						
		ObjectAttribute secondModelfirstAttribute = new ObjectAttribute();
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
		
		modifyModel.workOnEntityList(projectFileWithPath, models, "{98F20947-C2FF-40bb-B815-7F1972040190}");
		
	}
	
	
	
	
	
}
