package com.order.adapters;


import java.text.SimpleDateFormat;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.order.bms.R;
import com.order.models.CustomItem;
import com.order.models.Customer;
import com.order.models.Order;

public class OrderAdapter extends ArrayAdapter<Order> {
    private List<Order> items;
    private int resourceId;
    private Context context;
    private LayoutInflater inflater;

    public OrderAdapter(Context context,  int resource,List<Order> items) {
        super(context,resource, items);
        this.context = context;
        this.items = items;
        this.resourceId = resource;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Order getItem(int position) {
        if (items == null)
            return null;
        // return items.get(position);
        return items.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Reference to children views to avoid unnecessary calls to findViewById() on each row
        ViewHolder holder;
        View row = null;
        convertView = null;
        row = convertView;

        if (convertView == null) {
            row = inflater.inflate(resourceId, parent, false);

            // Create a ViewHolder to store reference to the voucher payment
            holder = new ViewHolder();
            holder.textOrderNo = (TextView) row.findViewById(R.id.textOrderNo);
            holder.amountText = (TextView) row.findViewById(R.id.amountText);
            holder.textcustomerName = (TextView) row.findViewById(R.id.customerName);
            row.setTag(holder);
        } else
            // When convertView is not null, we can reuse it directly, there is no need to re-inflate it.
            // We only inflate a new View when the convertView supplied by ListView is null.
            holder = (ViewHolder) row.getTag();

        Order item = getItem(position);
        try{

            holder.textOrderNo.setText("Order No: "+item.getOrderNumber());
            
            SimpleDateFormat formatLong = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
            holder.textcustomerName.setText("Created by: "+item.getCreatedBy()+" Customer: "+item.getCustomerName());
            //holder.textcustomerName.setText("Created by: "+item.getCreatedBy()+" DateTime: "+ formatLong.parse(item.getCreatedDateTime()));
            holder.amountText.setText("Amount: "+item.getOrderAmount()+" Remark: "+item.getRemark());
        }catch(Exception e){
            e.printStackTrace();
        }
        return row;
    }

    public void refresh(List<Order> items)
    {
        this.items = items;
        notifyDataSetChanged();
    } 
    
    /**
     * Internal class for the View Holder pattern (for smoother scrolling).
     */
    private class ViewHolder {
        TextView textOrderNo,textcustomerName,amountText;
    }

}
