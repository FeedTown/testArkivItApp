package com.arkivit.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Converter {

	private String libreOfficeApp = "LibreOffice.app";
	private String notaPad = "notepad.exe", libOffice="soffice.exe", osName;
	Runtime rt = Runtime.getRuntime();
	Process p;

	public boolean openLibreOffice() {
		
		osName = System.getProperty("os.name");
		
		try 
		{
			if(osName.contains("Windows"))
			{
				p = rt.exec("cmd /c start " + libOffice);
			}
			else if(osName.contains("Mac"))
			{
				rt.exec("open -a " + libreOfficeApp);
			}
			
			
			Thread.sleep(5000);
			
			
			
			closeLibreOffice();
			
			return true;

		} 
		catch (IOException e) 
		{

			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return false;
	}

	public void closeLibreOffice() {		
		try 
		{
			//test(p.getInputStream());
			if(osName.contains("Windows"))
			{
				rt.exec("taskkill /IM soffice.bin");
			}
			else if(osName.contains("Mac"))
			{
				rt.exec("pkill -f " + libreOfficeApp);
			}
			

		} 

		catch (IOException e) 
		{

			e.printStackTrace();
		}
		
		//process.destroy();

	}
	
	/*private void test(InputStream is) {
		 InputStreamReader isr = new InputStreamReader(is);
         BufferedReader br = new BufferedReader(isr);
         String line=null;
         try {
			while ( (line = br.readLine()) != null)
			     System.out.println("OutPut" + ">" + line);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}*/

	public static void main(String[] args)
	{
		new Converter().openLibreOffice();
	}

}
