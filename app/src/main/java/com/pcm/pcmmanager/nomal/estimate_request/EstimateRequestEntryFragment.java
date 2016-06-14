package com.pcm.pcmmanager.nomal.estimate_request;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.pcm.pcmmanager.data.EstimateRequestResult;
import com.pcm.pcmmanager.manager.NetworkManager;
import com.pcm.pcmmanager.manager.PropertyManager;
import com.pcm.pcmmanager.nomal.estimate_list.MyEstimateListActivity;

import java.io.IOException;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class EstimateRequestEntryFragment extends Fragment {
    public static final String EntryCode = PropertyManager.getInstance().getCommonCodeList().get(MyApplication.CODELIST_ENTRY_POSITION).getCode();
    public static final String TEMP = PropertyManager.getInstance().getCommonCodeList().get(MyApplication.CODELIST_ESTIMATE_TYPE_POSITION).getList().get(0).getCode(); //현재는 무조건 신규

    RadioGroup radioGroup;
    SeekBar seekBar;
    Spinner marketSubTypeSpinner, address1Spinner, address2Spinner;
    ArrayAdapter<String> marketSubAdapter, a1Adapter, a2Adapter;
    Button entryAdd;
    EditText businessScale, employeeCount, entryContent;
    String marketSubType, address1, address2, marketType1_3, tempAddress1; //업종, 주소(시, 군),부가내용
    String endDate = "7"; // 마감일
    RadioButton radioButton;
    TextView day1,day2,day3,day4,day5,day6,day7;
    public EstimateRequestEntryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_estimate_request_entry, container, false);
        radioGroup = (RadioGroup) view.findViewById(R.id.entry_radiogroup);
        seekBar = (SeekBar) view.findViewById(R.id.entry_seekbar);
        day1 = (TextView)view.findViewById(R.id.seek_day1);
        day2 = (TextView)view.findViewById(R.id.seek_day2);
        day3 = (TextView)view.findViewById(R.id.seek_day3);
        day4 = (TextView)view.findViewById(R.id.seek_day4);
        day5 = (TextView)view.findViewById(R.id.seek_day5);
        day6 = (TextView)view.findViewById(R.id.seek_day6);
        day7 = (TextView)view.findViewById(R.id.seek_day7);

        marketSubTypeSpinner = (Spinner) view.findViewById(R.id.estimate_request_entry_marketSubtype_spinner);
        address1Spinner = (Spinner) view.findViewById(R.id.estimate_request_entry_regionType_spinner);
        address2Spinner = (Spinner) view.findViewById(R.id.estimate_request_entry_regionSubtype_spinner);
        marketSubAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item_text);
        a1Adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item_text);
        a2Adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item_text);
        entryAdd = (Button) view.findViewById(R.id.estimate_request_entry_endtry_btn);
        entryContent = (EditText) view.findViewById(R.id.estimate_request_entry_content);
        businessScale = (EditText) view.findViewById(R.id.estimate_request_entry_businessScale);
        employeeCount = (EditText) view.findViewById(R.id.estimate_request_entry_employleeCount);
        radioButton = (RadioButton) view.findViewById(R.id.btn_entry_artifical_radio);
        /*3자리 씩 끊기*/
        businessScale.addTextChangedListener(new CustomTextWathcer(businessScale));

        marketType1_3 = PropertyManager.getInstance().getCommonCodeList().get(MyApplication.CODELIST_BUSINESS_TYPE_POSITION).getList().get(0).getCode(); //디폴트값

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
        seekBar.setProgress(6);
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
                address2Spinner.setSelection(0);
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
                NetworkManager.getInstance().getNomalEstiamteRequestList(EntryCode, marketSubType, address1, address2, TEMP, temp, marketType1_3, employeeCount.getText().toString(), null, TEMP
                        , endDate, entryContent.getText().toString(), new NetworkManager.OnResultListener<EstimateRequestResult>() {
                            @Override
                            public void onSuccess(Request request, EstimateRequestResult result) {
                                Intent intent = new Intent(getContext(), MyEstimateListActivity.class);
                                startActivity(intent);
                                getActivity().finish();
                            }
                            @Override
                            public void onFail(Request request, IOException exception) {

                            }
                        });
            }
        });

        return view;
    }

}
