package com.techblogon.activityresult;

import com.example.activityresult.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SecondActivity extends Activity 
{
    EditText  editTextMessage;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout2);
		// Get the reference of Edit Text
		editTextMessage=(EditText)findViewById(R.id.editTextMessage);
	}
	public void submitMessage(View V)
	{
		// get the Entered  message
		String message=editTextMessage.getText().toString();
		Intent intentMessage=new Intent();
		
		// put the message in Intent
		intentMessage.putExtra("MESSAGE",message);
		// Set The Result in Intent
		setResult(2,intentMessage);
		// finish The activity 
        finish();
	}
}