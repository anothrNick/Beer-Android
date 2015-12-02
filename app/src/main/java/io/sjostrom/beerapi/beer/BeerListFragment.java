package io.sjostrom.beerapi.beer;

import android.content.Context;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import io.sjostrom.beerapi.R;
import io.sjostrom.beerapi.utils.CallBack;
import io.sjostrom.beerapi.utils.HttpAsyncTask;
import io.sjostrom.beerapi.utils.HttpUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class BeerListFragment extends Fragment {

    public static String TAG = BeerListFragment.class.getSimpleName();

    private LinearLayout mHeaderProgress;
    private RecyclerView mRecyclerView;
    private BeerListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    final CallBack callback = new CallBack(){
            @Override
            public void onProgress() {
                Log.d(TAG, "PROGRESS");
            }

            @Override
            public void onResult(String result) {
                Log.d(TAG, "RESULT");

                mHeaderProgress.setVisibility(View.GONE);
                if(result != null && !result.isEmpty()) {
                    try {
                        mAdapter.addItems(new JSONArray(result));
                    } catch(Exception e) {
                        Log.d(TAG, "Exception parsing string to jsonarray: " + e.toString());
                    }
                }
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "CANCEL");
            }
        };

    public static BeerListFragment newInstance() {
        return new BeerListFragment();
    }

    public BeerListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.beer_list_fragment, container, false);

        HttpAsyncTask task = new HttpAsyncTask(getActivity(), callback, null, null, HttpUtil.GET);
        task.execute("https://beer.radicaldinosaur.com/api/beer");

        mRecyclerView = (RecyclerView) v.findViewById(R.id.generic_list);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new BeerListAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mHeaderProgress = (LinearLayout) v.findViewById(R.id.headerProgress);

        return v;
    }

}
