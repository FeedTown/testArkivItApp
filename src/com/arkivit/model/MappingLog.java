package com.arkivit.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
		//model.clearArrayList();
	}

	public void mappedLog() {

		Logger logger = Logger.getLogger("MyLog");  
		FileHandler fh;
		String logName = model.getTargetexcelFilepath() + "/" + model.getExcelFileName() + ".log";

		try {  
			System.out.println(model.getMappedFiles().toString());

			if(!(model.getMappedFiles().isEmpty())) {

				fh = new FileHandler(logName);
				logger.addHandler(fh);
				SimpleFormatter formatter = new SimpleFormatter();  
				fh.setFormatter(formatter);  
				int count = 0;

				for(File mappedTemp: model.getMappedFiles()) {
					if(mappedTemp.isDirectory()) 
					{
						logger.info("\n Mapped folder: " + mappedTemp.getName() + "\n" + 
								" Orignal folder: "+ model.getIllegalCharFiles().get(count)+ "\n");
					}
					
					else 
					{
						logger.info("\n Mapped file: " + mappedTemp.getName() + "\n" + 
								" Orignal file: "+ model.getIllegalCharFiles().get(count)+ "\n");
					}
					count++;
				}

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


