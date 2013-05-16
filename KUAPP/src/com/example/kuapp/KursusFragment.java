package com.example.kuapp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class KursusFragment extends Fragment {

	public ArrayList<String> faculty, courses;
	private JSONArray json;
	
	private String chosenFaculty, chosenCourse;
	
	Spinner spinner1, spinner2;
	ArrayAdapter<String> adapter1, adapter2;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	// Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.kursusfragment, container, false);
        faculty = new ArrayList<String>();
        courses = new ArrayList<String>();
        faculty.add("Pick a faculty");
        courses.add("Pick a course");
        
        // Create spinner and adapter 1
        spinner1 = (Spinner) v.findViewById(R.id.planets_spinner);
    	adapter1 = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.spinner_item, faculty);
    	
    	// Apply the adapter to the spinner
    	adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	spinner1.setAdapter(adapter1);
    	spinner1.setOnItemSelectedListener(new myOnItemSelectedListenerOne());

    	// Create spinner and adapter 2
    	spinner2 = (Spinner) v.findViewById(R.id.country_spinner);
    	adapter2 = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.spinner_item, courses);
    	
    	// Apply the adapter to the spinner
    	adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	spinner2.setAdapter(adapter2);
    	
    	// Create do in background task
    	postData task = new postData();
    	task.execute("SELECT faculty FROM FACULTY", "faculty");
    	
    	return v;
    }
    
    
private class postData extends AsyncTask<String, Void, String> {
    	
    protected String doInBackground(String...strings) {
    	// Create a new HttpClient and Post Header
    	HttpClient httpClient = new DefaultHttpClient();
    	HttpPost httpPost = new HttpPost("http://karlashop.dk/conn.php");
    	
    	// This is the data to send
    	String localQuery = strings[0];
    	
    	try {
    		// Add data
    		StringEntity content = new StringEntity(localQuery);
    		httpPost.setEntity(content);
    		
    		// Execute HTTP Post   		
    		HttpResponse httpResponse = httpClient.execute(httpPost);
    		
    		// This is the response from the php application
    		BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"));
    		String queryResult = reader.readLine();
    		   		
    		// Instantiate a JSON array
    		json = new JSONArray(queryResult);
    		
    	} catch(Exception e) {
    		Log.e("info", Log.getStackTraceString(e));
    	}
		return strings[1];
    }
    
    protected void onPostExecute(String key) {
    	if ( key.equals("faculty") ) {
    		facultyToArray();
    	}
    	if ( key.equals("courses") ) {
    		coursesToArray();
    	}
    }
}
    
    public void facultyToArray() {
    	try {
    		for (int i = 0; i < json.length(); i++) {
    			String temp = json.getJSONObject(i).getString("faculty");
    			faculty.add(temp);
    		}
    	} catch(Exception e) {
    		Log.e("info1", Log.getStackTraceString(e));
    	}
    	adapter1.notifyDataSetChanged();
    	
    }
    
    public void coursesToArray() {
    	//query = "SELECT courses FROM COURSES, FACULTY WHERE faculty = " + chosenFaculty + " AND id_faculty = id_courses";
    	
    	try {
    		for (int i = 0; i < json.length(); i++) {
    			String temp = json.getJSONObject(i).getString("courses");
    			courses.add(temp);
    		}
    	} catch(Exception e) {
    		Log.e("info", Log.getStackTraceString(e));
    	}

    	
    }
    
    public class myOnItemSelectedListenerOne implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int pos,
				long id) {
			chosenFaculty = parent.getItemAtPosition(pos).toString();
			
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
		}	
    }

}
