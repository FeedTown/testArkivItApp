package com.arkivit.main;

import com.arkivit.controller.ExcelControllerFX;
import com.arkivit.model.MetadataToExcelGUI;
import com.arkivit.view.ExcelAppGUIFX;


public class Main {
	
	
	public static void main(String[] args) throws Exception {
		
		MetadataToExcelGUI model = new MetadataToExcelGUI();
		
		ExcelAppGUIFX viewFx = new ExcelAppGUIFX();
		
		ExcelControllerFX controllerFx = new ExcelControllerFX(model,viewFx);
		
		ExcelControllerFX.launch(controllerFx.getClass(),args);
			
	}
	
}
