package com.pcm.pcmmanager.expert;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pcm.pcmmanager.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExpertParentFragment extends Fragment {


    public ExpertParentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_expert_parent, container, false);
        getChildFragmentManager().beginTransaction()
                .add(R.id.expert_parent_container, new ExpertFragment())
                .commit();
        return view;
    }
}
