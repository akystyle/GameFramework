package akyDroid.gameStudio.GenericGame;

import akyDroid.gameFramework.Screen;
import akyDroid.gameFramework.implementation.GameMain;

public class Game extends GameMain{

	@Override
	public Screen getInitScreen() {
		return new LoadingScreen(this);
	}
	
	@Override
	public void onBackPressed() {
	getCurrentScreen().backButton();
	}
}
