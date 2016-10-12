package com.haseman.splashy;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout.Alignment;
import android.view.Menu;
import android.view.View;
import android.view.View.*;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;



public class MainMenu extends Activity
{
	TextView status; 
	public static final int IdOne = 1;
	public static final int IdTwo = 2;
	public static final int IdThree = 3;
	
	OnFocusChangeListener focusListener = new OnFocusChangeListener()
	{		
		public void onFocusChanged(View v, boolean hasFocus)
		{
			adjustTextColor(v, hasFocus);
		}
	};
	
	OnClickListener clickListener = new OnClickListener()
	{
		public void onClick(View v)
		{
			String text = "You selected Item: ";
			
			switch(v.getId())
			{
			case IdOne:
				text += "1";
				startActivity(new Intent("com.apress.example.LOGIN"));
				break;
			case IdTwo:
				text += "2";
				startActivity(new Intent("com.apress.example.CUSTOM_VIEW"));
				break;
			case IdThree:
				text += "3";
				break;
			}
			status.setText(text);
		}
	};
	
	private void adjustTextColor(View v, boolean hasFocus)
	{
		TextView t = (TextView)v;
		if(hasFocus)
			t.setTextColor(Color.RED);
		else
			t.setTextColor(Color.WHITE);
	}
	
	
	public void onCreate(Bundle bun)
	{
		super.onCreate(bun);
		
		this.setTitle("Socially Awkward");
		status = new TextView(this);
		//Create layout object.
		LinearLayout layout = new LinearLayout(this);
		layout.setBackground(R.drawable.general_bg);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setLayoutParams(
				new LayoutParams(LayoutParams.FILL_PARENT, 
						LayoutParams.FILL_PARENT));
		
		
		TextView title = new TextView(this);
		title.setText(R.string.man_menu_title);
		title.setLayoutParams(
				new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 
						LayoutParams.WRAP_CONTENT));
		title.setAlignment(Alignment.ALIGN_CENTER);
		
		layout.addView(title);
		
		
		TextView ItemOne = new TextView(this);
		ItemOne.setFocusable(true);
		ItemOne.setText("Login Screen");
		ItemOne.setTextColor(Color.WHITE);
		ItemOne.setLayoutParams(
				new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 
						LayoutParams.WRAP_CONTENT));
		
		ItemOne.setOnFocusChangeListener(focusListener);
		ItemOne.setOnClickListener(clickListener);
		ItemOne.setId(IdOne);
		layout.addView(ItemOne);
		
		TextView ItemTwo = new TextView(this);
		ItemTwo.setFocusable(true);
		ItemTwo.setText("Custom View Demo");
		ItemTwo.setTextColor(Color.WHITE);
		ItemTwo.setLayoutParams(
				new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 
						LayoutParams.WRAP_CONTENT));
		
		ItemTwo.setOnFocusChangeListener(focusListener);
		ItemTwo.setOnClickListener(clickListener);
		ItemTwo.setId(IdTwo);
		layout.addView(ItemTwo);
		
		TextView ItemThree = new TextView(this);
		ItemThree.setFocusable(true);
		ItemThree.setTextColor(Color.WHITE);
		ItemThree.setText("Menu Item Three");
		ItemThree.setLayoutParams(
				new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 
						LayoutParams.WRAP_CONTENT));
		
		ItemThree.setOnFocusChangeListener(focusListener);
		ItemThree.setOnClickListener(clickListener);
		ItemThree.setId(IdThree);
		layout.addView(ItemThree);
		
		status.setLayoutParams(
				new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 
						LayoutParams.WRAP_CONTENT));
		status.setAlignment(Alignment.ALIGN_CENTER);
		
		layout.addView(status);

		setContentView(layout);
		
	}
}
