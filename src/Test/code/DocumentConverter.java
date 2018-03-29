
package Test.code;

import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XComponentContext;
import com.sun.star.util.XCloseable;
import ooo.connector.BootstrapSocketConnector;
import java.io.File;

import com.arkivit.model.FileExtension;
import com.sun.star.beans.PropertyValue;
import com.sun.star.frame.XComponentLoader;
import com.sun.star.frame.XDesktop;
import com.sun.star.frame.XStorable;
import com.sun.star.lang.XComponent;
import com.sun.star.lang.XMultiComponentFactory;



/** The class <CODE>DocumentConverter</CODE> allows you to convert all documents
 * in a given directory and in its subdirectories to a given type. A converted
 * document will be created in the same directory as the origin document.
 *
 */
public class DocumentConverter {
	/** Containing the loaded documents
	 */
	private static XComponentLoader xCompLoader = null;
	private static XDesktop xDesktop;
	/** Containing the given type to convert to
	 */
	private static String sConvertType = "";
	/** Containing the given extension
	 */
	private static String sExtension = "";
	/** Containing the current file or directory
	 */
	private static String sIndent = "";
	/** Containing the directory where the converted files are saved
	 */
	private static String sOutputDir = "";

	private static String targetPath = "";
	
	private static String sOutUrl;
	
	private static File outdir; 


	
	
	public static File getOutdir() {
		return outdir;
	}

	public static void setOutdir(File outdir) {
		DocumentConverter.outdir = outdir;
	}

	public static String getsOutUrl() {
		return sOutUrl;
	}

	public static void setsOutUrl(String sOutUrl) {
		DocumentConverter.sOutUrl = sOutUrl;
	}

	public DocumentConverter() {

	}
	
	public DocumentConverter(String sOutUrl) {
		DocumentConverter.sOutUrl = sOutUrl;
	}

	public DocumentConverter(String sConvertType, String sExtension, String sOutputDir ) {
		DocumentConverter.sConvertType = sConvertType;
		DocumentConverter.sExtension = sExtension;
		DocumentConverter.sOutputDir = sOutputDir;
	}

	/** Traversing the given directory recursively and converting their files to
	 * the favoured type if possible
	 * @param fileDirectory Containing the directory
	 */
	public static void traverse(File fileDirectory) {
		// Testing, if the file is a directory, and if so, it throws an exception
		if ( !fileDirectory.isDirectory() ) {
			throw new IllegalArgumentException(
					"not a directory: " + fileDirectory.getName()
					);
		}

		// Prepare Url for the output directory
		outdir = new File(DocumentConverter.sOutputDir);
		sOutUrl = "file:///" + outdir.getAbsolutePath().replace( '\\', '/' );

		System.out.println("\nThe converted documents will stored in \""
				+ outdir.getPath() + "!");

		System.out.println(sIndent + "[" + fileDirectory.getName() + "]");
		sIndent += "  ";

		// Getting all files and directories in the current directory
		File[] entries = fileDirectory.listFiles();
		//FileExtension ext = new FileExtension();
		//int counter = 0;	

		// Iterating for each file and directory
		for ( int i = 0; i < entries.length; ++i ) {
			// Testing, if the entry in the list is a directory
			if ( entries[ i ].isDirectory() ) {
				// Recursive call for the new directory
				traverse( entries[i]);
			} else {
				// Converting the document to the favoured type
				try {

					//if(fileDirectory.getName().endsWith(ext.checkForConvertableFileExtensions().get(counter))) {
						// Composing the URL by replacing all backslashes
						String sUrl = "file:///"
								+ entries[ i ].getAbsolutePath().replace( '\\', '/' );
						
						System.out.println("Original Files: "+  entries[i].getName());
						
						// Loading the wanted document
						PropertyValue propertyValues[] = new PropertyValue[1];
						propertyValues[0] = new PropertyValue();
						propertyValues[0].Name = "Hidden";
						propertyValues[0].Value = Boolean.TRUE;

						Object oDocToStore = xCompLoader.loadComponentFromURL(
								sUrl, "_blank", 0, propertyValues);

						// Getting an object that will offer a simple way to store
						// a document to a URL.			

						XStorable xStorable =
								UnoRuntime.queryInterface(XStorable.class, oDocToStore );

						// Preparing properties for converting the document
						propertyValues = new PropertyValue[3];
						// Setting the flag for overwriting
						propertyValues[0] = new PropertyValue();
						propertyValues[0].Name = "Overwrite";
						propertyValues[0].Value = Boolean.TRUE;
						// Setting the filter name
						propertyValues[1] = new PropertyValue();
						propertyValues[1].Name = "FilterName";
						propertyValues[1].Value = sConvertType;

						propertyValues[2] = new PropertyValue();
						propertyValues[2].Name = "PDFViewSelection";
						propertyValues[2].Value = 2;

						// Appending the favoured extension to the origin document name
						int index1 = sUrl.lastIndexOf('/');
						int index2 = sUrl.lastIndexOf('.');
						String sStoreUrl = sOutUrl + sUrl.substring(index1, index2 + 1)
						+ sExtension;
						File convertedFiles = new File(sStoreUrl);
			
						// Storing and converting the document
						xStorable.storeToURL(sStoreUrl, propertyValues);
						System.out.println("Converted Files " + convertedFiles .getName());
						


						// Closing the converted document. Use XCloseable.close if the
						// interface is supported, otherwise use XComponent.dispose
						XCloseable xCloseable =
								UnoRuntime.queryInterface(XCloseable.class, xStorable);



						if ( xCloseable != null ) {
							xCloseable.close(false);
						} else {
							XComponent xComp =
									UnoRuntime.queryInterface(XComponent.class, xStorable);

							xComp.dispose();
						}
					}
				//}
				catch( Exception e ) {
					e.printStackTrace(System.err);
				}

				//System.out.println(sIndent + entries[ i ].getName());
			}
		}

		sIndent = sIndent.substring(2);
	}

	public static void testMethod(File file) {

		String path = "/Applications/LibreOffice.app/Contents/MacOS/";

		XComponentContext xContext = null;

		try {
			
			// get the remote office component context
			xContext = BootstrapSocketConnector.bootstrap(path);
			System.out.println("Connected to a running office ...");

			// get the remote office service manager
			XMultiComponentFactory xMCF =
					xContext.getServiceManager();

			Object oDesktop = xMCF.createInstanceWithContext(
					"com.sun.star.frame.Desktop", xContext);

			xDesktop = (XDesktop) UnoRuntime.queryInterface(XDesktop.class, oDesktop);

			xCompLoader = UnoRuntime.queryInterface(com.sun.star.frame.XComponentLoader.class,
					xDesktop);


			// Getting the given starting directory
			File sourceDir = new File("/Users/RobertoBlanco/Desktop/TestFiler/");

			// Getting the given type to convert to
			sConvertType = "writer_pdf_Export";

			// Getting the given extension that should be appended to the
			// origin document
			sExtension = "pdf";

			// Getting the given type to convert to
			//sOutputDir = "/Users/RobertoBlanco/Desktop/Con_map/";
			//outDir = new File("/Users/RobertoBlanco/Desktop/Con_map/");

			targetPath = "";

			// Starting the conversion of documents in the given directory
			// and subdirectories
			traverse(sourceDir);
		} 

		catch( Exception e ) 
		{
			e.printStackTrace(System.err);
			System.exit(1);
		}
	}

	
	/** Bootstrap UNO, getting the remote component context, getting a new instance
	 * of the desktop (used interface XComponentLoader) and calling the
	 * static method traverse
	 * @param args The array of the type String contains the directory, in which
	 *             all files should be converted, the favoured converting type
	 *             and the wanted extension
	 */
	/*public static void main( String args[] ) {


		String path = "/Applications/LibreOffice.app/Contents/MacOS/";

		XComponentContext xContext = null;

		try {
			// get the remote office component context
			//xContext = BootstrapSocketConnector.bootstrap(path, "localhost", 8100);
			xContext = BootstrapSocketConnector.bootstrap(path);
			System.out.println("Connected to a running office ...");

			// get the remote office service manager
			XMultiComponentFactory xMCF =
					xContext.getServiceManager();

			Object oDesktop = xMCF.createInstanceWithContext(
					"com.sun.star.frame.Desktop", xContext);

			xDesktop = (XDesktop) UnoRuntime.queryInterface(XDesktop.class, oDesktop);

			xCompLoader = UnoRuntime.queryInterface(com.sun.star.frame.XComponentLoader.class,
					xDesktop);


			// Getting the given starting directory
			File file = new File("/Users/RobertoBlanco/Desktop/TestFiler/");

			// Getting the given type to convert to
			sConvertType = "writer_pdf_Export";

			// Getting the given extension that should be appended to the
			// origin document
			sExtension = "pdf";

			// Getting the given type to convert to
			sOutputDir = "/Users/RobertoBlanco/Desktop/Con_map/";

			// Starting the conversion of documents in the given directory
			// and subdirectories
			traverse(file);

			System.exit(0);
		} catch( Exception e ) {
			e.printStackTrace(System.err);
			System.exit(1);
		}
	} */
}

