/**
 * 
 */
package akyDroid.gameFramework;

import android.graphics.Paint;
import android.media.Image;

/**
 * @author agl
 *
 */
public abstract class Graphics {
	 public static enum ImageFormat {
	        ARGB8888, ARGB4444, RGB565
	    }

	    public abstract Image newImage(String fileName, ImageFormat format);

	    public abstract void clearScreen(int color);

	    public abstract void drawLine(int x, int y, int x2, int y2, int color);

	    public abstract void drawRect(int x, int y, int width, int height, int color);

	    public abstract void drawImage(Image image, int x, int y, int srcX, int srcY,
	            int srcWidth, int srcHeight);

	    public abstract void drawImage(Image Image, int x, int y);

	    abstract void drawString(String text, int x, int y, Paint paint);

	    public abstract int getWidth();

	    public abstract int getHeight();

	    public abstract void drawARGB(int i, int j, int k, int l);
}
