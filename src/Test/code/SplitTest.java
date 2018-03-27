package Test.code;

public class SplitTest {

	public static void main(String[] args) {
		String[] arr;
		String testPath = " rel=\"style\" href=\"täst.html\">täst.html</a>\n";
		
		arr = testPath.split(" ");
		
		for(String tmp : arr)
		{
			System.out.println(tmp);
		}

	}

}
