package mx.com.adesis.asodesign.eamodeler.modeltospreadsheet;

import lombok.extern.slf4j.Slf4j;

import org.junit.Ignore;
import org.junit.Test;

@Slf4j
public class CreateAllEntitiesSpreadsheetTest {

	@Test
	@Ignore
	public void testGetElementDetailTree() throws Exception {
		
		CreateAllEntitiesSpreadsheet spreedSheet = new CreateAllEntitiesSpreadsheet();
		
		String mdcFile = "C:\\Temp\\Canonico.xls";
		String eaFile = "C:\\proyectos\\proyecto_ASO_multicanal\\diseño\\enterpsise_architect\\aso-arquitect\\design-template-aso.eap";
		String exitFile = "C:\\Temp\\EA-ASO-Model.xls";
		
		spreedSheet.createAllEntitiesSpreedSheet(eaFile, mdcFile, exitFile);
				
	}
}

