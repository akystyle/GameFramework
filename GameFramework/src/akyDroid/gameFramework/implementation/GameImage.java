package akyDroid.gameFramework.implementation;

import akyDroid.gameFramework.Graphics.ImageFormat;
import akyDroid.gameFramework.Image;
import android.graphics.Bitmap;

public class GameImage implements Image{

	Bitmap myBitmap;
	ImageFormat myFormat;
	
	public GameImage(Bitmap bitmap,ImageFormat format){
		this.myBitmap = bitmap;
		this.myFormat = format;
	}
	
	@Override
	public int getWidth() {
		return myBitmap.getWidth();
	}

	@Override
	public int getHeight() {
		return myBitmap.getHeight();
	}

	@Override
	public ImageFormat getFormat() {
		return myFormat;
	}

	@Override
	public void dispose() {
		myBitmap.recycle();
	}

}
