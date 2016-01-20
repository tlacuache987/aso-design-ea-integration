package mx.com.adesis.asodesign.eamodeler.compare;

import java.util.List;





import mx.com.adesis.asodesign.eaintegration.model.api.IModel;
import mx.com.adesis.asodesign.eaintegration.model.api.impl.Model;

import org.junit.Ignore;
import org.junit.Test;

public class CompareModelTest {

	@Test
	@Ignore
	public void testCompareModelExcelToEA(){
		
		String excelFile = "C:\\Temp\\Canonico.xls";
		String eaFile = "C:\\proyectos\\proyecto_ASO_multicanal\\diseño\\enterpsise_architect\\aso-arquitect\\design-template-aso.eap";
		
		String result = executeComparation(excelFile, eaFile, CompareModelType.EXCEL_ORIGIN);
				
		System.out.println(result);
		
	}
	
	@Test
	@Ignore
	public void testCompareModelEAToExcel(){
		
		String excelFile = "C:\\Temp\\Canonico.xls";
		String eaFile = "C:\\proyectos\\proyecto_ASO_multicanal\\diseño\\enterpsise_architect\\aso-arquitect\\design-template-aso.eap";
		
		String result = executeComparation(excelFile, eaFile, CompareModelType.EA_ORIGIN);
				
		System.out.println(result);
		
	}
	
	@Test
	@Ignore
	public void testCompareBothModels(){
		
		String excelFile = "C:\\Temp\\Canonico.xls";
		String eaFile = "C:\\proyectos\\proyecto_ASO_multicanal\\diseño\\enterpsise_architect\\aso-arquitect\\design-template-aso.eap";
		
		String result = executeComparation(excelFile, eaFile, CompareModelType.BOTH);
				
		System.out.println(result);
		
	}
	
	private String executeComparation(String excelFile, String eaFile, CompareModelType compareModelType){
		CompareModel compareModel = new CompareModel();
		String result = compareModel.compareModels(excelFile, eaFile, compareModelType);
		return result;
	}

}
