package com.arkivit.view;

import javafx.scene.control.Label;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class ExcelAppGUIFX extends Application {

	
	
	/*public static void main(String[] args) {
		launch(args);

	}*/
	
	/*ExcelAppGUIFX(String args){
		launch(args);
	}*/
	
	GridPane grid = new GridPane();
	//Text scenetitle = new Text("Welcome");
	
	Label BAL;
	TextField BALtxt;
	
	Label AK;
	TextField AKtxt;
	
	Label OA;
	TextField OAtxt;
	
	Label LM;
	TextField LMtxt;
	
	Label OLM;
	TextField OLMtxt;
	
	Label SK;
	TextField SKtxt;
	
	Label KFL;
	TextField KFLtxt;
	
	Label TTK;
	TextField TTKtxt ;
	
	Label EK;
	TextField EKtxt;
	
	Label AN;
	TextField ANtxt;
	
	Label SN;
	TextField SNtxt;
	
	Label UD;
	TextField UDtxt;
	
	Label KOM;
	TextField KOMtxt; 
	
	Button saveButton;
	
	@Override
	    public void start(Stage primaryStage) {
	        primaryStage.setTitle("ArkivIt");
	        BAL = new Label("Beskrivning av leverans");
	        AK = new Label("Arkivbildare");
	        OA = new Label("Organisationsnummer arkivbildare");
	        LM = new Label("Levererande myndighet");
	        Label OLM = new Label("Organisationsnummer levererande myndighet");
	        KFL = new Label("Kontaktperson för leverans");
	        SK = new Label("Servicebyrå/Konsult");
	        TTK = new Label("Telefonnummer till kontaktperson");
	        EK = new Label("E-post-adress till kontakperson");
	        AN = new Label("Arkivets namn");
	        SN = new Label("Systemts namn");
	        UD = new Label("Uttagsdatum:");
	        KOM = new Label("Kommentar");
	        saveButton = new Button("SAVE");
	        BALtxt = new TextField();
	    	
	    	 AKtxt = new TextField();
	    	
	    	OAtxt = new TextField();
	    	
	    	LMtxt = new TextField();
	    	
	    	OLMtxt = new TextField();
	    	
	    	 SKtxt = new TextField();
	    	
	    	 KFLtxt = new TextField();
	    	
	    	TTKtxt = new TextField();
	    	
	    EKtxt = new TextField();
	    	
	    	 ANtxt = new TextField();
	    	
	    	SNtxt = new TextField();
	    	
	     UDtxt = new TextField();
	    	
	    	 KOMtxt = new TextField();
	        
	        grid.setAlignment(Pos.CENTER);
	        grid.setHgap(10);
	        grid.setVgap(10);
	        grid.setPadding(new Insets(25, 25, 25, 25));

	        
	      //  scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
	        //grid.add(scenetitle, 0, 0, 2, 1);

	        
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
	        grid.add(UDtxt, 1, 12);
	        
	        grid.add(KOM, 0, 13);
	        grid.add(KOMtxt, 1, 13);

	        HBox hbBtn = new HBox(10);
	        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
	        hbBtn.getChildren().add(saveButton);
	        grid.add(hbBtn, 1, 14);

	        final Text actiontarget = new Text();
	        grid.add(actiontarget, 0, 14);

	
	      /*  saveButton.setOnAction(new EventHandler<ActionEvent>() {

	            @Override
	            public void handle(ActionEvent e) {
	                actiontarget.setFill(Color.FIREBRICK);
	                actiontarget.setText("Sign in button pressed");
	            }
	        });*/

	        Scene scene = new Scene(grid, 800, 600);
	        primaryStage.setScene(scene);
	        primaryStage.show();
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

	public TextField getANtxt() {
		return ANtxt;
	}


	public TextField getSNtxt() {
		return SNtxt;
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
	 
}
