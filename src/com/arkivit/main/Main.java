package com.arkivit.main;

import com.arkivit.controller.ExcelController;
import com.arkivit.controller.ExcelControllerFX;
import com.arkivit.model.MetadataToExcelGUI;
import com.arkivit.view.ExcelAppGUI;
import com.arkivit.view.ExcelAppGUIFX;

import javafx.application.Application;

public class Main {
	
	
	public static void main(String[] args) {
		//ExcelAppGUI view = new ExcelAppGUI();
		MetadataToExcelGUI model = new MetadataToExcelGUI();
		//ExcelController controller = new ExcelController(model, view);
		ExcelAppGUIFX viewFx = new ExcelAppGUIFX();
		
		ExcelControllerFX controllerFx = new ExcelControllerFX(model,viewFx);
		//Application.launch(viewFx.getClass(), args);
		controllerFx.init();
		
	}
	
}
