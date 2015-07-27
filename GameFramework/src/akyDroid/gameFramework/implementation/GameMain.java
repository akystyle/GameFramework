package akyDroid.gameFramework.implementation;

import akyDroid.gameFramework.Audio;
import akyDroid.gameFramework.FileIO;
import akyDroid.gameFramework.Game;
import akyDroid.gameFramework.Graphics;
import akyDroid.gameFramework.Input;
import akyDroid.gameFramework.Screen;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Window;
import android.view.WindowManager;

public abstract class GameMain extends Activity implements Game {
	GameFastRenderView myRenderView;
	Graphics myGraphics;
	Audio myAudio;
	Input myInput;
	FileIO myFileIO;
	Screen myScreen;
	WakeLock myWakelock;
	
	@SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        boolean isPortrait = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
        int frameBufferWidth = isPortrait ? 480: 800;
        int frameBufferHeight = isPortrait ? 800: 480;
        Bitmap frameBuffer = Bitmap.createBitmap(frameBufferWidth,
                frameBufferHeight, Config.RGB_565);
        
        float scaleX = (float) frameBufferWidth
                / getWindowManager().getDefaultDisplay().getWidth();
        float scaleY = (float) frameBufferHeight
                / getWindowManager().getDefaultDisplay().getHeight();

        myRenderView = new GameFastRenderView(this, frameBuffer);
        myGraphics = new GameGraphics(getAssets(), frameBuffer);
        myFileIO = new GameFileIO(this);
        myAudio = new GameAudio(this);
        myInput = new GameInput(this, myRenderView, scaleX, scaleY);
        myScreen = getInitScreen();
        setContentView(myRenderView);
        
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        myWakelock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "MyGame");
    }
	
	@Override
	public void onResume(){
		super.onResume();
		myWakelock.acquire();
		myScreen.resume();
		myRenderView.resume();
	}
	
	@Override
	public void onPause() {
        super.onPause();
        myWakelock.release();
        myRenderView.pause();
        myScreen.pause();

        if (isFinishing())
            myScreen.dispose();
    }
	
	@Override
	public void onStop(){
		super.onStop();
	}
	
	@Override
	public void onRestart(){
		super.onRestart();
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
	}
	
	@Override
	public Input getInput(){
		return myInput;
	}
	
	@Override
    public FileIO getFileIO() {
        return myFileIO;
    }

    @Override
    public Graphics getGraphics() {
        return myGraphics;
    }

    @Override
    public Audio getAudio() {
        return myAudio;
    }

    @Override
    public void setScreen(Screen screen){
    	if(screen == null){
    		throw new IllegalArgumentException("Screen should not be null");
    	}
    	this.myScreen.pause();
    	this.myScreen.dispose();
    	screen.resume();
    	screen.update(0);
    	this.myScreen = screen;
    }
    
    public Screen getCurrentScreen(){
    	return myScreen;
    }
    
}
