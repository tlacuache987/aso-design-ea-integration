package mx.com.adesis.asodesign.eamodeler.model;

import lombok.Data;
import mx.com.adesis.asodesign.eaintegration.model.api.IObjectAttribute;
import mx.com.adesis.asodesign.eaintegration.model.enums.AttributeType;

@Data
public class ModelObjectAttribute extends ModelAttribute implements IObjectAttribute {

	public String parseAsEAModelType() {
		String javaType = null;
		AttributeType attType = this.getAttributeType();
		javaType = "String";
		if (attType == AttributeType.BOOLEAN) {
			javaType = "Boolean";
		} else if (attType == AttributeType.INTEGER) {
			javaType = "Integer";
		} else if (attType == AttributeType.DATE) {
			javaType = "Date";
		} else if (attType == AttributeType.NUMBER) {
			javaType = "Double";
		} else if (attType == AttributeType.OBJECT) {
			javaType = this.getSubtype();
		}
		return javaType;
	}

}
