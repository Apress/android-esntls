package com.apress.mapexample;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayController;
import com.google.android.maps.Point;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentReceiver;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ImageView;

public class MapExampleActivity extends MapActivity {
	MapView m_mapView;
	MapController m_mapController;
	Point m_curLocation;
	LocationProvider m_locationProvider;
	LocationManager m_locationMgr;
	OverlayController m_overlayController;
	boolean m_locationLoopActive = false;
	
	class TackOverlay extends Overlay
	{
		MapExampleActivity ctx;
		Bitmap tack;
		
		TackOverlay(MapExampleActivity c)
		{
			super();
			BitmapDrawable b = (BitmapDrawable) c.getResources().getDrawable(R.drawable.tack);
			tack = b.getBitmap();
		}
		public void draw(Canvas canvas, PixelCalculator calculator, boolean shadow)
		{
			super.draw(canvas, calculator, shadow);
			int xy[] = new int[2];
			
			try{
			//Convert the center point to an XY coordanate.
			//We could hard-code this, but where's the fun in that?
			
			if(m_curLocation == null)
				return;
			
			
			calculator.getPointXY(m_curLocation, xy);
			
			int tackX = xy[0] - (tack.getWidth()/2);
			int tackY = xy[1] - (tack.getHeight());
			//Always draw the tack in the center of the screen.
			canvas.drawBitmap(tack ,tackX, tackY, new Paint());
			}catch (Exception e)
			{
				Log.e("Tack","Exception!");
			}
			
		}
	}
	
	public void onCreate(Bundle ice)
	{
		super.onCreate(ice);
		m_mapView = new MapView(this);
		m_mapController = m_mapView.getController();
		m_overlayController = m_mapView.createOverlayController();
		m_overlayController.add(new TackOverlay(this), true);
		m_mapController.zoomTo(9);
		buildGPS();
		
		setContentView(m_mapView);
	}
	protected void doHandleMessage(Message message)
	{
		super.doHandleMessage(message);
	}
	private void buildGPS()
	{		
		List<LocationProvider> providorList = null;
		Criteria c = new Criteria();

		c.setAccuracy(50);
		c.setAltitudeRequired(false);
		c.setCostAllowed(false);
		c.setSpeedRequired(false);
		//c.setPowerRequirement(Criteria.POWER_LOW);
		
		m_locationMgr = (LocationManager) getSystemService(LOCATION_SERVICE);
		
		m_locationProvider = m_locationMgr.getBestProvider(c);
	 	
		if(m_locationProvider != null)
			return;

		providorList = m_locationMgr.getProviders();
 
		if(providorList.size() > 0)
			m_locationProvider = providorList.get(0);
	}
	
	class LocationUpdater extends IntentReceiver
	{
		public void onReceiveIntent(Context context, Intent intent) 
		{
			Location here;

			if (m_locationProvider == null)
				here = m_locationMgr.getCurrentLocation("gps");
			else
				here = m_locationMgr
						.getCurrentLocation(m_locationProvider
								.getName());
			
			setMapLocationCenter(here.getLatitude(), here.getLongitude());
		}
	};
	private void startLocationThread()
	{
		try
		{
			LocationUpdater l = new LocationUpdater();
			registerReceiver(l, new IntentFilter("GPS_UPDATE"));
			
			m_locationMgr.requestUpdates(m_locationProvider, 5000, 50, new Intent("GPS_UPDATE"));
			
			
		} catch (Exception e)
		{
			Log.e("Toast", "WithJam");
		}
			
	}
	public boolean onKeyDown(int KeyCode, KeyEvent evt)
	{
		super.onKeyDown(KeyCode, evt);
		
		switch(KeyCode)
		{
		case KeyEvent.KEYCODE_DPAD_CENTER:
			if(isLocationAvailable())
				m_mapController.setFollowMyLocation(true);
			else
			{
				if(!m_locationLoopActive)
				{
					m_locationLoopActive = true;
					startLocationThread();
				}
			}
			break;
		}
		
		return true;
	}
	public void setMapLocationCenter(double lat, double lon)
	{
		
		m_curLocation = new Point((int)(lat * 1E6),
				(int) (lon * 1E6));
		
		m_mapController.animateTo(m_curLocation);
		//m_mapController.centerMapTo(m_curLocation, false);
		
	}
	public void setMapLocationSecond(double lat, double lon)
	{
		
	}
}