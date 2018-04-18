
package Test.code;

import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XComponentContext;
import com.sun.star.util.XCloseable;
import ooo.connector.BootstrapSocketConnector;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
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
	private XComponentLoader xCompLoader = null;
	private XDesktop xDesktop;
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
	ArrayList<File> fileDirectory;


	List<File> testEntries; 
	File filezz;


	public DocumentConverter() {

	}

	public ArrayList<File> traverse(ArrayList<File> fileDirectory, String targetPath) {

		sOutUrl = "file:///" + targetPath.replace( '\\', '/' );

		for ( int i = 0; i < fileDirectory.size(); ++i ) 
		{

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

					if(fileDirectory.get(i).getName().endsWith(".doc") || fileDirectory.get(i).getName().endsWith(".docx") || 
							fileDirectory.get(i).getName().endsWith(".xlsx")) 
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

						//removeFile(fileDirectory);
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

		return  fileDirectory;
	}


	public ArrayList<File> testMethod2(ArrayList<File> file, String targetPath) {

		String libreOfficePath = "/Applications/LibreOffice.app/Contents/MacOS/";

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


			// Getting the given type to convert to
			sConvertType = "writer_pdf_Export";

			// Getting the given extension that should be appended to the
			// origin document
			sExtension = "pdf";


			file = traverse(file, targetPath);
			removeFile(file);


		} 

		catch( Exception e ) 
		{
			e.printStackTrace(System.err);
			System.exit(1);
		}


		return file;
	}



	public void removeFile(ArrayList<File> file) 
	{

		for(int i = 0; i<file.size(); i++) 
		{

			if(file.get(i).getName().endsWith(".docx") || file.get(i).getName().endsWith(".doc") || 
					file.get(i).getName().endsWith(".xlsx")) 
			{
				File tempFile = file.get(i);
				file.remove(tempFile);
				tempFile.delete();
			}

		} 

	}


	public ArrayList<File> getFileDirectory() {
		return fileDirectory;
	}

	public void setFileDirectory(ArrayList<File> fileDirectory) {
		this.fileDirectory = fileDirectory;
	}

	public ArrayList<File> getConvertedFiles() {
		return convertedFiles;
	}

	public void setConvertedFiles(ArrayList<File> convertedFiles) {
		this.convertedFiles = convertedFiles;
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


}

