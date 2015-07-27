package akyDroid.gameFramework.implementation;

import java.io.IOException;
import java.io.InputStream;

import akyDroid.gameFramework.Graphics;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.media.Image;

public class GameGraphics implements Graphics {
	AssetManager myAssets;
	Bitmap myFrameBuffer;
	Canvas myCanvas;
	Paint myPaint;
	Rect mySrcRect = new Rect();
	Rect myDstRect = new Rect();

	public GameGraphics(AssetManager assets, Bitmap frameBuffer) {
		this.myAssets = assets;
		this.myFrameBuffer = frameBuffer;
		this.myCanvas = new Canvas(frameBuffer);
		myPaint = new Paint();
	}

	@Override
	public Image newImage(String fileName, ImageFormat format) {
		Config myConfig = null;
		if (format == ImageFormat.RGB565)
			myConfig = Config.RGB_565;
		else if (format == ImageFormat.ARGB4444)
			myConfig = Config.ARGB_4444;
		else
			myConfig = Config.ARGB_8888;

		Options myOptions = new Options();
		myOptions.inPreferredConfig = myConfig;

		InputStream myInputStream = null;
		Bitmap myBitmap = null;
		try {
			myInputStream = myAssets.open(fileName);
			myBitmap = BitmapFactory.decodeStream(myInputStream, null,
					myOptions);
			if (myBitmap == null)
				throw new RuntimeException("Error loading Image: '" + fileName
						+ "'");
		} catch (IOException e) {
			throw new RuntimeException("Error loading Image: '" + fileName
					+ "'");
		} finally {
			try {
				if (myInputStream != null) {
					myInputStream.close();
				}
			} catch (IOException e) {

			}
		}

		if (myBitmap.getConfig() == Config.RGB_565)
			format = ImageFormat.RGB565;
		else if (myBitmap.getConfig() == Config.ARGB_4444)
			format = ImageFormat.ARGB4444;
		else
			format = ImageFormat.ARGB8888;

		return new GameImage(myBitmap, format);
	}

	@Override
	public void clearScreen(int color) {
		myCanvas.drawRGB((color & 0xff0000) >> 16, (color & 0xff00) >> 8,
				(color & 0xff));

	}

	@Override
	public void drawLine(int x, int y, int x2, int y2, int color) {
		myPaint.setColor(color);
		myCanvas.drawLine(x, y, x2, y2, myPaint);
	}

	@Override
	public void drawRect(int x, int y, int width, int height, int color) {
		myPaint.setColor(color);
		myPaint.setStyle(Style.FILL);
		myCanvas.drawRect(x, y, x + width - 1, y + height - 1, myPaint);
	}

	@Override
	public void drawARGB(int a, int r, int g, int b) {
		myPaint.setStyle(Style.FILL);
		myCanvas.drawARGB(a, r, g, b);
	}

	@Override
	public void drawString(String text, int x, int y, Paint paint) {
		myCanvas.drawText(text, x, y, paint);

	}

	public void drawImage(Image image, int x, int y, int srcX, int srcY,
			int srcWidth, int srcHeight) {
		mySrcRect.left = srcX;
		mySrcRect.top = srcY;
		mySrcRect.right = srcX + srcWidth;
		mySrcRect.bottom = srcY + srcHeight;

		myDstRect.left = x;
		myDstRect.top = y;
		myDstRect.right = x + srcWidth;
		myDstRect.bottom = y + srcHeight;

		myCanvas.drawBitmap(((GameImage) Image).bitmap, mySrcRect,
				myDstRect, null);
	}

	@Override
	public void drawImage(Image Image, int x, int y) {
		myCanvas.drawBitmap(((GameImage) Image).bitmap, x, y, null);
	}

	public void drawScaledImage(Image Image, int x, int y, int width,
			int height, int srcX, int srcY, int srcWidth, int srcHeight) {

		mySrcRect.left = srcX;
		mySrcRect.top = srcY;
		mySrcRect.right = srcX + srcWidth;
		mySrcRect.bottom = srcY + srcHeight;

		myDstRect.left = x;
		myDstRect.top = y;
		myDstRect.right = x + width;
		myDstRect.bottom = y + height;

		myCanvas.drawBitmap(((GameImage) Image).bitmap, mySrcRect,
				myDstRect, null);

	}

	@Override
	public int getWidth() {
		return myFrameBuffer.getWidth();
	}

	@Override
	public int getHeight() {
		return myFrameBuffer.getHeight();
	}

}
