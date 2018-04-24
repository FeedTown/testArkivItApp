package com.arkivit.model;

import java.io.File;
import java.util.ArrayList;

import org.apache.commons.io.FilenameUtils;

import com.sun.star.beans.PropertyValue;
import com.sun.star.frame.XComponentLoader;
import com.sun.star.frame.XDesktop;
import com.sun.star.frame.XStorable;
import com.sun.star.lang.XComponent;
import com.sun.star.lang.XMultiComponentFactory;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XComponentContext;
import com.sun.star.util.XCloseable;

import ooo.connector.BootstrapSocketConnector;

public class DocumentConverter {

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
	private File testFile;


	File fileDirectory;
	ArrayList<File> originalListFile = new ArrayList<>();
	ArrayList<File> convertedFiles;
	ArrayList<File> fileList = new ArrayList<>(); 


	public DocumentConverter() {

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


	public void testMethod3(String targetPath) {

		String libreOfficePath = "/C:/Program Files (x86)/LibreOffice/program/soffice.exe/";

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

			traverse3(targetPath);
			//removeFile3(file);

		} 

		catch( Exception e ) 
		{
			e.printStackTrace(System.err);
			System.exit(1);
		}
		//return file;
	}

	public void traverse3(String targetPath) {
		//ArrayList<File> fileList = new ArrayList<>(); 
		//fileList.add(fileDirectory);
		fileDirectory = new File(targetPath);
		sOutUrl = "file:///" + fileDirectory.getAbsolutePath().replace( '\\', '/' );


		for(File f : fileDirectory.listFiles())
		{

			if(f.isFile())
			{
				//Stores the original files for the html/css/js parser
				originalListFile.add(f);
				
				// Converting the document to the favoured type
				try 
				{
					// Composing the URL by replacing all backslashes
					String testUrl = "file:///" + f.getParentFile().getAbsolutePath().replace("\\", "/");
					String sUrl = "file:///"
							+ f.getAbsolutePath().replace( '\\', '/' ); 


					if(f.getName().endsWith(".doc") || f.getName().endsWith(".docx") || 
							f.getName().endsWith(".xls") || f.getName().endsWith(".xlsx") ||
							f.getName().endsWith(".ppt") || f.getName().endsWith(".pptx")) 
					{ 
						System.out.println("Original Files: "+  f.getName());
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

						String tmp = FilenameUtils.removeExtension(f.getName());

						String sStoreUrl = testUrl+ "/" + tmp + "." + sExtension;  
						xStorable.storeToURL(sStoreUrl, propertyValues);

						String removeBeginningOfPath = sStoreUrl.replace("file:///", "");
						testFile = new File(removeBeginningOfPath);

						fileList.add(testFile);
						System.out.println("Converted Files " + testFile.getName());

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
						System.out.println("NOT CONVERTED : " + f.getName());

					} 
				}

				catch( Exception e ) 
				{
					e.printStackTrace(System.err);
				}
			}
			else if (f.isDirectory()) 
			{
				traverse3(f.getAbsolutePath());
			}
		}


	}

	public void removeFile3(File file) 
	{

		for(int i = 0; i<fileList.size(); i++) 
		{

			if(fileList.get(i).getName().endsWith(".doc") || fileList.get(i).getName().endsWith(".docx") || 
					fileList.get(i).getName().endsWith(".xls") ||fileList.get(i).getName().endsWith(".xlsx") ||
					fileList.get(i).getName().endsWith(".ppt") || fileList.get(i).getName().endsWith(".pptx")) 
			{
				File tempFile = fileList.get(i);
				fileList.remove(tempFile);
				tempFile.delete();
			}

		} 
	}
}
