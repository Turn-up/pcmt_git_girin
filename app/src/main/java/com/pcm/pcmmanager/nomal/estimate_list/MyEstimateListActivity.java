package com.pcm.pcmmanager.nomal.estimate_list;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.data.MyEsitmateList;
import com.pcm.pcmmanager.data.MyEstimateListResult;
import com.pcm.pcmmanager.manager.NetworkManager;
import com.pcm.pcmmanager.nomal.NomalMainActivity;
import com.pcm.pcmmanager.nomal.estimate_modify.MyEstimateEntryModify;
import com.pcm.pcmmanager.nomal.estimate_modify.MyEstimateEtcModify;
import com.pcm.pcmmanager.nomal.estimate_modify.MyEstimateTaxModify;

import java.io.IOException;

import okhttp3.Request;

public class MyEstimateListActivity extends AppCompatActivity {

    public static final String PAGESIZE = "10";
    String last_marketSn,marketSn,markettype,status;
    RecyclerView listView;
    MyEstimateListAdapter mAdapter;
    LinearLayoutManager mLayoutManager;
    Boolean isLast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estimate_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        last_marketSn="0";
        mLayoutManager = new LinearLayoutManager(this);

        mAdapter = new MyEstimateListAdapter();
        listView = (RecyclerView) findViewById(R.id.rv_list);
        mAdapter.setOnItemClickListener(new MyEstimateListViewHolder.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, MyEsitmateList myEsitmateList) {
                marketSn = String.valueOf(myEsitmateList.getMarketSn());
                markettype = myEsitmateList.getMarketType();
                status = myEsitmateList.getStatus();
                if(myEsitmateList.getStatus().equals("입찰전")){
                    ModifyDialog modifyDialog = new ModifyDialog(MyEstimateListActivity.this);
                    modifyDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    modifyDialog.show();

                }else {
                    Intent intent = new Intent(MyEstimateListActivity.this, MyEstimateDetailActivity.class);
                    intent.putExtra("marketSn",marketSn);
                    intent.putExtra("status", status);
                    startActivity(intent);
                }



            }
        });
        listView.setAdapter(mAdapter);
        listView.setLayoutManager(mLayoutManager);

        isLast=false;
        listView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if(isLast && newState == RecyclerView.SCROLL_STATE_IDLE){
                    getMoreData();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int totalCount = mAdapter.getItemCount();
                int lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
                if (totalCount > 0 && lastVisibleItem >= totalCount - 1){
                    isLast =true;
                }else
                    isLast= false;
            }
        });
        setData();
        //recycler view 구현
    }
    //입찰 수정 다이얼로그
    class ModifyDialog extends Dialog implements View.OnClickListener {

        Button estimate_detail_btn, estimate_modify_btn, btn_cancel;

        public ModifyDialog(Context context) {
            super(context);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_estimate_modify);

            btn_cancel = (Button) findViewById(R.id.dialog_estimate_modify_cancel);
            estimate_modify_btn = (Button) findViewById(R.id.dialog_estimate_modify_btn);
            estimate_detail_btn = (Button) findViewById(R.id.dialog_estimate_modify_detail_view);

            btn_cancel.setOnClickListener(this);
            estimate_modify_btn.setOnClickListener(this);
            estimate_detail_btn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v == btn_cancel) {
                dismiss();
            } else if (v == estimate_modify_btn) {
                if(markettype.equals("기장")){
                    Intent intent = new Intent(MyEstimateListActivity.this, MyEstimateEntryModify.class);
                    intent.putExtra("marketSn",marketSn);
                    startActivity(intent);
                    dismiss();
                }else if(markettype.equals("TAX")){
                    Intent intent = new Intent(MyEstimateListActivity.this, MyEstimateTaxModify.class);
                    intent.putExtra("marketSn",marketSn);
                    startActivity(intent);
                    dismiss();
                }else{
                    Intent intent = new Intent(MyEstimateListActivity.this, MyEstimateEtcModify.class);
                    intent.putExtra("marketSn",marketSn);
                    startActivity(intent);
                    dismiss();
                }
            } else if (v == estimate_detail_btn) {
                Intent intent = new Intent(MyEstimateListActivity.this, MyEstimateDetailActivity.class);
                intent.putExtra("marketSn",marketSn);
                intent.putExtra("status", status);
                startActivity(intent);
                dismiss();
            }
        }
    }
    private boolean isMoreData = false;
    public void getMoreData(){
        if(!isMoreData && mAdapter.isMoreData()){
            isMoreData = true;
            final int sn = mAdapter.getLastSn(mAdapter.getItemCount()-1);
            try {
                NetworkManager.getInstance().getNomalEstiamteList(PAGESIZE, String.valueOf(sn),new NetworkManager.OnResultListener<MyEstimateListResult>() {
                    @Override
                    public void onSuccess(Request request, MyEstimateListResult result) {
                        mAdapter.addAll(result.getList());
                        isMoreData = false;
                    }

                    @Override
                    public void onFail(Request request, IOException exception) {

                    }
                });
            }catch (Exception e){
                e.printStackTrace();
                isMoreData = false;

            }

        }
    }

    private void setData() {
        mAdapter.clear();
        NetworkManager.getInstance().getNomalEstiamteList(PAGESIZE, "0", new NetworkManager.OnResultListener<MyEstimateListResult>() {
            @Override
            public void onSuccess(Request request, MyEstimateListResult result) {
                mAdapter.clear();
                mAdapter.addAll(result.getList());
                mAdapter.setTotalCount(result.getTotalCount());
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
        if(id == android.R.id.home){
            finish();
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_home) {
            Intent intent = new Intent(this, NomalMainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
