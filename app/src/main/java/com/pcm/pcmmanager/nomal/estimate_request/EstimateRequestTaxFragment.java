package com.pcm.pcmmanager.nomal.estimate_request;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.pcm.pcmmanager.data.EstimateRequestResult;
import com.pcm.pcmmanager.manager.NetworkManager;
import com.pcm.pcmmanager.manager.PropertyManager;
import com.pcm.pcmmanager.nomal.estimate_list.MyEstimateListActivity;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class EstimateRequestTaxFragment extends Fragment {

    public static final String TAX = PropertyManager.getInstance().getCommonCodeList().get(MyApplication.CODELIST_TAX_POSITION).getCode();

    SeekBar seekBar;
    Spinner marketSubTypeSpinner, address1Spinner, address2Spinner;
    int endDate = 7; //마감일
    ArrayAdapter<String> bAdapter, a1Adapter, a2Adapter; //업종, 주소
    Button taxAdd;
    CheckBox check_tax_land, check_tax_house, check_tax_stock, check_tax_deposit; //재산대상
    EditText marketType2_2, taxContent; //재산시가
    String marketSubType, address1, address2, tempAddress1; //내용 , 주소 , 부가내용
    ArrayList<String> assetList;
    LinearLayout asset_layout;
    TextView[] day;
    int color;

    public EstimateRequestTaxFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_estimate_request_tax, container, false);

        seekBar = (SeekBar) view.findViewById(R.id.estimate_request_tax_seekbar);
        day = new TextView[7];
        day[0] = (TextView) view.findViewById(R.id.seek_day1);
        day[1] = (TextView) view.findViewById(R.id.seek_day2);
        day[2] = (TextView) view.findViewById(R.id.seek_day3);
        day[3] = (TextView) view.findViewById(R.id.seek_day4);
        day[4] = (TextView) view.findViewById(R.id.seek_day5);
        day[5] = (TextView) view.findViewById(R.id.seek_day6);
        day[6] = (TextView) view.findViewById(R.id.seek_day7);
        color = view.getResources().getColor(R.color.bid_finish);

        taxAdd = (Button) view.findViewById(R.id.btn_estimate_request_tax_add);
        marketSubTypeSpinner = (Spinner) view.findViewById(R.id.estimate_request_tax_marketType_spinner);
        address1Spinner = (Spinner) view.findViewById(R.id.estimate_request_tax_regionType);
        address2Spinner = (Spinner) view.findViewById(R.id.estimate_request_tax_regionSubtype);
        bAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item_text);
        a1Adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item_text);
        a2Adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item_text);
        asset_layout = (LinearLayout) view.findViewById(R.id.asset_type_layout);

        marketType2_2 = (EditText) view.findViewById(R.id.estimate_request_tax_price);
        taxContent = (EditText) view.findViewById(R.id.estimate_request_tax_content);
        marketType2_2.addTextChangedListener(new CustomTextWathcer(marketType2_2));

        seekBar.setProgress(6);
        day[6].setTextColor(Color.BLACK);
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
        check_tax_land = (CheckBox) view.findViewById(R.id.check_estimate_request_tax_asset_land);
        check_tax_house = (CheckBox) view.findViewById(R.id.check_estimate_request_tax_asset_house);
        check_tax_stock = (CheckBox) view.findViewById(R.id.check_estimate_request_tax_asset_stock);
        check_tax_deposit = (CheckBox) view.findViewById(R.id.check_estimate_request_tax_asset_deposit);

        taxAdd = (Button) view.findViewById(R.id.btn_estimate_request_tax_add);

        bAdapter.setDropDownViewResource(R.layout.spinner_item_text);
        for (int i = 0; i < PropertyManager.getInstance().getCommonCodeList().get(MyApplication.CODELIST_TAX_POSITION).getList().size(); i++) {
            bAdapter.add(PropertyManager.getInstance().getCommonCodeList().get(MyApplication.CODELIST_TAX_POSITION).getList().get(i).getValue());
        }

        marketSubTypeSpinner.setAdapter(bAdapter);
        marketSubTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                marketSubType = PropertyManager.getInstance().getCommonCodeList().get(MyApplication.CODELIST_TAX_POSITION).getList().get(position).getCode();
                if (bAdapter.getItem(position).equals("세무조사"))
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


        taxAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check_tax_land.isChecked()) {
                    assetList.add(PropertyManager.getInstance().getCommonCodeList().get(MyApplication.CODELIST_ASSET_TYPE_POSITION).getList().get(0).getCode());
                }
                if (check_tax_deposit.isChecked()) {
                    assetList.add(PropertyManager.getInstance().getCommonCodeList().get(MyApplication.CODELIST_ASSET_TYPE_POSITION).getList().get(3).getCode());
                }
                if (check_tax_house.isChecked()) {
                    assetList.add(PropertyManager.getInstance().getCommonCodeList().get(MyApplication.CODELIST_ASSET_TYPE_POSITION).getList().get(1).getCode());
                }
                if (check_tax_stock.isChecked()) {
                    assetList.add(PropertyManager.getInstance().getCommonCodeList().get(MyApplication.CODELIST_ASSET_TYPE_POSITION).getList().get(2).getCode());
                }
                if (TextUtils.isEmpty(marketType2_2.getText().toString())) {
                    Toast.makeText(getContext(), "시가를 입력하세요", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(taxContent.getText().toString())) {
                    Toast.makeText(getContext(), "부가정보를 입력하세요", Toast.LENGTH_SHORT).show();
                } else {
                    String temp = marketType2_2.getText().toString();
                    String content = taxContent.getText().toString();
                    temp = temp.replaceAll(",", "");
                    NetworkManager.getInstance().getNomalEstiamteRequestList(TAX, marketSubType, address1, address2, "", "", "", "", assetList, temp
                            , String.valueOf(endDate), content, new NetworkManager.OnResultListener<EstimateRequestResult>() {
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
            }
        });
        return view;
    }
}
