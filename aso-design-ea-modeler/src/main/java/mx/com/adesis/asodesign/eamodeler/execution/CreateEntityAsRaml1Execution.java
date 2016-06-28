package mx.com.adesis.asodesign.eamodeler.execution;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.util.Map;

import javax.swing.DefaultListModel;

import org.sparx.Repository;

import mx.com.adesis.asodesign.eaintegration.model.api.IModel;
import mx.com.adesis.asodesign.eamodeler.generatesourcecode.GenerateEntityAsRaml1;
import mx.com.adesis.asodesign.eamodeler.generatesourcecode.GenerateJsonExample;
import mx.com.adesis.asodesign.eamodeler.modeltospreadsheet.CreateMappingSpreadsheet;
import mx.com.adesis.asodesign.eamodeler.ui.ExecutionUI;

public class CreateEntityAsRaml1Execution implements IExecution {
	
	
	public String toString() {
		return "CreateEntityAsRaml1.0Execution";
	}
	
	@Override
	public String getDescription() {
		return "Crea una entidad como formato raml 1.0";
	}

	public void runProcess(ExecutionUI uiFrame, File projectFile, String elementGuid, Map<String, Object> additionalParameters) {
		
		DefaultListModel outputList = uiFrame.getOutputListModel();
		
		try{
			System.out.println(elementGuid);
			System.out.println(projectFile.getAbsolutePath());
			GenerateEntityAsRaml1  entityRaml = new GenerateEntityAsRaml1();
			entityRaml.generateEntity(projectFile.getAbsolutePath(), elementGuid);
	
		}
		catch (Exception e){
			outputList.addElement(e.getMessage());
		}
	}

	@Override
	public boolean openWindow() {
		return true;
	}

}
