package mx.com.adesis.asodesign.eamodeler.spreadsheettomodel;

import lombok.extern.slf4j.Slf4j;

import org.junit.Ignore;
import org.junit.Test;

@Slf4j
public class CreateEntitiesTest {

	@Test
	@Ignore
	public void testGetElementDetailTree() throws Exception {
		
		CreateEntities createEntities = new CreateEntities();
		
		String mdcFile = "C:\\Temp\\Canonico_new_3.xls";
		String eaFile = "C:\\proyectos\\proyecto_ASO_multicanal\\diseño\\enterpsise_architect\\aso-arquitect\\design-template-aso.eap";
				
		createEntities.createEntities(eaFile, mdcFile, "{438ADC07-A35B-4367-93B7-B5E413A46685}");
				
	}
}
