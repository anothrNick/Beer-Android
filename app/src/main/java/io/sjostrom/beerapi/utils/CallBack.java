package io.sjostrom.beerapi.utils;

/**
 * Created by Nick on 12/1/2015.
 */
public interface CallBack {
    public void onProgress();
    public void onResult(String result);
    public void onCancel();
}
