package com.rdeus.sunsine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ForecastFragment extends Fragment {
	public ForecastFragment() {
		
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
	
	private static String fetchForecastJson(String uri) {
	
		// These two need to be declared outside the try/catch
		// so that they can be closed in the finally block.
		HttpURLConnection urlConnection = null;
		BufferedReader reader = null;
		 
		// Will contain the raw JSON response as a string.
		String forecastJsonStr = null;
		 
		try {
		    // Construct the URL for the OpenWeatherMap query
		    // Possible parameters are avaiable at OWM's forecast API page, at
		    // http://openweathermap.org/API#forecast
			// "http://api.openweathermap.org/data/2.5/forecast/daily?q=94043&mode=json&units=metric&cnt=7"
		    URL url = new URL(uri);
		 
		    // Create the request to OpenWeatherMap, and open the connection
		    urlConnection = (HttpURLConnection) url.openConnection();
		    urlConnection.setRequestMethod("GET");
		    urlConnection.connect();
		 
		    // Read the input stream into a String
		    InputStream inputStream = urlConnection.getInputStream();
		    StringBuffer buffer = new StringBuffer();
		    if (inputStream == null) {
		        // Nothing to do.
		        forecastJsonStr = null;
		    }
		    reader = new BufferedReader(new InputStreamReader(inputStream));
		 
		    String line;
		    while ((line = reader.readLine()) != null) {
		        // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
		        // But it does make debugging a *lot* easier if you print out the completed
		        // buffer for debugging.
		        buffer.append(line + "\n");
		    }

		    if (buffer.length() == 0) {
		        // Stream was empty.  No point in parsing.
		        forecastJsonStr = null;
		    }
		    forecastJsonStr = buffer.toString();
		} catch (IOException e) {
		    Log.e("PlaceholderFragment", "Error ", e);
		    // If the code didn't successfully get the weather data, there's no point in attemping
		    // to parse it.
		    forecastJsonStr = null;
		} finally{
		    if (urlConnection != null) {
		        urlConnection.disconnect();
		    }
		    if (reader != null) {
		        try {
		            reader.close();
		        } catch (final IOException e) {
		            Log.e("PlaceholderFragment", "Error closing stream", e);
		        }
		    }
		}
		return forecastJsonStr;
	}
	
	public static class FetchWeatherTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... args) {
			return fetchForecastJson("http://api.openweathermap.org/data/2.5/forecast/daily?q=94043&mode=json&units=metric&cnt=7");
		}
		
	}
}

