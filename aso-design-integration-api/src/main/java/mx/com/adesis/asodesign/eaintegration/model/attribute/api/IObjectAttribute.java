package mx.com.adesis.asodesign.eaintegration.model.attribute.api;

import java.util.List;

import mx.com.adesis.asodesign.eaintegration.model.enums.AttributeType;

public interface IObjectAttribute extends IAttribute {

	AttributeType getAttributeType();

	void setAttributeType(AttributeType attributeType);

	List<String> getAllowedValues();

	void setAllowedValues(List<String> allowedValues);
}
