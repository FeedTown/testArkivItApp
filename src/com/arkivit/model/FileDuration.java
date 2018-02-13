package com.arkivit.model;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.BasicConfigurator;

import io.humble.video.Demuxer;
import io.humble.video.Global;


public class FileDuration {

	private String audioVideoFile;
	private ArrayList<String> audioVideoList = new ArrayList<>();

	public  void getDuration(String path) {

		final Demuxer demuxer = Demuxer.make();

		try {
			demuxer.open(path, null, false, true, null, null);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			
		}
		
		final String formattedDuration = formatTimeStamp(demuxer.getDuration());
		System.out.printf("Duration: %s \n", 
				formattedDuration); 
		
		

	}

	public String formatTimeStamp(long duration) {
		if ( duration == Global.NO_PTS) {
			return "00:00:00";
		}
		double d = 1.0 * duration / Global.DEFAULT_PTS_PER_SECOND;
		int hours = (int) (d / (60*60));
		int mins = (int) ((d - hours*60*60) / 60);
		int secs = (int) (d - hours*60*60 - mins*60);
		return audioVideoFile =  String.valueOf(String.format("%1$02d:%2$02d:%3$02d", hours, mins, secs));

	}


	public ArrayList<String> getAudioVideoList(){
		return audioVideoList;
	}

	public String getAudioVideoDuration() {
		return audioVideoFile;
	}


}