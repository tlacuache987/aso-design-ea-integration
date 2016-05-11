package mx.com.adesis.asodesign.eamodeler.modeltospreadsheet;

import lombok.extern.slf4j.Slf4j;

import org.junit.Ignore;
import org.junit.Test;

@Slf4j
public class CreateEnumEntitiesSpreadsheetTest {

	@Test
	//@Ignore
	public void testGetElementDetailTree() throws Exception {
		
		CreateEnumEntitiesSpreadsheet spreedSheet = new CreateEnumEntitiesSpreadsheet();
		
		String eaFile = "C:\\proyectos\\proyecto_ASO_multicanal\\diseño\\enterpsise_architect\\aso-arquitect\\design-template-aso-act.eap";
		String exitFile = "C:\\Temp\\EA-ASO-Enum-Model.xls";
		
		spreedSheet.createEntitiesSpreedSheet(eaFile, exitFile);
				
	}
}

