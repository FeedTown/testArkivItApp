package com.arkivit.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;



public class MetadataToExcelGUI {

	 private String excelFileName; //= "standard.xls";
	 private long fileSize;
	 private String filePath;
	 private String targetexcelFilepath;  // generated excel file location
	 private String sourceFolderPath; // source Directory
	 private int totalFileCount = 0, tCount= 0; // variable to store total file count
	 private int fileCount = 0; // variable to store required files count
	 private ArrayList<String> aList = new ArrayList<String>();
	 private ArrayList<String> filePathList = new ArrayList<String>();
	 private ArrayList<Long> sizeList = new ArrayList<Long>();
	 private ArrayList<File> fList;// = new ArrayList<File>();
	 private int folderCounter = 0;
	 private int filec = 0;
	 File myFile;
	 File myFile2;
	 File destinationFile;
	 private InputStreamReaderDecoder decoder = new InputStreamReaderDecoder();
	 private FileDuration fileDuration = new FileDuration();
	 //private static ArrayList<String> fileExtention = new ArrayList<String>();
	
	
	
	public MetadataToExcelGUI()
	{
		
	}
	
	public MetadataToExcelGUI(String excelFileName)
	{   
		
	}
	
	public void testMeth() {
		fList = new ArrayList<File>();
		//this.excelFileName = excelFileName + ".xls";
		listOfFilesAndDirectory(sourceFolderPath);
		testFunc();
	}
	
	
	public void listOfFilesAndDirectory(String folderPathName/*, ArrayList<File> fList*/)
	{
		File folder = new File(folderPathName);
		File[] listOfFilesInDirectory = folder.listFiles();

		for(File file : listOfFilesInDirectory)
		{
			if(file.isFile())
			{
				filec++;
				fList.add(file);
				System.out.println("Nr " + filec + " : " + file.getName());
			}
			else if(file.isDirectory())
			{
				listOfFilesAndDirectory(file.getAbsolutePath());
			}
		}

		System.out.println(filec);

	}

	public void testFunc() 
	{

		fList.sort(new Comparator<File>() {
			@Override
			public int compare(File o1, File o2) {
				String s1 = o1.getName().toLowerCase();
				String s2 = o2.getName().toLowerCase();
				final int s1Dot = s1.lastIndexOf('.');
				final int s2Dot = s2.lastIndexOf('.');
				// 
				if ((s1Dot == -1) == (s2Dot == -1)) { // both or neither
					s1 = s1.substring(s1Dot + 1);
					s2 = s2.substring(s2Dot + 1);
					return s1.compareTo(s2);
				} else if (s1Dot == -1) { // only s2 has an extension, so s1 goes first
					return -1;
				} else { // only s1 has an extension, so s1 goes second
					return 1;
				}

			}});
			String duration;
		try {

			if(!fList.isEmpty())
			{
				for (int numberOfFilesInFolder = 0; numberOfFilesInFolder < fList.size(); numberOfFilesInFolder++) {

					decoder.fileEncoder(fList.get(numberOfFilesInFolder).getParentFile().getAbsolutePath(), fList.get(numberOfFilesInFolder).getName());  
					duration = "";
					String files = fList.get(numberOfFilesInFolder).getName();

					/*fileDuration.CheckFileDuration(fList.get(numberOfFilesInFolder).getParentFile().getAbsolutePath(),
							files); */
					if(files.endsWith(".mov") || 
							files.endsWith(".mp4") || 
							files.endsWith(".mp3") || 
							files.endsWith("m4v"))
					{

						fileDuration.CheckFileDuration(fList.get(numberOfFilesInFolder).getParentFile().getAbsolutePath()
								+ "/" + files);
						duration = fileDuration.getAudioVideoDuration();

					}
					
					//String files = fList.get(numberOfFilesInFolder).getName();

					String fPath;//, testPath;
					fileSize = fList.get(numberOfFilesInFolder).length();


					fPath = fList.get(numberOfFilesInFolder).getParentFile().getAbsolutePath();
					fPath = fPath.replace(sourceFolderPath, "");

					aList.add(files);
					sizeList.add(fileSize);
					filePathList.add(fPath);
					decoder.getUtfList().add(decoder.getUtfString());
					fileDuration.getAudioVideoList().add(duration);
					//fileCount++;
					System.out.println("File size: " + fileSize);

				}
			}
			else
			{
				System.out.println("The list is empty");
			}


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		createExcelFile();

	}

	public void createExcelFile() {

		File file = new File(targetexcelFilepath +"/"+ excelFileName);

		try {
			System.out.println("createExcelFile");
			WorkbookSettings wbSettings = new WorkbookSettings();
			WritableWorkbook workbook = Workbook.createWorkbook(file,
					wbSettings);
			workbook.createSheet("File Names", 0);
			System.out.println("Excel file is created in path -- "
					+ targetexcelFilepath);
			WritableSheet excelSheet = (WritableSheet) workbook.getSheet(0);
			if (!aList.isEmpty()) {
				for (int rowNumber = 0; rowNumber < aList.size(); rowNumber++) {

					/*CellView cell = workbook.getSheet(0).getColumnView(rowNumber);
		    	cell.setSize(14000);
		    	workbook.getSheet(0).setColumnView(0, cell);*/
						
					String tempString = aList.get(rowNumber);
					
					if(tempString.contains("å") || tempString.contains("ä") || tempString.contains("ö")
							|| tempString.contains("Å") || tempString.contains("Ä") || tempString.contains("Ö") 
							|| tempString.contains("Ü") || tempString.contains("ü"))
					{
						System.out.println("INSIDE replaceILLEGALE");
						tempString = replaceIllegalChars(tempString);
					}
					
					
					//System.out.println(aList.get(rowNumber));

					String sizeInString = Objects.toString(sizeList.get(rowNumber), null); 
					String fileExtention = FilenameUtils.getExtension(aList.get(rowNumber));
					// FilenameUtils.get

					Label label2 = new Label(0, 0, "FILNAMN");
					Label label = new Label(0, rowNumber+1, aList.get(rowNumber));

					Label fileTypeLabelName = new Label(1,0,"FILTYP");
					Label fileTypeLabel = new Label(1, rowNumber+1, fileExtention);
					
					Label fileTypeVersionName = new Label(2,0, "FILTYPSVERSION");
					//Label fileTypeVersionLabel = new Label(2, rowNumber+1,"")

					Label fileSizeLabelName = new Label(3, 0, "STORLEK (Bytes)");
					Label fileSizeLabel = new Label(3, rowNumber+1, sizeInString);


					Label utfLabelName = new Label(4,0, "TECKENUPPSÄTTNING");
					Label utfLabel = new Label(4, rowNumber+1, decoder.getUtfList().get(rowNumber));

					Label fileDurationLabel = new Label (5,0, "SPELTID(endast audio och video)");
					Label durationLabel = new Label(5, rowNumber+1, fileDuration.getAudioVideoList().get(rowNumber));

					Label filePathLabelName = new Label(6, 0, "SÖKVÄG(path,url)");
					Label filePathLabel = new Label(6, rowNumber+1, filePathList.get(rowNumber));
					
					Label confidentialityLabelName = new Label(7,0, "SEKRETESSGRAD HOS MYNDIGHETEN");
					//Label confidentialityLabel = new Label(7, rowNumber+1,"");
					
					Label personalInformationHandelingLabelName = new Label(8,0, "BEHANDLING AV PERSONUPPGIFTER");
					//Label personalInformationHandelingLabel = new Label(8, rowNumber+1, "");
					
					Label commentLabelName = new Label(9,0, "KOMMENTAR");
					//Label commentLabel = new Label(9, rowNumber+1, "");

					excelSheet.setColumnView(0, getLargestString(aList));
					excelSheet.setColumnView(2, 16);
					excelSheet.setColumnView(4, 20);
					excelSheet.setColumnView(5, 27);
					excelSheet.setColumnView(6, getLargestString(filePathList));
					excelSheet.setColumnView(7, 33);
					excelSheet.setColumnView(8, 33);
					excelSheet.setColumnView(9, 13);
					
					excelSheet.addCell(label2);
					excelSheet.addCell(label);
					
					excelSheet.addCell(fileTypeLabelName);
					excelSheet.addCell(fileTypeLabel);
					
					excelSheet.addCell(filePathLabelName);
					excelSheet.addCell(filePathLabel);
					
					excelSheet.addCell(fileTypeVersionName);
					//excelSheet.addCell(fileTypeVersionLabel);
					
					excelSheet.addCell(fileSizeLabelName);
					excelSheet.addCell(fileSizeLabel);
					
					excelSheet.addCell(utfLabelName);
					excelSheet.addCell(utfLabel);
					
					excelSheet.addCell(fileDurationLabel);
					excelSheet.addCell(durationLabel);
					
					excelSheet.addCell(confidentialityLabelName);
					//excelSheet.addCell(confidentialityLabel);
					
					excelSheet.addCell(personalInformationHandelingLabelName);
					//excelSheet.addCell(personalInformationHandelingLabel);
					
					excelSheet.addCell(commentLabelName);
					//excelSheet.addCell(commentLabel);
					
					
				}
			} else {
				System.out.println("No matching files found");
			}
			workbook.write();
			excelSheet = null;
			workbook.close();
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	private String replaceIllegalChars(String string) {
		String newString = StringUtils.replaceEach (string, 
				new String[] {"å", "ä", "ö", "ü","Å", "Ä", "Ö", "Ü", " "}, 
				new String[] {"aa", "ae", "oe", "ue","AA", "AE", "OE", "UE", "_"});
		return newString;
	}

	private int getLargestString(ArrayList<String> stringList) {

		int largestString = stringList.get(0).length();
		int index = 0;

		for(int i = 0; i < stringList.size(); i++)
		{
			if(stringList.get(i).length() > largestString)
			{
				largestString = stringList.get(i).length();
				index = i;
			}
		}

		return largestString;
	}

	public String getExcelFileName() {
		return excelFileName;
	}

	public void setExcelFileName(String excelFileName) {
		this.excelFileName = excelFileName;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getTargetexcelFilepath() {
		return targetexcelFilepath;
	}

	public void setTargetexcelFilepath(String targetexcelFilepath) {
		this.targetexcelFilepath = targetexcelFilepath;
	}

	public String getSourceFolderPath() {
		return sourceFolderPath;
	}

	public void setSourceFolderPath(String sourceFolderPath) {
		this.sourceFolderPath = sourceFolderPath;
	}

	public int getTotalFileCount() {
		return totalFileCount;
	}

	public void setTotalFileCount(int totalFileCount) {
		this.totalFileCount = totalFileCount;
	}

	public int gettCount() {
		return tCount;
	}

	public void settCount(int tCount) {
		this.tCount = tCount;
	}

	public int getFileCount() {
		return fileCount;
	}

	public void setFileCount(int fileCount) {
		this.fileCount = fileCount;
	}

	public ArrayList<String> getaList() {
		return aList;
	}

	public void setaList(ArrayList<String> aList) {
		this.aList = aList;
	}

	public ArrayList<String> getFilePathList() {
		return filePathList;
	}

	public void setFilePathList(ArrayList<String> filePathList) {
		this.filePathList = filePathList;
	}

	public ArrayList<Long> getSizeList() {
		return sizeList;
	}

	public void setSizeList(ArrayList<Long> sizeList) {
		this.sizeList = sizeList;
	}

	public ArrayList<File> getfList() {
		return fList;
	}

	public void setfList(ArrayList<File> fList) {
		this.fList = fList;
	}

	public int getFolderCounter() {
		return folderCounter;
	}

	public void setFolderCounter(int folderCounter) {
		this.folderCounter = folderCounter;
	}

	public int getFilec() {
		return filec;
	}

	public void setFilec(int filec) {
		this.filec = filec;
	}

	public File getMyFile() {
		return myFile;
	}

	public void setMyFile(File myFile) {
		this.myFile = myFile;
	}

	public File getMyFile2() {
		return myFile2;
	}

	public void setMyFile2(File myFile2) {
		this.myFile2 = myFile2;
	}

	public File getDestinationFile() {
		return destinationFile;
	}

	public void setDestinationFile(File destinationFile) {
		this.destinationFile = destinationFile;
	}
	
	
	
	 
	 
	 
}
