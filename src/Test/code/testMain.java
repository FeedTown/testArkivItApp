package Test.code;

import java.io.IOException;
import java.util.Scanner;

public class testMain {

	public static void main(String[] args) throws IOException {
	    /*FileOutputStream inMemoryOut = new FileOutputStream(new File("inMemoryWorkbook.xlsx"));
	    XSSFWorkbook workbook = new XSSFWorkbook();
	    WorkbookExample example = new WorkbookExample(workbook, inMemoryOut);
	    example.export();

	    FileOutputStream streamOut = new FileOutputStream(new File("streamWorkbook.xlsx"));
	    SXSSFWorkbook streamWorkbook = new SXSSFWorkbook();
	    WorkbookExample streamExample = new WorkbookExample(streamWorkbook, streamOut);
	    streamExample.export();*/
		
		//MetadataToExcelGUI data = new MetadataToExcelGUI();
		//MappingLog mapping = new MappingLog(data);
		boolean doMapping = false;
		
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Do you want to mapp everthing in selected folder?\n Yes(y)/No(n)? ");
		String answer = scan.nextLine();
		
		if(answer.equals("y") || answer.equals("Y")) {
			
			doMapping = true;
		}
		else if(answer.equals("n") || answer.equals("N"))
		{
			doMapping = false;
		}
			
		
		new testMapping(doMapping).init();
		
		scan.close();
	}
}
