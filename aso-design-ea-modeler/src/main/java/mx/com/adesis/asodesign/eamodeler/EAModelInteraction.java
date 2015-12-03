package mx.com.adesis.asodesign.eamodeler;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import mx.com.adesis.asodesign.eaintegration.api.IArrayAttribute;
import mx.com.adesis.asodesign.eaintegration.api.IAttribute;
import mx.com.adesis.asodesign.eaintegration.api.IEnumAttribute;
import mx.com.adesis.asodesign.eaintegration.api.IModel;
import mx.com.adesis.asodesign.eaintegration.api.IObjectAttribute;
import mx.com.adesis.asodesign.eaintegration.enums.AttributeType;

import org.sparx.Attribute;
import org.sparx.AttributeTag;
import org.sparx.Collection;
import org.sparx.Element;
import org.sparx.Package;
import org.sparx.Repository;
import org.sparx.TaggedValue;

@Slf4j
public class EAModelInteraction {
	
	public void workOnNewEntity(String eapFile, IModel model, String packageGUID){
		Repository rep = null;
		
		log.debug("iniciando parseo de entidad..." + model.getName());
		try
		{
			// Create a repository object - This will create a new instance of EA
			rep = new Repository();
			rep.OpenFile(eapFile); 
			
			// Nos ubicamos en el paquete base
			Package thePackage = rep.GetPackageByGuid(packageGUID);
						
			if ( thePackage != null && thePackage.GetParentID() != 0 )
			{
				Collection<Element> elements = thePackage.GetElements();
				Element theElement = elements.AddNew(model.getName(), "Class");
				theElement.SetNotes(model.getDescription());
				theElement.Update();
				
				log.debug( "Trabajando en el elemento" + theElement.GetName() 
						+ "' (Type=" + theElement.GetType() + " ID=" 
						+ theElement.GetElementID() + ")" );
				
				//Trabaja con atributos
				for (IAttribute modelAttribute : model.getAttributes()) {
					workOnEntityAttributes(rep, modelAttribute, theElement.GetElementID());
				}
						
				
			}
			
		}		
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		finally
		{
			if ( rep != null )
			{
				// Clean up
				rep.CloseFile();
				rep.Exit();
				rep.destroy();
			}
		}
		
	}
	
	public void workOnEntityAttributes(Repository rep, IAttribute modelAttribute, int elementID) throws Exception{
				
		try
		{
			Element theElement = rep.GetElementByID( elementID );
			
			// ==================================================
			// ADD AN ATTRIBUTE
			// ==================================================
			// Create an attribute to work on
			Collection<Attribute> attributes = theElement.GetAttributes();
			Attribute newAttribute = attributes.AddNew( modelAttribute.getName(), 
					parseAttributeType(modelAttribute, modelAttribute.getFormat()) );
			newAttribute.Update();				
			attributes.Refresh();
			
			log.debug( "Se agrega  attribute: " + newAttribute.GetName() 
					+ "(Type=" + newAttribute.GetType() + ", ID=" 
					+ newAttribute.GetAttributeID() + ")" );
			
			int addedAttributeID = newAttribute.GetAttributeID();
			
			if(modelAttribute.getRequired() != null && modelAttribute.getRequired() == true){
				// ==================================================
				// MANAGE ATTRIBUTE TAGGED VALUES
				// ==================================================
				// Add an attribute tag
				Collection<AttributeTag> tags = newAttribute.GetTaggedValues();
				String tagName = "json-param-required";
				AttributeTag newTag 
						= tags.AddNew( tagName, "true" );
				newTag.Update();
				tags.Refresh();
				
				log.debug( "Se agrega tag: " + newTag.GetName() 
						+ " (ID=" + newTag.GetTagID() + ")" );
				int newTagID = newTag.GetTagID();
				
				newTag = null;
				tags = null;
			}
			
		}		
		catch ( Exception e )
		{
			e.printStackTrace();
			throw e;
		}
	}
	
	public void workOnEntityAttributesEAP(String eapFile, IAttribute modelAttribute, int elementID){
		
		Repository rep = null;
		
		try
		{
			// Create a repository object - This will create a new instance of EA
			rep = new Repository();
			
			rep.OpenFile(eapFile); 
			
			Element theElement = rep.GetElementByID( elementID );
			
			// ==================================================
			// ADD AN ATTRIBUTE
			// ==================================================
			// Create an attribute to work on
			Collection<Attribute> attributes = theElement.GetAttributes();
			Attribute newAttribute = attributes.AddNew( modelAttribute.getName(), 
					parseAttributeType(modelAttribute, modelAttribute.getFormat()) );
			newAttribute.Update();				
			attributes.Refresh();
			
			log.debug( "Added attribute: " + newAttribute.GetName() 
					+ "(Type=" + newAttribute.GetType() + ", ID=" 
					+ newAttribute.GetAttributeID() + ")" );
			
			int addedAttributeID = newAttribute.GetAttributeID();
			
			if(modelAttribute.getRequired() != null && modelAttribute.getRequired() == true){
				// ==================================================
				// MANAGE ATTRIBUTE TAGGED VALUES
				// ==================================================
				// Add an attribute tag
				Collection<AttributeTag> tags = newAttribute.GetTaggedValues();
				String tagName = "json-param-required";
				AttributeTag newTag 
						= tags.AddNew( tagName, "true" );
				newTag.Update();
				tags.Refresh();
				
				log.debug( "Added tag: " + newTag.GetName() 
						+ " (ID=" + newTag.GetTagID() + ")" );
				int newTagID = newTag.GetTagID();
				
				newTag = null;
				tags = null;
			}
			
		}		
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		finally
		{
			if ( rep != null )
			{
				// Clean up
				rep.CloseFile();
				rep.Exit();
				rep.destroy();
			}
		}
			
	}
	
	public void parseEntities(String eapFile, List<IModel> models){
	
		log.debug("iniciando parseo de entidades...");
		org.sparx.Repository rep = new org.sparx.Repository();
		
		
		try{
			rep.OpenFile(eapFile);
			log.debug("archivo design-template-aso.eap cargado...");
			
			// Nos ubicamos en el paquete raiz
			Package thePackage = rep.GetPackageByID(210);
			
			if ( thePackage != null && thePackage.GetParentID() != 0 )
			{
				for (IModel model : models) {
									
					Collection<Element> elements = thePackage.GetElements();
					Element element = elements.AddNew(model.getName(), "Class");
					element.SetNotes(model.getDescription());
					
					//create tag value
					Collection<TaggedValue> elementTaggedValues = element.GetTaggedValues();
					TaggedValue classTag = elementTaggedValues.AddNew("class-required-test", "true"); 
					classTag.Update();
									
					element.Update();
													
					Collection<Attribute> attributes = null;
					for(IAttribute modelAttribute : model.getAttributes()){
						attributes = element.GetAttributes();
						Attribute newAttribute = attributes.AddNew(modelAttribute.getName(), parseAttributeType(modelAttribute, modelAttribute.getFormat()));
						newAttribute.SetNotes(modelAttribute.getDescription());
						
						Collection<AttributeTag> tags = newAttribute.GetTaggedValues();
						if(modelAttribute.getRequired() != null && modelAttribute.getRequired() == true){
							// ==================================================
							// MANAGE ATTRIBUTE TAGGED VALUES
							// ==================================================
							// Add an attribute tag
							String tagName = "MyAttributeTag";
							AttributeTag newTag = tags.AddNew( tagName, "Number" );
							newTag.Update();
							tags.Refresh();
							
							newTag = null;
						}
											
						//Lista los tag values
						// List all element tags
						if(tags != null){
							log.debug("cantidad de taggedValues " + tags.GetCount());
							for ( short i = 0 ; i < tags.GetCount() ; i++ )
							{
								AttributeTag currentTag = tags.GetAt( i );
								String currentTagName = currentTag.GetName();
								
								log.debug( "    Tagged Value: " + currentTagName );
							}
						}									
						
						log.debug("termina de agregar atributos");
						tags = null;
						
						newAttribute.Update();
						newAttribute = null;
					}
					
					attributes.Refresh();
					thePackage.GetElements().Refresh();
					
					//testElementID = element.ElementID;
					//Session.Output( "Added Element: " + element.Name + " (ID=" + testElementID + ")" );
									
					log.debug("termina");
					
				}
			}
		}
		catch ( Exception e )
		{
			log.error("error al parsear entidad:", e);
		}
		finally
		{
			if ( rep != null )
			{
				// Clean up
				rep.CloseFile();
				rep.Exit();
				rep.destroy();
			}
		}
		
	}
	
	public String parseAttributeType(IAttribute attribute, String format){
		String javaType = null;
		
		if(attribute instanceof IObjectAttribute){
			
			IObjectAttribute att = (IObjectAttribute) attribute;
			AttributeType attType = att.getAttributeType();
			javaType = "String";
			if(attType == AttributeType.BOOLEAN){
				javaType = "Boolean";
			} else if (attType == AttributeType.INTEGER){
				javaType = "Integer";
			} else if (attribute.hasSubtype()){
				javaType = att.getSubtype();
			}
			if(format != null && format.equals("date-time") && attType == AttributeType.STRING){
				javaType = "Date";
			}		
			
		} else if (attribute instanceof IArrayAttribute){
			
			IArrayAttribute att = (IArrayAttribute) attribute;
			AttributeType attType = att.getAttributeType();
			javaType = "String[]";
			if(attType == AttributeType.BOOLEAN){
				javaType = "Boolean[]";
			} else if (attType == AttributeType.INTEGER){
				javaType = "Integer[]";
			} else if (attribute.hasSubtype()){
				javaType = att.getSubtype() + "[]";
			}
			if(format != null && format.equals("date-time") && attType == AttributeType.STRING){
				javaType = "Date[]";
			}
			
		}else if (attribute instanceof IEnumAttribute){
			
			//TODO creaClaseEnum
			javaType = "enum";
		}
			
		return javaType;
	}
	
	public String parseAttributeTypeOld(AttributeType modelAttributeType, String format){
		String javaType = "String";
		if(modelAttributeType == AttributeType.BOOLEAN){
			javaType = "Boolean";
		} else if (modelAttributeType == AttributeType.INTEGER){
			javaType = "Integer";
		}
		
		if(format != null && format.equals("date-time") && modelAttributeType == AttributeType.STRING){
			javaType = "Date";
		}		
		
		return javaType;
	}
	

}
