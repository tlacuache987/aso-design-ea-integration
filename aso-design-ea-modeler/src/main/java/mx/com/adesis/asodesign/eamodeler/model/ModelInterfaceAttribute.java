package mx.com.adesis.asodesign.eamodeler.model;

import java.util.List;

import lombok.Data;
import mx.com.adesis.asodesign.eaintegration.api.IInterfaceAttribute;

@Data
public class ModelInterfaceAttribute extends ModelAttribute implements IInterfaceAttribute{
	
	public List<String> resources;
	
}
