package com.example.kuapp;

import android.app.Activity;
import android.os.Bundle;

public class SetPreference extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getFragmentManager().beginTransaction().replace(android.R.id.content, new Preference()).commit();
	}

}
