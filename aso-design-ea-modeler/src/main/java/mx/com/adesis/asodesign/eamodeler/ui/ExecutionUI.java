package mx.com.adesis.asodesign.eamodeler.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.URI;
import java.net.URL;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
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
import javax.swing.JTextField;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import mx.com.adesis.asodesign.eamodeler.execution.CreateAllEntitiesSpreadsheetExecution;
import mx.com.adesis.asodesign.eamodeler.execution.CreateEntityExecution;
import mx.com.adesis.asodesign.eamodeler.execution.CreateEntityMappingExecution;
import mx.com.adesis.asodesign.eamodeler.execution.GetDuplicatedEntitiesExecution;
import mx.com.adesis.asodesign.eamodeler.execution.IExecution;
import mx.com.adesis.asodesign.eamodeler.execution.ModelDifferencesExecution;
import sun.misc.Launcher;

/**
 * Main UI Window for the Java Example. Allows the user to choose an EAP file to run examples on,
 * select various example from the list and see their description, and run the examples and view
 * the output.
 */
public class ExecutionUI extends JFrame implements ActionListener, ListSelectionListener, HyperlinkListener, WindowListener
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
	private JLabel loadLabel;
	
	//OpenWindow
	private JFrame window = new JFrame ("Select EA Element guid");
	
	
	private IExecution asIExample;
	
	//----------------------------------------------------------
	//                      CONSTRUCTORS
	//----------------------------------------------------------
	public ExecutionUI()
	{
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle( "Enterprise Architect Object Model" );
		this.projectFile = new File("C:\\proyectos\\proyecto_ASO_multicanal\\diseño\\enterpsise_architect\\aso-arquitect\\design-template-aso.eap");
		
		loadConfig();
		
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
		
		//ImageIcon loading = new ImageIcon("loading_icon.gif");
        //loadLabel = new JLabel("Loading... ", loading, JLabel.CENTER);
		loadLabel = new JLabel("Loading... ", JLabel.CENTER);
        loadLabel.setVisible(false);
		
		// ====================================
		// INPUT PANEL
		// ====================================
		JPanel inputPanel = new JPanel();
		GridBagLayout gridBag = new GridBagLayout();
		GridBagConstraints gridCon = new GridBagConstraints();
		
		inputPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
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
				
//		gridBag.setConstraints( this.lblJSONFilePath, gridCon );
//		inputPanel.add( this.lblJSONFilePath );
//		
//		gridBag.setConstraints( this.cmdJSONFilePathChooser, gridCon );
//		inputPanel.add( this.cmdJSONFilePathChooser );
	
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
		
		inputPanel.add( loadLabel );
		
		// ====================================
		// OUTPUT PANEL
		// ====================================
		this.listOutputModel = new DefaultListModel();
		this.listOutput = new JList( this.listOutputModel );
		
		JSplitPane splitPane = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT, inputPanel, new JScrollPane(this.listOutput) );
		
		this.getContentPane().add(  splitPane, BorderLayout.CENTER );
		
		//window.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		window.addWindowListener(this);
		
		InternalOptionsPanel myWindowPanel = new InternalOptionsPanel();
		window.getContentPane().add (myWindowPanel);
		window.pack();
	}
	
	private void initExampleListEntries()
	{
		// Create a new instance of the IExample and add it to the list
		IExecution newExample = new CreateEntityExecution();
		IExecution newEntityMappingExample = new CreateEntityMappingExecution();
		IExecution createAllEntitiesSpreadsheetExecution = new CreateAllEntitiesSpreadsheetExecution();
		IExecution modelDifferencesExecution = new ModelDifferencesExecution();
		IExecution getDuplicatedEntitiesExecution = new GetDuplicatedEntitiesExecution();
		this.listSelectExampleModel.addElement( newExample );
		this.listSelectExampleModel.addElement( newEntityMappingExample );
		this.listSelectExampleModel.addElement( createAllEntitiesSpreadsheetExecution );
		this.listSelectExampleModel.addElement( modelDifferencesExecution );
		this.listSelectExampleModel.addElement( getDuplicatedEntitiesExecution );
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
			
			asIExample = 
				(IExecution)this.listSelectExample.getSelectedValue();
			
			if ( asIExample != null )
			{
				// Set a wait cursor while the example is running
				this.setCursor( Cursor.getPredefinedCursor( Cursor.WAIT_CURSOR ) );
				
				if(asIExample.openWindow()){
					window.setVisible (true);
				} else {
					// Run the example
					
					ExecutionUI.this.runProcess(projectFile, null, null);
					
				}
							
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
		this.lblExampleDescription.paintImmediately(this.lblExampleDescription.getVisibleRect());
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
	
	private void runProcess(File projectFile, File jsonSchemaFile, String guid){
		ExecutionUI.this.setDescriptionText( "Comienzo del Proceso, espere..." );
		
		try{
			Thread.sleep(2000);
		}
		catch(Exception ex){
		}
		
		asIExample.runProcess( projectFile, jsonSchemaFile, ExecutionUI.this, guid );
					
		ExecutionUI.this.setDescriptionText("PROCESO TERMINADO");
	}
	
	
	public class InternalOptionsPanel extends JPanel implements ActionListener {
		
		private JLabel lblJSONFilePath;
		private JButton cmdJSONFilePathChooser;
		
		private JButton jbutton;
		private JLabel jlabel;
		private JTextField jguid;
		
		private File jsonSchemaFile;
		
		public InternalOptionsPanel() {
		    //construct components
			
			jsonSchemaFile = new File("C:\\Temp\\raml\\schemas\\Participation-fak.raml");
			
			jlabel = new JLabel ("Set EA Element GUID");
		    jbutton = new JButton ("Aceptar");
		    jbutton.addActionListener(this);
		    jguid = new JTextField (20);
		    jguid.setPreferredSize( new Dimension( 240, 24 ) );

		    //adjust size and set layout
		    setPreferredSize (new Dimension (550, 156));
		    setLayout (null);
		    
		    this.lblJSONFilePath = new JLabel();
			lblJSONFilePath.setText("Select JSON-SCHEMA File: ");
			
			this.cmdJSONFilePathChooser = new JButton("...");
			this.cmdJSONFilePathChooser.addActionListener( this );

		    //add components
		    add (jlabel);
		    add (jbutton);
		    add (jguid);
		    
		    //set component bounds (only needed by Absolute Positioning)
		    jlabel.setBounds (20, 45, 150, 25);
		    jguid.setBounds (260, 45, 240, 25);
		    jbutton.setBounds (195, 90, 110, 25);
		    
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if ( e.getSource() == this.cmdJSONFilePathChooser )
			{
				//
				// File path chooser was clicked
				//
				
				// Show the file chooser dialog
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.addChoosableFileFilter(null);
				int returnValue = fileChooser.showOpenDialog( this );
				
				// If the action wasn't cancelled, then select the file and
				// update the label
				if ( returnValue == JFileChooser.APPROVE_OPTION )
				{
					this.jsonSchemaFile = fileChooser.getSelectedFile();
					this.updateJSONSchemaFilePathLabel();
				}
			} 
			else if (e.getSource() == this.jbutton) {
				
				// Check the JSON-SCHEMA file exists
//				if ( !(this.jsonSchemaFile.exists() && !this.jsonSchemaFile.isDirectory()) )
//				{
//						String message = "The file '";
//						message += this.jsonSchemaFile.getAbsolutePath();
//						message += "' does not exist or is a directory.";
//						JOptionPane.showMessageDialog( this, message, "Invalid File", JOptionPane.ERROR_MESSAGE );
//						return;
//				}
								
				// TODO Auto-generated method stub
				window.setVisible(false);
							
				ExecutionUI.this.runProcess(projectFile, jsonSchemaFile, jguid.getText());
			}			
			
		}
		
		/**
		 * Updates the File Path label text
		 */
		private void updateJSONSchemaFilePathLabel()
		{
			this.lblJSONFilePath.setText( "JSON File Path: " + this.jsonSchemaFile.getAbsolutePath() );
		}
	}


	@Override
	public void windowClosing(java.awt.event.WindowEvent windowEvent){
			
		
		
		
	}
	
	private String loadConfig(){
		File configFile = new File("config.properties");
		String host = null; 
		try {
		    FileReader reader = new FileReader(configFile);
		    Properties props = new Properties();
		    props.load(reader);
		 
		    host = props.getProperty("eaFile");
		 
		    System.out.print("eaFile name is: " + host);
		    reader.close();
		} catch (FileNotFoundException ex) {
		   ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return host;
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
		System.out.println(" ***** si entra aqui: 1");
		
	    
	        //save the file
	    	
	    	System.out.print("si entra aqui:");
	    	
	    	   	File configFile = new File("config.properties");
		        try{
		        	
		        	Properties props = new Properties();
		            props.setProperty("eaFile", this.projectFile.getAbsolutePath());
		            FileWriter writer = new FileWriter(configFile);
		            props.store(writer, "Program settings");
		            writer.close();
		        	
		        	dispose();  //dispose the frame
		        }
		        catch(IOException io){
		            JOptionPane.showMessageDialog(null,io.getMessage());
		        }
	         
		
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		System.out.println(" ***** si entra aqui: 2");
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		System.out.println(" ***** si entra aqui: 3");
	}

}
