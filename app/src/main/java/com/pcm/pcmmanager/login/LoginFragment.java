package com.pcm.pcmmanager.login;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.pcm.pcmmanager.MyApplication;
import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.data.ExpertCheckResult;
import com.pcm.pcmmanager.data.LoginResult;
import com.pcm.pcmmanager.expert.ExpertMainActivity;
import com.pcm.pcmmanager.manager.NetworkManager;
import com.pcm.pcmmanager.manager.PropertyManager;
import com.pcm.pcmmanager.nomal.NomalMainActivity;

import java.io.IOException;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    EditText email,password;
    ImageButton login_btn, join_expert_btn, join_user_btn;
    LoginResult loginResult;

    public LoginFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        email = (EditText)v.findViewById(R.id.login_email);
        password = (EditText)v.findViewById(R.id.login_password);
        login_btn = (ImageButton)v.findViewById(R.id.login_btn);
        join_user_btn = (ImageButton)v.findViewById(R.id.join_user_btn);
        join_expert_btn = (ImageButton)v.findViewById(R.id.join_expert_btn);
        join_user_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.container,new SignupNomalFragment())
                        .commit();
            }
        });
        join_expert_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.container,new SignupExpertFragment())
                        .commit();
            }
        });
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(email.getText().toString())||TextUtils.isEmpty(password.getText().toString()))
                    Toast.makeText(getContext(), "아이디 또는 패스워드를 확인하세요", Toast.LENGTH_SHORT).show();
                NetworkManager.getInstance().getLogin(email.getText().toString(), password.getText().toString(), new NetworkManager.OnResultListener<LoginResult>() {
                    @Override
                    public void onSuccess(Request request, LoginResult result) {
                        loginResult = result;
                        if(result.getResult() == -1){
                            Toast.makeText(getContext(), "아이디 또는 패스워드를 확인하세요", Toast.LENGTH_SHORT).show();
                        }else{
                            PropertyManager.getInstance().setAuthorizationToken(loginResult.getToken());
                            if(loginResult.getRoles().equals("Users")) {
                                MyApplication.setUserType(loginResult.getRoles());
                                Intent intent = new Intent(getContext(), NomalMainActivity.class);
                                startActivity(intent);
                                getActivity().finish();
                            }else if(loginResult.getRoles().equals("Experts")){
                                MyApplication.setUserType(loginResult.getRoles());
                                getExpertState();
                                Intent intent = new Intent(getContext(), ExpertMainActivity.class);
                                startActivity(intent);
                                getActivity().finish();
                            }else{
                                Toast.makeText(getContext(), "죄송합니다, 잠시후 다시 시도해주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    @Override
                    public void onFail(Request request, IOException exception) {
                    }
                });
            }
        });
        return v;
    }

    public void getExpertState(){
        NetworkManager.getInstance().getExpertCheck(new NetworkManager.OnResultListener<ExpertCheckResult>() {
            @Override
            public void onSuccess(Request request, ExpertCheckResult result) {
                if(result.getResult() == -1){
                    Toast.makeText(getContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
                }else{
                    PropertyManager.getInstance().setExpertCheck(result.getCheck());
                }
            }

            @Override
            public void onFail(Request request, IOException exception) {

            }
        });
    }
}
