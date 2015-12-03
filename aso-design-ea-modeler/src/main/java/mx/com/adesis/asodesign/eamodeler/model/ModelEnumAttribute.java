package mx.com.adesis.asodesign.eamodeler.model;

import java.util.List;

import lombok.Data;
import mx.com.adesis.asodesign.eaintegration.api.IEnumAttribute;
import mx.com.adesis.asodesign.eaintegration.api.IObjectAttribute;
import mx.com.adesis.asodesign.eaintegration.enums.AttributeType;

@Data
public class ModelEnumAttribute  extends ModelAttribute implements IEnumAttribute{
	
	public List<String> enumValues;
	
}
