package com.arkivit.controller;


import java.io.File;
import java.nio.file.Path;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.arkivit.model.MetadataToExcelGUI;
import com.arkivit.view.ExcelAppGUI;
import com.arkivit.view.ExcelAppGUIFX;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;

public class ExcelControllerFX {

	private MetadataToExcelGUI model;
	private ExcelAppGUIFX view;

	public ExcelControllerFX(MetadataToExcelGUI model, ExcelAppGUIFX view){

		this.model = model;
		this.view = view;
		Application.launch(this.view.getClass());

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void init() {
		
		//done button in panelForm	
			view.getSaveButton().setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					System.out.println("SAVEN B");
					doneButton();
					model.testMeth();
					
					
				}});
			
	
		
		//select folder button
		/*view.getBtnOpenFile().addActionListener(new ActionListener() {

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

		});*/
	}
	
	public void doneButton() {
		
		model.getGeneralBean().setDescDelivery(view.getBALtxt().getText());
		
		model.getGeneralBean().setArchiveCreator(view.getAKtxt().getText());
		
		model.getGeneralBean().setArchiveCreatorNum(view.getOAtxt().getText());
		
		model.getGeneralBean().setArchiveName(view.getLMtxt().getText());
		
		model.getGeneralBean().setComment(view.getOLMtxt().getText());
		
		model.getGeneralBean().setConsultantBur(view.getSKtxt().getText());
		
		model.getGeneralBean().setEmail(view.getKFLtxt().getText());
		
		model.getGeneralBean().setContactDelivPerson(view.getTTKtxt().getText());
		
		model.getGeneralBean().setDelivGov(view.getEKtxt().getText());
		
		model.getGeneralBean().setDelivGovNum(view.getANtxt().getText());
		
		model.getGeneralBean().setSystemName(view.getSNtxt().getText());
		
		model.getGeneralBean().setTelContactPerson(view.getUDtxt().getText());
		
		model.getGeneralBean().setWithdrawDate(view.getKOMtxt().getText());
				
		
		//view.getPanelForm().setVisible(false);
		//view.getPanel().setVisible(true);
		
		
	}

	/*public void openButton(ActionEvent e) {
		int returnValue = view.getChooser().showOpenDialog(null);
		String path;

		if(returnValue == JFileChooser.APPROVE_OPTION) {

			model.setSourceFolderPath(view.getChooser().getSelectedFile().getAbsolutePath());
			view.getOpenTxtField().setText(model.getSourceFolderPath());

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
	
			model.setTargetexcelFilepath(view.getSaveFile().getSelectedFile().getParentFile().getAbsolutePath());
			model.setExcelFileName(view.getSaveFile().getSelectedFile().getName() + ".xls");
			view.getSaveTxtField().setText(model.getTargetexcelFilepath());

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
		try {
			boolean check = new File(model.getTargetexcelFilepath(), model.getExcelFileName()).exists();
			if(!check) {
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
	}*/

}