package Test.code;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.text.View;

import com.arkivit.controller.ExcelController;
import com.arkivit.model.MetadataToExcelGUI;
import com.arkivit.view.ExcelAppGUI;

public class testClass {

	static ExcelAppGUI view;
	static MetadataToExcelGUI model;
	public static void main(String[] args)
	{
		view = new ExcelAppGUI();
		model = new MetadataToExcelGUI();

		//ExcelController controller = new ExcelController(model,view);


		view.setVisible(true);

		view.getBtnOpenFile().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				testFunc(e);

			}});

		view.getBtnConvert().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				testFunc2(e);

			}});

		view.getBtnSaveAs().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				testFunc1(e);

			}});


	}

	public static void testFunc(ActionEvent e)
	{

		int returnValue = view.getChooser().showOpenDialog(null);

		if(returnValue == JFileChooser.APPROVE_OPTION) {
			//model.setMyFile(view.getChooser().getSelectedFile());
			//1model.setSourceFolderPath(view.getChooser().getSelectedFile().getAbsolutePath());
			//model.getSourceFolderPath() = model.getMyFile().getAbsolutePath();
			//2view.getOpenTxtField().setText(view.getChooser().getSelectedFile().getAbsolutePath());
			
			//System.out.println(testOutput);
			//model.setMyFile(view.getChooser().getSelectedFile());
			//model.setSourceFolderPath(model.getMyFile().getAbsolutePath());
			
			
			//String testOutput = model.getSourceFolderPath();
			model.setSourceFolderPath(view.getChooser().getSelectedFile().getAbsolutePath());
			view.getOpenTxtField().setText(model.getSourceFolderPath());
			String testOutput = model.getSourceFolderPath();
			System.out.println(testOutput);

		}
		
		if(returnValue == JFileChooser.CANCEL_OPTION) {
			view.getOpenTxtField().setText("");
			view.getBtnSaveAs().setEnabled(false);
		}
		
		if(e.getSource() == view.getBtnOpenFile() && returnValue == JFileChooser.APPROVE_OPTION) {
			view.getBtnSaveAs().setEnabled(true);
		}
		

	}

	public static void testFunc1(ActionEvent e)
	{
		 int returnSaveVal = view.getSaveFile().showSaveDialog(null);
			if(returnSaveVal == JFileChooser.APPROVE_OPTION) {
				//model.setMyFile2(view.getSaveFile().getSelectedFile());
				/*model.setTargetexcelFilepath(view.getSaveFile().getSelectedFile().getAbsolutePath());
				model.setExcelFileName(view.getSaveFile().getParent() + ".xls");
				view.getSaveTxtField().setText(view.getSaveFile().getSelectedFile().getName());*/
				model.setTargetexcelFilepath(view.getSaveFile().getSelectedFile().getParentFile().getAbsolutePath());
				
				model.setExcelFileName(view.getSaveFile().getSelectedFile().getName() + ".xls");
				view.getSaveTxtField().setText(model.getTargetexcelFilepath());
				
				System.out.println("File path ? : " + model.getTargetexcelFilepath());
				System.out.println("File name ? : " + model.getExcelFileName());
				
				//model.setMyFile2(view.getSaveFile().getSelectedFile());
				//model.setTargetexcelFilepath(model.getMyFile2().getParent());
				//model.setExcelFileName(view.getSaveFile().getName(model.getMyFile2()) + ".xls");
				

			}
			if(returnSaveVal == JFileChooser.CANCEL_OPTION) {
				view.getSaveTxtField().setText("");
				view.getBtnConvert().setEnabled(false);
			}
			if(e.getSource() == view.getBtnSaveAs() && returnSaveVal == JFileChooser.APPROVE_OPTION) {
				view.getBtnConvert().setEnabled(true);
			}


	}

	public static void testFunc2(ActionEvent e)
	{



	}


}
