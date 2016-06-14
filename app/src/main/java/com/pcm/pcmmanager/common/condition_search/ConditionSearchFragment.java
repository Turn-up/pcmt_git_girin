package com.pcm.pcmmanager.common.condition_search;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.pcm.pcmmanager.MyApplication;
import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.expert.ExpertFragment;
import com.pcm.pcmmanager.manager.PropertyManager;
import com.pcm.pcmmanager.nomal.NomalUserFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConditionSearchFragment extends Fragment {

    ImageButton entryBtn, taxBtn;
    Spinner marketSubTypeSpinner, address1Spinner, address2Spinner;
    String marketSubType, marketType, region1, region2, tempResgion;
    ArrayAdapter<String> marketSubAdapter, a1Adapter, a2Adapter;
    Boolean entryBoolean, taxBoolean;
    Button searchBtn;

    public ConditionSearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_condition_search, container, false);
        marketSubTypeSpinner = (Spinner) v.findViewById(R.id.condition_search_marketSub_spinner);
        address1Spinner = (Spinner) v.findViewById(R.id.condition_search_address1_spinner);
        address2Spinner = (Spinner) v.findViewById(R.id.condition_search_address2_spinner);
        marketSubAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item);
        a1Adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item);
        a2Adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item);
        entryBtn = (ImageButton) v.findViewById(R.id.condition_search_marketType_entry_btn);
        taxBtn = (ImageButton) v.findViewById(R.id.condition_search_marketType_tax_btn);
        searchBtn = (Button) v.findViewById(R.id.condition_search_btn);
        entryBoolean = false;
        taxBoolean = false;
        marketSubType = "";
        marketType = "";
        region1 = "";
        region2 = "";
        v.setFocusableInTouchMode(true);
        v.requestFocus();
        //Fragment back 구현
        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (MyApplication.getUserType().equals("Users")) {
                        getParentFragment().getChildFragmentManager().beginTransaction()
                                .replace(R.id.nomal_parent_container, new NomalUserFragment())
                                .commit();
                    } else if (MyApplication.getUserType().equals("Experts")) {
                        getParentFragment().getChildFragmentManager().beginTransaction()
                                .replace(R.id.expert_parent_container, new ExpertFragment())
                                .commit();
                    }
                    return true;
                } else {
                    return false;
                }
            }
        });

        marketSubAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        marketSubAdapter.clear();
        marketSubAdapter.add("전체");
        marketSubTypeSpinner.setAdapter(marketSubAdapter);

        a2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        a2Adapter.clear();
        a2Adapter.add("전체");
        address2Spinner.setAdapter(a2Adapter);

         /*지역 시 분류*/
        a1Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        a1Adapter.clear();
        a1Adapter.add("전체");
        for (int i = 0; i < PropertyManager.getInstance().getCommonRegionLists().size(); i++) {
            a1Adapter.add(PropertyManager.getInstance().getCommonRegionLists().get(i).getValue());
        }
        address1Spinner.setAdapter(a1Adapter);
        address1Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!a1Adapter.getItem(position).equals("전체")) {
                    region1 = PropertyManager.getInstance().getCommonRegionLists().get(position - 1).getCode();
                }
                tempResgion = a1Adapter.getItem(position);
                if (!tempResgion.equals("전체")) {
                    a2Adapter.clear();
                    a2Adapter.add("전체");
                    for (int i = 0; i < PropertyManager.getInstance().getCommonRegionLists().get(position - 1).getList().size(); i++) {
                        a2Adapter.add(PropertyManager.getInstance().getCommonRegionLists().get(position - 1).getList().get(i).getValue());
                        address2Spinner.setAdapter(a2Adapter);
                    }
                }
                address2Spinner.setSelection(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        /*지역 구 분류*/
        address2Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!a2Adapter.getItem(position).equals("전체"))
                    region2 = PropertyManager.getInstance().getCommonRegionLists().get(a1Adapter.getPosition(tempResgion)-1).getList().get(position-1).getCode();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        entryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (entryBoolean) {
                    entryBoolean = false;
                    entryBtn.setImageResource(R.drawable.expert_search_entry_icon_unselect);
                    //클릭 안됐을때 이미지
                } else {
                    entryBoolean = true;
                    entryBtn.setImageResource(R.drawable.expert_search_entry_icon_select);
                    taxBtn.setImageResource(R.drawable.expert_search_tax_icon_unselect);
                    marketType = PropertyManager.getInstance().getCommonCodeList().get(MyApplication.CODELIST_ENTRY_POSITION).getCode();//기장
                    //클릭 됐을때 이미지
                }
                setBtnClick(entryBoolean, MyApplication.CODELIST_ENTRY_POSITION); //0
            }
        });

        taxBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (taxBoolean) {
                    taxBoolean = false;
                    taxBtn.setImageResource(R.drawable.expert_search_tax_icon_unselect);
                    //클릭 안됐을때 이미지
                } else {
                    taxBoolean = true;
                    taxBtn.setImageResource(R.drawable.expert_search_tax_icon_select);
                    entryBtn.setImageResource(R.drawable.expert_search_entry_icon_unselect);
                    marketType = PropertyManager.getInstance().getCommonCodeList().get(MyApplication.CODELIST_TAX_POSITION).getCode();//TAX
                    //클릭 됐을때 이미지
                }
                setBtnClick(taxBoolean, MyApplication.CODELIST_TAX_POSITION); //1
            }
        });
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//pagesize, last_expertsn, markettype
                Intent intent = new Intent(getParentFragment().getContext(), ConditionSearchResultActivity.class);
                intent.putExtra("markettype", marketType);
                intent.putExtra("marketsubtype", marketSubType);
                intent.putExtra("regiontype", region1);
                intent.putExtra("regionsubtype", region2);
                startActivity(intent);
            }
        });
        return v;
    }

    public void setBtnClick(Boolean b, final int index) {
        if (b) {
            marketSubAdapter.clear();
            marketSubAdapter.add("전체");
            for (int i = 0; i < PropertyManager.getInstance().getCommonCodeList().get(index).getList().size(); i++) {
                marketSubAdapter.add(PropertyManager.getInstance().getCommonCodeList().get(index).getList().get(i).getValue());
            }
            marketSubTypeSpinner.setAdapter(marketSubAdapter);
            marketSubTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position == 0) {
                        marketSubType = "";
                    } else{
                        marketSubType = PropertyManager.getInstance().getCommonCodeList().get(index).getList().get(position-1).getCode();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } else {
            marketSubAdapter.clear();
            marketSubAdapter.add("전체");
            marketSubTypeSpinner.setAdapter(marketSubAdapter);
        }
    }
}
