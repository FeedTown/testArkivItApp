package com.arkivit.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Converter {


	private Runtime rt = Runtime.getRuntime();
	private String libreOfficeApp = "LibreOffice.app";
	private String libOffice ="soffice.exe", osName;

	Process p;

	public boolean openLibreOffice() {
		try 
		{
			osName = System.getProperty("os.name");
			
			if(osName.contains("Windows"))
			{
				p = rt.exec("cmd /c start " + libOffice);
				//readBashScript(pr1.getInputStream());
			}
			else if(osName.contains("Mac") || osName.contains("Ubuntu") || osName.contains("Debian"))
			{
				Process pr1 = Runtime.getRuntime().exec("/Users/RobertoBlanco/eclipse-workspace-JavaEE/testArkivItApp/lib/script1.sh");
				rt.exec("open -a " + libreOfficeApp);
				readBashScript(pr1.getInputStream());
			}
			
			return true;

		}
		catch(IOException e) {
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
			else if(osName.contains("Mac") || osName.contains("Ubuntu") || osName.contains("Debian"))
			{
				rt.exec("pkill -f " + libreOfficeApp);
			}


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

	public static void main(String[] args)
	{
		Converter c = new Converter();
		c.openLibreOffice();
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		c.closeLibreOffice();
	}

}

