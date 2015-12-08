package mx.com.adesis.asodesign.eaintegration.model.attribute.api;

import mx.com.adesis.asodesign.eaintegration.model.enums.AttributeType;

public interface IArrayAttribute extends IAttribute {
	AttributeType getAttributeType();

	void setAttributeType(AttributeType attributeType);

	Integer getMinItems();

	void setMinItems(Integer minItems);

	Boolean getUniqueItems();

	void setUniqueItems(Boolean uniqueItems);
}
