package mx.com.adesis.asodesign.eamodeler.ui;

import javax.swing.UIManager;

/**
 * Driver class for the Java Example application
 */
public class Main
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
	
	//----------------------------------------------------------
	//                     STATIC METHODS
	//----------------------------------------------------------
	/**
	 * Entry point for the Java Example application
	 */
	public static void main( String[] args )
	{
	    // Enable Anti-Aliasing
	    System.setProperty( "swing.aatext", "true" );
		
		// Set look and feel to platform default
	    try 
	    {
	    	String lookAndFeelClass = UIManager.getSystemLookAndFeelClassName(); 
	        UIManager.setLookAndFeel( lookAndFeelClass );
	    } 
	    catch ( Exception e )
	    {
	    	System.out.println( "WARNING: Unable to set look and feel to system default" );
	    }
	    
		// Create a new ExampleUI, size it and show it.
		ExecutionUI theExample = new ExecutionUI();
		theExample.setSize( 800, 600 );
		theExample.setVisible( true );
	}
}
