package mx.com.adesis.asodesign.eamodeler.modeltojson;

import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.ui.velocity.VelocityEngineUtils;

import lombok.extern.slf4j.Slf4j;
import mx.com.adesis.asodesign.eaintegration.model.api.IModel;
import mx.com.adesis.asodesign.eamodeler.modeltospreadsheet.SpreadSheetAttribute;

@Slf4j
public class ModelToJsonUtils {
	
	private static VelocityEngine velocityEngine;
	
	public void setVelocityEngine(VelocityEngine velocityEngine){
		this.velocityEngine = velocityEngine;
	}
	
	public void parseJson(IModel model){
		Map<String,Object> valueMap = new HashMap<String,Object>();
		valueMap.put("model", model);
		log.debug("VelocityEngine: " + velocityEngine);
		String jsonSchema = VelocityEngineUtils.mergeTemplateIntoString(
	            velocityEngine, "src/main/resources/templates/template1.vm", valueMap);
		log.debug(jsonSchema);
	}
	
	public String parseToRaml1(SpreadSheetAttribute attribute){
		Map<String,Object> valueMap = new HashMap<String,Object>();
		valueMap.put("attribute", attribute);
		log.debug("VelocityEngine: " + velocityEngine);
		String raml = VelocityEngineUtils.mergeTemplateIntoString(
	            velocityEngine, "src/main/resources/templates/templateRaml1.vm", valueMap);
		return raml;
	}
	
	
	
}
