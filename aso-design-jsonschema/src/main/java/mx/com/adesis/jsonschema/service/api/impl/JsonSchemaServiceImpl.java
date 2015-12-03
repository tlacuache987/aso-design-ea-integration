package mx.com.adesis.jsonschema.service.api.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import mx.com.adesis.asodesign.eaintegration.api.IModel;
import mx.com.adesis.jsonschema.model.JsonSchema;
import mx.com.adesis.jsonschema.model.JsonSchemaBuilder;
import mx.com.adesis.jsonschema.service.api.IJsonSchemaService;

public abstract class JsonSchemaServiceImpl implements IJsonSchemaService {

	@Override
	public IModel getModelFormJsonSchema(InputStream jsonSchemaInputStream) {
		return this.getModelFormJsonSchema(getJsonSchemaFromInputStream(jsonSchemaInputStream));
	}

	@Override
	public IModel getModelFormJsonSchema(String jsonSchemaString) {
		System.out.println("JsonSchemaServiceImpl: " + this.toString());

		final JsonSchemaBuilder jsonSchemaBuilder = getJsonSchemaBuilder();

		System.out.println("jsonSchemaBuilder: " + jsonSchemaBuilder.toString());

		final JsonSchema jsonSchema = jsonSchemaBuilder.build(jsonSchemaString);

		System.out.println(jsonSchema);

		return null;
	}

	public abstract JsonSchemaBuilder getJsonSchemaBuilder();

	private String getJsonSchemaFromInputStream(InputStream jsonSchemaInputStream) {
		StringBuilder sb = new StringBuilder();

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					jsonSchemaInputStream));

			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			br.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

}
