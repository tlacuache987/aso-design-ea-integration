package mx.com.adesis.asodesign.eaintegration.api;

import mx.com.adesis.asodesign.eaintegration.enums.AttributeType;


public interface IAttribute {
	
	String getName();

	void setName(String name);
	
	String getDescription();
	
	void setDescription(String description);
	
	String getFormat();
	
	void setFormat(String format);
	
	Boolean getRequired();
	
	void setRequired(Boolean format);
	
	Boolean getReadOnly();
	
	void setReadOnly(Boolean readOnly);
	
	AttributeType getAttributeType();
	
	void setAttributeType(AttributeType attributeType);
	
	Boolean hasSubtype();
	
	String getSubtype();
	
	void setSubtype(String subtype);
	
}
