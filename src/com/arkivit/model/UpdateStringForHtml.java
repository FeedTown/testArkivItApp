package com.arkivit.model;

import java.util.ArrayList;
import java.util.Arrays;

import Test.code.GetHtmlEndTag;

public class UpdateStringForHtml {
	
	String[] arr,arr2,arr3;
	String splittedValue ="", checkWord;
	String tmpJoinedWord1="",test2="",test3="";
	GetHtmlEndTag checkForHtmlEndTag = new GetHtmlEndTag();
	ArrayList<String> testList = new ArrayList<String>(), testList2 = new ArrayList<String>(), testList3 = new ArrayList<String>(),
			backupList = new ArrayList<String>();

	public UpdateStringForHtml() {

	}

	public String updateWordInString(String currentTextLine, String searchWord, String updatedWord )
	{
		System.out.println("Before splited : " + currentTextLine);
		
		checkWord = checkForHtmlEndTag.getLinkRef(currentTextLine);
		
		if(currentTextLine.contains(checkWord) && !checkWord.equals("")) 
		{

			splittedValue = checkWord;
			arr = currentTextLine.split(splittedValue);
			testList3.addAll(Arrays.asList(arr)); 
			int counter = 0;
			for(String tmp : testList3)
			{

				if(tmp.contains("\"") && !checkForHtmlEndTag.getTagType(tmp))
				{

					arr2 = tmp.split("\"");
					testList2.addAll(Arrays.asList(arr2));
					int count2 = 0;
					for(String innerTmp : testList2)
					{

						if(innerTmp.contains("/") && !checkForHtmlEndTag.hasEndLink(innerTmp))
						{
							backupList.add(innerTmp);
							System.out.println("4");
							arr3 = innerTmp.split("/");
							testList.addAll(Arrays.asList(arr3));
							int count = 0;
							for(String furtherInnerTmp : testList)
							{
								System.out.println("Arr3" + testList);
								if(furtherInnerTmp.equals(searchWord))
								{
									String word = furtherInnerTmp.replaceAll(searchWord, updatedWord);
									testList.set(count, word);
								}
								count++;
							}
							tmpJoinedWord1 = String.join("/", testList);
							String word = testList2.get(1).replaceAll(testList2.get(1), tmpJoinedWord1);
							testList2.set(1, word);

						}
						else
						{
							if(innerTmp.equals(searchWord))
							{
								System.out.println("5");
								String word = innerTmp.replaceAll(searchWord, updatedWord);
								testList2.set(count2, word);

							}

						}
						count2++;
					}
				}
				counter++;
				test2=String.join("\"", testList2);
			}

			String word2 = testList3.get(0).replaceAll(testList3.get(0), test2);
			testList3.set(1, word2);


		}
		
		test3 = String.join(splittedValue , testList3);
		String joinedPath = test3;
		clearList();


		return joinedPath;
	}

	private void clearList() {
		testList3.clear();
		testList2.clear();
		
		if(!testList.isEmpty())
			testList.clear();
		
	}

}
