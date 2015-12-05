package mx.com.adesis.asodesign.eamodeler.model;

import lombok.Data;
import mx.com.adesis.asodesign.eaintegration.model.api.IArrayAttribute;

@Data
public class ModelArrayAttribute extends ModelAttribute implements IArrayAttribute{
	public Integer minItems;
	public Boolean uniqueItems;
}
