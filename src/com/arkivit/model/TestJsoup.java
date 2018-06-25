package com.arkivit.model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


public class TestJsoup {

	public String jSoupExtractElementsFromHtmlFile(String currentLine, String searchWord, String updatedWord)
	{
			String linkToFile = "", newString = "";
			Document document = Jsoup.parse(currentLine);
			Element link = null;

			if(currentLine.contains("<link"))
			{
				link = document.select("link").first(); 

				linkToFile = link.attr("href");

				newString = splitAndReturnNewString(linkToFile,updatedWord, searchWord);

				currentLine = link.attr("href" , newString).toString();
			}
			else if(currentLine.contains("<script")) {
				link = document.select("script").first(); 

				linkToFile = link.attr("src");

				newString = splitAndReturnNewString(linkToFile,updatedWord, searchWord);

				currentLine = link.attr("src" , newString).toString();
			}
			else if(currentLine.contains("<a")) {
				link = document.select("a").first(); 
				
				linkToFile = link.attr("href");

				newString = splitAndReturnNewString(linkToFile,updatedWord, searchWord);

				currentLine = link.attr("href" , newString).toString();
			}
			else if(currentLine.contains("<img")) {
				link = document.select("img").first(); 
				
				linkToFile = link.attr("src");

				newString = splitAndReturnNewString(linkToFile,updatedWord, searchWord);
				
				currentLine = link.attr("src" , newString).toString();
			}

		return currentLine;
	}
	
	private String splitAndReturnNewString(String hrefLink, String updatedWord, String searchWord) {
		
		return hrefLink = hrefLink.replaceAll("\\b"+searchWord+"\\b", updatedWord) ;
		
	}
	
	
	/*public static void main(String args[])
	{
		String linkLine = "<a href=\"Å.doc\">Å.doc</a>";
		
		String scriptLine = "<script type=\"text/javascript\" src=\"jävascript.js\"></script>";
		String imgLine = "<img id='loadingImg' src=\"../resource/gäphy.gif\" alt=\"Loading\" width=\"200\" height=\"200\">";
		String cssLine = "<link rel=\"stylesheet\" type=\"text/css\" href=\"äää.css\">";
		
		String[] tempArr = new String[4];
		
		tempArr[0] = linkLine;
		tempArr[1] = scriptLine;
		tempArr[2] = imgLine;
		tempArr[3] = cssLine;
		
		/*for(String s : tempArr)
		{
			System.out.println(new TestJsoup().jSoupExtractElementsFromHtmlFile(s, "Å.doc", "A.doc"));
		}*/
		
		/*System.out.println(new TestJsoup().jSoupExtractElementsFromHtmlFile(linkLine, "Å.doc", "A.pdf"));
		System.out.println(new TestJsoup().jSoupExtractElementsFromHtmlFile(scriptLine, "jävascript.js", "jaevascript.js"));
		System.out.println(new TestJsoup().jSoupExtractElementsFromHtmlFile(imgLine, "gäphy.gif", "gaephy.png"));
		System.out.println(new TestJsoup().jSoupExtractElementsFromHtmlFile(cssLine, "äää.css", "aeaeae.css"));
		
		
	}*/
	
}
