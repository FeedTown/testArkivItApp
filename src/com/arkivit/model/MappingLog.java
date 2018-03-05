package com.arkivit.model;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.arkivit.controller.ExcelControllerFX;
import com.arkivit.view.SecondScene;

public class MappingLog {
	MetadataToExcelGUI model = new MetadataToExcelGUI();
	//ExcelControllerFX controller = new ExcelControllerFX();


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
 
				for(String temp: model.getMappedFiles()) {
					logger.info("Mapped file: " + temp + "\n");
				}
				fh.close();
			}
			
			else {
				System.out.println("No logg file was created."); 
			}

		}
			
		catch (SecurityException e) {  
			e.printStackTrace();  
		} catch (IOException e) {  
			e.printStackTrace();  
		}  


	}
}


