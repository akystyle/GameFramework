package akyDroid.gameFramework.implementation;

import java.util.ArrayList;
import java.util.List;

//import akyDroid.gameFramework.Input;
import akyDroid.gameFramework.Input.TouchEvent;
import akyDroid.gameFramework.Pool;
import akyDroid.gameFramework.Pool.PoolObjectFactory;
import android.view.MotionEvent;
import android.view.View;

public class SingleTouchHandler implements TouchHandler{

    boolean isTouched;
    int touchX;
    int touchY;
    Pool<TouchEvent> myTouchEventPool;
    List<TouchEvent> myTouchEvents = new ArrayList<TouchEvent>();
    List<TouchEvent> myTouchEventsBuffer = new ArrayList<TouchEvent>();
    float myScaleX;
    float myScaleY;
	
    public SingleTouchHandler(View myView,float scaleX,float scaleY) {
    	PoolObjectFactory<TouchEvent> myFactory = new PoolObjectFactory<TouchEvent>() {
    		@Override
    		public TouchEvent createObject(){
    			return new TouchEvent();
    		}
		};
		myTouchEventPool = new Pool<TouchEvent>(myFactory, 100);
		myView.setOnTouchListener(this);
		
		this.myScaleX = scaleX;
		this.myScaleY = scaleY;
	}
    
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		synchronized (this) {
			TouchEvent deviceTouchEvent = myTouchEventPool.newObject();
			switch(event.getAction()){
			case MotionEvent.ACTION_DOWN:
				deviceTouchEvent.type = TouchEvent.TOUCH_DOWN;
				isTouched = true;
				break;
			case MotionEvent.ACTION_MOVE:
				deviceTouchEvent.type = TouchEvent.TOUCH_DRAGGED;
				isTouched = true;
				break;
			case MotionEvent.ACTION_CANCEL:
			case MotionEvent.ACTION_UP:
				deviceTouchEvent.type = TouchEvent.TOUCH_UP;
				isTouched = false;
				break;
			}
			deviceTouchEvent.x = (int)(event.getX() * myScaleX);
			deviceTouchEvent.y = (int)(event.getY() * myScaleY);
			myTouchEventsBuffer.add(deviceTouchEvent);
			return true;
		}
	}

	@Override
	public boolean isTouchDown(int pointer) {
		synchronized (this) {
			if(pointer == 0)
				return isTouched;
			else
				return false;
		}
	}

	@Override
	public int getTouchX(int pointer) {
		synchronized (this) {
			return touchX;
		}
	}

	@Override
	public int getTouchY(int pointer) {
		synchronized (this) {
			return touchY;
		}
	}

	@Override
	public List<TouchEvent> getTouchEvents() {
		synchronized (this) {
			int len = myTouchEvents.size();
			for(int i = 0;i < len; i++)
				myTouchEventPool.free(myTouchEvents.get(i));
			myTouchEvents.clear();
			myTouchEvents.addAll(myTouchEventsBuffer);
			myTouchEventsBuffer.clear();
			return myTouchEvents;
		}
		
	}
}