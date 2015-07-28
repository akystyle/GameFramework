package akyDroid.gameStudio.friWreckringOris;

import akyDroid.gameFramework.Screen;
import akyDroid.gameFramework.implementation.GameMain;

public class FriWreckringOrisGame extends GameMain{

	@Override
	public Screen getInitScreen() {
		return new FriWreckringOrisLoadingScreen(this);
	}
	
	@Override
	public void onBackPressed() {
	getCurrentScreen().backButton();
	}
}
