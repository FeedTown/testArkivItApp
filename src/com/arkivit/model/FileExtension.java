package com.arkivit.model;

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


	public ArrayList<String> getOfficeFileExtensions() {

		convertExList = new ArrayList<>();

		String docExt = ".doc",
				docxExt = ".docx",
				xlsxExt = ".xlsx",
				xlsExt = ".xls",
				pptExt = ".ppt",
				pptxExt = ".pptx";

		convertExList.add(docExt);
		convertExList.add(docxExt);
		convertExList.add(xlsxExt);
		convertExList.add(xlsExt);
		convertExList.add(pptExt);
		convertExList.add(pptxExt);
		
		for(String e : convertExList) 
		{
			
		}

		return convertExList;

	} 
	

}
