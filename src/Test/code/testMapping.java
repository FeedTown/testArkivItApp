package Test.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

public class testMapping {

	String inputPath,backupFilePath,folderName;
	ArrayList<File> dirList = new ArrayList<File>();
	ArrayList<File> fileList = new ArrayList<File>();
	ArrayList<String> mappedFiles = new ArrayList<String>();
	ArrayList<String> illegalCharFiles = new ArrayList<String>();
	boolean mapping = false;

	public testMapping(boolean mapping) {

		this.mapping = mapping;
	}

	public void init()
	{
		inputPath = "H:\\Skrivbord\\TestFiles";
		backupFilePath = "H:\\Skrivbord";
		folderName = new File(inputPath).getName();


		if(mapping)
		{
			copyFolder();
			
			listOfFilesAndDirectory(inputPath);
			
			mappTheFolders();
		}
		else
		{
			listOfFilesAndDirectory(inputPath);
		}
		
		
		//testMeth();
		
		dirList.clear();
		fileList.clear();

		
	}


	private void mappTheFolders() {
		int counter = 0;
		File tempFile;
		try {
			if(!fileList.isEmpty())
			{
				for(File file : fileList)
				{
					tempFile = file;
					
					if(new File(file.getParent()).isDirectory())
					{
						System.out.println("Dir ? : " + file.getParent());
						System.out.println("**********************************");
						
						tempFile = doMapping(tempFile,new File(file.getParent()));
					}
					
					
					
					counter++;

				}

			}
			else
			{
				System.out.println("The list is empty");
			}


		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public File doMapping(File tempFile, File currFileOrDir) {
		
		System.out.println("****************? : " + currFileOrDir.getParentFile().getAbsolutePath());
		
		tempFile = new File(currFileOrDir.getParentFile().getAbsolutePath(), replaceIllegalChars(currFileOrDir.getName()));
		currFileOrDir.renameTo(tempFile);

		return tempFile;


	}

	private void testMeth() {


		//String fullPathforCurrentFile = "";

		try {
			if(!fileList.isEmpty())
			{
				for(File file : fileList)
				{
					//System.out.println("Current file : " + file.getName());
					
					System.out.println("*******Is Folder : " + file.getAbsolutePath());

				}

			}
			else
			{
				System.out.println("The list is empty");
			}


		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	//Copying folder to outside of the root folder
	private void copyFolder() {
		File selectedFolder = new File(inputPath);
		try {
			FileUtils.copyDirectoryToDirectory(selectedFolder, new File(backupFilePath + "/" + folderName + "_backup"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void listOfFilesAndDirectory(String inputFolder){
		File folder = new File(inputFolder);
		File tempFile;

		for(File currentFileOrDir : folder.listFiles())
		{
			tempFile = currentFileOrDir;
			if(currentFileOrDir.isFile())
			{
				if(mapping)
				{
					//tempFile = getIllChars(tempFile,currentFileOrDir);
					tempFile = doMapping(tempFile,currentFileOrDir);
				}

				System.out.println("Current File : "  + currentFileOrDir.getName());

				fileList.add(tempFile);
				/*System.out.println("Nr " + fileCount + " : " + currentFileOrDir.getName());
				fileCount++;*/
			}
			else if(currentFileOrDir.isDirectory())	
			{
				//pathTest.add(tempFile.getAbsolutePath());

				/*if(mapping)
					//tempFile = getIllChars(tempFile,currentFileOrDir);
					tempFile = doMapping(tempFile,currentFileOrDir);*/
				dirList.add(currentFileOrDir);
				System.out.println("Current Dir : "  + currentFileOrDir.getName());

				listOfFilesAndDirectory(tempFile.getAbsolutePath());

			}

		}
	}


	//If String contains illegal characters they will be replaced and returned.
	private String replaceIllegalChars(String currentString) {
		if(currentString.contains("å") || currentString.contains("ä") || currentString.contains("ö")
				|| currentString.contains("ü") || currentString.contains("Å") || currentString.contains("Ä") 
				|| currentString.contains("Ö") || currentString.contains("Ü"))
		{

			illegalCharFiles.add(currentString);
			currentString = StringUtils.replaceEach (currentString, 
					new String[] { "å",  "ä",  "ö",  "ü", "Å",  "Ä",  "Ö", "Ü", " "}, 
					new String[] {"aa", "ae", "oe", "ue","AA", "AE", "OE", "UE", "_"});

			mappedFiles.add(currentString);
		}

		return currentString;
	}
}
