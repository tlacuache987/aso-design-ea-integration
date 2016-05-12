package mx.com.adesis.asodesign.eamodeler.generatesourcecode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import mx.com.adesis.asodesign.eaintegration.model.api.IModel;
import mx.com.adesis.asodesign.eaintegration.model.api.impl.Model;
import mx.com.adesis.asodesign.eaintegration.model.attribute.api.IAttribute;
import mx.com.adesis.asodesign.eaintegration.model.enums.AttributeType;
import mx.com.adesis.asodesign.eamodeler.EAModelInteractionHelper;
import mx.com.adesis.asodesign.eamodeler.modeltospreadsheet.SpreadSheetAttribute;

import org.sparx.Attribute;
import org.sparx.Collection;
import org.sparx.Element;
import org.sparx.Repository;

@Slf4j
public class GenerateJsonExample {
	
	
	public String getElementDetailTreeAsJson(IModel iModel){
		
		String modelName = iModel.getName();
				
		StringBuffer sb = new StringBuffer();
		sb.append("{\n");
		
		int numAttributes = this.getNumAttributesInModel(iModel);
		int currentAttributeNumber = 1;
		for(IAttribute attribute : iModel.getAttributes()){
			SpreadSheetAttribute shAttribute = (SpreadSheetAttribute) attribute;
	    	if(!shAttribute.isHasChildAttributes()){
	    		sb.append("\"").append(shAttribute.getName()).append("\" : ");
	    		if(!shAttribute.isHasChildAttributesList()){
	    			sb.append("\"").append(getExampleTextByAttributeType(shAttribute));
		    		String endLine = "\",\n";
		    		//System.out.println(currentAttributeNumber + " - " + numAttributes);
		    		if(currentAttributeNumber == numAttributes){
		    			endLine = "\"\n";
		    		}
		    		sb.append(endLine);	
	    		}else{
	    			sb.append("[\"").append(getExampleTextByAttributeType(shAttribute));
		    		String endLine = "\"],\n";
		    		//System.out.println(currentAttributeNumber + " - " + numAttributes);
		    		if(currentAttributeNumber == numAttributes){
		    			endLine = "\"]\n";
		    		}
		    		sb.append(endLine);	
	    		}
	    		
	    	} else {
	    		
	    		//verifica que el atributo no se llame igual que el de la entidad padre
	    		if(shAttribute.getSubtype().equalsIgnoreCase(iModel.getName())){
	    			continue;
	    		}
	    		sb.append("\"").append(shAttribute.getName()).append("\" : ");
	    		if(!shAttribute.isHasChildAttributesList()){
	    			//prefixFixed += "." + shAttribute.getName();
		    		String str = getElementDetailTreeAsJson(shAttribute.getChildModel());
		    		sb.append(str);
		    		//System.out.println("[entidad: " + str + " - " +  currentAttributeNumber + " - " + numAttributes + " ]");
		    		if(currentAttributeNumber < numAttributes){
		    			sb.append(",");
		    		}
	    		}else{
	    			String str = getElementDetailTreeAsJson(shAttribute.getChildModel());
		    		sb.append("["+str+"]");
		    		//System.out.println("[entidad: " + str + " - " +  currentAttributeNumber + " - " + numAttributes + " ]");
		    		if(currentAttributeNumber < numAttributes){
		    			sb.append(",");
		    		}
	    			
	    		}
	    		
	    	}
	    	currentAttributeNumber++;
	    }
	    sb.append("\n}");
	   
		return sb.toString();	
	}
	
	public IModel getElementDetailTree(String EAFileName, String rootElementGuid){
		
		IModel model = null;
		Repository rep = null;
		
		log.debug( "Comienza el proceso de obtener las propiedades del elemento con Guid(" + rootElementGuid + ")" );
		
		try {
			// Create a repository object - This will create a new instance of
			// EA
			rep = new Repository();
			rep.OpenFile( EAFileName );
			
			//Se obtiene una lista de todos los elementos del modelo
			log.debug( "Obteniendo la lista de entidades completas del modelo" );
			Map<String,Element> allElements = EAModelInteractionHelper.getAllRepositoryEntities(rep);
			
			Element theRootElement =  rep.GetElementByGuid(rootElementGuid);
			
			if(theRootElement != null){

				model = this.getElementDetail(theRootElement, allElements);
			
			} else {
				log.debug( "Elemento con Guid(" + rootElementGuid + ") no encontrado" );
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
		
		return model;		
	}
	
	public IModel getElementDetail(Element parentElement, Map<String,Element> allElementsMap)
			throws Exception {
		IModel model = new Model();
		
		model.setName(parentElement.GetName());
		List<IAttribute> modelAttributes = new ArrayList<IAttribute>();
		model.setAttributes(modelAttributes);
		if(parentElement != null){
			log.debug( "Trabajando en el elemento " + parentElement.GetName() 
					+ "' (Type=" + parentElement.GetType() + " ID=" 
					+ parentElement.GetElementID() + ")" );
			
			// Obtiene todos los atributos del objeto
			Collection<Attribute> attributesCollection = parentElement.GetAttributes();
			if( attributesCollection.GetCount() > 0 )
			{						
				Iterator<Attribute> it = attributesCollection.iterator();
				while(it.hasNext()){
					Attribute attribute = it.next();
					String type = attribute.GetType();
					SpreadSheetAttribute modelAttribute = new SpreadSheetAttribute();
					modelAttribute.setName(attribute.GetName());
					modelAttribute.setAttributeType(setAttributeType(type));
					modelAttribute.setStereotype(attribute.GetStereotype());
					modelAttribute.setSubtype(type);
					modelAttributes.add(modelAttribute);
					
					//valida si es tipolista
					if(type.indexOf("<")!= -1){
						//regresa solo el tipo de la lista
						type = type.substring(type.indexOf("<")+1, type.length()-1);
						modelAttribute.setHasChildAttributesList(true);
					}
					
					//verifica si es un objeto que a su vez tiene más objetos hijos
					Element element = allElementsMap.get(type);
					if(element != null){
						
						//en caso de que tenga objetos hijos se manda llamar este metodo de 
						StringBuffer sb = new StringBuffer();
						sb.append("El elemento: '").append(parentElement.GetName()).append("' (Type=")
							.append(parentElement.GetType()).append(") tiene una entidad hija de tipo (")
							.append(attribute.GetType()).append(") " + element.GetStereotype());
						log.debug( sb.toString() );
						
						if(!element.GetStereotype().equalsIgnoreCase("enumeration") && 
								!element.GetStereotype().equalsIgnoreCase("interface")){
							
							//Se evita ciclado de entidades si hay atributos del mismo tipo que de la entidad padre
							/*se genera validacion para ver si la entidad tiene un atributo lista de tu tipo 
 
							 */
							if(!parentElement.GetName().equalsIgnoreCase(attribute.GetType())){
								String atribustoLista= attribute.GetType();
								if(atribustoLista.indexOf("<") != -1 ){
									log.debug( "Atributo Lista Ignorado: " + parentElement.GetName() + " - " +attribute.GetType());
								}else{
									modelAttribute.setHasChildAttributes(true);
									
									IModel childModel = 
											getElementDetail(element, allElementsMap);
									
									modelAttribute.setChildModel(childModel);
								
								}
							
							
							} else {
								log.debug( "Atributo Ignorado: " + parentElement.GetName() + " - " +attribute.GetType());
							}
						
						} else {
							//TODO procesa ENUMS
							//TODO procesa Interfaces
						}
						
					}
										
				}
			}
		}
		return model;
	}
	
	private String getExampleTextByAttributeType(SpreadSheetAttribute attribute){
		AttributeType attType = attribute.getAttributeType();
		String stereotype = attribute.getStereotype();
		
		String exampleText= "";
		
		if(attType != null){
		
			switch (attType){
				case STRING:
					exampleText = "Sample text";
					break;
				case NUMBER:
					exampleText = "10000.76";
					break;
				case INTEGER:
					exampleText = "109";
					break;
				case DATE:
					exampleText = "100000989775";
					break;
				case BOOLEAN:
					exampleText = "true";
					break;
			}
		
		}
		
		if (stereotype!= null) {
			if(stereotype.equalsIgnoreCase("enumeration") || stereotype.equalsIgnoreCase("enum")){
				exampleText = "ENUMvalue";
			}
		}
		
		return exampleText;
	}
	
	private AttributeType setAttributeType(String type){
				
		AttributeType attributeType = null;
		
		if(type != null){
			
			if(type.equalsIgnoreCase("String") || type.equalsIgnoreCase("string")){
				attributeType = AttributeType.STRING;
			} else if(type.equalsIgnoreCase("BigDecimal") || type.equalsIgnoreCase("BigDecimal")){
				attributeType = AttributeType.NUMBER;
			} else if(type.equalsIgnoreCase("Boolean") || type.equalsIgnoreCase("boolean")){
				attributeType = AttributeType.BOOLEAN;
			} else if(type.equalsIgnoreCase("Integer") || type.equalsIgnoreCase("int") || type.equalsIgnoreCase("Long") || type.equalsIgnoreCase("long")){
				attributeType = AttributeType.INTEGER;
			} else if(type.equalsIgnoreCase("Date") || type.equalsIgnoreCase("DateTime")){
					attributeType = AttributeType.DATE;
			} else {
				attributeType = AttributeType.OBJECT;
			}
		}
		return attributeType;
	}
	
	private int getNumAttributesInModel(IModel model){
		int count = 0;
		for(IAttribute modelItem : model.getAttributes()){
			count++;
		}
		return count;
	}
	
}
