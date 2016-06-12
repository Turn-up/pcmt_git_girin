package com.pcm.pcmmanager.service;

import android.app.IntentService;
import android.content.Intent;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.manager.PropertyManager;

import java.io.IOException;

/**
 * Created by LG on 2016-06-07.
 */
public class RegistrationIntentService extends IntentService{
    private static final String TAG = "RegistrationIntentService";

    public RegistrationIntentService() {
        super(TAG);
    }

    // GCM 서버에 실제로 디바이스를 등록하고 Device Token을 발급받는 작업을 수행하는 클래스
    @Override
    protected void onHandleIntent(Intent intent) {

        // GCM Instance ID의 토큰을 가져오는 작업이 시작되면 LocalBoardcast로 GENERATING 액션임을 Broadcast한다.
//        LocalBroadcastManager.getInstance(this)
//                .sendBroadcast(new Intent(MainActivityFragment.REGISTRATION_GENERATING));

        // GCM을 위한 Instance ID를 가져온다.
        InstanceID instanceID = InstanceID.getInstance(this);
        String token = null;
        try {
            synchronized (TAG) {
                // GCM 앱을 등록하고 획득한 설정파일인 google-services.json을 기반으로 SenderID를 추출한다.
                // 이때 'apply plugin: 'com.google.gms.google-services'  부분이 정상적으로 적용되어 있는지
                // 체크해야 한다. 되어 있지 않을 경우 google-services.json파일에서 자동으로 gcm_defaultSenderId를
                // 추출해오지 못한다.
                String default_senderId = getString(R.string.gcm_defaultSenderId);
                // GCM의 기본 scope는 "GCM"으로 되어 있다.
                String scope = GoogleCloudMessaging.INSTANCE_ID_SCOPE;
                // Instance ID에 해당하는 토큰을 생성하여 가져온다.
                // (실제 Push Provider에서 Push메시지를 전송하는데 필요한 디바이스 Token값이다.)
                token = instanceID.getToken(default_senderId, scope, null);
                PropertyManager.getInstance().setRegistrationToken(token);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // GCM Instance ID에 해당하는 토큰을 획득하면 LocalBoardcast에 COMPLETE 액션을 알린다.
        // 이때 토큰을 함께 넘겨주어서 UI에 토큰 정보를 활용할 수 있도록 했다.
        // MainActivityFragment에 선언한 registBroadcastReceiver함수에서 받아서 처리한다.
//        Intent registrationComplete = new Intent(MainActivityFragment.REGISTRATION_COMPLETE);
//        registrationComplete.putExtra("token", token);
//        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);

    }
}
