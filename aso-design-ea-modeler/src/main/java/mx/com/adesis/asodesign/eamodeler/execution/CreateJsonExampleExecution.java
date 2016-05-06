package mx.com.adesis.asodesign.eamodeler.execution;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.util.Map;

import javax.swing.DefaultListModel;

import org.sparx.Repository;

import mx.com.adesis.asodesign.eaintegration.model.api.IModel;
import mx.com.adesis.asodesign.eamodeler.generatesourcecode.GenerateJsonExample;
import mx.com.adesis.asodesign.eamodeler.modeltospreadsheet.CreateMappingSpreadsheet;
import mx.com.adesis.asodesign.eamodeler.ui.ExecutionUI;

public class CreateJsonExampleExecution implements IExecution {
	
	
	public String toString() {
		return "CreateJsonExampleExecution";
	}
	
	@Override
	public String getDescription() {
		return "Crea el ejemplo JSON de una entidad dada";
	}

	public void runProcess(ExecutionUI uiFrame, File projectFile, String elementGuid, Map<String, Object> additionalParameters) {
		
		DefaultListModel outputList = uiFrame.getOutputListModel();
		
		try{
			
			GenerateJsonExample spreedSheet = new GenerateJsonExample();
			IModel iModel = spreedSheet.getElementDetailTree(projectFile.getAbsolutePath(), elementGuid);
			outputList.addElement(iModel.toString());
			
			String mapping = spreedSheet.getElementDetailTreeAsJson(iModel);
			
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
