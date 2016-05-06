package mx.com.adesis.asodesign.eamodeler.execution;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListModel;

import mx.com.adesis.asodesign.eamodeler.EAModelInteraction;
import mx.com.adesis.asodesign.eamodeler.ui.ExecutionUI;

public class GetDuplicatedEntitiesExecution implements IExecution {

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Obtiene el listado de entidades repetidas";
	}

	@Override
	public void runProcess(ExecutionUI uiFrame, File projectFile, String elementGuid, Map<String, Object> additionalParameters) {
		
		DefaultListModel outputList = uiFrame.getOutputListModel();
		
		try{
		
			EAModelInteraction modifyModel = new EAModelInteraction();
			List<String> duplicateElements = modifyModel.getDuplicateEntitiesNames(projectFile.getAbsolutePath());
			StringBuffer sb = new StringBuffer();
			for (String elementName : duplicateElements) {
				sb.append(elementName + "\n");
				outputList.addElement(elementName);
			}
			if(duplicateElements == null || duplicateElements.isEmpty()){
				String noDuplicates = "empty list";
				sb.append(noDuplicates);
				outputList.addElement(noDuplicates);
			}
			
			//Copia el texto al Clipboard
			StringSelection stringSelection = new StringSelection(sb.toString());
			Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
			clpbrd.setContents(stringSelection, null);
				
			
			outputList.addElement("Proceso Terminado... texto copiado en el Portapapeles");
	
		}
		catch(Exception e){
			outputList.addElement(e.getMessage());
		}
		
		
	}

	@Override
	public boolean openWindow() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public String toString() {
		return "GetDuplicatedEntitiesExecution";
	}

}
