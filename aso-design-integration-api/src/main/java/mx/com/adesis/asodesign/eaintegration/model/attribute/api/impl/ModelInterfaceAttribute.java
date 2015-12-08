package mx.com.adesis.asodesign.eaintegration.model.attribute.api.impl;

import java.util.List;

import lombok.Data;
import mx.com.adesis.asodesign.eaintegration.model.attribute.api.IInterfaceAttribute;

@Data
public class ModelInterfaceAttribute extends ModelAttribute implements IInterfaceAttribute {
	public List<String> resources;

	public String parseAsEAModelType() {
		return this.getSubtype();
	}
}
