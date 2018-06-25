package com.arkivit.model;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tika.Tika;
import com.arkivit.view.FirstScene;


/**
 * This class is handling the process of sending data and importing metadata
 * to two excel sheets.
 * 
 * @author RobertoBlanco, Saikat Takluder, Kevin Olofosson
 * @since 2018-01-24
 *
 */
public class MetadataToExcelGUI{

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
	private ArrayList<String> illegalCharFiles = new ArrayList<String>(), illegarCharFolders = new ArrayList<String>();
	private ArrayList<File> convertedFiles = new ArrayList<File>();
	private int fileCount = 0;
	private int count;
	private FileDuration  fileDuration = new FileDuration(); 
	private Tika fileType = new Tika();
	private String duration, fPath, currentFileName, tempString, tempPath, newFileString;
	private CharsetDetector checkDecoder = new CharsetDetector();
	private GeneralBean generalBean = new GeneralBean();
	private DocumentConverter docCon = new DocumentConverter();
	private ImageFileConverter img = new ImageFileConverter();
	private FileExtension officeFileEx = new FileExtension();
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
		//sourceFolderPath = "/Users/RobertoBlanco/Desktop/TestFiles";
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
	 * @throws IOException 
	 * @throws TranscoderException 
	 */
	public void init(boolean mapp, boolean overW) throws IOException{
		
		
		
		this.mapping = mapp;
		this.overwrite = overW;
		folderName = new File(sourceFolderPath).getName();


		if(mapping && !overwrite) 
		{
			copyFolder();
		}

		docCon.libreOfficeConnectionMethod(sourceFolderPath);
		deleteOfficeFiles(sourceFolderPath);
		//img.convertImage(sourceFolderPath);
		//deleteIllegalImageFiles(sourceFolderPath);
		
		listOfFilesAndDirectory(sourceFolderPath);
		
		getAndAddFileDataToList();


		getAndAddFileDataToList();
		closeLibreOffice();
		
	}
	
	public void deleteOfficeFiles(String officePath) 
	{
		ArrayList<File> deletedOfficeFilesList = new ArrayList<>();

		for(File f : docCon.getOriginalListFile()) 
		{

			if(f.getName().endsWith(".doc") || f.getName().endsWith(".DOC") || 
					f.getName().endsWith(".docx") || f.getName().endsWith(".DOCX") ||
					f.getName().endsWith(".xls") || f.getName().endsWith(".XLS") ||
					f.getName().endsWith(".xlsx") || f.getName().endsWith(".XLSX") ||
					f.getName().endsWith(".ppt") || f.getName().endsWith(".PPT") ||
					f.getName().endsWith(".pptx") || f.getName().endsWith(".PPTX"))
			{

				deletedOfficeFilesList.remove(f);
				f.delete();
			}


		}
	}

	public void deleteIllegalImageFiles(String imagePath) {

		ArrayList<File> deletedImageFilesList = new ArrayList<>();

		for(File f : img.getOrignalImageFileList()) {

			if(f.getName().endsWith(".gif") || f.getName().endsWith(".GIF") || 
					f.getName().endsWith(".jpg") || f.getName().endsWith(".JPG") ||
					f.getName().endsWith(".bmp") || f.getName().endsWith(".BMP") || 
					f.getName().endsWith(".wbmp") || f.getName().endsWith("WBMP") ||
					f.getName().endsWith(".ico") || f.getName().endsWith(".ICO") ||
					f.getName().endsWith(".svg") || f.getName().endsWith(".SVG")) {

				deletedImageFilesList.remove(f);
				f.delete();
			}

		} 

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

		fileList.clear();
		fileNameList.clear();
		sizeList.clear();
		filePathList.clear();
		fileDuration.getAudioVideoList().clear();
		illegalCharFiles.clear();
		mappedFiles.clear();

	}

	/* Goes through folder and subfolders and adding files to an ArrayList.
	 * If mapping = true All files with illegal characters are renamed.
	 * If file is a directory the path will be retrieved.a
	 */
	private void listOfFilesAndDirectory(String inputFolder) throws IOException {
		File folder = new File(inputFolder);
		File tempFile;
		

		for(File currentFileOrDir : folder.listFiles())
		{


			tempFile = currentFileOrDir;

			//img.convertImage(fileList, sourceFolderPath);

			if(currentFileOrDir.isFile())
			{
				if(tempFile.getName().endsWith(".gif") || tempFile.getName().endsWith(".GIF") || 
						tempFile.getName().endsWith(".jpg") || tempFile.getName().endsWith(".JPG") ||
						tempFile.getName().endsWith(".bmp") || tempFile.getName().endsWith(".BMP") || 
						tempFile.getName().endsWith(".wbmp") || tempFile.getName().endsWith("WBMP") ||
						tempFile.getName().endsWith(".ico") || tempFile.getName().endsWith(".ICO") ||
						tempFile.getName().endsWith(".svg") || tempFile.getName().endsWith(".SVG")) 
				{
					tempFile = img.convertImage1(tempFile);	
				}
				
				if(mapping)
				{
					tempFile = doMapping(currentFileOrDir,false);
				}
				
				

				fileList.add(tempFile);
				System.out.println("Current File : "  + tempFile.getName());
				System.out.println("Nr " + fileCount + " : " + currentFileOrDir.getName());
				fileCount++;

			}

			else if(currentFileOrDir.isDirectory())	
			{
				
				if(mapping)
				{
					tempFile = doMapping(currentFileOrDir,true);
				}

				System.out.println("Current Dir : "  + currentFileOrDir.getName());

				listOfFilesAndDirectory(tempFile.getAbsolutePath());
			}
			count++;

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
		}
		else
		{
			tempFile = currFileOrDir;
		}

		currFileOrDir.renameTo(tempFile);

		return tempFile;

	}

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


					checkForAudioVideoDuration(file);

					fileSize = file.length();
					fPath = file.getParentFile().getAbsolutePath();
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


		} catch (Exception e) {
			e.printStackTrace();
		}

		fileListeLength = fileNameList.size();

		System.out.println("File name list length : " + fileListeLength);


		//System.out.println("Last list check....:" + fileNameList);
		try {

			System.out.println("Creating workbook......");
			ExcelFileCreator createExcelF = new ExcelFileCreator(fileDuration, fileNameList , filePathList,
					fileDecodeList, sizeList, fileList,  generalBean,targetexcelFilepath, excelFileName, confidentialChecked,personalDataChecked);
			createExcelF.createWorkbook();
			//hibSession();
			System.out.println("Workbook created!");

		} catch (IOException e) {
			e.printStackTrace();
		} 
		
	}

	private void changeLinkInFile(File file) throws IOException {

		if(file.getName().endsWith(".html") || file.getName().endsWith(".css") || file.getName().endsWith(".js"))
		{
			
			String fileExt = FilenameUtils.getExtension(file.getName());
			List<String> list = new ArrayList<String>();
			FileExtension ext;
			ReadAndUpdateLinks br = new ReadAndUpdateLinks(file.getAbsolutePath());
			list = br.readFileAndAddInfoToList(); 
			int counter = 0;

			for(File s : mappedFiles) 
			{
				if(!s.isDirectory())
				{
					ext = new FileExtension(s.getName());

					if(ext.getHtmlCssFileExtension()) 
					{
						br.updateInfoInFile(illegalCharFiles.get(counter), s.getName(), list, fileExt) ;
					}

					if(ext.getJsImgFileExtension())
					{
						br.updateInfoInFile(illegalCharFiles.get(counter), s.getName(), list, fileExt);

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
	
	public void closeLibreOffice() {
		Runtime rt = Runtime.getRuntime();
		String libreOfficeApp = "LibreOffice.app";
		String  osName;
		
		try 

		{
			osName = System.getProperty("os.name");
			//test(p.getInputStream());
			if(osName.contains("Windows"))
			{
				rt.exec("taskkill /IM soffice.bin");
			}
			else if(osName.contains("Mac") || osName.contains("Ubuntu") || osName.contains("Debian"))
			{
				rt.exec("pkill -f " + libreOfficeApp);
			}


		} 

		catch (IOException e) 
		{

			e.printStackTrace();

		} 
		
		
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

	public ArrayList<File> getConvertedFiles(){
		return convertedFiles;
	}

	public void setConvertedFiles(ArrayList<File> convertedFiles) {
		this.convertedFiles = convertedFiles;
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

	public ArrayList<File> getFileList() {
		return fileList;
	}

	public boolean isLibreOfficeOpen() {
		return isLibreOfficeOpen;
	}

}