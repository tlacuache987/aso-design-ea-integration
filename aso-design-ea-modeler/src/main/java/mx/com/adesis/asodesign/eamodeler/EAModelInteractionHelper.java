package mx.com.adesis.asodesign.eamodeler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;
import mx.com.adesis.asodesign.eaintegration.model.api.IModel;
import mx.com.adesis.asodesign.eaintegration.model.api.impl.Model;
import mx.com.adesis.asodesign.eaintegration.model.attribute.api.IAttribute;
import mx.com.adesis.asodesign.eaintegration.model.attribute.api.impl.ModelObjectAttribute;

import org.sparx.Attribute;
import org.sparx.AttributeTag;
import org.sparx.Collection;
import org.sparx.Connector;
import org.sparx.ConnectorEnd;
import org.sparx.Element;
import org.sparx.Package;
import org.sparx.Repository;
import org.sparx.TaggedValue;

@Slf4j
public final class EAModelInteractionHelper {
	
	/**
	 * Crea una nueva entidad en EA
	 * @param rep
	 * @param thePackage
	 * @param name
	 * @param description
	 * @param type
	 * @param stereotype
	 * @return
	 * @throws Exception
	 */
	public static Element workOnNewElement(Repository rep, Package thePackage, String name, String description, String type, String stereotype) throws Exception {
		Collection<Element> elements = thePackage.GetElements();
		Element theElement = elements.AddNew(name, type);
		if(description != null){
			theElement.SetNotes(description);
		}
		if(stereotype != null){
			theElement.SetStereotype(stereotype);
		}
		theElement.Update();
		
		log.debug( "Trabajando en el elemento " + theElement.GetName() 
				+ "' (Type=" + theElement.GetType() + " ID=" 
				+ theElement.GetElementID() + ")" );
		
		return theElement;
	}
	
	
	/**
	 * Crea un TagValue de un atributo
	 * @param tagName
	 * @param tagValue
	 * @param attribute
	 */
	public static void workOnAttributeTagValue(String tagName, String tagValue, Attribute attribute){
		// ==================================================
		// MANAGE ATTRIBUTE TAGGED VALUES
		// ==================================================
		// Add an attribute tag
		Collection<AttributeTag> tags = attribute.GetTaggedValues();
		//String tagName = "json-param-required";
		AttributeTag newTag 
				= tags.AddNew( tagName, tagValue );
		newTag.Update();
		tags.Refresh();
		
		log.debug( "Se agrega tag: " + newTag.GetName() 
				+ " (ID=" + newTag.GetTagID() + ")" );
		int newTagID = newTag.GetTagID();
				
	}
	
	/**
	 * Crea un TagValue de un Elemento
	 * @param tagName
	 * @param tagValue
	 * @param attribute
	 */
	public static void workOnElementTagValue(String tagName, String tagValue, Element element){
		// ==================================================
		// MANAGE ATTRIBUTE TAGGED VALUES
		// ==================================================
		// Add an attribute tag
		Collection<TaggedValue> tags = element.GetTaggedValues();
		//String tagName = "json-param-required";
		TaggedValue newTag 
				= tags.AddNew( tagName, tagValue );
		newTag.Update();
		tags.Refresh();
		
		log.debug( "Se agrega tag: " + newTag.GetName() 
				+ " (ID=" + newTag.GetElementID() + ")" );
	}
	
	/**
	 * Conecta dos entidades
	 * @param sourceElement
	 * @param targetElement
	 * @param connName
	 * @param connStereotype
	 */
	public static void workOnConnector(Element sourceElement, Element targetElement, String connName, String connStereotype){
		// ==================================================
		// CREATE CONNECTOR
		// ==================================================
		// Create the connector, set its endpoint and save it
		Connector theConnector = sourceElement.GetConnectors().AddNew( 
				connName, connStereotype );
		theConnector.SetSupplierID( targetElement.GetElementID() );
		theConnector.Update();
		
		// Refresh the connectors collection to include the newly created connector
		sourceElement.GetConnectors().Refresh();
		
		log.debug( "Connector creado entre " + sourceElement.GetName() 
				+ " and " + targetElement.GetName() );
				
		// ==================================================
		// SET CLIENT AND SUPPLIER ROLES
		// ==================================================
		ConnectorEnd clientEnd = theConnector.GetClientEnd();
		clientEnd.SetVisibility( "Private" );
		clientEnd.SetRole( sourceElement.GetName() );
		clientEnd.Update();
		
//		ConnectorEnd supplierEnd = theConnector.GetSupplierEnd();
//		supplierEnd.SetVisibility( "Protected" );
//		supplierEnd.SetRole( sourceElement.GetName() );
//		supplierEnd.Update();
	}
	
	
	public static Map<String,Element> getAllRepositoryEntities(Repository rep){
		Collection<Element> elementSet = rep.GetElementSet("",0);
		Map<String,Element> repoEntitiesMap = new HashMap<String, Element>();
		
		Iterator<Element> it = elementSet.iterator();
		while(it.hasNext()){
			Element nextElement = it.next();
			repoEntitiesMap.put(nextElement.GetName(), nextElement);
		}		
		return repoEntitiesMap;
	}
	
	public static List<String> getDuplicateEntitiesNames(Repository rep){
		List<String> duplicateEntities = new ArrayList<String>();
		Collection<Element> elementSet = rep.GetElementSet("",0);
		Map<String,Element> repoEntitiesMap = new HashMap<String, Element>();
		
		Iterator<Element> it = elementSet.iterator();
		while(it.hasNext()){
			Element nextElement = it.next();
			if(nextElement.GetType().equals("Package")){
				continue;
			}
			Element elementFound = repoEntitiesMap.get(nextElement.GetName());
			if(elementFound != null){
				duplicateEntities.add(nextElement.GetName());
			} else {
				repoEntitiesMap.put(nextElement.GetName(), nextElement);
			}
		}		
		return duplicateEntities;
	}	
	
	
	public static List<IModel> getEAElementDetailTree(String EAFileName){
		
		List<IModel> iModelList = new ArrayList<IModel>();
		Repository rep = null;
				
		try {
			// Create a repository object - This will create a new instance of
			// EA
			rep = new Repository();
			rep.OpenFile( EAFileName );
			
			//Se obtiene una lista de todos los elementos del modelo
			log.debug( "Obteniendo la lista de entidades completas del modelo" );
			Map<String,Element> allElements = EAModelInteractionHelper.getAllRepositoryEntities(rep);
			Set<String> keys = allElements.keySet();
			for (String key : keys) {
				Element element = allElements.get(key);
				Model model = new Model();
				model.setName(element.GetName());
				model.setStereotype(element.GetStereotype());
				iModelList.add(model);
				List<IAttribute> modelAttributeList = new ArrayList<IAttribute>();
				model.setAttributes(modelAttributeList);
				
				// Obtiene todos los atributos del objeto
				Collection<Attribute> attributesCollection = element.GetAttributes();
				if( attributesCollection.GetCount() > 0 )
				{						
					Iterator<Attribute> it = attributesCollection.iterator();
					while(it.hasNext()){
						Attribute attribute = it.next();
						ModelObjectAttribute modelAttribute = new ModelObjectAttribute();
						modelAttribute.setName(attribute.GetName());
						modelAttribute.setDescription(attribute.GetNotes());
						modelAttribute.setSubtype(attribute.GetType());
						modelAttributeList.add(modelAttribute);
					}
				}
			}
			
		} 
		catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
		finally {
			if (rep != null) {
				// Clean up
				rep.CloseFile();
				rep.Exit();
				rep.destroy();
			}
		}
		
		return iModelList;		
	}
		
}
