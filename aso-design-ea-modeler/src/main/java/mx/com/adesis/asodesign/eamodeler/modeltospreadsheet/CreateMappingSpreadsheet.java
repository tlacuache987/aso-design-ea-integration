package mx.com.adesis.asodesign.eamodeler.modeltospreadsheet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import mx.com.adesis.asodesign.eaintegration.model.api.IModel;
import mx.com.adesis.asodesign.eaintegration.model.api.impl.Model;
import mx.com.adesis.asodesign.eaintegration.model.attribute.api.IAttribute;
import mx.com.adesis.asodesign.eamodeler.EAModelInteractionHelper;

import org.sparx.Attribute;
import org.sparx.Collection;
import org.sparx.Element;
import org.sparx.Repository;

@Slf4j
public class CreateMappingSpreadsheet {
	
	public String getElementDetailTreeAsSpreadSheetRows(String prefix, IModel iModel){
		
		log.debug("*** valor de prefix: " + prefix);
		
		String modelName = iModel.getName();
		String prefixFixed = modelName.substring(0, 1).toLowerCase() + modelName.substring(1);
		if(prefix != null){
			prefixFixed = prefix;
		} 
		
		StringBuffer sb = new StringBuffer();
	    for(IAttribute attribute : iModel.getAttributes()){
	    	SpreadSheetAttribute shAttribute = (SpreadSheetAttribute) attribute;
	    	if(!shAttribute.isHasChildAttributes()){
	    		sb.append(prefixFixed).append(".").append(shAttribute.getName()).append("\n");
	    	} else {
	    		//prefixFixed += "." + shAttribute.getName();
	    		String str = getElementDetailTreeAsSpreadSheetRows(prefixFixed 
	    				+ "." + shAttribute.getName(), shAttribute.getChildModel());
	    		sb.append(str);
	    	}
	    }
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

				CreateMappingSpreadsheet spreedSheet = new CreateMappingSpreadsheet();
				model = spreedSheet.getElementDetail(theRootElement, allElements);
			
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
					SpreadSheetAttribute modelAttribute = new SpreadSheetAttribute();
					modelAttribute.setName(attribute.GetName());
					modelAttributes.add(modelAttribute);
					
					//verifica si es un objeto que a su vez tiene más objetos hijos
					Element element = allElementsMap.get(attribute.GetType());
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
							if(!parentElement.GetName().equalsIgnoreCase(attribute.GetType())){
							
								modelAttribute.setHasChildAttributes(true);
													
								IModel childModel = 
										getElementDetail(element, allElementsMap);
								
								modelAttribute.setChildModel(childModel);
							
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
	
	/** para mandarse llamar de manera externa
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) {
		
		CreateMappingSpreadsheet spreedSheet = new CreateMappingSpreadsheet();
				
		String path = args[0];
		String guid = args[1];
		
		try{
		
			IModel iModel = spreedSheet.getElementDetailTree(path, guid);
			log.debug(iModel.toString());
			
			String mapping = spreedSheet.getElementDetailTreeAsSpreadSheetRows(null, iModel);
			
			log.debug(mapping);
			System.out.println("***********");
			System.out.println(mapping);
		
		}
		catch(Exception e){
			System.out.println("Error al ejecutar el programa: " + e);
		}
		
		
	}
	
}
