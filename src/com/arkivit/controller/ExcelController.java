package com.arkivit.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Path;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.arkivit.model.MetadataToExcelGUI;
import com.arkivit.view.ExcelAppGUI;

public class ExcelController {

	private MetadataToExcelGUI model;
	private ExcelAppGUI view;

	public ExcelController(MetadataToExcelGUI model, ExcelAppGUI view){

		this.model = model;
		this.view = view;

	}

	public void init() {
		view.setVisible(true);
		//done button in panelForm
				view.getBtnDone().addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						
						doneButton();
						

					}

				});
	
		
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
	
	public void doneButton() {
		
		model.getGeneralBean().setDescDelivery(view.getDescriptionDeliveryTxt().getText());
		
		model.getGeneralBean().setArchiveCreator(view.getArchiveCreatorTxt().getText());
		
		model.getGeneralBean().setArchiveCreatorNum(view.getoNumArchiveCreatorTxt().getText());
		
		model.getGeneralBean().setArchiveName(view.getArchiveNameTxt().getText());
		
		model.getGeneralBean().setComment(view.getCommentTxt().getText());
		
		model.getGeneralBean().setConsultantBur(view.getConsultantBureauTxt().getText());
		
		model.getGeneralBean().setEmail(view.getMailContactPersonTxt().getText());
		
		model.getGeneralBean().setContactDelivPerson(view.getContactPersonDelivTxt().getText());
		
		model.getGeneralBean().setDelivGov(view.getDelivGovernmentTxt().getText());
		
		model.getGeneralBean().setDelivGovNum(view.getoNumDelivGovernmentTxt().getText());
		
		model.getGeneralBean().setSystemName(view.getSystemNameTxt().getText());
		
		model.getGeneralBean().setTelContactPerson(view.getTelContactPersonTxt().getText());
		
		model.getGeneralBean().setWithdrawDate(view.getWithdrawalDateTxt().getText());
				
		
		view.getPanelForm().setVisible(false);
		view.getPanel().setVisible(true);
		
	}

	public void openButton(ActionEvent e) {
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
	}

}