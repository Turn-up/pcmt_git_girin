package com.pcm.pcmmanager.login;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pcm.pcmmanager.MyApplication;
import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.data.UserSignupResult;
import com.pcm.pcmmanager.expert.ExpertMainActivity;
import com.pcm.pcmmanager.manager.NetworkManager;
import com.pcm.pcmmanager.manager.PropertyManager;
import com.pcm.pcmmanager.utill.SignupCheck;

import java.io.IOException;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignupExpertFragment extends Fragment {
    EditText name, email, password, passwordCheck, phone;
    UserSignupResult userSignupResult;
    ImageView passwordCheckIcon, passwordRecheckIcon;
    ImageButton btn;
    TextView passwordCheckText, passwordRecheckText;
    public static final String ROLE_EXPERT_USER = PropertyManager.getInstance().getCommonCodeList().get(MyApplication.CODELIST_ROLES_POSITION).getList().get(1).getCode();

    public SignupExpertFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_signup_expert, container, false);
        name = (EditText) v.findViewById(R.id.signup_expert_name);
        email = (EditText) v.findViewById(R.id.signup_expert_email);
        password = (EditText) v.findViewById(R.id.signup_expert_password);
        passwordCheck = (EditText) v.findViewById(R.id.signup_expert_password_check);
        btn = (ImageButton) v.findViewById(R.id.signup_expert_btn);
        passwordCheckText = (TextView) v.findViewById(R.id.expert_password_text);
        passwordRecheckText = (TextView) v.findViewById(R.id.expert_password_check_text);
        passwordCheckIcon = (ImageView) v.findViewById(R.id.user_password_icon);
        passwordRecheckIcon = (ImageView) v.findViewById(R.id.user_password_check_icon);
        phone = (EditText) v.findViewById(R.id.signup_expert_phone);
        TelephonyManager telManager = (TelephonyManager) getContext().getSystemService(getContext().TELEPHONY_SERVICE);
        String phoneNum = telManager.getLine1Number();
        phone.setText(phoneNum);
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (password.getText().toString().length() >= 8) {
                    passwordCheckText.setVisibility(View.GONE);
                    passwordCheckIcon.setVisibility(View.VISIBLE);
                } else {
                    passwordCheckText.setVisibility(View.VISIBLE);
                    passwordCheckIcon.setVisibility(View.GONE);
                }
            }
        });
        passwordCheck.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (passwordCheck.getText().length() >= 8) {
                    passwordRecheckText.setVisibility(View.GONE);
                    if (password.getText().toString().equals(passwordCheck.getText().toString()) && !TextUtils.isEmpty(passwordCheck.getText().toString())) {
                        passwordRecheckIcon.setVisibility(View.VISIBLE);
                        passwordCheck.setBackgroundResource(R.drawable.signp_expert_password);

                    } else {
                        passwordCheck.setBackgroundResource(R.drawable.signp_expert_password_error);
                        passwordRecheckIcon.setVisibility(View.GONE);
                    }
                } else {
                    passwordCheck.setBackgroundResource(R.drawable.signp_expert_password);
                    passwordRecheckText.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(name.getText().toString()))
                    Toast.makeText(getContext(), "이름을 입력하세요", Toast.LENGTH_SHORT).show();
                else if (TextUtils.isEmpty(email.getText().toString()))
                    Toast.makeText(getContext(), "이메일을 입력하세요", Toast.LENGTH_SHORT).show();
                else if (TextUtils.isEmpty(phone.getText().toString()))
                    Toast.makeText(getContext(), "전화번호를 입력하세요", Toast.LENGTH_SHORT).show();
                else if (!password.getText().toString().equals(passwordCheck.getText().toString()))
                    Toast.makeText(getContext(), "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                else if (!SignupCheck.validateEmail(email.getText().toString()))
                    Toast.makeText(getContext(), "이메일을 제대로 입력하지 않았습니다", Toast.LENGTH_SHORT).show();
                else if (password.getText().toString().length() < 8)
                    Toast.makeText(getContext(), "비밀번호를 제대로 입력하지 않았습니다", Toast.LENGTH_SHORT).show();
                else if (!phone.getText().toString().contains("010"))
                    Toast.makeText(getContext(), "핸드폰 번호를 제대로 입력하지 않았습니다", Toast.LENGTH_SHORT).show();
                else
                    setData();
            }
        });

        v.setFocusableInTouchMode(true);
        v.requestFocus();
        //Fragment back 구현
        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    getFragmentManager().beginTransaction()
                            .replace(R.id.container, new LoginFragment())
                            .commit();
                    return true;
                } else {
                    return false;
                }
            }
        });
        return v;
    }

    public void setData() {
        NetworkManager.getInstance().getSignUp(name.getText().toString(), email.getText().toString(), phone.getText().toString(), password.getText().toString(),
                PropertyManager.getInstance().getRegistrationToken(), "", "", ROLE_EXPERT_USER, new NetworkManager.OnResultListener<UserSignupResult>() {
                    @Override
                    public void onSuccess(Request request, UserSignupResult result) {
                        if (result.getResult() == -1) {
                            Toast.makeText(getContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            userSignupResult = result;
                            PropertyManager.getInstance().setAuthorizationToken(userSignupResult.getToken());
                            PropertyManager.getInstance().setExpertCheck(false);
                            MyApplication.setUserType("Experts");
                            Intent intent = new Intent(getContext(), ExpertMainActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }
                    }

                    @Override
                    public void onFail(Request request, IOException exception) {

                    }
                });
    }
}