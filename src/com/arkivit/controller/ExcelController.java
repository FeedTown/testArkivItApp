package com.arkivit.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Path;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.arkivit.model.MetadataToExcel;
import com.arkivit.view.ExcelAppGUI;

public class ExcelController {
	
	private MetadataToExcel model;
	private ExcelAppGUI view;
	
	public ExcelController(MetadataToExcel model, ExcelAppGUI view){
		
		this.model = model;
		this.view = view;
		
	}
	
	public void init() {
		view.getFrame().setVisible(true);
		//select folder button
		view.getBtnOpenFile().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				openButton(e);
				
			}
			   
		   });
		
		//save as button
		view.getBtnSaveAs().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveButton(e);
				
				
			}
			   
		   });
		
		//create button
		view.getBtnConvert().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				createButton(e);
				
				
			}
			   
		   });
	}
	   
	   public void openButton(ActionEvent e) {
		   int returnValue = view.getChooser().showOpenDialog(null);
		   String path;
		   
			if(returnValue == JFileChooser.APPROVE_OPTION) {
				model.setMyFile(view.getChooser().getSelectedFile());
				model.setSourceFolderPath(view.getChooser().getSelectedFile().getAbsolutePath());
				//model.getSourceFolderPath() = model.getMyFile().getAbsolutePath();
				view.getOpenTxtField().setText(view.getChooser().getSelectedFile().getAbsolutePath());

			}
			if(returnValue == JFileChooser.CANCEL_OPTION) {
				view.getOpenTxtField().setText("");
				view.getBtnSaveAs().setEnabled(false);
			}
			if(e.getSource() == view.getBtnOpenFile() && returnValue == JFileChooser.APPROVE_OPTION) {
				view.getBtnSaveAs().setEnabled(true);
			}
	   }
	   
	   public void saveButton(ActionEvent e) {
		   int returnSaveVal = view.getSaveFile().showSaveDialog(null);
			if(returnSaveVal == JFileChooser.APPROVE_OPTION) {
				model.setMyFile2(view.getSaveFile().getSelectedFile());
				model.setTargetexcelFilepath(view.getSaveFile().getSelectedFile().getAbsolutePath());
				model.setExcelFileName(view.getSaveFile().getParent() + ".xls");
				view.getSaveTxtField().setText(view.getSaveFile().getSelectedFile().getName());

			}
			if(returnSaveVal == JFileChooser.CANCEL_OPTION) {
				view.getSaveTxtField().setText("");
				view.getBtnConvert().setEnabled(false);
			}
			if(e.getSource() == view.getBtnSaveAs() && returnSaveVal == JFileChooser.APPROVE_OPTION) {
				view.getBtnConvert().setEnabled(true);
			}
	   }
	   
	   public void createButton(ActionEvent e) {
		   File sourceFile = new File(view.getOpenTxtField().getText());
			model.setDestinationFile(new File(view.getSaveTxtField().getText()));
			Path sourcePath = sourceFile.toPath();
			Path destinationPath = model.getDestinationFile().toPath();
			//sourcePath.relativize(destinationPath);

			try {
				boolean check = new File(model.getTargetexcelFilepath(), model.getExcelFileName()).exists();
				if(!check) {
					//Files.copy(sourcePath, destinationPath);
					model.testMeth();
					JOptionPane.showMessageDialog(null, "File was successfully created", 
							"Success", JOptionPane.INFORMATION_MESSAGE);
					
					view.getOpenTxtField().setText("");
					view.getSaveTxtField().setText("");
					view.getBtnSaveAs().setEnabled(false);
					view.getBtnConvert().setEnabled(false);

				}
				else if(check){
					System.out.println("CATCH IF");		
					JOptionPane.showMessageDialog(null, "File already exists", 
							"INFO", 
							JOptionPane.INFORMATION_MESSAGE);
					view.getOpenTxtField().setText("");
					view.getSaveTxtField().setText("");
					if(e.getSource() == view.getBtnConvert()) {
						view.getBtnSaveAs().setEnabled(false);
						view.getBtnConvert().setEnabled(false);

					}
				}
			}
			catch(Exception e1){
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "You can't overwrite file", 
						"INFO", 
						JOptionPane.INFORMATION_MESSAGE);
				view.getBtnSaveAs().setEnabled(false);
				view.getBtnConvert().setEnabled(false);
				view.getOpenTxtField().setText("");
				view.getSaveTxtField().setText("");
			}
	   }
	
	
	   /*public void setExcelName(String excelFileName){
	      model.setExcelFileName(excelFileName);		
	   }

	   public String getExcelName(){
	      return model.getExcelFileName();		
	   }*/
}
