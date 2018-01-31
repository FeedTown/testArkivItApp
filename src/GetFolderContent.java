import java.util.Scanner;

public class GetFolderContent{
 

	public static void main(String[] args) 
	{
		Scanner scan = new Scanner(System.in);
		String filename;
		@SuppressWarnings("unused")
		MetadataToExcel excelCreater;
		
		System.out.println("Give a name for the excelfile: ");
		filename = scan.nextLine();
		excelCreater = new MetadataToExcel(filename);
		//excelCreater.createExcel();
		scan.close();	
	}
	
}