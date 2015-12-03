package mx.com.adesis.asodesign.eamodeler.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.reflect.Modifier;
import java.net.URI;
import java.net.URL;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;




import mx.com.adesis.asodesign.eamodeler.execution.CreateEntityExecution;
import mx.com.adesis.asodesign.eamodeler.execution.IExecution;
import sun.misc.Launcher;

/**
 * Main UI Window for the Java Example. Allows the user to choose an EAP file to run examples on,
 * select various example from the list and see their description, and run the examples and view
 * the output.
 */
public class ExecutionUI extends JFrame implements ActionListener, ListSelectionListener, HyperlinkListener
{	
	//----------------------------------------------------------
	//                    STATIC VARIABLES
	//----------------------------------------------------------
	private static final long serialVersionUID = 6743733221739030693L;
	private static final String EXAMPLE_PACKAGE = "mx.com.adesis.asodesign.eamodeler.execution";
	private static final String STYLE_TEXT = "<style type=\"text/css\">body { font-size: 12pt; font-family: sans-serif }</style>";
	
	//----------------------------------------------------------
	//                   INSTANCE VARIABLES
	//----------------------------------------------------------
	private JLabel lblHeader;
	
	private JLabel lblFilePath;
	private File projectFile;
	private JButton cmdFilePathChooser;
	
	private JLabel lblSelectExample;
	private JList listSelectExample;
	private DefaultListModel listSelectExampleModel;
	private JEditorPane lblExampleDescription;
	private JButton cmdRunExample;
	
	private JList listOutput;
	private DefaultListModel listOutputModel;
	
	//----------------------------------------------------------
	//                      CONSTRUCTORS
	//----------------------------------------------------------
	public ExecutionUI()
	{
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle( "Enterprise Architect Object Model" );
		this.projectFile = new File("C:\\proyectos\\proyecto_ASO_multicanal\\diseño\\fuentes_descargados\\repo_git\\aso-design\\Diagrams\\design-template.eap");
		this.initComponents();
	}
	
	//----------------------------------------------------------
	//                    INSTANCE METHODS
	//----------------------------------------------------------
	/**
	 * Lays out the components for the ExampleUI frame
	 */
	public void initComponents()
	{
		// Frame Content Pane will be laid out with BorderLayout
		this.getContentPane().setLayout( new BorderLayout() );
		
		// Place a header in north
		Font headerFont = new Font( "Arial", Font.PLAIN, 24 );
		this.lblHeader = new JLabel( "EA MODEL INTERACTION" );
		this.lblHeader.setFont( headerFont );
		this.getContentPane().add( this.lblHeader, BorderLayout.NORTH );
		
		// ====================================
		// INPUT PANEL
		// ====================================
		JPanel inputPanel = new JPanel();
		GridBagLayout gridBag = new GridBagLayout();
		GridBagConstraints gridCon = new GridBagConstraints();
		
		Insets unrelatedInsets = new Insets(5, 5, 5, 5);
		Insets relatedInsets = new Insets(5, 5, 0, 5);
		
		// Initialise basic grid constraints
		inputPanel.setLayout( gridBag );
		gridCon.fill = GridBagConstraints.BOTH;
		gridCon.weighty = 0.0;
		gridCon.weightx = 0.9;
		gridCon.gridwidth = 9;
		gridCon.gridheight = 1;
		gridCon.insets = unrelatedInsets;
		gridCon.ipadx = 5;
		gridCon.ipady = 5;
		
		// 
		// File Path: Label and Button
		//
		this.lblFilePath = new JLabel();
		this.updateFilePathLabel();
		gridBag.setConstraints( this.lblFilePath, gridCon );
		inputPanel.add( this.lblFilePath );
				
		gridCon.weightx = 0.1;
		gridCon.gridwidth = GridBagConstraints.REMAINDER;
		this.cmdFilePathChooser = new JButton("...");
		this.cmdFilePathChooser.addActionListener( this );
		gridBag.setConstraints( this.cmdFilePathChooser, gridCon );
		inputPanel.add( this.cmdFilePathChooser );
	
		//
		// Select Example: Label, List and Description field
		//
		this.lblSelectExample = new JLabel("Select Process");
		gridCon.insets = relatedInsets;
		gridBag.setConstraints( this.lblSelectExample, gridCon );
		inputPanel.add( this.lblSelectExample );
		
		gridCon.weighty = 0.7;
		gridCon.gridheight = 7;
		this.listSelectExampleModel = new DefaultListModel();
		this.listSelectExample = new JList( this.listSelectExampleModel );
		this.listSelectExample.addListSelectionListener( this );
		this.initExampleListEntries();
		
		JScrollPane testScroller = new JScrollPane( this.listSelectExample );
		gridBag.setConstraints( testScroller, gridCon );
		inputPanel.add( testScroller );
		
		gridCon.weighty = 0.3;
		gridCon.gridheight = 3;
		this.lblExampleDescription = new JEditorPane("text/html", "");
		this.lblExampleDescription.setEditable( false );
		this.lblExampleDescription.setBackground( new Color(245, 246, 190) );
		this.lblExampleDescription.addHyperlinkListener( this );
		this.setDescriptionText( "Select an example from the list to view its description." );
		
		JScrollPane descriptionScroller = new JScrollPane( this.lblExampleDescription );
		gridBag.setConstraints( descriptionScroller, gridCon );
		inputPanel.add( descriptionScroller );
		
		//
		// Run Example: Button
		//
		gridCon.insets = unrelatedInsets;
		gridCon.weighty = 0.0;
		gridCon.gridheight = 1;
		this.cmdRunExample = new JButton("Run Process");
		this.cmdRunExample.addActionListener( this );
		gridBag.setConstraints( this.cmdRunExample, gridCon );
		inputPanel.add(  this.cmdRunExample );
		
		// ====================================
		// OUTPUT PANEL
		// ====================================
		this.listOutputModel = new DefaultListModel();
		this.listOutput = new JList( this.listOutputModel );
		
		JSplitPane splitPane = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT, inputPanel, new JScrollPane(this.listOutput) );
		
		this.getContentPane().add(  splitPane, BorderLayout.CENTER );
	}
	
	private void initExampleListEntries()
	{
		// Create a new instance of the IExample and add it to the list
		IExecution newExample = new CreateEntityExecution();
		this.listSelectExampleModel.addElement( newExample );
	}
	
	/**
	 * Dynamically instantiates any classes that realise the IExample interface in the package 
	 * org.sparx.javaexample.examples and adds them to the example list.
	 */
	private void initExampleListEntriesOri()
	{
		String examplePackagePath = EXAMPLE_PACKAGE.replace( '.', '/' );
		if ( !examplePackagePath.startsWith( "/" ) )
			examplePackagePath = "/" + examplePackagePath;
		
		// Get a file object for the package
		URL testURL = Launcher.class.getResource( examplePackagePath );
		if ( testURL == null )
		{
			// If the path couldn't be resolved, then notify the user and return
			String message = "The example package " + EXAMPLE_PACKAGE;
			message += "' is not on the classpath.\n\n";
			message += "Examples will not be loaded.";
			JOptionPane.showMessageDialog( this, message, "Unable to load examples", JOptionPane.ERROR_MESSAGE );
			return;
		}
		
		// Convert %20 in the file path to spaces
		String filePath = testURL.getFile().replace( "%20", " " );
		File testPackageAsDir = new File( filePath );
		
		if ( testPackageAsDir.exists() )
		{
			// Search all files in the package directory
			String[] files = testPackageAsDir.list();
			for ( String file : files )
			{
				// If the file is a class
				if ( file.endsWith(".class") )
				{
					// remove the class extension
					String className = file.substring(0, file.length() - 6 );
					
					try
					{
						// Get a reference to its Class object
						Class<?> exampleClass = Class.forName( EXAMPLE_PACKAGE + "." + className );
						
						// Don't try and instantiate interfaces or abstract classes
						if ( !exampleClass.isInterface() && !Modifier.isAbstract(exampleClass.getModifiers()) )
						{
							// Cast it as an IExample (Will throw ClassCastException if it fails)
							Class<? extends IExecution> asIExample = exampleClass.asSubclass( IExecution.class );
						
							// Create a new instance of the IExample and add it to the list
							IExecution newExample = asIExample.newInstance();
							this.listSelectExampleModel.addElement( newExample );
						}
						
					}
					catch ( ClassNotFoundException cnfe )
					{
						System.out.println( "WARNING: Class " + className + " not found");
					}
					catch ( ClassCastException cce )
					{
						System.out.println( "WARNING: Not adding class " + className 
												+ " as it does not implement the IExample interface");
					}
					catch ( IllegalAccessException iae )
					{
						System.out.println( "WARNING: Could not instantiate private example class " + className );
					}
					catch ( InstantiationException ie )
					{
						System.out.println( "WARNING: Could not instantiate example class " + className 
												+ " as it has no default constructor");
					}
				}
			}
		}
		else
		{
			// Notify the user that the example package does not exist
			String message = "The example package " + EXAMPLE_PACKAGE;
			message += "' is not on the classpath.\n\n";
			message += "Examples will not be loaded.";
			JOptionPane.showMessageDialog( this, message, "Unable to load examples", JOptionPane.ERROR_MESSAGE );
		}
	}
	
	/**
	 * Updates the File Path label text
	 */
	private void updateFilePathLabel()
	{
		this.lblFilePath.setText( "File Path: " + this.projectFile.getAbsolutePath() );
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed( ActionEvent e )
	{
		if ( e.getSource() == this.cmdFilePathChooser )
		{
			//
			// File path chooser was clicked
			//
			
			// Show the file chooser dialog
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.addChoosableFileFilter( new EAPFileFilter() );
			int returnValue = fileChooser.showOpenDialog( this );
			
			// If the action wasn't cancelled, then select the file and
			// update the label
			if ( returnValue == JFileChooser.APPROVE_OPTION )
			{
				this.projectFile = fileChooser.getSelectedFile();
				this.updateFilePathLabel();
			}
		}
		else if ( e.getSource() == this.cmdRunExample )
		{
			//
			// Run example button was clicked
			//
			this.listOutputModel.clear();
			
			// Check the file exists
			if ( !(this.projectFile.exists() && !this.projectFile.isDirectory()) )
			{
				String message = "The file '";
				message += this.projectFile.getAbsolutePath();
				message += "' does not exist or is a directory.";
				JOptionPane.showMessageDialog( this, message, "Invalid File", JOptionPane.ERROR_MESSAGE );
				return;
			}
			
			IExecution asIExample = 
				(IExecution)this.listSelectExample.getSelectedValue();
			
			if ( asIExample != null )
			{
				// Set a wait cursor while the example is running
				this.setCursor( Cursor.getPredefinedCursor( Cursor.WAIT_CURSOR ) );
				
				// Run the example
				asIExample.runExample( this.projectFile, this );
				
				// Restore the cursor to the default
				this.setCursor( Cursor.getDefaultCursor() );
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	public void valueChanged( ListSelectionEvent e )
	{
		if ( e.getSource() == this.listSelectExample )
		{
			// A list selection changed on the example list, so update the description field
			IExecution asIExample = 
				(IExecution)this.listSelectExample.getSelectedValue();
			
			
			this.setDescriptionText( asIExample.getDescription() );
			this.lblExampleDescription.setCaretPosition( 0 );
		}	
	}
	
	public DefaultListModel getOutputListModel()
	{
		return this.listOutputModel;
	}
	
	private void setDescriptionText(String text)
	{
		this.lblExampleDescription.setText( STYLE_TEXT + "<body>" + text + "</body>");
	}
	//----------------------------------------------------------
	//                     STATIC METHODS
	//----------------------------------------------------------	
	/*
	 * Called in response to a HyperLink event being triggered
	 */
	public void hyperlinkUpdate( HyperlinkEvent e )
	{
		// If the event is an ACTIVATED event
		if ( e.getEventType() == HyperlinkEvent.EventType.ACTIVATED )
		{
			URL theUrl = e.getURL();
			
			// The following only works in Java 6+ so if you've got an older version,
			// you'll have to comment it out and live without hyperlink navigation			
			try
			{
				// Get desktop object (Requires Java 6)
				Desktop desktop = Desktop.getDesktop();
					
				// Convert the URL to a URI
				URI urlAsUri = theUrl.toURI();
				desktop.browse( urlAsUri );	
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog( this, "Could not navigate to: " 
						+ theUrl.toExternalForm()  );
			}
		}
	}
	


}
