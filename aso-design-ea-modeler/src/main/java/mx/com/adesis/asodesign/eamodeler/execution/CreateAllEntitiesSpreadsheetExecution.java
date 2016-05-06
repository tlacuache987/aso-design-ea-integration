package mx.com.adesis.asodesign.eamodeler.execution;

import java.io.File;
import java.util.Map;

import javax.swing.DefaultListModel;

import mx.com.adesis.asodesign.eamodeler.modeltospreadsheet.CreateAllEntitiesSpreadsheet;
import mx.com.adesis.asodesign.eamodeler.ui.ExecutionUI;

public class CreateAllEntitiesSpreadsheetExecution implements IExecution {

	
	public String toString() {
		return "CreateAllEntitiesSpreadsheetExecution";
	}

	public String getDescription() {
		return "Crea una hoja de calculo con todas las entidades del modelo EA";
	}

	public void runProcess(ExecutionUI uiFrame, File projectFile, String elementGuid, Map<String, Object> additionalParameters) {
		
		DefaultListModel outputList = uiFrame.getOutputListModel();
		
		try{
		
			CreateAllEntitiesSpreadsheet spreedSheet = new CreateAllEntitiesSpreadsheet();
			
			String mdcFile = "C:\\Temp\\Canonico.xls";
			String exitFile = "C:\\Temp\\EA-ASO-Model.xls";
			
			spreedSheet.createAllEntitiesSpreedSheet(projectFile.getAbsolutePath(), mdcFile, exitFile);
			
			outputList.addElement("Hoja de calculo creada en: " + exitFile);
		
		}
		catch(Exception e){
			outputList.addElement(e.getMessage());
		}
				
	}

	@Override
	public boolean openWindow() {
		return false;
	}

}
