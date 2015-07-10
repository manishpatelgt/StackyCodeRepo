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
import com.order.models.CustomItem;
import com.order.models.Customer;

/**
 * Custom adapter used in the UiLanguage spinner
 */
public class CustomItemAdapter extends ArrayAdapter<CustomItem> {
    private List<CustomItem> items;
    private int resourceId;
    private Context context;
    private LayoutInflater inflater;

    public CustomItemAdapter(Context context,  int resource,List<CustomItem> items) {
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
    public CustomItem getItem(int position) {
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
            holder.textItemName = (TextView) row.findViewById(R.id.textItemName);
            holder.textItemPrice = (TextView) row.findViewById(R.id.textItemPrice);
            row.setTag(holder);
        } else
            // When convertView is not null, we can reuse it directly, there is no need to re-inflate it.
            // We only inflate a new View when the convertView supplied by ListView is null.
            holder = (ViewHolder) row.getTag();

        CustomItem item = getItem(position);
        try{

            holder.textItemName.setText(item.getItemName());
            holder.textItemPrice.setText("Quantity: "+item.getItemQuantity()+" Amount: "+item.getItemAmount()+ " Packs: "+item.getItemPack());
        }catch(Exception e){
            e.printStackTrace();
        }
        return row;
    }

    public void refresh(List<CustomItem> items)
    {
        this.items = items;
        notifyDataSetChanged();
    } 
    
    /**
     * Internal class for the View Holder pattern (for smoother scrolling).
     */
    private class ViewHolder {
        TextView textItemName,textItemPrice;
    }

}
