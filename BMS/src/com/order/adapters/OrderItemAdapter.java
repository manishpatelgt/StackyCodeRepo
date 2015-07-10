package com.order.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.order.bms.R;
import com.order.models.Customer;
import com.order.models.OrderItem;

public class OrderItemAdapter extends ArrayAdapter<OrderItem> {

    private List<OrderItem> items;
    private Context context;

    public OrderItemAdapter(Context context, int resourceId, List<OrderItem> items) {
        super(context, resourceId, items);

        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }
    
    @Override
    public OrderItem getItem(int position) {
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
        View row = inflater.inflate(R.layout.spinner_item_customer_role, parent, false);

        TextView cName = (TextView) row.findViewById(R.id.CustomerName);
        OrderItem item = getItem(position);
        cName.setText(item.getItemName());
        return row;
    }
}
