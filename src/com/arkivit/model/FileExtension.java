package com.arkivit.model;

import java.io.File;
import java.util.ArrayList;

public class FileExtension {

	private String getFileName;
	ArrayList<String> convertExList;

	public FileExtension() {

	}

	public FileExtension(String getFileName)
	{
		this.getFileName = getFileName;
	}

	public boolean getHtmlCssFileExtension()
	{
		String htmlExt = ".html", cssExt = ".css";
		//getExtension = checkForExtension(getFileName);
		if(getFileName.endsWith(htmlExt) || getFileName.endsWith(cssExt))
		{
			return true;
		}
		else 
		{
			return false;
		}


	}

	public boolean getJsImgFileExtension()
	{
		return checkForExtension();
	}

	private boolean checkForExtension() {
		String imgExt = ".img", 
				jsExt = ".js", 
				jpegExt = ".jpeg", 
				jpgExt = ".jpg", 
				pngExt = ".png", 
				gifExt = ".gif",
				pdfExt = ".pdf"
				/*docExt = ".doc",
				docxExt = ".docx"*/;


		if(getFileName.endsWith(pdfExt) || getFileName.endsWith(gifExt) || getFileName.endsWith(pngExt) || getFileName.endsWith(jpgExt) ||
				getFileName.endsWith(jpegExt) || getFileName.endsWith(jsExt) || getFileName.endsWith(imgExt))
		{
			return true;
		}
		else 
		{
			return false;
		}

	}

	public String getOfficeExtensions()
	{
		convertExList = new ArrayList<>();		
		String tempEx;
		for(String f : convertExList)
		{	
			if(f.endsWith(".doc") || f.endsWith(".DOC") || 
					f.endsWith(".docx") || f.endsWith(".DOCX") ||
					f.endsWith(".xls") || f.endsWith(".XLS") ||
					f.endsWith(".xlsx") || f.endsWith(".XLSX") ||
					f.endsWith(".ppt") || f.endsWith(".PPT") ||
					f.endsWith(".pptx") || f.endsWith(".PPTX"))
			{
				f = getFileName;
				convertExList.add(getFileName);
			}
			
			/*if(f.getName().endsWith(".doc") || f.getName().endsWith(".DOC") || 
					f.getName().endsWith(".docx") || f.getName().endsWith(".DOCX") ||
					f.getName().endsWith(".xls") || f.getName().endsWith(".XLS") ||
					f.getName().endsWith(".xlsx") || f.getName().endsWith(".XLSX") ||
					f.getName().endsWith(".ppt") || f.getName().endsWith(".PPT") ||
					f.getName().endsWith(".pptx") || f.getName().endsWith(".PPTX"))
			{
				convertExList.add(f);
			} */
			return getFileName;
		}
		return getFileName;


	}


	public boolean hasFileOfficeExtension() {

		//convertExList = new ArrayList<>();

		String docExt = ".doc",
				docxExt = ".docx",
				xlsxExt = ".xlsx",
				xlsExt = ".xls",
				pptExt = ".ppt",
				pptxExt = ".pptx";

		/*convertExList.add(docExt);
		convertExList.add(docxExt);
		convertExList.add(xlsxExt);
		convertExList.add(xlsExt);
		convertExList.add(pptExt);
		convertExList.add(pptxExt);*/


		if(getFileName.endsWith(docExt) || getFileName.endsWith(docxExt) || getFileName.endsWith(xlsxExt) || getFileName.endsWith(xlsExt) ||
				getFileName.endsWith(pptExt) || getFileName.endsWith(pptxExt))
		{
			return true;
		}
		else 
		{
			return false;
		}


	} 


}
