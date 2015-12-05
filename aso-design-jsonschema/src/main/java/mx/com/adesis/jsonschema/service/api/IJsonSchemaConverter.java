package mx.com.adesis.jsonschema.service.api;

import mx.com.adesis.asodesign.eaintegration.model.api.IModel;
import mx.com.adesis.jsonschema.JsonSchema;

public interface IJsonSchemaConverter {
	IModel convert(JsonSchema jsonSchema);
}
