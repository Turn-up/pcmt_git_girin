package com.pcm.pcmmanager.common.notice.text;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.pcm.pcmmanager.BaseActivity;
import com.pcm.pcmmanager.MyApplication;
import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.data.NoticeList;
import com.pcm.pcmmanager.data.NoticeListResult;
import com.pcm.pcmmanager.manager.NetworkManager;
import com.pcm.pcmmanager.manager.PropertyManager;

import java.io.IOException;

import okhttp3.Request;

public class NoticeListActivity extends BaseActivity {

    RecyclerView recyclerView;
    NoticeListAdapter mAdapter;
    public static final String NOTICE_TEXT_STATUS = PropertyManager.getInstance().getCommonCodeList().get(MyApplication.CODELIST_NOTICE_STATE_POSITION).getList().get(0).getCode();
    public static final String NOTICE_TEXT_PAGESIZE = "10";
    public static final String NOTICE_TEXT_LAST_NOTICESN = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        recyclerView = (RecyclerView) findViewById(R.id.notice_rv_list);
        mAdapter = new NoticeListAdapter();
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter.setOnItemClickListener(new NoticeListViewHolder.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, NoticeList noticeList) {
                Intent intent = new Intent(NoticeListActivity.this, NoticeTextDetailActivity.class);
                intent.putExtra("title", noticeList.getTitle());
                intent.putExtra("content", noticeList.getContent());
                intent.putExtra("date", noticeList.getRegdate());
                startActivity(intent);

            }
        });
        setData();
    }

    public void setData() {
        mAdapter.clear();
        NetworkManager.getInstance().getNoticeList(NOTICE_TEXT_PAGESIZE, NOTICE_TEXT_LAST_NOTICESN, NOTICE_TEXT_STATUS, new NetworkManager.OnResultListener<NoticeListResult>() {
            @Override
            public void onSuccess(Request request, NoticeListResult result) {
                mAdapter.addAll(result.getNoticeList());
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
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
