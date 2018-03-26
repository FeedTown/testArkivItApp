package Test.code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class TestJsoup {

	private String filePath = "H:\\Skrivbord\\TestFilesWithHTML_backup\\TestFilesWithHTML\\htmlmapp\\Kladdkaka.html";


	public String jSoupExtractElementsFromHtmlFile2(String currentLine, String searchWord, String updatedWord, String filePath)
	{
		if(currentLine.contains(searchWord)){
			Document document = Jsoup.parse(currentLine);
			Element link = null;
			//a with href
			// System.out.println("\nBefore edit: " + document);
			
			if(!filePath.equals(""))
			{
				updatedWord = "../" + filePath + "/" + updatedWord;
			}
			
			if(currentLine.contains("<link"))
			{
				link = document.select("link").first(); 
				currentLine = link.attr("href" , updatedWord).toString();
			}
			else if(currentLine.contains("<script")) {
				link = document.select("script").first(); 
				currentLine = link.attr("src" , updatedWord).toString();
			}
			else if(currentLine.contains("<a")) {
				link = document.select("a").first(); 
				currentLine = link.attr("href" , updatedWord).toString();
			}
			else if(currentLine.contains("<img")) {
				link = document.select("img").first(); 
				currentLine = link.attr("src" , updatedWord).toString();
			}

		}


		return currentLine;
	}

	public void jSoupExtractElementsFromHtmlFile()
	{
		String html = "<link rel=\"stylesheet\" type=\"text/css\" href=\"äää.css\">";
		Document document = Jsoup.parse(html);

		//a with href
		// System.out.println("\nBefore edit: " + document);
		Element link = document.select("link").first();         

		String test2 = link.attr("href" , "aeaeae.css").toString();

		String test1 = document.toString();
		//String test2 = t.toString();
		System.out.println("\n\nAfter edit: " + test1);
		System.out.println("\n\nAfter edit line: " + test2);
	}

	public void testFile()
	{
		File f = new File(filePath);
		String fName = f.getParentFile().getName();
		System.out.println(fName);
		//System.out.println(f.getParentFile());
		
	}
	
	public static void main(String[] args) throws IOException
	{
		String html = "<link rel=\"stylesheet\" type=\"text/css\" href=\"äää.css\">";
		String html2 = "<a rel=\"stylesheet\" type=\"text/css\" href=\"äää.css\">";
		String html3 = "<img id='loadingImg' src=\"../resource/äää.css\" alt=\"Loading\" width=\"200\" height=\"200\">";
		String html4 = "<script type=\"text/javascript\" src=\"äää.css\">";
		
		String filePath = "resource";
		
		ArrayList<String> list = new ArrayList<String>(), listPath = new ArrayList<String>();
		listPath.add(0, "");
		listPath.add(1, "");
		listPath.add(2, "");
		listPath.add(3, "resource");
		
		list.add(html);
		list.add(html2);
		list.add(html3);
		list.add(html4);

		//new TestJsoup().jSoupExtractElementsFromHtmlFile();
		
		int counter = 0;
		for(String h : list)
		{
			System.out.println("\nEdited line : " + new TestJsoup().jSoupExtractElementsFromHtmlFile2(h, "äää.css", "aeaeae.css", listPath.get(counter)));
			counter++;
		}
		
		
		//new TestJsoup().testFile();

		//System.out.println("Edited line : " + new TestJsoup().jSoupExtractElementsFromHtmlFile2(html, "äää.css", "aeaeae.css"));
	}

}
