package akyDroid.gameFramework.implementation;

import java.util.List;

import akyDroid.gameFramework.Input;
import android.content.Context;
import android.os.Build.VERSION;
import android.view.View;

public class GameInput implements Input{
	TouchHandler myTouchHandler;
	
	public GameInput(Context myContext, View myView, float scaleX, float scaleY){
		if(VERSION.SDK_INT < 5)
			myTouchHandler = new SingleTouchHandler(myView,scaleX,scaleY);
		else
			myTouchHandler = new MultiTouchHandler(myView,scaleX,scaleY);
	}

	@Override
	public boolean isTouchDown(int pointer) {
		return myTouchHandler.isTouchDown(pointer);
	}

	@Override
	public int getTouchX(int pointer) {
		return myTouchHandler.getTouchX(pointer);
	}

	@Override
	public int getTouchY(int pointer) {
		return myTouchHandler.getTouchY(pointer);
	}

	@Override
	public List<TouchEvent> getTouchEvents() {
		return myTouchHandler.getTouchEvents();
	}
}
