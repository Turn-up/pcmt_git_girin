package com.pcm.pcmmanager.nomal.qna_list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.pcm.pcmmanager.MyApplication;
import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.data.QnaMyList;
import com.pcm.pcmmanager.data.QnaMyListResult;
import com.pcm.pcmmanager.expert.ExpertMainActivity;
import com.pcm.pcmmanager.manager.NetworkManager;
import com.pcm.pcmmanager.nomal.NomalMainActivity;
import com.pcm.pcmmanager.qna.detail.QnaDetailActivity;

import java.io.IOException;

import okhttp3.Request;

public class MyQnaListActivity extends AppCompatActivity {

    RecyclerView rv_list;
    MyQnaListAdapter mAdapter;
    Boolean isLast;
    LinearLayoutManager mLayoutManager;
    String last_qnaSn;
    public static final String PAGESIZE = "10";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_qna_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        last_qnaSn = "0";
        mLayoutManager = new LinearLayoutManager(this);

        rv_list = (RecyclerView) findViewById(R.id.my_qna_rv_list);
        mAdapter = new MyQnaListAdapter();
        mAdapter.setOnItemClickListener(new MyQnaListViewHolder.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, QnaMyList qnaMyList) {
                Intent intent = new Intent(MyQnaListActivity.this, QnaDetailActivity.class);
                intent.putExtra("qnaSn", String.valueOf(qnaMyList.getQnasn()));
                intent.putExtra("myList", "true");
                startActivity(intent);
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
                NetworkManager.getInstance().getMyQnaList(PAGESIZE, String.valueOf(sn), new NetworkManager.OnResultListener<QnaMyListResult>() {
                    @Override
                    public void onSuccess(Request request, QnaMyListResult result) {
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
        NetworkManager.getInstance().getMyQnaList(PAGESIZE, "0", new NetworkManager.OnResultListener<QnaMyListResult>() {
            @Override
            public void onSuccess(Request request, QnaMyListResult result) {
                if (result.getResult() == -1) {
                    Toast.makeText(MyQnaListActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    mAdapter.clear();
                    mAdapter.addAll(result.getQnaList());
                    mAdapter.setTotalCount(result.getTotlacount());
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
