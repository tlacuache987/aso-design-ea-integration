package mx.com.adesis.asodesign.eamodeler.execution;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;

import javax.swing.DefaultListModel;

import org.sparx.Repository;

import mx.com.adesis.asodesign.eaintegration.model.api.IModel;
import mx.com.adesis.asodesign.eamodeler.modeltospreadsheet.CreateMappingSpreadsheet;
import mx.com.adesis.asodesign.eamodeler.ui.ExecutionUI;

public class CreateEntityMappingExecution implements IExecution {
	
	
	public String toString() {
		return "CreateEntityMappingExecution";
	}
	
	@Override
	public String getDescription() {
		return "Crea el mapping de llenado de hoja ASO de una entidad dada";
	}

	public void runProcess(File projectFile, File jsonSchemaFile, ExecutionUI uiFrame, String elementGuid) {
		
		DefaultListModel outputList = uiFrame.getOutputListModel();
		
		try{
			
			CreateMappingSpreadsheet spreedSheet = new CreateMappingSpreadsheet();
			IModel iModel = spreedSheet.getElementDetailTree(projectFile.getAbsolutePath(), elementGuid);
			outputList.addElement(iModel.toString());
			
			String mapping = spreedSheet.getElementDetailTreeAsSpreadSheetRows(null, iModel);
			
			//Copia el texto al Clipboard
			StringSelection stringSelection = new StringSelection(mapping);
			Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
			clpbrd.setContents(stringSelection, null);
			
		
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
