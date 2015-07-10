
package com.order.adapters;


import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.order.bms.R;
import com.order.models.ColorCode;
import com.order.models.Customer;

/**
 * Custom adapter used in the UiLanguage spinner
 */
public class ColorCodeAdapter extends ArrayAdapter<ColorCode> {

    private List<ColorCode> items;
    private Context context;

    public ColorCodeAdapter(Context context, int resourceId, List<ColorCode> items) {
        super(context, resourceId, items);

        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }
    
    @Override
    public ColorCode getItem(int position) {
        if (items == null)
            return null;
       // return items.get(position);
        return items.get(position);
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.spinner_item_color_code, parent, false);

        TextView cName = (TextView) row.findViewById(R.id.colorCodeName);
        ColorCode item = getItem(position);
        cName.setText(item.getColourcode());
        return row;
    }
}
