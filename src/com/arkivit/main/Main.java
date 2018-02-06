package com.arkivit.main;

import com.arkivit.controller.ExcelController;
import com.arkivit.controller.ExcelControllerFX;
import com.arkivit.model.MetadataToExcelGUI;
import com.arkivit.view.ExcelAppGUI;
import com.arkivit.view.ExcelAppGUIFX;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main {
	
	
	public static void main(String[] args) throws Exception {
		//ExcelAppGUI view = new ExcelAppGUI();
		//ExcelController controller = new ExcelController(model, view);
		MetadataToExcelGUI model = new MetadataToExcelGUI();
		
		ExcelAppGUIFX viewFx = new ExcelAppGUIFX();
		
		ExcelControllerFX controllerFx = new ExcelControllerFX(model,viewFx);
		//Application.launch(viewFx.getClass(), args);
		//Application.launch(controllerFx.getClass());
		//new MainController();
		
		//Application.launch(controllerFx.getClass(),args);
		
	}
	
}
