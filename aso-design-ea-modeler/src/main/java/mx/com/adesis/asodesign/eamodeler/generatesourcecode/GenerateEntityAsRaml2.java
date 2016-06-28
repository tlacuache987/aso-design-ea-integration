package mx.com.adesis.asodesign.eamodeler.generatesourcecode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.sparx.Attribute;
import org.sparx.Collection;
import org.sparx.Element;
import org.sparx.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import mx.com.adesis.asodesign.eaintegration.model.api.IModel;
import mx.com.adesis.asodesign.eaintegration.model.api.impl.Model;
import mx.com.adesis.asodesign.eaintegration.model.attribute.api.IAttribute;
import mx.com.adesis.asodesign.eaintegration.model.enums.AttributeType;
import mx.com.adesis.asodesign.eamodeler.EAModelInteractionHelper;
import mx.com.adesis.asodesign.eamodeler.modeltojson.ModelToJsonUtils;
import mx.com.adesis.asodesign.eamodeler.modeltospreadsheet.SpreadSheetAttribute;

@Slf4j
@Component
public class GenerateEntityAsRaml2 {
	
	public static void main(String[] args) {
		GenerateEntityAsRaml2 raml = new GenerateEntityAsRaml2();
		String EAFileName = "C:\\Users\\MXPCL15063\\Desktop\\aso_demos\\design-template.eap";
		String rootElementGuid = "{B6DA6341-539F-4fa6-9E10-4517167C13E7}";
		raml.generateEntity(EAFileName,rootElementGuid);
	}
	@Autowired
	ModelToJsonUtils modelToJsonUtils;
	
	ApplicationContext appContext ;
	
	public  void generateEntity(String EAFileName, String rootElementGuid) {
		 appContext = 
		    	  new ClassPathXmlApplicationContext("/spring/eamodeler/asodesign-eamodeler-service-context.xml");
		AutowireCapableBeanFactory acbFactory = appContext.getAutowireCapableBeanFactory();
		acbFactory.autowireBean(this);
		Repository rep = null;
		StringBuffer sb = new StringBuffer();
		try {
			rep = new Repository();
			rep.OpenFile( EAFileName );
			log.debug( "Despues de abrir el reporsitorio");
			Element theRootElement =  rep.GetElementByGuid(rootElementGuid);
			sb.append(theRootElement.GetName()+" :\n");
			sb.append("\ttype: Object \n");
			sb.append("\tproperties: \n");
			this.generateBodyRaml(sb,theRootElement,rep);
			System.out.println(sb.toString());
			log.debug("fin");
		} catch (Exception e) {
			log.debug("Error",e);
		}
		finally {
			if (rep != null) {
				// Clean up
				rep.CloseFile();
		  	    rep.Exit();
				rep.destroy();
			}
		}
		
	}

	private  void generateBodyRaml(StringBuffer sb, Element theRootElement,Repository rep)throws Exception  {
		Collection<Attribute> attributes =  theRootElement.GetAttributes();
		Iterator<Attribute> it = attributes.iterator();
		IModel model = null;
		//Se obtiene una lista de todos los elementos del modelo
		log.debug( "Obteniendo la lista de entidades completas del modelo" );
		Map<String,Element> allElements = EAModelInteractionHelper.getAllRepositoryEntities(rep);
		
//		GenerateJsonExample spreedSheet = new GenerateJsonExample();
		model = this.getElementDetail(theRootElement, allElements);
		String subBody = this.generateBodySubEntity(model,"\t\t");
		sb.append(subBody);
	}
	
	private String generateBodySubEntity(IModel model,String tab) {
		StringBuffer sb = new StringBuffer();
	    String newTab = tab+"\t";
		for(IAttribute attribute : model.getAttributes()){
			SpreadSheetAttribute shAttribute = (SpreadSheetAttribute) attribute;
	    	if(!shAttribute.isHasChildAttributes()){
	    		if(!shAttribute.isHasChildAttributesList()){
	    			sb.append(modelToJsonUtils.parseToRaml1(shAttribute));
	    		}
	    	} else {
	    		
	    		//verifica que el atributo no se llame igual que el de la entidad padre
	    		if(shAttribute.getSubtype().equalsIgnoreCase(model.getName())){
	    			continue;
	    		}
	    		if(!shAttribute.isHasChildAttributesList()){
	    			sb.append(modelToJsonUtils.parseToRaml1(shAttribute));
	    			String str = generateBodySubEntity(shAttribute.getChildModel(),newTab);
		    		sb.append(str);
	    		}
	    	}
	    }
		return sb.toString();
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
					modelAttribute.setDescription(attribute.GetNotes());
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
									log.debug( "Atributo Lista : " + parentElement.GetName() + " - " +attribute.GetType().substring(attribute.GetType().indexOf("<")+1, attribute.GetType().length()-1));
									if(!parentElement.GetName().equalsIgnoreCase(attribute.GetType().substring(attribute.GetType().indexOf("<")+1, attribute.GetType().length()-1))){
										modelAttribute.setHasChildAttributes(true);
										
										IModel childModel = 
												getElementDetail(element, allElementsMap);
										
										modelAttribute.setChildModel(childModel);
									}
								
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
							
							modelAttribute.setSubtype("String");
							Collection<Attribute> attributesEnum = element.GetAttributes();
							Iterator<Attribute> itEnum = attributesEnum.iterator();
							List<String> enumValues = new ArrayList<String>();
							while(itEnum.hasNext()){
								Attribute attributeEnum =itEnum.next();
								enumValues.add(attributeEnum.GetName());
							}
							modelAttribute.setAllowedValues(enumValues);
							System.out.println("***"+modelAttribute.getStereotype());
							System.out.println("***"+modelAttribute.getAllowedValues().toString());
							
							
						}
						
					}
										
				}
			}
		}
		return model;
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
}
