
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

import org.apache.commons.io.FilenameUtils;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;



public class MetadataToExcel {

	 private String excelFileName = "standard.xls";
	 private long fileSize;
	 private String filePath;
	 private String targetexcelFilepath = "/Users/RobertoBlanco/hej";  // generated excel file location
	 private String sourceFolderPath = "/Users/RobertoBlanco/Desktop"; // source Directory
	 private int totalFileCount = 0, tCount= 0; // variable to store total file count
	 private int fileCount = 0; // variable to store required files count
	 private ArrayList<String> aList = new ArrayList<String>();
	 private ArrayList<String> filePathList = new ArrayList<String>();
	 private ArrayList<Long> sizeList = new ArrayList<Long>();
	 private ArrayList<File> fList;// = new ArrayList<File>();
	 //private int folderCounter = 0;
	 private int filec = 0;
	 private String windowsCharset = "Cp1252", standardCharset = "UTF-8";
	 
	 private InputStreamReaderDecoder decoder = new InputStreamReaderDecoder();
	 private FileDuration fileDuration = new FileDuration();
	 //private static ArrayList<String> fileExtention = new ArrayList<String>();
	
	
	
	public MetadataToExcel()
	{
		
	}
	
	public MetadataToExcel(String excelFileName)
	{   
		fList = new ArrayList<File>();
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
//				System.out.println("Nr " + filec + " : " + file.getName());
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
		
		 try {
			 
			 if(!fList.isEmpty())
			 {
			   for (int numberOfFilesInFolder = 0; numberOfFilesInFolder < fList.size(); numberOfFilesInFolder++) {
				 
				   decoder.fileEncoder(fList.get(numberOfFilesInFolder).getParentFile().getAbsolutePath(), fList.get(numberOfFilesInFolder).getName());  
			    
				 String files = fList.get(numberOfFilesInFolder).getName();
				 //sSystem.out.println("Current file " + files );
				 
			     if(files.endsWith(".mov")||files.endsWith(".mp4")||files.endsWith(".mp3"))
			     {
			    	 	
			   	 fileDuration.CheckFileDuration(fList.get(numberOfFilesInFolder).getParentFile().getAbsolutePath() + "/" + files);
			    			
			     }
			     
			     
			    	 String fPath;//, testPath;
			    	 fileSize = fList.get(numberOfFilesInFolder).length();
			    	 
			    	 
			    	 fPath = fList.get(numberOfFilesInFolder).getParentFile().getAbsolutePath();
			    	 //System.out.println("current path" + fPath);
			    	 fPath = fPath.replace(sourceFolderPath, "");
			    	 
			      aList.add(files);
			      sizeList.add(fileSize);
			      filePathList.add(fPath);
			      decoder.getUtfList().add(decoder.getUtfString());
			      //fileDuration.getAudioVideoList().add(fileDuration.getAudioVideoFiles());
			 
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
		  
		  createExcelFileAndGetContent();
		
	}
	
	/*public void createExcelTest(ArrayList<File> fileList)
	{
		/*
		  Arrays.sort(listOfFilesInDirectory, new Comparator<Object>() {
		      public int compare(final Object o1, final Object o2) {
		          String s1 = ((File) o1).getName().toLowerCase();
		          String s2 = ((File) o2).getName().toLowerCase();
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
		      }
		    });*/
		  
		  
		/*  try {
			   for (int numberOfFilesInFolder = 0; numberOfFilesInFolder < fileList.size(); numberOfFilesInFolder++) {
				   
			     String files = fileList.get(numberOfFilesInFolder).getName();
			     
			    	 String fPath;
			    	 fileSize = fileList.get(numberOfFilesInFolder).length();
			    	 
			    	 
			    	 fPath = fileList.get(numberOfFilesInFolder).getAbsolutePath();
			    	 fPath = fPath.replace(sourceFolderPath, "");
			    	
			    	 
			      aList.add(files);
			      sizeList.add(fileSize);
			      filePathList.add(fPath);
			      //fileCount++;
			      
				   System.out.println("File size: " + fileSize);
				   
			     }
			    
			   
			  } catch (Exception e) {
			   // TODO Auto-generated catch block
			   e.printStackTrace();
			  }
		  
		  createExcelFileAndGetContent();
		  
	}
	
	public void createExcel()
	{
		// TODO Auto-generated method stub
		  // to get file names
		Path path;
		String pathTest;
		  File folder = new File(sourceFolderPath);
		  File[] listOfFilesInDirectory = folder.listFiles();
		  
		  Arrays.sort(listOfFilesInDirectory, new Comparator<Object>() {
		      public int compare(final Object o1, final Object o2) {
		          String s1 = ((File) o1).getName().toLowerCase();
		          String s2 = ((File) o2).getName().toLowerCase();
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
		      }
		    });
		  
		 /* for(File file : listOfFilesInDirectory)
		  {
			  if (file.isFile())
			   {
				  tCount++;
				  System.out.println(file.getAbsolutePath());
			   }
			  else if(file.isDirectory())
			  {
				  folderCounter++;
			  }
			    	
		  }
		  
		  System.out.println("Total file count = " + tCount);
		  System.out.println("Total folder count = " + folderCounter);*/
		  
		  /*try {
		   for (int numberOfFilesInFolder = 0; numberOfFilesInFolder < listOfFilesInDirectory.length; numberOfFilesInFolder++) {
			   
		     String files = listOfFilesInDirectory[numberOfFilesInFolder]
		       .getName();
		     
		    	 String fPath;
		    	 fileSize = listOfFilesInDirectory[numberOfFilesInFolder].length();
		    	 
		    	 
		    	 fPath = listOfFilesInDirectory[numberOfFilesInFolder].getParentFile().getAbsolutePath();
		    	 fPath = fPath.replace(sourceFolderPath, "");
		    	
		    	 
		      aList.add(files);
		      sizeList.add(fileSize);
		      filePathList.add(fPath);
		      //fileCount++;
		      
			   System.out.println("File size: " + fileSize);
			   
		     }
		    
		   
		  } catch (Exception e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		  }
		  
		  System.out.println("Total file count = " + totalFileCount);
		  System.out.println("Total folder count = " + folderCounter);
		  
		 
		  createExcelFileAndGetContent();
	}*/
	
	 public void createExcelFileAndGetContent() {
		 
		  File file = new File(targetexcelFilepath +"/"+ excelFileName);
		  
		  try {
		   WorkbookSettings wbSettings = new WorkbookSettings();
		   WritableWorkbook workbook = Workbook.createWorkbook(file,
		     wbSettings);
		   workbook.createSheet("File Names", 0);
		   System.out.println("Excel file is created in path -- "
		     + targetexcelFilepath);
		   WritableSheet excelSheet = (WritableSheet) workbook.getSheet(0);
		   if (!aList.isEmpty()) {
		    for (int rowNumber = 0; rowNumber < aList.size(); rowNumber++) {
		     
		    	/*CellView cell = workbook.getSheet(0).getColumnView(rowNumber);
		    	cell.setSize(14000);
		    	workbook.getSheet(0).setColumnView(0, cell);*/
		    	
		    	
		    	
		    	//System.out.println(aList.get(rowNumber));
		     
		     String sizeInString = Objects.toString(sizeList.get(rowNumber), null); 
		     String fileExtention = FilenameUtils.getExtension(aList.get(rowNumber));
		    // FilenameUtils.get
		     
		    
		     Label fileDurationLabel = new Label (5,0, "Speltid");
		    Label durationLabel = new Label(5, rowNumber+1, fileDuration.getAudioVideoList().get(rowNumber));
		     
		     Label utfLabelName = new Label(4,0, "Teckenuppsättning");
			 Label utfLabel = new Label(4, rowNumber+1, decoder.getUtfList().get(rowNumber));
		     
		     Label filePathLabelName = new Label(6, 0, "FilePath(path,url)");
		     
		     Label filePathLabel = new Label(6, rowNumber+1, filePathList.get(rowNumber));
		     
		     Label label2 = new Label(0, 0, "Filename");
		     
		     Label fileTypeLabelName = new Label(1,0,"FileType");
		     Label fileTypeLabel = new Label(1, rowNumber+1, fileExtention);
		     Label fileSizeLabelName = new Label(2, 0, "File Size (in Bytes)");
		     Label fileSizeLabel = new Label(2, rowNumber+1, sizeInString);
		     Label label = new Label(0, rowNumber+1, aList.get(rowNumber));
		     
		     
		     excelSheet.setColumnView(0, getLargestString(aList));
		     excelSheet.setColumnView(6, getLargestString(filePathList));
		     excelSheet.addCell(filePathLabelName);
		     excelSheet.addCell(filePathLabel);
		     excelSheet.addCell(label2);
		     excelSheet.addCell(fileTypeLabelName);
		     excelSheet.addCell(fileTypeLabel);
		     excelSheet.addCell(fileSizeLabelName);
		     excelSheet.addCell(label);
		     excelSheet.addCell(fileSizeLabel);
		     excelSheet.addCell(utfLabelName);
		     excelSheet.addCell(utfLabel);
		     excelSheet.addCell(fileDurationLabel);
		     excelSheet.addCell(durationLabel);
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

	private int getLargestString(ArrayList<String> aList2) {
		
		int largestString = aList2.get(0).length();
	    int index = 0;
	    
	    for(int i = 0; i < aList2.size(); i++)
        {
            if(aList2.get(i).length() > largestString)
            {
                largestString = aList2.get(i).length();
                                index = i;
            }
        }
	    
		return largestString;
	}
}
