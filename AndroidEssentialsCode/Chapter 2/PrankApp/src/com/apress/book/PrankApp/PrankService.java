package com.apress.book.PrankApp;

import java.io.IOException;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Binder;
import android.media.MediaPlayer;

public class PrankService extends Service
{
	MediaPlayer player = null;
	String str;
	 public IBinder onBind(Intent intent) 
	 {
	    	return null;
	 }

	public void onStart(int startId, Bundle arguments)
	{
		super.onStart(startId, arguments);
		try
		{
			player = MediaPlayer.create((Context)this, R.raw.test);
			player.start();
		} catch (IOException e)
		{
			System.out.print("Exception while starting audio");
		}
	}
	public void onDestroy()
	{
		super.onDestroy();
		player.stop();
	}
	
}
