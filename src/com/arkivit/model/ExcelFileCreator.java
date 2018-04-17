package com.arkivit.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public class ExcelFileCreator {

	GeneralBean generalBean;
	FileDuration fileDuration = new FileDuration();
	private ArrayList<String> fileNameList = new ArrayList<String>();
	private ArrayList<String> filePathList = new ArrayList<String>();
	private ArrayList<String> fileDecodeList = new ArrayList<String>();
	private ArrayList<Long> sizeList = new ArrayList<Long>();
	private ArrayList<File> fileList = new ArrayList<File>();
	private ArrayList<File> converterFileList = new ArrayList<File>();
	ArrayList<String> contentList = new ArrayList<>();
	private String targetexcelFilepath, excelFileName, confidentialChecked = "", personalDataChecked = ""; 

	
	public ExcelFileCreator() 
	{
		
	}
	
	
	public ExcelFileCreator(FileDuration fileDuration, ArrayList<String> fileNameList,
			ArrayList<String> filePathList, ArrayList<String> fileDecodeList, ArrayList<Long> sizeList,
			ArrayList<File> fileList, GeneralBean generalBean, String targetexcelFilepath, String excelFileName
			, String confidentialChecked, String personalDataChecked) 
	{
		
		
		this.fileDuration = fileDuration;
		this.fileNameList = fileNameList;
		this.filePathList = filePathList;
		this.fileDecodeList = fileDecodeList;
		this.sizeList = sizeList;
		this.fileList = fileList;
		this.targetexcelFilepath = targetexcelFilepath;
		this.excelFileName = excelFileName;
		this.generalBean = generalBean;
		this.confidentialChecked = confidentialChecked;
		this.personalDataChecked = personalDataChecked;
	

	}
	
	public void createWorkbook() throws IOException {
		
		FileOutputStream streamOut = new FileOutputStream(new File(targetexcelFilepath +"/"+ excelFileName));
		SXSSFWorkbook streamWorkbook = new SXSSFWorkbook();
		
		createFirstSheet(streamWorkbook);
		createSecondSheet(streamWorkbook);

		streamWorkbook.write(streamOut);
		streamOut.close();
		streamWorkbook.close();
		System.out.println("End of Workbook method");
		//clearArrayList();
	}

	private CellStyle createRedAndBoldFont(SXSSFWorkbook streamWorkbook) 
	{
		
		CellStyle style = streamWorkbook.createCellStyle();
		Font redAndBoldFont = streamWorkbook.createFont();
		
		redAndBoldFont.setFontHeightInPoints((short)9);
		redAndBoldFont.setFontName("Arial");
		redAndBoldFont.setColor(Font.COLOR_RED);
		redAndBoldFont.setBold(true);
		style.setFont(redAndBoldFont);
		
		return style;

	}

	private CellStyle createBoldFont(SXSSFWorkbook streamWorkbook) 
	{
		CellStyle style = streamWorkbook.createCellStyle();
		Font boldFont = streamWorkbook.createFont();
		boldFont.setFontHeightInPoints((short)9);
		boldFont.setFontName("Arial");
		boldFont.setColor(Font.COLOR_NORMAL);
		boldFont.setBold(true);
		style.setFont(boldFont);
		
		return style;

	}

	private CellStyle unLocked(SXSSFWorkbook streamWorkbook) 
	{
		CellStyle locked = streamWorkbook.createCellStyle();
		locked.setLocked(false);
		
		return locked;
	}
	
	private void createFirstSheet(SXSSFWorkbook streamWorkbook) throws IOException
	{

		SXSSFSheet sheet1 = streamWorkbook.createSheet("Allmänt");
		sheet1.protectSheet("");
		Row headerFirstSheet = sheet1.createRow(0);
		Row rowFirstSheet = sheet1.createRow(1);

		headerFirstSheet.createCell(0).setCellValue("RUBRIK");
		headerFirstSheet.createCell(1).setCellValue("INNEHÅLL");
		headerFirstSheet.getCell(0).setCellStyle(createBoldFont(streamWorkbook));
		headerFirstSheet.getCell(1).setCellStyle(createBoldFont(streamWorkbook));

		for (int row = 0; row < getGeneralHeaders().size(); row++) {

			getGeneralHeaders().get(row);
			rowFirstSheet = sheet1.createRow(row+1);
			rowFirstSheet.createCell(0).setCellValue(getGeneralHeaders().get(row));
			rowFirstSheet.createCell(1).setCellValue(getContentList().get(row));

			if(row == 0 || row == 1 || row == 11 || row == 12 || row == 17 || row == 18
					|| row == 19)
			{
				rowFirstSheet.getCell(0).setCellStyle(createRedAndBoldFont(streamWorkbook));
			}
			else
			{
				rowFirstSheet.getCell(0).setCellStyle(createBoldFont(streamWorkbook));
			}


			for (int colNb = 0; colNb < 2; colNb++) {
				sheet1.trackColumnForAutoSizing(colNb);
				sheet1.autoSizeColumn(colNb);
			}

			rowFirstSheet.getCell(1).setCellStyle(unLocked(streamWorkbook));

		}

	}

	private void createSecondSheet(SXSSFWorkbook streamWorkbook) throws IOException 
	{

		FileInfoStorageBean f;
		String fileExtension, sizeInString, fileTypeVersion = "" ,confidentialityColl =  confidentialChecked,
				personalInformationHandelingNameColl  = personalDataChecked, commentColl = "";

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

		SXSSFSheet sheet2 =  streamWorkbook.createSheet("Filer");
		sheet2.protectSheet("");

		Row header = sheet2.createRow(0);

		int currentHeader = 0;

		for(String tmp : fileHeaderList)
		{
			header.createCell(currentHeader).setCellValue(tmp);
			header.getCell(currentHeader).setCellStyle(createBoldFont(streamWorkbook));
			currentHeader++;

		}

		for (int rowNb = 0; rowNb < fileContentSheetList.size(); rowNb++) 
		{
			f = fileContentSheetList.get(rowNb);   
			Row row = sheet2.createRow(rowNb+1);

			for (int colNb = 0; colNb < 10; colNb++) {
				Cell cell = row.getCell(colNb);

				if(cell == null)
				{
					cell = row.createCell(colNb);
				} 

				if (colNb==0)
				{
					cell.setCellValue(f.getFileNameColl()); 
				}
				else if(colNb==1) {
					cell.setCellValue(f.getFileTypeNameColl());
				}
				else if(colNb==2) {
					cell.setCellValue(fileTypeVersion);
				}
				else if(colNb==3) {
					cell.setCellValue(f.getFileSizeNameColl());
				}
				else if(colNb==4) {
					cell.setCellValue(f.getCharsetNameColl());
				}
				else if(colNb==5) { 
					cell.setCellValue(f.getDurationColl());
				}
				else if(colNb==6) {
					cell.setCellValue(f.getFilePathNameColl());
				}
				else if(colNb==7){
					cell.setCellValue(confidentialChecked);
				}
				else if(colNb==8) { 
					cell.setCellValue(personalDataChecked);
				}
				else if(colNb==9) {
					cell.setCellValue(commentColl);
				}
				sheet2.trackColumnForAutoSizing(colNb);
				sheet2.autoSizeColumn(colNb);
				row.getCell(colNb).setCellStyle(unLocked(streamWorkbook));
			}
		}  
	} 




	private List<String> getGeneralHeaders() 
	{
		List<String> generalHeaderList= new ArrayList<String>();
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
		generalHeaderList.add("E-post-adress till kontaktperson");
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

	public List<String> getContentList()
	{
		List<String> contentList = new ArrayList<>();
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

		return contentList;	
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
	
	public GeneralBean getGeneralBean() {
		return generalBean;
	}

	public void setGeneralBean(GeneralBean generalBean) {
		this.generalBean = generalBean;
	}
}
