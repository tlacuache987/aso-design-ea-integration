package mx.com.adesis.asodesign.eamodeler.modeltospreadsheet;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import mx.com.adesis.asodesign.eaintegration.model.api.IModel;
import mx.com.adesis.asodesign.eaintegration.model.attribute.api.IAttribute;
import mx.com.adesis.asodesign.eamodeler.EAModelInteractionHelper;
import mx.com.adesis.asodesign.mcd.MCDInteractionHelper;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

@Slf4j
public class CreateEnumEntitiesSpreadsheet {
	
	public void createEntitiesSpreedSheet(String EAFileName, String exitFile){
		
		try{
			
			//creea el archivo excel
			FileOutputStream fileOut = new FileOutputStream(exitFile);
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet worksheet = workbook.createSheet("EA ASO Model");
			HSSFRow row = createSpreedSheetRow(worksheet, 0);
			addCell(workbook, row, 0, "Entidad");
			addCell(workbook, row, 1, "Atributo");
			addCell(workbook, row, 2, "Descripcion");
			addCell(workbook, row, 3, "Tipo Ext");
			addCell(workbook, row, 4, "SubTipo Ext");
			addCell(workbook, row, 5, "Tipo Int");
			addCell(workbook, row, 6, "Restriccion");
			addCell(workbook, row, 7, "Tipo Atributo");
						
			List<IModel> allEAmodelList = EAModelInteractionHelper.getEAElementDetailTree(EAFileName);
			int rowIndex = 1;
			for (IModel iModel : allEAmodelList) {
				
				
				if(iModel.getStereotype().equalsIgnoreCase("enumeration")){
				
				List<IAttribute> attributes = iModel.getAttributes();
								
					for (IAttribute iAttribute : attributes) {
						HSSFRow attributeRow = createSpreedSheetRow(worksheet, rowIndex++);
						addCell(workbook, attributeRow, 0, iModel.getName());
						addCell(workbook, attributeRow, 1, iAttribute.getName());
						addCell(workbook, attributeRow, 2, iAttribute.getDescription());
						addCell(workbook, attributeRow, 3, getAttributeExtType(iAttribute.getSubtype()));
						addCell(workbook, attributeRow, 4, getAttributeSubTypeExt(iAttribute.getSubtype()));
						addCell(workbook, attributeRow, 5, iAttribute.getSubtype());
						//addCell(workbook, attributeRow, 6, "");
						addCell(workbook, attributeRow, 7, getAttributeType(iAttribute.getSubtype()));
					}
				}
			}
							
			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
				
	}
	
	private HSSFRow createSpreedSheetRow(HSSFSheet worksheet, int index){
		HSSFRow row1 = worksheet.createRow((short) index);
		return row1;
	}
	
	private void addCell(HSSFWorkbook workbook, HSSFRow row1, int index, String cellValue){
		HSSFCell cellA1 = row1.createCell((short) index);
		cellA1.setCellValue(cellValue);
		//HSSFCellStyle cellStyle = workbook.createCellStyle();
		//cellStyle.setFillForegroundColor(HSSFColor.GOLD.index);
		//cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		//cellA1.setCellStyle(cellStyle);
	}
	
	private String getAttributeType( String type )
	{
		String attType = "Complejo";
		if(type != null){
			type = type.trim();
			if(type.equals("string") || type.equals("String") || type.equals("Boolean") || type.equals("boolean")
				|| type.equals("Date") || type.equals("DateTime") || type.equals("dateTime") || type.equals("BigDecimal") 
				|| type.equals("Integer") || type.equals("Long") || type.equals("long") || type.equals("float") || type.equals("Float")){
				attType = "Simple";
			} 
			else if (type.contains("[") || type.contains("<") || type.equals("enum") || type.equals("ENUM")){
				attType = "Complejo";
			}
		}
		return attType;
	}
	
	private String getAttributeExtType( String type )
	{
		String attType = "Object";
		if(type != null){
			type = type.trim();
			if(type.equals("string") || type.equals("String") || type.equals("Boolean") || type.equals("boolean")
				|| type.equals("Date") || type.equals("DateTime") || type.equals("Integer") 
				|| type.equals("Long") || type.equals("long") || type.equals("float") || type.equals("Float")){
				attType = type;
			} 
			else if (type.equals("BigDecimal")){
				attType = "Number";
			}
			else if (type.equals("enum") || type.equals("ENUM")){
				attType = "Enum";
			}
			else if (type.contains("[") || type.contains("<")){
				attType = "Array";
			}
		}
		return attType;
	}
	
	private String getAttributeSubTypeExt( String type )
	{
		String attType = type;
		if(type != null){
			type = type.trim();
			if(type.equals("string") || type.equals("String") || type.equals("Boolean") || type.equals("boolean")
				|| type.equals("Date") || type.equals("DateTime") || type.equals("BigDecimal") || type.equals("Integer") || type.equals("integer")
				|| type.equals("float") || type.equals("Float") || type.equals("int") || type.equals("Long") || type.equals("long")){
				attType = "";
			} else {
				attType = type;
			}
		}
		return attType;
	}

}
