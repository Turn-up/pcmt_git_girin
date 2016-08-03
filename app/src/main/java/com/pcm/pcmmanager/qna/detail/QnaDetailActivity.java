package com.pcm.pcmmanager.qna.detail;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.pcm.pcmmanager.MyApplication;
import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.common.expert_detail_info.ExpertDetailInfoActivity;
import com.pcm.pcmmanager.data.ExpertCheckResult;
import com.pcm.pcmmanager.data.QnaDetailResult;
import com.pcm.pcmmanager.data.QnaDetailReviewList;
import com.pcm.pcmmanager.data.QnaReviewResult;
import com.pcm.pcmmanager.expert.ExpertMainActivity;
import com.pcm.pcmmanager.manager.NetworkManager;
import com.pcm.pcmmanager.nomal.NomalMainActivity;

import java.io.IOException;

import okhttp3.Request;

public class QnaDetailActivity extends AppCompatActivity {

    RecyclerView rv_list;
    QnaDetailAdapter mAdapter;
    LinearLayoutManager mLayoutManager;
    Boolean isLast;
    EditText reviewText;
    ImageButton reviewButton;
    String qnaSn;
    InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qna_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);

        mLayoutManager = new LinearLayoutManager(this);
        reviewText = (EditText) findViewById(R.id.qna_detail_input_text);
        reviewButton = (ImageButton) findViewById(R.id.qna_detail_image_btn);
        if(MyApplication.getUserType().equals("Users")){
            reviewText.setVisibility(View.GONE);
            reviewButton.setVisibility(View.GONE);
        }else{
            reviewButton.setVisibility(View.VISIBLE);
            reviewText.setVisibility(View.VISIBLE);
        }
        rv_list = (RecyclerView)findViewById(R.id.qna_detail_rv_list);
        mAdapter = new QnaDetailAdapter();

        rv_list.setAdapter(mAdapter);
        rv_list.setLayoutManager(mLayoutManager);
        isLast = false;

        reviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(reviewText.getText().toString())){
                    ReviewConfirmDialog();
                }else{
                    Toast.makeText(QnaDetailActivity.this, "댓글을 입력하세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mAdapter.setOnItemClickLitener(new QnaDetailReviewViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(View view, QnaDetailReviewList mList) {

                Intent intent = new Intent(QnaDetailActivity.this, ExpertDetailInfoActivity.class);
                intent.putExtra("expertSn",String.valueOf(mList.getExpertsn()));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setData();
    }
    private void setData() {
        qnaSn = getIntent().getStringExtra("qnaSn");
        NetworkManager.getInstance().getQnaDetail(qnaSn, new NetworkManager.OnResultListener<QnaDetailResult>() {
            @Override
            public void onSuccess(Request request, QnaDetailResult result) {
                if(result.getResult() == -1){
                    Toast.makeText(QnaDetailActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                }else{
                    if(!TextUtils.isEmpty(getIntent().getStringExtra("myList"))){
                        mAdapter.setQnaDetailData(result.getQnaDetail(),qnaSn,getIntent().getStringExtra("myList"));
                    }else{
                        mAdapter.setQnaDetailData(result.getQnaDetail(),qnaSn);
                    }
                }
            }
            @Override
            public void onFail(Request request, IOException exception) {

            }
        });
        /*전문가 체크*/
        NetworkManager.getInstance().getExpertCheck(new NetworkManager.OnResultListener<ExpertCheckResult>() {
            @Override
            public void onSuccess(Request request, ExpertCheckResult result) {
                if(result.getCheck()){
                    reviewButton.setVisibility(View.VISIBLE);
                    reviewText.setVisibility(View.VISIBLE);
                }else{
                    reviewButton.setVisibility(View.GONE);
                    reviewText.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFail(Request request, IOException exception) {

            }
        });
    }
    private void ReviewConfirmDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        // AlertDialog 셋팅
        alertDialogBuilder
                .setMessage("댓글을 작성하시겠습니까?")
                .setCancelable(false)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        qnaSn = getIntent().getStringExtra("qnaSn");
                        NetworkManager.getInstance().getQnaReviewAdd(qnaSn,reviewText.getText().toString(),new NetworkManager.OnResultListener<QnaReviewResult>() {
                            @Override
                            public void onSuccess(Request request, QnaReviewResult result) {
                                if (result.getResult() == -1)
                                    Toast.makeText(QnaDetailActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                                else {
                                    Toast.makeText(QnaDetailActivity.this, "댓글 작성 완료", Toast.LENGTH_SHORT).show();
                                    imm.hideSoftInputFromWindow(reviewText.getWindowToken(),0);
                                    reviewText.setText("");
                                    setData();

                                }
                            }
                            @Override
                            public void onFail(Request request, IOException exception) {

                            }
                        });

                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // 다이얼로그를 취소한다
                        dialog.cancel();
                    }
                });
        // 다이얼로그 보여주기
        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.show();
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
            if (MyApplication.getUserType().equals("Users")) {
                Intent intent = new Intent(this, NomalMainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            } else if (MyApplication.getUserType().equals("Experts")) {
                Intent intent = new Intent(this, ExpertMainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
