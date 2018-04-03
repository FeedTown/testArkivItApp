package Test.code;

import java.util.ArrayList;
import java.util.Arrays;

import com.itextpdf.text.log.SysoCounter;

public class AlgorithmForUpdateString {

	private String[] arr,arr2,arr3;
	private String splittedValue ="", splittedValue2 ="", splittedValue3 ="", checkWord;
	private String test1="",test2="",test3="";
	private GetHtmlEndTag checkForHtmlEndTag = new GetHtmlEndTag();
	private ArrayList<String> testList = new ArrayList<String>(), testList2 = new ArrayList<String>(), 
			testList3 = new ArrayList<String>(),
			backupList = new ArrayList<String>(), backupListSplitWord = new ArrayList<String>(), backupList1= new ArrayList<String>(),
			backupList2 = new ArrayList<String>();
	private String currentTextLine, searchWord, updateWord, updatetString;


	public AlgorithmForUpdateString(String currentTextLine , String searchWord , String updateWord) 
	{
		this.currentTextLine = currentTextLine;
		this.searchWord = searchWord;
		this.updateWord = updateWord;

		updateWordInString(currentTextLine,1);
		printOut();
	}


	private void printOut() {
		System.out.println("1 : "+testList.toString());
		System.out.println("2 : "+testList2.toString());
		System.out.println("3 : "+testList3.toString());

	}


	public void updateWordInString(String currentTextLine, int splitedValueNum)
	{
		if(splitedValueNum < 4)
		{
			System.out.println("\nSplited num : "+splitedValueNum);
			checkWord = checkForHtmlEndTag.getLinkRefTest(currentTextLine,splitedValueNum);

			if(currentTextLine.contains(checkWord))
			{
				if(splitedValueNum == 1)
				{

					System.out.println("if 1");
					/*splittedValue = checkWord;
					arr = currentTextLine.split(splittedValue);
					testList3.addAll(Arrays.asList(arr));*/

					testList3.
					addAll(Arrays.
							asList(infoAdder(arr,checkWord,currentTextLine)));

					backupList2 = testList3;
				}

				else if(splitedValueNum == 2) {

					System.out.println("else-if 2");
					
					if(!checkForHtmlEndTag.getTagType(currentTextLine))
						testList2.addAll(Arrays.asList(infoAdder(arr, checkWord,currentTextLine)));
					
					backupList2 = testList2;
					
				}
				else if(splitedValueNum == 3) {
					System.out.println("else-if 3");
					
					if(!checkForHtmlEndTag.hasEndLink(currentTextLine));
						testList.addAll(Arrays.asList(infoAdder(arr, checkWord,currentTextLine)));

					backupList2 = testList;
				}
				
				
				for(String s : backupList2)
				{
					
				}
				
				String t = String.join("", backupList2);
				
				updateWordInString(t,splitedValueNum+1);
			
			}
			else
			{

				System.out.println("\nStill in update, please be patient and wait.");
				return;
			}
		}

	}

	public String[] infoAdder(String[] arr,  String currentWord, String currentLine)
	{
		String splitValue = currentWord;
		arr = currentLine.split(splitValue);
		backupListSplitWord.add(splitValue);
		backupList.add(currentWord);
		backupList1.add(currentLine);
		//list.addAll(Arrays.asList(arr));

		return arr;

	}

	public String getUpdatedString() 
	{
		return updatetString;
	}

	public static void main(String[] args)
	{
		String testPath = " rel=\"style\" href=\"/täst.html\"> täst.html </a>\n";
		String testWord = "täst.html";
		String testUpdateWord = "taest.html";

		new AlgorithmForUpdateString(testPath,testWord,testUpdateWord);
	}

}
