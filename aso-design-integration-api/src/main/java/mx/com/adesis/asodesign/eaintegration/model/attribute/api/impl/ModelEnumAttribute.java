package mx.com.adesis.asodesign.eaintegration.model.attribute.api.impl;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import mx.com.adesis.asodesign.eaintegration.model.attribute.api.IEnumAttribute;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ModelEnumAttribute extends ModelAttribute implements IEnumAttribute {
	private List<String> enumValues;

	public String parseAsEAModelType() {
		String eaType = this.getSubtype();
		return eaType;
	}
}
