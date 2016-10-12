package com.haseman.splashy;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.view.View.OnClickListener;

public class loginScreen extends Activity
{
	private Button btn;
	private OnClickListener buttonListener = new OnClickListener()
	{
		public void onClick(View v)
		{
			grabEnteredText();
		}
	};
	
	public void onCreate(Bundle args)
	{
		super.onCreate(args);
		setContentView(R.layout.login);
		
		btn = (Button) findViewById(R.id.loginbutton);
		btn.setOnClickListener(buttonListener);
	}
	public void onClick(View v)
	{
		if(v == btn)
			grabEnteredText();
	}
	public void grabEnteredText()
	{
		TextView status = (TextView) findViewById(R.id.status);
		EditText username = (EditText) findViewById(R.id.username);
		EditText pwd = (EditText) findViewById(R.id.password);
		
		String usrTxt = username.getText().toString();
		String pwdTxt = pwd.getText().toString();
		
		//Http transaction would spin up a new thread here
		status.setText("Login" + usrTxt + " : " + pwdTxt);
		
		this.showAlert("Login Data", 0, "Login" + usrTxt + " : " + pwdTxt, "ok!", false);
	}
	
}
