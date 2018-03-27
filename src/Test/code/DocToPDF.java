package Test.code;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.xmlbeans.XmlOptions;

public class DocToPDF {
	
	
	public static void main(String[] args) {
		//converter();
		String docPath = "/Users/RobertoBlanco/Desktop/test.docx";
		String pdfPath = "/Users/RobertoBlanco/Desktop/pdf_test.pdf";
		try {
	        InputStream doc = new FileInputStream(new File(docPath));
	        XWPFDocument document = new XWPFDocument(doc);
	        PdfOptions options = PdfOptions.create();
	        OutputStream out = new FileOutputStream(new File(pdfPath));
	        PdfConverter.getInstance().convert(document, out, options);
	    } 
		catch (FileNotFoundException ex) {
	        //Logger.getLogger(Convert.class.getName()).log(Level.SEVERE, null, ex);s
	    		ex.printStackTrace();
	    }
		catch (IOException ex) {
	        //Logger.getLogger(Convert.class.getName()).log(Level.SEVERE, null, ex);
	    		ex.printStackTrace();
	    }
	}
	
	
	
	/*public static void converter() {
		 
		try {
		        InputStream doc = new FileInputStream(new File(docPath));
		        XWPFDocument document = new XWPFDocument(doc);
		        PdfOptions options = PdfOptions.create();
		        OutputStream out = new FileOutputStream(new File(pdfPath));
		        PdfConverter.getInstance().convert(document, out, options);
		    } 
			catch (FileNotFoundException ex) {
		        //Logger.getLogger(Convert.class.getName()).log(Level.SEVERE, null, ex);s
		    		ex.printStackTrace();
		    }
			catch (IOException ex) {
		        //Logger.getLogger(Convert.class.getName()).log(Level.SEVERE, null, ex);
		    		ex.printStackTrace();
		    }
	} */

	 
}
