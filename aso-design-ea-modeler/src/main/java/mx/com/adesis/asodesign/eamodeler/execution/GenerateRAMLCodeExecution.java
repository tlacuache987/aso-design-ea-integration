package mx.com.adesis.asodesign.eamodeler.execution;

import java.io.File;
import java.util.Map;

import javax.swing.DefaultListModel;

import mx.com.adesis.asodesign.eamodeler.generatesourcecode.GenerateSourceCode;
import mx.com.adesis.asodesign.eamodeler.ui.ExecutionUI;

public class GenerateRAMLCodeExecution implements IExecution {
	
	public String toString() {
		return "GenerateRAMLCodeExecution";
	}
	
	@Override
	public String getDescription() {
		return "Genera el código RAML, esquemas JSON y eemplos de todo el Modelo";
	}

	public void runProcess(ExecutionUI uiFrame, File projectFile, String elementGuid, Map<String, Object> additionalParameters) {
		
		DefaultListModel outputList = uiFrame.getOutputListModel();
		
		try{
			
			String codeGenPath = "C:\\Temp\\codeGen";
			
			//Genera el codigo RAML
			String packageGuidServices = "{AAA333A4-2F78-447d-8F01-A5D72983FECC}";
						
			GenerateSourceCode sc = new GenerateSourceCode();
			//sc.generateSourceCode(eaFile, packageGuidService);
			sc.generateSourceCode(projectFile.getAbsolutePath(), packageGuidServices, "raml", codeGenPath);
			
			outputList.addElement("Se generó código de servicios en " + codeGenPath);
			
			//Genera el codigo de los modelos JSON Schema
			String packageGuidModel = "{CC351FB4-6244-490f-976B-488ABD82008D}";
			sc.generateSourceCode(projectFile.getAbsolutePath(), packageGuidModel, "raml", codeGenPath);
			
			outputList.addElement("Se generó código de esquemas JSON en " + codeGenPath);
			
			//Cambia las extensiones a archivos
			sc.changeAllFilesExtension(codeGenPath, "raml");
			
			outputList.addElement("Se cambio la extension (a raml) a los archivos en: " + codeGenPath);
			
			//Crea los ejemplos
			sc.createEAElementSamples(projectFile.getAbsolutePath(),
					packageGuidServices, codeGenPath);
			
			outputList.addElement("Se generó código de esquemas Ejemplos JSON en " + codeGenPath);
		}
		catch (Exception e){
			outputList.addElement(e.getMessage());
		}
	}

	@Override
	public boolean openWindow() {
		return false;
	}

}
