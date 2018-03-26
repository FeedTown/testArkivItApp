package com.arkivit.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Converter {



	private String libreOfficeApp = "LibreOffice.app";
	Runtime rt = Runtime.getRuntime();

	public boolean openLibreOffice() {
		try 
		{

			//Process pr = Runtime.getRuntime().exec("/Users/RobertoBlanco/Desktop/script.sh");
			Process pr1 = Runtime.getRuntime().exec("/Users/RobertoBlanco/eclipse-workspace-JavaEE/testArkivItApp/lib/script1.sh");
			readBashScript(pr1.getInputStream());
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

	public static void readBashScript(InputStream is) {

		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String line=null;

		try 
		{
			while ( (line = br.readLine()) != null)
				System.out.println("OutPut" + "> " + line);
		} 

		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	/*public static void main(String[] vad ) {
		new Converter().openLibreOffice();
	} */

}


