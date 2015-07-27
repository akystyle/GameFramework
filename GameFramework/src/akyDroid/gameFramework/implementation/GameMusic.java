package akyDroid.gameFramework.implementation;

import java.io.IOException;

import akyDroid.gameFramework.Music;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;

public class GameMusic implements Music, OnCompletionListener,
		OnSeekCompleteListener, OnPreparedListener, OnVideoSizeChangedListener {

	MediaPlayer myMediaPlayer;
	boolean isPrepared = false;

	public GameMusic(AssetFileDescriptor myAssetDesc) {
		myMediaPlayer = new MediaPlayer();
		try {
			myMediaPlayer.setDataSource(myAssetDesc.getFileDescriptor(),
					myAssetDesc.getStartOffset(), myAssetDesc.getLength());
			myMediaPlayer.prepare();
			isPrepared = true;
			myMediaPlayer.setOnCompletionListener(this);
			myMediaPlayer.setOnSeekCompleteListener(this);
			myMediaPlayer.setOnPreparedListener(this);
			myMediaPlayer.setOnVideoSizeChangedListener(this);
		} catch (Exception e) {
			throw new RuntimeException("Couldn't load music");
		}
	}

	@Override
	public void play() {
		if (this.myMediaPlayer.isPlaying())
			return;

		try {
			synchronized (this) {
				if (!isPrepared)
					myMediaPlayer.prepare();
				myMediaPlayer.start();
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void stop() {
		if (this.myMediaPlayer.isPlaying() == true) {
			this.myMediaPlayer.stop();

			synchronized (this) {
				isPrepared = false;
			}
		}
	}
	@Override
	public void pause() {
		if (this.myMediaPlayer.isPlaying())
			myMediaPlayer.pause();
	}
	@Override
	public void setLooping(boolean looping) {
		myMediaPlayer.setLooping(looping);
	}
	@Override
	public void setVolume(float volume) {
		myMediaPlayer.setVolume(volume, volume);
	}
	@Override
	public boolean isPlaying() {
		return this.myMediaPlayer.isPlaying();
	}
	@Override
	public boolean isStopped() {
		return !isPrepared;
	}
	@Override
	public boolean isLooping() {
		return this.myMediaPlayer.isLooping();
	}
	@Override
	public void dispose() {
		if (this.myMediaPlayer.isPlaying()) {
			this.myMediaPlayer.stop();
		}
		this.myMediaPlayer.release();
	}
	@Override
	public void seekBegin() {
		myMediaPlayer.seekTo(0);
	}
	@Override
	public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
		// TODO Auto-generated method stub
	}
	@Override
	public void onPrepared(MediaPlayer mp) {
		synchronized (this) {
			isPrepared = true;
		}
	}
	@Override
	public void onSeekComplete(MediaPlayer mp) {
		// TODO Auto-generated method stub
	}
	@Override
	public void onCompletion(MediaPlayer mp) {
		synchronized (this) {
			isPrepared=false;
		}
	}

}
