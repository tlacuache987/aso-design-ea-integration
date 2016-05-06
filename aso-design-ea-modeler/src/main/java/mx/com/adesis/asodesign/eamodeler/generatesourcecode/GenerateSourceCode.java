package mx.com.adesis.asodesign.eamodeler.generatesourcecode;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.lang.model.util.Elements;

import lombok.extern.slf4j.Slf4j;
import mx.com.adesis.asodesign.eaintegration.model.api.IModel;
import mx.com.adesis.asodesign.eaintegration.model.api.impl.Model;
import mx.com.adesis.asodesign.eaintegration.model.attribute.api.IAttribute;
import mx.com.adesis.asodesign.eaintegration.model.attribute.api.impl.ModelObjectAttribute;
import mx.com.adesis.asodesign.eamodeler.EAModelInteractionHelper;

import org.sparx.Attribute;
import org.sparx.Collection;
import org.sparx.Element;
import org.sparx.Method;
import org.sparx.Package;
import org.sparx.Project;
import org.sparx.Repository;

@Slf4j
public class GenerateSourceCode {
	
	public void generateSourceCode(String EAFileName, String packageGuid, String fileExtension, String path){
		
		Repository rep = null;
		
		log.debug( "Comienza el proceso de generacion de codigo" );
		
		try {
			// Create a repository object - This will create a new instance of
			// EA
			rep = new Repository();
			rep.OpenFile( EAFileName );
			
			Project project = rep.GetProjectInterface();
			project.GeneratePackage(packageGuid, "recurse=1;overwrite=1;dir=" + path);
						
			changeAllFilesExtension(path, fileExtension);			
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
		
		
	}
	
	public void setGenType(String EAFileName, String packageGuid, String genType){
		
		Repository rep = null;
		
		log.debug( "Comienza el proceso de cambio de genType" );
		
		try {
			// Create a repository object - This will create a new instance of
			// EA
			rep = new Repository();
			rep.OpenFile( EAFileName );
			
			setGenTypeforSinglePackage(rep, packageGuid, genType);
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
		
	}
	
	private void setGenTypeforSinglePackage(Repository rep, String packageGuid, String genType){
		
		Package rootPackage = rep.GetPackageByGuid(packageGuid);
		Collection<Element> childElements = rootPackage.GetElements();
		
		log.debug("paquete raiz: " + rootPackage.GetName() + " - elementos hijos " + childElements.GetCount() + "");
		
		for (Element el : childElements) {
			log.debug(el.GetType());
			if(el.GetType().equals("Class") || el.GetType().equals("Interface")){
				log.debug(el.GetGenfile() + " - " + el.GetName());
				el.SetGentype(genType);
				el.Update();
			} else {
				log.debug(el.GetGenfile() + " - " + el.GetName() + " - " + el.GetType());
			}
		}
		
		Collection<Package> childPackages = rootPackage.GetPackages();
		for (Package packageItem : childPackages) {
			setGenTypeforSinglePackage(rep, packageItem.GetPackageGUID(), genType);
		}								
		
	}
	
	public void changeAllFilesExtension(String directoryName, String extension) {
	    // .............list file
	    File directory = new File(directoryName);

	    // get all the files from a directory
	    File[] fList = directory.listFiles();

	    for (File file : fList) {
	        if (file.isFile()) {
	        	changeFileExtension(file, extension);
	        } else if (file.isDirectory()) {
	        	changeAllFilesExtension(file.getAbsolutePath(), extension);
	        }
	    }
	    
	    System.out.println(fList);
	}
	
	private void changeFileExtension(File f, String targetExtension){
		FileUtils.renameFileExtension(f.getAbsolutePath(), targetExtension);
	}
	
	public void createEAElementSamples(String EAFileName, String packageGUID, String outputPath){
		
		Repository rep = null;
				
		try {
			// Create a repository object - This will create a new instance of
			// EA
			rep = new Repository();
			rep.OpenFile( EAFileName );
			
			//Se obtiene una lista de todos los elementos del modelo
			log.debug( "Obteniendo la lista de entidades del paquete" );
			Package rootPackage = rep.GetPackageByGuid(packageGUID);
			Collection<Package> rootElements = rootPackage.GetPackages();
			
			//crea la carpeta raiz
			String rootOutputFolderStr = outputPath + "\\examples";
			File rootFolder = new File(rootOutputFolderStr);
			if(!rootFolder.exists()){
				rootFolder.mkdir();
			}
			
			Iterator<Package> packagesIterator = rootElements.iterator();
			while(packagesIterator.hasNext()){
				
				Package singlePackage = packagesIterator.next();
				log.debug(singlePackage.GetName() + "\t");		
				
				Collection<Element> elements = 
						singlePackage.GetElements();
				Iterator<Element> elementsIterator = elements.iterator(); 
				
				while(elementsIterator.hasNext()){
					Element element = elementsIterator.next();
					
					Collection<Method> methodsCollection = element.GetMethods();
					Iterator<Method> methodIterator = methodsCollection.iterator();
					while(methodIterator.hasNext()){
						Method method = methodIterator.next();
						log.debug("\t" + method.GetName());
						
						String behavior = method.GetBehavior();
						if(behavior != null && !behavior.equals("")){
							
							int jsonSamplesInputFound = behavior.indexOf("*INPUT");
							int jsonSamplesOutputFound = behavior.indexOf("*OUTPUT");							
							
							if(jsonSamplesInputFound != -1 || jsonSamplesOutputFound != -1){
																
								String inputString = null;
								String outputString = null; 
								
								if(jsonSamplesInputFound != -1){
									int initPoc = jsonSamplesInputFound + 7;
									int endPoc = jsonSamplesOutputFound;
									if(jsonSamplesOutputFound == -1){
										endPoc = behavior.length();
									}
									inputString = behavior.substring(initPoc, endPoc); 
								} 
								
								if(jsonSamplesOutputFound != -1){
									int initPoc = jsonSamplesOutputFound + 7;
									int endPoc = behavior.length();
									outputString = behavior.substring(initPoc, endPoc); 
								}
								
								log.debug("inputString:[" + inputString + "]");
								log.debug("outputString:[" + outputString + "]");
								
								if(inputString != null){
									//Crea los archivos de ejemplo de entrada
									File folder = new File(rootOutputFolderStr);
									if(!folder.exists()){
										folder.mkdir();
									}
									String fileName = rootOutputFolderStr + "\\" + element.GetName() + "_" + method.GetName() + "_input.json";
									log.debug("filename:[" + fileName + "]");
									File file = new File(fileName);
									boolean createFile = file.createNewFile();
									log.debug("file created: " + createFile);
									
									PrintWriter writer = new PrintWriter(file, "UTF-8");
									writer.print(inputString);
									writer.close();
								}
								if(outputString != null){
									//Crea los archivos de ejemplo de salida
									File folder = new File(rootOutputFolderStr);
									if(!folder.exists()){
										folder.mkdir();
									}
									String fileName = rootOutputFolderStr + "\\" + element.GetName() + "_" + method.GetName() + "_output.json";
									log.debug("filename:[" + fileName + "]");
									File file = new File(fileName);
									boolean createFile = file.createNewFile();
									log.debug("file created: " + createFile);
									
									PrintWriter writer = new PrintWriter(file, "UTF-8");
									writer.print(outputString);
									writer.close();
								}
								
								
							}						
							
						}
						
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
						
	}

}
