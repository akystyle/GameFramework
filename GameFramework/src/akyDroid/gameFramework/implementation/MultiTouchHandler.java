package akyDroid.gameFramework.implementation;

import java.util.ArrayList;
import java.util.List;

import akyDroid.gameFramework.Input.TouchEvent;
import akyDroid.gameFramework.Pool.PoolObjectFactory;
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
			int myAction = event.getAction() & MotionEvent.ACTION_MASK;
			int myPointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
			int myPointerCount = event.getPointerCount();
			TouchEvent myTouchEvent;
			for(int i=0 ; i < MAX_TOUCHPOINTS ; i++){
				if(i >= myPointerCount){
					isTouched[i] = false;
					id[i] = -1;
					continue;
				}
				int pointerId = event.getPointerId(i);
				if(event.getAction() != MotionEvent.ACTION_MOVE && i != myPointerIndex){
					// if it's an up/down/cancel/out event, mask the id to see if we should process it for this touch
                    // point
					continue;
				}
				switch(myAction){
				case MotionEvent.ACTION_DOWN:
				case MotionEvent.ACTION_POINTER_DOWN:
					myTouchEvent = myTouchEventPool.newObject();
					myTouchEvent.type = TouchEvent.TOUCH_DOWN;
					myTouchEvent.pointer = pointerId;
					myTouchEvent.x = touchX[i] = (int) (event.getX(i) * myScaleX);
					myTouchEvent.y = touchY[i] = (int) (event.getY(i) * myScaleY);
					isTouched[i] = true;
					id[i] = pointerId;
					myTouchEventsBuffer.add(myTouchEvent);
					break;
					
				case MotionEvent.ACTION_UP:
				case MotionEvent.ACTION_POINTER_UP:
				case MotionEvent.ACTION_CANCEL:
					myTouchEvent = myTouchEventPool.newObject();
					myTouchEvent.type = TouchEvent.TOUCH_UP;
					myTouchEvent.pointer = pointerId;
					myTouchEvent.x = touchX[i] = (int) (event.getX(i) * myScaleX);
					myTouchEvent.y = touchY[i] = (int) (event.getY(i) * myScaleY);
					isTouched[i] = false;
					id[i] = -1;
					myTouchEventsBuffer.add(myTouchEvent);
					break;
					
				case MotionEvent.ACTION_MOVE:
					myTouchEvent = myTouchEventPool.newObject();
					myTouchEvent.type = TouchEvent.TOUCH_DRAGGED;
					myTouchEvent.pointer = pointerId;
					myTouchEvent.x = touchX[i] = (int) (event.getX(i) * myScaleX);
					myTouchEvent.y = touchY[i] = (int) (event.getY(i) * myScaleY);
					isTouched[i] = true;
					id[i] = pointerId;
					myTouchEventsBuffer.add(myTouchEvent);
					break;
				}
			}
			return true;
		}
	}
	@Override
	public boolean isTouchDown(int pointer) {
		synchronized (this) {
			int index = getIndex(pointer);
			if(index < 0 || index >=MAX_TOUCHPOINTS)
				return false;
			else
				return isTouched[index];
		}
	}
	@Override
	public int getTouchX(int pointer) {
		synchronized (this) {
			int index = getIndex(pointer);
			if(index < 0 || index >=MAX_TOUCHPOINTS)
				return 0;
			else
				return touchX[index];
		}
	}
	@Override
	public int getTouchY(int pointer) {
		synchronized (this) {
			int index = getIndex(pointer);
			if(index < 0 || index >=MAX_TOUCHPOINTS)
				return 0;
			else
				return touchY[index];
		}
	}
	@Override
	public List<TouchEvent> getTouchEvents() {
        synchronized (this) {
            int len = myTouchEvents.size();
            for (int i = 0; i < len; i++)
                myTouchEventPool.free(myTouchEvents.get(i));
            myTouchEvents.clear();
            myTouchEvents.addAll(myTouchEventsBuffer);
            myTouchEventsBuffer.clear();
            return myTouchEvents;
        }
	}
	
    private int getIndex(int pointerId) {
        for (int i = 0; i < MAX_TOUCHPOINTS; i++) {
            if (id[i] == pointerId) {
                return i;
            }
        }
        return -1;
    }
}
