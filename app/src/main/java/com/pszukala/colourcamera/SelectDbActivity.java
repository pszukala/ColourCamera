package com.pszukala.colourcamera;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SelectDbActivity extends Activity {

    private SimpleAdapter adpt;
    private RGBtoColourName rgbToName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_db);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        if(isOnline()){
            rgbToName = new RGBtoColourName(this);
            adpt  = new SimpleAdapter(new ArrayList<ColourDbDTO>(), this);
            ListView lView = (ListView) findViewById(R.id.list_view);

            lView.setAdapter(adpt);

            // Exec async load task
            (new AsyncListViewLoader()).execute("http://webapicolour.azurewebsites.net/api/getcolourdbs");

            lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ColourDbDTO colourDb = (ColourDbDTO) parent.getItemAtPosition(position);
                    (new AsyncColourDbLoader()).execute("http://webapicolour.azurewebsites.net/api/getcolourdbbyid/"+ colourDb.ColourDbID);
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent i = new Intent(this, MenuActivity.class);
                startActivity(i);
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, MenuActivity.class);
        startActivity(i);
        finish();
    }

    public boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
            showToast("No internet connection!", 3000);
            return false;
        }
        return true;
    }

    private void showToast (String text, int ms){
        final Toast toast = Toast.makeText(SelectDbActivity.this, text, Toast.LENGTH_SHORT);
        toast.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, ms);
    }

    private class AsyncListViewLoader extends AsyncTask<String, Void, List<ColourDbDTO>> {
        private final ProgressDialog dialog = new ProgressDialog(SelectDbActivity.this);

        @Override
        protected void onPostExecute(List<ColourDbDTO> result) {
            super.onPostExecute(result);
            dialog.dismiss();
            adpt.setItemList(result);
            adpt.notifyDataSetChanged();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Loading data...");
            dialog.show();
        }

        @Override
        protected List<ColourDbDTO> doInBackground(String... params) {

            try {
                URL u = new URL(params[0]);

                HttpURLConnection conn = (HttpURLConnection) u.openConnection();
                conn.setRequestMethod("GET");

                conn.connect();
                InputStream is = conn.getInputStream();

                Reader reader = null;
                try {
                    reader = new InputStreamReader(is);
                } catch (Exception e) {
                    return null;
                }
                Gson gson = new Gson();

                Type listType = new TypeToken<ArrayList<ColourDbDTO>>() {
                }.getType();

                List<ColourDbDTO> result = gson.fromJson(reader, listType);

                return result;
            }
            catch(Throwable t) {
                t.printStackTrace();
            }
            return null;
        }

    }

    private class AsyncColourDbLoader extends AsyncTask<String, Void, Integer> {
        private final ProgressDialog dialog = new ProgressDialog(SelectDbActivity.this);

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            dialog.dismiss();
            showToast("Success!", 3000);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Loading new database...");
            dialog.show();
        }

        @Override
        protected Integer doInBackground(String... params) {

            try {
                URL u = new URL(params[0]);

                HttpURLConnection conn = (HttpURLConnection) u.openConnection();
                conn.setRequestMethod("GET");

                conn.connect();
                InputStream is = conn.getInputStream();

                Reader reader = null;
                try {
                    reader = new InputStreamReader(is);
                } catch (Exception e) {
                    return null;
                }
                Gson gson = new Gson();

                Type listType = new TypeToken<ArrayList<ColourWithName>>() {
                }.getType();

                List<ColourWithName> json = gson.fromJson(reader, listType);
                rgbToName.saveSharedPref(gson.toJson(json));

                return 1;
            }
            catch(Throwable t) {
                t.printStackTrace();
            }
            return 0;
        }

    }
}
