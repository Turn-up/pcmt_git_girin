package com.pcm.pcmmanager.expert.payment;

import android.app.Activity;
import android.webkit.JavascriptInterface;

/**
 * Created by LG on 2016-08-03.
 */
public class AndroidBridge {
    /** Instantiate the interface and set the context */
    Activity mActivity;

    AndroidBridge(Activity activity) {
        mActivity = activity;
    }
    /** Show a toast from the web page */
    @JavascriptInterface
    public void CloseActivity() {
        mActivity.finish();
    }
}
