package com.arkivit.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import javax.xml.bind.SchemaOutputResolver;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.tika.Tika;

import Test.code.Person;
import Test.code.WorkbookExample;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.BoldStyle;
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
	private String excelFileName, folderName = "", confidentialChecked, personalDataChecked;  
	private long fileSize;
	private int fileListeLength;
	private String targetexcelFilepath, backupFilePath;
	private String sourceFolderPath;
	private ArrayList<String> fileNameList = new ArrayList<String>();
	private ArrayList<String> filePathList = new ArrayList<String>();
	private ArrayList<String> fileDecodeList = new ArrayList<String>();
	private ArrayList<Long> sizeList = new ArrayList<Long>();
	private ArrayList<File> fileList = new ArrayList<File>();
	private ArrayList<String> mappedFiles = new ArrayList<String>();
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

		sourceFolderPath = "C:\\Users\\Kevin\\Desktop\\test";
		//sourceFolderPath = "/Users/RobertoBlanco/Desktop/TestFiles";

		init(true,true);
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

			else if(currentFileOrDir.isDirectory())	
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
		//createExcelFile();


		try {
			//createSecondSheet();
			createWorkbook();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	
	private void createWorkbook() throws IOException {
		FileOutputStream streamOut = new FileOutputStream(new File("testfile2.xlsx"));
		SXSSFWorkbook streamWorkbook = new SXSSFWorkbook();
		
		createFirstSheet(streamWorkbook);
		createSecondSheet(streamWorkbook);
		
		streamWorkbook.write(streamOut);
		streamOut.close();
		streamWorkbook.close();
	}
	
	public void createFirstSheet(SXSSFWorkbook streamWorkbook) throws IOException{
		

		List<String> generalHeaderList= new ArrayList<String>();
		generalHeaderList = addGeneralHeadersToList(generalHeaderList);

		Sheet sheet1 = streamWorkbook.createSheet("Allmänt");
		Row rowFirstSheet;
		Row headerFirstSheet = sheet1.createRow(0);
		
		headerFirstSheet.createCell(0).setCellValue("RUBRIK");
		headerFirstSheet.createCell(1).setCellValue("INNEHÅLL");
		
		Cell cell0,cell1 ;
		
		for (int i = 0; i < generalHeaderList.size(); i++) {
			generalHeaderList.get(i);
			rowFirstSheet = sheet1.createRow(i+1);
			cell0 = rowFirstSheet.createCell(0);
			cell1 = rowFirstSheet.createCell(1);
		
			cell0.setCellValue(generalHeaderList.get(i));
			//cell1.setCellValue("INNEHÅLL");

		}
			
	}

	public void createSecondSheet(SXSSFWorkbook streamWorkbook) throws IOException {
		
		FileInfoStorageBean f;
		String fileExtension, sizeInString, fileTypeVersion = "" ,confidentialityColl = "",
				personalInformationHandelingNameColl  ="", commentColl = "";

		List<FileInfoStorageBean> fileContentSheetList = new ArrayList<FileInfoStorageBean>();

		List<String> fileHeaderList = new ArrayList<String>();
		fileHeaderList = addHeadersToList(fileHeaderList);


		for (int i = 0; i < fileList.size(); i++) {
			fileExtension = FilenameUtils.getExtension(fileNameList.get(i));
			sizeInString = Objects.toString(sizeList.get(i), null);

			fileContentSheetList.add(new FileInfoStorageBean(fileNameList.get(i), fileExtension, 
					fileTypeVersion, sizeInString, fileDecodeList.get(i), fileDuration.getAudioVideoList().get(i),
					filePathList.get(i), confidentialityColl, personalInformationHandelingNameColl,commentColl));


		}

		Sheet sheet2 =  streamWorkbook.createSheet("FILER");
		Row rowSecondSheet;
		Row header = sheet2.createRow(0);

		int currentHeader = 0;


		for(String tmp : fileHeaderList)
		{
			header.createCell(currentHeader).setCellValue(tmp);
			currentHeader++;

		}
		
		Cell cell1, cell2, cell3,cell4, cell5, cell6, cell7, cell8, cell9, cell;
		for (int i = 0; i < fileContentSheetList.size(); i++) {
			f = fileContentSheetList.get(i);
			rowSecondSheet = sheet2.createRow(i+1);
			cell = rowSecondSheet.createCell(0);
			cell1 = rowSecondSheet.createCell(1);
			cell2 = rowSecondSheet.createCell(2);
			cell3 = rowSecondSheet.createCell(3);
			cell4 = rowSecondSheet.createCell(4);
			cell5 = rowSecondSheet.createCell(5);
			cell6 = rowSecondSheet.createCell(6);
			cell7 = rowSecondSheet.createCell(7);
			cell8 = rowSecondSheet.createCell(8);
			cell9 = rowSecondSheet.createCell(9);

			cell.setCellValue(f.getFileNameColl());
			cell1.setCellValue(f.getFileTypeNameColl());
			cell2.setCellValue(fileTypeVersion);
			cell3.setCellValue(f.getFileSizeNameColl());
			cell4.setCellValue(f.getCharsetNameColl());
			cell5.setCellValue(f.getDurationColl());
			cell6.setCellValue(f.getFilePathNameColl());
			cell7.setCellValue(confidentialityColl);
			cell8.setCellValue(personalInformationHandelingNameColl);
			cell9.setCellValue(commentColl);

		}

	}


	private List<String> addGeneralHeadersToList(List<String> generalHeaderList) {
		
		generalHeaderList.add("Riksarkivets diarienummer leveransöverenskommelse");
		generalHeaderList.add("Riksarkivets diarienummer leverans");
		generalHeaderList.add("Beskrivning av leveransen");
		generalHeaderList.add("Arkivbildare");
		generalHeaderList.add("Organisationsnummer arkivbildare");
		generalHeaderList.add("Levererande myndighet");
		generalHeaderList.add("Organisationsnummer levererande myndighet");
		generalHeaderList.add("Servicebyrå/Konsult");
		generalHeaderList.add("Kontaktperson för leverans");
		generalHeaderList.add("Telefonnummer till kontaktperson");
		generalHeaderList.add("Kostnadsställe");
		generalHeaderList.add("Kontaktperson för e-fakturering");
		generalHeaderList.add("Arkivets namn");
		generalHeaderList.add("Systemets namn");
		generalHeaderList.add("Uttagsdatum");
		generalHeaderList.add("Kommentar");
		generalHeaderList.add("Projektkod");
		generalHeaderList.add("Accessions-ID");
		generalHeaderList.add("Batch-ID");
		return generalHeaderList;
		
	}


	private List<String> addHeadersToList(List<String> fileHeaderList) 
	{
		fileHeaderList.add("FILNAMN");
		fileHeaderList.add("FILTYP");
		fileHeaderList.add("FILTYPSVERSION");
		fileHeaderList.add("STORLEK (Bytes)");
		fileHeaderList.add("TECKENUPPSÄTTNING");
		fileHeaderList.add("SPELTID (endast audio och video)");
		fileHeaderList.add("SÖKVÄG (path, url)");
		fileHeaderList.add("SEKRETESSGRAD HOS MYNDIGHETEN");
		fileHeaderList.add("BEHANDLING AV PERSONUPPGIFTER");
		fileHeaderList.add("KOMMENTAR");
		
		
		return fileHeaderList;
		
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

		WritableFont redFont = new WritableFont(WritableFont.ARIAL, 9);
		WritableFont boldFont = new WritableFont(WritableFont.ARIAL, 9);

		redFont.setColour(Colour.RED);
		redFont.setBoldStyle(WritableFont.BOLD);

		boldFont.setBoldStyle(WritableFont.BOLD);

		WritableCellFormat fontColor = new WritableCellFormat(redFont);
		WritableCellFormat bold = new WritableCellFormat(boldFont);

		headerLabel = new Label(0, 0, "RUBRIK", bold);
		//headerLabelCol = new Label(0, rowNum+1, tempString);
		archiveDiareNum = new Label(0, 1, "Riksarkivets diarienummer leveransöverenskommelse", fontColor);
		archiveDiareNumDeliv = new Label(0, 2, "Riksarkivets diarienummer leverans", fontColor);
		descDelivery  = new Label(0, 3, "Beskrivning av leveransen", bold); 
		archiveCreator = new Label(0, 4, "Arkivbildare", bold); 
		oNumArchiveCreator = new Label(0, 5, "Organisationsnummer arkivbildare", bold); 
		delivGov = new Label(0, 6, "Levererande myndighet", bold);
		oNumDelivGov = new Label(0, 7, "Organisationsnummer levererande myndighet", bold);
		consultantBureau = new Label(0, 8, "Servicebyrå/Konsult", bold);
		contactPersonDeliv = new Label(0, 9, "Kontaktperson för leverans", bold);
		telContactPerson  = new Label(0, 10, "Telefonnummer till kontaktperson", bold);
		mailContactPerson  = new Label(0, 11, "E-post-adress till kontaktperson", bold);
		costCenter  = new Label(0, 12, "Kostnadsställe", fontColor);
		eBillingContactPerson  = new Label(0, 13, "Kontaktperson för e-fakturering", fontColor);
		archiveName  = new Label(0, 14, "Arkivets namn", bold);
		systemName  = new Label(0, 15, "Systemets namn", bold);
		withdrawalDate  = new Label(0, 16, "Uttagsdatum", bold);
		comment  = new Label(0, 17, "Kommentar", bold);
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

		contentLabel = new Label(1,0,"INNEHÅLL", bold);
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
		Row,Coll,filePathNameRow,filePathNameColl,confidentialityRow, confidentialityColl, personalInformationColl,
		personalInformationHandelingNameRow,commentLabelName,commentRow;
		

		excelSheet.getSettings().setProtected(true);

		WritableFont boldFont = new WritableFont(WritableFont.ARIAL, 9);
		boldFont.setBoldStyle(WritableFont.BOLD);
		WritableCellFormat bold = new WritableCellFormat(boldFont);
		int rowNum = 0;
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

			fileNameRow = new Label(0, 0, "FILNAMN", bold);
			fileNameColl = new Label(0, rowNum+1, filename);

			fileTypeNameRow = new Label(1,0,"FILTYP", bold);
			fileTypeNameColl = new Label(1, rowNum+1, fileExtention);

			fileTypeVersionNameRow = new Label(2,0, "FILTYPSVERSION", bold);
			//Label fileTypeVersionLabel = new Label(2, rowNumber+1,"")

			fileSizeNameRow = new Label(3, 0, "STORLEK (Bytes)", bold);
			fileSizeNameColl = new Label(3, rowNum+1, sizeInString);

			charsetNameRow = new Label(4,0, "TECKENUPPSÄTTNING", bold);
			//charsetNameColl = new Label(4, rowNum+1, decoder.getUtfileList().get(rowNum));
			charsetNameColl = new Label(4, rowNum+1, fileDecodeList.get(rowNum));


			Row = new Label (5,0, "SPELTID (endast audio och video)", bold);
			Coll = new Label(5, rowNum+1, fileDuration.getAudioVideoList().get(rowNum));

			filePathNameRow = new Label(6, 0, "SÖKVÄG (path, url)", bold);
			filePathNameColl = new Label(6, rowNum+1, filePathList.get(rowNum));

			confidentialityRow= new Label(7,0, "SEKRETESSGRAD HOS MYNDIGHETEN", bold);
			confidentialityColl = new Label(7, rowNum+1, confidentialChecked);

			personalInformationHandelingNameRow = new Label(8,0, "BEHANDLING AV PERSONUPPGIFTER", bold);
			personalInformationColl = new Label(8, rowNum+1, personalDataChecked);

			commentLabelName = new Label(9,0, "KOMMENTAR", bold);
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
			excelSheet.addCell(confidentialityColl);

			excelSheet.addCell(personalInformationHandelingNameRow);
			excelSheet.addCell(personalInformationColl);

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
			mappedFiles.add(currentString);
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

	public ArrayList<String> getMappedFiles() {
		return mappedFiles;
	}

	public void setMappedFiles(ArrayList<String> mappedFiles) {
		this.mappedFiles = mappedFiles;
	}
	
	


}
