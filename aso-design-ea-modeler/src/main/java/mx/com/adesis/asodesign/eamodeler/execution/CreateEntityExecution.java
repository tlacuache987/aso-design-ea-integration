package mx.com.adesis.asodesign.eamodeler.execution;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;

import mx.com.adesis.asodesign.eaintegration.model.api.IModel;
import mx.com.adesis.asodesign.eaintegration.model.api.impl.Model;
import mx.com.adesis.asodesign.eaintegration.model.attribute.api.IAttribute;
import mx.com.adesis.asodesign.eaintegration.model.attribute.api.impl.ModelArrayAttribute;
import mx.com.adesis.asodesign.eaintegration.model.attribute.api.impl.ModelEnumAttribute;
import mx.com.adesis.asodesign.eaintegration.model.attribute.api.impl.ModelObjectAttribute;
import mx.com.adesis.asodesign.eaintegration.model.enums.AttributeType;
import mx.com.adesis.asodesign.eaintegration.service.api.IModelService;
import mx.com.adesis.asodesign.eamodeler.EAModelInteraction;
import mx.com.adesis.asodesign.eamodeler.ui.ExecutionUI;


public class CreateEntityExecution implements IExecution {

	
	public String toString() {
		return "CreateEntityExecution";
	}

	public String getDescription() {
		return "Crea una entidad en EA apartir de un JSON Schema";
	}

	public void runProcess(File projectFile, File jsonSchemaFile, ExecutionUI uiFrame, String guid) {
		DefaultListModel outputList = uiFrame.getOutputListModel();
		outputList.addElement("Comienza la ejecución de la implementacion CreateEntityExecution... espere un momento");
		try {
			createAttributeFromNewElement2(jsonSchemaFile.getAbsolutePath(), uiFrame);
			outputList.addElement("Ejecución terminada");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	

	private void createAttributeFromNewElement2(String projectFileWithPath, ExecutionUI uiFrame) throws RuntimeException {
		
		DefaultListModel outputList = uiFrame.getOutputListModel();
		
		ApplicationContext appContext = 
				new ClassPathXmlApplicationContext("classpath:/spring/eamodeler/asodesign-eamodeler-service-context.xml");
		IModelService modelService = (IModelService) appContext.getBean("modelService");
		
		//se lee el arhivo con el contentenido de JSON Schema
		CustomResourceLoader cust = 
		           (CustomResourceLoader) appContext.getBean("customResourceLoader");
		    	
		Resource resource = cust.getResource("file:" + projectFileWithPath);
		
		StringBuffer JSONSchemaStr = new StringBuffer();
		InputStream is = null;
		IModel model = null;
		try{
			is = resource.getInputStream();
			
			model = modelService.getModel(is);
			outputList.addElement("SALIDA: " + model);
			
			is = resource.getInputStream();
			
		    BufferedReader br = new BufferedReader(new InputStreamReader(is));
		        	
		          String line;
		          while ((line = br.readLine()) != null) {
		        	  JSONSchemaStr.append(line);
		        	  outputList.addElement(line);
		          } 
		          br.close();
		        	
		} catch(IOException e){
		    	throw new RuntimeException(e);
		}		
		
		EAModelInteraction modifyModel = new EAModelInteraction();
		
		List models = new ArrayList<IModel>();
		models.add(model);
		
		modifyModel.workOnEntityList(projectFileWithPath, models, "{98F20947-C2FF-40bb-B815-7F1972040190}");
	}
	
	private void createAttributeFromNewElement(String projectFileWithPath) {
		
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
		
		modifyModel.workOnEntityList(projectFileWithPath, models, "{98F20947-C2FF-40bb-B815-7F1972040190}");
		
	}

	@Override
	public boolean openWindow() {
		return false;
	}
	
	
	
	
	
}
