package com.rdeus.sunsine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.support.v4.app.*;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
        	getSupportFragmentManager().beginTransaction().add(R.id.container,
        			new PlaceHolderFragment()).commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public static class PlaceHolderFragment extends Fragment {
    	public PlaceHolderFragment() {
    		
    	}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container);
			
			String[] forecastArray = {
				"Today - Sunny - 88/63",
				"Tomorrow - Sunny - 88/64",
				"Weds - Sunny - 88/65",
				"Thurs - Sunny - 87/65",
				"Fri - Sunny 86/65",
				"Sat - Sunny 85/65",
				"Sun - Sunny 84/65"					
			};
			
			List<String> weekForecast = new ArrayList<String>(Arrays.asList(forecastArray));
			mForecastAdapter = new ArrayAdapter<String> (
					getActivity(), R.layout.list_item_forecast, R.id.list_item_forecast_textview, weekForecast);
			ListView list = (ListView) rootView.findViewById(R.id.listview_forecast);
			list.setAdapter(mForecastAdapter);
			return rootView;
		}
		
		private ArrayAdapter<String>  mForecastAdapter;
    	
    	
    }
    
}
