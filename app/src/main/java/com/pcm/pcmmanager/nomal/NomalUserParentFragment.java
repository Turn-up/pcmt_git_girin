package com.pcm.pcmmanager.nomal;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pcm.pcmmanager.MyApplication;
import com.pcm.pcmmanager.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NomalUserParentFragment extends Fragment {


    public NomalUserParentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        MyApplication myApplication = new MyApplication();
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_nomal_user_parent, container, false);
        getChildFragmentManager().beginTransaction().add(R.id.nomal_parent_container, new NomalUserFragment()).commit();
        return v;

    }

}
