package mx.com.adesis.asodesign.eamodeler.modeltospreadsheet;

import lombok.extern.slf4j.Slf4j;
import mx.com.adesis.asodesign.eaintegration.model.api.IModel;

import org.junit.Ignore;
import org.junit.Test;

@Slf4j
public class CreateMappingsSpreadsheetTest {

	@Test
	@Ignore
	public void testGetElementDetailTree() throws Exception {
		
		CreateMappingSpreadsheet spreedSheet = new CreateMappingSpreadsheet();
		String escrowOfficeId = "{6128C7F4-C5C1-44a7-B668-9C08504FED7D}";
		String escrowInvestmentContract = "{AC3BF4D9-83D7-48d9-93E1-BFE556D87CED}";
		IModel iModel = spreedSheet.getElementDetailTree("C:\\proyectos\\proyecto_ASO_multicanal\\diseño\\enterpsise_architect\\aso-arquitect\\design-template-aso.eap",
				escrowInvestmentContract);
		log.debug(iModel.toString());
		
		String mapping = spreedSheet.getElementDetailTreeAsSpreadSheetRows(null, iModel);
		
		log.debug(mapping);
		
		
	}
}
