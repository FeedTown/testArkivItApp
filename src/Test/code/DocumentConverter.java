
package Test.code;

import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XComponentContext;
import com.sun.star.util.XCloseable;
import ooo.connector.BootstrapSocketConnector;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FilenameUtils;

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
	private String sConvertType = "";
	/** Containing the given extension
	 */
	private String sExtension = "";
	/** Containing the current file or directory
	 */
	private String sIndent = "";
	/** Containing the directory where the converted files are saved
	 */
	private String sOutputDir = "";

	private String targetPath = "";

	private String sOutUrl;

	private File outdir;

	ArrayList<File> convertedFiles;
	ArrayList<String> convertedFiles2;


	List<File> testEntries; 
	File filezz;


	public DocumentConverter() {

	}
	
public ArrayList<File> traverse(ArrayList<File> fileDirectory, String targetPath) {
		
		sOutUrl = "file:///" + targetPath.replace( '\\', '/' );
		

		// Iterating for each file and directory
		for ( int i = 0; i < fileDirectory.size(); ++i ) {
			// Testing, if the entry in the list is a directory
			if(fileDirectory.get(i).isDirectory())
			{
				System.out.println("This is a folder");
			}
			else if(fileDirectory.get(i).isFile()) 
			{
				// Converting the document to the favoured type
				try 
				{
					// Composing the URL by replacing all backslashes
					String sUrl = "file:///"
							+ fileDirectory.get(i).getAbsolutePath().replace( '\\', '/' ); 


					System.out.println("Original Files: "+  fileDirectory.get(i).getName());

					if(fileDirectory.get(i).getName().endsWith(".doc") || fileDirectory.get(i).getName().endsWith(".docx")) 
					{ 

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

						String tmp = FilenameUtils.removeExtension(fileDirectory.get(i).getName());

						//int index1 = sUrl.lastIndexOf('/');
						//int index2 = sUrl.lastIndexOf('.');

						/*String sStoreUrl = sOutUrl + sUrl.substring(index1, index2 + 1) + sExtension; */
						//String test = sOutUrl.replace("file:///", "");
						String sStoreUrl = sOutUrl+ "/" + tmp + "." + sExtension;  
						xStorable.storeToURL(sStoreUrl, propertyValues);

						String removeBeginningOfPath = sStoreUrl.replace("file:///", "");
						File convertedFiles = new File(removeBeginningOfPath);

						fileDirectory.add(convertedFiles);

						System.out.println("Converted Files " + convertedFiles.getName());
						
					
						// Closing the converted document. Use XCloseable.close if the
						// interface is supported, otherwise use XComponent.dispose
						XCloseable xCloseable =
								UnoRuntime.queryInterface(XCloseable.class, xStorable);


						if ( xCloseable != null ) 
						{
							xCloseable.close(false);
						} 

						else 
						{
							XComponent xComp =
									UnoRuntime.queryInterface(XComponent.class, xStorable);

							xComp.dispose();
						}
					}

					else 
					{
						System.out.println("NOT CONVERTED : " + fileDirectory.get(i).getName());

					} 
				}

				catch( Exception e ) 
				{
					e.printStackTrace(System.err);
				}
			}

		}


		//sIndent = sIndent.substring(0);
		return  fileDirectory;
	}

	/*public ArrayList<File> traverse(ArrayList<File> fileDirectory, String targetPath) {
		
		sOutUrl = "file:///" + targetPath.replace( '\\', '/' );
		

		// Iterating for each file and directory
		for ( int i = 0; i < fileDirectory.size(); ++i ) {
			// Testing, if the entry in the list is a directory
			if(fileDirectory.get(i).isDirectory())
			{
				System.out.println("This is a folder");
			}
			else if(fileDirectory.get(i).isFile()) {
				// Converting the document to the favoured type
				try 
				{
					// Composing the URL by replacing all backslashes
					String sUrl = "file:///"
							+ fileDirectory.get(i).getAbsolutePath().replace( '\\', '/' ); 


					System.out.println("Original Files: "+  fileDirectory.get(i).getName());

					if(fileDirectory.get(i).getName().endsWith(".docx") || fileDirectory.get(i).getName().endsWith(".doc")) 
					{ 

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

						String tmp = FilenameUtils.removeExtension(fileDirectory.get(i).getName());

						//int index1 = sUrl.lastIndexOf('/');
						//int index2 = sUrl.lastIndexOf('.');

						//String sStoreUrl = sOutUrl + sUrl.substring(index1, index2 + 1) + sExtension; 
						//String test = sOutUrl.replace("file:///", "");
						String sStoreUrl = sOutUrl+ "/" + tmp + "." + sExtension;  
						xStorable.storeToURL(sStoreUrl, propertyValues);

						String removeBeginningOfPath = sStoreUrl.replace("file:///", "");
						File convertedFiles = new File(removeBeginningOfPath);

						fileDirectory.add(convertedFiles);

						System.out.println("Converted Files " + convertedFiles.getName());

						// Closing the converted document. Use XCloseable.close if the
						// interface is supported, otherwise use XComponent.dispose
						XCloseable xCloseable =
								UnoRuntime.queryInterface(XCloseable.class, xStorable);


						if ( xCloseable != null ) 
						{
							xCloseable.close(false);
						} 

						else 
						{
							XComponent xComp =
									UnoRuntime.queryInterface(XComponent.class, xStorable);

							xComp.dispose();
						}
					}

					else 
					{
						System.out.println("NOT CONVERTED : " + fileDirectory.get(i).getName());

					} 
				}

				catch( Exception e ) 
				{
					e.printStackTrace(System.err);
				}
			}

		}


		//sIndent = sIndent.substring(0);
		return  fileDirectory;
	} */

	public ArrayList<File> testMethod2(ArrayList<File> file, String targetPath) {

		String libreOfficePath = "/Applications/LibreOffice.app/Contents/MacOS/";

		/*for(File f : file)
		{
			System.out.println("********Files for the list**********\n" + f.getName());
		} */

		XComponentContext xContext = null;

		try {

			// get the remote office component context
			xContext = BootstrapSocketConnector.bootstrap(libreOfficePath);
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
			//File sourceDir = new File("/Users/RobertoBlanco/Desktop/TestFiler/");

			// Getting the given type to convert to
			sConvertType = "writer_pdf_Export";

			// Getting the given extension that should be appended to the
			// origin document
			sExtension = "pdf";

			// Getting the given type to convert to
			//sOutputDir = "/Users/RobertoBlanco/Desktop/Con_map/";
			//outdir = new File("/Users/RobertoBlanco/Desktop/Con_map/");
			//targetPath = "/Users/RobertoBlanco/Desktop/TestFiler/Test_map/";
			//targetPath = "/Users/RobertoBlanco/Desktop/Test_map/";
			//targetPath = "/Users/RobertoBlanco/Desktop/TestFiler/";
			//convertedFiles = file;
			//targetPath = "";

			// Starting the conversion of documents in the given directory
			// and subdirectories
			

			file = traverse(file, targetPath);


		} 

		catch( Exception e ) 
		{
			e.printStackTrace(System.err);
			System.exit(1);
		}
	

		//BYT TILL gamla klassisk for loop, kan funja
		for(File tempFile: file) {
			if(tempFile.getName().endsWith(".docx") || tempFile.getName().endsWith(".doc")) 
			{
				System.out.println("About to remove doc files---");
				file.remove(tempFile);
				System.out.println("DONE REMOVING...");
				tempFile.delete();
				
			}
			return file;
		}

		return file;
	}

	/*public void testMethod(ArrayList<File> file) {

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
			//File sourceDir = new File("/Users/RobertoBlanco/Desktop/TestFiler/");

			// Getting the given type to convert to
			sConvertType = "writer_pdf_Export";

			// Getting the given extension that should be appended to the
			// origin document
			sExtension = "pdf";

			// Getting the given type to convert to
			sOutputDir = "/Users/RobertoBlanco/Desktop/Con_map/";
			//outdir = new File("/Users/RobertoBlanco/Desktop/Con_map/");
			targetPath = "/Users/RobertoBlanco/Desktop/Test_map/";

			//targetPath = "";

			// Starting the conversion of documents in the given directory
			// and subdirectories
			traverse2(file);
		} 

		catch( Exception e ) 
		{
			e.printStackTrace(System.err);
			System.exit(1);
		}
	} */


	public ArrayList<File> getConvertedFiles() {
		return convertedFiles;
	}

	public void setConvertedFiles(ArrayList<File> convertedFiles) {
		this.convertedFiles = convertedFiles;
	}

	public ArrayList<String> getConvertedFiles2() {
		return convertedFiles2;
	}

	public void setConvertedFiles2(ArrayList<String> convertedFiles2) {
		this.convertedFiles2 = convertedFiles2;
	}

	public  File getOutdir() {
		return outdir;
	}

	public  void setOutdir(File outdir) {
		this.outdir = outdir;
	}

	public String getsOutUrl() {
		return sOutUrl;
	}

	public void setsOutUrl(String sOutUrl) {
		this.sOutUrl = sOutUrl;
	}


	/** Bootstrap UNO, getting the remote component context, getting a new instance
	 * of the desktop (used interface XComponentLoader) and calling the
	 * static method traverse
	 * @param args The array of the type String contains the directory, in which
	 *             all files should be converted, the favoured converting type
	 *             and the wanted extension
	 */

	public void traverse2( ArrayList<File> file ) {
		// Testing, if the file is a directory, and if so, it throws an exception
		/*if ( !file.isDirectory() ) {
			throw new IllegalArgumentException(
					"not a directory: " + file.getName()
					);
		}*/
		// Prepare Url for the output directory
		File outdir = new File(targetPath);
		String sOutUrl = "file:///" + outdir.getAbsolutePath().replace( '\\', '/' );

		System.out.println("\nThe converted documents will stored in \""
				+ outdir.getPath() + "!"); 

		/*System.out.println(sIndent + "[" + file.getName() + "]");
		sIndent += "  ";*/

		// Getting all files and directories in the current directory
		//File[] entries = file.listFiles();

		// Iterating for each file and directory
		for ( int i = 0; i < file.size(); ++i ) {
			// Testing, if the entry in the list is a directory
			int counter = 0;
			// Converting the document to the favoured type
			try {
				System.out.println("Iterating in traverse2...");
				// Composing the URL by replacing all backslashes
				String sUrl = "file:///"
						+ file.get(counter).getAbsolutePath().replace( '\\', '/' );

				// Loading the wanted document
				com.sun.star.beans.PropertyValue propertyValues[] =
						new com.sun.star.beans.PropertyValue[1];
				propertyValues[0] = new com.sun.star.beans.PropertyValue();
				propertyValues[0].Name = "Hidden";
				propertyValues[0].Value = Boolean.TRUE;

				Object oDocToStore =
						DocumentConverter.xCompLoader.loadComponentFromURL(
								sUrl, "_blank", 0, propertyValues);

				// Getting an object that will offer a simple way to store
				// a document to a URL.
				com.sun.star.frame.XStorable xStorable =
						UnoRuntime.queryInterface(
								com.sun.star.frame.XStorable.class, oDocToStore );

				// Preparing properties for converting the document
				propertyValues = new com.sun.star.beans.PropertyValue[2];
				// Setting the flag for overwriting
				propertyValues[0] = new com.sun.star.beans.PropertyValue();
				propertyValues[0].Name = "Overwrite";
				propertyValues[0].Value = Boolean.TRUE;
				// Setting the filter name
				propertyValues[1] = new com.sun.star.beans.PropertyValue();
				propertyValues[1].Name = "FilterName";
				propertyValues[1].Value = sConvertType;

				// Appending the favoured extension to the origin document name
				int index1 = sUrl.lastIndexOf('/');
				int index2 = sUrl.lastIndexOf('.');
				String sStoreUrl = sOutUrl + sUrl.substring(index1, index2 + 1)
				+ sExtension;

				// Storing and converting the document
				File f = new File(sStoreUrl);

				xStorable.storeAsURL(sStoreUrl, propertyValues);


				convertedFiles.add(f);

				System.out.println("Converted file: " + f.getName());


				// Closing the converted document. Use XCloseable.close if the
				// interface is supported, otherwise use XComponent.dispose
				com.sun.star.util.XCloseable xCloseable =
						UnoRuntime.queryInterface(
								com.sun.star.util.XCloseable.class, xStorable);

				if ( xCloseable != null ) {
					xCloseable.close(false);
				} else {
					com.sun.star.lang.XComponent xComp =
							UnoRuntime.queryInterface(
									com.sun.star.lang.XComponent.class, xStorable);

					xComp.dispose();
				}
			}
			catch( Exception e ) {
				e.printStackTrace(System.err);
			}

			System.out.println(sIndent + file.get(i).getName());
		}


		sIndent = sIndent.substring(2);
	}



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

