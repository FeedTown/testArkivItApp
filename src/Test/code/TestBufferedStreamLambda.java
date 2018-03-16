package Test.code;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TestBufferedStreamLambda {
	
	private String filePath;
	
	public TestBufferedStreamLambda(String filePath) {
		this.filePath = filePath;
	}
	
	public List<String> testBuffer()
	{
		List<String> list = new ArrayList<String>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(filePath));) {

			//br returns as stream and convert it into a List
			//list = br.lines().collect(Collectors.toList());
			
			String line = "";
			while((line = br.readLine()) != null)
			{
				list.add(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public void updateInfoInFile(String searchWord, String updatedWord, List<String> brList) throws IOException
	{
		
		/*for(String currWord : brList)
		{
			if(currWord.equals(searchWord))
			{
				brList.set(counter, updatedWord);
				writeToFile(brList);
			}
			counter++;
		}*/
		
		//System.out.println(brList.toString());
		String word = "";
		for(int i = 0; i < brList.size(); i++)
		{
			if(brList.get(i).contains(searchWord))
			{
				System.out.println(brList.get(i).toString());
				word = brList.get(i).replaceAll(searchWord, updatedWord);
				brList.set(i, word);
				writeToFile(brList);
			}
		}
		
		System.out.println(brList.toString());
	}
	
	

	public void writeToFile(List<String> currFile) throws IOException
	{
		//File f = new File(filePath);
		//BufferedWriter writer = Files.newBufferedWriter(new File(filePath).toPath());
		BufferedWriter w = new BufferedWriter(new FileWriter(filePath));
		
		for(String x : currFile)
		{
			w.write(x);
			w.newLine();
		}
		
		/*currFile.forEach(x -> {
			try {
				w.write(x);
				w.newLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});*/
		w.close();
		
}
			
	
	String mapInFile;
	
	public static void main(String[] args) throws IOException
	{
		//String filePath = "/Users/RobertoBlanco/Desktop/TestDokument.txt";
		String filePath = "H:\\Skrivbord\\HtmlTestFile\\examUppgift.html";
		
		
		TestBufferedStreamLambda reader = new TestBufferedStreamLambda(filePath);
		
		List<String> list = new ArrayList<String>();
		
		System.out.println("******* Before update ******");
		
		list = reader.testBuffer();
		
		//list.forEach(System.out::println);
		//reader.writeToFile(list);
		
		//reader.updateInfoInFile("<a href=exäämUppgiftSida2.html>Kladdkaka Recept</a>", "<a href= examUppgiftSida2.html>Kladdkaka Recept</a>", list);

		reader.updateInfoInFile("\"exäämUppgiftSida2.html\"","\"examUppgiftSida2.html\"", list);
		

		//reader.updateInfoInFile("skäteboard","longboard", list);

		System.out.println(list.toString());
		
		System.out.println("******* After update  ******");
		//list.forEach(System.out::println);
			
	}
}
