package com.iwant.tt.super_yunzhisheng;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import com.unisound.client.IAudioSource;

/**
 * 音频源设置外部实现类
 * @author unisound Copyright (c) 2015, unisound.com All Rights Reserved
 *	该类用来定义相应的audiosource 操作
 */
public class AudioSourceImpl implements IAudioSource{
	
	public static final int FREQUENCY_16K = 16000;
	private static int FREQUENCY = FREQUENCY_16K;
	private static int bufferSizeInBytes;
	private AudioRecord audioRecord = null;

	protected static int CHANNEL = AudioFormat.CHANNEL_IN_MONO; // .CHANNEL_CONFIGURATION_MONO;
	protected static int ENCODING = AudioFormat.ENCODING_PCM_16BIT;
	
	protected static int STREAM_TYPE = AudioManager.STREAM_MUSIC;
	protected static int SAMPLE_RATE = 16 * 1000;
	protected static int CHANNEL_OUT = AudioFormat.CHANNEL_OUT_MONO;
	protected static int MODE = AudioTrack.MODE_STREAM;
	
	private AudioTrack mAudioTrack = null;
	
	static {
		bufferSizeInBytes = 6400; 
		int bufferSize = AudioRecord.getMinBufferSize(FREQUENCY, CHANNEL, ENCODING);
		if (bufferSizeInBytes < bufferSize) {
			bufferSizeInBytes = bufferSize;
		}
	}
	
	public AudioSourceImpl(){
	}

	@Override
	public int openAudioIn() {
		return open();
	}

	@Override
	public int openAudioOut() {
		return openOut();
	}

	@Override
	public int readData(byte[] buffer, int size) {
		return read(buffer, size);
	}

	@Override
	public int writeData(byte[] buffer, int size) {
		return write(buffer, size);
	}

	@Override
	public void closeAudioIn() {
		close();		
	}

	@Override
	public void closeAudioOut() {
		closeOut();
	}
	
	private int open() {
		audioRecord = new AudioRecord(MediaRecorder.AudioSource.DEFAULT,
				16000, CHANNEL, ENCODING, bufferSizeInBytes);
		if (audioRecord.getState() == AudioRecord.STATE_INITIALIZED) {
			audioRecord.startRecording();
			return 0;
		}
		return -1;
	}
	
	private int openOut() {
		mAudioTrack = new AudioTrack(STREAM_TYPE, SAMPLE_RATE, CHANNEL_OUT, ENCODING, bufferSizeInBytes, MODE);
		if (mAudioTrack.getState() == AudioTrack.STATE_INITIALIZED) {
			mAudioTrack.play();
			return 0;
		}
		return -1;
	}
	
	private void close() {
		if(audioRecord != null) {
 			if (audioRecord.getState() == AudioRecord.STATE_INITIALIZED) {
 				audioRecord.stop();
 			}
 			audioRecord.release();
 			audioRecord = null;
 		}
	}
	
	private void closeOut() {
		if (mAudioTrack != null) {
			if (mAudioTrack.getState() == AudioTrack.STATE_INITIALIZED) {
				mAudioTrack.stop();
			}
			mAudioTrack.flush();
			mAudioTrack.release();
			mAudioTrack = null;
		}
	}

	private int read(byte[] buffer , int size) {
		int read = 0;
		if(audioRecord != null) {
			read = audioRecord.read(buffer, 0, size);
		}
		return read;
	}
	
	private int write(byte[] buffer, int size){
		if(mAudioTrack != null) {
			return mAudioTrack.write(buffer, 0, size);
		}
		return 0;
	}
}
