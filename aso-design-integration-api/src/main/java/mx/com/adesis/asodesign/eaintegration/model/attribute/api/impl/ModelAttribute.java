package mx.com.adesis.asodesign.eaintegration.model.attribute.api.impl;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import mx.com.adesis.asodesign.eaintegration.model.attribute.api.IAttribute;

@Data
@EqualsAndHashCode
@ToString(callSuper = true)
public abstract class ModelAttribute implements IAttribute {
	private String name;
	private String format;
	private String description;
	private Boolean required;
	private Boolean readOnly;
	private String subtype;
	private String pattern;

	public Boolean hasSubtype() {
		Boolean returnHasSubtype = false;
		if (subtype != null && !subtype.isEmpty()) {
			returnHasSubtype = true;
		}
		return returnHasSubtype;
	}
}
