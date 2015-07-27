package akyDroid.gameFramework.implementation;

import java.io.IOException;

import akyDroid.gameFramework.Audio;
import akyDroid.gameFramework.Music;
import akyDroid.gameFramework.Sound;
import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;

public class GameAudio implements Audio{

	AssetManager myAssets;
	SoundPool mySoundPool;
	
	public GameAudio(Activity myActivity){
		myActivity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		myAssets = myActivity.getAssets();
		mySoundPool = new SoundPool(20,AudioManager.STREAM_MUSIC,0);
	}
	
	@Override
	public Music createMusic(String file) {
		try{
			AssetFileDescriptor myAssetDesc = myAssets.openFd(file);
			return new GameMusic(myAssetDesc);
		}catch(IOException e){
			throw new RuntimeException("Error Loading Music: '" + file + "'");
		}
	}

	@Override
	public Sound createSound(String file) {
		try{
			AssetFileDescriptor myAssetDesc = myAssets.openFd(file);
			int mySoundID = mySoundPool.load(myAssetDesc, 0);
			return new GameSound(mySoundPool,mySoundID);
		} catch (IOException e){
			throw new RuntimeException("Error Loading Music: '" + file + "'");
		}
	}

}
