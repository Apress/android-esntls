package com.apress.radio;

import java.io.InputStream;
import java.util.List;
import java.util.Vector;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.recognition.Logger;
import android.util.Log;
import android.view.UIThreadUtilities;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;



public class StationPicker extends ListActivity {

	Vector<StationData> stationListVector = new Vector<StationData>();
	SAXParser parser = null;
	XMLReader reader = null;
	XMLHandler handler = new XMLHandler();
	ArrayAdapter<StationData> adapter = null;
	Activity ctx = this;
	
	
	class XMLHandler implements org.xml.sax.ContentHandler
	{
		StationData currentStation;
		String lastName;
		
		public void endDocument(){}
	 	public void   	endPrefixMapping(String prefix){}
	 	public void   	ignorableWhitespace(char[] ch, int start, int length){}
	 	public void processingInstruction(String s, String a){}
	 	public void setDocumentLocator(Locator l){}
	 	public void   	skippedEntity(String name){}
	 	public void   	startDocument(){}
	 	public void   	startPrefixMapping(String prefix, String s){}
		 public void characters(char[] ch, int start, int length)
		 {
			 if(lastName.equals("title") && currentStation.title.length() == 0)
			 {
				 currentStation.title = String.copyValueOf(ch, start, length);
			 }
			 else if(lastName.equals("audioUrl") && currentStation.url.length() == 0)
			 {
				 currentStation.url = String.copyValueOf(ch, start, length);
			 }
		 }
		public void startElement(String uri, String localName, String qName, Attributes atts)
		{
			lastName = localName;
			if(localName.equals("station"))
			{
				currentStation = new StationData();
			}
		}
		
		 public void endElement(String uri, String localName, String qName)
		 {
			 if(localName.equals("station"))
				{
				 if(currentStation != null)
					 stationListVector.add(currentStation);
				}
		 }
	}
	
	
	class StationData
	{
		public String title = "";
		public String url = "";
		
		public String toString()
		{
			return title;
		}
	}
	
	private void initList()
	{
		adapter = new ArrayAdapter<StationData>(StationPicker.this, R.layout.list_element, R.id.textElement); 
    	setListAdapter(adapter);
	}
	
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        try
        {
        	setContentView(R.layout.main);
        	SAXParserFactory f = SAXParserFactory.newInstance();
        	parser = f.newSAXParser();
        	reader = parser.getXMLReader();
        	reader.setContentHandler(handler);
        	initList();
        } catch (Exception e)
        {
        	Log.e("StationPicker", "Parser FAIL!");
        }
    }

    protected void onListItemClick(ListView l, View v, int position, long id)
    {
    	StationData selectedStation = stationListVector.elementAt(position);
    	MediaPlayer player = new MediaPlayer();
    	try
    	{
    		player.setDataSource(selectedStation.url);
    		player.start();
    	}
    	catch (Exception e)
    	{
    		Log.e("PlayerException", "SetData");
    	}
    }
    
    public void onStart()
    {
    	super.onStart();
    	
    	Thread t = new Thread() 
    	{
    		public void run()
    		{
	    		HttpUriRequest request = null;
	        	HttpResponse resp = null;
	        	InputStream is = null;
	 
	        	DefaultHttpClient client = new DefaultHttpClient();
	        	
		    	try{
		    		//Build the request
		    		request = new HttpGet("http://www.wanderingoak.net/stations.xml");
		    		//Execute it using the default HTTP Client settings;
		    		resp = client.execute(request);
		    		//Pull out the entity
		         	HttpEntity entity= resp.getEntity();
		         	//Snag the response stream from the entity
		         	is = entity.getContent();
		         	//Initalize a new XML  parser
		         	reader.parse(new InputSource(is));

		         	
		         	Runnable r = new Runnable()
		         	{
		         		public void run()
		         		{
		         			TextView t = (TextView) findViewById(R.id.loadingStatus);
		         			t.setText("Stations Loaded");
		         			
		         			try{
			         			for(int i=0; i < stationListVector.size(); i++)
			         				adapter.addObject(stationListVector.elementAt(i));
		         			}catch (Exception e)
		         			{
		         				Log.e("Fail", "Superfail");
		         			}
		         			getListView().invalidate();
		         		}
		         	};
		         	
			    	UIThreadUtilities.runOnUIThread(StationPicker.this,r);
		         	
		     	}
		    	catch (Exception e)
		    	{
		    		Log.e("Stations()", "Exception", e);
		    	}
    		}
    	};
    	t.start();

    }
}