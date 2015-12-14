package mx.com.adesis.asodesign.eaintegration.model.attribute.api.impl;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import mx.com.adesis.asodesign.eaintegration.model.attribute.api.IInterfaceAttribute;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ModelInterfaceAttribute extends ModelAttribute implements IInterfaceAttribute {
	public List<String> resources;

	public String parseAsEAModelType() {
		return this.getSubtype();
	}
}
