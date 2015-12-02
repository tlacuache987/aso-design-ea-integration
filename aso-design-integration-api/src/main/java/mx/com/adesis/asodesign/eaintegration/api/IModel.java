package mx.com.adesis.asodesign.eaintegration.api;

import java.util.List;

public interface IModel {
	
	String getName();

	void setName(String name);
	
	String getDescription();
	
	void setDescription(String description);
	
	List<IAttribute> getAttributes();
	
	void setAttributes(List<IAttribute> attributes);
	
}
