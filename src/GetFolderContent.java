import java.util.Scanner;

import com.arkivit.model.MetadataToExcelGUI;

public class GetFolderContent{
 

	public static void main(String[] args) 
	{
		Scanner scan = new Scanner(System.in);
		String filename;
		@SuppressWarnings("unused")
		MetadataToExcelGUI excelCreater;
		
		System.out.println("Give a name for the excelfile: ");
		filename = scan.nextLine();
		excelCreater = new MetadataToExcelGUI(filename);
		//excelCreater.testMeth();
		scan.close();	
	}
	
}