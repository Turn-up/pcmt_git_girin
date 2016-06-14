package com.pcm.pcmmanager.nomal.estimate_modify;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;

import com.pcm.pcmmanager.MyApplication;
import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.data.ExpertEstimateDetail;
import com.pcm.pcmmanager.data.ExpertEstimateDetailResult;
import com.pcm.pcmmanager.data.MyEstimateEditModifyResult;
import com.pcm.pcmmanager.expert.ExpertMainActivity;
import com.pcm.pcmmanager.manager.NetworkManager;
import com.pcm.pcmmanager.manager.PropertyManager;
import com.pcm.pcmmanager.nomal.estimate_list.MyEstimateListActivity;

import java.io.IOException;

import okhttp3.Request;

public class MyEstimateEtcModify extends AppCompatActivity {
    public static final String ESTIMATE_REQUEST_ETC_CODE = PropertyManager.getInstance().getCommonCodeList().get(MyApplication.CODELIST_ETC_POSITION).getCode();
    SeekBar seekBar;
    String content, address1, address2, tempAddress1;
    EditText etcContent;
    ArrayAdapter<String> a1Adapter, a2Adapter; //업종, 주소
    Spinner address1Spinner, address2Spinner;
    String endDate = "1", marketSn; // 마감일
    Button etcAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_estimate_etc_modify);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        address1Spinner = (Spinner) findViewById(R.id.estimate_modify_etc_address1_spinner);
        address2Spinner = (Spinner) findViewById(R.id.estimate_modify_etc_address2_spinner);
        a1Adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_text);
        a2Adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_text);
        etcAdd = (Button) findViewById(R.id.estimate_modify_etc_btn);

        marketSn = getIntent().getStringExtra("marketSn");
        setData();
        a1Adapter.setDropDownViewResource(R.layout.spinner_item_text);
        for (int i = 0; i < PropertyManager.getInstance().getCommonRegionLists().size(); i++) {
            a1Adapter.add(PropertyManager.getInstance().getCommonRegionLists().get(i).getValue());
        }
        address1Spinner.setAdapter(a1Adapter);
        address1Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                address1 = PropertyManager.getInstance().getCommonRegionLists().get(position).getCode();
                tempAddress1 = a1Adapter.getItem(position);
                a2Adapter.clear();
                for (int i = 0; i < PropertyManager.getInstance().getCommonRegionLists().get(position).getList().size(); i++) {
                    a2Adapter.add(PropertyManager.getInstance().getCommonRegionLists().get(position).getList().get(i).getValue());
                }
                address2Spinner.setSelection(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        a2Adapter.setDropDownViewResource(R.layout.spinner_item_text);
        address2Spinner.setAdapter(a2Adapter);
        address2Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                address2 = PropertyManager.getInstance().getCommonRegionLists().get(a1Adapter.getPosition(tempAddress1)).getList().get(position).getCode();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        etcContent = (EditText) findViewById(R.id.estimate_modify_etc_content);
        seekBar = (SeekBar) findViewById(R.id.etc_seekbar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                endDate = String.valueOf(progress + 1);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });



        etcAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content = etcContent.getText().toString();
                address1 = PropertyManager.getInstance().getCommonRegionLists().get(address1Spinner.getSelectedItemPosition()).getCode();
                address2 = PropertyManager.getInstance().getCommonRegionLists().get(address1Spinner.getSelectedItemPosition()).getList().get(address2Spinner.getSelectedItemPosition()).getCode();
                NetworkManager.getInstance().getNomalEstiamteModify(marketSn, ESTIMATE_REQUEST_ETC_CODE, "", address1, address2, "", "", "", "", null,
                        "", endDate, content, new NetworkManager.OnResultListener<MyEstimateEditModifyResult>() {
                            @Override
                            public void onSuccess(Request request, MyEstimateEditModifyResult result) {
                                Intent intent = new Intent(MyEstimateEtcModify.this, MyEstimateListActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }

                            @Override
                            public void onFail(Request request, IOException exception) {

                            }
                        });
            }
        });


    }

    private void setData() {
        NetworkManager.getInstance().getExpertEstimateDetailResult(marketSn, new NetworkManager.OnResultListener<ExpertEstimateDetailResult>() {
            @Override
            public void onSuccess(Request request, ExpertEstimateDetailResult result) {
                ExpertEstimateDetail items = result.getItem();
                endDate = items.getEnddate();
                seekBar.setProgress(Integer.valueOf(endDate) - 1);
                etcContent.setText(items.getContent());
                address1 = items.getAddress1();
                address2 = items.getAddress2();
                address1Spinner.setSelection(a1Adapter.getPosition(address1));
                address2Spinner.setSelection(a2Adapter.getPosition(address2));
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
            Intent intent = new Intent(MyEstimateEtcModify.this, ExpertMainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}