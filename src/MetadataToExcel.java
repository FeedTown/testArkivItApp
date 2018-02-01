import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;



public class MetadataToExcel {

	private String excelFileName = "standard.xls",folderName = "";
	private long fileSize;
	//private String filePath;
	private String targetexcelFilepath = "/Users/RobertoBlanco/Desktop/target" ;  // generated excel file location
	private String sourceFolderPath = "/Users/RobertoBlanco/Desktop/source"; // source Directory
	//private int totalFileCount = 0, tCount= 0; // variable to store total file count
	//private int fileCount = 0; // variable to store required files count
	private ArrayList<String> listOfFileNames = new ArrayList<String>();
	private ArrayList<String> filePathList = new ArrayList<String>();
	private ArrayList<Long> sizeList = new ArrayList<Long>();
	private ArrayList<File> fList;// = new ArrayList<File>();
	//private int folderCounter = 0;
	private int filec = 0;
	//private String windowsCharset = "Cp1252", standardCharset = "UTF-8";

	private InputStreamReaderDecoder decoder = new InputStreamReaderDecoder();
	private FileDuration fileDuration = new FileDuration();
	//private static ArrayList<String> fileExtention = new ArrayList<String>();



	public MetadataToExcel()
	{

	}

	public MetadataToExcel(String excelFileName)
	{   
		fList = new ArrayList<File>();
		folderName = new File(sourceFolderPath).getName();
		this.excelFileName = excelFileName + ".xls";
		listOfFilesAndDirectory(sourceFolderPath);
		testFunc();
	}


	public void listOfFilesAndDirectory(String folderPathName/*, ArrayList<File> fList*/)
	{
		File folder = new File(folderPathName);
		File[] listOfFilesInDirectory = folder.listFiles();
		
		
		for(File file : listOfFilesInDirectory)
		{
			if(file.isFile())
			{	
				filec++;
				fList.add(file);
				System.out.println("Nr " + filec + " : " + file.getName());
			}
			else if(file.isDirectory())
			{
				listOfFilesAndDirectory(file.getAbsolutePath());
			}
		}

		System.out.println(filec);

	}

	public void testFunc() 
	{

		fList.sort(new Comparator<File>() {
			@Override
			public int compare(File o1, File o2) {
				String s1 = o1.getName().toLowerCase();
				String s2 = o2.getName().toLowerCase();
				final int s1Dot = s1.lastIndexOf('.');
				final int s2Dot = s2.lastIndexOf('.');
				// 
				if ((s1Dot == -1) == (s2Dot == -1)) { // both or neither
					s1 = s1.substring(s1Dot + 1);
					s2 = s2.substring(s2Dot + 1);
					return s1.compareTo(s2);
				} else if (s1Dot == -1) { // only s2 has an extension, so s1 goes first
					return -1;
				} else { // only s1 has an extension, so s1 goes second
					return 1;
				}

			}});
		String duration;
		try {
			String fPath, file, test,currentFolderName="";
			if(!fList.isEmpty())
			{
				for (int numberOfFilesInFolder = 0; numberOfFilesInFolder < fList.size(); numberOfFilesInFolder++) {

					decoder.fileEncoder(fList.get(numberOfFilesInFolder).getParentFile().getAbsolutePath(), fList.get(numberOfFilesInFolder).getName());  

					duration = "";
					file = fList.get(numberOfFilesInFolder).getName();
					
					if(file.endsWith(".mov") || 
							file.endsWith(".mp4") || 
							file.endsWith(".mp3") || 
							file.endsWith("m4v"))
					{

						fileDuration.CheckFileDuration(fList.get(numberOfFilesInFolder).getParentFile().getAbsolutePath()
								+ "/" + file);
						duration = fileDuration.getAudioVideoDuration();

					}
					//String folderName = "";
					test = fList.get(numberOfFilesInFolder).getParentFile().getAbsolutePath();
					
					
					currentFolderName = fList.get(numberOfFilesInFolder).getParentFile().getName();
					
					System.out.println("SELECTED FOLDER NAME : " + folderName);
					System.out.println("CURRENT FILE PATH : " + test);
					System.out.println("FOLDERNAME? : " + currentFolderName);
					
					/*System.out.println("ORIGINAL FILE CHOOSER FOLDER PATH : " + sourceFolderPath);*/
					//, testPath;
					fileSize = fList.get(numberOfFilesInFolder).length();
					
					

					fPath = fList.get(numberOfFilesInFolder).getParentFile().getAbsolutePath();
					fPath = fPath.replace(sourceFolderPath, folderName);

					listOfFileNames.add(file);
					sizeList.add(fileSize);
					filePathList.add(fPath);
					decoder.getUtfList().add(decoder.getUtfString());
					fileDuration.getAudioVideoList().add(duration);
					//fileCount++;

					System.out.println("File size: " + fileSize);

				}
			}
			else
			{
				System.out.println("The list is empty");
			}


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		System.out.println("Done with search for files!");
		
		createExcelFile();
		//testLabel();

	}
	
	void testLabel()
	{
		String[] colNames = new String[]{"FILNAMN","FILTYP","FIlTYPSVERSION","STORLEK(i bytes)", "TECKENUPPSÄTTNING", "SPELTID","FILSÖKVÄG"};
		ArrayList<String> _colNames = new ArrayList<String>();
		
		
		for(int i = 0; i < colNames.length; i++)
		{
			_colNames.add(colNames[i]);
		}
		
		System.out.println(_colNames);
		
	}
	
	public void createExcelFile() {
		
		/*String[] colNames = new String[]{"FILNAMN","FILTYP","FIlTYPSVERSION","STORLEK(i bytes)", "TECKENUPPSÄTTNING", "SPELTID","FILSÖKVÄG"};
		ArrayList<String> _colNames = new ArrayList<String>();
		
		
		for(int i = 0; i < colNames.length; i++)
		{
			_colNames.add(colNames[i]);
		}*/
		
		File file = new File(targetexcelFilepath +"/"+ excelFileName);

		try {
			int cellRowForName = 0;// cellCol[] = new int[_colNames.size()];
			String tempString,sizeInString,fileExtention;
			
			WorkbookSettings wbSettings = new WorkbookSettings();
			WritableWorkbook workbook = Workbook.createWorkbook(file,
					wbSettings);
			workbook.createSheet("File Names", 0);
			System.out.println("Excel file is created in path -- "
					+ targetexcelFilepath);
			WritableSheet excelSheet = (WritableSheet) workbook.getSheet(0);
			if (!listOfFileNames.isEmpty()) {
				for (int rowNumber = 0; rowNumber < listOfFileNames.size(); rowNumber++) {

					/*CellView cell = workbook.getSheet(0).getColumnView(rowNumber);
		    	cell.setSize(14000);
		    	workbook.getSheet(0).setColumnView(0, cell);*/
						
					tempString = listOfFileNames.get(rowNumber);
					
					if(tempString.contains("Å") || tempString.contains("Ä") || tempString.contains("Ö")
							|| tempString.contains("å") || tempString.contains("ä") || tempString.contains("ö") 
							|| tempString.contains("ü") || tempString.contains("Ü"))
					{
						tempString = replaceIllegalChars(tempString);
					}
					
					
					//System.out.println(aList.get(rowNumber));

					sizeInString = Objects.toString(sizeList.get(rowNumber), null); 
					fileExtention = FilenameUtils.getExtension(listOfFileNames.get(rowNumber));
					// FilenameUtils.get
					
					

					
					Label fileNameLabelHeader = new Label(0, cellRowForName, "FILNAMN");
					Label fileNameLabel = new Label(0, rowNumber+1, tempString);

					Label fileTypeLabelHeader = new Label(1,cellRowForName,"FILTYP");
					Label fileTypeLabel = new Label(1, rowNumber+1, fileExtention);
					
					Label fileTypeVersionLabelHeader = new Label(2,cellRowForName,"FILTYPSVERSION");
					//Label fileTypeLabel = new Label(1, rowNumber+1, fileExtention);
					
					Label fileSizeLabelHeader = new Label(3, cellRowForName, "STORLEK(Bytes)");
					Label fileSizeLabel = new Label(3, rowNumber+1, sizeInString);
					
					Label utfLabelHeader = new Label(4,cellRowForName, "TECKENUPPSÄTTNING");
					Label utfLabel = new Label(4, rowNumber+1, decoder.getUtfList().get(rowNumber));
					
					Label durationLabelHeader = new Label(5,cellRowForName, "SPELTID(endast audio och video)");
					Label durationLabel = new Label(5, rowNumber+1, fileDuration.getAudioVideoList().get(rowNumber));

					Label filePathLabelHeader = new Label(6, cellRowForName, "SÖKVÄG(path,url)");
					Label filePathLabel = new Label(6, rowNumber+1, filePathList.get(rowNumber));


					excelSheet.setColumnView(0, getLargestString(listOfFileNames));
					excelSheet.setColumnView(2, 16);
					excelSheet.setColumnView(3, 16);
					excelSheet.setColumnView(4, 20);
					excelSheet.setColumnView(5, 33);
					excelSheet.setColumnView(6, getLargestString(filePathList));
					/*excelSheet.setColumnView(7, 33);
					excelSheet.setColumnView(8, 33);
					excelSheet.setColumnView(9, 13);*/
					
					excelSheet.addCell(fileNameLabelHeader);
					excelSheet.addCell(fileNameLabel);
					
					excelSheet.addCell(fileTypeLabelHeader);
					excelSheet.addCell(fileTypeLabel);
					
					excelSheet.addCell(fileTypeVersionLabelHeader);
					
					
					excelSheet.addCell(fileSizeLabelHeader);
					excelSheet.addCell(fileSizeLabel);
					
					excelSheet.addCell(utfLabelHeader);
					excelSheet.addCell(utfLabel);
					
					excelSheet.addCell(durationLabelHeader);
					excelSheet.addCell(durationLabel);
					
					excelSheet.addCell(filePathLabelHeader);
					excelSheet.addCell(filePathLabel);
					
				}
			} else {
				System.out.println("No matching files found");
			}
			workbook.write();
			workbook.close();
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private String replaceIllegalChars(String string) {
		String newString = StringUtils.replaceEach (string, 
				new String[] {"å", "ä", "ö", "ü","Å", "Ä", "Ö", "Ü", " "}, 
				new String[] {"aa", "ae", "oe", "ue","AA", "AE", "OE", "UE", "_"});
		return newString;
	}

	private int getLargestString(ArrayList<String> stringList) {

		int largestString = stringList.get(0).length();
		int index = 0;

		for(int i = 0; i < stringList.size(); i++)
		{
			if(stringList.get(i).length() > largestString)
			{
				largestString = stringList.get(i).length();
				index = i;
			}
		}

		return largestString;
	}
}