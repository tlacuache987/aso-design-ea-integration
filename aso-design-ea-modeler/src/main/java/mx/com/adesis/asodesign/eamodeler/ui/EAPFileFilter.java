package mx.com.adesis.asodesign.eamodeler.ui;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 * Custom file filter that allows the JFileChooser to only show .eap files
 */
public class EAPFileFilter extends FileFilter
{
	//----------------------------------------------------------
	//                    STATIC VARIABLES
	//----------------------------------------------------------
	
	//----------------------------------------------------------
	//                   INSTANCE VARIABLES
	//----------------------------------------------------------
	
	//----------------------------------------------------------
	//                      CONSTRUCTORS
	//----------------------------------------------------------
	
	//----------------------------------------------------------
	//                    INSTANCE METHODS
	//----------------------------------------------------------
	@Override
	public boolean accept( File theFile )
	{
		// Accept files if they are a directory, or if they end in .eap
		if ( theFile.isDirectory() )
			return true;
		
		String convertedFileName = theFile.getName().toLowerCase();
		return convertedFileName.endsWith( ".eap" );
	}

	@Override
	public String getDescription()
	{
		return "Enterprise Architect Project Files (*.eap)";
	}
	
	//----------------------------------------------------------
	//                     STATIC METHODS
	//----------------------------------------------------------

}
