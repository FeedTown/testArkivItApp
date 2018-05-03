package com.arkivit.main;

import com.arkivit.controller.ExcelControllerFX;
import com.arkivit.model.MetadataToExcelGUI;
import com.arkivit.view.SecondScene;
import com.arkivit.view.FirstScene;


public class Main {


	public static void main(String[] args) throws Exception {

		MetadataToExcelGUI model = new MetadataToExcelGUI();

		FirstScene firstScene = new FirstScene();

		SecondScene secondScene = new SecondScene();

		ExcelControllerFX controllerFx = new ExcelControllerFX(model,firstScene,secondScene);
		
		ExcelControllerFX.launch(controllerFx.getClass(),args);
		

	}

}
