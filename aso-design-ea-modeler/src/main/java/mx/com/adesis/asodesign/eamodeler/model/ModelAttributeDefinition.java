package mx.com.adesis.asodesign.eamodeler.model;

import lombok.Data;
import mx.com.adesis.asodesign.eaintegration.model.enums.AttributeType;

@Data
public class ModelAttributeDefinition {
	
	public AttributeType attributeType;
	public String attributeSubtype;
	
	
}
