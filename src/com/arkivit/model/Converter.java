package com.arkivit.model;

import java.io.IOException;

public class Converter {
	private String libreOfficeApp = "LibreOffice.app";
	Process pr;
	Runtime rt = Runtime.getRuntime();
	public boolean openLibreOffice() {

	
			try {
				System.out.println("OPENING...");
				rt.exec("open -a " + libreOfficeApp);
				System.out.println("App is open!"); 
				return true;

			} 
			catch (IOException e) 
			{

				e.printStackTrace();
			}
		
		
		//return open;
		return false;
	}

	public void closeLibreOffice() {		
		try 
		{

			System.out.println("DESTROYING");
			rt.exec("pkill -f " + libreOfficeApp);
			System.out.println("DESTROYED!!!!!!!!!");

		} 

		catch (IOException e) 
		{

			e.printStackTrace();
		}

	}

}
