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
import android.widget.SeekBar;
import android.widget.Spinner;

import com.pcm.pcmmanager.MyApplication;
import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.data.EstimateRequestResult;
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
    EditText etcContent;
    ArrayAdapter<String> a1Adapter, a2Adapter; //업종, 주소
    Spinner address1Spinner, address2Spinner;
    String endDate = "1"; // 마감일
    Button etcAdd;

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
        seekBar = (SeekBar) view.findViewById(R.id.etc_seekbar);
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
        etcContent = (EditText) view.findViewById(R.id.estimate_request_etc_content);
        etcAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content = etcContent.getText().toString();
                NetworkManager.getInstance().getNomalEstiamteRequestList(ESTIMATE_REQUEST_ETC_CODE, "", address1, address2, "", "", "", "", null,
                        "", endDate, content, new NetworkManager.OnResultListener<EstimateRequestResult>() {
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
