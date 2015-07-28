/**
 * 
 */
package akyDroid.gameFramework;

import akyDroid.gameFramework.implementation.GameImage;
import android.graphics.Paint;

/**
 * @author agl
 *
 */
public interface Graphics {
	public static enum ImageFormat {
		ARGB8888, ARGB4444, RGB565
	}

	public GameImage newImage(String fileName, ImageFormat format);

	public void clearScreen(int color);

	public void drawLine(int x, int y, int x2, int y2, int color);

	public void drawRect(int x, int y, int width, int height, int color);

	public void drawImage(GameImage image, int x, int y, int srcX, int srcY,
			int srcWidth, int srcHeight);

	void drawImage(GameImage Image, int x, int y);

	void drawString(String text, int x, int y, Paint paint);

	public int getWidth();

	public int getHeight();

	public void drawARGB(int i, int j, int k, int l);


}
