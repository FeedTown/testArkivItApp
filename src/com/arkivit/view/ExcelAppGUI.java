package com.arkivit.view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JCheckBox;
import java.awt.CardLayout;
import java.awt.Font;

public class ExcelAppGUI extends JFrame{

	//private JFrame frame;
	BufferedWriter readFile;
	JButton btnOpenFile = new JButton("Select file...");
	JButton btnSaveAs = new JButton("Save As...");
	JButton btnConvert = new JButton("Create");
	JButton btnDone = new JButton("Done");
	JFileChooser chooser = new JFileChooser();
	JFileChooser saveFile = new JFileChooser();
	JPanel panelForm = new JPanel();
	JPanel panel = new JPanel();
	private JTextField openTxtField = new JTextField();
	private JTextField saveTxtField = new JTextField();
	
	private final JLabel lblNewLabel = new JLabel("Directory");
	private final JLabel lblOutput = new JLabel("Output");
	private final JLabel lblColumns = new JLabel("Map");
	private JTextField descriptionDeliveryTxt = new JTextField();
	private JTextField archiveCreatorTxt = new JTextField();
	private JTextField oNumArchiveCreatorTxt = new JTextField();
	private JTextField delivGovernmentTxt = new JTextField();
	private JTextField oNumDelivGovernmentTxt = new JTextField();
	private JTextField consultantBureauTxt = new JTextField();
	private JTextField contactPersonDelivTxt = new JTextField();
	private JTextField telContactPersonTxt = new JTextField();
	private JTextField mailContactPersonTxt = new JTextField();
	private JTextField archiveNameTxt = new JTextField();
	private JTextField systemNameTxt = new JTextField();
	private JTextField withdrawalDateTxt = new JTextField();
	private JTextField commentTxt = new JTextField();
	private final JLabel oNumArchiveCreatorLabel = new JLabel("Organisationsnummer arkivbildare");
	private final JLabel delivGovernmentLabel = new JLabel("Levererande myndighet");
	private final JLabel oNumDelivGovernmentLabel = new JLabel("Organisationsnummer levererande myndighet");
	private final JLabel consultantBureauLabel = new JLabel("Servicebyr�/Konsult");
	private final JLabel contactPersonDelivLabel = new JLabel("Kontaktperson f�r leverans");
	private final JLabel telContactPersonLabel = new JLabel("Telefonnummer till kontaktperson");
	private final JLabel mailContactPersonLabel = new JLabel("E-post-adress till kontaktperson");
	private final JLabel archiveNameLabel = new JLabel("Arkivets namn");
	private final JLabel systemNameLabel = new JLabel("Systemets namn");
	private final JLabel withdrawalDateLabel = new JLabel("Uttagsdatum");
	private final JLabel commentLabel = new JLabel("Kommentar");

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ExcelAppGUI window = new ExcelAppGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the application.
	 */
	public ExcelAppGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()  {

		//frame = new JFrame();
		this.setTitle("ArkivitApp");
		this.setResizable(false);
		this.setBounds(100, 100, 707, 752);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(new CardLayout(0, 0));
		
		
		panelForm.setBackground(Color.GRAY);
		getContentPane().add(panelForm, "name_157896301514130");
		panelForm.setLayout(null);
		
		
		btnDone.setBounds(293, 663, 89, 23);
		panelForm.add(btnDone);
		
		descriptionDeliveryTxt = new JTextField();
		descriptionDeliveryTxt.setBorder(null);
		descriptionDeliveryTxt.setBounds(373, 112, 260, 28);
		panelForm.add(descriptionDeliveryTxt);
		descriptionDeliveryTxt.setColumns(10);
		archiveCreatorTxt.setColumns(10);
		archiveCreatorTxt.setBorder(null);
		archiveCreatorTxt.setBounds(373, 149, 260, 28);
		
		panelForm.add(archiveCreatorTxt);
		oNumArchiveCreatorTxt.setColumns(10);
		oNumArchiveCreatorTxt.setBorder(null);
		oNumArchiveCreatorTxt.setBounds(373, 188, 260, 28);
		
		panelForm.add(oNumArchiveCreatorTxt);
		delivGovernmentTxt.setColumns(10);
		delivGovernmentTxt.setBorder(null);
		delivGovernmentTxt.setBounds(373, 228, 260, 28);
		
		panelForm.add(delivGovernmentTxt);
		oNumDelivGovernmentTxt.setColumns(10);
		oNumDelivGovernmentTxt.setBorder(null);
		oNumDelivGovernmentTxt.setBounds(373, 267, 260, 28);
		
		panelForm.add(oNumDelivGovernmentTxt);
		consultantBureauTxt.setColumns(10);
		consultantBureauTxt.setBorder(null);
		consultantBureauTxt.setBounds(373, 306, 260, 28);
		
		panelForm.add(consultantBureauTxt);
		contactPersonDelivTxt.setColumns(10);
		contactPersonDelivTxt.setBorder(null);
		contactPersonDelivTxt.setBounds(373, 345, 260, 28);
		
		panelForm.add(contactPersonDelivTxt);
		telContactPersonTxt.setColumns(10);
		telContactPersonTxt.setBorder(null);
		telContactPersonTxt.setBounds(373, 384, 260, 28);
		
		panelForm.add(telContactPersonTxt);
		mailContactPersonTxt.setColumns(10);
		mailContactPersonTxt.setBorder(null);
		mailContactPersonTxt.setBounds(373, 423, 260, 28);
		
		panelForm.add(mailContactPersonTxt);
		archiveNameTxt.setColumns(10);
		archiveNameTxt.setBorder(null);
		archiveNameTxt.setBounds(373, 462, 260, 28);
		
		panelForm.add(archiveNameTxt);
		systemNameTxt.setColumns(10);
		systemNameTxt.setBorder(null);
		systemNameTxt.setBounds(373, 501, 260, 28);
		
		panelForm.add(systemNameTxt);
		withdrawalDateTxt.setColumns(10);
		withdrawalDateTxt.setBorder(null);
		withdrawalDateTxt.setBounds(373, 540, 260, 28);
		
		panelForm.add(withdrawalDateTxt);
		commentTxt.setColumns(10);
		commentTxt.setBorder(null);
		commentTxt.setBounds(373, 579, 260, 28);
		
		panelForm.add(commentTxt);
		
		JLabel descriptionDeliveryLabel = new JLabel("Beskrivning av leveransen");
		descriptionDeliveryLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		descriptionDeliveryLabel.setBounds(61, 110, 260, 28);
		panelForm.add(descriptionDeliveryLabel);
		
		JLabel archiveCreatorLabel = new JLabel("Arkivbildare");
		archiveCreatorLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		archiveCreatorLabel.setBounds(61, 147, 260, 28);
		panelForm.add(archiveCreatorLabel);
		oNumArchiveCreatorLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		oNumArchiveCreatorLabel.setBounds(61, 186, 260, 28);
		
		panelForm.add(oNumArchiveCreatorLabel);
		delivGovernmentLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		delivGovernmentLabel.setBounds(61, 225, 260, 28);
		
		panelForm.add(delivGovernmentLabel);
		oNumDelivGovernmentLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		oNumDelivGovernmentLabel.setBounds(61, 265, 307, 28);
		
		panelForm.add(oNumDelivGovernmentLabel);
		consultantBureauLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		consultantBureauLabel.setBounds(61, 305, 260, 28);
		
		panelForm.add(consultantBureauLabel);
		contactPersonDelivLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		contactPersonDelivLabel.setBounds(61, 347, 260, 28);
		
		panelForm.add(contactPersonDelivLabel);
		telContactPersonLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		telContactPersonLabel.setBounds(61, 384, 260, 28);
		
		panelForm.add(telContactPersonLabel);
		mailContactPersonLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		mailContactPersonLabel.setBounds(61, 423, 260, 28);
		
		panelForm.add(mailContactPersonLabel);
		archiveNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		archiveNameLabel.setBounds(61, 462, 260, 28);
		
		panelForm.add(archiveNameLabel);
		systemNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		systemNameLabel.setBounds(61, 501, 260, 28);
		
		panelForm.add(systemNameLabel);
		withdrawalDateLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		withdrawalDateLabel.setBounds(61, 540, 260, 28);
		
		panelForm.add(withdrawalDateLabel);
		commentLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		commentLabel.setBounds(61, 580, 260, 28);
		
		panelForm.add(commentLabel);

		
		panel.setBackground(Color.GRAY);
		this.getContentPane().add(panel, "name_157896014914099");
		panel.setLayout(null);
		btnSaveAs.setEnabled(false);
		btnConvert.setEnabled(false);

		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		saveFile.setFileSelectionMode(JFileChooser.FILES_ONLY);

		btnOpenFile.setBounds(374, 232, 103, 23);
		panel.add(btnOpenFile);



		btnSaveAs.setBounds(374, 321, 103, 23);
		panel.add(btnSaveAs);



		btnConvert.setBounds(289, 369, 103, 23);
		panel.add(btnConvert);




		openTxtField.setBounds(215, 232, 157, 22);
		openTxtField.setColumns(10);
		openTxtField.setEditable(false);
		panel.add(openTxtField);


		saveTxtField.setEditable(false);
		saveTxtField.setColumns(10);
		saveTxtField.setBounds(215, 321, 157, 22);
		panel.add(saveTxtField);
		lblNewLabel.setBounds(156, 236, 46, 14);

		panel.add(lblNewLabel);
		lblOutput.setBounds(156, 325, 46, 14);

		panel.add(lblOutput);
		lblColumns.setBounds(156, 278, 46, 14);

		panel.add(lblColumns);

		JCheckBox checkBox = new JCheckBox("");
		checkBox.setContentAreaFilled(false);
		checkBox.setBorderPaintedFlat(true);
		checkBox.setBounds(215, 278, 21, 18);
		panel.add(checkBox);
		
		
		
	}
	
	//getters and setters for form panel
	
	
	
	public JButton getBtnDone() {
		return btnDone;
	}

	public JTextField getDescriptionDeliveryTxt() {
		return descriptionDeliveryTxt;
	}

	public void setDescriptionDeliveryTxt(JTextField descriptionDeliveryTxt) {
		this.descriptionDeliveryTxt = descriptionDeliveryTxt;
	}

	public JTextField getArchiveCreatorTxt() {
		return archiveCreatorTxt;
	}

	public JTextField getoNumArchiveCreatorTxt() {
		return oNumArchiveCreatorTxt;
	}

	public JTextField getDelivGovernmentTxt() {
		return delivGovernmentTxt;
	}

	public JTextField getoNumDelivGovernmentTxt() {
		return oNumDelivGovernmentTxt;
	}

	public JTextField getConsultantBureauTxt() {
		return consultantBureauTxt;
	}

	public JTextField getContactPersonDelivTxt() {
		return contactPersonDelivTxt;
	}

	public JTextField getTelContactPersonTxt() {
		return telContactPersonTxt;
	}

	public JTextField getMailContactPersonTxt() {
		return mailContactPersonTxt;
	}

	public JTextField getArchiveNameTxt() {
		return archiveNameTxt;
	}

	public JTextField getSystemNameTxt() {
		return systemNameTxt;
	}

	public JTextField getWithdrawalDateTxt() {
		return withdrawalDateTxt;
	}

	public JTextField getCommentTxt() {
		return commentTxt;
	}

	public JPanel getPanelForm() {
		return panelForm;
	}

	public void setPanelForm(JPanel panelForm) {
		this.panelForm = panelForm;
	}

	//getters and setters for second panel
	public JButton getBtnOpenFile() {
		return btnOpenFile;
	}

	public void setBtnOpenFile(JButton btnOpenFile) {
		this.btnOpenFile = btnOpenFile;
	}


	public JButton getBtnSaveAs() {
		return btnSaveAs;
	}

	public void setBtnSaveAs(JButton btnSaveAs) {
		this.btnSaveAs = btnSaveAs;
	}

	public JButton getBtnConvert() {
		return btnConvert;
	}

	public void setBtnConvert(JButton btnConvert) {
		this.btnConvert = btnConvert;
	}

	public JFileChooser getChooser() {
		return chooser;
	}

	public void setChooser(JFileChooser chooser) {
		this.chooser = chooser;
	}

	public JFileChooser getSaveFile() {
		return saveFile;
	}

	public void setSaveFile(JFileChooser saveFile) {
		this.saveFile = saveFile;
	}

	
	public JTextField getOpenTxtField() {
		return openTxtField;
	}

	public JTextField getSaveTxtField() {
		return saveTxtField;
	}

	public JPanel getPanel() {
		return panel;
	}

	public void setPanel(JPanel panel) {
		this.panel = panel;
	}
	
}


