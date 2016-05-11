package mx.com.adesis.asodesign.eamodeler.modeltospreadsheet;

import lombok.Data;
import lombok.ToString;
import mx.com.adesis.asodesign.eaintegration.model.api.IModel;
import mx.com.adesis.asodesign.eaintegration.model.attribute.api.impl.ModelObjectAttribute;

@Data
@ToString(callSuper = true)
public class SpreadSheetAttribute extends ModelObjectAttribute{
	
	private boolean hasChildAttributes;
	private IModel childModel;
	private String stereotype;
	private boolean hasChildAttributesList;
	
}
