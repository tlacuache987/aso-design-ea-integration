package mx.com.adesis.asodesign.eamodeler.execution;

import java.io.File;

import mx.com.adesis.asodesign.eamodeler.ui.ExecutionUI;


/**
 * Interface that defines a basic example unit. The ExampleUI Window will call toString and 
 * getDescription to get the values for this Example's list entry and description respectivley.
 * 
 * The ExampleUI window will also call runExample() to execute this example, supplying the
 * file that has been selected (which has been pre-checked for existence) and a reference
 * to the ListModel of the ExampleUI window's output list.
 */
public interface IExecution
{
	//----------------------------------------------------------
	//                    STATIC VARIABLES
	//----------------------------------------------------------
	
	//----------------------------------------------------------
	//                    METHOD PROTOTYPES
	//----------------------------------------------------------
	/**
	 * Returns a brief description of this Example
	 */
	public abstract String getDescription();
	
	/**
	 * Runs this example with the specified project file, outputing results to outputList
	 */
	public abstract void runExample( File projectFile, ExecutionUI uiFrame );
	
}
