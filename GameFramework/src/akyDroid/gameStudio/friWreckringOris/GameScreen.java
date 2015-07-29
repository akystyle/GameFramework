package akyDroid.gameStudio.friWreckringOris;

import java.util.List;

import akyDroid.gameFramework.Game;
import akyDroid.gameFramework.Input.TouchEvent;
import akyDroid.gameFramework.Screen;
import android.graphics.Color;
import android.graphics.Paint;

public class GameScreen extends Screen{

	enum GameState{
		Ready,Running,Paused,GameOver
	}

	GameState state = GameState.Ready;
	
	int lifeleft = 1;
	Paint myPainter;
	
	public GameScreen(Game game) {
		super(game);
		
		myPainter = new Paint();
		myPainter.setTextSize(30);
		myPainter.setTextAlign(Paint.Align.CENTER);
		myPainter.setAntiAlias(true);
		myPainter.setColor(Color.WHITE);
		
	}
	
	@Override
	public void update(float deltaTime) {
		List<TouchEvent> myTouchEvent = game.getInput().getTouchEvents();
		
		if(state == GameState.Ready){
		updateReady(myTouchEvent);	
		}else if(state == GameState.Running){
			updateRunning(myTouchEvent);	
		}else if(state == GameState.Paused){
			updatePaused(myTouchEvent);
		}else if(state == GameState.GameOver){
			updateGameOver(myTouchEvent);
		}
	}
	
	private void updateGameOver(List<TouchEvent> myTouchEvent) {
		
	}

	private void updatePaused(List<TouchEvent> myTouchEvent) {
		
	}

	private void updateRunning(List<TouchEvent> myTouchEvent) {
		
	}

	private void updateReady(List<TouchEvent> myTouchEvent) {
		if(myTouchEvent.size()>0)
			state = GameState.Running;
	}

	@Override
	public void paint(float deltaTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void backButton() {
		// TODO Auto-generated method stub
		
	}
	
	
}
