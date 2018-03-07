package Test.code;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.tika.Tika;


public class TestForReadingFiles {
	ArrayList<String> utfList = new ArrayList<>();
	static String utfString;
	FileInputStream fileInputStream = null;
	InputStreamReader inputStreamReader = null;
	
	public static void main(String[] args) throws IOException {
		
		//InputStreamReaderDecoder input = new InputStreamReaderDecoder();
		//TestForReadingFiles test = new TestForReadingFiles();
		
		
		
		
		Tika fileType = new Tika();
		String path ="H:\\Hämtade filer\\Individuell-studieplan-Saikat-Talkuder-20170331.doc"; //path2 = "H:\\Skrivbord\\js.js";
		//Path _path = Paths.get(URI.create(path2));
		FileInputStream _fileInputStream = new FileInputStream(path);
		
		
		/*InputStreamReader inputStreamReader = new InputStreamReader(_fileInputStream);
		//test.fileEncoder(path);
		System.out.println("Encoding " + inputStreamReader.getEncoding());*/
		
		System.out.println("Filetype: " + fileType.detect(path));
		//System.out.println("File encoding type : " + utfString);
		_fileInputStream.close();
		
		
	}
	
	
	public void fileEncoder(String sourcePath)  {
		try {
			// new input stream reader is created 
			fileInputStream = new FileInputStream(sourcePath);
			inputStreamReader = new InputStreamReader(fileInputStream);

			// the name of the character encoding returned
			utfString = inputStreamReader.getEncoding();
			//utfList.add(utfString);
			//System.out.print("Character Encoding: " + utfString +  " ");

		} catch (Exception e) {
			e.printStackTrace();
			// print error
			//System.out.print("The stream is already closed");
		} finally {

			// closes the stream and releases resources associated
			try {
				if(fileInputStream!=null)
					fileInputStream.close();
				if(inputStreamReader!=null)
					inputStreamReader.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}   
	}


}