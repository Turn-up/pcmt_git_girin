package com.pcm.pcmmanager.nomal.estimate_list;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.common.expert_detail_info.ExpertDetailInfoActivity;
import com.pcm.pcmmanager.data.EstimateConfirmResult;
import com.pcm.pcmmanager.data.ExpertEstimateDetail;
import com.pcm.pcmmanager.data.ExpertEstimateDetailBidList;
import com.pcm.pcmmanager.data.ExpertEstimateDetailResult;
import com.pcm.pcmmanager.data.ReviewWriteResult;
import com.pcm.pcmmanager.manager.NetworkManager;
import com.pcm.pcmmanager.nomal.NomalMainActivity;

import java.io.IOException;

import okhttp3.Request;

/*
* 내 견적 상세리스트
* */
public class MyEstimateDetailActivity extends AppCompatActivity {

    RecyclerView listView;
    MyEstimateDetailAdapter mAdapter;
    CutomDialog dialog;
    int reviewSn;
    String expertSn, marketSn;
    String eName, ePhoto, eMoney, eMoney2, eSex, status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estimate_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        listView = (RecyclerView) findViewById(R.id.detail_rv_list);
        mAdapter = new MyEstimateDetailAdapter();

        listView.setAdapter(mAdapter);
        listView.setLayoutManager(new LinearLayoutManager(this));

        marketSn = getIntent().getStringExtra("marketSn");
        status = getIntent().getStringExtra("status");

        setData();

        dialog = new CutomDialog(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mAdapter.setOnItemClickLitener(new MyEstimateDetailBidListsViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(View view, ExpertEstimateDetailBidList mList) {
                expertSn = String.valueOf(mList.getExpertSn());
                if (status.equals("낙찰완료")) {
                    if (reviewSn ==0) {
                        ReviewDialog reviewDialog = new ReviewDialog(MyEstimateDetailActivity.this);
                        reviewDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        reviewDialog.show();
                    }else{
                        Intent intent = new Intent(MyEstimateDetailActivity.this, ExpertDetailInfoActivity.class);
                        intent.putExtra("expertSn",expertSn);
                        startActivity(intent);
                    }
                } else {
                    eName = mList.getExpertName();
                    ePhoto = mList.getPhoto();
                    eMoney = String.valueOf(mList.getPrice());
                    eMoney2 = String.valueOf(mList.getPrice2());
                    eSex = mList.getSex();
                    dialog.show();
                }
            }
        });
    }

    private void setData() {
        NetworkManager.getInstance().getExpertEstimateDetailResult(marketSn, new NetworkManager.OnResultListener<ExpertEstimateDetailResult>() {
            @Override
            public void onSuccess(Request request, ExpertEstimateDetailResult result) {
                ExpertEstimateDetail items = result.getItem();
                if (result.getResult() == -1) {
                    Toast.makeText(MyEstimateDetailActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    mAdapter.setDetailItem(items);
                    reviewSn = items.getReviewsn();
                }
            }

            @Override
            public void onFail(Request request, IOException exception) {

            }
        });
    }

    //입찰 목록의 전문가 선택하면 나오는 커스텀 다이얼로그
    class CutomDialog extends Dialog implements View.OnClickListener {

        Button btn_go_expert, btn_do_confirm, btn_cancel;

        public CutomDialog(Context context) {
            super(context);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_estimate_detail_bid_list);

            btn_cancel = (Button) findViewById(R.id.dialog_btn_bid_list_cancel);
            btn_do_confirm = (Button) findViewById(R.id.dialog_btn_bid_list_confirm);
            btn_go_expert = (Button) findViewById(R.id.dialog_btn_bid_list_go_expert);

            btn_cancel.setOnClickListener(this);
            btn_do_confirm.setOnClickListener(this);
            btn_go_expert.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v == btn_cancel) {
                dismiss();
            } else if (v == btn_do_confirm) {
                EstimateConfirmDialog dialog = new EstimateConfirmDialog(MyEstimateDetailActivity.this);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                dismiss();
            } else if (v == btn_go_expert) {
                Intent intent = new Intent(MyEstimateDetailActivity.this, ExpertDetailInfoActivity.class);
                intent.putExtra("expertSn", expertSn);
                startActivity(intent);
                dismiss();
            }
        }

        //입찰 목록의 전문가 선택하고 낙찰하기 누르면 나오는 커스텀 다이얼로그
        class EstimateConfirmDialog extends Dialog implements View.OnClickListener {

            ImageButton btn_do_confirm_ok, btn_do_confirm_cancel;
            ImageView profile_image;
            TextView name, month_money, modify_money;

            public EstimateConfirmDialog(Context context) {
                super(context);
                requestWindowFeature(Window.FEATURE_NO_TITLE);
                setContentView(R.layout.dialog_estimate_confirm);

                btn_do_confirm_ok = (ImageButton) findViewById(R.id.dialog_estimate_confirm_confirm_ok);
                btn_do_confirm_ok.setOnClickListener(this);

                btn_do_confirm_cancel = (ImageButton) findViewById(R.id.dialog_estimate_confirm_confirm_cancel);
                btn_do_confirm_cancel.setOnClickListener(this);
                profile_image = (ImageView) findViewById(R.id.dialog_estimate_confirm_expert_image);
                name = (TextView) findViewById(R.id.dialog_estimate_confirm_expert_name);
                month_money = (TextView) findViewById(R.id.dialog_estimate_confirm_month_money);
                modify_money = (TextView) findViewById(R.id.dialog_estimate_confirm_modify_money);


                if (TextUtils.isEmpty(ePhoto)) {
                    if (eSex.equals("남자")) profile_image.setImageResource(R.drawable.semooman_icon);
                    else if (eSex.equals("여자"))
                        profile_image.setImageResource(R.drawable.semoogirl_icon);
                } else {
                    Glide.with(getContext()).load(ePhoto).into(profile_image);
                }
                name.setText(eName);
                month_money.setText(eMoney);
                modify_money.setText(eMoney2);
            }

            @Override
            public void onClick(View v) {
                if (v == btn_do_confirm_ok) {
                    NetworkManager.getInstance().getEstimateConfirm(marketSn, String.valueOf(expertSn), new NetworkManager.OnResultListener<EstimateConfirmResult>() {
                        @Override
                        public void onSuccess(Request request, EstimateConfirmResult result) {
                            if(result.getResult() == -1){
                                Toast.makeText(getContext(), result.getMassage(), Toast.LENGTH_SHORT).show();
                            }else {
                                ThankYouImage thankYouImage = new ThankYouImage(MyEstimateDetailActivity.this);
                                thankYouImage.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                thankYouImage.show();
                            }
                        }

                        @Override
                        public void onFail(Request request, IOException exception) {
                            //낙찰 처리가 안됐을때
                        }
                    });
                    dismiss();
                }
                if (v == btn_do_confirm_cancel) {
                    dismiss();
                }
            }
        }

        //최종 낙찰완료하면 나오는 이미지 팝업
        class ThankYouImage extends Dialog {
            public ThankYouImage(Context context) {
                super(context);
                requestWindowFeature(Window.FEATURE_NO_TITLE);
                setContentView(R.layout.dialog_thank_you_image);

                Handler handler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        dismiss();
                        Intent intent = new Intent(MyEstimateDetailActivity.this, MyEstimateListActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                };
                handler.sendEmptyMessageDelayed(0, 2000);
            }

        }
    }
/*리뷰*/
    class ReviewDialog extends Dialog {
        RatingBar rating;
        EditText review_write;
        ImageButton review_add_btn, review_cancel_btn;
        String rating_value;

        public ReviewDialog(final Context context) {
            super(context);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_expert_review_do);
            rating = (RatingBar) findViewById(R.id.expert_review_rating);
            review_write = (EditText) findViewById(R.id.expert_review_content);
            review_add_btn = (ImageButton) findViewById(R.id.expert_review_do_btn);
            review_cancel_btn = (ImageButton) findViewById(R.id.expert_review_cancel_btn);
            rating.setRating(3.0f);

            review_add_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (TextUtils.isEmpty(review_write.getText().toString())) {
                        Toast.makeText(getContext(), "리뷰를 작성해주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        NetworkManager.getInstance().getReviewWrite(marketSn, expertSn, String.valueOf(rating.getRating()), review_write.getText().toString(), new NetworkManager.OnResultListener<ReviewWriteResult>() {
                            @Override
                            public void onSuccess(Request request, ReviewWriteResult result) {
                                if(result.getResult() == -1){
                                    Toast.makeText(getContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
                                }else {
                                    dismiss();
                                    Toast.makeText(getContext(), "리뷰를 작성해주셔서 감사합니다.", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFail(Request request, IOException exception) {

                            }
                        });

                    }
                }
            });
            review_cancel_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
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
            Intent intent = new Intent(MyEstimateDetailActivity.this, NomalMainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}