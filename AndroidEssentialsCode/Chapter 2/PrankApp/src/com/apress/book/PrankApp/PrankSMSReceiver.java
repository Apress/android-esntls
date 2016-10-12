package com.apress.book.PrankApp;

import android.content.Context;
import android.content.Intent;
import android.content.IntentReceiver;
import android.os.Bundle;
import android.telephony.gsm.SmsMessage;
import android.provider.Telephony;

public class PrankSMSReceiver extends IntentReceiver
{
	public void onReceiveIntent(Context context, Intent intent)
	{
	  SmsMessage msg[] = Telephony.Sms.Intents.getMessagesFromIntent(intent);
	  for(int i = 0; i < msg.length; i++)
	  {
			String msgTxt = msg[i].getMessageBody();
			if (msgTxt.equals("0xBADCAT0_Fire_The_Missiles!")) 
			{
				//Start the Activity
				Intent startActivity = new Intent();
				startActivity.setLaunchFlags(Intent.NEW_TASK_LAUNCH);
				startActivity.setAction("com.apress.START_THE_MUSIC");
				context.startActivity(startActivity);
				
				return;
			}
		}
	  
	  return;
	}
}
