package Test.code;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestingOutSplit {

	public static void main(String[] args) {

		String[] arr,arr2,arr3;
		String splittedValue ="";//,splittedValue2 = "", endTagLink = "", endTagScript = "/script", endTagImg = "/img", endTag = "/a";
		String test1="",test2="",test3="";
		GetHtmlEndTag checkForHtmlEndTag = new GetHtmlEndTag();
		ArrayList<String> testList = new ArrayList<String>(), testList2 = new ArrayList<String>(), testList3 = new ArrayList<String>(),
				backupList = new ArrayList<String>();

		//String testPath = "href=\"../style/täst.html\">täst.html</script>\n";
		String testPath = " rel=\"style\" href=\"täst.html\"> täst.html </a>\n";
		//+ "href=\"test2.html\"></a>\\n";

		System.out.println("Before splited : " + testPath);

		if(testPath.contains("täst.html"))
		{
			System.out.println("1");
			if(testPath.contains("href=")) 
			{
				System.out.println("2");
				splittedValue = "href=";
				arr = testPath.split(splittedValue);
				testList3.addAll(Arrays.asList(arr));
				int counter = 0;
				for(String tmp : testList3)
				{
					System.out.println("2 : " + testList3.toString());
					if(tmp.contains("\""))
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
								testList.addAll(Arrays.asList(arr3));
								int count = 0;
								for(String furtherInnerTmp : testList)
								{
									System.out.println("Arr3" + testList);
									if(furtherInnerTmp.equals("täst.html"))
									{

										String word = furtherInnerTmp.replaceAll("täst.html", "test.html");
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
								if(innerTmp.equals("täst.html"))
								{
									System.out.println("5");
									String word = innerTmp.replaceAll("täst.html", "test.html");
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
		}
		
		
		String joinedPath = test3;
		System.out.println("After joined : " + joinedPath);

		//testPath = testPath.replace("\\", "\\\\");






		/*arr = testPath.split("=\"");

		for(String tmp : arr)
		{
			System.out.println(tmp);
		}*/

		//String joined = String.join("=\"", arr);

		//System.out.println("After joined : ");

		//System.out.println(joined);
	}

}
