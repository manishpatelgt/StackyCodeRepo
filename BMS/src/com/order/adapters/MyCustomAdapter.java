package com.order.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import com.order.bms.R;
import java.util.ArrayList;
import java.util.List;

import com.order.models.Stages;

/**
 * Created by Manish on 03/17/2015.
 */
public class MyCustomAdapter extends ArrayAdapter<Stages> {

    private List<Stages> coEngList;
    private Context context;
  
    public MyCustomAdapter(Context context, int textViewResourceId,
                           List<Stages> countryList) {
        super(context, textViewResourceId, countryList);
        this.coEngList=countryList;
        this.context=context;
      
    }

    private class ViewHolder {
        TextView name;
        CheckBox chk;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        //Log.v("ConvertView", String.valueOf(position));

        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.layout_stages, null);

            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.textTitle);
            holder.chk = (CheckBox) convertView.findViewById(R.id.chk);
            convertView.setTag(holder);

            holder.chk.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    int status;
                    CheckBox cb = (CheckBox) v;
                    Stages country = (Stages) cb.getTag();
                  
                    country.setSelected(cb.isChecked());
                }
            });
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Stages coEng = coEngList.get(position);
        holder.name.setText(coEng.getName());
        holder.chk.setChecked(coEng.isSelected());
        holder.chk.setTag(coEng);

        return convertView;
    }
}