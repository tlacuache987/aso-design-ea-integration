package mx.com.adesis.asodesign.eamodeler.ui;

import javax.swing.JOptionPane;

public class UIExecutionHelpers
{
	/**
	 * Prompts the user to input an ID. If the user enters a negative number, or input that
	 * cannot be converted to a number, an appropriate error message will be displayed and
	 * they will be reprompted for the input.
	 *  
	 * @param promptMessage The message to display on the prompt
	 * @param uiFrame The parent frame
	 * @return the ID that the user entered as a short, or -1 if the user cancelled
	 */
	public static short promptForShortID( String promptMessage, ExecutionUI uiFrame )
	{
		short value = -1;
		
		while ( value < 0 )
		{		
			String result = (String)JOptionPane.showInputDialog( uiFrame, promptMessage, "Enter value", 
											JOptionPane.QUESTION_MESSAGE, null, null, 1 );
			
			// A cancel will return -1
			if ( result == null )
				return -1;
			
			try
			{
				// Try to parse the input as a number
				value = Short.parseShort( result );
			
				// If the number was negative, notify the user
				if ( value <= 0 )
				{
					String message = "Provided ID must not be negative";
					JOptionPane.showMessageDialog( uiFrame, message, "Invalid Value", JOptionPane.ERROR_MESSAGE );
				}
			}
			catch ( NumberFormatException nfe )
			{
				// If there was a problem parsing the input then notify the user
				String message = "Unable to convert provided ID to a number";
				JOptionPane.showMessageDialog( uiFrame, message, "Invalid Value", JOptionPane.ERROR_MESSAGE );
			}
		}
		
		return value;
	}
}
