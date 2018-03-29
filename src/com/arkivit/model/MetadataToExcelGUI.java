package com.arkivit.model;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tika.Tika;

import Test.code.DocumentConverter;

/**
 * This class is handling the process of sending data and importing metadata
 * to two excel sheets.
 * 
 * @author RobertoBlanco, Saikat Takluder, Kevin Olofosson
 * @since 2018-01-24
 *
 */
public class MetadataToExcelGUI{

	//private File file;
	private String excelFileName, folderName = "", confidentialChecked = "", personalDataChecked = "";  
	private long fileSize;
	private int fileListeLength, counter = 1;
	private String sourceFolderPath, targetexcelFilepath, backupFilePath, fileExtension = "", fileNameWithOutExt = "";
	private ArrayList<String> fileNameList = new ArrayList<String>();
	private ArrayList<String> filePathList = new ArrayList<String>();
	private ArrayList<String> fileDecodeList = new ArrayList<String>();
	private ArrayList<Long> sizeList = new ArrayList<Long>();
	private ArrayList<File> fileList = new ArrayList<File>();
	private ArrayList<File> mappedFiles = new ArrayList<File>(), mappedFolder = new ArrayList<File>();
	//private ArrayList<String> mappedFile = new ArrayList<String>();
	private ArrayList<String> illegalCharFiles = new ArrayList<String>(), illegarCharFolders = new ArrayList<String>();
	private int fileCount = 0;
	private FileDuration  fileDuration = new FileDuration(); 
	private Tika fileType = new Tika();
	private String duration, fPath, currentFileName, tempString, tempPath, newFileString;
	private CharsetDetector checkDecoder = new CharsetDetector();
	//private ExcelFileCreator ex = new ExcelFileCreator();
	private GeneralBean generalBean = new GeneralBean();
	private Converter converter = new Converter();
	//private DocumentConverter docCon = new DocumentConverter();
	private boolean mapping = false;
	private boolean overwrite = false;
	private boolean isLibreOfficeOpen = false;

	/**
	 * No args constructor
	 */
	public MetadataToExcelGUI()
	{

		//sourceFolderPath = "C:\\Users\\Kevin\\Desktop\\test";
		//sourceFolderPath = "F:\\Skola\\Svenska";
		/*sourceFolderPath = "/Users/RobertoBlanco/Desktop/TestFiles";*/
		//init(mapping,overwrite);
	}

	/**
	 * Constructor with argument.
	 * @param excelFileName The name of the excel file
	 * @throws IOException 
	 */
	public MetadataToExcelGUI(String excelFileName)
	{   

		this.excelFileName = excelFileName + ".xlsx";
		//fileList = new ArrayList<File>();
		//testMeth();
	} 

	/**
	 * Name of source folder instantiated and
	 * if mapping = true the method copyFolder gets called.
	 * listOfFilesAndDirectory and getAndAddFileDataToList get called.
	 * @param mapping A boolean, false by default
	 */
	public void init(boolean mapp, boolean overW) {

		this.mapping = mapp;
		this.overwrite = overW;
		folderName = new File(sourceFolderPath).getName();


		if(mapping && !overwrite) 
		{

			copyFolder();
			//System.out.println("Copying folder.........");
		}


		listOfFilesAndDirectory(sourceFolderPath);
		getAndAddFileDataToList();

		/*if(isLibreOfficeOpen) 
		{
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			converter.closeLibreOffice();
		}*/
		//createExFile();

		//System.out.println(illegalCharFiles.toString());
	}

	//Copying folder to outside of the root folder
	private void copyFolder() {
		File selectedFolder = new File(sourceFolderPath);
		try {

			FileUtils.copyDirectoryToDirectory(selectedFolder, new File(backupFilePath + "/" + folderName + "_backup"));

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	//Clear ArrayList(s) if they aren't empty
	public void clearArrayList() {

		//if(!(fileList.isEmpty() || fileNameList.isEmpty() || sizeList.isEmpty() || filePathList.isEmpty()))
		//{
		fileList.clear();
		fileNameList.clear();
		sizeList.clear();
		filePathList.clear();
		fileDuration.getAudioVideoList().clear();
		illegalCharFiles.clear();
		mappedFiles.clear();
		//}

	}

	/* Goes through folder and subfolders and adding files to an ArrayList.
	 * If mapping = true All files with illegal characters are renamed.
	 * If file is a directory the path will be retrieved.
	 */
	private void listOfFilesAndDirectory(String inputFolder){
		File folder = new File(inputFolder);
		int convertExtCounter = 0;
		File tempFile;
		FileExtension ext = new FileExtension();

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


				if(currentFileOrDir.getName().endsWith(ext.checkForConvertableFileExtensions().get(convertExtCounter))) 
				{
					System.out.println("About to traverse...");
					//docCon = new DocumentConverter();
					//currentFileOrDir = DocumentConverter.getOutdir();
					//DocumentConverter cv = new DocumentConverter(currentFileOrDir);
					DocumentConverter.testMethod(tempFile);
					//System.out.println("Done traversing....");
					//isLibreOfficeOpen = converter.openLibreOffice();
					//tempFile = convertToPDF(currentFileOrDir);

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

				System.out.println("Current Dir : "  + currentFileOrDir.getName());

				listOfFilesAndDirectory(tempFile.getAbsolutePath());
			}
			
		}

	}

	public File doMapping(File currFileOrDir, boolean isDir) {

		File tempFile = null;
		String currFile = "";//replaceIllegalChars(currFileOrDir.getName());


		if(checkIfCurrentFileOrDirContainsIllegalChars(currFileOrDir))
		{
			if(isDir)
			{
				illegarCharFolders.add(currFileOrDir.getName());
			}
			else
			{	
				illegalCharFiles.add(currFileOrDir.getName());
			}

			//addToList(illegarCharMapp, illegalCharFiles, currFileOrDir.getName(),isDir);

			currFile = replaceIllegalChars(currFileOrDir.getName());
			tempFile = new File(currFileOrDir.getParentFile().getAbsolutePath(), currFile);

			checkForFileOrDirAndSeparateWithExt(isDir,tempFile);

			if(tempFile.exists()) {

				tempFile = renameFile(tempFile,isDir,currFileOrDir);

			}

			if(isDir)
			{
				mappedFolder.add(tempFile);
			}
			else
			{	
				mappedFiles.add(tempFile);
			}


			//mappedFiles.add(tempFile);
		}
		else
		{
			tempFile = currFileOrDir;
		}

		currFileOrDir.renameTo(tempFile);

		return tempFile;

	}

	/*private void addToList(ArrayList<String> fileList, ArrayList<String> folderList, String name, boolean isDir) {

		if(isDir)
		{
			fileList.add(name);
		}
		else
		{	
			folderList.add(name);
		}
	}*/

	//If String contains illegal characters they will be replaced and returned.
	private String replaceIllegalChars(String currentString) {

		currentString = StringUtils.replaceEach (currentString, 
				new String[] { "å",  "ä",  "ö",  "ü", "Å",  "Ä",  "Ö", "Ü", " "}, 
				new String[] {"aa", "ae", "oe", "ue","AA", "AE", "OE", "UE", "_"});

		return currentString;
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

	/*
	 * If fileList is not empty:  
	 * 
	 * 1.It will check for certain extensions and call
	 * getFileDecoder() method.
	 * 
	 * 2. Call checkForAudioVideoDuration() method.
	 * 
	 * 3. If getDecoding = null then fileDecodeList adds an empty String.
	 * Else getDecoding().name will be added to fileDecodeList.
	 * 
	 * 4. Adds columns to ArrayLists
	 * 
	 * 5. Calling createExcelFile() method.
	 */
	private void getAndAddFileDataToList() 
	{
		Charset getDecoding;
		sortFileList();
		String fullPathforCurrentFile = "";

		try {
			if(!fileList.isEmpty())
			{
				for(File file : fileList)
				{
					fullPathforCurrentFile = file.getAbsolutePath();
					getDecoding = null;
					if(file.getName().endsWith(".html") || file.getName().endsWith(".xhtml") || file.getName().endsWith(".xml")
							|| file.getName().endsWith(".css") || file.getName().endsWith(".xsd") || file.getName().endsWith(".dtd") 
							|| file.getName().endsWith(".xsl") || file.getName().endsWith(".txt") || file.getName().endsWith(".js")) 
					{
						getDecoding = getFileDecoder(fullPathforCurrentFile);
					}


					if(mapping)
					{
						changeLinkInFile(file);
					}
					
					//DocumentConverter.testMethod(file);

					checkForAudioVideoDuration(file);


					fileSize = file.length();
					fPath = file.getParentFile().getAbsolutePath();
					//System.out.println(fPath);
					fPath = fPath.replace(sourceFolderPath, folderName);

					if(getDecoding == null)
					{
						fileDecodeList.add("");
					}
					else
					{
						fileDecodeList.add(getDecoding.name());
					}

					fileNameList.add(currentFileName);
					sizeList.add(fileSize);
					filePathList.add(fPath);
					fileDuration.getAudioVideoList().add(duration);

				}

			}
			else
			{
				//System.out.println("The list is empty");
			}


		} catch (Exception e) {
			e.printStackTrace();
		}

		fileListeLength = fileNameList.size();

		System.out.println("File name list length : " + fileListeLength);


		try {

			System.out.println("Creating workbook......");
			ExcelFileCreator createExcelF = new ExcelFileCreator(fileDuration, fileNameList, filePathList,
					fileDecodeList, sizeList, fileList, generalBean,targetexcelFilepath, excelFileName, confidentialChecked,personalDataChecked);
			createExcelF.createWorkbook();
			System.out.println("Workbook created!");
		} catch (IOException e) {
			e.printStackTrace();
		} 

	}

	private void changeLinkInFile(File file) throws IOException {

		if(file.getName().endsWith(".html") || file.getName().endsWith(".css") || file.getName().endsWith(".js"))
		{
			List<String> list = new ArrayList<String>();
			FileExtension ext;
			ReadAndUpdateLinks br = new ReadAndUpdateLinks(file.getAbsolutePath());
			list = br.readFileAndAddInfoToList(); 
			int counter = 0;
			String href = "href=\"";
			String endLink = "\"" ;
			String src = "src=\"" ;
			String pathName = "";

			for(File s : mappedFiles) 
			{
				if(!s.isDirectory())
				{
					
					
					ext = new FileExtension(s.getName());
					if(ext.getHtmlCssFileExtension()) 
					{
						if(!s.getParentFile().getName().equals(folderName))
						{
							pathName = s.getParentFile().getName();
						}
						br.updateInfoInFile(illegalCharFiles.get(counter), s.getName(), list, pathName) ;
					}

					if(ext.getJsImgFileExtension())
					{
						if(!s.getParentFile().getName().equals(folderName))
						{
							pathName = s.getParentFile().getName();
						}
						
						br.updateInfoInFile(illegalCharFiles.get(counter), s.getName(), list, s.getParentFile().getName());
					}
				}
				counter++;
			}
			list.clear();
		}

	}

	//Checking what kind of charset the file has
	private Charset getFileDecoder(String fullPathforCurrentFile) {
		File currentFile = new File(fullPathforCurrentFile);

		String[] charsetsToBeTested = {"UTF-8", "windows-1253", "ISO-8859-7"};
		Charset charsetForfile = checkDecoder.detectCharset(currentFile, charsetsToBeTested);

		return charsetForfile;
	}

	/*Checks the duration of a video or an audio file ONLY if
	 * the file is detected as a "video/" or an "audio/" file.
	 */
	private void checkForAudioVideoDuration(File currentfile) {
		duration = "";
		currentFileName = currentfile.getName();
		tempPath = currentfile.getParentFile().getAbsolutePath() + "/"+ currentFileName;
		tempString = checkVideoAudioFiles(tempPath);
		newFileString = tempString.replaceAll(".*/", "");


		if(tempString.equals("video/"+newFileString) || tempString.equals("audio/"+newFileString))
		{

			fileDuration.getDuration(currentfile.getParentFile().getAbsolutePath()
					+ "/" + currentFileName); 

			duration = fileDuration.getAudioVideoDuration();

		} 

	}

	private void sortFileList() {
		//fileList.sort((o1,o2) -> o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase()));
		fileList.sort(new Comparator<File>() {
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

	}

	//Checks what type of file it is and returns the type.
	private String checkVideoAudioFiles(String fileType) {
		return this.fileType.detect(fileType);
	}

	@SuppressWarnings("unused")
	private int getLargestString(List<String> stringList) {

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

		return index;
	}

	public String getFolderName() {
		return folderName;
	}

	public int getFileListeLength() {
		return fileListeLength;
	}

	public ArrayList<String> getFileNameList() {
		return fileNameList;
	}

	public String getExcelFileName() {
		return excelFileName;
	}

	public void setExcelFileName(String excelFileName) {
		this.excelFileName = excelFileName;
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


	public int getFileCount() {
		return fileCount;
	}

	public void setFileCount(int fileCount) {
		this.fileCount = fileCount;
	}

	public String getBackupFilePath() {
		return backupFilePath;
	}

	public void setBackupFilePath(String backupFilePath) {
		this.backupFilePath = backupFilePath;
	}

	public String getConfidentialChecked() {
		return confidentialChecked;
	}

	public void setConfidentialChecked(String confidentialChecked) {
		this.confidentialChecked = confidentialChecked;
	}

	public String getPersonalDataChecked() {
		return personalDataChecked;
	}

	public void setPersonalDataChecked(String personalDataChecked) {
		this.personalDataChecked = personalDataChecked;
	}


	public ArrayList<File> getMappedFiles() {
		return mappedFiles;
	}

	public void setMappedFiles(ArrayList<File> mappedFiles) {
		this.mappedFiles = mappedFiles;
	} 

	public ArrayList<String> getIllegalCharFiles() {
		return illegalCharFiles;
	}

	public void setIllegalCharFiles(ArrayList<String> illegalCharFiles) {
		this.illegalCharFiles = illegalCharFiles;
	}


	public ArrayList<File> getMappedFolder() {
		return mappedFolder;
	}

	public ArrayList<String> getIllegarCharFolders() {
		return illegarCharFolders;
	}

	public GeneralBean getGeneralBean() {
		return generalBean;
	}

	public boolean isLibreOfficeOpen() {
		return isLibreOfficeOpen;
	}


}