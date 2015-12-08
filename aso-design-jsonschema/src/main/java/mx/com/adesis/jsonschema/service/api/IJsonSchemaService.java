package mx.com.adesis.jsonschema.service.api;

import java.io.InputStream;

import mx.com.adesis.jsonschema.JsonSchema;

public interface IJsonSchemaService {
	JsonSchema getJsonSchema(InputStream jsonSchemaAsInputStream);

	JsonSchema getJsonSchema(String jsonSchemaAsString);
}
