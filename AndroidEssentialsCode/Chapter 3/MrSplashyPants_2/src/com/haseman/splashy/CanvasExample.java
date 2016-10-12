package com.haseman.splashy;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.UIThreadUtilities;
import android.view.View;

public class CanvasExample extends Activity
{
	CustomView vw = null;
	boolean running = true;
	boolean playSound = true;
	Bitmap ball = null;
	
	protected class CustomView extends View
	{
		Context ctx;
		Paint lPaint = new Paint();
		int x_1=0,y_1=0;
		MediaPlayer player = null;
		
		CustomView(Context c) 
		{
			super(c);
			player = MediaPlayer.create(c, R.raw.bounce);
			BitmapDrawable d = (BitmapDrawable) c.getResources().getDrawable(R.drawable.theball);
			ball = d.getBitmap();
			ctx = c;
		}
		
		protected void drawSprint(int x, int y, Canvas canvas)
		{
			canvas.drawBitmap(ball, x, y, lPaint);
		}

		public void onDraw(Canvas canvas)
		{ 
			Rect rct = new Rect();
			rct.set(0, 0, canvas.getBitmapWidth(), canvas.getBitmapHeight());
			
			Paint pnt = new Paint();
			pnt.setStyle(Paint.Style.FILL);
			pnt.setColor(Color.WHITE);
			
			canvas.drawRect(rct, pnt);
			
			x_1+=2;
			y_1+=2;
			
			if(x_1 >= canvas.getBitmapWidth())
			{
				x_1 = 0;
				y_1 = 0;
				player.stop();
				player.release();
				player = MediaPlayer.create(ctx, R.raw.bounce);
			}
			
			//Draw ball 1
			drawSprint(x_1, y_1, canvas);
			//Draw ball 2
			drawSprint(canvas.getBitmapWidth() - x_1, y_1, canvas);
			
			if(playSound && canvas.getBitmapWidth() - x_1 -16 == x_1 + 16)
			{
				player.start();
			}
			
			if(running)
				invalidate();
			
		}
	}
	
	public void onCreate(Bundle args)
	{
		super.onCreate(args);
		vw = new CustomView(this);
		setTitle("Bounce or Pass, sounds changes everything");
		setContentView(vw);
	}
	public boolean onKeyDown(int key, KeyEvent kc)
	{
		if(key == KeyEvent.KEYCODE_DPAD_CENTER)
		{
			playSound = !playSound;
			return true;
		}
		
		return false;
	} 
	public void onDestroy()
	{
		super.onDestroy();
		running = false;
	}
	public void onPause()
	{
		super.onPause();
		running = false;
	}
	public void onResume()
	{
		super.onResume();
		vw.invalidate();
	}
}
