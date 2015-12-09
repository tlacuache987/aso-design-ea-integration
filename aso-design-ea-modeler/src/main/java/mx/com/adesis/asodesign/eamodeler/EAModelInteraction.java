package mx.com.adesis.asodesign.eamodeler;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import mx.com.adesis.asodesign.eaintegration.model.api.IModel;
import mx.com.adesis.asodesign.eaintegration.model.attribute.api.IArrayAttribute;
import mx.com.adesis.asodesign.eaintegration.model.attribute.api.IAttribute;
import mx.com.adesis.asodesign.eaintegration.model.attribute.api.IEnumAttribute;
import mx.com.adesis.asodesign.eaintegration.model.attribute.api.IInterfaceAttribute;

import org.sparx.Attribute;
import org.sparx.Collection;
import org.sparx.Element;
import org.sparx.Package;
import org.sparx.Repository;

@Slf4j
public class EAModelInteraction {

	/**
	 * Crea una lista de entidades
	 * 
	 * @param eapFile
	 * @param models
	 * @param packageGUID
	 */
	public void workOnEntityList(String eapFile, List<IModel> models, String packageGUID) {
		Repository rep = null;

		log.debug("iniciando proceso de parseo de entidades ...");
		try
		{
			// Create a repository object - This will create a new instance of EA
			rep = new Repository();
			rep.OpenFile(eapFile);

			for (IModel model : models) {
				workOnNewEntity(rep, model, packageGUID);
			}

		} 
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		} 
		finally
		{
			if (rep != null)
			{
				// Clean up
				rep.CloseFile();
				rep.Exit();
				rep.destroy();
			}
		}
	}

	/**
	 * 
	 * @param rep
	 * @param model
	 * @param packageGUID
	 * @throws Exception
	 */
	private void workOnNewEntity(Repository rep, IModel model, String packageGUID) throws Exception {

		log.debug("iniciando parseo de entidad..." + model.getName());
		try
		{
			// Nos ubicamos en el paquete base
			Package thePackage = rep.GetPackageByGuid(packageGUID);

			if (thePackage != null && thePackage.GetParentID() != 0)
			{
				String modelName = model.getName();
				if(modelName == null){
					throw new RuntimeException("Nombre de la entidad no informado");
				}
				
				
				Element theElement = EAModelInteractionHelper.workOnNewElement(rep, thePackage, model.getName(),
						model.getDescription(), "Class", null);
				
				
				//Trabaja con tag values
				if(model.getSchema() != null){
						EAModelInteractionHelper.workOnElementTagValue("element-schema", model.getSchema(), theElement);
				}
				if(model.getType() != null){
						EAModelInteractionHelper.workOnElementTagValue("element-type", model.getType(), theElement);
				}
				
				List<IAttribute> attributes = model.getAttributes();
				if(attributes == null || attributes.isEmpty()){
					throw new RuntimeException("la entidad debe tener atributos");
				}
								
				//Trabaja con atributos
				for (IAttribute modelAttribute : model.getAttributes()) {
					workOnEntityAttributes(rep, modelAttribute, theElement.GetElementID(), thePackage);
				}

			}
			else {
				log.error("Paquete destino con GUID: " + packageGUID + " no se pudo encontrar");
			}

		} catch (Exception e)
		{
			log.error(e.getMessage());
			throw e;
		}

	}

	/**
	 * Crea atributos de entidades
	 * 
	 * @param rep
	 * @param modelAttribute
	 * @param elementID
	 * @param thePackage
	 * @throws Exception
	 */
	private void workOnEntityAttributes(Repository rep, IAttribute modelAttribute, int elementID, Package thePackage)
			throws Exception {

		try
		{
			Element theElement = rep.GetElementByID(elementID);

			// ==================================================
			// ADD AN ATTRIBUTE
			// ==================================================
			// Create an attribute to work on
			Collection<Attribute> attributes = theElement.GetAttributes();
			String attributeNameLowerCase = modelAttribute.getName();
			attributeNameLowerCase = attributeNameLowerCase.substring(0, 1).toLowerCase()
					+ attributeNameLowerCase.substring(1);
			Attribute newAttribute = attributes.AddNew(attributeNameLowerCase, modelAttribute.parseAsEAModelType());
			if (modelAttribute instanceof IEnumAttribute) {

				//Se crea enum
				Element theEnumElement = EAModelInteractionHelper.workOnNewElement(rep, thePackage,
						modelAttribute.getName(), modelAttribute.getDescription(), "Class", "enumeration");
				IEnumAttribute iEnum = (IEnumAttribute) modelAttribute;
				Collection<Attribute> enumAttributes = theEnumElement.GetAttributes();
				for (String enumAttribute : iEnum.getEnumValues()) {
					Attribute newEnumAttribute = enumAttributes.AddNew(enumAttribute, modelAttribute.getName());
					newEnumAttribute.Update();
					enumAttributes.Refresh();
				}

				//Se relaciona el enum con clase Padre
				EAModelInteractionHelper.workOnConnector(theEnumElement, theElement, "enum", "Composition");

			}
			if (modelAttribute instanceof IInterfaceAttribute) {
				//Se crea la interfaz
				Element theInterfaceElement = EAModelInteractionHelper.workOnNewElement(rep, thePackage,
						modelAttribute.getName(), modelAttribute.getDescription(), "Interface", null);

				//Se relaciona la interfaz con clase Padre
				EAModelInteractionHelper.workOnConnector(theInterfaceElement, theElement, "interface", "Composition");

				IInterfaceAttribute interfaceAtt = (IInterfaceAttribute) modelAttribute;
				StringBuffer resourceList = new StringBuffer("");

				//Se crea tag con las implementaciones relacionadas
				for (String resource : interfaceAtt.getResources()) {
					resourceList.append(resource).append(",");
				}
				EAModelInteractionHelper.workOnAttributeTagValue("json-param-resources", resourceList.toString(),
						newAttribute);
			}

			newAttribute.SetNotes(modelAttribute.getDescription());
			newAttribute.Update();
			attributes.Refresh();

			log.debug("Se agrega  attribute: " + newAttribute.GetName()
					+ "(Type=" + newAttribute.GetType() + ", ID="
					+ newAttribute.GetAttributeID() + ")");

			int addedAttributeID = newAttribute.GetAttributeID();

			if (modelAttribute.getRequired() != null && modelAttribute.getRequired() == true) {
				EAModelInteractionHelper.workOnAttributeTagValue("json-param-required", "true", newAttribute);
			}
			if (modelAttribute.getReadOnly() != null && modelAttribute.getReadOnly() == true) {
				EAModelInteractionHelper.workOnAttributeTagValue("json-param-readonly", "true", newAttribute);
			}
			if (modelAttribute.getFormat() != null) {
				EAModelInteractionHelper.workOnAttributeTagValue("json-param-format", modelAttribute.getFormat(),
						newAttribute);
			}
			if (modelAttribute.getPattern() != null) {
				EAModelInteractionHelper.workOnAttributeTagValue("json-param-pattern", modelAttribute.getPattern(),
						newAttribute);
			}

			//work on particular attributes tags
			if (modelAttribute instanceof IArrayAttribute) {
				IArrayAttribute iArrayAttribute = (IArrayAttribute) modelAttribute;
				if (iArrayAttribute.getMinItems() != null) {
					EAModelInteractionHelper.workOnAttributeTagValue("json-param-minitems",
							String.valueOf(iArrayAttribute.getMinItems()), newAttribute);
				}
				if (iArrayAttribute.getUniqueItems() != null) {
					EAModelInteractionHelper.workOnAttributeTagValue("json-param-uniqueitems",
							String.valueOf(iArrayAttribute.getUniqueItems()), newAttribute);
				}
			}

		} catch (Exception e)
		{
			log.error(e.getMessage());
			throw e;
		}
	}

}
