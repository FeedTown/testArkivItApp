import java.io.File;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.activation.MimeType;
import javax.activation.MimetypesFileTypeMap;
import javax.activation.FileTypeMap;

import org.apache.log4j.BasicConfigurator;
import org.apache.tika.Tika;

import com.xuggle.xuggler.IContainer;

public class FileDuration {
	
	public static void main (String[] args) {
		
		String path = "/Users/RobertoBlanco/Desktop/source/MinaKvarter.mov";
		 //String file = "/Users/RobertoBlanco/Desktop/source/robertosimon.m4v";;
		File newFile = new File("/Users/RobertoBlanco/Desktop/source/MjölkbackSlideLiljeholemn.mp4");
		
		FileDuration fileDuration = new FileDuration();
		String video = newFile.toString();
		String test = fileDuration.getMimeType(path);
		/*System.out.println(test);
		
		if(fileDuration.isVideo(path)) {
			System.out.println("The file is a video.");
	
		} */
		
		System.out.println("This is a tika file detector: " + fileDuration.getFileType(path));
		
		
		
		
	}

	/*private final static String fileName = "/Users/RobertoBlanco/Desktop/front-desk-bells-daniel_simon.mp3";
	private final static String fileName1 = "/Users/RobertoBlanco/Desktop//MinaKvarter.mov";
	private String audioLengthSeconds;
	private String videoLengthSeconds;*/
	//private String filePath;
	private String audioVideoFile;
	private File file;
	private ArrayList<String> audioVideoList = new ArrayList<>();
	private Tika defaultTika = new Tika();
	
	public String getFileType(String fileUrl) {
		return defaultTika.detect(fileUrl);
	}
	

	public FileDuration() {
		//this.filePath = filePath;
		//CheckFileDuration();
	}
	
	/*public static String getMimeType(String fileUrl) {
		MimeType mimeType = new MimeType();
	    fileUrl = mimeType.getPrimaryType();
	  	
		return fileUrl;
	}*/
	
	public  String getMimeType(String fileUrl) {
		MimetypesFileTypeMap m = new MimetypesFileTypeMap();
		MimeType mimeType = new MimeType();
		//String fileType =  m.getContentType(fileUrl.getName());
		String fileType =  m.getContentType(fileUrl);
		
		
		return fileType;
	} 
	
	public boolean isVideo(String path) {
		String mimeType = URLConnection.guessContentTypeFromName(path);
		return mimeType != null && mimeType.startsWith("video");
	}
	
	public void CheckFileDuration (String filePath){
		/*IContainer audioContainer = IContainer.make();
	int audioResult = audioContainer.open(fileName, IContainer.Type.READ, null);
	long audioDuration = audioContainer.getDuration();*/

		/*String time = String.format("%02d min, %d sec",
			TimeUnit.MILLISECONDS.toMinutes(duration),
			TimeUnit.MILLISECONDS.toSeconds(duration),
			TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));*/
		/*double audioSeconds = audioDuration / 1000000;
	 audioLengthSeconds = String.valueOf(audioSeconds);*/
		//double audioMinutes = audioSeconds / 60;

		//String time1 = String.format("%02d sec", TimeUnit.MILLISECONDS.toSeconds(duration));

		/*System.out.println("Audio length in seconds: " + audioSeconds);
	System.out.println("Audio lenght in minutes: " + audioMinutes);*/

		/*IContainer videoContainer = IContainer.make();

	int videoResult = videoContainer.open(fileName1, IContainer.Type.READ, null);
	long videoDuration = videoContainer.getDuration();

	double videoSeconds = videoDuration / 1000000;
	videoLengthSeconds = String.valueOf(videoSeconds);*(
	//double videoMinutes = videoSeconds / 60;
	/*System.out.println("-------------------------------");
	System.out.println("Video length in seconds: " + videoSeconds);
	System.out.println("Video lenght in minutes: " + videoMinutes); */
		BasicConfigurator.configure();
		IContainer fileContainer = IContainer.make();
		int fileResult = fileContainer.open(filePath, IContainer.Type.READ, null);
		long fileDuration = fileContainer.getDuration();
		double fileSeconds = fileDuration / 1000000;
		audioVideoFile =  String.valueOf(fileSeconds);
		//audioVideoList.add(audioVideoFiles);
		
		if(fileResult<0) {
			System.out.println("Problem with the file...");
		}

	}
	
	public File getFile() {
		return file;
	}

	public ArrayList<String> getAudioVideoList(){
		return audioVideoList;
	}
	
	public String getAudioVideoDuration() {
		return audioVideoFile;
	}

}
