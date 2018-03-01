package com.arkivit.model;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MappingLog {
	MetadataToExcelGUI model = new MetadataToExcelGUI();
	public MappingLog(MetadataToExcelGUI model) {
		this.model = model;
	}
	public void mappedLog() {
	 Logger logger = Logger.getLogger("MyLog");  
	 FileHandler fh;
	 String logName = "test.log";

	    try {  

	        // This block configure the logger with handler and formatter  
	        fh = new FileHandler(logName);  
	        logger.addHandler(fh);
	        SimpleFormatter formatter = new SimpleFormatter();  
	        fh.setFormatter(formatter);  

	        // the following statement is used to log any messages 
	        for(String temp: model.getMappedFiles()) {
	        	logger.info("Mapped file: " + temp + "\n");
	        }
	        
	    } catch (SecurityException e) {  
	        e.printStackTrace();  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }  


	}
}
	

