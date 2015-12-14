package com.pszukala.colourcamera;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import static android.widget.AdapterView.*;

public class ShowColourByNameActivity extends Activity {

    private RGBtoColourName colours;
    private List<ColourWithName> colourList;
    private List<String> colourNamesList = new ArrayList<String>();
    private ColourWithName selectedColour;
    private ImageView rgbBox, boxFrame;
    private TextView rgbText;
    private Button findBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_colour_by_name);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        boxFrame = (ImageView) findViewById(R.id.imageFrame);
        boxFrame.setVisibility(View.INVISIBLE);

        rgbBox = (ImageView) findViewById(R.id.rgbBoxColourByName);
        rgbBox.setVisibility(View.INVISIBLE);

        findBtn = (Button) findViewById(R.id.findBtn);
        findBtn.setVisibility(View.INVISIBLE);

        rgbText = (TextView) findViewById(R.id.rgbTextColourByName);

        colours = new RGBtoColourName(this);
        colours.initColorList();
        colourList = colours.getColourList();

        for (ColourWithName col : colourList) {
            colourNamesList.add(col.Name);
        }

        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.colourAutoComplete);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.text_view_layout, colourNamesList);
        textView.setAdapter(adapter);
        textView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                boxFrame.setVisibility(View.VISIBLE);
                rgbBox.setVisibility(View.VISIBLE);
                findBtn.setVisibility(View.VISIBLE);
                String colour = (String) parent.getItemAtPosition(position);
                int pos = colourNamesList.indexOf(colour);
                selectedColour = colourList.get(pos);
                rgbBox.setBackgroundColor(Color.rgb(selectedColour.Red, selectedColour.Green, selectedColour.Blue));
                String text = "R: " + selectedColour.Red + "\nG: " + selectedColour.Green + "\nB: " + selectedColour.Blue;
                rgbText.setText(text);
            }
        });

        findBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                String objJson = gson.toJson(selectedColour);
                Intent intent = new Intent(v.getContext(), SearchColourOnViewActivity.class);
                intent.putExtra("selectedColourObj", objJson);
                startActivity(intent);
                finish();
            }
        });
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
}
