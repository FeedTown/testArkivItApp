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
		view.getPanelForm().setVisible(false);
		view.getPanel().setVisible(true);
		
	}

	public void openButton(ActionEvent e) {
		int returnValue = view.getChooser().showOpenDialog(null);
		String path;

		if(returnValue == JFileChooser.APPROVE_OPTION) {
			//model.setMyFile(view.getChooser().getSelectedFile());
			//1model.setSourceFolderPath(view.getChooser().getSelectedFile().getAbsolutePath());
			//model.getSourceFolderPath() = model.getMyFile().getAbsolutePath();
			//2view.getOpenTxtField().setText(view.getChooser().getSelectedFile().getAbsolutePath());
			//model.setTargetexcelFilepath(view.getChooser().getSelectedFile().getAbsolutePath());

			/*model.setMyFile(view.getChooser().getSelectedFile());
				model.setSourceFolderPath(model.getMyFile().getAbsolutePath());
				view.getOpenTxtField().setText(model.getMyFile().getAbsolutePath());*/

			model.setSourceFolderPath(view.getChooser().getSelectedFile().getAbsolutePath());
			
		//model.setText(view.getTextbox.getText)
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
			//model.setMyFile2(view.getSaveFile().getSelectedFile());
			/*model.setTargetexcelFilepath(view.getSaveFile().getSelectedFile().getAbsolutePath());
				model.setExcelFileName(view.getSaveFile().getParent() + ".xls");
				view.getSaveTxtField().setText(view.getSaveFile().getSelectedFile().getName());*/
			/*model.setMyFile2(view.getSaveFile().getSelectedFile());
				model.setTargetexcelFilepath(model.getMyFile2().getParent());
				model.setExcelFileName(view.getSaveFile().getName(model.getMyFile2()) + ".xls");
				view.getSaveTxtField().setText(model.getMyFile2().getAbsolutePath());*/

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
		/*File sourceFile = new File(view.getOpenTxtField().getText());
		model.setDestinationFile(new File(view.getSaveTxtField().getText()));
		Path sourcePath = sourceFile.toPath();
		Path destinationPath = model.getDestinationFile().toPath();*/
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

}
