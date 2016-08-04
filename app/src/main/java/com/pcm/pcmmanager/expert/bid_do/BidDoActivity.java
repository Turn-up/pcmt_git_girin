package com.pcm.pcmmanager.expert.bid_do;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pcm.pcmmanager.BaseActivity;
import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.common.CustomTextWathcer;
import com.pcm.pcmmanager.data.CommonResult;
import com.pcm.pcmmanager.data.ExpertEstimateDetail;
import com.pcm.pcmmanager.data.ExpertEstimateDetailResult;
import com.pcm.pcmmanager.manager.NetworkManager;

import java.io.IOException;

import okhttp3.Request;

public class BidDoActivity extends BaseActivity {
    ImageButton bid_do;
    ImageButton bid_do_up, bid_do_down;
    TextView moneyTitle1, moneyTitle2, marketTypeView, endDateView, bidCountView, addressView, contentView, moneyBackColor1, moneyBackColor2,comment_text_count;
    TextView[] marketSubView;
    EditText moneyEdit1, moneyEdit2, comment;
    FrameLayout bid_do_frame;
    LinearLayout layout, layoutComent, layout_input2;
    String marketSn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bid_do);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        layout = (LinearLayout) findViewById(R.id.expert_estimate_bid_do_input_layout);
        layout_input2 = (LinearLayout) findViewById(R.id.estimate_layout_input2);
        layoutComent = (LinearLayout) findViewById(R.id.layout_comment);
        bid_do = (ImageButton) findViewById(R.id.btn_expert_estimate_bid_do);
        bid_do_up = (ImageButton) findViewById(R.id.btn_expert_estimate_bid_do_up_image);
        bid_do_down = (ImageButton) findViewById(R.id.btn_expert_estimate_bid_do_down_image);
        bid_do_frame = (FrameLayout) findViewById(R.id.expert_estimate_bid_do_frame);
        moneyTitle1 = (TextView) findViewById(R.id.estimate_put_money_title_1);
        moneyTitle2 = (TextView) findViewById(R.id.estimate_put_money_title_2);
        moneyEdit1 = (EditText) findViewById(R.id.estimate_put_money_editText_1);
        moneyEdit2 = (EditText) findViewById(R.id.estimate_put_money_editText_2);
        moneyBackColor1 = (TextView) findViewById(R.id.estimate_put_money1_backround);
        moneyBackColor2 = (TextView) findViewById(R.id.estimate_put_money2_backround);
        comment = (EditText) findViewById(R.id.estimate_put_comment);

        marketTypeView = (TextView) findViewById(R.id.expert_estimate_market_type_title);
        marketSubView = new TextView[4];
        marketSubView[0] = (TextView) findViewById(R.id.expert_estimate_market_type_sub1);
        marketSubView[1] = (TextView) findViewById(R.id.expert_estimate_market_type_sub2);
        marketSubView[2] = (TextView) findViewById(R.id.expert_estimate_market_type_sub3);
        marketSubView[3] = (TextView) findViewById(R.id.expert_estimate_market_type_sub4);
        endDateView = (TextView) findViewById(R.id.expert_estimate_end_date);
        bidCountView = (TextView) findViewById(R.id.expert_estimate_bid_count);
        addressView = (TextView) findViewById(R.id.expert_estiamte_address);
        contentView = (TextView) findViewById(R.id.expert_estimate_content);
        comment_text_count = (TextView)findViewById(R.id.expert_estimate_bid_comment_text_count);

        marketSn = getIntent().getStringExtra("marketSn");
        setData();

        /*전문가 코멘트 text 숫자 카운트*/
        comment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                count += comment.getText().toString().length();
                comment_text_count.setText(count + "/50");
            }

            @Override
            public void afterTextChanged(Editable s) {
                comment_text_count.setText(comment.getText().toString().length()+"/50");
            }
        });

        bid_do.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bid_do_frame.getVisibility() == View.INVISIBLE) {
                    bid_do_up.setVisibility(View.GONE);
                    bid_do_frame.setVisibility(View.VISIBLE);
                    layoutComent.setVisibility(View.VISIBLE);
                    layout.setBackgroundResource(R.drawable.expert_bid_do_input_box);
                } else {
                    if (marketTypeView.getText().toString().equals("상속세") && TextUtils.isEmpty(moneyEdit1.getText()) && TextUtils.isEmpty(moneyEdit2.getText())) {
                        Toast.makeText(BidDoActivity.this, "금액을 제시해 주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                    } else if (!marketTypeView.getText().toString().equals("상속세") && TextUtils.isEmpty(moneyEdit1.getText())) {
                        Toast.makeText(BidDoActivity.this, "금액을 제시해 주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                    } else if (!marketTypeView.getText().toString().equals("상속세") && TextUtils.isEmpty(moneyEdit2.getText()) && !marketTypeView.getText().toString().equals("기타")) {
                        Toast.makeText(BidDoActivity.this, "금액을 제시해 주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(comment.getText().toString())) {
                        Toast.makeText(BidDoActivity.this, "글을 작성 해주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        String monthMoney = moneyEdit1.getText().toString();
                        monthMoney = monthMoney.replaceAll(",", "");
                        String editMoney = moneyEdit2.getText().toString();
                        if (!TextUtils.isEmpty(editMoney) && !moneyTitle2.getText().equals("과세금액")) {
                            editMoney = moneyEdit2.getText().toString();
                            editMoney = editMoney.replaceAll(",", "");
                        }
                        if (TextUtils.isEmpty(editMoney))
                            editMoney = "0";
                        //네트워크 호출 후에 메인으로가서 팝업
                        NetworkManager.getInstance().getBidDo(comment.getText().toString(), monthMoney, editMoney, marketSn, new NetworkManager.OnResultListener<CommonResult>() {
                            @Override
                            public void onSuccess(Request request, CommonResult result) {
                                if (result.getResult() == -1) {
                                    Toast.makeText(BidDoActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                                } else {
                                    //팝업 띄우기
                                    finish();
                                }
                            }

                            @Override
                            public void onFail(Request request, IOException exception) {

                            }
                        });
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
                    long enddate = Long.valueOf(item.getEnddate()) - (item.getRegDate() / (24 * 60 * 60 * 1000));

                    if (enddate > 0) {
                        endDateView.setText("D-" + enddate);
                    } else if (enddate == 0) {
                        enddate = 24 - (item.getRegDate() % (24 * 60 * 60 * 1000)) / (1 * 60 * 60 * 1000);
                        endDateView.setText(enddate + "시간");
                        if (enddate <= 1) {
                            endDateView.setText("마감 임박");
                        }
                    } else if (enddate < 0) {
                        enddate = 0;
                        endDateView.setText("종료");
                    }

                    if (item.getMarketType().equals("기장")) {
                        marketTypeView.setText(item.getMarketSubType());
                        moneyTitle1.setText("월 기장료");
                        moneyTitle2.setText("조정료");
                        marketSubView[0].setText("매출 " + item.getBusinessScale() + ", 종업원 "
                                + item.getEmployeeCount() + "명");
                    } else if (item.getMarketType().equals("TAX")) {
                        marketTypeView.setText(item.getMarketSubType());
                        moneyTitle1.setText("제시금액");
                        moneyTitle2.setText("과세금액");
                        if (item.getMarketSubType().equals("상속세")) {
                            moneyEdit1.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                }

                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {
                                    moneyEdit2.setFocusableInTouchMode(false);
                                    moneyBackColor2.setVisibility(View.VISIBLE);

                                }

                                @Override
                                public void afterTextChanged(Editable s) {
                                    if (TextUtils.isEmpty(moneyEdit1.getText())) {
                                        moneyEdit2.setFocusableInTouchMode(true);
                                        moneyBackColor2.setVisibility(View.GONE);
                                    }
                                }
                            });
                            moneyEdit2.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                }

                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {
                                    moneyEdit1.setFocusableInTouchMode(false);
                                    moneyBackColor1.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void afterTextChanged(Editable s) {
                                    if (TextUtils.isEmpty(moneyEdit2.getText())) {
                                        moneyEdit1.setFocusableInTouchMode(true);
                                        moneyBackColor1.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }
                        moneyEdit2.setBackgroundResource(R.drawable.expert_bid_do_persent_box);
                        for (int i = 0; i < item.getAssetMoney().size(); i++) {
                            marketSubView[i].setVisibility(View.VISIBLE);
                            marketSubView[i].setText("자산 " + item.getAssetType().get(i) + ", " + item.getAssetMoney().get(i));
                        }
                    } else if (item.getMarketType().equals("기타")) {
                        marketTypeView.setText("기타");
                        marketSubView[0].setText("상세 내용을 확인해주세요");
                        moneyTitle1.setText("제시금액");
                        layoutComent.setVisibility(View.INVISIBLE);
                        moneyEdit2.setText("");
                        layout_input2.setVisibility(View.GONE);
                    }
                    moneyEdit1.addTextChangedListener(new CustomTextWathcer(moneyEdit1));
                    if (!marketTypeView.getText().equals("기타") && !moneyTitle2.getText().equals("과세금액")) {
                        moneyEdit2.addTextChangedListener(new CustomTextWathcer(moneyEdit2));
                    }
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
                layout.setBackgroundResource(R.drawable.expert_bid_do_input_box);
                layoutComent.setVisibility(View.VISIBLE);
                bid_do_up.setVisibility(View.GONE);
                break;

            case R.id.btn_expert_estimate_bid_do_down_image:
                bid_do_frame.setVisibility(View.INVISIBLE);
                layout.setBackgroundResource(R.color.bid_do_layout_backround);
                if (marketTypeView.getText().equals("기타")) {
                    layoutComent.setVisibility(View.INVISIBLE);
                } else layoutComent.setVisibility(View.GONE);
                bid_do_up.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.expert_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
