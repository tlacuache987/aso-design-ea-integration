package mx.com.adesis.asodesign.eaintegration.model.attribute.api.impl;

import java.util.List;

import lombok.Data;
import mx.com.adesis.asodesign.eaintegration.model.attribute.api.IObjectAttribute;
import mx.com.adesis.asodesign.eaintegration.model.enums.AttributeType;

@Data
public class ObjectAttribute extends ModelAttribute implements IObjectAttribute {
	private AttributeType attributeType;
	private List<String> allowedValues;

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
