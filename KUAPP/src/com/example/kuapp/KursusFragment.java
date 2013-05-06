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
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class KursusFragment extends Fragment {
	
	private String query;
	private ArrayList<String> faculty = new ArrayList<String>();
	private ArrayList<String> courses = new ArrayList<String>(); 
	private JSONArray json;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	// Inflate the layout for this fragment
        this.facultyToArray();
        View v = inflater.inflate(R.layout.kursusfragment, container, false);
        
        Spinner spinner = (Spinner) v.findViewById(R.id.planets_spinner);
    	// Create an ArrayAdapter using the string array and a default spinner layout
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, faculty);
    	// Apply the adapter to the spinner
    	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	spinner.setAdapter(adapter);
    	
    	Spinner spinner2 = (Spinner) v.findViewById(R.id.country_spinner);
    	// Create an ArrayAdapter using the string array and a default spinner layout
    	ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
    	        R.array.country_array, R.layout.spinner_item);
    	// Apply the adapter to the spinner
    	spinner2.setAdapter(adapter2);
        
    	return v;
    }

    public void postData(String toPost) {
    	// Create a new HttpClient and Post Header
    	HttpClient httpClient = new DefaultHttpClient();
    	HttpPost httpPost = new HttpPost("http://www.karlashop.dk/conn.php");
    	
    	// This is the data to send
    	String localQuery = toPost;
    	
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
    		e.printStackTrace();
    	}
    }
    
    public void facultyToArray() {
    	query = "SELECT faculty FROM FACULTY";
    	this.postData(query);
    	
    	try {
    		for (int i = 0; i < json.length(); i++) {
    			faculty.add(json.getJSONObject(i).getString("faculty"));    	
    		}
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public void coursesToArray() {
    	try {
    		for (int i = 0; i < json.length(); i++) {
    			courses.add(json.getJSONObject(i).getString("courses"));
    		}
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    	
    }
}
