package akyDroid.gameFramework.implementation;

import akyDroid.gameFramework.Audio;
import akyDroid.gameFramework.FileIO;
import akyDroid.gameFramework.Game;
import akyDroid.gameFramework.Graphics;
import akyDroid.gameFramework.Input;
import akyDroid.gameFramework.Screen;
import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.PowerManager.WakeLock;
import android.view.Window;
import android.view.WindowManager;

public abstract class GameMain extends Activity implements Game {
	AndroidFastRenderView myRenderView;
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
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        boolean isPortrait = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
        int frameBufferWidth = isPortrait ? 800: 1280;
        int frameBufferHeight = isPortrait ? 1280: 800;
        Bitmap frameBuffer = Bitmap.createBitmap(frameBufferWidth,
                frameBufferHeight, Config.RGB_565);
        
        float scaleX = (float) frameBufferWidth
                / getWindowManager().getDefaultDisplay().getWidth();
        float scaleY = (float) frameBufferHeight
                / getWindowManager().getDefaultDisplay().getHeight();

        myRenderView = new AndroidFastRenderView(this, frameBuffer);
        myGraphics = new AndroidGraphics(getAssets(), frameBuffer);
        myFileIO = new AndroidFileIO(this);
        myAudio = new AndroidAudio(this);
        myInput = new AndroidInput(this, myRenderView, scaleX, scaleY);
        myScreen = getInitScreen();
        setContentView(myRenderView);
        
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "MyGame");
    }	
}
