package com.pcm.pcmmanager.nomal.estimate_request;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioGroup;

import com.pcm.pcmmanager.BaseActivity;
import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.nomal.NomalMainActivity;

public class EstimateRequestActivity extends BaseActivity {

    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estimate_request);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        radioGroup = (RadioGroup) findViewById(R.id.entry_radiogroup);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new EstimateRequestEntryFragment())
                    .commit();
        }
 /*사업자 구분*/
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.btn_estimate_entry:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, new EstimateRequestEntryFragment())
                                .commit();
                        break;
                    case R.id.btn_estimate_tax:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, new EstimateRequestTaxFragment())
                                .commit();
                        break;
                    case R.id.btn_estimate_etc:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, new EstimateRequestEtcFragment())
                                .commit();
                        break;
                }
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
            return true;
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
