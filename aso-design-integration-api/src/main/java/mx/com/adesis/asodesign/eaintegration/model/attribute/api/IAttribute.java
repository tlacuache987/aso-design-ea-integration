package mx.com.adesis.asodesign.eaintegration.model.attribute.api;


public interface IAttribute {

	String getName();

	void setName(String name);

	String getDescription();

	void setDescription(String description);

	String getFormat();

	void setFormat(String format);

	Boolean getRequired();

	void setRequired(Boolean format);

	Boolean getReadOnly();

	void setReadOnly(Boolean readOnly);

	Boolean hasSubtype();

	String getSubtype();

	void setSubtype(String subtype);

	String getPattern();

	void setPattern(String pattern);

	String parseAsEAModelType();

}
