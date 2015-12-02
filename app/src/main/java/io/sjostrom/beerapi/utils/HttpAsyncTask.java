package io.sjostrom.beerapi.utils;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Nick on 12/1/2015.
 */
public class HttpAsyncTask extends AsyncTask<String, Void, String> {

    private CallBack mCb;
    HashMap<Object, Object> mData = null;
    HashMap<Object, Object> mParams = new HashMap<Object, Object>();
    String mTypeOfRequest;
    String mStrToBeAppended = "";
    JSONObject mJSONPostData = null;
    Context mContext = null;

    public HttpAsyncTask(Context context, CallBack c, HashMap<Object, Object> data, JSONObject jsonObj, String request) {
        mContext = context;
        mCb = c;
        mTypeOfRequest = request;
        mJSONPostData = jsonObj;

        if((data != null) && (jsonObj == null)){
            mData = data;
            if(mTypeOfRequest.equalsIgnoreCase(HttpUtil.GET)){
                Object key = null;
                Iterator<Object> it = mData.keySet().iterator();
                while(it.hasNext()){
                    key = it.next();
                    mParams.put(key, mData.get(key));
                }
                for (int i = 0; i<mParams.size()-1; i++){
                    mStrToBeAppended+= "?" + key + "=" + mParams.get(key) + "&";
                }
                //add the last parameter without the "&"
                mStrToBeAppended+= "?" + key + "=" + mParams.get(key);
            }

            if(mTypeOfRequest.equalsIgnoreCase(HttpUtil.POST)){
                Object key = null;
                Iterator<Object> it = mData.keySet().iterator();
                while(it.hasNext()){
                    key = it.next();
                    mParams.put(key, mData.get(key));
                }
            }
        }
    }

    @Override
    protected String doInBackground(String... baseUrls) {

        publishProgress(null);
        if(mTypeOfRequest.equalsIgnoreCase("GET")){
            String finalURL = baseUrls[0]+ mStrToBeAppended;
            return HttpUtil.GET(finalURL);
        }

        if (mTypeOfRequest.equalsIgnoreCase("POST")){
            return HttpUtil.POST(baseUrls[0]);
        }
        return null;
    }

    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String result) {
        //if(mContext != null){
        mCb.onResult(result);
        //}
    }

    @Override
    protected void onProgressUpdate(Void...voids ) {
        //if(mContext != null){
        mCb.onProgress();
        //}
    }
}
