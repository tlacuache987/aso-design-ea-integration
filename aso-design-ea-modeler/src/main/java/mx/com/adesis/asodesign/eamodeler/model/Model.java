package mx.com.adesis.asodesign.eamodeler.model;

import java.util.List;

import lombok.Data;
import mx.com.adesis.asodesign.eaintegration.api.IAttribute;
import mx.com.adesis.asodesign.eaintegration.api.IModel;

@Data
public class Model implements IModel{

	private String name;
	private List<IAttribute> attributes;
	private String description;

}
