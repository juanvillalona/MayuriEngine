package com.mayuri.engine.audio;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class soundClip {
	
	private Clip clip = null;
	private FloatControl gainControl;

	public soundClip(String path) throws LineUnavailableException {
		
		try {
			InputStream audioSrc = soundClip.class.getResourceAsStream(path);
			InputStream bufferedIn = new BufferedInputStream(audioSrc); //throw our audio into the ram
			AudioInputStream ais = AudioSystem.getAudioInputStream(bufferedIn); //we are going to get this from our BufferedInputStream (Audio Input Stream)
			AudioFormat baseFormat = ais.getFormat();
			AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
													 baseFormat.getSampleRate(),
													 16,
													 baseFormat.getChannels(),
													 baseFormat.getChannels() * 2,
													 baseFormat.getSampleRate(),
													 false);
			
			AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais); //decoded audio input stream
			
			clip = AudioSystem.getClip();
			clip.open(dais); //tell this clip that we just created to open our decoded audio input stream
			
			gainControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
			
		} catch (UnsupportedAudioFileException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public void play() {
		if (clip == null) {
			return;
		}
			stop();
			clip.setFramePosition(0); //go to the beginning of the clip
			
			//we want the game to keep trying to play the sound if it can't
			while(!clip.isRunning()) {
				clip.start();
			}
		}
	
	public void stop() {
		if(clip.isRunning()) {
			clip.stop();
		}
	}
	
	public void close() {
		stop();
		clip.drain(); //will empty the stream
		clip.close(); //close the stream
	}
	
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		play();
	}
	
	public void setVolume(float value) {
		gainControl.setValue(value);
	}
	
	public boolean isRunning() {
		return clip.isRunning();
	}
}
