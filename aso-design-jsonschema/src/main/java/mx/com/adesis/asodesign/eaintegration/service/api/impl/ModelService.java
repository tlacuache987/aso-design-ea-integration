package mx.com.adesis.asodesign.eaintegration.service.api.impl;

import java.io.InputStream;

import mx.com.adesis.asodesign.eaintegration.model.api.IModel;
import mx.com.adesis.asodesign.eaintegration.service.api.IModelService;
import mx.com.adesis.jsonschema.JsonSchema;
import mx.com.adesis.jsonschema.service.api.IJsonSchemaConverter;
import mx.com.adesis.jsonschema.service.api.IJsonSchemaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ModelService implements IModelService {

	@Autowired
	private IJsonSchemaService jsonSchemaService;

	@Autowired
	private IJsonSchemaConverter jsonSchemaConverter;

	@Override
	public IModel getModel(String jsonSchemaAsString) {
		final JsonSchema jsonSchema = jsonSchemaService.getJsonSchema(jsonSchemaAsString);

		return jsonSchemaConverter.convert(jsonSchema);
	}

	@Override
	public IModel getModel(InputStream jsonSchemaAsInputStream) {
		final JsonSchema jsonSchema = jsonSchemaService.getJsonSchema(jsonSchemaAsInputStream);

		return jsonSchemaConverter.convert(jsonSchema);
	}

}
