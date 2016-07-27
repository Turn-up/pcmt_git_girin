package com.pcm.pcmmanager.common.faq;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.pcm.pcmmanager.MyApplication;
import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.data.FaqList;
import com.pcm.pcmmanager.data.FaqListResult;
import com.pcm.pcmmanager.expert.ExpertMainActivity;
import com.pcm.pcmmanager.manager.NetworkManager;
import com.pcm.pcmmanager.nomal.NomalMainActivity;

import java.io.IOException;

import okhttp3.Request;

public class FaqActivity extends AppCompatActivity {
    public static final String PAGE_SIZE = "10";
    RecyclerView recyclerView;
    FaqAdapter mAdapter;
    Boolean isLast;
    LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mLayoutManager = new LinearLayoutManager(this);
        recyclerView = (RecyclerView) findViewById(R.id.faq_rv_list);
        mAdapter = new FaqAdapter();
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(mLayoutManager);
        mAdapter.setOnItemClickListener(new FaqViewHolder.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, FaqList faqList) {
                TextView faqContentText = (TextView) view.findViewById(R.id.faq_content_text);
                TextView faqUnderbar = (TextView) view.findViewById(R.id.faq_underbar);
                if (faqContentText.getVisibility() == View.VISIBLE) {
                    faqContentText.setVisibility(View.GONE);
                    faqUnderbar.setVisibility(View.GONE);
                }
                else {
                    faqContentText.setVisibility(View.VISIBLE);
                    faqUnderbar.setVisibility(View.VISIBLE);
                }
            }
        });
        isLast = false;
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (isLast && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    getMoreData();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int totalCount = mAdapter.getItemCount();
                int lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
                if (totalCount > 0 && lastVisibleItem >= totalCount - 1) {
                    isLast = true;
                } else
                    isLast = false;
            }
        });
        setData();

    }

    private boolean isMoreData = false;

    public void getMoreData() {
        if (!isMoreData && mAdapter.isMoreData()) {
            isMoreData = true;
            final int sn = mAdapter.getLastSn(mAdapter.getItemCount() - 1);
            try {
                NetworkManager.getInstance().getFaqList(PAGE_SIZE, String.valueOf(sn), new NetworkManager.OnResultListener<FaqListResult>() {
                    @Override
                    public void onSuccess(Request request, FaqListResult result) {
                        mAdapter.addAll(result.getFaqList());
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

    public void setData() {
        NetworkManager.getInstance().getFaqList(PAGE_SIZE, "0", new NetworkManager.OnResultListener<FaqListResult>() {
            @Override
            public void onSuccess(Request request, final FaqListResult result) {
                mAdapter.clear();
                if (result.getResult() == -1) {
                    Toast.makeText(FaqActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    mAdapter.setTotalCount(result.getTotalcount());
                    mAdapter.addAll(result.getFaqList());
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