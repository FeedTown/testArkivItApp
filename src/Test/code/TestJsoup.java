package Test.code;

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
		String[] tmp;

		tmp = hrefLink.split(searchWord);
		
		return tmp.length == 0 ? updatedWord : tmp[0] + updatedWord;
	}
	
}
