package com.order.adapters;

import java.util.ArrayList;

import com.order.models.OrderItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.order.bms.R;


public class AutoCompetAdapter extends ArrayAdapter<OrderItem> {
	
    private final String MY_DEBUG_TAG = "AutoCompetAdapter";
    private ArrayList<OrderItem> items;
    private ArrayList<OrderItem> itemsAll;
    private ArrayList<OrderItem> suggestions;
    private int viewResourceId;

    public AutoCompetAdapter(Context context, int viewResourceId, ArrayList<OrderItem> items) {
        super(context, viewResourceId, items);
        this.items = items;
        this.itemsAll = (ArrayList<OrderItem>) items.clone();
        this.suggestions = new ArrayList<OrderItem>();
        this.viewResourceId = viewResourceId;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(viewResourceId, null);
        }
        OrderItem customer = items.get(position);
        if (customer != null) {
            TextView customerNameLabel = (TextView) v.findViewById(R.id.CustomerName);
            if (customerNameLabel != null) {
//              Log.i(MY_DEBUG_TAG, "getView Customer Name:"+customer.getName());
                customerNameLabel.setText(customer.getItemName());
            }
        }
        return v;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    Filter nameFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            String str = ((OrderItem)(resultValue)).getItemName(); 
            return str;
        }
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if(constraint != null) {
                suggestions.clear();
                for (OrderItem customer : itemsAll) {
                    if(customer.getItemName().startsWith(constraint.toString())){
                        suggestions.add(customer);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<OrderItem> filteredList = (ArrayList<OrderItem>) results.values;
            if(results != null && results.count > 0) {
                clear();
                for (OrderItem c : filteredList) {
                    add(c);
                }
                notifyDataSetChanged();
            }
        }
    };

}