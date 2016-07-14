package com.pcm.pcmmanager.nomal.estimate_request;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.pcm.pcmmanager.MyApplication;
import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.data.CommonResult;
import com.pcm.pcmmanager.manager.NetworkManager;
import com.pcm.pcmmanager.manager.PropertyManager;
import com.pcm.pcmmanager.nomal.estimate_list.MyEstimateListActivity;

import java.io.IOException;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class EstimateRequestEtcFragment extends Fragment {
    public static final String ESTIMATE_REQUEST_ETC_CODE = PropertyManager.getInstance().getCommonCodeList().get(MyApplication.CODELIST_ETC_POSITION).getCode();
    SeekBar seekBar;
    String content, address1, address2, tempAddress1;
    EditText etcContent,phone;
    ArrayAdapter<String> a1Adapter, a2Adapter; //업종, 주소
    Spinner address1Spinner, address2Spinner;
    String endDate = "7"; // 마감일
    Button etcAdd;
    TextView[] day;
    int color;

    public EstimateRequestEtcFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_estimate_request_etc, container, false);
        address1Spinner = (Spinner) view.findViewById(R.id.estimate_request_etc_address1_spinner);
        address2Spinner = (Spinner) view.findViewById(R.id.estimate_request_etc_address2_spinner);
        a1Adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item_text);
        a2Adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item_text);
        etcAdd = (Button) view.findViewById(R.id.estimate_request_etc_btn);
        phone = (EditText)view.findViewById(R.id.estimate_request_etc_phone);
        day = new TextView[7];
        day[0] = (TextView) view.findViewById(R.id.seek_day1);
        day[1] = (TextView) view.findViewById(R.id.seek_day2);
        day[2] = (TextView) view.findViewById(R.id.seek_day3);
        day[3] = (TextView) view.findViewById(R.id.seek_day4);
        day[4] = (TextView) view.findViewById(R.id.seek_day5);
        day[5] = (TextView) view.findViewById(R.id.seek_day6);
        day[6] = (TextView) view.findViewById(R.id.seek_day7);
        color = view.getResources().getColor(R.color.bid_finish);

        TelephonyManager telManager = (TelephonyManager) getContext().getSystemService(getContext().TELEPHONY_SERVICE);
        final String phoneNum = telManager.getLine1Number();
        phone.setText(phoneNum);

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
        seekBar = (SeekBar) view.findViewById(R.id.etc_seekbar);
        seekBar.setProgress(6);
        day[6].setTextColor(Color.BLACK);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                endDate = String.valueOf(progress + 1);
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
        etcContent = (EditText) view.findViewById(R.id.estimate_request_etc_content);
        etcAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etcContent.getText().toString())) {
                    Toast.makeText(getContext(), "부가정보를 입력하세요", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(phone.getText().toString())) {
                    Toast.makeText(getContext(), "연락처를 입력하세요", Toast.LENGTH_SHORT).show();
                } else {
                    content = etcContent.getText().toString();
                    CheckDialog(content);
                }
            }
        });

        return view;
    }

    private void CheckDialog(final String sContent) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        // AlertDialog 셋팅
        alertDialogBuilder
                .setMessage("견적을 등록 하시겠습니까?")
                .setCancelable(false)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String content = sContent;
                        NetworkManager.getInstance().getNomalEstiamteRequestList(phone.getText().toString(),ESTIMATE_REQUEST_ETC_CODE, "", address1, address2, "", "", "", "", null,
                                null, endDate, content, new NetworkManager.OnResultListener<CommonResult>() {
                                    @Override
                                    public void onSuccess(Request request, CommonResult result) {
                                        Intent intent = new Intent(getContext(), MyEstimateListActivity.class);
                                        startActivity(intent);
                                        getActivity().finish();
                                    }

                                    @Override
                                    public void onFail(Request request, IOException exception) {

                                    }
                                });

                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // 다이얼로그를 취소한다
                        dialog.cancel();
                    }
                });
        // 다이얼로그 보여주기
        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.show();
    }
}
