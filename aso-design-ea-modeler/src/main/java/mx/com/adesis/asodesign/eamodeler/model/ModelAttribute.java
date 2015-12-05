package mx.com.adesis.asodesign.eamodeler.model;

import java.util.List;

import lombok.Data;
import mx.com.adesis.asodesign.eaintegration.model.api.IAttribute;
import mx.com.adesis.asodesign.eaintegration.model.enums.AttributeType;

@Data
public abstract class ModelAttribute implements IAttribute {
	
	public String name;
	public String format;
	public String description;
	public Boolean required;
	public Boolean readOnly;
	public AttributeType attributeType;
	public String subtype;
	public List<String> allowedValues;
	public String pattern;
	
	public Boolean hasSubtype(){
		Boolean returnHasSubtype = false;
		if (subtype != null && !subtype.isEmpty()){
			returnHasSubtype = true;
		}
		return returnHasSubtype;
	}
	
}
