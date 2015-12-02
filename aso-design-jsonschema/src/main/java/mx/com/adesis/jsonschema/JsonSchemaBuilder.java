package mx.com.adesis.jsonschema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import mjson.Json;

public class JsonSchemaBuilder {

	private Json j;
	private JsonSchema jsonSchema;

	public JsonSchema build(String jsonSchemaAsString) {

		j = Json.read(jsonSchemaAsString);

		jsonSchema = new JsonSchema();

		@SuppressWarnings("unchecked")
		HashMap<String, Object> map = (HashMap<String, Object>) j.getValue();

		processMap(map);

		return jsonSchema;
	}

	@SuppressWarnings("unchecked")
	private void processMap(HashMap<String, Object> map) {
		Set<String> keySet = map.keySet();

		for (String key : keySet) {

			final JsonSchemaValueType jsonSchemaType = getType(map.get(key));

			//System.out.println("llave: " + key + " => " + jsonSchemaType);

			switch (jsonSchemaType) {
			case STRING_VALUE:
				//System.out.print(" : " + map.get(key));

				if (key.equalsIgnoreCase("$schema")) {
					jsonSchema.setSchema(new JsonSchemaKeyValuePair<String>());
					jsonSchema.getSchema().setKey("$schema");
					jsonSchema.getSchema().setValue((String) map.get(key));
					continue;
				}

				if (key.equalsIgnoreCase("type")) {
					jsonSchema.setType(new JsonSchemaKeyValuePair<String>());
					jsonSchema.getType().setKey("type");
					jsonSchema.getType().setValue((String) map.get(key));
					continue;
				}

				if (key.equalsIgnoreCase("id")) {
					jsonSchema.setId(new JsonSchemaKeyValuePair<String>());
					jsonSchema.getId().setKey("id");
					jsonSchema.getId().setValue((String) map.get(key));
					continue;
				}

				if (key.equalsIgnoreCase("description")) {
					jsonSchema.setDescription(new JsonSchemaKeyValuePair<String>());
					jsonSchema.getDescription().setKey("description");
					jsonSchema.getDescription().setValue((String) map.get(key));
					continue;
				}

				break;
			case OBJECT_VALUE:
				//System.out.println(" : ");

				if (key.equalsIgnoreCase("properties")) {
					jsonSchema.setProperties(new JsonSchemaKeyValuePair<JsonSchemaProperties>());
					jsonSchema.getProperties().setKey("properties");

					final JsonSchemaProperties jsonSchemaProperties = processProperties((HashMap<String, Object>) map
							.get(key));
					jsonSchema.getProperties().setValue(jsonSchemaProperties);
					continue;
				}
				break;
			}

		}
	}

	private JsonSchemaProperties processProperties(HashMap<String, Object> map) {

		final JsonSchemaProperties jsonProperties = new JsonSchemaProperties();

		Set<String> keySet = map.keySet();

		for (String key : keySet) {

			final JsonSchemaValueType jsonSchemaType = getType(map.get(key));

			final JsonSchemaProperty jsonProperty = new JsonSchemaProperty(key);

			//System.out.println("llave: " + key + " => " + jsonSchemaType);

			switch (jsonSchemaType) {
			case STRING_VALUE:
				break;
			case OBJECT_VALUE:

				@SuppressWarnings("unchecked")
				HashMap<String, Object> propertyMap = (HashMap<String, Object>) map.get(key);

				if (propertyMap.containsKey("description")) {
					jsonProperty.getDefinition().setDescription(
							new JsonSchemaKeyValuePair<String>());
					jsonProperty.getDefinition().getDescription().setKey("description");
					jsonProperty.getDefinition().getDescription()
							.setValue((String) propertyMap.get("description"));
				}

				if (propertyMap.containsKey("type")) {
					jsonProperty.getDefinition().setType(
							new JsonSchemaKeyValuePair<JsonSchemaPropertyType>());
					jsonProperty.getDefinition().getType().setKey("type");
					jsonProperty.getDefinition().getType()
							.setValue(obtainPropertyType((String) propertyMap.get("type")));
				}

				if (propertyMap.containsKey("$ref")) {
					jsonProperty.getDefinition().setRef(new JsonSchemaKeyValuePair<String>());
					jsonProperty.getDefinition().getRef().setKey("$ref");
					jsonProperty.getDefinition().getRef()
							.setValue((String) propertyMap.get("$ref"));
				}

				if (propertyMap.containsKey("enum")) {
					jsonProperty.getDefinition().setEnumType(
							new JsonSchemaKeyValuePair<List<String>>());
					jsonProperty.getDefinition().getEnumType().setKey("enum");

					@SuppressWarnings("unchecked")
					List<String> enumList = (ArrayList<String>) propertyMap.get("enum");

					jsonProperty.getDefinition().getEnumType()
							.setValue(enumList);
				}

				if (propertyMap.containsKey("readonly")) {
					jsonProperty.getDefinition().setReadonly(new JsonSchemaKeyValuePair<Boolean>());
					jsonProperty.getDefinition().getReadonly().setKey("readonly");
					jsonProperty.getDefinition().getReadonly()
							.setValue((Boolean) propertyMap.get("readonly"));
				}

				if (propertyMap.containsKey("required")) {
					jsonProperty.getDefinition().setRequired(new JsonSchemaKeyValuePair<Boolean>());
					jsonProperty.getDefinition().getRequired().setKey("required");
					jsonProperty.getDefinition().getRequired()
							.setValue((Boolean) propertyMap.get("required"));
				}

				if (propertyMap.containsKey("format")) {
					jsonProperty.getDefinition().setFormat(
							new JsonSchemaKeyValuePair<String>());
					jsonProperty.getDefinition().getFormat().setKey("format");
					jsonProperty.getDefinition().getFormat()
							.setValue((String) propertyMap.get("format"));
				}

				if (propertyMap.containsKey("items")) {
					jsonProperty.getDefinition().setItems(
							new JsonSchemaKeyValuePair<JsonSchemaItems>());
					jsonProperty.getDefinition().getItems().setKey("items");

					@SuppressWarnings("unchecked")
					final JsonSchemaItems jsonSchemaItems = processItems((HashMap<String, Object>) propertyMap
							.get("items"));

					jsonProperty.getDefinition().getItems()
							.setValue(jsonSchemaItems);
				}

				break;
			}

			jsonProperties.getProperties().add(jsonProperty);
		}

		System.out.println(map);

		return jsonProperties;
	}

	private JsonSchemaItems processItems(HashMap<String, Object> map) {
		final JsonSchemaItems jsonItems = new JsonSchemaItems();

		Set<String> keySet = map.keySet();

		for (String key : keySet) {

			final JsonSchemaValueType jsonSchemaType = getType(map.get(key));

			final JsonSchemaItem jsonItem = new JsonSchemaItem(key);

			System.out.println("llave: " + key + " => " + jsonSchemaType);

			switch (jsonSchemaType) {
			case STRING_VALUE:

				if (key.equalsIgnoreCase("type")) {
					jsonItem.getDefinition().setType(
							new JsonSchemaKeyValuePair<JsonSchemaPropertyType>());
					jsonItem.getDefinition().getType().setKey("type");

					JsonSchemaPropertyType typeValue = obtainPropertyType((String) map.get("type"));

					System.out.println("typevalue: " + typeValue);

					jsonItem.getDefinition().getType()
							.setValue(typeValue);
					continue;
				}

				break;
			case OBJECT_VALUE:
				break;
			}

			jsonItems.getItems().add(jsonItem);
		}

		return jsonItems;
	}

	private JsonSchemaPropertyType obtainPropertyType(String string) {
		System.out.println("STRING: " + string);
		if (string != null && string.equalsIgnoreCase("object"))
			return JsonSchemaPropertyType.OBJECT;

		if (string != null && string.equalsIgnoreCase("string"))
			return JsonSchemaPropertyType.STRING;

		return null;
	}

	private static JsonSchemaValueType getType(Object object) {
		if (object instanceof String)
			return JsonSchemaValueType.STRING_VALUE;
		else
			return JsonSchemaValueType.OBJECT_VALUE;
	}

}
