package mx.com.adesis.jsonschema.service.api;

import java.io.InputStream;

import mx.com.adesis.asodesign.eaintegration.api.IModel;

public interface IJsonSchemaService {
	IModel getModelFormJsonSchema(InputStream jsonSchemaInputStream);

	IModel getModelFormJsonSchema(String jsonSchemaString);
}
