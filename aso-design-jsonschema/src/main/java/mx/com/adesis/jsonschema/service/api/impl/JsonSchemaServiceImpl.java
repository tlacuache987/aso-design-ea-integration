package mx.com.adesis.jsonschema.service.api.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import mx.com.adesis.jsonschema.JsonSchema;
import mx.com.adesis.jsonschema.JsonSchemaBuilder;
import mx.com.adesis.jsonschema.service.api.IJsonSchemaToModelConverter;
import mx.com.adesis.jsonschema.service.api.IJsonSchemaService;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class JsonSchemaServiceImpl implements IJsonSchemaService {

	@Autowired
	private IJsonSchemaToModelConverter jsonSchemaConverter;

	@Override
	public JsonSchema getJsonSchema(InputStream jsonSchemaInputStream) {
		return this.getJsonSchema(this.getJsonSchemaFromInputStream(jsonSchemaInputStream));
	}

	@Override
	public JsonSchema getJsonSchema(String jsonSchemaAsString) {
		System.out.println("JsonSchemaServiceImpl: " + this.toString());

		final JsonSchemaBuilder jsonSchemaBuilder = getJsonSchemaBuilder();

		System.out.println("jsonSchemaBuilder: " + jsonSchemaBuilder.toString());

		return jsonSchemaBuilder.build(jsonSchemaAsString);
	}

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

	public abstract JsonSchemaBuilder getJsonSchemaBuilder();

}
