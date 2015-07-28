package akyDroid.gameStudio.friWreckringOris;

import akyDroid.gameFramework.Screen;
import akyDroid.gameFramework.implementation.GameMain;

public class friWreckringOrisGame extends GameMain{

	@Override
	public Screen getInitScreen() {
		return new LoadingScreen(this);
	}
	
}
