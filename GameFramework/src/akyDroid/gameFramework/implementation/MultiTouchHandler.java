package akyDroid.gameFramework.implementation;

import java.util.ArrayList;
import java.util.List;

import akyDroid.gameFramework.Input.TouchEvent;
import akyDroid.gameFramework.Pool;
import android.view.MotionEvent;
import android.view.View;

public class MultiTouchHandler implements TouchHandler{
private static final int MAX_TOUCHPOINTS = 10;
    
    boolean[] isTouched = new boolean[MAX_TOUCHPOINTS];
    int[] touchX = new int[MAX_TOUCHPOINTS];
    int[] touchY = new int[MAX_TOUCHPOINTS];
    int[] id = new int[MAX_TOUCHPOINTS];
    Pool<TouchEvent> myTouchEventPool;
    List<TouchEvent> myTouchEvents = new ArrayList<TouchEvent>();
    List<TouchEvent> myTouchEventsBuffer = new ArrayList<TouchEvent>();
    float myScaleX;
    float myScaleY;
    
    public MultiTouchHandler(View myView, float scaleX, float scaleY) {

    	
	}
    
	@Override
	public boolean onTouch(View v, MotionEvent event) {

		
		
		return false;
	}
	@Override
	public boolean isTouchDown(int pointer) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public int getTouchX(int pointer) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int getTouchY(int pointer) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public List<TouchEvent> getTouchEvents() {
		// TODO Auto-generated method stub
		return null;
	}

}
