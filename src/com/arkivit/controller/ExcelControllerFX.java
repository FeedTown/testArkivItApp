package com.arkivit.controller;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.SynchronousQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.arkivit.model.MetadataToExcelGUI;
import com.arkivit.view.ExcelAppGUIFX;
import com.sun.prism.paint.Color;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ProgressBar;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ExcelControllerFX extends Application {

	private MetadataToExcelGUI model;
	private ExcelAppGUIFX view;
	private Stage stage;

	public ExcelControllerFX()
	{
		model = new MetadataToExcelGUI();
		view = new ExcelAppGUIFX();
		//launch();
	}

	public ExcelControllerFX(MetadataToExcelGUI model, ExcelAppGUIFX view){

		this.model = model;
		this.view = view;
		//this.view.start();
	}


	@Override
	public void start(Stage primaryStage) throws Exception {
		view.start();
		view.startSecondScene();
		primaryStage.setTitle("ArkivIT");
		primaryStage.setResizable(false);
		this.stage = primaryStage;
		stage.setScene(view.getScene());
		stage.show();
		view.addActionListenerForButton(new ActionListen());


	}



	public void saveContentButton() {
		//1
		model.getGeneralBean().setDescDelivery(view.getBALtxt().getText());
		//2
		model.getGeneralBean().setArchiveCreator(view.getAKtxt().getText());
		//3
		model.getGeneralBean().setArchiveCreatorNum(view.getOAtxt().getText());
		//4
		model.getGeneralBean().setDelivGov(view.getLMtxt().getText());
		//5
		model.getGeneralBean().setDelivGovNum(view.getOLMtxt().getText());
		//6
		model.getGeneralBean().setConsultantBur(view.getSKtxt().getText());
		//7
		model.getGeneralBean().setContactDelivPerson(view.getKFLtxt().getText());
		//8
		model.getGeneralBean().setTelContactPerson(view.getTTKtxt().getText());
		//9
		model.getGeneralBean().setEmail(view.getEKtxt().getText());
		//10
		model.getGeneralBean().setArchiveName(view.getANtxt().getText());
		//11
		model.getGeneralBean().setSystemName(view.getSNtxt().getText());
		//12
		if(view.getDatePicker().getValue() == null)
		{
			System.out.println("No value at date");
			model.getGeneralBean().setDate("");
		}
		else
		{
			model.getGeneralBean().setDate(view.getDatePicker().getValue().toString());
		}
		//13
		model.getGeneralBean().setComment(view.getKOMtxt().getText());


	}



	private void createButton(ActionEvent event) {
		boolean check = new File(model.getTargetexcelFilepath(), model.getExcelFileName()).exists();
		if(!check) {
			//model.init();
			//setAlert();
			progressBar();
			view.getOpenTxtField().setText("");
			view.getSaveTxtField().setText("");
		}
		else if(check){
			//model.init();
			//setAlert();
			progressBar();
			view.getOpenTxtField().setText("");
			view.getSaveTxtField().setText("");
		}


	}

	private void setAlert() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("ArkivIT");
		alert.setHeaderText(null);
		alert.setContentText("File was successfully created");
		alert.showAndWait();
	}

	private void saveButton(ActionEvent event, Stage stage) {
		FileChooser fileChooser = new FileChooser();
		String fileName = "";
		fileChooser.setTitle("VA");
		//Set extension filter to .xlsx files
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel files (*.xls)", "*.xls");
		fileChooser.getExtensionFilters().add(extFilter);

		//Show save file dialog
		File file = fileChooser.showSaveDialog(stage);

		//If file is not null, write to file using output stream.
		if (file != null) {
			model.setTargetexcelFilepath(file.getParent());

			System.out.println("PATH : " + file.getParent());

			model.setExcelFileName(file.getName());

			fileName = file.getName();
			System.out.println(fileName);

			view.getSaveTxtField().setText(model.getTargetexcelFilepath());
			view.getBtnConvert().setDisable(false);
		}
	}


	public void openButton(ActionEvent e, Stage stage) {
		File selectedDir = view.getDirectoryChooser().showDialog(stage);
		String path;

		if(selectedDir != null) {
			view.getBtnSaveAs().setDisable(false);
			model.setSourceFolderPath(selectedDir.getAbsolutePath());
			view.getOpenTxtField().setText(model.getSourceFolderPath());
			path = selectedDir.getAbsolutePath();
			System.out.println(path);
			view.getBtnSaveAs().setDisable(false);

		}
		if(selectedDir == null) {
			//view.getOpenTxtField().setText("");
			//view.getBtnSaveAs().setDisable(true);
		}

		if(e.getSource() == view.getBtnOpenFile() && selectedDir != null) {
			view.getBtnSaveAs().setDisable(false);
		}
	}

	public boolean checkRequestedFields()
	{

		boolean checkFields = true;
		int emptyFields = 0;

		for(int i = 0; i < view.getMandatoryFieldsList().size(); i++)
		{	
			if(view.getMandatoryFieldsList().get(i).getText().isEmpty()) {
				view.getMandatoryFieldsList().get(i).setId("error");
				emptyFields++;


			}
			else
			{
				view.getMandatoryFieldsList().get(i).setId("");
			}
		}

		if(emptyFields >= 1)
		{
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("Please fill all requested fields");
			alert.setHeaderText(null);
			alert.showAndWait();

			checkFields = false;
		}

		return checkFields;

	}


	private boolean validateEmail() {
		Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
		Matcher m = p.matcher(view.getEKtxt().getText());
		if(m.find() && m.group().equals(view.getEKtxt().getText())) {
			view.getEKtxt().setId("");
			return true;
		}
		else {
			view.getEKtxt().setId("error");
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Not a valid email adress");
			alert.setHeaderText(null);
			alert.setContentText("Please enter a valid email form");
			alert.showAndWait();

			return false;
		}
	}
	public void progressBar() {

		Task<?> progressTask = getProgress();
		
		view.getPb().setVisible(true);
		view.getPb().setProgress(0);
		
		
		
		view.getPb().progressProperty().unbind();
		view.getPb().progressProperty().bind(progressTask.progressProperty());
		
		
		progressTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent arg0) {
				setAlert();
				view.getPb().setVisible(false);
				stage.setScene(view.getScene());
				view.resetTextField();
				
			}
			
		});
		

		//Start Thread
		Thread loadingThread = new Thread(progressTask);
		loadingThread.start();
		


	}

	private Task<?> getProgress() {
		
		return new Task<Object>() {
            @Override
            protected Object call() throws Exception {
            	model.init();
                for (int i = 0; i < model.getFileNameList().size(); i++) {
                    Thread.sleep(200);
                   // updateMessage("2000 milliseconds");
                    updateProgress(i + 1,model.getFileNameList().size());
                }
                return true;
            }
        };
	}

	class ActionListen implements EventHandler<ActionEvent>
	{

		@Override
		public void handle(ActionEvent event) {

			if(event.getSource().equals(view.getSaveButton()))
			{
				saveContentButton();
				//view.startSecondScene();

				//if(checkRequestedFields() && validateEmail() == true) {
				stage.setScene(view.getSecondScene());
				//view.getBALtxt().getStyleClass().remove("error");
				//}

				view.getBtnConvert().setDisable(true);
				view.getBtnSaveAs().setDisable(true);
			}
			else if(event.getSource().equals(view.getBtnOpenFile()))
			{
				openButton(event, stage);
			}
			else if(event.getSource().equals(view.getBtnSaveAs()))
			{
				saveButton(event, stage);
			}
			else if(event.getSource().equals(view.getBtnConvert()))
			{

				createButton(event);
				

			}
			else if(event.getSource().equals(view.getBtnBack())){
				stage.setScene(view.getScene());

			}

		}

	}



	@SuppressWarnings("unused")
	private void junkCodes()
	{
		/*public void saveButton(ActionEvent e) {
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
	}*/

		/*public void createButton(ActionEvent e) {
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

		/*public void firstScene(Stage stage) {


		//done button	
		view.getSaveButton().setOnAction((event) -> {
			doneButton();
			view.startSecondScene();
			stage.setScene(view.getSecondScene());
			secondSceneEventHandler(stage);			
		});


	}

	private void secondSceneEventHandler(Stage stage) {
		//select folder button
		view.getBtnSaveAs().setDisable(true);
		view.getBtnConvert().setDisable(true);

		view.getBtnOpenFile().setOnAction((event) -> {
			openButton(event, stage);

		});

		view.getBtnSaveAs().setOnAction((event) -> {
			saveButton(event,stage);
		});

		view.getBtnConvert().setOnAction((event) -> {
			createButton(event);
		});

	}


	}*/


		/* if(view.getBALtxt().getText().isEmpty() || view.getAKtxt().getText().isEmpty() || view.getOAtxt().getText().isEmpty() || 
		   view.getLMtxt().getText().isEmpty() || view.getOLMtxt().getText().isEmpty() ||
		   view.getKFLtxt().getText().isEmpty() || view.getTTKtxt().getText().isEmpty() || view.getEKtxt().getText().isEmpty() ||
		   view.getANtxt().getText().isEmpty() || view.getSNtxt().getText().isEmpty()){
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Error");
      alert.setContentText("Please fill all requested fields");
      alert.setHeaderText(null);
      alert.showAndWait();
     // view.getBALtxt().getStyleClass().add("error");
		//view.getAKtxt().getStyleClass().add("error");
      checkFields = false;
  }*/

		/* if(!(view.getBALtxt().getText().isEmpty() || view.getAKtxt().getText().isEmpty() || 
			view.getOAtxt().getText().isEmpty() || 
			view.getLMtxt().getText().isEmpty() || 
			view.getOLMtxt().getText().isEmpty() ||
			view.getKFLtxt().getText().isEmpty() ||
			view.getTTKtxt().getText().isEmpty() || view.getEKtxt().getText().isEmpty() ||
			view.getANtxt().getText().isEmpty() || view.getSNtxt().getText().isEmpty())) {
		System.out.println("NOT EMPTY");
		view.getBALtxt().getStyleClass().remove("error");
		view.getAKtxt().getStyleClass().remove("error");
		//view.getBALtxt().setText("");
		checkFields = false;

	} */

	}

}

