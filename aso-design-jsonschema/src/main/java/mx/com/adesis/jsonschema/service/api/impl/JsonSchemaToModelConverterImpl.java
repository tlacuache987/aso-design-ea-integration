package mx.com.adesis.jsonschema.service.api.impl;

import java.util.ArrayList;
import java.util.List;

import mx.com.adesis.asodesign.eaintegration.model.api.IModel;
import mx.com.adesis.asodesign.eaintegration.model.api.impl.Model;
import mx.com.adesis.asodesign.eaintegration.model.attribute.api.IAttribute;
import mx.com.adesis.asodesign.eaintegration.model.attribute.api.impl.ModelArrayAttribute;
import mx.com.adesis.asodesign.eaintegration.model.attribute.api.impl.ModelEnumAttribute;
import mx.com.adesis.jsonschema.JsonSchema;
import mx.com.adesis.jsonschema.JsonSchemaItemPropertyDefinition;
import mx.com.adesis.jsonschema.JsonSchemaOneOfPropertyDefinition;
import mx.com.adesis.jsonschema.JsonSchemaProperty;
import mx.com.adesis.jsonschema.JsonSchemaPropertyDefinition;
import mx.com.adesis.jsonschema.JsonSchemaPropertyType;
import mx.com.adesis.jsonschema.service.api.IJsonSchemaToModelConverter;
import mx.com.adesis.jsonschema.util.StringUtils;

import org.springframework.stereotype.Component;

@Component
public class JsonSchemaToModelConverterImpl implements IJsonSchemaToModelConverter {

	private enum ModelAtributeType {
		IS_ENUM, IS_OBJECT, IS_ARRAY, IS_INTERFACE
	}

	@Override
	public IModel convert(JsonSchema jsonSchema) {

		final Model model = new Model();

		if (jsonSchema.hasId()) {
			model.setName(jsonSchema.getId().getValue());
		}
		if (jsonSchema.hasDescription()) {
			model.setDescription(jsonSchema.getDescription().getValue());
		}
		if (jsonSchema.hasSchema()) {
			model.setSchema(jsonSchema.getSchema().getValue());
		}
		if (jsonSchema.hasType()) {
			model.setType(jsonSchema.getType().getValue());
		}

		final List<IAttribute> attributeList = new ArrayList<IAttribute>();

		if (jsonSchema.hasProperties()) {

			List<JsonSchemaProperty> propertyList = jsonSchema.getProperties().getValue().getList();

			for (JsonSchemaProperty jsp : propertyList) {

				IAttribute attribute = null;

				switch (calculateAttributeType(jsp)) {

				case IS_ENUM:
					attribute = processEnum(jsp);
					break;
				case IS_ARRAY:
					attribute = processArray(jsp);
					break;
				case IS_INTERFACE:
					break;
				case IS_OBJECT:
					break;

				default:
					attribute = null;
					break;
				}

				if (attribute != null)
					attributeList.add(attribute);
			}

		}

		model.setAttributes(attributeList);

		return model;
	}

	private IAttribute processArray(JsonSchemaProperty jsp) {

		final ModelArrayAttribute attribute = new ModelArrayAttribute();

		final JsonSchemaPropertyDefinition definition = jsp.getDefinition();

		if (jsp.getName() != null && jsp.getName().length() > 0) {
			attribute.setName(jsp.getName());
			//attribute.setSubtype(StringUtils.toUpperCamelCase(jsp.getName()));
		}

		if (definition.hasItems()) {
			JsonSchemaItemPropertyDefinition jsonSchemaItemPropertyDefinition = definition.getItems().getValue();

			System.out.println("jsonSchemaItemPropertyDefinition: " + jsonSchemaItemPropertyDefinition);
			JsonSchemaPropertyType itemPropertytype = jsonSchemaItemPropertyDefinition.getType().getValue();
		}

		if (definition.hasDescription())
			attribute.setDescription(definition.getDescription().getValue());

		if (definition.hasFormat())
			attribute.setFormat(definition.getFormat().getValue());

		if (definition.isReadonly())
			attribute.setReadOnly(definition.getReadonly().getValue());

		if (definition.isRequired())
			attribute.setRequired(definition.getRequired().getValue());

		return attribute;
	}

	private IAttribute processEnum(JsonSchemaProperty jsp) {

		final ModelEnumAttribute attribute = new ModelEnumAttribute();

		final JsonSchemaPropertyDefinition definition = jsp.getDefinition();

		if (jsp.getName() != null && jsp.getName().length() > 0) {
			attribute.setName(jsp.getName());
			attribute.setSubtype(StringUtils.toUpperCamelCase(jsp.getName()));
		}

		if (definition.hasDescription())
			attribute.setDescription(definition.getDescription().getValue());

		if (definition.isEnumType()) {
			final List<String> enumValues = new ArrayList<String>();
			for (String value : definition.getEnumType().getValue()) {
				enumValues.add(value);
			}
			attribute.setEnumValues(enumValues);
		}

		if (definition.hasFormat())
			attribute.setFormat(definition.getFormat().getValue());

		if (definition.isReadonly())
			attribute.setReadOnly(definition.getReadonly().getValue());

		if (definition.isRequired())
			attribute.setRequired(definition.getRequired().getValue());

		//Falta set pattern

		return attribute;
	}

	private ModelAtributeType calculateAttributeType(JsonSchemaProperty jsp) {
		final JsonSchemaPropertyDefinition def = jsp.getDefinition();

		/*if (def.isEnumType())
			return ModelAtributeType.IS_ENUM;*/

		if (def.hasType()) {
			switch (def.getType().getValue()) {
			case ARRAY:
				return ModelAtributeType.IS_ARRAY;
			case OBJECT:
				if (def.isOneOf()) {
					JsonSchemaOneOfPropertyDefinition oneOfPropDef = def.getOneOf().getValue().get(0);
					if (oneOfPropDef.hasRef())
						return ModelAtributeType.IS_INTERFACE;
				}
				return ModelAtributeType.IS_OBJECT;
			case BOOLEAN:
			case INTEGER:
			case NUMBER:
			case STRING:
				return ModelAtributeType.IS_OBJECT;
			default:
				return null;
			}

		} else {
			if (def.isEnumType())
				return ModelAtributeType.IS_ENUM;

			if (def.hasItems())
				return ModelAtributeType.IS_ARRAY;

			if (def.hasRef())
				return ModelAtributeType.IS_OBJECT;

			if (def.isOneOf()) {
				JsonSchemaOneOfPropertyDefinition oneOfPropDef = def.getOneOf().getValue().get(0);
				if (oneOfPropDef.hasRef())
					return ModelAtributeType.IS_INTERFACE;
				return ModelAtributeType.IS_OBJECT;
			}
			return ModelAtributeType.IS_OBJECT;
		}
	}
}
