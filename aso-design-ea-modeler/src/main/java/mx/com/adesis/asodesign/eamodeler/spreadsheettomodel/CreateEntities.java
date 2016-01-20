package mx.com.adesis.asodesign.eamodeler.spreadsheettomodel;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import mx.com.adesis.asodesign.eaintegration.model.api.IModel;
import mx.com.adesis.asodesign.eamodeler.EAModelInteraction;
import mx.com.adesis.asodesign.mcd.MCDInteractionHelper;

@Slf4j
public class CreateEntities {
	
	public void createEntities(String excelFile, String eapFile, String packageGUID){
		
		log.debug("Obteniendo las entidades de excel CMD");
		List<IModel> excelModelList = MCDInteractionHelper.parseMCDExcelFile(excelFile);
		
		EAModelInteraction modifyModel = new EAModelInteraction();
		log.debug("Creando entidades en el paquete " + packageGUID);
		modifyModel.workOnEntityList(eapFile, excelModelList, packageGUID);
			
	}
	

}
