package com.arkivit.model;

import java.util.ArrayList;
import java.util.Arrays;

import Test.code.GetHtmlEndTag;

public class UpdateStringForHtml {
	
	String[] arr,arr2,arr3;
	String splittedValue ="", checkWord;
	String test1="",test2="",test3="";
	GetHtmlEndTag checkForHtmlEndTag = new GetHtmlEndTag();
	ArrayList<String> testList = new ArrayList<String>(), testList2 = new ArrayList<String>(), testList3 = new ArrayList<String>(),
			backupList = new ArrayList<String>();

	public UpdateStringForHtml() {

	}

	public String updateWordInString(String currentTextLine, String searchWord, String updatedWord )
	{
		

		//String testPath = "href=\"../style/täst.html\">täst.html</script>\n";
		//String testPath = "href=\"täst.html\">täst.html</a>\n";
		//+ "href=\"test2.html\"></a>\\n";

		System.out.println("Before splited : " + currentTextLine);
		checkWord = checkForHtmlEndTag.getLinkRef(currentTextLine);

		System.out.println("1");


		if(currentTextLine.contains(checkWord) && !checkWord.equals("")) 
		{
			System.out.println("2");
			splittedValue = checkWord;
			arr = currentTextLine.split(splittedValue);
			testList3.addAll(Arrays.asList(arr)); 
			int counter = 0;
			for(String tmp : testList3)
			{
				System.out.println("2 : " + testList3.toString());
				if(tmp.contains("\"") && !checkForHtmlEndTag.getTagType(tmp))
				{
					System.out.println("3");
					arr2 = tmp.split("\"");
					testList2.addAll(Arrays.asList(arr2));
					int count2 = 0;
					for(String innerTmp : testList2)
					{
						System.out.println("1 : " + testList2.toString());
						if(innerTmp.contains("/") && !checkForHtmlEndTag.hasEndLink(innerTmp))
						{
							backupList.add(innerTmp);
							System.out.println("4");
							arr3 = innerTmp.split("/");
							//testList.addAll();
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
							test1 = String.join("/", testList);
							System.out.println("Further inner tmp joined : " +test1);

							System.out.println(testList2.toString());
							String word = testList2.get(1).replaceAll(testList2.get(1), test1);
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
				System.out.println("test2 : " + test2);



			}

			String word2 = testList3.get(0).replaceAll(testList3.get(0), test2);
			testList3.set(1, word2);


		}

		test3 = String.join(splittedValue , testList3);

		String joinedPath = test3;
		System.out.println("After joined : " + joinedPath);



		return joinedPath;
	}

}
