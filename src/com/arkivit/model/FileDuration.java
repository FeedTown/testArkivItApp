package com.arkivit.model;

import java.io.IOException;
import java.util.ArrayList;
import io.humble.video.Demuxer;
import io.humble.video.Global;

/**
 * 
 * The FileDuration class job is to find out the duration of video/audio files
 * and display the duration in a certain format.
 * 
 * @author Roberto Blanco
 * @since 2018-02-13
 *
 */
public class FileDuration {

	private String audioVideoFile;
	
	private ArrayList<String> audioVideoList = new ArrayList<>();

	/**
	 * Opens up Demuxer container so a file can be read and then
	 * retrieve duration from file.
	 * 
	 * @param file A String that represents a file.
	 */
	public  void getDuration(String file) {

		final Demuxer demuxer = Demuxer.make();

		try {
			demuxer.open(file, null, false, true, null, null);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			
		}
		
		//*final String formattedDuration =*/ 
		formatTimeStamp(demuxer.getDuration());
		//System.out.printf("Duration: %s \n", 
		//		formattedDuration); 
		
		try {
			demuxer.close();
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Formats a parsed long so duration displays
	 * accordingly to regulations of customer.
	 * 
	 * @param duration  Duration for a file
	 * @return Formatted long thats been parsed to a String
	 */
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