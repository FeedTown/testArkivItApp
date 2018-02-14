package com.arkivit.main;

import org.apache.log4j.BasicConfigurator;

import com.arkivit.controller.ExcelController;
import com.arkivit.controller.ExcelControllerFX;
import com.arkivit.model.MetadataToExcelGUI;
import com.arkivit.view.ExcelAppGUI;
import com.arkivit.view.ExcelAppGUIFX;


public class Main {
	
	
	public static void main(String[] args) throws Exception {
		
		MetadataToExcelGUI model = new MetadataToExcelGUI();
		
		ExcelAppGUIFX viewFx = new ExcelAppGUIFX();
		
		ExcelAppGUI view = new ExcelAppGUI();
		
		@SuppressWarnings("unused")
		ExcelController controller = new ExcelController(model,view);
		
		ExcelControllerFX controllerFx = new ExcelControllerFX(model,viewFx);
		
		ExcelControllerFX.launch(controllerFx.getClass(),args);
			
	}
	
}
