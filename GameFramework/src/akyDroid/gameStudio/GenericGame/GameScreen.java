package akyDroid.gameStudio.GenericGame;

import java.util.List;

import akyDroid.gameFramework.Game;
import akyDroid.gameFramework.Graphics;
import akyDroid.gameFramework.Input.TouchEvent;
import akyDroid.gameFramework.Screen;
import android.graphics.Color;
import android.graphics.Paint;

public class GameScreen extends Screen {

	enum GameState {
		Ready, Running, Paused, GameOver
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

		if (state == GameState.Ready) {
			updateReady(myTouchEvent);
		} else if (state == GameState.Running) {
			updateRunning(myTouchEvent);
		} else if (state == GameState.Paused) {
			updatePaused(myTouchEvent);
		} else if (state == GameState.GameOver) {
			updateGameOver(myTouchEvent);
		}
	}

	private void updateGameOver(List<TouchEvent> myTouchEvent) {
		int len = myTouchEvent.size();
		for(int i =0;i<len;i++){
			TouchEvent tempEvent = myTouchEvent.get(i);
			if(tempEvent.type == TouchEvent.TOUCH_UP){
				if(tempEvent.x > 300 && tempEvent.x < 980 && tempEvent.y > 100 && tempEvent.y < 500){
					GameRestart();
					game.setScreen(new MainMenuScreen(game));
					return;
				}
			}
		}
	}

	private void updatePaused(List<TouchEvent> myTouchEvent) {
		int len = myTouchEvent.size();
		for (int i =0;i < len; i++){
			TouchEvent tempEvent = myTouchEvent.get(i);
			if(tempEvent.type == TouchEvent.TOUCH_UP){
				//TODO write code to return to Game after the Pause has been resumed
			}
		}
	}

	private void updateRunning(List<TouchEvent> myTouchEvent) {

		// 1. All touch input is handled here:
		int len = myTouchEvent.size();
		for (int i = 0; i < len; i++) {
			TouchEvent tempEvent = myTouchEvent.get(i);

			if (tempEvent.type == TouchEvent.TOUCH_DOWN) {

				if (tempEvent.x < 640) {
					// Move Left
				} else if (tempEvent.x > 640) {
					// Move Right
				}
			} 
			if (tempEvent.type == TouchEvent.TOUCH_UP){
				
				// TODO Stop Moving. Debug and find out if we need the next if loop
				if(tempEvent.x < 640){
					// Stop moving Left
				}else if(tempEvent.x > 640){
					// Stop moving Right					
				}
			}
		}
		
	       // 2. Check miscellaneous events like death:		
		if (lifeleft == 0){
			state = GameState.GameOver;
		}
		
        // 3. Call individual update() methods here.
        // This is where all the game updates happen.
        // For example, robot.update();		
	}

	private void updateReady(List<TouchEvent> myTouchEvent) {
		if (myTouchEvent.size() > 0)
			state = GameState.Running;
	}

	@Override
	public void paint(float deltaTime) {
        Graphics g = game.getGraphics();

        // First draw the game elements.

        // Example:
        // g.drawImage(Assets.background, 0, 0);
        // g.drawImage(Assets.character, characterX, characterY);

        // Secondly, draw the UI above the game elements.
        if (state == GameState.Ready)
            drawReadyUI();
        if (state == GameState.Running)
            drawRunningUI();
        if (state == GameState.Paused)
            drawPausedUI();
        if (state == GameState.GameOver)
            drawGameOverUI();

	}

	private void drawGameOverUI() {
        Graphics g = game.getGraphics();
        g.drawRect(0, 0, 1281, 801, Color.BLACK);
        g.drawString("GAME OVER", 640, 300, myPainter);
	}

	private void drawPausedUI() {
        Graphics g = game.getGraphics();
        // Darken the entire screen so you can display the Paused screen.
        g.drawARGB(155, 0, 0, 0);
	}

	private void drawRunningUI() {
		Graphics g = game.getGraphics();
		
	}

	private void drawReadyUI() {
        Graphics g = game.getGraphics();

        g.drawARGB(155, 0, 0, 0);
        g.drawString("Tap each side of the screen to move in that direction.",
                640, 300, myPainter);
		
	}

	private void GameRestart() {
		// Set all variables to null. You will be recreating them in the
        // constructor.
        myPainter = null;

        // Call garbage collector to clean up memory.
        System.gc();
	}
	
	@Override
	public void pause() {
		if(state == GameState.Running)
			state = GameState.Paused;
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
		pause();
	}

}
