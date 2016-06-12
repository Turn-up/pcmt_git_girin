package com.pcm.pcmmanager.nomal.estimate_modify;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;

import com.pcm.pcmmanager.MyApplication;
import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.common.CustomTextWathcer;
import com.pcm.pcmmanager.data.ExpertEstimateDetail;
import com.pcm.pcmmanager.data.ExpertEstimateDetailResult;
import com.pcm.pcmmanager.data.MyEstimateEditModifyResult;
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
        taxAdd = (Button) findViewById(R.id.btn_estimate_modify_tax_add);
        marketSubTypeSpinner = (Spinner) findViewById(R.id.estimate_modify_tax_marketType_spinner);
        address1Spinner = (Spinner) findViewById(R.id.estimate_modify_tax_regionType);
        address2Spinner = (Spinner) findViewById(R.id.estimate_modify_tax_regionSubtype);
        bAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item_text);
        a1Adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_text);
        a2Adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_text);

        marketType2_2 = (EditText) findViewById(R.id.estimate_modify_tax_price);
        taxContent = (EditText) findViewById(R.id.estimate_modify_tax_content);
        marketType2_2.addTextChangedListener(new CustomTextWathcer(marketType2_2));

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                endDate = progress + 1;
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
                marketSubType = PropertyManager.getInstance().getCommonCodeList().get(MyApplication.CODELIST_TAX_POSITION).getList().get(position).getCode();
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
                String temp = marketType2_2.getText().toString();
                String content = taxContent.getText().toString();
                temp = temp.replaceAll(",", "");
                if (check_tax_land.isChecked()) {
                    assetList.add(PropertyManager.getInstance().getCommonCodeList().get(MyApplication.CODELIST_ASSET_TYPE_POSITION).getList().get(0).getCode());
                }
                if (check_tax_deposit.isChecked()) {
                    assetList.add(PropertyManager.getInstance().getCommonCodeList().get(MyApplication.CODELIST_ASSET_TYPE_POSITION).getList().get(1).getCode());
                }
                if (check_tax_house.isChecked()) {
                    assetList.add(PropertyManager.getInstance().getCommonCodeList().get(MyApplication.CODELIST_ASSET_TYPE_POSITION).getList().get(2).getCode());
                }
                if (check_tax_stock.isChecked()) {
                    assetList.add(PropertyManager.getInstance().getCommonCodeList().get(MyApplication.CODELIST_ASSET_TYPE_POSITION).getList().get(3).getCode());
                }
                NetworkManager.getInstance().getNomalEstiamteModify(marketSn, TAX, marketSubType, address1, address2, "", "", "", "", assetList, temp
                        , String.valueOf(endDate), content, new NetworkManager.OnResultListener<MyEstimateEditModifyResult>() {
                            @Override
                            public void onSuccess(Request request, MyEstimateEditModifyResult result) {
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
        });
    }

    public void setData() {
        NetworkManager.getInstance().getExpertEstimateDetailResult(marketSn, new NetworkManager.OnResultListener<ExpertEstimateDetailResult>() {
            @Override
            public void onSuccess(Request request, ExpertEstimateDetailResult result) {
                ExpertEstimateDetail items = result.getItem();
                endDate = Integer.valueOf(items.getEnddate()) - 1;
                seekBar.setProgress(Integer.valueOf(endDate));
                taxContent.setText(items.getContent());
                marketType2_2.setText(String.valueOf(items.getNumberAssetMoney()));
                marketSubType = items.getMarketSubType();
                address1 = items.getAddress1();
                address2 = items.getAddress2();
                address1Spinner.setSelection(a1Adapter.getPosition(address1));
                address2Spinner.setSelection(a2Adapter.getPosition(address2));
                marketSubTypeSpinner.setSelection(bAdapter.getPosition(marketSubType));
                for (int i = 0; i < items.getAsset_type().size(); i++) {
                    if (items.getAsset_type().get(i).equals("토지")) {
                        check_tax_land.setChecked(true);
                    } else if (items.getAsset_type().get(i).equals("예금")) {
                        check_tax_deposit.setChecked(true);
                    } else if (items.getAsset_type().get(i).equals("주식")) {
                        check_tax_stock.setChecked(true);
                    } else if (items.getAsset_type().get(i).equals("주택")) {
                        check_tax_house.setChecked(true);
                    }
                }
            }

            @Override
            public void onFail(Request request, IOException exception) {

            }
        });
    }
}