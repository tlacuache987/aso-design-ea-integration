package mx.com.adesis.asodesign.eamodeler.generatesourcecode;

import org.junit.Ignore;
import org.junit.Test;

public class GenerateSourceCodeTest {

	@Test
	@Ignore
	public void test() {
		
		String personaPackage = "{B4F0F82B-EE5D-447b-AC5A-729398EDE563}";
		String eaFile = "c://Temp//design-template.eap";
		
		GenerateSourceCode sc = new GenerateSourceCode();
		sc.generateSourceCode(eaFile, personaPackage);
	}

}
