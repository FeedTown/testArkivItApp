package com.arkivit.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

import javax.xml.bind.SchemaOutputResolver;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tika.Tika;

import Test.code.WorkbookExample;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;


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
	private String excelFileName, folderName = "";  
	private long fileSize;
	private int fileListeLength;
	private String targetexcelFilepath, backupFilePath;
	private String sourceFolderPath;
	private ArrayList<String> fileNameList = new ArrayList<String>();
	private ArrayList<String> filePathList = new ArrayList<String>();
	private ArrayList<String> fileDecodeList = new ArrayList<String>();
	private ArrayList<Long> sizeList = new ArrayList<Long>();
	private ArrayList<File> fileList = new ArrayList<File>();
	private ArrayList<String> pathTest = new ArrayList<String>();
	private int fileCount = 0;
	private FileDuration  fileDuration = new FileDuration();
	private GeneralBean generalBean = new GeneralBean();
	private Tika fileType = new Tika();
	private String duration, fPath, currentFileName, tempString, tempPath, newFileString;
	private CharsetDetector checkDecoder = new CharsetDetector();
	private boolean mapping = false;
	private boolean overwrite = false;

	/**
	 * No args constructor
	 */
	public MetadataToExcelGUI()
	{

	}

	/**
	 * Constructor with argument.
	 * @param excelFileName The name of the excel file
	 */
	public MetadataToExcelGUI(String excelFileName)
	{   
		this.excelFileName = excelFileName + ".xls";
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
			System.out.println("Copying folder.........");
		}

		/*if(mapping && overwrite)
		{
			listOfFilesAndDirectory(sourceFolderPath);
		} */


		listOfFilesAndDirectory(sourceFolderPath);
		for(String s : pathTest)
		{
			System.out.println("Folder paths: " + s);
		}
		getAndAddFileDataToList();
	}


	//Copying folder to outside of the root folder
	private void copyFolder() {
		File selectedFolder = new File(sourceFolderPath);
		try {
			FileUtils.copyDirectoryToDirectory(selectedFolder, new File(backupFilePath + "/" +folderName+ "_backup"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	//Clear ArrayList(s) if they aren't empty
	private void clearArrayList() {

		if(!(fileList.isEmpty() || fileNameList.isEmpty() || sizeList.isEmpty() || filePathList.isEmpty()))
		{
			fileList.clear();
			fileNameList.clear();
			sizeList.clear();
			filePathList.clear();
			fileDuration.getAudioVideoList().clear();
		}
		else
		{
			System.out.println("The list is already empty.");
		}
	}

	/* Goes through folder and subfolders and adding files to an ArrayList.
	 * If mapping = true All files with illegal characters are renamed.
	 * If file is a directory the path will be retrieved.
	 */
	private void listOfFilesAndDirectory(String folderPathName)
	{

		File folder = new File(folderPathName);
		File tempFile;

		for(File currentFileOrDir : folder.listFiles())
		{
			tempFile = currentFileOrDir;
			if(currentFileOrDir.isFile())
			{
				if(mapping)
					tempFile = doMapping(tempFile,currentFileOrDir);


				fileList.add(tempFile);
				System.out.println("Nr " + fileCount + " : " + currentFileOrDir.getName());
				fileCount++;
			}

			if(currentFileOrDir.isDirectory())	
			{
				//pathTest.add(tempFile.getAbsolutePath());

				if(mapping)
					tempFile = doMapping(tempFile,currentFileOrDir);

				listOfFilesAndDirectory(tempFile.getAbsolutePath());


			}

		}

		System.out.println(fileCount);


	}

	private File doMapping(File tempFile, File currFileOrDir) {


		tempFile = new File(currFileOrDir.getParentFile().getAbsolutePath(), replaceIllegalChars(currFileOrDir.getName()));
		currFileOrDir.renameTo(tempFile);

		return tempFile;


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
							|| file.getName().endsWith(".xsl") || file.getName().endsWith(".txt") || file.getName().endsWith(".js")) {
						getDecoding = getFileDecoder(fullPathforCurrentFile);

					}

					checkForAudioVideoDuration(file);

					fileSize = file.length();
					fPath = file.getParentFile().getAbsolutePath();
					System.out.println(fPath);
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

					System.out.println("File size: " + fileSize);


				}

			}
			else
			{
				System.out.println("The list is empty");
			}


		} catch (Exception e) {
			e.printStackTrace();
		}

		fileListeLength = fileNameList.size();

		System.out.println("File name list length : " + fileListeLength);
		createExcelFile();

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

	public void createExcelFilePOI() throws IOException {

		FileOutputStream inMemoryOut = 
				new FileOutputStream(new File(targetexcelFilepath +"/"+ excelFileName));
		XSSFWorkbook workbook = new XSSFWorkbook();
		WorkbookExample example = new WorkbookExample(workbook, inMemoryOut);
		example.export();




	}

	/*
	 * Instantiates source path and file name.
	 * Creates the excel sheets and adds fileNameList to them if !fileNameList. 
	 */
	private void createExcelFile() {

		File file = new File(targetexcelFilepath +"/"+ excelFileName);

		try {
			System.out.println("createExcelFile");
			WorkbookSettings wbSettings = new WorkbookSettings();
			WritableWorkbook workbook = Workbook.createWorkbook(file,
					wbSettings);
			workbook.createSheet("Allmänt", 0);
			workbook.createSheet("Filer", 1);
			System.out.println("Excel file is created in path -- "
					+ targetexcelFilepath);

			WritableSheet generalSheet = (WritableSheet) workbook.getSheet(0);
			WritableSheet excelSheet = (WritableSheet) workbook.getSheet(1);

			if (!fileNameList.isEmpty()) {
				generalSheet = createGeneralSheet(generalSheet);
				excelSheet = createMetadataExcelSheet(excelSheet);

			} else {
				System.out.println("No matching files found");
			}
			workbook.write();
			workbook.close();
			clearArrayList();
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (WriteException e) { 
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}



	/*
	 * Creates labels and adds them as column names and adds user input data into
	 * specific rows.
	 */
	private WritableSheet createGeneralSheet(WritableSheet generalSheet) throws RowsExceededException, WriteException {

		int generalListSize = 1;
		ArrayList<String> contentList = new ArrayList<String>();

		contentList.add(0, "");
		contentList.add(1, "");
		contentList.add(2, generalBean.getDescDelivery());
		contentList.add(3, generalBean.getArchiveCreator());
		contentList.add(4, generalBean.getArchiveCreatorNum());
		contentList.add(5, generalBean.getDelivGov());
		contentList.add(6, generalBean.getDelivGovNum());
		contentList.add(7, generalBean.getConsultantBur());
		contentList.add(8, generalBean.getContactDelivPerson());
		contentList.add(9, generalBean.getTelContactPerson());
		contentList.add(10, generalBean.getEmail());
		contentList.add(11, "");
		contentList.add(12, "");
		contentList.add(13, generalBean.getArchiveName());
		contentList.add(14, generalBean.getSystemName());
		contentList.add(15, generalBean.getDate());
		contentList.add(16, generalBean.getComment());
		contentList.add(17, "");
		contentList.add(18, "");
		contentList.add(19, "");




		Label headerLabel, contentLabel, archiveDiareNum, archiveDiareNumDeliv, descDelivery, archiveCreator,
		oNumArchiveCreator, delivGov, oNumDelivGov, consultantBureau, contactPersonDeliv, telContactPerson,
		mailContactPerson, costCenter, eBillingContactPerson, archiveName, systemName, withdrawalDate,
		comment, projectCode, accessId, batchId;

		generalSheet.getSettings().setProtected(true);
		WritableCellFormat unLocked = new WritableCellFormat();
		unLocked.setLocked(false);

		for(String infoList : contentList)
		{
			contentLabel = new Label(1, generalListSize, infoList, unLocked);
			generalSheet.addCell(contentLabel);
			generalListSize++;

		}

		WritableFont redFont = new WritableFont(WritableFont.ARIAL, 10);
		redFont.setColour(Colour.RED);

		WritableCellFormat fontColor = new WritableCellFormat(redFont);

		headerLabel = new Label(0, 0, "RUBRIK");
		//headerLabelCol = new Label(0, rowNum+1, tempString);
		archiveDiareNum = new Label(0, 1, "Riksarkiverts diarienummer leveransöverkommelse", fontColor);
		archiveDiareNumDeliv = new Label(0, 2, "Riksarkiverts diarienummer leverans", fontColor);
		descDelivery  = new Label(0, 3, "Beskrivning av leverans"); 
		archiveCreator = new Label(0, 4, "Arkivbildare"); 
		oNumArchiveCreator = new Label(0, 5, "Organisationsnummer arkivbildare"); 
		delivGov = new Label(0, 6, "Levererande myndighet");
		oNumDelivGov = new Label(0, 7, "Organisationsnummer levererande myndighet");
		consultantBureau = new Label(0, 8, "Servicebyrå/Konsult");
		contactPersonDeliv = new Label(0, 9, "Kontaktperson för leverans");
		telContactPerson  = new Label(0, 10, "Telefonnummer till kontaktperson");
		mailContactPerson  = new Label(0, 11, "E-post till kontaktperson");
		costCenter  = new Label(0, 12, "Kostnadsställe", fontColor);
		eBillingContactPerson  = new Label(0, 13, "Kontaktperson för e-fakturering", fontColor);
		archiveName  = new Label(0, 14, "Arkivets namn");
		systemName  = new Label(0, 15, "Systemets namn");
		withdrawalDate  = new Label(0, 16, "Uttagsdatum");
		comment  = new Label(0, 17, "Kommentar");
		projectCode  = new Label(0, 18, "Projektkod", fontColor);
		accessId  = new Label(0, 19, "Accessions-ID", fontColor);
		batchId  = new Label(0, 20, "Batch-ID", fontColor);

		generalSheet.setColumnView(0, 40);
		generalSheet.setColumnView(1, getLargestString(contentList));

		generalSheet.addCell(headerLabel);
		generalSheet.addCell(archiveDiareNum);
		generalSheet.addCell(archiveDiareNumDeliv);
		generalSheet.addCell(descDelivery);
		generalSheet.addCell(archiveCreator);
		generalSheet.addCell(oNumArchiveCreator);
		generalSheet.addCell(delivGov);
		generalSheet.addCell(oNumDelivGov);
		generalSheet.addCell(consultantBureau);
		generalSheet.addCell(contactPersonDeliv);
		generalSheet.addCell(telContactPerson);
		generalSheet.addCell(mailContactPerson);
		generalSheet.addCell(costCenter);
		generalSheet.addCell(eBillingContactPerson);
		generalSheet.addCell(archiveName);
		generalSheet.addCell(systemName);
		generalSheet.addCell(withdrawalDate);
		generalSheet.addCell(comment);
		generalSheet.addCell(projectCode);
		generalSheet.addCell(accessId);
		generalSheet.addCell(batchId);

		contentLabel = new Label(1,0,"INNEHÅLL");
		generalSheet.addCell(contentLabel);
		//contentLabelCol = new Label(1, rowNum+1, fileExtention);
		return generalSheet;


	}

	/*
	 * Creates labels and adds them as column names and adds specific data into
	 * the specific columns.
	 */
	@SuppressWarnings("unused")
	private WritableSheet createMetadataExcelSheet(WritableSheet excelSheet) throws RowsExceededException, WriteException  {

		String sizeInString,fileExtention,tempString,tempString2;
		Label fileNameRow,fileNameColl,fileTypeNameRow,fileTypeNameColl,fileTypeVersionNameRow,
		fileTypeVersionNameColl,fileSizeNameRow,fileSizeNameColl,charsetNameRow,charsetNameColl,
		Row,Coll,filePathNameRow,filePathNameColl,confidentialityRow,confidentialityColl,
		personalInformationHandelingNameRow,commentLabelName,commentRow;
		int rowNum = 0;

		excelSheet.getSettings().setProtected(true);

		for(String filename : fileNameList)
		{

			/*if(mapping) {
				tempString = replaceIllegalChars(filename);
				//tempString = replaceIllegalChars(filePathList.toString());
			}
			else {
				tempString = filename;
				//tempString = filePathList.toString();
			}*/

			sizeInString = Objects.toString(sizeList.get(rowNum), null); 
			fileExtention = FilenameUtils.getExtension(filename);

			fileNameRow = new Label(0, 0, "FILNAMN");
			fileNameColl = new Label(0, rowNum+1, filename);

			fileTypeNameRow = new Label(1,0,"FILTYP");
			fileTypeNameColl = new Label(1, rowNum+1, fileExtention);

			fileTypeVersionNameRow = new Label(2,0, "FILTYPSVERSION");
			//Label fileTypeVersionLabel = new Label(2, rowNumber+1,"")

			fileSizeNameRow = new Label(3, 0, "STORLEK (Bytes)");
			fileSizeNameColl = new Label(3, rowNum+1, sizeInString);

			charsetNameRow = new Label(4,0, "TECKENUPPSÄTTNING");
			//charsetNameColl = new Label(4, rowNum+1, decoder.getUtfileList().get(rowNum));
			charsetNameColl = new Label(4, rowNum+1, fileDecodeList.get(rowNum));


			Row = new Label (5,0, "SPELTID(endast audio och video)");
			Coll = new Label(5, rowNum+1, fileDuration.getAudioVideoList().get(rowNum));

			filePathNameRow = new Label(6, 0, "SÖKVÄG(path,url)");
			filePathNameColl = new Label(6, rowNum+1, filePathList.get(rowNum));

			confidentialityRow = new Label(7,0, "SEKRETESSGRAD HOS MYNDIGHETEN");
			//Label confidentialityLabel = new Label(7, rowNumber+1,"");

			personalInformationHandelingNameRow = new Label(8,0, "BEHANDLING AV PERSONUPPGIFTER");
			//Label personalInformationHandelingLabel = new Label(8, rowNumber+1, "");

			commentLabelName = new Label(9,0, "KOMMENTAR");
			//Label commentLabel = new Label(9, rowNumber+1, "");

			excelSheet.setColumnView(0, getLargestString(fileNameList));
			excelSheet.setColumnView(2, 16);
			excelSheet.setColumnView(4, 20);
			excelSheet.setColumnView(5, 27);
			excelSheet.setColumnView(6, getLargestString(filePathList));
			excelSheet.setColumnView(7, 33);
			excelSheet.setColumnView(8, 33);
			excelSheet.setColumnView(9, 13);

			excelSheet.addCell(fileNameRow);
			excelSheet.addCell(fileNameColl);

			excelSheet.addCell(fileTypeNameRow);
			excelSheet.addCell(fileTypeNameColl);

			excelSheet.addCell(filePathNameRow);
			excelSheet.addCell(filePathNameColl);

			excelSheet.addCell(fileTypeVersionNameRow);
			//excelSheet.addCell(fileTypeVersionLabel);

			excelSheet.addCell(fileSizeNameRow);
			excelSheet.addCell(fileSizeNameColl);

			excelSheet.addCell(charsetNameRow);
			excelSheet.addCell(charsetNameColl);

			excelSheet.addCell(Row);
			excelSheet.addCell(Coll);

			excelSheet.addCell(confidentialityRow);
			//excelSheet.addCell(confidentialityColl);

			excelSheet.addCell(personalInformationHandelingNameRow);
			//excelSheet.addCell(personalInformationHandelingLabel);

			excelSheet.addCell(commentLabelName);
			//excelSheet.addCell(commentLabel);
			rowNum++;
		}

		return excelSheet;


	}

	//If String contains illegal characters they will be replaced and returned.
	private String replaceIllegalChars(String currentString) {
		if(currentString.contains("å") || currentString.contains("ä") || currentString.contains("ö")
				|| currentString.contains("ü") || currentString.contains("Å") || currentString.contains("Ä") 
				|| currentString.contains("Ö") || currentString.contains("Ü"))
		{
			currentString = StringUtils.replaceEach (currentString, 
					new String[] { "å",  "ä",  "ö",  "ü", "Å",  "Ä",  "Ö", "Ü", " "}, 
					new String[] {"aa", "ae", "oe", "ue","AA", "AE", "OE", "UE", "_"});
		}

		return currentString;
	}

	@SuppressWarnings("unused")
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

	public GeneralBean getGeneralBean() {
		return generalBean;
	}

	public String getBackupFilePath() {
		return backupFilePath;
	}

	public void setBackupFilePath(String backupFilePath) {
		this.backupFilePath = backupFilePath;
	}


}
