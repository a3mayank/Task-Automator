package com.mayankattri.mc_project_2;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherActivity extends AppCompatActivity {

    static String weatherResult;
    static TextView tv;
    static EditText et;
    static String zip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv = (TextView) findViewById(R.id.TV_weather);
        et = (EditText) findViewById(R.id.ET_weather);
        Button b = (Button) findViewById(R.id.B_weather);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zip = et.getText().toString();
                SharedPreferences.Editor editor = getSharedPreferences("weather", MODE_PRIVATE).edit();
                editor.putString("zip", zip);
                editor.commit();
                Receiver weatherReceiver = new Receiver();
                weatherReceiver.setAlarmWeather(WeatherActivity.this);

                Toast.makeText(WeatherActivity.this, "Weather Notification Set for City Zip : "+zip
                , Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static class FetchWeatherTask extends AsyncTask<String, Void, String> {

        private final String LOG_TAG = FetchWeatherTask.class.getSimpleName();

        private String getWeatherDataFromJson(String forecastJsonStr)
                throws JSONException {

            JSONObject forecastJson = new JSONObject(forecastJsonStr);
            JSONArray weatherArray = forecastJson.getJSONArray("weather");

            JSONObject weatherObject = weatherArray.getJSONObject(0);
            String Main = weatherObject.getString("main");
            String Description = weatherObject.getString("description");

            JSONObject mainObject = forecastJson.getJSONObject("main");
            String Temp = mainObject.getString("temp");
            String Pressure = mainObject.getString("pressure");
            String Humidity = mainObject.getString("humidity");
            String Temp_min = mainObject.getString("temp_min");
            String Temp_max = mainObject.getString("temp_max");

            String resultStrs = "Weather : "+Description+"\n"+"Temperature : "+Temp+"\n"+
                    "Min : "+Temp_min+" "+"Max : "+Temp_max+"\n"+
                    "Humidity : "+Humidity+"\n"+"Pressure : "+Pressure;

            return resultStrs;

        }

        @Override
        protected String doInBackground(String... params) {
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String forecastJsonStr = null;

            try {
                String url1 = "http://api.openweathermap.org/data/2.5/weather?zip=";
                String url2 = ",ind&units=metric&appid=da1778a739f2afbb276629630692a056";
                String zip = params[0];
                final String urlstr = url1+zip+url2;
                URL url = new URL(urlstr);

                Log.v(LOG_TAG, "BuiltUri" + urlstr);

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

                Log.v(LOG_TAG,"Forecast JSON String: "+ forecastJsonStr);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attempting
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
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            try {
                return getWeatherDataFromJson(forecastJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if(result != null) {
                weatherResult = result;
                System.out.println(result);
            }
        }
    }

}
