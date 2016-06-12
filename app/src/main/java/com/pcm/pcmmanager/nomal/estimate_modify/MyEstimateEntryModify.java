package com.pcm.pcmmanager.nomal.estimate_modify;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

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

import okhttp3.Request;

public class MyEstimateEntryModify extends AppCompatActivity {
    public static final String EntryCode = PropertyManager.getInstance().getCommonCodeList().get(MyApplication.CODELIST_ENTRY_POSITION).getCode();
    public static final String TEMP = PropertyManager.getInstance().getCommonCodeList().get(MyApplication.CODELIST_ESTIMATE_TYPE_POSITION).getList().get(0).getCode(); //현재는 무조건 신규

    RadioGroup radioGroup;
    SeekBar seekBar;
    Spinner marketSubTypeSpinner, address1Spinner, address2Spinner;
    ArrayAdapter<String> marketSubAdapter, a1Adapter, a2Adapter;
    Button entryAdd;
    EditText businessScale, employeeCount, entryContent;
    String marketSubType, address1, address2, marketType1_3, tempAddress1; //업종, 주소(시, 군),부가내용
    String endDate = "1", marketSn; // 마감일
    RadioButton radioButton;
    TextView day1, day2, day3, day4, day5, day6, day7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_estimate_entry_modify);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        marketSn = getIntent().getStringExtra("marketSn");

        setData();

        radioGroup = (RadioGroup) findViewById(R.id.entry_radiogroup);
        seekBar = (SeekBar) findViewById(R.id.entry_seekbar);
        day1 = (TextView) findViewById(R.id.seek_day1);
        day2 = (TextView) findViewById(R.id.seek_day2);
        day3 = (TextView) findViewById(R.id.seek_day3);
        day4 = (TextView) findViewById(R.id.seek_day4);
        day5 = (TextView) findViewById(R.id.seek_day5);
        day6 = (TextView) findViewById(R.id.seek_day6);
        day7 = (TextView) findViewById(R.id.seek_day7);

        marketSubTypeSpinner = (Spinner) findViewById(R.id.estimate_modify_entry_marketSubtype_spinner);
        address1Spinner = (Spinner) findViewById(R.id.estimate_modify_entry_regionType_spinner);
        address2Spinner = (Spinner) findViewById(R.id.estimate_modify_entry_regionSubtype_spinner);
        marketSubAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item_text);
        a1Adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_text);
        a2Adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_text);
        entryAdd = (Button) findViewById(R.id.estimate_modify_entry_btn);
        entryContent = (EditText) findViewById(R.id.estimate_modify_entry_content);
        businessScale = (EditText) findViewById(R.id.estimate_modify_entry_businessScale);
        employeeCount = (EditText) findViewById(R.id.estimate_modify_entry_employleeCount);
        radioButton = (RadioButton) findViewById(R.id.btn_entry_artifical_radio);

        /*3자리 씩 끊기*/
        businessScale.addTextChangedListener(new CustomTextWathcer(businessScale));


        /*사업자 구분*/
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.btn_entry_personal_radio:
                        marketType1_3 = PropertyManager.getInstance().getCommonCodeList().get(MyApplication.CODELIST_BUSINESS_TYPE_POSITION).getList().get(0).getCode();
                        radioButton.setBackgroundResource(R.drawable.radio_background_arti_person);
                        break;
                    case R.id.btn_entry_artifical_radio:
                        marketType1_3 = PropertyManager.getInstance().getCommonCodeList().get(MyApplication.CODELIST_BUSINESS_TYPE_POSITION).getList().get(1).getCode();

                        break;
                    case R.id.btn_entry_ease_radio:
                        marketType1_3 = PropertyManager.getInstance().getCommonCodeList().get(MyApplication.CODELIST_BUSINESS_TYPE_POSITION).getList().get(2).getCode();
                        radioButton.setBackgroundResource(R.drawable.radio_background_arti_ease);
                        break;
                }
            }
        });
        /*입찰 기간 선택*/
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
        /*업종 분류*/
        marketSubAdapter.setDropDownViewResource(R.layout.spinner_drop_down_item);
        for (int i = 0; i < PropertyManager.getInstance().getCommonCodeList().get(MyApplication.CODELIST_ENTRY_POSITION).getList().size(); i++) {
            marketSubAdapter.add(PropertyManager.getInstance().getCommonCodeList().get(MyApplication.CODELIST_ENTRY_POSITION).getList().get(i).getValue());
        }
        marketSubTypeSpinner.setAdapter(marketSubAdapter);
        marketSubTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                marketSubType = PropertyManager.getInstance().getCommonCodeList().get(MyApplication.CODELIST_ENTRY_POSITION).getList().get(position).getCode();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        /*지역 시 분류*/
        a1Adapter.setDropDownViewResource(R.layout.spinner_drop_down_item);
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

        /*지역 구 분류*/
        a2Adapter.setDropDownViewResource(R.layout.spinner_drop_down_item);
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

        entryAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = businessScale.getText().toString();
                temp = temp.replaceAll(",", "");
                NetworkManager.getInstance().getNomalEstiamteModify(marketSn, EntryCode, marketSubType, address1, address2, TEMP, temp, marketType1_3,
                        employeeCount.getText().toString(), null, TEMP, endDate, entryContent.getText().toString(),
                        new NetworkManager.OnResultListener<MyEstimateEditModifyResult>() {
                            @Override
                            public void onSuccess(Request request, MyEstimateEditModifyResult result) {
                                Intent intent = new Intent(MyEstimateEntryModify.this, MyEstimateListActivity.class);
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
                employeeCount.setText(String.valueOf(items.getEmployeeCount()));
                businessScale.setText(String.valueOf(items.getNumberBusinessScale()));
                entryContent.setText(items.getContent());
                marketSubType = items.getMarketSubType();
                address1 = items.getAddress1();
                address2 = items.getAddress2();
                marketType1_3 = items.getMarkettpye1_3();

                    /*사업자 구분 초기값*/
                if (marketType1_3.equals("개인")) {
                    radioGroup.check(R.id.btn_entry_personal_radio);
                    radioButton.setBackgroundResource(R.drawable.radio_background_arti_person);
                } else if (marketType1_3.equals("법인"))
                    radioGroup.check(R.id.btn_entry_artifical_radio);
                else if (marketType1_3.equals("간이")) {
                    radioGroup.check(R.id.btn_entry_ease_radio);
                    radioButton.setBackgroundResource(R.drawable.radio_background_arti_ease);
                }

                seekBar.setProgress(Integer.valueOf(endDate)-1);
                address1Spinner.setSelection(a1Adapter.getPosition(address1));
                address2Spinner.setSelection(a2Adapter.getPosition(address2));
                marketSubTypeSpinner.setSelection(marketSubAdapter.getPosition(marketSubType));
            }

            @Override
            public void onFail(Request request, IOException exception) {

            }
        });
    }
}