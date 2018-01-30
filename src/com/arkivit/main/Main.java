package com.arkivit.main;

import java.awt.EventQueue;

import com.arkivit.controller.ExcelController;
import com.arkivit.model.MetadataToExcel;
import com.arkivit.view.ExcelAppGUI;

public class Main {
	
	
	
	
	public static void main(String[] args) {
		ExcelAppGUI view = new ExcelAppGUI();
		MetadataToExcel model = new MetadataToExcel();
		ExcelController controller = new ExcelController(model, view);
		
		controller.init();
	}
	
}
