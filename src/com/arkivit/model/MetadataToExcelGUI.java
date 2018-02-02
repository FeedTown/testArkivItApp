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

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;



public class MetadataToExcelGUI {

	private String excelFileName,folderName = ""; //= "standard.xls";
	private long fileSize;
	private String targetexcelFilepath; //= "F:\\backup\\test\\Test_work";  // generated excel file location
	private String sourceFolderPath ;//= "F:\\Skola\\Svenska"; // source Directory
	private ArrayList<String> fileNameList = new ArrayList<String>();
	private ArrayList<String> filePathList = new ArrayList<String>();
	private ArrayList<Long> sizeList = new ArrayList<Long>();
	private ArrayList<File> fList = new ArrayList<File>();
	private int filec = 0;
	private InputStreamReaderDecoder decoder = new InputStreamReaderDecoder();
	private FileDuration fileDuration = new FileDuration();
	private GeneralBean genaralBean = new GeneralBean();

	public MetadataToExcelGUI()
	{
		//fList = new ArrayList<File>();
	}

	public MetadataToExcelGUI(String excelFileName)
	{   
		this.excelFileName = excelFileName + ".xls";
		//fList = new ArrayList<File>();
		//testMeth();
	}

	public void testMeth() {

		folderName = new File(sourceFolderPath).getName();
		listOfFilesAndDirectory(sourceFolderPath);
		testFunc();
	}


	private void clearArrList() {

		if(!(fList.isEmpty() || fileNameList.isEmpty() || sizeList.isEmpty() || filePathList.isEmpty()))
		{
			fList.clear();
			fileNameList.clear();
			sizeList.clear();
			filePathList.clear();
		}
		else
		{
			System.out.println("The list is already empty.");
		}
	}

	private void listOfFilesAndDirectory(String folderPathName)
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

	private void testFunc() 
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
		
		
		String duration,fPath,currentFileName;
		try {
			if(!fList.isEmpty())
			{
				for(File file : fList)
				{
					decoder.fileEncoder(file.getParentFile().getAbsolutePath(), file.getName());  
					duration = "";
					currentFileName = file.getName();
					
					if(currentFileName.endsWith(".mov") || 
							currentFileName.endsWith(".mp4") || 
							currentFileName.endsWith(".mp3") || 
							currentFileName.endsWith("m4v"))
					{
						fileDuration.CheckFileDuration(file.getParentFile().getAbsolutePath()
								+ "/" + currentFileName);
						duration = fileDuration.getAudioVideoDuration();
					}
					
					fileSize = file.length();
					fPath = file.getParentFile().getAbsolutePath();
					fPath = fPath.replace(sourceFolderPath, folderName);
					
					
					fileNameList.add(currentFileName);
					sizeList.add(fileSize);
					filePathList.add(fPath);
					decoder.getUtfList().add(decoder.getUtfString());
					fileDuration.getAudioVideoList().add(duration);

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


		//testForillegelChar();
		createExcelFile();

	}


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
			clearArrList();
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


	private WritableSheet createMetadataExcelSheet(WritableSheet excelSheet) throws RowsExceededException, WriteException  {
		
		String sizeInString,fileExtention,tempString;
		Label fileNameRow,fileNameColl,fileTypeNameRow,fileTypeNameColl,fileTypeVersionNameRow,
		fileTypeVersionNameColl,fileSizeNameRow,fileSizeNameColl,charsetNameRow,charsetNameColl,
		fileDurationRow,fileDurationColl,filePathNameRow,filePathNameColl,confidentialityRow,confidentialityColl,personalInformationHandelingNameRow,
		commentLabelName,commentRow;
		int rowNum = 0;

		for(String filename : fileNameList)
		{
			tempString = replaceIllegalChars(filename);


			sizeInString = Objects.toString(sizeList.get(rowNum), null); 
			fileExtention = FilenameUtils.getExtension(filename);

			fileNameRow = new Label(0, 0, "FILNAMN");

			fileNameColl = new Label(0, rowNum+1, tempString);

			fileTypeNameRow = new Label(1,0,"FILTYP");
			fileTypeNameColl = new Label(1, rowNum+1, fileExtention);

			fileTypeVersionNameRow = new Label(2,0, "FILTYPSVERSION");
			//Label fileTypeVersionLabel = new Label(2, rowNumber+1,"")

			fileSizeNameRow = new Label(3, 0, "STORLEK (Bytes)");
			fileSizeNameColl = new Label(3, rowNum+1, sizeInString);

			charsetNameRow = new Label(4,0, "TECKENUPPSÄTTNING");
			charsetNameColl = new Label(4, rowNum+1, decoder.getUtfList().get(rowNum));


			fileDurationRow = new Label (5,0, "SPELTID(endast audio och video)");
			fileDurationColl = new Label(5, rowNum+1, fileDuration.getAudioVideoList().get(rowNum));

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

			excelSheet.addCell(fileDurationRow);
			excelSheet.addCell(fileDurationColl);

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
	private WritableSheet createGeneralSheet(WritableSheet generalSheet) throws RowsExceededException, WriteException {
		
		int generalListSize = 1;
		
		ArrayList<String> inneHallList = new ArrayList<String>();
		
		
		inneHallList.add(0, "");
		inneHallList.add(1, "");
		inneHallList.add(2, genaralBean.getDescDelivery());
		inneHallList.add(3, genaralBean.getArchiveCreator());
		inneHallList.add(4, genaralBean.getArchiveCreatorNum());
		inneHallList.add(5, genaralBean.getDelivGov());
		inneHallList.add(6, genaralBean.getDelivGovNum());
		inneHallList.add(7, genaralBean.getConsultantBur());
		inneHallList.add(8, genaralBean.getContactDelivPerson());
		inneHallList.add(9, genaralBean.getTelContactPerson());
		inneHallList.add(10, genaralBean.getEmail());
		inneHallList.add(11, "");
		inneHallList.add(12, "");
		inneHallList.add(13, genaralBean.getArchiveName());
		inneHallList.add(14, genaralBean.getSystemName());
		inneHallList.add(15, genaralBean.getWithdrawDate());
		inneHallList.add(16, genaralBean.getComment());
		inneHallList.add(17, "");
		inneHallList.add(18, "");
		inneHallList.add(19, "");
	
		
		
		
		Label headerLabel, contentLabel, archiveDiareNum, 
		archiveDiareNumDeliv,
		descDelivery,
		archiveCreator,
		oNumArchiveCreator,
		delivGov,
		oNumDelivGov,
		consultantBureau,
		contactPersonDeliv,
		telContactPerson,
		mailContactPerson,
		costCenter,
		eBillingContactPerson,
		archiveName,
		systemName,
		withdrawalDate,
		comment,
		projectCode,
		accessId,
		batchId;
		
		
		for(String infoList : inneHallList)
		{
			contentLabel = new Label(1, generalListSize, infoList);
			generalSheet.addCell(contentLabel);
			generalListSize++;
			
		}
		
		headerLabel = new Label(0, 0, "RUBRIK");
		//headerLabelCol = new Label(0, rowNum+1, tempString);
		archiveDiareNum = new Label(0, 1, "Riksarkiverts diarienummer leveransöverkommelse");
		archiveDiareNumDeliv = new Label(0, 2, "Riksarkiverts diarienummer leverans");
		descDelivery  = new Label(0, 3, "Beskrivning av leverans"); 
		archiveCreator = new Label(0, 4, "Arkivbildare"); 
		oNumArchiveCreator = new Label(0, 5, "Organisationsnummer arkivbildare"); 
		delivGov = new Label(0, 6, "Levererande myndighet");
		oNumDelivGov = new Label(0, 7, "Organisationsnummer levererande myndighet");
		consultantBureau = new Label(0, 8, "Servicebyrå/Konsult");
		contactPersonDeliv = new Label(0, 9, "Kontaktperson för leverans");
		telContactPerson  = new Label(0, 10, "Telefonnummer till kontaktperson");
		mailContactPerson  = new Label(0, 11, "E-post till kontaktperson");
		costCenter  = new Label(0, 12, "Kostnadsställe");
		eBillingContactPerson  = new Label(0, 13, "Kontaktperson för e-fakturering");
		archiveName  = new Label(0, 14, "Arkivets namn");
		systemName  = new Label(0, 15, "Systemets namn");
		withdrawalDate  = new Label(0, 16, "Uttagsdatum");
		comment  = new Label(0, 17, "Kommentar");
		projectCode  = new Label(0, 18, "Projektkod");
		accessId  = new Label(0, 19, "Accessions-ID");
		batchId  = new Label(0, 20, "Batch-ID");
		
		generalSheet.setColumnView(0, 40);
		
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
	private String replaceIllegalChars(String currentString) {
		if(currentString.contains("Å") || currentString.contains("Ä") || currentString.contains("Ö")
				|| currentString.contains("å") || currentString.contains("ä") || currentString.contains("ö") 
				|| currentString.contains("Ü") || currentString.contains("ü"))
		{
			currentString = StringUtils.replaceEach (currentString, 
					new String[] { "å",  "ä",  "ö",  "ü", "Å",  "Ä",  "Ö", "Ü", " "}, 
					new String[] {"aa", "ae", "oe", "ue","AA", "AE", "OE", "UE", "_"});
		}

		return currentString;
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
	
	public GeneralBean getGenaralBean() {
		return genaralBean;
	}

	private void junkCodes()
	{

		//This code snipped is from CreateExcelFile function wich is replaced by for-each loop

		//for (int rowNumber = 0; rowNumber < fileNameList.size(); rowNumber++) {


		//tempString = replaceIllegalChars(fileNameList.get(rowNumber));

		/*if(tempString.contains("Å") || tempString.contains("Ä") || tempString.contains("Ö")
					|| tempString.contains("å") || tempString.contains("ä") || tempString.contains("ö") 
					|| tempString.contains("Ü") || tempString.contains("ü"))
			{
				System.out.println("INSIDE replaceILLEGALE");
				tempString = replaceIllegalChars(tempString);
			}*/


		/*
			sizeInString = Objects.toString(sizeList.get(rowNumber), null); 
			fileExtention = FilenameUtils.getExtension(fileNameList.get(rowNumber));
			// FilenameUtils.get

			fileNameRow = new Label(0, 0, "FILNAMN");
			fileNameColl = new Label(0, rowNumber+1, tempString);

			fileTypeNameRow = new Label(1,0,"FILTYP");
			fileTypeNameColl = new Label(1, rowNumber+1, fileExtention);

			fileTypeVersionNameRow = new Label(2,0, "FILTYPSVERSION");
			//Label fileTypeVersionLabel = new Label(2, rowNumber+1,"")

			fileSizeNameRow = new Label(3, 0, "STORLEK (Bytes)");
			fileSizeNameColl = new Label(3, rowNumber+1, sizeInString);


			charsetNameRow = new Label(4,0, "TECKENUPPSÄTTNING");
			charsetNameColl = new Label(4, rowNumber+1, decoder.getUtfList().get(rowNumber));

			fileDurationRow = new Label (5,0, "SPELTID(endast audio och video)");
			fileDurationColl = new Label(5, rowNumber+1, fileDuration.getAudioVideoList().get(rowNumber));

			filePathNameRow = new Label(6, 0, "SÖKVÄG(path,url)");
			filePathNameColl = new Label(6, rowNumber+1, filePathList.get(rowNumber));

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

			excelSheet.addCell(fileDurationRow);
			excelSheet.addCell(fileDurationColl);

			excelSheet.addCell(confidentialityRow);
			//excelSheet.addCell(confidentialityColl);

			excelSheet.addCell(personalInformationHandelingNameRow);
			//excelSheet.addCell(personalInformationHandelingLabel);

			excelSheet.addCell(commentLabelName);
			//excelSheet.addCell(commentLabel);
		 */

		//}
		
		
		/*for (int numberOfFilesInFolder = 0; numberOfFilesInFolder < fList.size(); numberOfFilesInFolder++) 
		{

			decoder.fileEncoder(fList.get(numberOfFilesInFolder).getParentFile().getAbsolutePath(), fList.get(numberOfFilesInFolder).getName());  
			duration = "";
			currentFileName = fList.get(numberOfFilesInFolder).getName();

			if(currentFileName.endsWith(".mov") || 
					currentFileName.endsWith(".mp4") || 
					currentFileName.endsWith(".mp3") || 
					currentFileName.endsWith("m4v"))
			{

				fileDuration.CheckFileDuration(fList.get(numberOfFilesInFolder).getParentFile().getAbsolutePath()
						+ "/" + currentFileName);
				duration = fileDuration.getAudioVideoDuration();

			}

			fileSize = fList.get(numberOfFilesInFolder).length();
			fPath = fList.get(numberOfFilesInFolder).getParentFile().getAbsolutePath();
			fPath = fPath.replace(sourceFolderPath, folderName);

			fileNameList.add(currentFileName);
			sizeList.add(fileSize);
			filePathList.add(fPath);
			decoder.getUtfList().add(decoder.getUtfString());
			fileDuration.getAudioVideoList().add(duration);

			System.out.println("File size: " + fileSize);

		}*/
	}

}
