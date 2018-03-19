package Test.code;

import java.util.Arrays;
import java.util.List;

public class TestingOutSplit {

	public static void main(String[] args) {
		
		String[] arr;
		
		String testPath = "H:\\Skrivbord\\TestFilesWithHTML_backup\\TestFilesWithHTML\\htmlmapp";
		
		//testPath = testPath.replace("\\", "\\\\");
		
		System.out.println("Before splited : " + testPath);
		
		arr = testPath.split("\\\\");
		
		for(String tmp : arr)
		{
			System.out.println(tmp);
		}
		
		String joined = String.join("\\", arr);
		
		System.out.println("After joined : ");
		
		System.out.println(joined);
	}

}
