package akyDroid.gameFramework.implementation;

import akyDroid.gameFramework.Sound;
import android.media.SoundPool;

public class GameSound implements Sound{
	
	int mySoundID;
	SoundPool mySoundPool;
	
	public GameSound(SoundPool soundPool, int soundID){
		mySoundID = soundID;
		mySoundPool = soundPool;
	}

	@Override
	public void play(float volume) {
		mySoundPool.play(mySoundID, volume, volume, 0, 0, 1);
	}

	@Override
	public void dispose() {
		mySoundPool.unload(mySoundID);
	}

}
