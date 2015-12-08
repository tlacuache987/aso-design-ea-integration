package mx.com.adesis.asodesign.eaintegration.model.attribute.api.impl;

import java.util.List;

import lombok.Data;
import mx.com.adesis.asodesign.eaintegration.model.attribute.api.IEnumAttribute;

@Data
public class ModelEnumAttribute extends ModelAttribute implements IEnumAttribute {
	private List<String> enumValues;

	public String parseAsEAModelType() {
		String eaType = this.getSubtype();
		return eaType;
	}
}
