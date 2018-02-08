package com.arkivit.model;
import java.time.Duration;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.log4j.BasicConfigurator;

import com.xuggle.xuggler.IContainer;

public class FileDuration {

	private String audioVideoFile;
	private ArrayList<String> audioVideoList = new ArrayList<>();
	//DurationFormatUtils durationFormat = new  DurationFormatUtils();
	Duration durationFormat;
	
	public FileDuration() {
		//this.filePath = filePath;
		//CheckFileDuration();
	}
	

	public void CheckFileDuration (String filePath){

		BasicConfigurator.configure();
		IContainer fileContainer = IContainer.make();
		int fileResult = fileContainer.open(filePath, IContainer.Type.READ, null);
		long fileDuration = fileContainer.getDuration();
		long durationSeconds = fileDuration / 1000000;
		
		String hms = String.format("%02d:%02d:%02d", TimeUnit.SECONDS.toHours(durationSeconds),
		        TimeUnit.SECONDS.toMinutes(durationSeconds) - 
		        TimeUnit.HOURS.toMinutes(TimeUnit.SECONDS.toHours(durationSeconds)),
		        TimeUnit.SECONDS.toSeconds(durationSeconds) - 
		        TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(durationSeconds))); 
		
		audioVideoFile =  String.valueOf(hms);
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
