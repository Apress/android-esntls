package com.apress.ContentReceiverExample;

import java.util.HashMap;

import android.R.string;
import android.app.Activity;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;

public class crActivity extends Activity {
    /** Called when the activity is first created. */
	String exp;
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.main);
    }
    private boolean doesBookmarkExist(String url)
    {
    	Cursor results;
    	try{
    		String[] proj = new String[] 
    	    {
    		    android.provider.BaseColumns._ID,
    		    android.provider.Browser.BookmarkColumns.URL,
    		    android.provider.Browser.BookmarkColumns.TITLE
    		};
    		results = managedQuery(android.provider.Browser.BOOKMARKS_URI, proj, 
    			null, android.provider.Browser.BookmarkColumns.URL + " ASC");
    		int urlColumn = results.getColumnIndex(android.provider.Browser.BookmarkColumns.URL); 
        	results.first();
    		do
        	{
        		if(results.getString(urlColumn).equals(url))
        			return false;
        	} while(results.next());
    		
    	} catch (Exception e) 
    	{
    		System.out.print("Suck it Trabeck");
    	}
    	
    	
    	
    	Cursor bookmarks = android.provider.Browser.getAllBookmarks(getContentResolver());
    	int urlColumn = bookmarks.getColumnIndex(android.provider.Browser.BookmarkColumns.URL); 
    	bookmarks.first();
    	do
    	{
    		if(bookmarks.getString(urlColumn).equals(url))
    			return true;
    	} while(bookmarks.next());
    	return false;
    }
    
    private void addBookmark(String url, String title)
    {
    	ContentValues inputValues = new ContentValues();
		inputValues.put(android.provider.Browser.BookmarkColumns.BOOKMARK, "1");
    	inputValues.put(android.provider.Browser.BookmarkColumns.URL, url);
    	inputValues.put(android.provider.Browser.BookmarkColumns.TITLE, title);
    	inputValues.put(android.provider.Browser.BookmarkColumns.CREATED, "5/5/01");
    	//inputValues.put(android.provider.Browser.BookmarkColumns., value)
    	
    	ContentResolver cr = getContentResolver();
    	Uri uri = cr.insert(android.provider.Browser.BOOKMARKS_URI, inputValues);    
    	//android.provider.Browser.saveBookmark(this, title, url);
    }
    
    public boolean onKeyDown(int Keycode, KeyEvent k)
    {
    	if(doesBookmarkExist("http://www.apress.com/"))
    	{
    		
    	}
    	else
    	{
    		addBookmark("http://www.apress.com/", "Apress");
    	}
    	return true;
    }
}