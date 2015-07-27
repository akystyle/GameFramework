package akyDroid.gameFramework.implementation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameFastRenderView extends SurfaceView implements Runnable {

	GameMain myGame;
	Bitmap myFrameBuffer;
	Thread myRenderingThread = null;
	SurfaceHolder myHolder;
	volatile boolean myRunning = false;
	
	public GameFastRenderView(GameMain game,Bitmap frameBuffer){
		super(game);
		this.myGame = game;
		this.myFrameBuffer = frameBuffer;
		this.myHolder = getHolder();
	}
	public void resume(){
		myRunning = true;
		myRenderingThread = new Thread(this);
		myRenderingThread.start();
	}
	@Override
	public void run() {
		Rect myDstRect = new Rect();
		long myStartTime = System.nanoTime();
		
		while(myRunning){
			if(!myHolder.getSurface().isValid())
				continue;
			float myDeltaTime = (System.nanoTime() - myStartTime)/10000000.000f;
			myStartTime = System.nanoTime();
			
			if(myDeltaTime > 3.15){
				myDeltaTime = (float) 3.15;
			}
			
			myGame.getCurrentScreen().update(myDeltaTime);
			myGame.getCurrentScreen().paint(myDeltaTime);
			
			Canvas myCanvas = myHolder.lockCanvas();
			myCanvas.getClipBounds(myDstRect);
			myCanvas.drawBitmap(myFrameBuffer, null, myDstRect,null);
			myHolder.unlockCanvasAndPost(myCanvas);
		}
	}
	public void pause(){
		myRunning = false;
		while(true){
			try{
				myRenderingThread.join();
				break;
			}catch(InterruptedException e){
				
			}
		}
	}
	
}
