package mx.com.adesis.asodesign.eaintegration.api;


public interface IArrayAttribute extends IAttribute {
	
	Integer getMinItems();

	void setMinItems(Integer minItems);
	
	Boolean getUniqueItems();

	void setUniqueItems(Boolean uniqueItems);
	
	
}
