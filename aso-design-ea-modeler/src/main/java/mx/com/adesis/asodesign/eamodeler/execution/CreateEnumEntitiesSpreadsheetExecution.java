package mx.com.adesis.asodesign.eamodeler.execution;

import java.io.File;
import java.util.Map;

import javax.swing.DefaultListModel;

import mx.com.adesis.asodesign.eamodeler.modeltospreadsheet.CreateAllEntitiesSpreadsheet;
import mx.com.adesis.asodesign.eamodeler.modeltospreadsheet.CreateEnumEntitiesSpreadsheet;
import mx.com.adesis.asodesign.eamodeler.ui.ExecutionUI;

public class CreateEnumEntitiesSpreadsheetExecution implements IExecution {

	
	public String toString() {
		return "CreateEnumEntitiesSpreadsheetExecution";
	}

	public String getDescription() {
		return "Crea una hoja de calculo con todas las entidades del modelo EA de tipo ENUM";
	}

	public void runProcess(ExecutionUI uiFrame, File projectFile, String elementGuid, Map<String, Object> additionalParameters) {
		
		DefaultListModel outputList = uiFrame.getOutputListModel();
		
		try{
		
			CreateEnumEntitiesSpreadsheet spreedSheet = new CreateEnumEntitiesSpreadsheet();
			
			String exitFile = "C:\\Temp\\EA-ASO-Enum-Model.xls";
			
			spreedSheet.createEntitiesSpreedSheet(projectFile.getAbsolutePath(), exitFile);
			
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
