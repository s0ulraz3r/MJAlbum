package com.example.kvaru.mjalbum;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.kvaru.mjalbum.datamodels.MainDataModelClass;
import com.example.kvaru.mjalbum.datamodels.Result;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public ListView listView;
    public CustomAdapterClass mAdapter;
    public Context context;
    public List<Result> results;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.AlbumListView);
        new JSONData().execute("https://itunes.apple.com/search?term=Michael+jackson");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Result resultObj = (Result) parent.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), DisplayActivity.class);
                intent.putExtra("ArtistName",resultObj.getArtistName());
                intent.putExtra("Country", resultObj.getCountry());
                intent.putExtra("Currency", resultObj.getCurrency());
                intent.putExtra("PreviewURL", resultObj.getPreviewUrl());
                intent.putExtra("TrackPrice",resultObj.getTrackPrice());
                intent.putExtra("ReleaseDate", resultObj.getReleaseDate());
                intent.putExtra("PrimaryGenreName", resultObj.getPrimaryGenreName());
                intent.putExtra("TrackTime",resultObj.getTrackTimeMillis());
                intent.putExtra("TrackName",resultObj.getTrackName());
                startActivity(intent);

            }
        });


    }


   public class JSONData extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection httpURLConnection = null;
            try {
                URL url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();
                InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer stringBuffer = new StringBuffer();
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line);
                }
                Log.d("onCreate: ", stringBuffer.toString());
                return stringBuffer.toString();
            } catch (Exception e) {
                Log.e("doInBackground: " ,  e.getMessage());
            }finally {
                if ( httpURLConnection!= null) {
                    httpURLConnection.disconnect();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            results = new ArrayList<>();
            try {
                JSONObject jsonObject = new JSONObject(s);
                System.out.println("Result: " + jsonObject.get("results"));

                JSONArray jsonArray = jsonObject.getJSONArray("results");
                for (int i = 0; i < jsonArray.length(); i++) {

                    results.add(new Result(jsonArray.getJSONObject(i).getString("artistName"),
                            jsonArray.getJSONObject(i).getString("trackName"),
                            jsonArray.getJSONObject(i).getString("previewUrl"),
                            jsonArray.getJSONObject(i).getString("artworkUrl100"),
                            jsonArray.getJSONObject(i).getDouble("trackPrice"),
                            jsonArray.getJSONObject(i).getString("releaseDate"),
                            jsonArray.getJSONObject(i).getInt("trackTimeMillis"),
                            jsonArray.getJSONObject(i).getString("country"),
                            jsonArray.getJSONObject(i).getString("currency"),
                            jsonArray.getJSONObject(i).getString("primaryGenreName")));

                }
                Log.d("Obj", String.valueOf(results.size()));
                mAdapter = new CustomAdapterClass(getApplicationContext(),R.layout.activity_listitem,results);
                listView.setAdapter(mAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}