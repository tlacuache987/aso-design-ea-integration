package mx.com.adesis.asodesign.eaintegration.model.attribute.api.impl;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import mx.com.adesis.asodesign.eaintegration.model.attribute.api.IArrayAttribute;
import mx.com.adesis.asodesign.eaintegration.model.enums.AttributeType;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ModelArrayAttribute extends ModelAttribute implements IArrayAttribute {
	private AttributeType attributeType;
	private Integer minItems;
	private Boolean uniqueItems;

	public String parseAsEAModelType() {
		String javaType = null;
		AttributeType attType = this.getAttributeType();
		javaType = "String[]";
		if (attType == AttributeType.BOOLEAN) {
			javaType = "Boolean[]";
		} else if (attType == AttributeType.INTEGER) {
			javaType = "Integer[]";
		} else if (attType == AttributeType.DATE) {
			javaType = "Date[]";
		} else if (attType == AttributeType.NUMBER) {
			javaType = "Double[]";
		} else if (attType == AttributeType.OBJECT) {
			javaType = this.getSubtype() + "[]";
		}
		return javaType;
	}
}
