package com.arkivit.view;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class FirstScene {
	
	private ArrayList<TextField> content; 
	private ArrayList<TextField> mandatoryFields;
	private ArrayList<TextField> validateFields;
	
	private GridPane grid;
	private GridPane langGrid;
	
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
	
	/**
	 * Adds all components to the first scene.
	 */
	public void startFirstScene() {
		saveButton = new Button("Spara");
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
		grid.setPadding(new Insets(95, 25, 25, 25));
		
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
		firstScene = new Scene(root, 800, 620);
		firstScene.getStylesheets().add("resources/style/style.css");
		

	}
	
	/**
	 * Adds action listener saveButton in first scene
	 * @param listenForEvent
	 */
	public void addActionListenerForButton(EventHandler<ActionEvent> listenForEvent)
	{
		saveButton.setOnAction(listenForEvent);
	}
	
	/**
	 * Adds all text fields to an array list
	 */
	public void addContentToList() {
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
	
	
	public Scene getFirstScene() {
		return firstScene;
	}

	public TextField getTmp() {
		return tmp;
	}

	public void setTmp(TextField tmp) {
		this.tmp = tmp;
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
	
	public void setSaveButton(Button saveButton) {
		this.saveButton = saveButton;
	}

	public Label getBAL() {
		return BAL;
	}

	public void setBAL(Label bAL) {
		BAL = bAL;
	}

	public Label getAK() {
		return AK;
	}

	public void setAK(Label aK) {
		AK = aK;
	}

	public Label getOA() {
		return OA;
	}

	public void setOA(Label oA) {
		OA = oA;
	}

	public Label getLM() {
		return LM;
	}

	public void setLM(Label lM) {
		LM = lM;
	}

	public Label getOLM() {
		return OLM;
	}

	public void setOLM(Label oLM) {
		OLM = oLM;
	}

	public Label getSK() {
		return SK;
	}

	public void setSK(Label sK) {
		SK = sK;
	}

	public Label getKFL() {
		return KFL;
	}

	public void setKFL(Label kFL) {
		KFL = kFL;
	}

	public Label getTTK() {
		return TTK;
	}

	public void setTTK(Label tTK) {
		TTK = tTK;
	}

	public Label getEK() {
		return EK;
	}

	public void setEK(Label eK) {
		EK = eK;
	}

	public Label getAN() {
		return AN;
	}

	public void setAN(Label aN) {
		AN = aN;
	}

	public Label getSN() {
		return SN;
	}

	public void setSN(Label sN) {
		SN = sN;
	}

	public Label getUD() {
		return UD;
	}

	public void setUD(Label uD) {
		UD = uD;
	}

	public Label getKOM() {
		return KOM;
	}

	public void setKOM(Label kOM) {
		KOM = kOM;
	}
		
}
