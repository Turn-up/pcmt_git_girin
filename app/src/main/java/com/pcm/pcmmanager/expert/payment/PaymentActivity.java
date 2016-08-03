package com.pcm.pcmmanager.expert.payment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.data.UserProfileResult;
import com.pcm.pcmmanager.manager.NetworkManager;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import okhttp3.Request;

public class PaymentActivity extends Activity {

    private String TAG = "iamport";
    private WebView mainWebView;
    private NiceWebViewClient niceClient;
    private final String APP_SCHEME = "iamportnice://";
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        mainWebView = (WebView) findViewById(R.id.mainWebView);

        niceClient = new NiceWebViewClient(this, mainWebView);
        mainWebView.addJavascriptInterface(new AndroidBridge(this), "paymentApp");
        mainWebView.setWebViewClient(niceClient);
        WebSettings settings = mainWebView.getSettings();
        settings.setJavaScriptEnabled(true);

        Intent intent = getIntent();
        Uri intentData = intent.getData();
        if (intentData == null) {
            NetworkManager.getInstance().getUserProfile(new NetworkManager.OnResultListener<UserProfileResult>() {
                @Override
                public void onSuccess(Request request, UserProfileResult result) {
                    try {
                        String str = null;
                        str = "email=" + URLEncoder.encode(result.getUserProfile().getEmail(), "UTF-8");
                        mainWebView.postUrl("http://52.78.37.73:3002/payment", str.getBytes());
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onFail(Request request, IOException exception) {

                }
            });
        } else {
            //isp 인증 후 복귀했을 때 결제 후속조치
            String url = intentData.toString();
            if (url.startsWith(APP_SCHEME)) {
                String redirectURL = url.substring(APP_SCHEME.length() + 3); //"://"가 추가로 더 전달됨
                mainWebView.loadUrl(redirectURL);
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /* 실시간 계좌이체 인증 후 후속처리 루틴 */
        String resVal = data.getExtras().getString("bankpay_value");
        String resCode = data.getExtras().getString("bankpay_code");

        if ("000".equals(resCode)) {
            niceClient.bankPayPostProcess(resCode, resVal);
        } else if ("091".equals(resCode)) {//계좌이체 결제를 취소한 경우
            Log.e(TAG, "계좌이체 결제를 취소하였습니다.");
        } else if ("060".equals(resCode)) {
            Log.e(TAG, "타임아웃");
        } else if ("050".equals(resCode)) {
            Log.e(TAG, "전자서명 실패");
        } else if ("040".equals(resCode)) {
            Log.e(TAG, "OTP/보안카드 처리 실패");
        } else if ("030".equals(resCode)) {
            Log.e(TAG, "인증모듈 초기화 오류");
        }
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

    }
}