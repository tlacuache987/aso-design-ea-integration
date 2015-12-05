package mx.com.adesis.asodesign.eaintegration.model.api;


public interface IArrayAttribute extends IAttribute {
	
	Integer getMinItems();

	void setMinItems(Integer minItems);
	
	Boolean getUniqueItems();

	void setUniqueItems(Boolean uniqueItems);
	
	
}
