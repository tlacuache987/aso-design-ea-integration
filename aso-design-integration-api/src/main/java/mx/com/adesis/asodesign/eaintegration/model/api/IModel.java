package mx.com.adesis.asodesign.eaintegration.model.api;

import java.util.List;

import mx.com.adesis.asodesign.eaintegration.model.attribute.api.IAttribute;

public interface IModel {

	String getName();

	void setName(String name);

	String getDescription();

	void setDescription(String description);

	List<IAttribute> getAttributes();

	void setAttributes(List<IAttribute> attributes);

	String getSchema();

	void setSchema(String schema);

	String getType();

	void setType(String type);
	
	String getStereotype();
	
	void setStereotype(String stereotype);
}
