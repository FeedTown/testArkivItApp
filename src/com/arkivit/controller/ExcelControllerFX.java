package com.arkivit.controller;


import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.arkivit.model.MetadataToExcelGUI;
import com.arkivit.view.ExcelAppGUIFX;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * 
 * @author Kevin Olofsson, Roberto Blanco, Saikat Talukder
 * Class that connects the model with the view
 *
 */

public class ExcelControllerFX extends Application {

	private MetadataToExcelGUI model;
	private ExcelAppGUIFX view;
	private Stage stage;
	private Thread loadingThread;
	private boolean mapping = false;
	private boolean overwrite = false;
	private Task<?> progressTask;
	private File backupDir;

	/**
	 * No args constructor with objects from MetadataToExcelGUI and ExcelAppGUIFX
	 */
	public ExcelControllerFX()
	{
		model = new MetadataToExcelGUI();
		view = new ExcelAppGUIFX();
		//launch();
	}
	
	/**
	 * Args constructor with following parameters
	 * @param model object of MetadataToExcelGUI
	 * @param view object of ExcelAppGUIFX
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public ExcelControllerFX(MetadataToExcelGUI model, ExcelAppGUIFX view) throws InstantiationException, IllegalAccessException{

		this.model = model;
		this.view = view;
		//this.view.start();
	}

	/**
	 * Starts the application
	 */
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


	/**
	 * Gets the users input from text fields from the first scene
	 */
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
	
	private void setDefault() {
		view.getOpenTxtField().setText("");
		view.getSaveTxtField().setText("");
		view.getCheckBox().setDisable(true);
		view.getCheckBox().setSelected(false);
		view.getCheckBox2().setDisable(true);
		view.getCheckBox2().setSelected(false);
		view.getBtnSaveAs().setDisable(true);
		view.getBtnDelete().setDisable(true);
		view.getBtnOverwrite().setDisable(true);
		view.getBtnConvert().setDisable(true);
	}

	/**
	 * Actions that performs to create the excel file
	 * @param event
	 */
	private void createButton(ActionEvent event){
		boolean check = new File(model.getTargetexcelFilepath(), model.getExcelFileName()).exists();
		if(!check) {
			//model.init();
			//setAlert();
			progressBar();
			//stopThread();
			setDefault();
		}
		else if(check){
			//model.init();
			//setAlert();
			progressBar();
			setDefault();
		}


	}
	/**
	 * Alert popup message for succeded excel file creation
	 */
	private void setAlert() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("ArkivIT");
		alert.setHeaderText(null);
		alert.setContentText("File was successfully created");
		alert.showAndWait();
	}
	/**
	 * Action that performs to save content in directory
	 * @param event
	 * @param stage
	 */
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
			view.getCheckBox().setDisable(true);
		}
		else if(file == null) {
			view.getSaveTxtField().setText("");
			view.getBtnConvert().setDisable(true);
			view.getCheckBox().setDisable(false);
		}
	}
	
	private void overwriteButton(ActionEvent event, Stage stage) {
		//model.setBackupFilePath();
		backupDir = view.getDirectoryChooser().showDialog(stage);
		if(backupDir != null) {
			view.getBtnSaveAs().setDisable(false);
			model.setBackupFilePath(backupDir.getAbsolutePath());
			view.getBtnSaveAs().setDisable(false);
			view.getBtnDelete().setDisable(true);
			
			
			if(event.getSource() == view.getBtnOverwrite() && backupDir != null) {
				view.getCheckBox2().setDisable(true);
				view.getBtnDelete().setDisable(false);
			}
			

		}
	}
	
	private void deleteButton(ActionEvent event) {
		backupDir = null;
		
		if(backupDir == null) {
			view.getBtnDelete().setDisable(true);
			view.getCheckBox2().setDisable(false);
			view.getBtnSaveAs().setDisable(true);
		}
	}
	
	
	
	private void checkBox() {
		if(view.getCheckBox().isSelected()) {
			view.getCheckBox2().setDisable(false);
			view.getBtnOverwrite().setDisable(false);
			view.getBtnSaveAs().setDisable(true);
		}
		else {
			view.getCheckBox2().setDisable(true);
			view.getCheckBox2().setSelected(false);
			view.getBtnOverwrite().setDisable(true);
			view.getBtnSaveAs().setDisable(false);
			
		}
	}
	private void checkBox2() {
		if(view.getCheckBox2().isSelected()) {
			view.getBtnOverwrite().setDisable(true);
			view.getBtnSaveAs().setDisable(false);
		}
		else if(!view.getCheckBox2().isSelected()) {
			view.getBtnSaveAs().setDisable(true);
			view.getBtnOverwrite().setDisable(false);
		}
		
	}

	/**
	 * Action that performs for selecting folder
	 * @param e
	 * @param stage
	 */
	public void openButton(ActionEvent e, Stage stage) {
		File selectedDir = view.getDirectoryChooser().showDialog(stage);
		String path;

		if(selectedDir != null) {
			view.getBtnSaveAs().setDisable(false);
			model.setSourceFolderPath(selectedDir.getAbsolutePath());
			view.getOpenTxtField().setText(model.getSourceFolderPath());
			path = selectedDir.getAbsolutePath();
			System.out.println(path);
			//view.getBtnSaveAs().setDisable(false);

		}
		if(selectedDir == null) {
			//view.getOpenTxtField().setText("");
			//view.getBtnSaveAs().setDisable(true);
		}

		if(e.getSource() == view.getBtnOpenFile() && selectedDir != null) {
			view.getBtnSaveAs().setDisable(false);
			view.getCheckBox().setDisable(false);
		}
	}
	
	/*
	 * Checks if mandatory field is empty or not.
	 * If empty a red border will be visual, an alert will pop up and user cannot
	 * go to the second scene. If !empty the user can go on to the second scene.
	 */
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
	
	public void setInfoAlert() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Not a valid number");
		alert.setHeaderText(null);
		alert.setContentText("This field only allows numbers");
		alert.showAndWait();
	}
	
	/**
	 * Validates email format in the first scene
	 * @return true or false
	 */
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
	
	/**
	 * Validates number format in first scene if specified fields have numbers or not
	 * @return true or false
	 */
	private boolean validateNumbers() {
		int notNumber = 0;
		boolean checkFieldsForNumbers = true;
		for(int i = 0; i < view.validateNumberList().size(); i++)
		{	
			if(view.validateNumberList().get(i).getText().matches("[0-9-]*")) {
				view.validateNumberList().get(i).setId("");
				
			}
			else
			{
				view.validateNumberList().get(i).setId("error");
				notNumber++;
				
			}
		}
		
		if(notNumber >= 1)
		{
			setInfoAlert();
			checkFieldsForNumbers = false;
		}
		return checkFieldsForNumbers;
	}
	
	/**
	 * Thread that runs simultaneously with Fx application thread to show loading progress
	 * Tasks performed and bound/unbound to progress bar
	 * Actions performed when tasks are succeeded
	 * Mapping of illegal characters
	 */
	public void progressBar() {

		progressTask = getProgress();

		view.getPi().setVisible(true);
		view.getWaitLabel().setVisible(true);
		//view.getPb().setProgress(0);

		//view.getPb().progressProperty().bind(progressTask.progressProperty());

		progressTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent arg0) {

				setAlert();
				//view.getPb().setVisible(false);
				stage.setScene(view.getScene());
				view.resetTextField();
				view.getCheckBox().setDisable(true);
				view.getCheckBox().setSelected(false);
				//view.getPb().progressProperty().unbind();
				view.getCheckBox2().setSelected(false);
				view.getCheckBox2().setDisable(true);
				view.getBtnOverwrite().setDisable(true);
				view.getBtnSaveAs().setDisable(true);
				view.getBtnDelete().setDisable(true);
			}

		});


		//Start Thread

		//System.out.println("Thread was not alive.");
		loadingThread = new Thread(progressTask);
		loadingThread.start();



		//startThread(progressTask);

	}

	private Task<?> getProgress() {

		return new Task<Object>() {
			@Override
			protected Object call() throws Exception {
				if(view.getCheckBox().isSelected()) {
					mapping = true;
					if(view.getCheckBox2().isSelected())
					{
						overwrite = true;
					}
				}
				

				model.init(mapping, overwrite);
				view.getPi().setVisible(false);
				view.getWaitLabel().setVisible(false);
				//view.getPb().setVisible(true);
				//Thread.sleep(200);

				for (int i = 0; i < model.getFileListeLength(); i++) {
					// updateMessage("2000 milliseconds");
					Thread.sleep(20);
					updateProgress(i + 1,model.getFileListeLength());
				}

				return true;
			}
		};

	}
	
	/**
	 * 
	 * Action events on when buttons are clicked
	 *
	 */
	class ActionListen implements EventHandler<ActionEvent>
	{

		@Override
		public void handle(ActionEvent event) {

			if(event.getSource().equals(view.getSaveButton()))
			{
				saveContentButton();

				//if(checkRequestedFields() && validateEmail() && validateNumbers() == true) {

				stage.setScene(view.getSecondScene());
				//view.getBALtxt().getStyleClass().remove("error");

				//}
				
				/*view.getBtnOverwrite().setDisable(true);
				view.getCheckBox().setDisable(true);
				view.getCheckBox2().setDisable(true);
				view.getBtnConvert().setDisable(true);
				view.getBtnSaveAs().setDisable(true);*/
				
				
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
			else if(event.getSource().equals(view.getCheckBox())) {
				checkBox();
			}
			else if(event.getSource().equals(view.getCheckBox2())) {
				checkBox2();
			}
			else if(event.getSource().equals(view.getBtnOverwrite())) {
				overwriteButton(event, stage);
				
			}
			else if(event.getSource().equals(view.getBtnDelete())) {
				deleteButton(event);
			}

		}

	}

}

