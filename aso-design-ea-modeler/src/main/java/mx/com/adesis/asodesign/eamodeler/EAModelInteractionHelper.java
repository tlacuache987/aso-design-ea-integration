package mx.com.adesis.asodesign.eamodeler;

import lombok.extern.slf4j.Slf4j;

import org.sparx.Attribute;
import org.sparx.AttributeTag;
import org.sparx.Collection;
import org.sparx.Connector;
import org.sparx.ConnectorEnd;
import org.sparx.Element;
import org.sparx.Package;
import org.sparx.Repository;

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
		theElement.SetNotes(description);
		if(stereotype!= null){
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
	
	
}
