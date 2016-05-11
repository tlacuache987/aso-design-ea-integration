package mx.com.adesis.asodesign.eaintegration.model.api.impl;

import java.util.List;

import lombok.Data;
import mx.com.adesis.asodesign.eaintegration.model.api.IModel;
import mx.com.adesis.asodesign.eaintegration.model.attribute.api.IAttribute;

@Data
public class Model implements IModel {
	private String name;
	private List<IAttribute> attributes;
	private String description;
	private String schema;
	private String type;
	private String stereotype;
}
