package com.pcm.pcmmanager.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.gcm.GcmListenerService;
import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.SplashActivity;

/**
 * Created by LG on 2016-06-07.
 */
public class MyGcmListenerService extends GcmListenerService{
    public MyGcmListenerService(){

    }
//메시지가 전달 되었을때 호출되는 함수
    @Override
    public void onMessageReceived(String from, Bundle data){
        // 실제 GCM 서버로부터 수신한 PayLoad를 처리하는 부분
        String title = data.getString("title");
        String message = data.getString("message");


        // GCM으로 받은 메세지를 디바이스에 알려주는 sendNotification()을 호출한다.
        sendNotification(title, message);
    }

    // GCM으로 수신한 Push메시지를 처리하여 Notification Center에 출력처리한다
    private void sendNotification(String title, String message){
        Intent intent = new Intent(this, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        // Notification 관련 설정 처리
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        // 앱 상단 알림바 영역에 수신한 Push메시지 출력처리
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }
}
