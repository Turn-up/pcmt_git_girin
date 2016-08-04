package com.pcm.pcmmanager;

import android.app.Application;
import android.content.Context;

import com.tsengvn.typekit.Typekit;

/**
 * Created by LG on 2016-05-16.
 */
public class MyApplication extends Application {

    private static Context context;

    public static final int CODELIST_ENTRY_POSITION = 0; //기장
    public static final int CODELIST_TAX_POSITION = 1; //TAX
    public static final int CODELIST_ETC_POSITION = 2; //기타
    public static final int CODELIST_ROLES_POSITION = 3; //사용자 분류
    public static final int CODELIST_USER_STATE_POSITION = 4;//사용자 상태
    public static final int CODELIST_LICENSE_POSITION = 5;// 자격증
    public static final int CODELIST_SEX_POSITION = 6;//성별
    public static final int CODELIST_BELONG_POSITION = 7;//소속(개인,법인)
    public static final int CODELIST_ESTIMATE_TYPE_POSITION = 9; //경매타입
    public static final int CODELIST_BUSINESS_TYPE_POSITION = 10;//사업종류(개인,법인,간이)
    public static final int CODELIST_ESTIMATE_STATE_POSITION = 11;//경매상태(입찰전, 입찰중, 입찰마감, 낙찰완료, 입찰취소
    public static final int CODELIST_POINT_ADD_TYPE_POSITION = 12;//포인트 충전타입(충전, 사용, 입찰취소지급, 관리자지급, 관리자회수, 리뷰지급
    public static final int CODELIST_NOTICE_STATE_POSITION = 13;//공지분류
    public static final int CODELIST_TAXATION_TYPE_POSITION = 14;//사업자분류
    public static final int CODELIST_ASSET_TYPE_POSITION = 15;//재산대상(토지, 주택, 주식, 예금)


    private static String UserType;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        Typekit.getInstance()
                .addCustom1(Typekit.createFromAsset(this, "fonts/NanumBarunGothic.ttf"))
                .addCustom2(Typekit.createFromAsset(this, "fonts/NotoSans-Regular.ttf"));

    }
    public static Context getContext() {
        return context;
    }

    public static String getUserType() {
        return UserType;
    }

    public static void setUserType(String userType) {
        UserType = userType;
    }


}
