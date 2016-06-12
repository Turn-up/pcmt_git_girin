package com.pcm.pcmmanager.expert.point;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.data.PointListResult;
import com.pcm.pcmmanager.manager.NetworkManager;

import java.io.IOException;

import okhttp3.Request;

public class PointListActivity extends AppCompatActivity {
    public static final String POINT_PAGESIZE = "10";
    public static final String POINT_LAST_POINTSN = "0";

    RecyclerView recyclerView;
    PointListAdapter mAdapter;
    TextView pointView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pointView = (TextView) findViewById(R.id.point_value);
        recyclerView = (RecyclerView) findViewById(R.id.point_rv_list);
        mAdapter = new PointListAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
        setData();
    }

    private void setData() {
        mAdapter.clear();
        NetworkManager.getInstance().getMileageList(POINT_PAGESIZE, POINT_LAST_POINTSN, new NetworkManager.OnResultListener<PointListResult>() {
            @Override
            public void onSuccess(Request request, PointListResult result) {
                if (result.getResult() == -1) {
                    Toast.makeText(PointListActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    mAdapter.addAll(result.getPointList());
                    pointView.setText(""+result.getRemainMileage());
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
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

