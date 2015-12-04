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
		final HashMap<String, Object> map = (HashMap<String, Object>) j.getValue();

		this.processMap(map);

		return jsonSchema;
	}

	@SuppressWarnings("unchecked")
	private void processMap(HashMap<String, Object> map) {
		final Set<String> keySet = map.keySet();

		for (String key : keySet) {

			final JsonSchemaValueType jsonSchemaType = getType(map.get(key));

			//System.out.println("llave: " + key + " => " + jsonSchemaType);

			switch (jsonSchemaType) {
			case STRING_VALUE:

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

		final Set<String> keySet = map.keySet();

		for (String key : keySet) {

			final JsonSchemaValueType jsonSchemaType = getType(map.get(key));

			final JsonSchemaProperty jsonProperty = new JsonSchemaProperty(key);

			System.out.println("llave 1: " + key + " => " + jsonSchemaType);

			switch (jsonSchemaType) {
			case STRING_VALUE:
				break;
			case OBJECT_VALUE:

				@SuppressWarnings("unchecked")
				final HashMap<String, Object> propertyMap = (HashMap<String, Object>) map.get(key);

				System.out.println(propertyMap);

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
					final List<String> enumList = (ArrayList<String>) propertyMap.get("enum");

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
							new JsonSchemaKeyValuePair<List<JsonSchemaItemPropertyDefinition>>());
					jsonProperty.getDefinition().getItems().setKey("items");

					@SuppressWarnings("unchecked")
					final List<JsonSchemaItemPropertyDefinition> jsonSchemaItemPropertyDefinitionList = processItems2((HashMap<String, Object>) propertyMap
							.get("items"));

					jsonProperty.getDefinition().getItems().setValue(jsonSchemaItemPropertyDefinitionList);

					/*jsonProperty.getDefinition().setItems(new JsonSchemaKeyValuePair<List<JsonSchemaItem_>>());
					jsonProperty.getDefinition().getItems().setKey("items");

					@SuppressWarnings("unchecked")
					final List<JsonSchemaItem_> jsonSchemaListItems = processItems((HashMap<String, Object>) propertyMap
							.get("items"));

					jsonProperty.getDefinition().getItems()
							.setValue(jsonSchemaListItems);*/
				}

				if (propertyMap.containsKey("minItems")) {
					jsonProperty.getDefinition().setMinItems(
							new JsonSchemaKeyValuePair<Integer>());
					jsonProperty.getDefinition().getMinItems().setKey("minItems");

					jsonProperty.getDefinition().getMinItems().setValue(
							Integer.valueOf(String.valueOf((Long) propertyMap
									.get("minItems"))));
				}

				if (propertyMap.containsKey("uniqueItems")) {
					jsonProperty.getDefinition().setUniqueItems(
							new JsonSchemaKeyValuePair<Boolean>());
					jsonProperty.getDefinition().getUniqueItems().setKey("uniqueItems");

					jsonProperty.getDefinition().getUniqueItems()
							.setValue((Boolean) propertyMap.get("uniqueItems"));
				}

				if (propertyMap.containsKey("oneOf")) {
					jsonProperty.getDefinition().setOneOf(
							new JsonSchemaKeyValuePair<List<JsonSchemaOneOfPropertyDefinition>>());

					jsonProperty.getDefinition().getOneOf().setKey("oneOf");

					@SuppressWarnings("unchecked")
					final List<JsonSchemaOneOfPropertyDefinition> listOneOf = processOneOf((List<HashMap<String, Object>>) propertyMap
							.get("oneOf"));

					jsonProperty.getDefinition().getOneOf().setValue(listOneOf);

				}

				break;
			}

			jsonProperties.getProperties().add(jsonProperty);
		}

		return jsonProperties;
	}

	private List<JsonSchemaOneOfPropertyDefinition> processOneOf(List<HashMap<String, Object>> listObjectMap) {
		System.out.println("one of:");

		final List<JsonSchemaOneOfPropertyDefinition> listOneOf = new ArrayList<JsonSchemaOneOfPropertyDefinition>();

		System.out.println("listObjectMap: " + listObjectMap);

		for (HashMap<String, Object> map : listObjectMap) {

			final Set<String> keySet = map.keySet();

			for (String key : keySet) {

				final JsonSchemaValueType jsonSchemaType = getType(map.get(key));

				System.out.println("llave: " + key + " => " + jsonSchemaType);

				switch (jsonSchemaType) {
				case STRING_VALUE:

					JsonSchemaOneOfPropertyDefinition jsonSchemaOneOfPropertyDefinition = null;

					if (key.equalsIgnoreCase("$ref")) {
						jsonSchemaOneOfPropertyDefinition = new JsonSchemaOneOfPropertyDefinition();
						jsonSchemaOneOfPropertyDefinition.setRef(new JsonSchemaKeyValuePair<String>());
						jsonSchemaOneOfPropertyDefinition.getRef().setKey("$ref");
						jsonSchemaOneOfPropertyDefinition.getRef().setValue((String) map.get(key));
					}

					if (key.equalsIgnoreCase("format")) {
						jsonSchemaOneOfPropertyDefinition = new JsonSchemaOneOfPropertyDefinition();
						jsonSchemaOneOfPropertyDefinition.setFormat(new JsonSchemaKeyValuePair<String>());
						jsonSchemaOneOfPropertyDefinition.getFormat().setKey("format");
						jsonSchemaOneOfPropertyDefinition.getFormat().setValue((String) map.get(key));
					}

					if (jsonSchemaOneOfPropertyDefinition != null)
						listOneOf.add(jsonSchemaOneOfPropertyDefinition);

					break;
				case OBJECT_VALUE:
					break;
				}

			}

		}

		return listOneOf;
	}

	private List<JsonSchemaItem_> processItems(HashMap<String, Object> map) {

		final List<JsonSchemaItem_> jsonSchemaListItems = new ArrayList<JsonSchemaItem_>();

		final Set<String> keySet = map.keySet();

		for (String key : keySet) {

			final JsonSchemaValueType jsonSchemaType = getType(map.get(key));

			final JsonSchemaItem_ jsonItem = new JsonSchemaItem_(key);

			//System.out.println("llaveS: " + key + " => " + jsonSchemaType);

			switch (jsonSchemaType) {
			case STRING_VALUE:

				if (key.equalsIgnoreCase("type")) {
					jsonItem.getDefinition().setType(
							new JsonSchemaKeyValuePair<JsonSchemaPropertyType>());
					jsonItem.getDefinition().getType().setKey("type");

					JsonSchemaPropertyType typeValue = obtainPropertyType((String) map.get("type"));

					jsonItem.getDefinition().getType()
							.setValue(typeValue);
				}

				if (key.equalsIgnoreCase("description")) {
					jsonItem.getDefinition().setDescription(
							new JsonSchemaKeyValuePair<String>());
					jsonItem.getDefinition().getDescription().setKey("description");
					jsonItem.getDefinition().getDescription()
							.setValue((String) map.get("description"));
				}

				break;
			case OBJECT_VALUE:
				break;
			}

			jsonSchemaListItems.add(jsonItem);
		}

		return jsonSchemaListItems;
	}

	private List<JsonSchemaItemPropertyDefinition> processItems2(HashMap<String, Object> map) {

		final List<JsonSchemaItemPropertyDefinition> jsonSchemaListItems = new ArrayList<JsonSchemaItemPropertyDefinition>();

		final Set<String> keySet = map.keySet();

		for (String key : keySet) {

			final JsonSchemaValueType jsonSchemaType = getType(map.get(key));

			//System.out.println("llaveS: " + key + " => " + jsonSchemaType);

			JsonSchemaItemPropertyDefinition jsonSchemaItemPropertyDefinition = null;

			switch (jsonSchemaType) {
			case STRING_VALUE:

				if (key.equalsIgnoreCase("type")) {
					jsonSchemaItemPropertyDefinition = new JsonSchemaItemPropertyDefinition();

					jsonSchemaItemPropertyDefinition.setType(new JsonSchemaKeyValuePair<JsonSchemaPropertyType>());
					jsonSchemaItemPropertyDefinition.getType().setKey("type");

					JsonSchemaPropertyType typeValue = obtainPropertyType((String) map.get("type"));

					jsonSchemaItemPropertyDefinition.getType().setValue(typeValue);
					/*jsonItem.getDefinition().setType(
							new JsonSchemaKeyValuePair<JsonSchemaPropertyType>());
					jsonItem.getDefinition().getType().setKey("type");

					JsonSchemaPropertyType typeValue = obtainPropertyType((String) map.get("type"));

					jsonItem.getDefinition().getType()
							.setValue(typeValue);*/
				}

				if (key.equalsIgnoreCase("description")) {
					jsonSchemaItemPropertyDefinition = new JsonSchemaItemPropertyDefinition();

					jsonSchemaItemPropertyDefinition.setDescription(new JsonSchemaKeyValuePair<String>());
					jsonSchemaItemPropertyDefinition.getDescription().setKey("description");
					jsonSchemaItemPropertyDefinition.getDescription().setValue((String) map.get("description"));
					/*jsonItem.getDefinition().setDescription(
							new JsonSchemaKeyValuePair<String>());
					jsonItem.getDefinition().getDescription().setKey("description");
					jsonItem.getDefinition().getDescription()
							.setValue((String) map.get("description"));*/
				}

				break;
			case OBJECT_VALUE:
				break;
			}

			if (jsonSchemaItemPropertyDefinition != null)
				jsonSchemaListItems.add(jsonSchemaItemPropertyDefinition);
		}

		return jsonSchemaListItems;
	}

	private static JsonSchemaPropertyType obtainPropertyType(String string) {
		if (string != null && string.equalsIgnoreCase("object"))
			return JsonSchemaPropertyType.OBJECT;

		if (string != null && string.equalsIgnoreCase("string"))
			return JsonSchemaPropertyType.STRING;

		if (string != null && string.equalsIgnoreCase("null"))
			return JsonSchemaPropertyType.NULL;

		if (string != null && string.equalsIgnoreCase("number"))
			return JsonSchemaPropertyType.NUMBER;

		if (string != null && string.equalsIgnoreCase("integer"))
			return JsonSchemaPropertyType.INTEGER;

		if (string != null && string.equalsIgnoreCase("boolean"))
			return JsonSchemaPropertyType.BOOLEAN;

		if (string != null && string.equalsIgnoreCase("array"))
			return JsonSchemaPropertyType.ARRAY;

		return null;
	}

	private static JsonSchemaValueType getType(Object object) {
		if (object instanceof String)
			return JsonSchemaValueType.STRING_VALUE;
		else
			return JsonSchemaValueType.OBJECT_VALUE;
	}

}
