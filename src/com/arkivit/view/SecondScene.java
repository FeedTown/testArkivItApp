package com.arkivit.view;

import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;

/**
 * 
 * @author Kevin Olofsson, Roberto Blanco, Saikat Talukder
 * The view of the application
 *
 */

public class SecondScene{
	
	private GridPane firstGrid, secondGrid;
	private Scene secondScene;
	private Button btnOpenFile;
	private Button btnOverwrite;
	private Button btnSaveAs;
	private Button btnConvert;
	private Button btnBack, btnDelete;
	private TextField openTxtField;// = new TextField();
	private TextField saveTxtField; //= new TextField();
	private Label dirLabel;// = new Label("Directory");
	private Label outputLabel;// = new Label("Output");
	private Label mapLabel;// = new Label("Map");
	private Label overwriteLabel;
	private CheckBox mappCheckBox;
	private CheckBox overwriteCheckBox;
	private DirectoryChooser directoryChooser = new DirectoryChooser();
	private ProgressBar pb;
	private ProgressIndicator pi;
	
	
	/**
	 * Adds all the components for the second scene to the second scene
	 */
	public void startSecondScene()
	{
		VBox root2 = new VBox();
		openTxtField = new TextField();
		saveTxtField = new TextField();
		dirLabel = new Label("Directory");
		outputLabel = new Label("Output");
		mapLabel = new Label("Map");
		mappCheckBox = new CheckBox("");
		mappCheckBox.setDisable(true);
		final Tooltip tooltip = new Tooltip();
		tooltip.setText(
		    "Replacing Illegal characters 'å, ä, ö, ü with aa, ae, oe, ue'\n" +
		    "and copies the original content to a backup folder. \n"  
		);
		mappCheckBox.setTooltip(tooltip);
		
		overwriteLabel = new Label("Overwrite");
		overwriteCheckBox = new CheckBox("");
		overwriteCheckBox.setDisable(true);
		final Tooltip tooltip2 = new Tooltip();
		tooltip2.setText(
		    "By checking overwrite you will overwrite \n" +
		    "and replace the original content. \n"  
		);
		overwriteCheckBox.setTooltip(tooltip2);
		btnOpenFile = new Button("Select folder...");
		btnOpenFile.setId("saveButton");
		btnOpenFile.setMaxWidth(Double.MAX_VALUE);
		btnOverwrite = new Button("Select Directory...");
		btnOverwrite.setDisable(true);
		btnOverwrite.setId("saveButton");
		btnOverwrite.setMaxWidth(Double.MAX_VALUE);
		btnDelete = new Button("X");
		btnDelete.setId("deleteButton");
		btnDelete.setDisable(true);
		btnDelete.setMaxWidth(Double.MAX_VALUE);
		final Tooltip tooltip4 = new Tooltip();
		tooltip4.setText(
		    "Remove the chosen directory for the backup content"
		);
		tooltip4.setId("tooltip");
		btnDelete.setTooltip(tooltip4);
		final Tooltip tooltip3 = new Tooltip();
		tooltip3.setText(
		    "Choose the directory where you want to save the  \n" +
		    "original content. \n"  
		);
		btnOverwrite.setTooltip(tooltip3);
		btnSaveAs = new Button("Save As...");
		btnSaveAs.setDisable(true);
		btnSaveAs.setId("saveButton");
		btnSaveAs.setMaxWidth(Double.MAX_VALUE);
		btnConvert = new Button("Create");
		btnConvert.setDisable(true);
		btnConvert.setId("saveButton");
		pb = new ProgressBar(0);
		pb.setMaxWidth(Double.MAX_VALUE);
		pi = new ProgressIndicator();
		btnBack = new Button("◀ Back");
		btnBack.setId("saveButton");
		
		firstGrid = new GridPane();
		firstGrid.setAlignment(Pos.CENTER);
		firstGrid.setHgap(10);
		firstGrid.setVgap(10);
		firstGrid.setPadding(new Insets(200, 200, 100, 200));
		
		
		
		secondGrid = new GridPane();
		secondGrid.setAlignment(Pos.BASELINE_LEFT);
		secondGrid.setHgap(10);
		secondGrid.setVgap(10);
		secondGrid.setPadding(new Insets(15, 0, 0, 15));
		HBox hBox = new HBox(mapLabel, mappCheckBox);
		hBox.setAlignment(Pos.CENTER_LEFT);
		HBox.setMargin(mappCheckBox,new Insets(10,10,10,10));
		HBox hBox2 = new HBox(overwriteLabel, overwriteCheckBox);
		hBox2.setAlignment(Pos.CENTER_LEFT);
		HBox.setMargin(overwriteCheckBox,new Insets(10,10,10,10));
		HBox hBox3 = new HBox(btnOverwrite, btnDelete);
		hBox3.setAlignment(Pos.CENTER_RIGHT);
		
		

		//Open dir components
		firstGrid.add(dirLabel, 0, 0);
		firstGrid.add(openTxtField, 1, 0);
		openTxtField.setEditable(false);
		firstGrid.add(btnOpenFile, 2, 0);

		//mapp
		firstGrid.add(hBox, 0, 1);
		
		//overwrite
		firstGrid.add(hBox2, 1, 1);
		firstGrid.add(hBox3, 2, 1);
		//firstGrid.add(btnOverwrite, 2, 1);

		//Out dir components
		firstGrid.add(outputLabel, 0, 2);
		firstGrid.add(saveTxtField, 1, 2);
		saveTxtField.setEditable(false);
		firstGrid.add(btnSaveAs, 2, 2);
		
		//Create Excel button
		firstGrid.add(btnConvert, 1, 3);
		
		firstGrid.add(pb, 1, 5);
		pb.setVisible(false);
		
		pi.setMinSize(80, 80);
		firstGrid.add(pi, 1, 5);
		pi.setVisible(false);
		
		//back button
		secondGrid.add(btnBack, 0, 0);
		
		root2.getChildren().add(firstGrid);
		root2.getChildren().add(secondGrid);
		secondScene = new Scene(root2, 800, 620);
		secondScene.getStylesheets().add("resources/style/style.css");
	}
	
	
	/**
	 * Adds action listeners to all the buttons in second scene
	 * @param listenForEvent
	 */
	public void addActionListenerForButton(EventHandler<ActionEvent> listenForEvent)
	{
		btnOpenFile.setOnAction(listenForEvent);
		btnSaveAs.setOnAction(listenForEvent);
		btnConvert.setOnAction(listenForEvent);
		btnBack.setOnAction(listenForEvent);
		btnOverwrite.setOnAction(listenForEvent);
		mappCheckBox.setOnAction(listenForEvent);
		overwriteCheckBox.setOnAction(listenForEvent);
		btnDelete.setOnAction(listenForEvent);
	}
		
	/**
	 * Getters and setters for variables
	 * @return s all the variables that have getters
	 */
	public Button getBtnOpenFile() {
		return btnOpenFile;
	}

	
	public void setBtnOpenFile(Button btnOpenFile) {
		this.btnOpenFile = btnOpenFile;
	}

	public Button getBtnSaveAs() {
		return btnSaveAs;
	}

	public void setBtnSaveAs(Button btnSaveAs) {
		this.btnSaveAs = btnSaveAs;
	}

	public Button getBtnConvert() {
		return btnConvert;
	}

	public void setBtnConvert(Button btnConvert) {
		this.btnConvert = btnConvert;
	}

	public TextField getOpenTxtField() {
		return openTxtField;
	}

	public void setOpenTxtField(TextField openTxtField) {
		this.openTxtField = openTxtField;
	}

	public TextField getSaveTxtField() {
		return saveTxtField;
	}

	public void setSaveTxtField(TextField saveTxtField) {
		this.saveTxtField = saveTxtField;
	}

	public DirectoryChooser getDirectoryChooser() {
		return directoryChooser;
	}

	public Scene getSecondScene() {
		return secondScene;
	}

	public Button getBtnBack() {
		return btnBack;
	}

	public void setBtnBack(Button btnBack) {
		this.btnBack = btnBack;
	}

	public ProgressBar getPb() {
		return pb;
	}

	public void setPb(ProgressBar pb) {
		this.pb = pb;
	}

	public ProgressIndicator getPi() {
		return pi;
	}

	public void setPi(ProgressIndicator pi) {
		this.pi = pi;
	}

	public CheckBox getMappCheckBox() {
		return mappCheckBox;
	}

	public void setMappCheckBox(CheckBox checkBox) {
		this.mappCheckBox = checkBox;
	}
	public Button getBtnOverwrite() {
		return btnOverwrite;
	}
	public void setBtnOverwrite(Button btnOverwrite) {
		this.btnOverwrite = btnOverwrite;
	}
	public CheckBox getOverwriteCheckBox() {
		return overwriteCheckBox;
	}
	public void setOverwriteCheckBox(CheckBox overwriteCheckBox) {
		this.overwriteCheckBox = overwriteCheckBox;
	}
	public Button getBtnDelete() {
		return btnDelete;
	}
	public void setBtnDelete(Button btnDelete) {
		this.btnDelete = btnDelete;
	}
	
	
}
