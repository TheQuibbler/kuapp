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

public class KursusFragment extends Fragment implements OnItemSelectedListener {
	
	private String query;
	private ArrayList<String> faculty = new ArrayList<String>();
	private String chosenFaculty;
	private ArrayList<String> courses = new ArrayList<String>(); 
	private JSONArray json;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	// Inflate the layout for this fragment
        this.facultyToArray();
        View v = inflater.inflate(R.layout.kursusfragment, container, false);
        
        Spinner spinner = (Spinner) v.findViewById(R.id.planets_spinner);
    	// Create an ArrayAdapter using the string array and a default spinner layout
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.spinner_item, faculty);
    	// Apply the adapter to the spinner
    	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	spinner.setAdapter(adapter);
    	spinner.setOnItemSelectedListener(this);
    	
    	Spinner spinner2 = (Spinner) v.findViewById(R.id.country_spinner);
    	// Create an ArrayAdapter using the string array and a default spinner layout
    	ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity().getApplicationContext(),
    	        R.layout.spinner_item, courses);
    	// Apply the adapter to the spinner
    	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	spinner2.setAdapter(adapter2);
        
    	return v;
    }
    
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		chosenFaculty = parent.getItemAtPosition(pos).toString();
		coursesToArray();
		
		
		
	}
    
    private class postData extends AsyncTask<String, Void, String> {
    	
    protected String doInBackground(String...strings) {
    	// Create a new HttpClient and Post Header
    	HttpClient httpClient = new DefaultHttpClient();
    	HttpPost httpPost = new HttpPost("http://www.karlashop.dk/conn.php");
    	
    	// This is the data to send
    	String localQuery = query;
    	
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
		return null;
    }
    }
    
    public void facultyToArray() {
    	query = "SELECT faculty FROM FACULTY";
    	postData task = new postData();
    	task.execute("hey");
    	
    	try {
    		for (int i = 0; i < json.length(); i++) {
    			String temp = json.getJSONObject(i).getString("faculty");
    			faculty.add(temp);
    		}
    	} catch(Exception e) {
    		Log.e("info", Log.getStackTraceString(e));
    	}
    }
    
    public void coursesToArray() {
    	query = "SELECT courses FROM COURSES, FACULTY WHERE faculty = " + chosenFaculty + " AND id_faculty = id_courses";
    	postData task = new postData();
    	task.execute("hey");
    	
    	try {
    		for (int i = 0; i < json.length(); i++) {
    			String temp = json.getJSONObject(i).getString("courses");
    			courses.add(temp);
    		}
    	} catch(Exception e) {
    		Log.e("info", Log.getStackTraceString(e));
    	}
    	
    }

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
}
