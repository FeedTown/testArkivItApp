package com.arkivit.model;
import java.util.ArrayList;

import org.apache.log4j.BasicConfigurator;

import com.xuggle.xuggler.IContainer;

public class FileDuration {

	private String audioVideoFile;
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

	public ArrayList<String> getAudioVideoList(){
		return audioVideoList;
	}
	
	public String getAudioVideoDuration() {
		return audioVideoFile;
	}

}
