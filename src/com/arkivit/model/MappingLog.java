package com.arkivit.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.arkivit.controller.ExcelControllerFX;
import com.arkivit.view.SecondScene;

public class MappingLog {

	MetadataToExcelGUI model = new MetadataToExcelGUI();
	String logName = model.getTargetexcelFilepath() + "/" + model.getExcelFileName() + ".log";
	private File logFile = new File(logName);


	public MappingLog() {

	}

	public MappingLog(MetadataToExcelGUI model) {
		this.model = model;
	}

	public void mappedLog() {

		Logger logger = Logger.getLogger("MyLog");  
		FileHandler fh;
		String logName = model.getTargetexcelFilepath() + "/" + model.getExcelFileName() + ".log";

		try {  

			if(!(model.getMappedFiles().isEmpty())) {

				fh = new FileHandler(logName);
				logger.addHandler(fh);
				SimpleFormatter formatter = new SimpleFormatter();  
				fh.setFormatter(formatter); 
				int count = 0;


				// Displays number of mapped  folders and files
				long mappedFilecount = model.getMappedFiles().stream()
						.filter(mappedTemp -> (mappedTemp.exists()))
						.count();
				logger.info(" Number of Mapped folders and files: " + mappedFilecount + "\n"); 

				// Displays mapped folders and files 


				String path ="";
				getLog(model.getMappedFiles(), model.getIllegalCharFiles(),logger, "Original file:","Mapped file:");
				getLog(model.getMappedFolder(), model.getIllegarCharFolders(),logger , "Original folder:","Mapped folder:");


				/*for(File mappedTemp: model.getMappedFiles()) 
				{

					//Path for mapped files
					path = mappedTemp.getParentFile().getAbsolutePath();
					path = path.replace(model.getSourceFolderPath(), model.getFolderName());

					/*if(mappedTemp.isDirectory()) 
					{


						logger.info("\n Path: " + path
								+ "\n Mapped folder: " + mappedTemp.getName() + "\n" + 
								" Orignal folder: "+ model.getIllegalCharFiles().get(count)+ "\n");
					}*/


				/*		logger.info("\n Path: " + path
								+ "\n Mapped file: " + mappedTemp.getName() + "\n" + 
								" Orignal file: "+ model.getIllegalCharFiles().get(count)+ "\n");

					count++;
				} */

				fh.close();
			}

			else {

				System.out.println("No log file was created."); 
			}

		}

		catch (SecurityException e) {  
			e.printStackTrace();  
		} catch (IOException e) {  
			e.printStackTrace();  
		}


	}

	private void getLog(ArrayList<File> charMappedList, ArrayList<String> charIllegalList, Logger log, String orgName, String mappedName) {
		int count = 0;
		String path ="";
		for(File mappedTemp: charMappedList) 
		{
			//Path for mapped files
			path = mappedTemp.getParentFile().getAbsolutePath();
			path = path.replace(model.getSourceFolderPath(), model.getFolderName());


			log.info("\n Path: " + path
					+ "\n "+ mappedName+" " + mappedTemp.getName() + "\n" + 
					orgName + " "+ charIllegalList.get(count)+ "\n");

			count++;
		} 


	}
	public File getLogFile() {
		return logFile;
	}

	public void setLogFile(File logFile) {
		this.logFile = logFile;
	}

}


