package com.arkivit.model;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Converter {
	Process pr;
	Runtime rt = Runtime.getRuntime();
	public void openLibreOffice() {
		try {

			rt.exec("open -a LibreOffice.app");

		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void closeLibreOffice() {
		//String command = "killall \"/Applications/LibreOffice.app/Contents/MacOS/soffice\""; 
		//pr.destroy();
		//pr.destroyForcibly();
		try 
		{
			pr = rt.exec("/Applications/LibreOffice.app/Contents/MacOS/soffice");
			BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			System.out.println("DESTROYING");
			
			//Thread.sleep(1000);
			pr.destroyForcibly();
			//pr.waitFor();
			input.close();
			System.out.println("DESTROYED!!!!!!!!!");
			
		} 
		
		catch (IOException e) 
		{
			
			e.printStackTrace();
		}

	}

}
