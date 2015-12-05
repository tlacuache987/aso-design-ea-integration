package mx.com.adesis.asodesign.eaintegration.model.api;

import java.util.List;

public interface IInterfaceAttribute extends IAttribute{
	
	List<String> getResources();
	
	void setResources(List<String> resources);
	
}
