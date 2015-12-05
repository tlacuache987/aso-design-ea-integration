package mx.com.adesis.asodesign.eaintegration.service.api;

import java.io.InputStream;

import mx.com.adesis.asodesign.eaintegration.model.api.IModel;

public interface IModelService {
	IModel getModel(String jsonSchemaAsString);

	IModel getModel(InputStream jsonSchemaAsInputStream);
}
