package Test.code;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class testMain {

	public static void main(String[] args) throws IOException {
	    FileOutputStream inMemoryOut = new FileOutputStream(new File("inMemoryWorkbook.xlsx"));
	    XSSFWorkbook workbook = new XSSFWorkbook();
	    WorkbookExample example = new WorkbookExample(workbook, inMemoryOut);
	    example.export();

	    FileOutputStream streamOut = new FileOutputStream(new File("streamWorkbook.xlsx"));
	    SXSSFWorkbook streamWorkbook = new SXSSFWorkbook();
	    WorkbookExample streamExample = new WorkbookExample(streamWorkbook, streamOut);
	    streamExample.export();
	}
}
