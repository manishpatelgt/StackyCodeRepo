package com.order.adapters;

import java.util.ArrayList;
import java.util.List;

import com.order.models.OrderItem;
import com.order.models.TaskNumber;
import com.order.bms.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

public class AutoCompetAdapter3 extends ArrayAdapter<TaskNumber> {
	private LayoutInflater layoutInflater;
	List<TaskNumber> mCustomers;

	private Filter mFilter = new Filter() {
		@Override
		public String convertResultToString(Object resultValue) {
			return ((TaskNumber) resultValue).getTaskNumber();
		}

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			FilterResults results = new FilterResults();

			if (constraint != null) {
				ArrayList<TaskNumber> suggestions = new ArrayList<TaskNumber>();
				for (TaskNumber customer : mCustomers) {
					// Note: change the "contains" to "startsWith" if you only
					// want starting matches
					if (customer.getTaskNumber().toLowerCase()
							.contains(constraint.toString().toLowerCase())) {
						suggestions.add(customer);
					}
				}

				results.values = suggestions;
				results.count = suggestions.size();
			}

			return results;
		}

		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {
			clear();
			if (results != null && results.count > 0) {
				// we have filtered results
				addAll((ArrayList<TaskNumber>) results.values);
			} else {
				// no filter, add entire original list back in
				addAll(mCustomers);
			}
			notifyDataSetChanged();
		}
	};

	public AutoCompetAdapter3(Context context, int textViewResourceId,
			List<TaskNumber> customers) {
		super(context, textViewResourceId, customers);
		// copy all the customers into a master list
		mCustomers = new ArrayList<TaskNumber>(customers.size());
		mCustomers.addAll(customers);
		layoutInflater = (LayoutInflater) getContext().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;

		if (view == null) {
			view = layoutInflater.inflate(R.layout.autocomple_item, null);
		}

		TaskNumber customer = getItem(position);

		TextView name = (TextView) view.findViewById(R.id.itemName);
		name.setText(customer.getTaskNumber());

		return view;
	}

	@Override
	public Filter getFilter() {
		return mFilter;
	}
}