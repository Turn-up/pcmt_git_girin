package com.pcm.pcmmanager.nomal.estimate_modify;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.pcm.pcmmanager.MyApplication;
import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.common.CustomTextWathcer;
import com.pcm.pcmmanager.data.CommonResult;
import com.pcm.pcmmanager.data.ExpertEstimateDetail;
import com.pcm.pcmmanager.data.ExpertEstimateDetailResult;
import com.pcm.pcmmanager.expert.ExpertMainActivity;
import com.pcm.pcmmanager.manager.NetworkManager;
import com.pcm.pcmmanager.manager.PropertyManager;
import com.pcm.pcmmanager.nomal.estimate_list.MyEstimateListActivity;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Request;

public class MyEstimateTaxModify extends AppCompatActivity {
    public static final String TAX = PropertyManager.getInstance().getCommonCodeList().get(MyApplication.CODELIST_TAX_POSITION).getCode();

    SeekBar seekBar;
    Spinner marketSubTypeSpinner, address1Spinner, address2Spinner;
    int endDate; //마감일
    ArrayAdapter<String> bAdapter, a1Adapter, a2Adapter; //업종, 주소
    Button taxAdd;
    CheckBox check_tax_land, check_tax_house, check_tax_stock, check_tax_deposit; //재산대상
    EditText marketType2_2, taxContent; //재산시가
    String marketSubType, address1, address2, marketSn, tempAddress1; //내용 , 주소 , 부가내용
    ArrayList<String> assetList;
    LinearLayout asset_layout;
    TextView[] day;
    int color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_estimate_tax_modify);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        marketSn = getIntent().getStringExtra("marketSn");
        setData();

        seekBar = (SeekBar) findViewById(R.id.estimate_modify_tax_seekbar);
        day = new TextView[7];
        day[0] = (TextView) findViewById(R.id.seek_day1);
        day[1] = (TextView) findViewById(R.id.seek_day2);
        day[2] = (TextView) findViewById(R.id.seek_day3);
        day[3] = (TextView) findViewById(R.id.seek_day4);
        day[4] = (TextView) findViewById(R.id.seek_day5);
        day[5] = (TextView) findViewById(R.id.seek_day6);
        day[6] = (TextView) findViewById(R.id.seek_day7);
        color = getResources().getColor(R.color.bid_finish);

        taxAdd = (Button) findViewById(R.id.btn_estimate_modify_tax_add);
        marketSubTypeSpinner = (Spinner) findViewById(R.id.estimate_modify_tax_marketType_spinner);
        address1Spinner = (Spinner) findViewById(R.id.estimate_modify_tax_regionType);
        address2Spinner = (Spinner) findViewById(R.id.estimate_modify_tax_regionSubtype);
        bAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item_text);
        a1Adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_text);
        a2Adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_text);
        asset_layout = (LinearLayout)findViewById(R.id.asset_type_layout);

        marketType2_2 = (EditText) findViewById(R.id.estimate_modify_tax_price);
        taxContent = (EditText) findViewById(R.id.estimate_modify_tax_content);
        marketType2_2.addTextChangedListener(new CustomTextWathcer(marketType2_2));

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                endDate = progress + 1;
                for (int i = 0; i < 7; i++) {
                    day[i].setTextColor(color);
                }
                day[progress].setTextColor(Color.BLACK);

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        assetList = new ArrayList<String>();
        check_tax_land = (CheckBox) findViewById(R.id.check_estimate_modify_tax_asset_land);
        check_tax_house = (CheckBox) findViewById(R.id.check_estimate_modify_tax_asset_house);
        check_tax_stock = (CheckBox) findViewById(R.id.check_estimate_modify_tax_asset_stock);
        check_tax_deposit = (CheckBox) findViewById(R.id.check_estimate_modify_tax_asset_deposit);

        taxAdd = (Button) findViewById(R.id.btn_estimate_modify_tax_add);

        bAdapter.setDropDownViewResource(R.layout.spinner_item_text);
        for (int i = 0; i < PropertyManager.getInstance().getCommonCodeList().get(MyApplication.CODELIST_TAX_POSITION).getList().size(); i++) {
            bAdapter.add(PropertyManager.getInstance().getCommonCodeList().get(MyApplication.CODELIST_TAX_POSITION).getList().get(i).getValue());
        }
        marketSubTypeSpinner.setAdapter(bAdapter);
        marketSubTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(bAdapter.getItem(position).equals("세무조사"))
                    asset_layout.setVisibility(View.GONE);
                else
                    asset_layout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
                address2Spinner.setSelection(a2Adapter.getPosition(address2));
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


        taxAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check_tax_land.isChecked()) {
                    assetList.add(PropertyManager.getInstance().getCommonCodeList().get(MyApplication.CODELIST_ASSET_TYPE_POSITION).getList().get(0).getCode());
                }
                if (check_tax_house.isChecked()) {
                    assetList.add(PropertyManager.getInstance().getCommonCodeList().get(MyApplication.CODELIST_ASSET_TYPE_POSITION).getList().get(1).getCode());
                }
                if (check_tax_stock.isChecked()) {
                    assetList.add(PropertyManager.getInstance().getCommonCodeList().get(MyApplication.CODELIST_ASSET_TYPE_POSITION).getList().get(2).getCode());
                }
                if (check_tax_deposit.isChecked()) {
                    assetList.add(PropertyManager.getInstance().getCommonCodeList().get(MyApplication.CODELIST_ASSET_TYPE_POSITION).getList().get(3).getCode());
                }
                if (TextUtils.isEmpty(marketType2_2.getText().toString())) {
                    Toast.makeText(MyEstimateTaxModify.this, "시가를 입력하세요", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(taxContent.getText().toString())) {
                    Toast.makeText(MyEstimateTaxModify.this, "부가정보를 입력하세요", Toast.LENGTH_SHORT).show();
                } else {
                    String temp = marketType2_2.getText().toString();
                    String content = taxContent.getText().toString();
                    temp = temp.replaceAll(",", "");

                    address1 = PropertyManager.getInstance().getCommonRegionLists().get(address1Spinner.getSelectedItemPosition()).getCode();
                    address2 = PropertyManager.getInstance().getCommonRegionLists().get(address1Spinner.getSelectedItemPosition()).getList().get(address2Spinner.getSelectedItemPosition()).getCode();
                    marketSubType = PropertyManager.getInstance().getCommonCodeList().get(MyApplication.CODELIST_TAX_POSITION).getList().get(marketSubTypeSpinner.getSelectedItemPosition()).getCode();
                    NetworkManager.getInstance().getNomalEstiamteModify(marketSn, TAX, marketSubType, address1, address2, "", "", "", "", assetList, temp
                            , String.valueOf(endDate), content, new NetworkManager.OnResultListener<CommonResult>() {
                                @Override
                                public void onSuccess(Request request, CommonResult result) {
                                    Intent intent = new Intent(MyEstimateTaxModify.this, MyEstimateListActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                                }

                                @Override
                                public void onFail(Request request, IOException exception) {

                                }
                            });
                }
            }
        });
    }

    public void setData() {
        NetworkManager.getInstance().getExpertEstimateDetailResult(marketSn, new NetworkManager.OnResultListener<ExpertEstimateDetailResult>() {
            @Override
            public void onSuccess(Request request, ExpertEstimateDetailResult result) {
                ExpertEstimateDetail items = result.getItem();
                endDate = Integer.valueOf(items.getEnddate()) - 1;
                seekBar.setProgress(endDate);
                day[endDate-1].setTextColor(Color.BLACK);

                taxContent.setText(items.getContent());
                marketType2_2.setText(String.valueOf(items.getNumberAssetMoney()));
                marketSubType = items.getMarketSubType();
                address1 = items.getAddress1();
                address2 = items.getAddress2();
                address1Spinner.setSelection(a1Adapter.getPosition(address1));
                address2Spinner.setSelection(a2Adapter.getPosition(address2));

                marketSubTypeSpinner.setSelection(bAdapter.getPosition(marketSubType));
                for (int i = 0; i < items.getAssetType().size(); i++) {
                    if (items.getAssetType().get(i).equals("토지")) {
                        check_tax_land.setChecked(true);
                    } else if (items.getAssetType().get(i).equals("예금")) {
                        check_tax_deposit.setChecked(true);
                    } else if (items.getAssetType().get(i).equals("주식")) {
                        check_tax_stock.setChecked(true);
                    } else if (items.getAssetType().get(i).equals("주택")) {
                        check_tax_house.setChecked(true);
                    }
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
            Intent intent = new Intent(MyEstimateTaxModify.this, ExpertMainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}