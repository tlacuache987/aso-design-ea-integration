package mx.com.adesis.asodesign.eamodeler.generatesourcecode;

import org.junit.Ignore;
import org.junit.Test;

public class GenerateSourceCodeTest {

	@Test
	//@Ignore
	public void generateSourceCode() {
		
		//String packageGuidService = "{F2339377-96CA-44cb-9DF6-36D218BB1B84}";
		String packageGuidModel = "{38197096-5ABB-4552-891F-33565BA7743B}";
		
		String eaFile = "C:\\proyectos\\proyecto_ASO_multicanal\\diseño\\enterpsise_architect\\aso-arquitect\\design-template-aso.eap";
		
		GenerateSourceCode sc = new GenerateSourceCode();
		//sc.generateSourceCode(eaFile, packageGuidService);
		sc.generateSourceCode(eaFile, packageGuidModel, "raml", "C:\\Temp\\codeGen");
	}
	
	@Test
	//@Ignore
	public void setGenType() {
		
		String packageGuid = "{9B3E07B6-FDE7-44c6-AF6C-1194A56BF147}";
		String eaFile = "C:\\proyectos\\proyecto_ASO_multicanal\\diseño\\enterpsise_architect\\aso-arquitect\\design-template-aso.eap";
		
		GenerateSourceCode sc = new GenerateSourceCode();
		sc.setGenType(eaFile, packageGuid, "JSON_SCHEMA");
	}
	
	@Test
	public void changeFilesExtension(){
		
		GenerateSourceCode sc = new GenerateSourceCode();
		sc.changeAllFilesExtension("C:\\Temp\\codeGen", "raml");
		
	}
	
	@Test
	public void generateSamples(){
		GenerateSourceCode sc = new GenerateSourceCode();
		sc.createEAElementSamples("C:\\proyectos\\proyecto_ASO_multicanal\\diseño\\enterpsise_architect\\aso-arquitect\\design-template-aso.eap",
				"{AAA333A4-2F78-447d-8F01-A5D72983FECC}", "C:\\Temp\\codeGen");
		
	}


}
