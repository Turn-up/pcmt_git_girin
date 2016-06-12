package com.pcm.pcmmanager.expert.bid_do;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.common.CustomTextWathcer;
import com.pcm.pcmmanager.data.BidDoResult;
import com.pcm.pcmmanager.data.ExpertEstimateDetail;
import com.pcm.pcmmanager.data.ExpertEstimateDetailResult;
import com.pcm.pcmmanager.manager.NetworkManager;

import java.io.IOException;

import okhttp3.Request;

public class BidDoActivity extends AppCompatActivity {
    ImageButton bid_do;
    ImageButton bid_do_up, bid_do_down;
    TextView moneyTitle1, moneyTitle2, marketTypeView, marketSubView, endDateView, bidCountView, addressView, contentView;
    EditText moneyEdit1, moneyEdit2;
    FrameLayout bid_do_frame;
    LinearLayout layout;
    String marketSn, marketType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bid_do);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        layout = (LinearLayout) findViewById(R.id.expert_estimate_bid_do_input_layout);
        bid_do = (ImageButton) findViewById(R.id.btn_expert_estimate_bid_do);
        bid_do_up = (ImageButton) findViewById(R.id.btn_expert_estimate_bid_do_up_image);
        bid_do_down = (ImageButton) findViewById(R.id.btn_expert_estimate_bid_do_down_image);
        bid_do_frame = (FrameLayout) findViewById(R.id.expert_estimate_bid_do_frame);
        moneyTitle1 = (TextView) findViewById(R.id.estimate_put_money_title_1);
        moneyTitle2 = (TextView) findViewById(R.id.estimate_put_money_title_2);
        moneyEdit1 = (EditText) findViewById(R.id.estimate_put_money_editText_1);
        moneyEdit2 = (EditText) findViewById(R.id.estimate_put_money_editText_2);

        marketTypeView = (TextView) findViewById(R.id.expert_estimate_market_type_title);
        marketSubView = (TextView) findViewById(R.id.expert_estimate_market_type_sub);
        endDateView = (TextView) findViewById(R.id.expert_estimate_end_date);
        bidCountView = (TextView) findViewById(R.id.expert_estimate_bid_count);
        addressView = (TextView) findViewById(R.id.expert_estiamte_address);
        contentView = (TextView) findViewById(R.id.expert_estimate_content);

        //3자리씩 끊기
        moneyEdit1.addTextChangedListener(new CustomTextWathcer(moneyEdit1));
        if (!marketTypeView.getText().equals("기타")) {
            moneyEdit2.addTextChangedListener(new CustomTextWathcer(moneyEdit2));
        }

        marketSn = getIntent().getStringExtra("marketSn");
        setData();

        bid_do.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String monthMoney = moneyEdit1.getText().toString();
                monthMoney = monthMoney.replaceAll(",", "");

                if (bid_do_frame.getVisibility() == View.GONE) {
                    bid_do_up.setVisibility(View.GONE);
                    bid_do_frame.setVisibility(View.VISIBLE);
                } else {
                    if (TextUtils.isEmpty(moneyEdit1.getText())) {
                        Toast.makeText(BidDoActivity.this, "금액을 제시해 주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        if (marketTypeView.getText().equals("기타") || !TextUtils.isEmpty(moneyEdit2.getText())) {
                            String editMoney = moneyEdit2.getText().toString();
                            editMoney = editMoney.replaceAll(",", "");
                            //네트워크 호출 후에 메인으로가서 팝업
                            NetworkManager.getInstance().getBidDo(monthMoney, editMoney, marketSn, new NetworkManager.OnResultListener<BidDoResult>() {
                                @Override
                                public void onSuccess(Request request, BidDoResult result) {
                                    if(result.getResult()==-1){
                                        Toast.makeText(BidDoActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                                    }else{
                                        //팝업 띄우기
                                        finish();
                                    }
                                }

                                @Override
                                public void onFail(Request request, IOException exception) {

                                }
                            });
                        } else {
                            Toast.makeText(BidDoActivity.this, "금액을 제시해 주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

    }

    private void setData() {
        NetworkManager.getInstance().getExpertEstimateDetailResult(marketSn, new NetworkManager.OnResultListener<ExpertEstimateDetailResult>() {
            @Override
            public void onSuccess(Request request, ExpertEstimateDetailResult result) {
                if (result.getResult() == -1) {
                    Toast.makeText(BidDoActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    ExpertEstimateDetail item = result.getItem();
                    marketTypeView.setText(item.getMarketSubType());
                    if (item.getMarketType().equals("기장")) {
                        moneyTitle1.setText("월 기장료");
                        moneyTitle2.setText("조정료");
                        marketSubView.setText("매출" + item.getBusinessScale() + ", 종업원 " + item.getEmployeeCount() + "명");

                    } else if (item.getMarketType().equals("TAX")) {
                        moneyTitle1.setText("제시금액");
                        moneyTitle2.setText("과세금액");
                        moneyEdit2.setBackgroundResource(R.drawable.expert_bid_do_persent_box);
                        String temp = ", 자산내용 ";
                        for (int i = 0; i < item.getAsset_type().size(); i++) {
                            temp += item.getAsset_type().get(i);
                        }
                        marketSubView.setText("자산 " + item.getAssetMoney() + temp);
                    } else if (item.getMarketType().equals("기타")) {
                        moneyTitle1.setText("제시금액");
                        moneyTitle2.setVisibility(View.GONE);
                        moneyEdit2.setText("");
                        moneyEdit2.setVisibility(View.GONE);

                    }
                    endDateView.setText("D-" + item.getEnddate());
                    bidCountView.setText("" + item.getBidCount());
                    addressView.setText(item.getAddress1() + " " + item.getAddress2());
                    contentView.setText(item.getContent());
                }
            }
            @Override
            public void onFail(Request request, IOException exception) {

            }
        });
    }

    public void onBidUpDown(View v) {
        switch (v.getId()) {
            case R.id.btn_expert_estimate_bid_do_up_image:
                bid_do_frame.setVisibility(View.VISIBLE);
                bid_do_up.setVisibility(View.GONE);
                layout.setBackgroundResource(R.drawable.expert_bid_do_input_box);
                break;

            case R.id.btn_expert_estimate_bid_do_down_image:
                bid_do_frame.setVisibility(View.GONE);
                bid_do_up.setVisibility(View.VISIBLE);
                layout.setBackgroundResource(R.color.bid_do_layout_backround);
                break;
        }
    }
}
