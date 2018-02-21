package com.arkivit.view;

import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;

/**
 * 
 * @author Kevin Olofsson, Roberto Blanco, Saikat Talukder
 * The view of the application
 *
 */

public class ExcelAppGUIFX{
	
	private ArrayList<TextField> content; 


	private ArrayList<TextField> mandatoryFields;
	private ArrayList<TextField> validateFields;
	
	private GridPane grid,gridSecondScene, gridThirdScene;

	private Scene secondScene;
	private Label BAL;
	private TextField BALtxt;
	
	private Label AK;
	private TextField AKtxt;
	private TextField tmp;
 
	private Label OA;
	private TextField OAtxt;

	private Label LM;
	private TextField LMtxt;

	private Label OLM;
	private TextField OLMtxt;

	private Label SK;
	private TextField SKtxt;

	private Label KFL;
	private TextField KFLtxt;

	private Label TTK;
	private TextField TTKtxt ;

	private Label EK;
	private TextField EKtxt;

	private Label AN;
	private TextField ANtxt;

	private Label SN;
	private TextField SNtxt;

	private Label UD;
	private DatePicker datePicker;
	private TextField UDtxt;

	private Label KOM;
	private TextField KOMtxt; 

	private Button saveButton;
	private VBox root = new VBox();
	private Scene firstScene;
	private Button btnOpenFile;
	private Button btnOverwrite;
	private Button btnSaveAs;
	private Button btnConvert;
	private Button btnBack;
	private TextField openTxtField;// = new TextField();
	private TextField saveTxtField; //= new TextField();
	private Label dirLabel;// = new Label("Directory");
	private Label outputLabel;// = new Label("Output");
	private Label mapLabel;// = new Label("Map");
	private Label overwriteLabel;
	private CheckBox checkBox;
	private CheckBox checkBox2;
	private DirectoryChooser directoryChooser = new DirectoryChooser();
	private ProgressBar pb;
	private ProgressIndicator pi;
	
	/**
	 * No args constructor
	 */
	public ExcelAppGUIFX()
	{
		
	}
	/**
	 * Adds all components to the first scene.
	 */
	public void start() {
		saveButton = new Button("SAVE");
		saveButton.setId("saveButton");
		BAL = new Label("*Beskrivning av leverans:");
		AK = new Label("*Arkivbildare:");
		OA = new Label("*Organisationsnummer arkivbildare:");
		LM = new Label("*Levererande myndighet:");
	    OLM = new Label("*Organisationsnummer levererande myndighet:");
		KFL = new Label("*Kontaktperson för leverans:");
		SK = new Label("Servicebyrå/Konsult:");
		TTK = new Label("*Telefonnummer till kontaktperson:");
		EK = new Label("*E-post-adress till kontakperson:");
		AN = new Label("*Arkivets namn:");
		SN = new Label("*Systemts namn:");
		UD = new Label("Uttagsdatum:");
		KOM = new Label("Kommentar:");
		
		BALtxt = new TextField();
		AKtxt = new TextField();
		OAtxt = new TextField();
		LMtxt = new TextField();
		OLMtxt = new TextField();
		SKtxt = new TextField();
		KFLtxt = new TextField();
		TTKtxt = new TextField();
		EKtxt = new TextField();
		EKtxt.setPromptText("exempel@arkivit.se");
		ANtxt = new TextField();
		SNtxt = new TextField();
		//UDtxt = new TextField();
		datePicker = new DatePicker();
		KOMtxt = new TextField();
		addContentToList();

		//setUpGridPane(grid);
		//  scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		//grid.add(scenetitle, 0, 0, 2, 1);
		
		grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(93, 25, 25, 25));
		
		grid.add(BAL, 0, 1);
		grid.add(BALtxt, 1, 1);

		grid.add(AK, 0, 2);
		grid.add(AKtxt, 1, 2);

		grid.add(OA, 0, 3);
		grid.add(OAtxt, 1, 3);

		grid.add(LM, 0, 4);
		grid.add(LMtxt, 1, 4);

		grid.add(OLM, 0, 5);
		grid.add(OLMtxt, 1, 5);

		grid.add(SK, 0, 6);
		grid.add(SKtxt, 1, 6);

		grid.add(KFL, 0, 7);
		grid.add(KFLtxt, 1, 7);

		grid.add(TTK, 0, 8);
		grid.add(TTKtxt, 1, 8);

		grid.add(EK, 0, 9);
		grid.add(EKtxt, 1, 9);

		grid.add(AN, 0, 10);
		grid.add(ANtxt, 1, 10);

		grid.add(SN, 0, 11);
		grid.add(SNtxt, 1, 11);

		grid.add(UD, 0, 12);
		grid.add(datePicker, 1, 12);
		datePicker.setEditable(false);

		grid.add(KOM, 0, 13);
		grid.add(KOMtxt, 1, 13);

		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(saveButton);
		grid.add(hbBtn, 1, 14);
		
		root.getChildren().add(grid);
		
		
		//root.getChildren().add(hbBtn);
		
		firstScene = new Scene(root, 800, 620);
		firstScene.getStylesheets().add("resources/style/style.css");
		

	}

	/*private void setUpGridPane(GridPane grid) {
		grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

	}*/

	
	/**
	 * Adds all text fields to an array list
	 */
	private void addContentToList() {
		content = new ArrayList<TextField>();
		content.add(BALtxt);
		content.add(AKtxt);
		content.add(OAtxt);
		content.add(LMtxt);
		content.add(OLMtxt);
		content.add(SKtxt);
		content.add(KFLtxt);
		content.add(TTKtxt);
		content.add(EKtxt);
		content.add(ANtxt);
		content.add(SNtxt);
		content.add(KOMtxt);
		
		
	}
	
	/**
	 * Adds all the text fields that are mandatory to fill out, into an array list
	 * @return the mandatory text fields
	 */
	public ArrayList<TextField> getMandatoryFieldsList() {
		mandatoryFields = new ArrayList<TextField>();
		mandatoryFields.add(BALtxt);
		mandatoryFields.add(AKtxt);
		mandatoryFields.add(OAtxt);
		mandatoryFields.add(LMtxt);
		mandatoryFields.add(OLMtxt);
		mandatoryFields.add(KFLtxt);
		mandatoryFields.add(TTKtxt);
		mandatoryFields.add(EKtxt);
		mandatoryFields.add(ANtxt);
		mandatoryFields.add(SNtxt);
		return mandatoryFields;
		
	}
	
	public ArrayList<TextField> validateNumberList() {
		validateFields = new ArrayList<TextField>();
		validateFields.add(OAtxt);
		validateFields.add(OLMtxt);
		validateFields.add(TTKtxt);
		return validateFields;
	}
	
	
	
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
		checkBox = new CheckBox("");
		final Tooltip tooltip = new Tooltip();
		tooltip.setText(
		    "Replacing Illegal characters 'å, ä, ö, ü with aa, ae, oe, ue'\n" +
		    "and copies the original content to a backup folder. \n"  
		);
		checkBox.setTooltip(tooltip);
		overwriteLabel = new Label("Overwrite");
		checkBox2 = new CheckBox("");
		final Tooltip tooltip2 = new Tooltip();
		tooltip2.setText(
		    "By checking overwrite you will overwrite \n" +
		    "and replace the original content. \n"  
		);
		checkBox2.setTooltip(tooltip2);
		btnOpenFile = new Button("Select folder...");
		btnOpenFile.setId("saveButton");
		btnOpenFile.setMaxWidth(Double.MAX_VALUE);
		btnOverwrite = new Button("Select Directory...");
		btnOverwrite.setId("saveButton");
		btnOverwrite.setMaxWidth(Double.MAX_VALUE);
		final Tooltip tooltip3 = new Tooltip();
		tooltip3.setText(
		    "Choose the directory where you want to save the  \n" +
		    "original content. \n"  
		);
		btnOverwrite.setTooltip(tooltip3);
		btnSaveAs = new Button("Save As...");
		btnSaveAs.setId("saveButton");
		btnSaveAs.setMaxWidth(Double.MAX_VALUE);
		btnConvert = new Button("Create");
		btnConvert.setId("saveButton");
		pb = new ProgressBar(0);
		pb.setMaxWidth(Double.MAX_VALUE);
		pi = new ProgressIndicator();
		btnBack = new Button("◀ Back");
		btnBack.setId("saveButton");
		
		gridSecondScene = new GridPane();
		gridSecondScene.setAlignment(Pos.CENTER);
		gridSecondScene.setHgap(10);
		gridSecondScene.setVgap(10);
		gridSecondScene.setPadding(new Insets(200, 200, 100, 200));
		
		
		
		gridThirdScene = new GridPane();
		gridThirdScene.setAlignment(Pos.BASELINE_LEFT);
		gridThirdScene.setHgap(10);
		gridThirdScene.setVgap(10);
		gridThirdScene.setPadding(new Insets(5, 0, 0, 15));
		HBox hBox = new HBox(mapLabel, checkBox);
		hBox.setAlignment(Pos.CENTER_LEFT);
		HBox.setMargin(checkBox,new Insets(15,15,15,15));
		HBox hBox2 = new HBox(overwriteLabel, checkBox2);
		hBox2.setAlignment(Pos.CENTER_LEFT);
		HBox.setMargin(checkBox2,new Insets(15,15,15,15));
		
		

		//Open dir components
		gridSecondScene.add(dirLabel, 0, 0);
		gridSecondScene.add(openTxtField, 1, 0);
		openTxtField.setEditable(false);
		gridSecondScene.add(btnOpenFile, 2, 0);

		//mapp
		gridSecondScene.add(hBox, 0, 1);
		
		//overwrite
		gridSecondScene.add(hBox2, 1, 1);
		gridSecondScene.add(btnOverwrite, 2, 1);

		//Out dir components
		gridSecondScene.add(outputLabel, 0, 2);
		gridSecondScene.add(saveTxtField, 1, 2);
		saveTxtField.setEditable(false);
		gridSecondScene.add(btnSaveAs, 2, 2);
		
		//Create Excel button
		gridSecondScene.add(btnConvert, 1, 3);
		
		gridSecondScene.add(pb, 1, 5);
		pb.setVisible(false);
		
		pi.setMinSize(80, 80);
		gridSecondScene.add(pi, 1, 5);
		pi.setVisible(false);
		
		//back button
		gridThirdScene.add(btnBack, 0, 0);

		
		
		root2.getChildren().add(gridSecondScene);
		//root2.getChildren().add(pb);
		root2.getChildren().add(gridThirdScene);
		secondScene = new Scene(root2, 800, 620);
		secondScene.getStylesheets().add("resources/style/style.css");
	}
	/**
	 * Resets the contents of the text fields
	 */
	public void resetTextField()
	{
		for(TextField tField : content)
		{
			if(!tField.getText().isEmpty())
			{
				tField.setText("");
			}
		}
		if(datePicker.getValue() != null)
		{
			datePicker.setValue(null);
		}
	}
	
	/**
	 * Adds action listeners to all the buttons in this application
	 * @param listenForEvent
	 */
	public void addActionListenerForButton(EventHandler<ActionEvent> listenForEvent)
	{
		saveButton.setOnAction(listenForEvent);
		btnOpenFile.setOnAction(listenForEvent);
		btnSaveAs.setOnAction(listenForEvent);
		btnConvert.setOnAction(listenForEvent);
		btnBack.setOnAction(listenForEvent);
		checkBox.setOnAction(listenForEvent);
		checkBox2.setOnAction(listenForEvent);
	}
		
	/**
	 * Getters and setters for variables
	 * @return s all the variables that have getters
	 */
	public Button getBtnOpenFile() {
		return btnOpenFile;
	}

	public TextField getTmp() {
		return tmp;
	}

	public void setTmp(TextField tmp) {
		this.tmp = tmp;
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

	public void setSaveButton(Button saveButton) {
		this.saveButton = saveButton;
	}

	public DirectoryChooser getDirectoryChooser() {
		return directoryChooser;
	}

	public Scene getSecondScene() {
		return secondScene;
	}

	public Scene getScene() {
		return firstScene;
	}

	public TextField getBALtxt() {
		return BALtxt;
	}

	public TextField getAKtxt() {
		return AKtxt;
	}

	public TextField getOAtxt() {
		return OAtxt;
	}

	public TextField getLMtxt() {
		return LMtxt;
	}

	public TextField getOLMtxt() {
		return OLMtxt;
	}

	public TextField getSKtxt() {
		return SKtxt;
	}

	public TextField getKFLtxt() {
		return KFLtxt;
	}

	public TextField getTTKtxt() {
		return TTKtxt;
	}

	public TextField getEKtxt() {
		return EKtxt;
	}
	
	public void setEKtxt(TextField eKtxt) {
		EKtxt = eKtxt;
	}

	public TextField getANtxt() {
		return ANtxt;
	}

	public TextField getSNtxt() {
		return SNtxt;
	}
	
	public DatePicker getDatePicker() {
		return datePicker;
	}

	public TextField getUDtxt() {
		return UDtxt;
	}

	public TextField getKOMtxt() {
		return KOMtxt;
	}

	public Button getSaveButton() {
		return saveButton;
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

	public CheckBox getCheckBox() {
		return checkBox;
	}

	public void setCheckBox(CheckBox checkBox) {
		this.checkBox = checkBox;
	}
	public Button getBtnOverwrite() {
		return btnOverwrite;
	}
	public void setBtnOverwrite(Button btnOverwrite) {
		this.btnOverwrite = btnOverwrite;
	}
	public CheckBox getCheckBox2() {
		return checkBox2;
	}
	public void setCheckBox2(CheckBox checkBox2) {
		this.checkBox2 = checkBox2;
	}
	
	
}
