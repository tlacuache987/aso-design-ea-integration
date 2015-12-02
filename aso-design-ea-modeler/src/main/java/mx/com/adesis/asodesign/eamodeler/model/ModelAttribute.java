package mx.com.adesis.asodesign.eamodeler.model;

import lombok.Data;
import mx.com.adesis.asodesign.eaintegration.api.IAttribute;
import mx.com.adesis.asodesign.eaintegration.enums.AttributeType;

@Data
public class ModelAttribute implements IAttribute {
	
	private String name;
	private AttributeType attributeType;
	private String format;
	private String description;
	private Boolean required;
		
}
