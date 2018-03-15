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

	public MappingLog() {

	}

	public MappingLog(MetadataToExcelGUI model) {
		this.model = model;
	}

	public void mappedLog() {

		Logger logger = Logger.getLogger("MyLog");  
		FileHandler fh;
		String logName = model.getTargetexcelFilepath() + "/" + model.getExcelFileName() + ".log";
		//int count = 0 ;
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
				for(File mappedTemp: model.getMappedFiles()) {
					
					path = mappedTemp.getParentFile().getAbsolutePath();
					path = path.replace(model.getSourceFolderPath(), model.getFolderName());
					
					if(mappedTemp.isDirectory()) 
					{
					
						
						logger.info("\n Path: " + path
								+ "\n Mapped folder: " + mappedTemp.getName() + "\n" + 
								" Orignal folder: "+ model.getIllegalCharFiles().get(count)+ "\n");
					}

					else 
					{
						logger.info("\n Path: " + path
								+ "\n Mapped file: " + mappedTemp.getName() + "\n" + 
								" Orignal file: "+ model.getIllegalCharFiles().get(count)+ "\n");
					}
					count++;
				} 
								
			/*	final AtomicInteger counter = new AtomicInteger();
				
				model.getMappedFiles().forEach(mappedTemp -> {
					if(mappedTemp.isDirectory()) {
						logger.info(" Mapped folder: " + mappedTemp.getName() + "\n" + 
								    "       Orignal folder: "+ model.getIllegalCharFiles().get(counter.intValue())+ "\n");
					}
					else {
						logger.info(" Mapped file: " + mappedTemp.getName() + "\n" + 
							 	    "       Orignal file: "+ model.getIllegalCharFiles().get(counter.intValue())+ "\n");
					}
					
					counter.incrementAndGet();
					System.out.println("INCREMENT ATOMIC INTEGER " + counter);
				});   */

			/*	model.getMappedFiles().stream()
				.filter(mappedTemp -> mappedTemp.isFile())
				.forEach(mappedTemp ->{logger.info("\n Mapped File: " + mappedTemp.getName() + "\n" + 
													" Orignal file: "+ model.getIllegalCharFiles().get(test.intValue()) + "\n");
				test.incrementAndGet();
				});

				model.getMappedFiles().stream()
				.filter(mappedTemp -> mappedTemp.isDirectory())
				.forEach(mappedTemp -> { logger.info("\n Mapped folder: " + mappedTemp.getName() + "\n" + 
													" Orignal folder: "+ model.getIllegalCharFiles().get(test.intValue()) + "\n");

				test.incrementAndGet();
				});  */

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
}


