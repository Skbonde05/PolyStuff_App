package myapp.org.userapp;

import android.content.Context;
import android.content.res.Configuration;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class CustomArrayAdapter extends ArrayAdapter<String> {

    private final List<String> originalList;
    private final List<String> filteredList;
    private final Filter filter = new CustomFilter();

    public CustomArrayAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        this.originalList = new ArrayList<>(objects);
        this.filteredList = new ArrayList<>(objects);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return filter;
    }

    private class CustomFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<String> filtered = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filtered.addAll(originalList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (String item : originalList) {
                    if (item.toLowerCase().contains(filterPattern)) {
                        filtered.add(item);
                    }
                }
            }

            results.values = filtered;
            results.count = filtered.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredList.clear();
            //noinspection unchecked
            filteredList.addAll((List<String>) results.values);
            clear();
            addAll(filteredList);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        TextView textView = (TextView) super.getView(position, convertView, parent);
        applyThemeAwareColors(getContext(), textView);
        return textView;
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        TextView textView = (TextView) super.getDropDownView(position, convertView, parent);
        applyThemeAwareColors(getContext(), textView);
        return textView;
    }

    private void applyThemeAwareColors(Context context, TextView textView) {
        int nightModeFlags = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES) {
            textView.setBackgroundColor(ContextCompat.getColor(context, R.color.dropdown_bg_dark));
            textView.setTextColor(ContextCompat.getColor(context, R.color.dropdown_text_dark));
        } else {
            textView.setBackgroundColor(ContextCompat.getColor(context, R.color.dropdown_bg_light));
            textView.setTextColor(ContextCompat.getColor(context, R.color.dropdown_text_light));
        }

        textView.setPadding(16, 16, 16, 16);
        textView.setTextSize(16);
    }
}
