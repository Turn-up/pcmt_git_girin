package com.pcm.pcmmanager.service;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by LG on 2016-06-07.
 */
public class MyInstanceIDListenerService extends InstanceIDListenerService {
    public MyInstanceIDListenerService(){

    }
    //주소
    @Override
    public void onTokenRefresh(){
        // Fetch updated Instance ID token and notify our app's server of any changes (if applicable).
        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);
    }
}
