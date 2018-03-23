package com.arkivit.model;

import java.io.IOException;

public class Converter {

	private String libreOfficeApp = "LibreOffice.app";
	Runtime rt = Runtime.getRuntime();

	public boolean openLibreOffice() {
		try 
		{

			rt.exec("open -a " + libreOfficeApp);
			return true;

		} 
		catch (IOException e) 
		{

			e.printStackTrace();
		}


		return false;
	}

	public void closeLibreOffice() {		
		try 
		{

			rt.exec("pkill -f " + libreOfficeApp);

		} 

		catch (IOException e) 
		{

			e.printStackTrace();
		}

	}

}
