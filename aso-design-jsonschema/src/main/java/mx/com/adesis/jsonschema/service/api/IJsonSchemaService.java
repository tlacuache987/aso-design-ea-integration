package mx.com.adesis.jsonschema.service.api;

import java.io.InputStream;

import mx.com.adesis.asodesign.eaintegration.api.IModel;
import mx.com.adesis.jsonschema.JsonSchema;

public interface IJsonSchemaService {
	IModel getModelFormJsonSchema(InputStream jsonSchemaInputStream);

	IModel getModelFormJsonSchema(String jsonSchemaString);

	IModel getModelFormJsonSchema(JsonSchema jsonSchema);

	JsonSchema getJsonSchema(InputStream jsonSchemaInputStream);

	JsonSchema getJsonSchema(String jsonSchemaAsString);
}
