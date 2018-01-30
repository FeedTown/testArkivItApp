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

public class ExcelAppGUI {



	private JFrame frame;
	BufferedWriter readFile;
	JButton btnOpenFile = new JButton("Select file...");
	JButton btnSaveAs = new JButton("Save As...");
	JButton btnConvert = new JButton("Convert");
	JFileChooser chooser = new JFileChooser();
	JFileChooser saveFile = new JFileChooser();
	private final JTextField openTxtField = new JTextField();
	private final JTextField saveTxtField = new JTextField();
	
	private final JLabel lblNewLabel = new JLabel("Directory");
	private final JLabel lblOutput = new JLabel("Output");
	private final JLabel lblColumns = new JLabel("Map");

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

		frame = new JFrame();
		frame.setTitle("ArkivitApp");
		frame.setResizable(false);
		frame.setBounds(100, 100, 663, 276);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);

		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		panel.setBounds(0, 0, 699, 285);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		btnSaveAs.setEnabled(false);
		btnConvert.setEnabled(false);

		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		saveFile.setFileSelectionMode(JFileChooser.FILES_ONLY);

		btnOpenFile.setBounds(374, 69, 103, 23);
		panel.add(btnOpenFile);



		btnSaveAs.setBounds(374, 156, 103, 23);
		panel.add(btnSaveAs);



		btnConvert.setBounds(302, 187, 103, 23);
		panel.add(btnConvert);




		openTxtField.setBounds(215, 69, 157, 22);
		openTxtField.setColumns(10);
		openTxtField.setEditable(false);
		panel.add(openTxtField);


		saveTxtField.setEditable(false);
		saveTxtField.setColumns(10);
		saveTxtField.setBounds(215, 156, 157, 22);
		panel.add(saveTxtField);
		lblNewLabel.setBounds(156, 73, 46, 14);

		panel.add(lblNewLabel);
		lblOutput.setBounds(156, 160, 46, 14);

		panel.add(lblOutput);
		lblColumns.setBounds(156, 123, 46, 14);

		panel.add(lblColumns);

		JCheckBox checkBox = new JCheckBox("");
		checkBox.setContentAreaFilled(false);
		checkBox.setBorderPaintedFlat(true);
		checkBox.setBounds(215, 119, 21, 18);
		panel.add(checkBox);


		//openFile button action
		/*btnOpenFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int returnValue = chooser.showOpenDialog(null);

				if(returnValue == JFileChooser.APPROVE_OPTION) {
					//File myFile = chooser.getSelectedFile();
					myFile = chooser.getSelectedFile();
					openTxtField.setText(myFile.getAbsolutePath());

				}
				if(returnValue == JFileChooser.CANCEL_OPTION) {
					openTxtField.setText("");
					btnSaveAs.setEnabled(false);
				}
				if(e.getSource() == btnOpenFile && returnValue == JFileChooser.APPROVE_OPTION) {
					btnSaveAs.setEnabled(true);
				}

			}
		});*/

		/*btnSaveAs.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				FileFilter filter = new FileNameExtensionFilter(".txt","txt");
				saveFile.addChoosableFileFilter(filter);
				int returnSaveVal = saveFile.showSaveDialog(null);
				if(returnSaveVal == JFileChooser.APPROVE_OPTION) {
					myFile2 = saveFile.getSelectedFile();
					saveTxtField.setText(myFile2.getAbsolutePath());

				}
				if(returnSaveVal == JFileChooser.CANCEL_OPTION) {
					saveTxtField.setText("");
					btnConvert.setEnabled(false);
				}
				if(e.getSource() == btnSaveAs && returnSaveVal == JFileChooser.APPROVE_OPTION) {
					btnConvert.setEnabled(true);
				}


			}

		});*/




		/*btnConvert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File sourceFile = new File(openTxtField.getText());
				File destinationFile = new File(saveTxtField.getText());
				Path sourcePath = sourceFile.toPath();
				Path destinationPath = destinationFile.toPath();
				//sourcePath.relativize(destinationPath);

				try {
					if(!sourceFile.equals(destinationFile)) {
						Files.copy(sourcePath, destinationPath);
						JOptionPane.showMessageDialog(null, "File was successfully created", 
								"Success", JOptionPane.INFORMATION_MESSAGE);
						openTxtField.setText("");
						saveTxtField.setText("");

					}
					else {
						System.out.println("CATCH IF");		
						JOptionPane.showMessageDialog(null, "File already exists", 
								"INFO", 
								JOptionPane.INFORMATION_MESSAGE);
						openTxtField.setText("");
						saveTxtField.setText("");
						if(e.getSource() == btnConvert) {
							btnSaveAs.setEnabled(false);
							btnConvert.setEnabled(false);

						}
					}
				}
				catch(Exception e1){
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "You can't overwrite file", 
							"INFO", 
							JOptionPane.INFORMATION_MESSAGE);
					openTxtField.setText("");
					saveTxtField.setText("");
				}
			}


		}); */


	}



	public JButton getBtnOpenFile() {
		return btnOpenFile;
	}

	public void setBtnOpenFile(JButton btnOpenFile) {
		this.btnOpenFile = btnOpenFile;
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public BufferedWriter getReadFile() {
		return readFile;
	}

	public void setReadFile(BufferedWriter readFile) {
		this.readFile = readFile;
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

	public JLabel getLblNewLabel() {
		return lblNewLabel;
	}

	public JLabel getLblOutput() {
		return lblOutput;
	}

	public JLabel getLblColumns() {
		return lblColumns;
	}
	
	

}


