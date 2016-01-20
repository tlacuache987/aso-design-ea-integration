package mx.com.adesis.asodesign.eamodeler.execution;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;

import javax.swing.DefaultListModel;

import mx.com.adesis.asodesign.eamodeler.compare.CompareModel;
import mx.com.adesis.asodesign.eamodeler.compare.CompareModelType;
import mx.com.adesis.asodesign.eamodeler.modeltospreadsheet.CreateAllEntitiesSpreadsheet;
import mx.com.adesis.asodesign.eamodeler.ui.ExecutionUI;

public class ModelDifferencesExecution implements IExecution {

	
	public String toString() {
		return "ModelDifferencesExecution";
	}

	public String getDescription() {
		return "Obtiene las diferencias entre el MCD y el Modelado en EA";
	}

	public void runProcess(File projectFile, File jsonSchemaFile, ExecutionUI uiFrame, String guid){ 
		
		String excelFile = "C:\\Temp\\Canonico.xls";
				
		DefaultListModel outputList = uiFrame.getOutputListModel();
		
		try{
		
			CompareModel compareModel = new CompareModel();
			String result = compareModel.compareModels(excelFile, projectFile.getAbsolutePath(), CompareModelType.BOTH);
			
			//Copia el texto al Clipboard
			StringSelection stringSelection = new StringSelection(result);
			Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
			clpbrd.setContents(stringSelection, null);
				
			outputList.addElement(result);
			outputList.addElement("Proceso Terminado... texto copiado en el Portapapeles");
		
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
