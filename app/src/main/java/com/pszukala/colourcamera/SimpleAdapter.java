package com.pszukala.colourcamera;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by pszukala on 2015-12-02.
 */
public class SimpleAdapter extends ArrayAdapter<ColourDbDTO> {

    private List<ColourDbDTO> itemList;
    private Context context;

    public SimpleAdapter(List<ColourDbDTO> itemList, Context ctx) {
        super(ctx, android.R.layout.simple_list_item_1, itemList);
        this.itemList = itemList;
        this.context = ctx;
    }

    public int getCount() {
        if (itemList != null)
            return itemList.size();
        return 0;
    }

    public ColourDbDTO getItem(int position) {
        if (itemList != null)
            return itemList.get(position);
        return null;
    }

    public long getItemId(int position) {
        if (itemList != null)
            return itemList.get(position).hashCode();
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_item, null);
        }

        ColourDbDTO c = itemList.get(position);
        TextView text = (TextView) v.findViewById(R.id.Row);
        text.setText(c.Name + "  " + c.Language);

        return v;

    }

    public List<ColourDbDTO> getItemList() {
        return itemList;
    }

    public void setItemList(List<ColourDbDTO> itemList) {
        this.itemList = itemList;
    }


}
