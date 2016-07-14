package com.pcm.pcmmanager.qna.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.pcm.pcmmanager.MyApplication;
import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.data.QnaKeywordSearchResult;
import com.pcm.pcmmanager.data.QnaList;
import com.pcm.pcmmanager.data.QnaListResult;
import com.pcm.pcmmanager.expert.ExpertMainActivity;
import com.pcm.pcmmanager.manager.NetworkManager;
import com.pcm.pcmmanager.nomal.NomalMainActivity;
import com.pcm.pcmmanager.qna.ask.QnaAskActivity;
import com.pcm.pcmmanager.qna.detail.QnaDetailActivity;

import java.io.IOException;

import okhttp3.Request;

public class QnaListActivity extends AppCompatActivity {
    EditText qnaKeywordText;
    ImageButton qnaKeywordButton;
    Button qnaDo;
    RecyclerView rv_list;
    QnaAdapter mAdapter;
    Boolean isLast;
    LinearLayoutManager mLayoutManager;
    String last_qnaSn;

    public static final String PAGESIZE = "10";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qna_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        last_qnaSn = "0";
        mLayoutManager = new LinearLayoutManager(this);

        qnaDo = (Button) findViewById(R.id.btn_qna_do);
        qnaKeywordText = (EditText) findViewById(R.id.edittext_qna_search_text);
        qnaKeywordButton = (ImageButton) findViewById(R.id.qna_list_keyword_search_imagebtn);
        rv_list = (RecyclerView) findViewById(R.id.qna_rv_list);
        mAdapter = new QnaAdapter();
        mAdapter.setOnItemClickListener(new QnaViewHolder.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, QnaList qnaList) {
                if (qnaList.getSecretyn()) {
                    if (MyApplication.getUserType().equals("Users")) {
                        if (qnaList.getReadyn()) {
                            Intent intent = new Intent(QnaListActivity.this, QnaDetailActivity.class);
                            intent.putExtra("qnaSn", "" + qnaList.getQnasn());
                            startActivity(intent);
                        } else
                            Toast.makeText(QnaListActivity.this, "비밀글 입니다.", Toast.LENGTH_SHORT).show();
                    } else if (MyApplication.getUserType().equals("Experts")) {
                        Intent intent = new Intent(QnaListActivity.this, QnaDetailActivity.class);
                        intent.putExtra("qnaSn", "" + qnaList.getQnasn());
                        startActivity(intent);
                    }
                } else {
                    Intent intent = new Intent(QnaListActivity.this, QnaDetailActivity.class);
                    intent.putExtra("qnaSn", "" + qnaList.getQnasn());
                    startActivity(intent);
                }
            }
        });
        rv_list.setLayoutManager(mLayoutManager);
        rv_list.setAdapter(mAdapter);
        isLast = false;
        rv_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (isLast && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    getMoreData();
                }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int totalCount = mAdapter.getItemCount();
                int lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
                if (totalCount > 0 && lastVisibleItem >= totalCount - 1) {
                    isLast = true;
                } else
                    isLast = false;
            }
        });
        if (MyApplication.getUserType().equals("Users")) {
            qnaDo.setVisibility(View.VISIBLE);
            qnaDo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(QnaListActivity.this, QnaAskActivity.class);
                    startActivity(intent);
                }
            });
        }
        qnaKeywordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(qnaKeywordText.getText()))
                    Toast.makeText(QnaListActivity.this, "키워드를 입력하세요", Toast.LENGTH_SHORT).show();
                else {
                    NetworkManager.getInstance().getQnakeywordSearch(PAGESIZE, "0", qnaKeywordText.getText().toString(), new NetworkManager.OnResultListener<QnaKeywordSearchResult>() {
                        @Override
                        public void onSuccess(Request request, QnaKeywordSearchResult result) {
                            if (result.getResult() == -1) {
                                Toast.makeText(QnaListActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                InputMethodManager mInputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                                mInputMethodManager.hideSoftInputFromWindow(qnaKeywordText.getWindowToken(), 0);
                                mAdapter.clear();
                                mAdapter.addAll(result.getKeywordSearchList());
                                mAdapter.setTotalCount(result.getTotalcount());
                            }
                        }

                        @Override
                        public void onFail(Request request, IOException exception) {

                        }
                    });
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        setData();
    }

    private boolean isMoreData = false;

    private void getMoreData() {
        if (!isMoreData && mAdapter.isMoreData()) {
            isMoreData = true;
            final int sn = mAdapter.getLastSn(mAdapter.getItemCount() - 1);
            try {
                NetworkManager.getInstance().getQnaList(PAGESIZE, String.valueOf(sn), new NetworkManager.OnResultListener<QnaListResult>() {
                    @Override
                    public void onSuccess(Request request, QnaListResult result) {
                        mAdapter.addAll(result.getQnaList());
                        isMoreData = false;
                    }

                    @Override
                    public void onFail(Request request, IOException exception) {

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                isMoreData = false;

            }

        }
    }

    private void setData() {
        mAdapter.clear();
        NetworkManager.getInstance().getQnaList(PAGESIZE, "0", new NetworkManager.OnResultListener<QnaListResult>() {
            @Override
            public void onSuccess(Request request, QnaListResult result) {
                if (result.getResult() == -1) {
                    Toast.makeText(QnaListActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    mAdapter.clear();
                    mAdapter.addAll(result.getQnaList());
                    mAdapter.setTotalCount(result.getTotalcount());
                }
            }

            @Override
            public void onFail(Request request, IOException exception) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
