package mx.com.adesis.asodesign.eamodeler.model;

import java.util.List;

import lombok.Data;
import mx.com.adesis.asodesign.eaintegration.model.api.IEnumAttribute;

@Data
public class ModelEnumAttribute extends ModelAttribute implements IEnumAttribute{
	
	public List<String> enumValues;

	public String parseAsEAModelType() {
		String eaType = this.getSubtype();
		return eaType;
	}
	
	
}
