package com.pszukala.colourcamera;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pszukala on 2015-11-09.
 */
public class RGBtoColourName {

    private List<ColourWithName> colorList = null;
    private Context ctx;
    SharedPreferences sharedpreferences;

    public RGBtoColourName(Context ctx){
        this.ctx = ctx;
        this.sharedpreferences = ctx.getSharedPreferences("MyPrefs", ctx.MODE_PRIVATE);
        colorList = initColorList();
    }

    private List<ColourWithName> initColorList() {

        Gson gson = new Gson();

        Type listType = new TypeToken<ArrayList<ColourWithName>>() {
        }.getType();

        String json = loadSharedPref();

        List<ColourWithName> colorList = new Gson().fromJson(json, listType);

        return colorList;
    }

    public String getColourNameFromRgb(int r, int g, int b) {
        ColourWithName closestMatch = null;
        int minMSE = Integer.MAX_VALUE;
        int mse;
        for (ColourWithName c : colorList) {
            mse = c.computeMSE(r, g, b);
            if (mse < minMSE) {
                minMSE = mse;
                closestMatch = c;
            }
        }

        if (closestMatch != null) {
            return closestMatch.getName();
        } else {
            return "No matched color name.";
        }
    }

    public String loadJSONFromResource() {
        String json = null;
        try {

            InputStream is = ctx.getResources().openRawResource(R.raw.colourlist);

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public void saveSharedPref(String value)
    {
        SharedPreferences.Editor preferencesEditor = sharedpreferences.edit();
        preferencesEditor.putString("colourList", value);
        preferencesEditor.commit();
    }

    public String loadSharedPref()
    {
        String sp = sharedpreferences.getString("colourList", "");
        if(!sp.isEmpty())
        {
            return sp;
        }
        else
        {
            saveSharedPref(loadJSONFromResource());
            return sharedpreferences.getString("colourList", "");
        }
    }
}
