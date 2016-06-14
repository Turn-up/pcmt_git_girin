package com.pcm.pcmmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.pcm.pcmmanager.data.CommonCodeListResult;
import com.pcm.pcmmanager.data.ExpertCheckResult;
import com.pcm.pcmmanager.data.RefreshTokenResult;
import com.pcm.pcmmanager.expert.ExpertMainActivity;
import com.pcm.pcmmanager.login.LoginActivity;
import com.pcm.pcmmanager.manager.NetworkManager;
import com.pcm.pcmmanager.manager.PropertyManager;
import com.pcm.pcmmanager.nomal.NomalMainActivity;
import com.pcm.pcmmanager.service.RegistrationIntentService;

import java.io.IOException;

import okhttp3.Request;

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        NetworkManager.getInstance().getCommonCodeList(new NetworkManager.OnResultListener<CommonCodeListResult>() {
            @Override
            public void onSuccess(Request request, CommonCodeListResult result) {
                PropertyManager.getInstance().commonCodeList = result.getCodelist();
                PropertyManager.getInstance().commonRegionLists = result.getRegionlist();
            }

            @Override
            public void onFail(Request request, IOException exception) {

            }
        });
        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (!TextUtils.isEmpty(PropertyManager.getInstance().getAuthorizationToken())) {
                    setRefreshToken();
                } else {
                    overridePendingTransition(0, android.R.anim.fade_in);
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };
        handler.sendEmptyMessageDelayed(0, 2000);
    }

    public void setRefreshToken() {
        NetworkManager.getInstance().getRefreshToken(PropertyManager.getInstance().getAuthorizationToken(), new NetworkManager.OnResultListener<RefreshTokenResult>() {
            @Override
            public void onSuccess(Request request, RefreshTokenResult result) {
                if (result.getResult() == -1) {
                    Toast.makeText(SplashActivity.this,result.getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    RefreshTokenResult refreshTokenResult = result;
                    PropertyManager.getInstance().setAuthorizationToken(refreshTokenResult.getToken());
                    if (refreshTokenResult.getRoles().equals("Users")) {
                        MyApplication.setUserType("Users");
                        overridePendingTransition(0, android.R.anim.fade_in);
                        startActivity(new Intent(SplashActivity.this, NomalMainActivity.class));
                        finish();
                    } else if (refreshTokenResult.getRoles().equals("Experts")) {
                        getExpertState();
                    }
                }

            }

            @Override
            public void onFail(Request request, IOException exception) {
                Toast.makeText(SplashActivity.this, "죄송합니다. 서비스 점검중 입니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getExpertState() {
        NetworkManager.getInstance().getExpertCheck(new NetworkManager.OnResultListener<ExpertCheckResult>() {
            @Override
            public void onSuccess(Request request, ExpertCheckResult result) {
                PropertyManager.getInstance().setExpertCheck(result.getCheck());
                MyApplication.setUserType("Experts");
                overridePendingTransition(0, android.R.anim.fade_in);
                startActivity(new Intent(SplashActivity.this, ExpertMainActivity.class));
                finish();

            }

            @Override
            public void onFail(Request request, IOException exception) {

            }
        });
    }
}
