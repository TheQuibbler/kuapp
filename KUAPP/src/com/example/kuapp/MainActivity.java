package com.example.kuapp;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;

public class MainActivity extends Activity {
	private static final int MENU_SETTINGS = 0;
	public static Context appContext;

	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        
        ActionBar actionbar = getActionBar();
        actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
        Tab SkemaTab = actionbar.newTab().setText("Skema");
        ActionBar.Tab KursusTab = actionbar.newTab().setText("Kursus");
        
        Fragment SkemaFrag = new SkemaFragment();
        Fragment KursusFrag = new KursusFragment();
        
        SkemaTab.setTabListener(new MyTabsListener(SkemaFrag));
        KursusTab.setTabListener(new MyTabsListener(KursusFrag));
        
        actionbar.addTab(SkemaTab);
        actionbar.addTab(KursusTab);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, MENU_SETTINGS, 0, "Settings");
        return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    	case MENU_SETTINGS:
    		startActivity(new Intent(this, SetPreference.class));
    	break;    	
    	}
		return true;
    }


    
}
