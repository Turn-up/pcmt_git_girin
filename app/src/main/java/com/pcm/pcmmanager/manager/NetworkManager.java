package com.pcm.pcmmanager.manager;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.google.gson.Gson;
import com.pcm.pcmmanager.MyApplication;
import com.pcm.pcmmanager.data.BidDoResult;
import com.pcm.pcmmanager.data.CommonCodeListResult;
import com.pcm.pcmmanager.data.ConditionSearchListResult;
import com.pcm.pcmmanager.data.EstimateConfirmResult;
import com.pcm.pcmmanager.data.EstimateRequestResult;
import com.pcm.pcmmanager.data.ExpertBidStatusResult;
import com.pcm.pcmmanager.data.ExpertCheckResult;
import com.pcm.pcmmanager.data.ExpertConfirmCheckResult;
import com.pcm.pcmmanager.data.ExpertDetailResult;
import com.pcm.pcmmanager.data.ExpertDetailReviewResult;
import com.pcm.pcmmanager.data.ExpertEstimateDetailResult;
import com.pcm.pcmmanager.data.ExpertEstimateResult;
import com.pcm.pcmmanager.data.ExpertNavInfoResult;
import com.pcm.pcmmanager.data.LoginResult;
import com.pcm.pcmmanager.data.LogoutResult;
import com.pcm.pcmmanager.data.MainBidCountResult;
import com.pcm.pcmmanager.data.MyEstimateEditModifyResult;
import com.pcm.pcmmanager.data.MyEstimateListResult;
import com.pcm.pcmmanager.data.NomalMainContentResult;
import com.pcm.pcmmanager.data.NomalUserMainResult;
import com.pcm.pcmmanager.data.NoticeListResult;
import com.pcm.pcmmanager.data.RefreshTokenResult;
import com.pcm.pcmmanager.data.ReviewWriteResult;
import com.pcm.pcmmanager.data.UserDeleteResult;
import com.pcm.pcmmanager.data.UserSignupResult;
import com.pcm.pcmmanager.data.PointListResult;

import java.io.File;
import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by LG on 2016-05-26.
 */
public class NetworkManager {
    private static NetworkManager instance;

    public static NetworkManager getInstance() {
        if (instance == null) {
            instance = new NetworkManager();
        }
        return instance;
    }

    private static final int DEFAULT_CACHE_SIZE = 50 * 1024 * 1024;
    private static final String DEFAULT_CACHE_DIR = "miniapp";
    OkHttpClient mClient;

    private NetworkManager() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        Context context = MyApplication.getContext();
        CookieManager cookieManager = new CookieManager(new PersistentCookieStore(context), CookiePolicy.ACCEPT_ALL);
        builder.cookieJar(new JavaNetCookieJar(cookieManager));

        File dir = new File(context.getExternalCacheDir(), DEFAULT_CACHE_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        builder.cache(new Cache(dir, DEFAULT_CACHE_SIZE));

        builder.connectTimeout(30, TimeUnit.SECONDS);
        builder.readTimeout(30, TimeUnit.SECONDS);
        builder.writeTimeout(30, TimeUnit.SECONDS);

        mClient = builder.build();
    }

    public interface OnResultListener<T> {
        public void onSuccess(Request request, T result);

        public void onFail(Request request, IOException exception);
    }

    private static final int MESSAGE_SUCCESS = 1;
    private static final int MESSAGE_FAIL = 2;

    class NetworkHandler extends Handler {
        public NetworkHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            NetworkResult result = (NetworkResult) msg.obj;
            switch (msg.what) {
                case MESSAGE_SUCCESS:
                    result.listener.onSuccess(result.request, result.result);
                    break;
                case MESSAGE_FAIL:
                    result.listener.onFail(result.request, result.excpetion);
                    break;
            }
        }
    }

    NetworkHandler mHandler = new NetworkHandler(Looper.getMainLooper());

    static class NetworkResult<T> {
        Request request;
        OnResultListener<T> listener;
        IOException excpetion;
        T result;
    }

    Gson gson = new Gson();

    private static final String PCM_SEVER = "http://52.78.37.73:3000";


    /*전문가 메인 견적 리스트*/
    private static final String PCM_EXPERT_ESTIMATE_LIST_URL = PCM_SEVER + "/api/markets/list";
    public Request getExpertEstimateResult(String pagesize, String last_marketsn, OnResultListener<ExpertEstimateResult> listener) {
        String url = PCM_EXPERT_ESTIMATE_LIST_URL;
        RequestBody body = new FormBody.Builder()
                .add("pagesize", pagesize)
                .add("last_marketsn", last_marketsn)
                .build();
        Request request = new Request.Builder()
                .header("version", "1.0") //안드로이드 버젼
                .header("authorization",PropertyManager.getInstance().getAuthorizationToken())
                .url(url)
                .post(body)
                .build();

        final NetworkResult<ExpertEstimateResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String text = response.body().string();
                if (response.isSuccessful()) {
                    ExpertEstimateResult data = gson.fromJson(text, ExpertEstimateResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    result.excpetion = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }

    /*전문가 메인 견적 리스트 상세 조회*/
    private static final String PCM_EXPERT_ESTIMATE_DETAIL_URL = PCM_SEVER + "/api/markets/info/%s";
    public Request getExpertEstimateDetailResult(String marketSn, OnResultListener<ExpertEstimateDetailResult> listener) {
        String url = String.format(PCM_EXPERT_ESTIMATE_DETAIL_URL, marketSn);
        RequestBody body = new FormBody.Builder()
                .build();
        Request request = new Request.Builder()
                .header("version", "1.0") //안드로이드 버젼
                .header("authorization",PropertyManager.getInstance().getAuthorizationToken())
                .url(url)
                .post(body)
                .build();

        final NetworkResult<ExpertEstimateDetailResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String text = response.body().string();
                if (response.isSuccessful()) {
                    ExpertEstimateDetailResult data = gson.fromJson(text, ExpertEstimateDetailResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    result.excpetion = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }

    /*전문가 견적 찾기*/
    private static final String PCM_EXPERT_ESTIAMTE_SEARCH = PCM_SEVER + "/api/markets/search/list";
    public Request getExpertEstimateSearch(String pageSize, String lastMarketSn, String marketType, String marketSubType, String regionType, String regionSubType, OnResultListener<ExpertEstimateResult> listener) {
        String url = PCM_EXPERT_ESTIAMTE_SEARCH;


        RequestBody body = new FormBody.Builder()
                .add("pagesize", pageSize)
                .add("last_marketsn", lastMarketSn)
                .add("markettype", marketType)
                .add("marketsubtype", marketSubType)
                .add("regiontype", regionType)
                .add("regionsubtype", regionSubType)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .header("version", "1.0") //안드로이드 버젼 전송
                .header("authorization",PropertyManager.getInstance().getAuthorizationToken())
                .post(body)
                .build();

        final NetworkResult<ExpertEstimateResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String text = response.body().string();
                if (response.isSuccessful()) {
                    ExpertEstimateResult data = gson.fromJson(text, ExpertEstimateResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    result.excpetion = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }

    /*전문가 검색 리스트*/
    private static final String PCM_EXPERT_SEARCH_LIST_URL = PCM_SEVER + "/experts/search";
    public Request getExpertConditionSearch(String pageSize, String lastExpertSn, String marketType, String marketSubType, String regionType, String regionSubType, OnResultListener<ConditionSearchListResult> listener) {
        String url = String.format(PCM_EXPERT_SEARCH_LIST_URL);
        RequestBody body = new FormBody.Builder()
                .add("pagesize", pageSize)
                .add("last_expertsn", lastExpertSn)
                .add("markettype", marketType)
                .add("marketsubtype", marketSubType)
                .add("regiontype", regionType)
                .add("regionsubtype", regionSubType)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .header("version", "1.0") //안드로이드 버젼 전송
                .post(body)
                .build();

        final NetworkResult<ConditionSearchListResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String text = response.body().string();
                if (response.isSuccessful()) {
                    ConditionSearchListResult data = gson.fromJson(text, ConditionSearchListResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    result.excpetion = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }

    /*전문가 검색 상세- 기본*/
    private static final String PCM_EXPERT_DETAIL_URL = PCM_SEVER + "/experts/info/%s";

    public Request getExpertDetailResult(String expertsn, OnResultListener<ExpertDetailResult> listener) {
        String url = String.format(PCM_EXPERT_DETAIL_URL, expertsn);
        RequestBody body = new FormBody.Builder()
                .build();

        Request request = new Request.Builder()
                .url(url)
                .header("version", "1.0") //안드로이드 버젼 전송
                .post(body)
                .build();

        final NetworkResult<ExpertDetailResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String text = response.body().string();
                if (response.isSuccessful()) {
                    ExpertDetailResult data = gson.fromJson(text, ExpertDetailResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    result.excpetion = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }

    /*전문가 검색 상세 -리뷰*/
    private static final String PCM_EXPERT_DETAIL_REVIEW_URL = PCM_SEVER + "/experts/info/%s/review";
    public Request getExpertDetailReviewResult(String pagesize, String last_marketsn, String expertsn, OnResultListener<ExpertDetailReviewResult> listener) {
        String url = String.format(PCM_EXPERT_DETAIL_REVIEW_URL, expertsn);
        RequestBody body = new FormBody.Builder()
                .add("pagesize", pagesize)
                .add("last_marketsn", last_marketsn)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .header("version", "1.0") //안드로이드 버젼 전송
                .post(body)
                .build();

        final NetworkResult<ExpertDetailReviewResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String text = response.body().string();
                if (response.isSuccessful()) {
                    ExpertDetailReviewResult data = gson.fromJson(text, ExpertDetailReviewResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    result.excpetion = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }

    /*전문가 매물 입찰하기*/
    private static final String PCM_EXPERT_BID_DO_URL = PCM_SEVER + "/api/markets/info/%s/insert";

    public Request getBidDo(String price, String price2, String marketSn, OnResultListener<BidDoResult> listener) {
        String url = String.format(PCM_EXPERT_BID_DO_URL, marketSn);
        RequestBody body = new FormBody.Builder()
                .add("price1", price)
                .add("price2", price2)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .header("authorization", PropertyManager.getInstance().getAuthorizationToken())//토큰전송
                .header("version", "1.0") //안드로이드 버젼 전송
                .post(body)
                .build();

        final NetworkResult<BidDoResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String text = response.body().string();
                if (response.isSuccessful()) {
                    BidDoResult data = gson.fromJson(text, BidDoResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    result.excpetion = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }

    /*전문가 기본 정보 조회*/
    private static final String PCM_EXPERT_NAV_INFO = PCM_SEVER + "/api/experts/mymain";

    public Request getExpertsNavInfo(OnResultListener<ExpertNavInfoResult> listener) {
        String url = PCM_EXPERT_NAV_INFO;

        RequestBody body = new FormBody.Builder()
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("authorization",PropertyManager.getInstance().getAuthorizationToken())
                .header("version", "1.0") //안드로이드 버젼 전송
                .build();

        final NetworkResult<ExpertNavInfoResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String text = response.body().string();
                if (response.isSuccessful()) {
                    ExpertNavInfoResult data = gson.fromJson(text, ExpertNavInfoResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    result.excpetion = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }

    /*메인 경매,거래중 count*/
    private static final String PCM_MAIN_BID_COUNT = PCM_SEVER + "/main";

    public Request getMainBidCount(OnResultListener<MainBidCountResult> listener) {
        String url = PCM_MAIN_BID_COUNT;

        RequestBody body = new FormBody.Builder()
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("version", "1.0") //안드로이드 버젼 전송
                .build();

        final NetworkResult<MainBidCountResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String text = response.body().string();
                if (response.isSuccessful()) {
                    MainBidCountResult data = gson.fromJson(text, MainBidCountResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    result.excpetion = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }

    /*전문가 입찰상태*/
    private static final String PCM_EXPERT_BID_STATUS = PCM_SEVER + "/api/experts/list";

    public Request getExpertBidStatus(String pagesize, String last_marketsn, String status, OnResultListener<ExpertBidStatusResult> listener) {
        String url = PCM_EXPERT_BID_STATUS;

        RequestBody body = new FormBody.Builder()
                .add("pagesize", pagesize)
                .add("last_marketsn", last_marketsn)
                .add("status", status)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("authorization",PropertyManager.getInstance().getAuthorizationToken())
                .header("version", "1.0") //안드로이드 버젼 전송
                .build();

        final NetworkResult<ExpertBidStatusResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String text = response.body().string();
                if (response.isSuccessful()) {
                    ExpertBidStatusResult data = gson.fromJson(text, ExpertBidStatusResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    result.excpetion = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }

    /*공지사항 리스트*/
    private static final String PCM_NOTICE_LIST = PCM_SEVER + "/notices/list";

    public Request getNoticeList(String pagesize, String last_noticesn, String status, OnResultListener<NoticeListResult> listener) {
        String url = PCM_NOTICE_LIST;

        RequestBody body = new FormBody.Builder()
                .add("pagesize", pagesize)
                .add("last_noticesn", last_noticesn)
                .add("status", status)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("version", "1.0") //안드로이드 버젼 전송
                .build();

        final NetworkResult<NoticeListResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String text = response.body().string();
                if (response.isSuccessful()) {
                    NoticeListResult data = gson.fromJson(text, NoticeListResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    result.excpetion = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }

    /*일반 사용자 프로필 정보*/
    private static final String PCM_USER_MAIN_INFO = PCM_SEVER + "/api/users/mymain";

    public Request getUserMainInfo(OnResultListener<NomalUserMainResult> listener) {
        String url = PCM_USER_MAIN_INFO;
        RequestBody body = new FormBody.Builder()
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("authorization",PropertyManager.getInstance().getAuthorizationToken())
                .header("version", "1.0") //안드로이드 버젼 전송
                .build();

        final NetworkResult<NomalUserMainResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String text = response.body().string();
                if (response.isSuccessful()) {
                    NomalUserMainResult data = gson.fromJson(text, NomalUserMainResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    result.excpetion = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }

    /*일반 사용자 메인 공지 & Tip*/
    private static final String PCM_NOMAL_MAIN_CONTENT_LIST = PCM_SEVER + "/maincontent";
    public Request getNomalMainContentList(OnResultListener<NomalMainContentResult> listener) {
        String url = PCM_NOMAL_MAIN_CONTENT_LIST;

        RequestBody body = new FormBody.Builder()
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("version", "1.0") //안드로이드 버젼 전송
                .build();

        final NetworkResult<NomalMainContentResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String text = response.body().string();
                if (response.isSuccessful()) {
                    NomalMainContentResult data = gson.fromJson(text, NomalMainContentResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    result.excpetion = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }

    /*22. 일반 사용자 내 견적 리스트*/
    private static final String PCM_NOMAL_ESTIMATE_LIST = PCM_SEVER + "/api/users/marketslist";

    public Request getNomalEstiamteList(String pagesize, String last_marketsn, OnResultListener<MyEstimateListResult> listener) {
        String url = PCM_NOMAL_ESTIMATE_LIST;

        RequestBody body = new FormBody.Builder()
                .add("pagesize", pagesize)
                .add("last_marketsn", last_marketsn)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("authorization",PropertyManager.getInstance().getAuthorizationToken())
                .header("version", "1.0") //안드로이드 버젼 전송
                .build();

        final NetworkResult<MyEstimateListResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String text = response.body().string();
                if (response.isSuccessful()) {
                    MyEstimateListResult data = gson.fromJson(text, MyEstimateListResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    result.excpetion = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }
    /*24. 일반 사용자 견적 수정*/
    private static final String PCM_NOMAL_ESTIMATE_MODIFY = PCM_SEVER + "/api/markets/info/%s/update";
    public Request getNomalEstiamteModify(String marketsn,String markettype, String marketsubtype, String regiontype,
                                          String regionsubtype, String markettype1_1, String markettype1_2,
                                          String markettype1_3, String markettype1_4, List<String> markettype2_1,
                                          String markettype2_2, String enddate, String content,OnResultListener<MyEstimateEditModifyResult> listener) {
        String url = String.format(PCM_NOMAL_ESTIMATE_MODIFY,marketsn);
        FormBody.Builder myBuilder = new FormBody.Builder();
        if (markettype2_1 != null) {
            for (int i = 0; i < markettype2_1.size(); i++) {
                myBuilder.add("markettype2_1", markettype2_1.get(i).toString());
            }
        }
        RequestBody body = myBuilder
                .add("markettype",markettype)
                .add("marketsubtype",marketsubtype)
                .add("regiontype",regiontype)
                .add("regionsubtype",regionsubtype)
                .add("markettype1_1",markettype1_1)
                .add("markettype1_2",markettype1_2)
                .add("markettype1_3",markettype1_3)
                .add("markettype1_4",markettype1_4)
                .add("markettype2_2",markettype2_2)
                .add("enddate",enddate)
                .add("content",content)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("authorization",PropertyManager.getInstance().getAuthorizationToken())
                .header("version", "1.0") //안드로이드 버젼 전송
                .build();

        final NetworkResult<MyEstimateEditModifyResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String text = response.body().string();
                if (response.isSuccessful()) {
                    MyEstimateEditModifyResult data = gson.fromJson(text, MyEstimateEditModifyResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    result.excpetion = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }
    /*일반 사용자 견적 요청하기*/
    private static final String PCM_NOMAL_ESTIMATE_REQUEST_LIST = PCM_SEVER + "/api/markets/insert";
    public Request getNomalEstiamteRequestList(String marketType, String marketSubType, String regionType, String regionSubType, String markettpye1_1, String markettype1_2,
                                               String markettype1_3, String employeeCount, List<String> taxAsset, String taxAssetScale, String enddate, String content, OnResultListener<EstimateRequestResult> listener) {
        String url = PCM_NOMAL_ESTIMATE_REQUEST_LIST;
        FormBody.Builder myBuilder = new FormBody.Builder();
        if (taxAsset != null) {
            for (int i = 0; i < taxAsset.size(); i++) {
                myBuilder.add("markettype2_1", taxAsset.get(i).toString());
            }
        }
        RequestBody body = myBuilder
                .add("markettype", marketType)
                .add("marketsubtype", marketSubType)
                .add("regiontype", regionType)
                .add("regionsubtype", regionSubType)
                .add("markettype1_1", markettpye1_1)
                .add("markettype1_2", markettype1_2)
                .add("markettype1_3", markettype1_3)
                .add("markettype1_4", employeeCount)
                .add("markettype2_2", taxAssetScale)
                .add("enddate", enddate)
                .add("content", content)
                .build();


        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("authorization",PropertyManager.getInstance().getAuthorizationToken())
                .header("version", "1.0") //안드로이드 버젼 전송
                .build();

        final NetworkResult<EstimateRequestResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String text = response.body().string();
                if (response.isSuccessful()) {
                    EstimateRequestResult data = gson.fromJson(text, EstimateRequestResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    result.excpetion = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }

    /*낙찰 완료*/
    private static final String PCM_ESTIMATE_CONFIRM = PCM_SEVER + "/api/markets/info/%s/success";
    public Request getEstimateConfirm(String marketSn, String expertSn, OnResultListener<EstimateConfirmResult> listener) {
        String url = String.format(PCM_ESTIMATE_CONFIRM, marketSn);

        RequestBody body = new FormBody.Builder()
                .add("expertsn", expertSn)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("authorization",PropertyManager.getInstance().getAuthorizationToken())
                .header("version", "1.0") //안드로이드 버젼 전송
                .build();

        final NetworkResult<EstimateConfirmResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String text = response.body().string();
                if (response.isSuccessful()) {
                    EstimateConfirmResult data = gson.fromJson(text, EstimateConfirmResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    result.excpetion = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }

    /*공통 코드 리스트*/
    private static final String PCM_COMMON_CODE_LIST = PCM_SEVER + "/codelist";
    public Request getCommonCodeList(OnResultListener<CommonCodeListResult> listener) {
        String url = PCM_COMMON_CODE_LIST;

        RequestBody body = new FormBody.Builder()
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("version", "1.0") //안드로이드 버젼 전송
                .build();

        final NetworkResult<CommonCodeListResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String text = response.body().string();
                if (response.isSuccessful()) {
                    CommonCodeListResult data = gson.fromJson(text, CommonCodeListResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    result.excpetion = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }

    /*전문가 이용가능 여부*/
    private static final String PCM_EXPERT_CONFIRM_CHECK = PCM_SEVER + "/api/experts/check";
    public Request getExpertConfirmCheck(OnResultListener<ExpertConfirmCheckResult> listener) {
        String url = PCM_EXPERT_CONFIRM_CHECK;

        RequestBody body = new FormBody.Builder()
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("authorization",PropertyManager.getInstance().getAuthorizationToken())
                .header("version", "1.0") //안드로이드 버젼 전송
                .build();

        final NetworkResult<ExpertConfirmCheckResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String text = response.body().string();
                if (response.isSuccessful()) {
                    ExpertConfirmCheckResult data = gson.fromJson(text, ExpertConfirmCheckResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    result.excpetion = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }

    /*5.유저가입*/
    private static final String PCM_SIGN_UP = PCM_SEVER + "/users/insert";
    public Request getSignUp(String username, String email, String phone, String password, String pushkey,
                             String usertype1,String usertype2,String role,OnResultListener<UserSignupResult> listener) {
        String url = PCM_SIGN_UP;

        RequestBody body = new FormBody.Builder()
                .add("username",username)
                .add("email",email)
                .add("phone",phone)
                .add("password",password)
                .add("pushkey",pushkey)
                .add("usertype1",usertype1)
                .add("usertype2",usertype2)
                .add("roles",role)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("version", "1.0") //안드로이드 버젼 전송
                .build();

        final NetworkResult<UserSignupResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String text = response.body().string();
                if (response.isSuccessful()) {
                    UserSignupResult data = gson.fromJson(text, UserSignupResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    result.excpetion = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }
    /*5.로그아웃*/
    private static final String PCM_LOGOUT = PCM_SEVER + "/api/users/logout";
    public Request getLogout(OnResultListener<UserSignupResult> listener) {
        String url = PCM_LOGOUT;

        RequestBody body = new FormBody.Builder()
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("authorization",PropertyManager.getInstance().getAuthorizationToken())
                .header("version", "1.0") //안드로이드 버젼 전송
                .build();

        final NetworkResult<UserSignupResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String text = response.body().string();
                if (response.isSuccessful()) {
                    UserSignupResult data = gson.fromJson(text, UserSignupResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    result.excpetion = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }
    /*15. 새로운 토큰 발급*/
    private static final String PCM_REFRESH_TOKEN = PCM_SEVER + "/api/users/refreshtoken";
    public Request getRefreshToken(String authorizationToken,OnResultListener<RefreshTokenResult> listener) {
        String url = PCM_REFRESH_TOKEN;

        RequestBody body = new FormBody.Builder()
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("version", "1.0") //안드로이드 버젼 전송
                .header("authorization",authorizationToken)
                .build();

        final NetworkResult<RefreshTokenResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String text = response.body().string();
                if (response.isSuccessful()) {
                    RefreshTokenResult data = gson.fromJson(text, RefreshTokenResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    result.excpetion = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }

    /*4. 유저 로그인*/
    private static final String PCM_LOGIN = PCM_SEVER + "/users/loginProcess";
    public Request getLogin(String email,String password,OnResultListener<LoginResult> listener) {
        String url = PCM_LOGIN;

        RequestBody body = new FormBody.Builder()
                .add("email",email)
                .add("password",password)
                .add("pushkey",PropertyManager.getInstance().getRegistrationToken())
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("version", "1.0") //안드로이드 버젼 전송
                .build();

        final NetworkResult<LoginResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String text = response.body().string();
                if (response.isSuccessful()) {
                    LoginResult data = gson.fromJson(text, LoginResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    result.excpetion = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }

    /*유저 탈퇴- > 아직*/
    private static final String PCM_USER_LOGOUT = PCM_SEVER + "/api/users/logout";
    public Request getUserLogout(OnResultListener<LogoutResult> listener) {
        String url = PCM_USER_LOGOUT;

        RequestBody body = new FormBody.Builder()
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("authorization",PropertyManager.getInstance().getAuthorizationToken())
                .header("version", "1.0") //안드로이드 버젼 전송
                .build();

        final NetworkResult<LogoutResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String text = response.body().string();
                if (response.isSuccessful()) {
                    LogoutResult data = gson.fromJson(text, LogoutResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    result.excpetion = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }
    /*91. 전문가 사용가능*/
    private static final String PCM_EXPERT_CHECK = PCM_SEVER + "/api/experts/check";
    public Request getExpertCheck(OnResultListener<ExpertCheckResult> listener) {
        String url = PCM_EXPERT_CHECK;

        RequestBody body = new FormBody.Builder()
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("authorization",PropertyManager.getInstance().getAuthorizationToken())
                .header("version", "1.0") //안드로이드 버젼 전송
                .build();

        final NetworkResult<ExpertCheckResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String text = response.body().string();
                if (response.isSuccessful()) {
                    ExpertCheckResult data = gson.fromJson(text, ExpertCheckResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    result.excpetion = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }

    /*88. 리뷰작성*/
    private static final String PCM_REVIEW_WRITE = PCM_SEVER + "/api/markets/info/%s/%s/insert";
    public Request getReviewWrite(String marketSn, String expertSn, String score, String content,OnResultListener<ReviewWriteResult> listener) {
        String url = String.format(PCM_REVIEW_WRITE,marketSn,expertSn);

        RequestBody body = new FormBody.Builder()
                .add("score",score)
                .add("content",content)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("authorization",PropertyManager.getInstance().getAuthorizationToken())
                .header("version", "1.0") //안드로이드 버젼 전송
                .build();

        final NetworkResult<ReviewWriteResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String text = response.body().string();
                if (response.isSuccessful()) {
                    ReviewWriteResult data = gson.fromJson(text, ReviewWriteResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    result.excpetion = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }

    /*95. 마일리지 조회*/
    private static final String PCM_MILEAGELOG = PCM_SEVER + "/api/experts/mileagelog";
    public Request getMileageList(String pagesize, String last_mileagelogsn, OnResultListener<PointListResult> listener) {
        String url = PCM_MILEAGELOG;

        RequestBody body = new FormBody.Builder()
                .add("pagesize",pagesize)
                .add("last_mileagelogsn",last_mileagelogsn)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("authorization",PropertyManager.getInstance().getAuthorizationToken())
                .header("version", "1.0") //안드로이드 버젼 전송
                .build();

        final NetworkResult<PointListResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String text = response.body().string();
                if (response.isSuccessful()) {
                    PointListResult data = gson.fromJson(text, PointListResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    result.excpetion = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }

    /*95. 유저 탈퇴*/
    private static final String PCM_USER_DELETE = PCM_SEVER + "/api/users/delete";
    public Request getUserDelete(OnResultListener<UserDeleteResult> listener) {
        String url = PCM_USER_DELETE;

        RequestBody body = new FormBody.Builder()
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("authorization",PropertyManager.getInstance().getAuthorizationToken())
                .header("version", "1.0") //안드로이드 버젼 전송
                .build();

        final NetworkResult<UserDeleteResult> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String text = response.body().string();
                if (response.isSuccessful()) {
                    UserDeleteResult data = gson.fromJson(text, UserDeleteResult.class);
                    result.result = data;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    result.excpetion = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }
}

