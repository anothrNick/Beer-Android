package io.sjostrom.beerapi.beer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import io.sjostrom.beerapi.R;

/**
 * Created by Nick on 12/1/2015.
 */
public class BeerListAdapter extends RecyclerView.Adapter<BeerListAdapter.ViewHolder> {

    Context mContext;
    int lastPosition = -1;
    JSONArray lstBeers;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mBeerName;
        LinearLayout container;

        public ViewHolder(View itemView, int viewType) {
            super(itemView);

            mBeerName = (TextView) itemView.findViewById(R.id.beer_name);
            container = (LinearLayout) itemView.findViewById(R.id.beer_container);
        }
    }

    BeerListAdapter(Context context) {
        lstBeers = new JSONArray();
        mContext = context;
    }

    public void addItem(JSONObject json) {
        lstBeers.put(json);
        this.notifyItemInserted(lstBeers.length()-1);
    }

    public void addItems(JSONArray jsonArr) {
        for (int i = 0; i < jsonArr.length(); i++) {
            try {
                lstBeers.put(jsonArr.getJSONObject(i));
                this.notifyItemInserted(lstBeers.length()-1);
            } catch(Exception e) {
                Log.w("BeerListAdapter", "exception updating: " + e.toString());
            }
        }
    }

    @Override
    public BeerListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.beer_list_item,parent,false); //Inflating the layout

        ViewHolder vhItem = new ViewHolder(v,viewType); //Creating ViewHolder and passing the object of type view

        return vhItem;
    }

    @Override
    public void onBindViewHolder(BeerListAdapter.ViewHolder holder, int position) {
        if(lstBeers.length() >= position) {
            try {
                holder.mBeerName.setText(lstBeers.getJSONObject(position).getString("name"));
            } catch(Exception e) {
                Log.w("BeerListAdapter", "exception: " + e.toString());
            }
        }

        //setAnimation(holder.container, position);
    }

    @Override
    public int getItemCount() {
        return lstBeers.length();
    }

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
}
