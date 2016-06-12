package com.pcm.pcmmanager.expert.auction;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.pcm.pcmmanager.MyApplication;
import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.data.ExpertEstimateList;
import com.pcm.pcmmanager.data.ExpertEstimateResult;
import com.pcm.pcmmanager.expert.ExpertMainActivity;
import com.pcm.pcmmanager.manager.NetworkManager;
import com.pcm.pcmmanager.nomal.NomalMainActivity;

import java.io.IOException;

import okhttp3.Request;

public class AuctionSearchListActivity extends AppCompatActivity {

    public static final String PAGE_SIZE="10";

    RecyclerView recyclerView;
    AuctionAdapter mAdapter;
    String marketType, marketSubType, regionType, regionSubType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auction_search_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        recyclerView = (RecyclerView) findViewById(R.id.expert_auction_search_recycler);
        mAdapter = new AuctionAdapter();
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.setOnItemClickListener(new AuctionViewHolder.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, ExpertEstimateList expertEstimateList) {

            }
        });
        marketType = getIntent().getStringExtra("markettype");
        marketSubType = getIntent().getStringExtra("marketsubtype");
        regionType = getIntent().getStringExtra("regiontype");
        regionSubType = getIntent().getStringExtra("regionsubtype");

    }

    @Override
    protected void onResume() {
        super.onResume();
        setData(marketType, marketSubType, regionType, regionSubType);
    }

    public void setData(String marketType, String marketSubType, String regionType, String regionSubType) {
        NetworkManager.getInstance().getExpertEstimateSearch(PAGE_SIZE, "0", marketType, marketSubType, regionType, regionSubType, new NetworkManager.OnResultListener<ExpertEstimateResult>() {
            @Override
            public void onSuccess(Request request, ExpertEstimateResult result) {
                mAdapter.addAll(result.getEstimateList());
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