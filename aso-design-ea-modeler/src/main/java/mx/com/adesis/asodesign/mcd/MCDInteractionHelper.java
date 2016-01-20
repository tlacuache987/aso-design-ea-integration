package mx.com.adesis.asodesign.mcd;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import mx.com.adesis.asodesign.eaintegration.model.api.IModel;
import mx.com.adesis.asodesign.eaintegration.model.api.impl.Model;
import mx.com.adesis.asodesign.eaintegration.model.attribute.api.IAttribute;
import mx.com.adesis.asodesign.eaintegration.model.attribute.api.impl.ModelObjectAttribute;
import mx.com.adesis.asodesign.eaintegration.model.enums.AttributeType;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 * Ineractua con la hoja de calculo que contiene el MCD
 * @author Jorge
 *
 */
public class MCDInteractionHelper {
	
public static List<IModel> parseMCDExcelFile(String excelFile){ 
		
		List<IModel> modelList = new ArrayList<IModel>(); 		
		try {
		     
		    FileInputStream file = new FileInputStream(new File(excelFile));
		     
		    //Get the workbook instance for XLS file 
		    HSSFWorkbook workbook = new HSSFWorkbook(file);
		 
		    //Get first sheet from the workbook
		    HSSFSheet sheet = workbook.getSheetAt(0);
		     
		    //Iterate through each rows from first sheet
		    Iterator<Row> rowIterator = sheet.iterator();

		    List<IAttribute> modelAttributeList = null;
		    boolean sameModel = false;
		    while(rowIterator.hasNext()) {
		        Row row = rowIterator.next();
		         
		        //For each row, iterate through each columns
		        Iterator<Cell> cellIterator = row.cellIterator();
		        
		        Cell entityCell = row.getCell(1);
		        //System.out.println("entidad: " + entityCell);
		        Cell attributeCell = row.getCell(2);
		        //System.out.println("atributo: " + attributeCell);
		        Cell attributeTypeCell = row.getCell(5);
		        
		        Cell attributeDescCell = row.getCell(9);
		        
		        
		        Model model = null;
		        if(entityCell != null && !entityCell.toString().isEmpty()){
		        	model = new Model();
		        	modelList.add(model);
		        	
		        	String name = entityCell.toString();
					name = name.substring(0, 1).toUpperCase() + name.substring(1);
		        	
		        	model.setName(name);
		        	modelAttributeList = new ArrayList<IAttribute>();
		        	model.setAttributes(modelAttributeList);
		        	sameModel = true;
		        }
		        		        
				if(sameModel && attributeCell != null){
					ModelObjectAttribute modelAttribute = new ModelObjectAttribute();
					modelAttribute.setName(attributeCell.toString());
					if(attributeDescCell != null){
						modelAttribute.setDescription(attributeDescCell.toString());
					}
					modelAttribute.setAttributeType(AttributeType.OBJECT);
					modelAttribute.setSubtype(attributeTypeCell.toString());
					modelAttributeList.add(modelAttribute);					
				}

		    }
		    file.close();
//		    FileOutputStream out = 
//		        new FileOutputStream(new File("C:\\Temp\\Canonico.xls"));
//		    workbook.write(out);
//		    out.close();
		     
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		return modelList;
	}
	
}
