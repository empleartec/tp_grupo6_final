package com.techblogon.activityresult;

import com.example.activityresult.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity 
{
    TextView  textViewMessage;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// get The reference of The textView 
		textViewMessage=(TextView)findViewById(R.id.textViewMessage);
	}
	// Method to handle the Click Event on GetMessage Button
	public void getMessage(View V)
	{
	    // Create The  Intent and Start The Activity to get The message
	    Intent intentGetMessage=new Intent(this,SecondActivity.class);
	    startActivityForResult(intentGetMessage, 2);// Activity is started with requestCode 2
	}
	// Call Back method  to get the Message form other Activity
	@Override
	   protected void onActivityResult(int requestCode, int resultCode, Intent data)
	   {
   	      super.onActivityResult(requestCode, resultCode, data);
   	      
   	       // check if the request code is same as what is passed  here it is 2
             if(requestCode==2)
   	         {
            	 if(null!=data)
            	 {	 	
            		 // fetch the message String
            		 String message=data.getStringExtra("MESSAGE"); 
            		 // Set the message string in textView
            		 textViewMessage.setText("Message from second Activity: " + message);
            	 }
   	         }
     }
}
