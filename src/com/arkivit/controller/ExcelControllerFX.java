package com.arkivit.controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.arkivit.model.MappingLog;
import com.arkivit.model.MetadataToExcelGUI;
import com.arkivit.model.db.FactorySessionSingleton;
import com.arkivit.model.db.Webbleveranser;
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
	File file;

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
		model.clearArrayList();
		if(!check) {
			progressBar();
			secondScene.getOpenTxtField().setText("");
			secondScene.getSaveTxtField().setText("");
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
	private void setAlert() 
	{
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
		file = fileChooser.showSaveDialog(stage);

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
	private void confidentialYesBox() {
		if(secondScene.getConfidentialYesBox().isSelected()) {
			secondScene.getConfidentialCheckBox().setSelected(false);
			model.setConfidentialChecked("JA");
		}
		else {
			model.setConfidentialChecked("");
		}
	}

	private void confidentialBox() {

		if(secondScene.getConfidentialCheckBox().isSelected()) {
			secondScene.getConfidentialYesBox().setSelected(false);
			model.setConfidentialChecked("NEJ");
		}
		else {
			model.setConfidentialChecked("");
		}
	}

	private void personalDataBox() {
		if(secondScene.getPersonalDataBox().isSelected()) {
			secondScene.getPersonalDataYesBox().setSelected(false);
			model.setPersonalDataChecked("NEJ");
		}
		else {
			model.setPersonalDataChecked("");
		}
	}

	private void personalDataYesBox() {
		if(secondScene.getPersonalDataYesBox().isSelected()) {
			secondScene.getPersonalDataBox().setSelected(false);
			model.setPersonalDataChecked("JA");
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
		/*if(selectedDir == null) {
			//view.getOpenTxtField().setText("");
			//view.getBtnSaveAs().setDisable(true);
		}*/

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
	public void progressBar(/*OnConvertFinish onFinish*/) {

		progressTask = getProgress();
		secondScene.getPi().setVisible(true);
		secondScene.getWaitLabel().setVisible(true);
		//secondScene.getPb().setProgress(0);

		//secondScene.getPb().progressProperty().bind(progressTask.progressProperty());

		progressTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent arg0) {
				
				//onFinish.proceed();
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
				secondScene.getConfidentialYesBox().setSelected(false);
				secondScene.getConfidentialCheckBox().setSelected(false);
				secondScene.getPersonalDataYesBox().setSelected(false);
				secondScene.getPersonalDataBox().setSelected(false);
				model.setConfidentialChecked("");
				model.setPersonalDataChecked("");
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

			//if(event.getSource().equals(firstScene.getSaveButton()))
			//{
			saveContentButton();

			//if(checkRequestedFields() && validateEmail() && validateNumbers() == true) 
			//{

			stage.setScene(secondScene.getSecondScene());
			//firstScene.getBALtxt().getStyleClass().remove("error");

			/*secondScene.getMappCheckBox().setDisable(true);
				secondScene.getOverwriteCheckBox().setDisable(true);
				secondScene.getBtnConvert().setDisable(true);
				secondScene.getBtnSaveAs().setDisable(true);*/


			//}
			if(event.getSource().equals(secondScene.getBtnOpenFile()))
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
			else if(event.getSource().equals(secondScene.getConfidentialYesBox())) {
				confidentialYesBox();
			}
			else if(event.getSource().equals(secondScene.getConfidentialCheckBox())) {
				confidentialBox();
			}
			else if(event.getSource().equals(secondScene.getPersonalDataYesBox())) {
				personalDataYesBox();
			}
			else if(event.getSource().equals(secondScene.getPersonalDataBox())) {
				personalDataBox();
			}
		}

	}
	
	//DB-management disabled because server is down.
	public void hibernateSession()  {
		
		/*
		 * Radera alla rader och nollställ auto increment: truncate ArkivIT.webbleveranser
		 */

		Session session = FactorySessionSingleton.getSessionFactoryInstance().getCurrentSession();
		
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Blob blob = Hibernate.getLobCreator(session).createBlob(inputStream, file.length());
		
		try 
		{
			
			//create a webleverans object
			System.out.println("Create a new object");
			Webbleveranser webLev = new Webbleveranser(firstScene.getLMtxt().getText() , blob, new Date());
			//start a transaction
			
			session.beginTransaction();
			
			//save the object
			System.out.println("Saving the object...");
			session.save(webLev);
			blob.free();
			//commit transaction
			session.getTransaction().commit();
			System.out.println("Done!");
			
			
			//Getting file from database
			webLev = null;
			session = FactorySessionSingleton.getSessionFactoryInstance().getCurrentSession();
			session.beginTransaction();
			webLev = (Webbleveranser) session.get(Webbleveranser.class, 1);
			blob = webLev.getExcelFile();
			byte[] blobBytes = blob.getBytes(1, (int) blob.length());
			saveBytesToFile(file.getParentFile().getAbsolutePath()+"\\Blob_"+file.getName(), blobBytes);
			blob.free();
			
			//session.close();
	
		}
		/*finally
		{
			//System.out.println("Factory is about to close.....");
			System.out.println("Session is about to close...");
			//factory.close();
			//session.close();
			System.out.println("Session is closed"); 
			//FactorySessionSingleton.getSessionFactoryInstance().close();
			//System.out.println("Factory is closed!");
		}*/
		catch(HibernateException e) 
		{
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void saveBytesToFile(String path, byte[] blobBytes) throws IOException {
		FileOutputStream outputStream = new FileOutputStream(path);
        outputStream.write(blobBytes);
        outputStream.close();
	}


}

