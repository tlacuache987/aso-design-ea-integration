package mx.com.adesis.asodesign.eamodeler.execution;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;

import mx.com.adesis.asodesign.eaintegration.api.IAttribute;
import mx.com.adesis.asodesign.eaintegration.enums.AttributeType;
import mx.com.adesis.asodesign.eamodeler.EAModelInteraction;
import mx.com.adesis.asodesign.eamodeler.model.Model;
import mx.com.adesis.asodesign.eamodeler.model.ModelArrayAttribute;
import mx.com.adesis.asodesign.eamodeler.model.ModelEnumAttribute;
import mx.com.adesis.asodesign.eamodeler.model.ModelObjectAttribute;
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
		outputList.addElement("Comienza la ejecución de la implementacion CreateEntityExecution... espere un momento");
		try {
			createAttributeFromNewElement(projectFile.getAbsolutePath());
			outputList.addElement("Ejecución terminada");
		} catch (Exception e) {
			outputList.addElement("ERROR: " + e.getMessage());
		}
	}
	
	private void createAttributeFromNewElement(String projectFileWithPath){
		
		Model model = new Model();
		model.setName("SystemUser");
		model.setDescription("Usuario del Sistema");
		
		EAModelInteraction modifyModel = new EAModelInteraction();
				
		ModelObjectAttribute modelAttribute = new ModelObjectAttribute();
		modelAttribute.setName("nameJOG2");
		modelAttribute.setAttributeType(AttributeType.STRING);
		modelAttribute.setDescription("atributo de nombre");
		modelAttribute.setRequired(true);
		
		ModelObjectAttribute secondModelAttribute = new ModelObjectAttribute();
		secondModelAttribute.setName("registrationDate");
		secondModelAttribute.setAttributeType(AttributeType.STRING);
		secondModelAttribute.setDescription("atributo de fecha de alta");
		secondModelAttribute.setFormat("date-time");
				
		ModelArrayAttribute thirdModelAttribute = new ModelArrayAttribute();
		thirdModelAttribute.setName("observations");
		thirdModelAttribute.setAttributeType(AttributeType.STRING);
		thirdModelAttribute.setDescription("atributo de observaciones");
				
		ModelObjectAttribute fourthModelAttribute = new ModelObjectAttribute();
		fourthModelAttribute.setName("contact1");
		fourthModelAttribute.setSubtype("Contract");
		fourthModelAttribute.setAttributeType(AttributeType.OBJECT);
		fourthModelAttribute.setDescription("contrato relacionado");
		fourthModelAttribute.setRequired(true);
		fourthModelAttribute.setReadOnly(true);
				
		ModelEnumAttribute fifthModelAttribute = new ModelEnumAttribute();
		fifthModelAttribute.setName("operationType");
		fifthModelAttribute.setDescription("tipo de operación");
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
		
		modifyModel.workOnNewEntity(projectFileWithPath, model, "{98F20947-C2FF-40bb-B815-7F1972040190}");
		
	}
	
	
	
	
	
}
