package Test.code;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CssFileParserTest {

	
	private String filePath;
	//private UpdateStringForHtml htmlWordUpdater = new UpdateStringForHtml();
	private TestJsoup tJsoup = new TestJsoup();
	
	public CssFileParserTest()
	{
		
	}
	
	public CssFileParserTest(String filePath) {
		this.filePath = filePath;
		
	}
	

	public List<String> readFileAndAddInfoToList()
	{
		List<String> list = new ArrayList<String>();

		try (BufferedReader br = new BufferedReader(new FileReader(filePath));) {

			String line = "";

			while((line = br.readLine()) != null)
			{
				list.add(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return list;
	}

	public void updateInfoInFile(String searchWord, String updatedWord, List<String> brList) throws IOException
	{
		
		String word = "";
		for(int i = 0; i < brList.size(); i++)
		{
			if(brList.get(i).contains(searchWord))
			{
				word = updateCssString(brList.get(i),searchWord,updatedWord);
				brList.set(i, word);
				writeToFile(brList);
			}
		}
	}

	public void writeToFile(List<String> currFile) throws IOException
	{

		BufferedWriter w = new BufferedWriter(new FileWriter(filePath));

		for(String x : currFile)
		{
			w.write(x);
			w.newLine();
		}

		w.close();

	}
	
	public String updateCssString(String currentLine, String searchWord, String updatedWord)
	{
		//System.out.println("Cssline before update : " + currentLine);
		currentLine = currentLine.replaceAll("\\b"+searchWord+"\\b", updatedWord);
	
		return currentLine;
	}
	
	public static void main(String[] args )
	{
		CssFileParserTest cssParser = new CssFileParserTest("H:\\Skrivbord\\Css_File\\cssTmp.css");
				
		List<String> searchList = new ArrayList<>();
		List<String> updatedList = new ArrayList<>();
		List<String> cssFileInString = new ArrayList<>();
		int counter = 0;		
		cssFileInString = cssParser.readFileAndAddInfoToList();
		
		searchList.add("börder.png");
		searchList.add("img_träd.gif");
		
		updatedList.add("boerder.png");
		updatedList.add("img_traed.gif");
		
		for(String tmp : searchList)
		{
			try {
				
				cssParser.updateInfoInFile(tmp, updatedList.get(counter), cssFileInString);
				counter++;
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		cssParser.readFileAndAddInfoToList().forEach(temp -> System.out.println(temp));
		
		
		//System.out.println("Updated line : " + cssParser.updateCssString("  background-image: url(\"../resources/img_flör.gif\"), url(\"päper.gif\");", "päper.gif","paeper.gif"));
		//System.out.println("Updated line : " + cssParser.updateCssString("  border-image-source: url(../resources/img_flör.gif), url(\"päper.gif\");", "img_flör.gif","img_floer"));
		//System.out.println("Updated line : " + cssParser.updateCssString("  border-image: url(börder.png) 30 stretch;", "börder.png", "boerder.png"));
		
	}
	
	void unUsedCode()
	{
		/*	
		private String parseCss2(String currentLine, String searchWord, String updatedWord)
		{
			String[] testArr, testArr1;
			String joinedWord = "";
			ArrayList<String> list1, list2;
			int counter = 0, counter2 = 0;
			
			if(currentLine.contains(","))
			{
				testArr = currentLine.split(",");
				list1 = new ArrayList<String>();
				list1.addAll(Arrays.asList(testArr));
				
				for(String firstSplit : list1)
				{
					System.out.println(counter+1 + " : " + firstSplit);
					if(firstSplit.contains("/") && firstSplit.contains(searchWord))
					{
						list1.set(counter, splitTheString(firstSplit,searchWord,updatedWord));
						
					}
					else if(firstSplit.equals("background-image: url("+searchWord+");") || firstSplit.equals(" url("+searchWord+")") || firstSplit.equals("background-image: url("+searchWord+")"))
					{
						System.out.println("hi");
						
						String word = firstSplit.replace(searchWord, updatedWord);
						list1.set(counter, word);
					}
					counter++;
					
				}
				
				currentLine = String.join(",", list1);
			}
			else
			{
				if(currentLine.contains("/"))
				{
					currentLine = splitTheString(currentLine,searchWord,updatedWord);
				}
				else if(currentLine.equals("background-image: url("+searchWord+");") || currentLine.equals(" url("+searchWord+")") || currentLine.equals("background-image: url("+searchWord+")"))
				{System.out.println(currentLine);
				currentLine = currentLine.replaceAll(searchWord, updatedWord);
				}
			}
			
			return currentLine;
		}
		
		private String parseCss(String currentLine, String searchWord, String updatedWord)
		{
			String[] testArr;
			ArrayList<String> list1;
			int counter = 0;
			
			if(currentLine.contains(","))
			{
				testArr = currentLine.split(",");
				list1 = new ArrayList<String>();
				list1.addAll(Arrays.asList(testArr));
				
				for(String tmpWord : list1)
				{
					System.out.println(counter+1 + " : " + tmpWord);
					if(tmpWord.contains("/") && tmpWord.contains(searchWord))
					{
						list1.set(counter, splitTheString(tmpWord,searchWord,updatedWord));
						
					}
					else if(tmpWord.equals(" url("+searchWord+");") || tmpWord.equals(" url("+searchWord+")") || tmpWord.equals(" url(\""+searchWord+"\")") || tmpWord.equals(" url(\""+searchWord+"\");"))
					{
						System.out.println("hi");
						
						String word = tmpWord.replace(searchWord, updatedWord);
						list1.set(counter, word);
					}
					counter++;
					
				}
				
				currentLine = String.join(",", list1);
				list1.clear();
			}
			else
			{
				if(currentLine.contains("/"))
				{
					currentLine = splitTheString(currentLine,searchWord,updatedWord);
				}
				else if(currentLine.equals(" url("+searchWord+");") || currentLine.equals(" url("+searchWord+")") || currentLine.equals(" url(\""+searchWord+"\")") || currentLine.equals(" url(\""+searchWord+"\");")
						|| currentLine.equals("\\b"+searchWord+"\\b"))
				{
					System.out.println(currentLine);
					currentLine = currentLine.replaceAll(searchWord, updatedWord);
				}
			}
			
			return currentLine;
		}
		
		private String splitTheString(String firstSplit, String searchWord, String updatedWord) {
			int counter = 0; String joinedWord;
			String[] testArr1 = firstSplit.split("/");
			ArrayList<String> list2 = new ArrayList<String>();
			list2.addAll(Arrays.asList(testArr1));
			for(String secondSplit : list2)
			{
				if(secondSplit.equals(searchWord+");") || secondSplit.equals(searchWord+")") || secondSplit.equals(searchWord+"\")") || secondSplit.equals(searchWord+"\");") )
				{
					String word = secondSplit.replace(searchWord, updatedWord);
					list2.set(counter, word);
				}
				counter++;
			}
			joinedWord = String.join("/", list2);
			
			list2.clear();
			return joinedWord;
			
		}
		
		private void firstSplit(String tmp, String searchWord, String updatetWord)
		{
			String[] testArr;
			String newString = "";
			int counter = 0;
			ArrayList<String> test = new ArrayList<String>();
			
			testArr = tmp.split(":");
			test.addAll(Arrays.asList(testArr));
			
			
			for(String word : test)
			{
				if(word.contains(searchWord))
				{
					newString = parseCss(word, searchWord, updatetWord);
					counter++;
				}	
			}
			
			test.set(counter, newString);
			
			
			String fullSentence = String.join(":", test);
			
			System.out.println(fullSentence);
			test.clear();
			
			//test.forEach(tmp1-> tmp1.equals(searchWord) ? parseCss(tmp1, searchWord, updatetWord));
			
		}
		
		*/
	}
}
