package mx.com.adesis.asodesign.eaintegration.model.api;

import java.util.List;

public interface IEnumAttribute extends IAttribute{
	
	public List<String> getEnumValues();
	
	public void setEnumValues(List<String> enumValues);
	
}
