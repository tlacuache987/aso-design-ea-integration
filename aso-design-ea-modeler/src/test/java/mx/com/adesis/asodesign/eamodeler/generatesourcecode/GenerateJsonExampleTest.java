package mx.com.adesis.asodesign.eamodeler.generatesourcecode;

import mx.com.adesis.asodesign.eaintegration.model.api.IModel;

import org.junit.Ignore;
import org.junit.Test;

public class GenerateJsonExampleTest {

	@Test
	//@Ignore
	public void generateJsonExamples() {
		
		//String packageGuidService = "{F2339377-96CA-44cb-9DF6-36D218BB1B84}";
		String packageGuidModel = "{4C07AC6B-86C1-45d4-9559-1D07A3619F34}";
		
		String eaFile = "C:\\proyectos\\proyecto_ASO_multicanal\\diseño\\enterpsise_architect\\aso-arquitect\\design-template-aso-act.eap";
		
		GenerateJsonExample sc = new GenerateJsonExample();
		//sc.generateSourceCode(eaFile, packageGuidService);
		IModel model = sc.getElementDetailTree(eaFile, packageGuidModel);
		System.out.println(model);
		
		String json = sc.getElementDetailTreeAsJson(model);
		
		System.out.println(json);
		
	}
	
}
