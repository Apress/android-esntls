package com.apress.book.PrankApp;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.view.KeyEvent;

public class PrankActivity extends Activity
{
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle) 
	{
		super.onCreate(icicle);
		Intent i = null;
		i = getIntent();

		String action = i.getAction();

		if (action != null && action.equals("com.apress.START_THE_MUSIC")) 
		{
			setContentView(R.layout.pranked);
			startService(new Intent("com.apress.START_AUDIO_SERVICE"), null);
		}
		else
			finish();
	}
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		stopService(new Intent("com.apress.START_AUDIO_SERVICE"));
		return true;
	}
}
