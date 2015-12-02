package io.sjostrom.beerapi.brewery;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import io.sjostrom.beerapi.R;

/**
 * Created by Nick on 12/2/2015.
 */
public class BreweryListAdapter extends RecyclerView.Adapter<BreweryListAdapter.ViewHolder> {

    public static String TAG = "BreweryListAdapter";

    JSONArray lstBreweries;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mBreweryName;

        public ViewHolder(View itemView, int viewType) {
            super(itemView);

            mBreweryName = (TextView) itemView.findViewById(R.id.beer_name);
        }
    }

    BreweryListAdapter() {
        lstBreweries = new JSONArray();
    }

    public void addItem(JSONObject json) {
        lstBreweries.put(json);
        this.notifyItemInserted(lstBreweries.length()-1);
    }

    public void addItems(JSONArray jsonArr) {
        for (int i = 0; i < jsonArr.length(); i++) {
            try {
                lstBreweries.put(jsonArr.getJSONObject(i));
                this.notifyItemInserted(lstBreweries.length()-1);
            } catch(Exception e) {
                Log.w(TAG, "exception updating: " + e.toString());
            }
        }
    }

    @Override
    public BreweryListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.beer_list_item,parent,false); //Inflating the layout

        ViewHolder vhItem = new ViewHolder(v,viewType); //Creating ViewHolder and passing the object of type view

        return vhItem;
    }

    @Override
    public void onBindViewHolder(BreweryListAdapter.ViewHolder holder, int position) {
        if(lstBreweries.length() >= position) {
            try {
                holder.mBreweryName.setText(lstBreweries.getJSONObject(position).getString("name"));
            } catch(Exception e) {
                Log.w(TAG, "exception: " + e.toString());
            }
        }
    }

    @Override
    public int getItemCount() {
        return lstBreweries.length();
    }
}
