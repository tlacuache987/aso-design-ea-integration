package mx.com.adesis.asodesign.eamodeler.compare;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;
import mx.com.adesis.asodesign.eaintegration.model.api.IModel;
import mx.com.adesis.asodesign.eaintegration.model.api.impl.Model;
import mx.com.adesis.asodesign.eaintegration.model.attribute.api.IAttribute;
import mx.com.adesis.asodesign.eaintegration.model.attribute.api.impl.ModelObjectAttribute;
import mx.com.adesis.asodesign.eamodeler.EAModelInteractionHelper;
import mx.com.adesis.asodesign.eamodeler.modeltospreadsheet.SpreadSheetAttribute;
import mx.com.adesis.asodesign.mcd.MCDInteractionHelper;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.collections.Predicate;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.velocity.runtime.directive.Foreach;
import org.sparx.Attribute;
import org.sparx.Collection;
import org.sparx.Element;
import org.sparx.Repository;

@Slf4j
public class CompareModel {
	
	
	
	public String compareModels(String excelFile, String eapFile, CompareModelType origin){
		
		log.debug("Obteniendo las entidades de excel CMD");
		List<IModel> excelModelList = MCDInteractionHelper.parseMCDExcelFile(excelFile);
		log.debug("Obteniendo las entidades del modelo EA");
		List<IModel> eaModelList = EAModelInteractionHelper.getEAElementDetailTree(eapFile);
		
		String result = "";
		if(origin == CompareModelType.EXCEL_ORIGIN){
			result = executeComparation(excelFile, eapFile, CompareModelType.EXCEL_ORIGIN, excelModelList, eaModelList);
		} else if(origin == CompareModelType.EA_ORIGIN){
			result = executeComparation(excelFile, eapFile, CompareModelType.EA_ORIGIN, eaModelList, excelModelList );
		} else {
			result = executeComparation(excelFile, eapFile, CompareModelType.EXCEL_ORIGIN, excelModelList, eaModelList) + "\n";
			result += executeComparation(excelFile, eapFile, CompareModelType.EA_ORIGIN, eaModelList, excelModelList);
		}		
		
		return result;
	}
	
	private String executeComparation(String excelFile, String eapFile, CompareModelType origin, List<IModel> originModelList, List<IModel> destModelList){
		
		StringBuffer result = new StringBuffer();
						
		Map<String,IModel> originModelmap = new HashMap<String,IModel>();
		for (IModel e : originModelList) {
			String name = e.getName();
			originModelmap.put(name, e);
		}
		
		Map<String,IModel> destModelmap = new HashMap<String,IModel>();
		for (IModel e : destModelList) {
			destModelmap.put(e.getName(), e);
		}
		
		String excelTitle = "MCD";
		String eaTitle = "EA";
		String title = null;
		if(origin == CompareModelType.EA_ORIGIN){
			result.append("Reporte de la comparación de " + eaTitle + " a " + excelTitle + "\n");
			title = excelTitle;
		} else {
			result.append("Reporte de la comparación de " + excelTitle + " a " + eaTitle + "\n");
			title = eaTitle;
		}
		
		//Compara los resultados de las dos listas
		for (IModel originModel : originModelList) {
			String name = originModel.getName();
			IModel destModel = destModelmap.get(name);
			if(destModel == null){
				if(!originModel.getAttributes().isEmpty()){
					result.append("la entidad " + name + " no existe en el modelo " + title + "\n");
				}
			}
			else{
				//evalua atributos
				List<IAttribute> originAttributeList = originModel.getAttributes();
				List<IAttribute> destAttributeList = destModel.getAttributes();
				List<IAttribute> intersectionList = new ArrayList<IAttribute>(); //ListUtils.subtract(originAttributeList, destAttributeList);
				
				Map<String, IAttribute> destAttributeMap = new HashMap<String, IAttribute>();
				for(IAttribute att: destAttributeList){
					destAttributeMap.put(att.getName(), att);
				}
				
				for(IAttribute att: originAttributeList){
					String attName = att.getName();
					int indexOfCharacter = attName.indexOf("[");
					if(indexOfCharacter > 0){
						attName = attName.substring(0, indexOfCharacter);
						attName = attName.trim();
					}					
					if( !destAttributeMap.containsKey(attName) ){
						if(!attName.equals("")){
							intersectionList.add(att);
						}
					}
				}
								
				if(!intersectionList.isEmpty()){
					result.append("la entidad " + name + " tiene diferencias en sus atributos\n");
					for (IAttribute iAttribute : intersectionList) {
						result.append("\t atributo " + iAttribute.getName() + " no presente en " + title + "\n");
					}				
				}
			}
		}		
	
		
		return result.toString();
	}
	
}
