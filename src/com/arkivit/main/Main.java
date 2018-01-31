package com.arkivit.main;

import com.arkivit.controller.ExcelController;
import com.arkivit.model.MetadataToExcelGUI;
import com.arkivit.view.ExcelAppGUI;

public class Main {
	
	
	public static void main(String[] args) {
		ExcelAppGUI view = new ExcelAppGUI();
		MetadataToExcelGUI model = new MetadataToExcelGUI();
		ExcelController controller = new ExcelController(model, view);
		controller.init();
	}
	
}
