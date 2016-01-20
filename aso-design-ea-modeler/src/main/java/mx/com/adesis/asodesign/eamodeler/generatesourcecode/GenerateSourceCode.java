package mx.com.adesis.asodesign.eamodeler.generatesourcecode;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import mx.com.adesis.asodesign.eamodeler.EAModelInteractionHelper;
import mx.com.adesis.asodesign.eamodeler.modeltospreadsheet.CreateMappingSpreadsheet;

import org.sparx.Collection;
import org.sparx.Element;
import org.sparx.Package;
import org.sparx.Project;
import org.sparx.Repository;

@Slf4j
public class GenerateSourceCode {
	
	public void generateSourceCode(String EAFileName, String packageGuid){
		
		Repository rep = null;
		
		log.debug( "Comienza el proceso de generacion de codigo" );
		
		try {
			// Create a repository object - This will create a new instance of
			// EA
			rep = new Repository();
			rep.OpenFile( EAFileName );
			
			Package rootPackage = rep.GetPackageByGuid(packageGuid);
			Collection<Element> childElements = rootPackage.GetElements();
						
			Project project = rep.GetProjectInterface();
			project.GeneratePackage(packageGuid, "recurse=1;overwrite=1;dir=C:\\Temp");
						
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
