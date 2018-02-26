package Test.code;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;




public class WorkbookExample {

    private Logger logger = Logger.getLogger(WorkbookExample.class.getName());
    private Workbook workbook;
    private OutputStream out;

    public WorkbookExample(Workbook workbook, OutputStream out) {
        this.workbook = workbook;
        this.out = out;
    }

    public void export() throws IOException {
        logger.info("export start for " + workbook.getClass().getName());

        List<Person> persons = new ArrayList<Person>();
        for (int i = 0; i < 100; i++) {
            persons.add(new Person(String.valueOf("user_" + i),String.valueOf("lastname_" + i)));
            
        }

        Sheet sheet = workbook.createSheet();
        for (int i = 0; i < persons.size(); i++) {
            Person p = persons.get(i);
            Row row = sheet.createRow(i);
            Cell cell = row.createCell(0);
            Cell cell1 = row.createCell(1);
            cell.setCellValue(p.getName());
            cell1.setCellValue(p.getlName());
        }
        workbook.write(out);
        logger.info("Is row 1 accessible after writing to output stream? " + String.valueOf(sheet.getRow(1) != null));
        out.close();
        workbook.close();

        logger.info("export finished for " + workbook.getClass().getName());
    }
}
