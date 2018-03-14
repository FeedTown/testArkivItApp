package com.arkivit.controller;


import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.View;

import com.arkivit.model.MappingLog;
import com.arkivit.model.MetadataToExcelGUI;
import com.arkivit.view.SecondScene;
import com.arkivit.view.FirstScene;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import jxl.write.Label;

/**
 * 
 * @author Kevin Olofsson, Roberto Blanco, Saikat Talukder
 * Class that connects the model with the view
 *
 */

public class ExcelControllerFX extends Application {

	private MetadataToExcelGUI model;
	private MappingLog log;
	private SecondScene secondScene;
	private FirstScene firstScene;
	private Stage stage;
	private Thread loadingThread;
	private boolean mapping = false;
	private boolean overwrite = false;
	private Task<?> progressTask;
	private File backupDir;

	/**
	 * No args constructor with objects from classes MetadataToExcelGUI, MappingLog,  FirstScene and SecondScene
	 */
	public ExcelControllerFX()
	{
		model = new MetadataToExcelGUI();
		log = new MappingLog(model);
		firstScene = new FirstScene();
		secondScene = new SecondScene();
		//launch();
	}

	/**
	 * Args constructor with following parameters
	 * @param model object of MetadataToExcelGUI
	 * @param view object of ExcelAppGUIFX
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public ExcelControllerFX(MetadataToExcelGUI model,FirstScene firstScene, SecondScene secondScene) 
			throws InstantiationException, IllegalAccessException{

		this.model = model;
		this.firstScene = firstScene;
		this.secondScene = secondScene;

	}

	/**
	 * Starts the application
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		firstScene.startFirstScene();
		secondScene.startSecondScene();
		primaryStage.setTitle("ArkivIT");
		primaryStage.setResizable(false);
		this.stage = primaryStage;
		stage.setScene(firstScene.getFirstScene());
		stage.show();
		firstScene.addActionListenerForButton(new ActionListen());
		secondScene.addActionListenerForButton(new ActionListen());


	}


	/**
	 * Gets the users input from text fields from the first scene
	 */
	public void saveContentButton() {
		//1
		model.getGeneralBean().setDescDelivery(firstScene.getBALtxt().getText());
		//2
		model.getGeneralBean().setArchiveCreator(firstScene.getAKtxt().getText());
		//3
		model.getGeneralBean().setArchiveCreatorNum(firstScene.getOAtxt().getText());
		//4
		model.getGeneralBean().setDelivGov(firstScene.getLMtxt().getText());
		//5
		model.getGeneralBean().setDelivGovNum(firstScene.getOLMtxt().getText());
		//6
		model.getGeneralBean().setConsultantBur(firstScene.getSKtxt().getText());
		//7
		model.getGeneralBean().setContactDelivPerson(firstScene.getKFLtxt().getText());
		//8
		model.getGeneralBean().setTelContactPerson(firstScene.getTTKtxt().getText());
		//9
		model.getGeneralBean().setEmail(firstScene.getEKtxt().getText());
		//10
		model.getGeneralBean().setArchiveName(firstScene.getANtxt().getText());
		//11
		model.getGeneralBean().setSystemName(firstScene.getSNtxt().getText());
		//12
		if(firstScene.getDatePicker().getValue() == null)
		{
			model.getGeneralBean().setDate("");
		}
		else
		{
			model.getGeneralBean().setDate(firstScene.getDatePicker().getValue().toString());
		}
		//13
		model.getGeneralBean().setComment(firstScene.getKOMtxt().getText());

	}


	/**
	 * Actions that performs to create the excel file
	 * @param event
	 */
	private void createButton(ActionEvent event){
		boolean check = new File(model.getTargetexcelFilepath(), model.getExcelFileName()).exists();
		if(!check) {
			progressBar();
			secondScene.getOpenTxtField().setText("");
			secondScene.getSaveTxtField().setText("");
			model.clearArrayList();
			secondScene.getBtnConvert().setDisable(true);
		}
		else if(check){
			progressBar();
			secondScene.getOpenTxtField().setText("");
			secondScene.getSaveTxtField().setText("");

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
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel files (*.xlsx)", "*.xlsx");
		fileChooser.getExtensionFilters().add(extFilter);

		//Show save file dialog
		File file = fileChooser.showSaveDialog(stage);

		//If file is not null, write to file using output stream.
		if (file != null) {
			model.setTargetexcelFilepath(file.getParent());

			//System.out.println("PATH : " + file.getParent());

			model.setExcelFileName(file.getName());

			fileName = file.getName();
			//System.out.println(fileName);

			secondScene.getSaveTxtField().setText(model.getTargetexcelFilepath());
			secondScene.getBtnConvert().setDisable(false);
			secondScene.getMappCheckBox().setDisable(true);
		}
		else if(file == null) {
			secondScene.getSaveTxtField().setText("");
			secondScene.getBtnConvert().setDisable(true);
			secondScene.getMappCheckBox().setDisable(false);
		}
	}

	private void overwriteButton(ActionEvent event, Stage stage) {
		//model.setBackupFilePath();
		backupDir = secondScene.getDirectoryChooser().showDialog(stage);
		if(backupDir != null) {
			secondScene.getBtnSaveAs().setDisable(false);
			model.setBackupFilePath(backupDir.getAbsolutePath());
			secondScene.getBtnSaveAs().setDisable(false);
			secondScene.getBtnDelete().setDisable(true);


			if(event.getSource() == secondScene.getBtnOverwrite() && backupDir != null) {
				secondScene.getOverwriteCheckBox().setDisable(true);
				secondScene.getBtnDelete().setDisable(false);
			}


		}
	}

	private void deleteButton(ActionEvent event) {
		backupDir = null;

		if(backupDir == null) {
			secondScene.getBtnDelete().setDisable(true);
			secondScene.getOverwriteCheckBox().setDisable(false);
			secondScene.getBtnSaveAs().setDisable(true);
		}
	}



	private void checkBox() {
		if(secondScene.getMappCheckBox().isSelected()) {
			secondScene.getOverwriteCheckBox().setDisable(false);
			secondScene.getBtnOverwrite().setDisable(false);
			secondScene.getBtnSaveAs().setDisable(true);
		}
		else {
			secondScene.getOverwriteCheckBox().setDisable(true);
			secondScene.getOverwriteCheckBox().setSelected(false);
			secondScene.getBtnOverwrite().setDisable(true);
			secondScene.getBtnSaveAs().setDisable(false);

		}
	}
	private void checkBox2() {
		if(secondScene.getOverwriteCheckBox().isSelected()) {
			secondScene.getBtnOverwrite().setDisable(true);
			secondScene.getBtnSaveAs().setDisable(false);
		}
		else if(!secondScene.getOverwriteCheckBox().isSelected()) {
			secondScene.getBtnSaveAs().setDisable(true);
			secondScene.getBtnOverwrite().setDisable(false);
		}

	}
	private void confidentialBox() {
		if(secondScene.getConfidentialCheckBox().isSelected()) {
			model.setConfidentialChecked("NEJ");
			//tempLabel.equals(model.getConfidentialityColl());
			//model.getConfidentialityColl().equals(new Label(7, model.getRowNum()+1, "JA"));
		}
		else {
			model.setConfidentialChecked("");
		}
	}

	private void personalDataBox() {
		if(secondScene.getPersonalDataBox().isSelected()) {
			model.setPersonalDataChecked("NEJ");
			//tempLabel.equals(model.getConfidentialityColl());
			//model.getConfidentialityColl().equals(new Label(7, model.getRowNum()+1, "JA"));
		}
		else {
			model.setPersonalDataChecked("");
		}
	}

	/**
	 * Action that performs for selecting folder
	 * @param e
	 * @param stage
	 */
	public void openButton(ActionEvent e, Stage stage) {
		File selectedDir = secondScene.getDirectoryChooser().showDialog(stage);
		String path;

		if(selectedDir != null) {
			secondScene.getBtnSaveAs().setDisable(false);
			model.setSourceFolderPath(selectedDir.getAbsolutePath());
			secondScene.getOpenTxtField().setText(model.getSourceFolderPath());
			path = selectedDir.getAbsolutePath();
			//System.out.println(path);
			//view.getBtnSaveAs().setDisable(false);

		}
		if(selectedDir == null) {
			//view.getOpenTxtField().setText("");
			//view.getBtnSaveAs().setDisable(true);
		}

		if(e.getSource() == secondScene.getBtnOpenFile() && selectedDir != null) {
			secondScene.getBtnSaveAs().setDisable(false);
			secondScene.getMappCheckBox().setDisable(false);
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
		for(int i = 0; i < firstScene.getMandatoryFieldsList().size(); i++)
		{	
			if(firstScene.getMandatoryFieldsList().get(i).getText().isEmpty()) {
				firstScene.getMandatoryFieldsList().get(i).setId("error");
				emptyFields++;
			}
			else
			{
				firstScene.getMandatoryFieldsList().get(i).setId("");
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
		Matcher m = p.matcher(firstScene.getEKtxt().getText());
		if(m.find() && m.group().equals(firstScene.getEKtxt().getText())) {
			firstScene.getEKtxt().setId("");
			return true;
		}
		else {
			firstScene.getEKtxt().setId("error");
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
		for(int i = 0; i < firstScene.validateNumberList().size(); i++)
		{	
			if(firstScene.validateNumberList().get(i).getText().matches("[0-9-]*")) {
				firstScene.validateNumberList().get(i).setId("");

			}
			else
			{
				firstScene.validateNumberList().get(i).setId("error");
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

		secondScene.getPi().setVisible(true);
		secondScene.getWaitLabel().setVisible(true);
		//secondScene.getPb().setProgress(0);

		//secondScene.getPb().progressProperty().bind(progressTask.progressProperty());

		progressTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent arg0) {

				setAlert();
				//secondScene.getPb().setVisible(false);
				stage.setScene(firstScene.getFirstScene());
				firstScene.resetTextField();
				secondScene.getMappCheckBox().setDisable(true);
				secondScene.getMappCheckBox().setSelected(false);
				//secondScene.getPb().progressProperty().unbind();
				secondScene.getOverwriteCheckBox().setSelected(false);
				secondScene.getOverwriteCheckBox().setDisable(true);
				secondScene.getBtnOverwrite().setDisable(true);
				secondScene.getBtnSaveAs().setDisable(true);
				secondScene.getBtnDelete().setDisable(true);
				secondScene.getConfidentialCheckBox().setSelected(false);
				secondScene.getPersonalDataBox().setSelected(false);
				mapping = false;
				overwrite = false;
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
				if(secondScene.getMappCheckBox().isSelected()) {
					mapping = true;
					if(secondScene.getOverwriteCheckBox().isSelected())
					{
						overwrite = true;
					}
				}


				model.init(mapping, overwrite);

				if(mapping || overwrite)
				{
					log.mappedLog();
				} 


				secondScene.getPi().setVisible(false);
				secondScene.getWaitLabel().setVisible(false);
				//secondScene.getPb().setVisible(true);
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

			if(event.getSource().equals(firstScene.getSaveButton()))
			{
				saveContentButton();

				if(checkRequestedFields() && validateEmail() && validateNumbers() == true) 
				{

					stage.setScene(secondScene.getSecondScene());
					firstScene.getBALtxt().getStyleClass().remove("error");

				}

				/*view.getBtnOverwrite().setDisable(true);HI
				view.getCheckBox().setDisable(true);
				view.getOverwriteCheckBox().setDisable(true);
				view.getBtnConvert().setDisable(true);
				view.getBtnSaveAs().setDisable(true);*/


			}
			else if(event.getSource().equals(secondScene.getBtnOpenFile()))
			{
				openButton(event, stage);
			}
			else if(event.getSource().equals(secondScene.getBtnSaveAs()))
			{
				saveButton(event, stage);
			}
			else if(event.getSource().equals(secondScene.getBtnConvert()))
			{
				createButton(event);
			}
			else if(event.getSource().equals(secondScene.getBtnBack())){
				stage.setScene(firstScene.getFirstScene());
			}
			else if(event.getSource().equals(secondScene.getMappCheckBox())) {
				checkBox();
			}
			else if(event.getSource().equals(secondScene.getOverwriteCheckBox())) {
				checkBox2();
			}
			else if(event.getSource().equals(secondScene.getBtnOverwrite())) {
				overwriteButton(event, stage);

			}
			else if(event.getSource().equals(secondScene.getBtnDelete())) {
				deleteButton(event);
			}
			else if(event.getSource().equals(secondScene.getConfidentialCheckBox())) {
				confidentialBox();
			}
			else if(event.getSource().equals(secondScene.getPersonalDataBox())) {
				personalDataBox();
			}

		}

	}

}

