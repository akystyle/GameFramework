package akyDroid.gameStudio.GenericGame;

import akyDroid.gameFramework.*;
import akyDroid.gameFramework.Graphics.ImageFormat;

public class LoadingScreen extends Screen{

	public LoadingScreen(Game game) {
		super(game);
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		Graphics myGraphics = game.getGraphics();
		Assets.myMenu = myGraphics.newImage("menu.jpg", ImageFormat.RGB565);
		Assets.myClickSound = game.getAudio().createSound("click.ogg");
		
		game.setScreen(new MainMenuScreen(game));
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
