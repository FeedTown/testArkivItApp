package Test.code;

import java.io.File;
import java.io.IOException;
import java.util.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

public class MappingRename {


	String inputPath,backupFilePath,folderName;
	ArrayList<File> dirList = new ArrayList<File>();
	ArrayList<File> fileList = new ArrayList<File>();
	ArrayList<String> mappedFiles = new ArrayList<String>();
	ArrayList<String> illegalCharFiles = new ArrayList<String>();
	int fileCount = 0, dirCount = 0, dupeFileCounter = 0, dupeDirCounter = 0, counter = 1;
	String fileExtension = "", fileNameWithOutExt = "";
	boolean mapping = false;

	public MappingRename(boolean mapping)
	{
		this.mapping = mapping;
	}

	public void init() throws IOException
	{
		inputPath = "/Users/RobertoBlanco/Desktop/MappingMap";
		backupFilePath = "/Users/RobertoBlanco/Desktop";
		folderName = new File(inputPath).getName();


		if(mapping)
		{
			copyFolder();
		}



		//testMeth();
		listOfFilesAndDirectory(inputPath);
		dirList.clear();
		fileList.clear();


	}

	private void copyFolder() {
		File selectedFolder = new File(inputPath);
		try {
			FileUtils.copyDirectoryToDirectory(selectedFolder, new File(backupFilePath + "/" + folderName + "_backup"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void listOfFilesAndDirectory(String inputFolder) throws IOException{
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
					tempFile = doMapping(currentFileOrDir,false);
				}

				System.out.println("Current File : "  + currentFileOrDir.getName());

				fileList.add(tempFile);
				System.out.println("Nr " + fileCount + " : " + currentFileOrDir.getName());
				fileCount++;
			}
			else if(currentFileOrDir.isDirectory())	
			{
				//pathTest.add(tempFile.getAbsolutePath());

				if(mapping)
				{
					//tempFile = getIllChars(tempFile,currentFileOrDir);
					tempFile = doMapping(currentFileOrDir,true);
				}

				dirList.add(currentFileOrDir);
				System.out.println("Current Dir : "  + currentFileOrDir.getName());
				dirCount++;

				listOfFilesAndDirectory(tempFile.getAbsolutePath());

			}

		}
	}

	public File doMapping(File currFileOrDir, boolean isDir) {

		File tempFile = null;
		String currFile = "";//replaceIllegalChars(currFileOrDir.getName());


		if(checkIfCurrentFileOrDirContainsIllegalChars(currFileOrDir))
		{
			currFile = replaceIllegalChars(currFileOrDir.getName());
			tempFile = new File(currFileOrDir.getParentFile().getAbsolutePath(), currFile);

			checkForFileOrDirAndSeparateWithExt(isDir,tempFile);

			if(tempFile.exists()) {

				tempFile = renameFile(tempFile,isDir,currFileOrDir);

			}

		}
		else
		{
			tempFile = currFileOrDir;
		}

		currFileOrDir.renameTo(tempFile);

		return tempFile;

	}

	private File renameFile(File tempFile, boolean isDir, File currFileOrDir) {
		if(!isDir)
		{
			tempFile = new File(currFileOrDir.getParentFile().getAbsolutePath(), fileNameWithOutExt + "_" + counter + "." + fileExtension);
		}
		else
		{
			tempFile = new File(currFileOrDir.getParentFile().getAbsolutePath(), fileNameWithOutExt + "_" + counter);
		}
		return tempFile;
	}

	private boolean checkIfCurrentFileOrDirContainsIllegalChars(File currFileOrDir) {
		if(currFileOrDir.getName().contains("å") || currFileOrDir.getName().contains("ä") || currFileOrDir.getName().contains("ö")
				|| currFileOrDir.getName().contains("ü") || currFileOrDir.getName().contains("Å") || currFileOrDir.getName().contains("Ä") 
				|| currFileOrDir.getName().contains("Ö") || currFileOrDir.getName().contains("Ü"))
		{
			return true;
		}
		else
		{
			return false;
		}

	}

	private void checkForFileOrDirAndSeparateWithExt(boolean isDir, File tempFile) {

		if(!isDir)
		{
			fileExtension = FilenameUtils.getExtension(tempFile.getName());
			fileNameWithOutExt = FilenameUtils.removeExtension(tempFile.getName());
		}
		else
		{
			fileNameWithOutExt = tempFile.getName();
		}
	}

	private String replaceIllegalChars(String currentString) {

		illegalCharFiles.add(currentString);
		currentString = StringUtils.replaceEach (currentString, 
				new String[] { "å",  "ä",  "ö",  "ü", "Å",  "Ä",  "Ö", "Ü", " "}, 
				new String[] {"aa", "ae", "oe", "ue","AA", "AE", "OE", "UE", "_"});

		mappedFiles.add(currentString);

		return currentString;
	}


	public static void main(String[] args) throws IOException {

		boolean doMapping = false;

		Scanner scan = new Scanner(System.in);

		System.out.println("Do you want to mapp everthing in selected folder?\n Yes(y)/No(n)? ");
		String answer = scan.nextLine();

		if(answer.equals("y") || answer.equals("Y")) {

			doMapping = true;
		}
		else if(answer.equals("n") || answer.equals("N"))
		{
			doMapping = false;
		}

		new MappingRename(doMapping).init();

		scan.close();

	}
}
