import java.util.ArrayList;

import org.apache.log4j.BasicConfigurator;

import com.xuggle.xuggler.IContainer;

public class FileDuration {

	/*private final static String fileName = "/Users/RobertoBlanco/Desktop/front-desk-bells-daniel_simon.mp3";
	private final static String fileName1 = "/Users/RobertoBlanco/Desktop/MinaKvarter.mov";
	private String audioLengthSeconds;
	private String videoLengthSeconds;*/
	//private String filePath;
	private String audioVideoFiles;
	private ArrayList<String> audioVideoList = new ArrayList<>();
	
	
	
	
	public FileDuration() {
		//this.filePath = filePath;
		//CheckFileDuration();
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
		audioVideoFiles =  String.valueOf(fileSeconds);
		//audioVideoList.add(audioVideoFiles);
		
		if(fileResult<0) {
			System.out.println("Problem with the file...");
		}

	}

	public ArrayList<String> getAudioVideoList(){
		return audioVideoList;
	}
	
	public String getAudioVideoDuration() {
		return audioVideoFiles;
	}


	public void setAudioVideoFiles(String audioVideoFiles) {
		this.audioVideoFiles = audioVideoFiles;
	}

}
