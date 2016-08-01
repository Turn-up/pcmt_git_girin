package com.pcm.pcmmanager.utill;

import com.pcm.pcmmanager.R;

/**
 * Created by LG on 2016-07-27.
 */
public class Utills {

    public static int entryIcon(String category) {
        switch (category) {
            case "음식":
                return R.drawable.category_food_icon;
            case "소매":
                return R.drawable.category_small_market_icon;
            case "생활서비스":
                return R.drawable.category_service_icon;
            case "제조":
                return R.drawable.category_making_icon;
            case "도매/유통/무역":
                return R.drawable.category_market_icon;
            case "학문/교육":
                return R.drawable.category_study_icon;
            case "의료":
                return R.drawable.category_hospital_icon;
            case "부동산":
                return R.drawable.category_property_icon;
            case "문화/예술/종교":
                return R.drawable.category_art_icon;
            case "금융":
                return R.drawable.category_bank_icon;
            case "숙박":
                return R.drawable.category_stay_icon;
            case "교통/운송":
                return R.drawable.category_traffic_icon;
            case "관광/여가/오락":
                return R.drawable.category_freetime_icon;
            case "스타트업":
                return R.drawable.category_startup_icon;
        }
        return R.drawable.entry_icon;
    }
}