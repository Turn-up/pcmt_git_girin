package com.pcm.pcmmanager.nomal.info;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.pcm.pcmmanager.MyApplication;
import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.data.CommonResult;
import com.pcm.pcmmanager.data.PersonalInfoSearch;
import com.pcm.pcmmanager.data.PersonalInfoSearchResult;
import com.pcm.pcmmanager.data.RefreshTokenResult;
import com.pcm.pcmmanager.data.UserInfoModifyResult;
import com.pcm.pcmmanager.data.UserSignupResult;
import com.pcm.pcmmanager.login.LoginActivity;
import com.pcm.pcmmanager.manager.NetworkManager;
import com.pcm.pcmmanager.manager.PropertyManager;

import java.io.IOException;

import okhttp3.Request;

public class NomalUserInfoEditActivity extends AppCompatActivity {

    EditText name, phone;
    TextView email, personl_out;
    Button logout;
    SwitchCompat pushSwitch;
    RadioGroup businessRadioGroup, taxationRadioGroup;
    RadioButton personRadioButton, artiRadioButton, easeRadioButton, taxationNoRadioButton;
    String marketType1_3, marketType1_4;
    Boolean personal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nomal_user_info_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        personal = true;
        name = (EditText) findViewById(R.id.nomal_personal_info_name);
        phone = (EditText) findViewById(R.id.nomal_personal_info_phone);
        email = (TextView) findViewById(R.id.nomal_personal_info_email);
        personl_out = (TextView) findViewById(R.id.nomal_personal_info_out);
        logout = (Button) findViewById(R.id.nomal_personal_info_password);
        pushSwitch = (SwitchCompat) findViewById(R.id.nomal_personal_info_push_switch);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogoutCheckDialog();
            }
        });
        personl_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserOut();
            }
        });
        businessRadioGroup = (RadioGroup) findViewById(R.id.business_radiogroup);
        taxationRadioGroup = (RadioGroup) findViewById(R.id.taxation_radiogroup);
        artiRadioButton = (RadioButton) findViewById(R.id.btn_entry_artifical_radio);
        personRadioButton = (RadioButton) findViewById(R.id.btn_entry_personal_radio);
        easeRadioButton = (RadioButton) findViewById(R.id.btn_entry_ease_radio);
        if (!artiRadioButton.isChecked()) {
            artiRadioButton.setBackgroundResource(R.drawable.article_default_icon);
        }
        /*사업자 구분*/
        businessRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.btn_entry_personal_radio:
                        marketType1_3 = PropertyManager.getInstance().getCommonCodeList().get(MyApplication.CODELIST_BUSINESS_TYPE_POSITION).getList().get(0).getCode();
                        artiRadioButton.setBackgroundResource(R.drawable.radio_background_arti_person);
                        if (personRadioButton.isChecked())
                            businessRadioGroup.clearCheck();
                        break;
                    case R.id.btn_entry_artifical_radio:
                        artiRadioButton.setBackgroundResource(R.drawable.business_arti_select);
                        marketType1_3 = PropertyManager.getInstance().getCommonCodeList().get(MyApplication.CODELIST_BUSINESS_TYPE_POSITION).getList().get(1).getCode();
                        break;
                    case R.id.btn_entry_ease_radio:
                        marketType1_3 = PropertyManager.getInstance().getCommonCodeList().get(MyApplication.CODELIST_BUSINESS_TYPE_POSITION).getList().get(2).getCode();
                        artiRadioButton.setBackgroundResource(R.drawable.radio_background_arti_ease);
                        break;
                }
            }
        });
        taxationNoRadioButton = (RadioButton) findViewById(R.id.taxation_no);

        if (!taxationNoRadioButton.isChecked()) {
            taxationNoRadioButton.setBackgroundResource(R.drawable.taxation_no_default);
        }
        /*과세여부*/
        taxationRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.taxation_no:
                        taxationNoRadioButton.setBackgroundResource(R.drawable.radio_signup_taxation_no);
                        marketType1_4 = PropertyManager.getInstance().getCommonCodeList().get(MyApplication.CODELIST_TAXATION_TYPE_POSITION).getList().get(0).getCode();
                        break;
                    case R.id.taxation_yes:
                        marketType1_4 = PropertyManager.getInstance().getCommonCodeList().get(MyApplication.CODELIST_TAXATION_TYPE_POSITION).getList().get(1).getCode();
                        break;
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        pushSwitch.setChecked(PropertyManager.getInstance().getPushYN());
        getData();

    }

    public void setPush() {
        NetworkManager.getInstance().getPushSetting(String.valueOf(pushSwitch.isChecked()), new NetworkManager.OnResultListener<RefreshTokenResult>() {
            @Override
            public void onSuccess(Request request, RefreshTokenResult result) {
                if (result.getResult() == -1) {
                    Toast.makeText(NomalUserInfoEditActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    PropertyManager.getInstance().setPushYN(pushSwitch.isChecked());
                    PropertyManager.getInstance().setAuthorizationToken(result.getToken());
                }
            }

            @Override
            public void onFail(Request request, IOException exception) {

            }
        });
    }

    /*정보 조회*/
    public void getData() {
        NetworkManager.getInstance().getPersonalInfoSearch(new NetworkManager.OnResultListener<PersonalInfoSearchResult>() {
            @Override
            public void onSuccess(Request request, PersonalInfoSearchResult result) {
                if (result.getResult() == -1) {
                    Toast.makeText(NomalUserInfoEditActivity.this, "개인정보 조회중 에러가 발생했습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    PersonalInfoSearch pSearch = result.getPersonalInfoSearch();
                    name.setText(pSearch.getUserName());
                    email.setText(pSearch.getEmail());
                    phone.setText(pSearch.getPhone());
                    name.setSelection(name.length());
                    if (!artiRadioButton.isChecked()) {
                        artiRadioButton.setBackgroundResource(R.drawable.article_default_icon);
                    }
                    if (pSearch.getUsertype1().equals("개인")) {
                        marketType1_3 = PropertyManager.getInstance().getCommonCodeList().get(MyApplication.CODELIST_BUSINESS_TYPE_POSITION).getList().get(0).getCode();
                        businessRadioGroup.check(R.id.btn_entry_personal_radio);
                        artiRadioButton.setBackgroundResource(R.drawable.radio_background_arti_person);
                    } else if (pSearch.getUsertype1().equals("법인")) {
                        marketType1_3 = PropertyManager.getInstance().getCommonCodeList().get(MyApplication.CODELIST_BUSINESS_TYPE_POSITION).getList().get(1).getCode();
                        businessRadioGroup.check(R.id.btn_entry_artifical_radio);
                        artiRadioButton.setBackgroundResource(R.drawable.business_arti_select);
                    } else if (pSearch.getUsertype1().equals("간이")) {
                        marketType1_3 = PropertyManager.getInstance().getCommonCodeList().get(MyApplication.CODELIST_BUSINESS_TYPE_POSITION).getList().get(2).getCode();
                        businessRadioGroup.check(R.id.btn_entry_ease_radio);
                        artiRadioButton.setBackgroundResource(R.drawable.radio_background_arti_ease);
                    }
                    if (!taxationNoRadioButton.isChecked()) {
                        taxationNoRadioButton.setBackgroundResource(R.drawable.taxation_no_default);
                    }
                    switch (pSearch.getUsertype2()) {
                        case "면세":
                            marketType1_4 = PropertyManager.getInstance().getCommonCodeList().get(MyApplication.CODELIST_TAXATION_TYPE_POSITION).getList().get(0).getCode();
                            taxationRadioGroup.check(R.id.taxation_no);
                            break;
                        case "과세":
                            marketType1_4 = PropertyManager.getInstance().getCommonCodeList().get(MyApplication.CODELIST_TAXATION_TYPE_POSITION).getList().get(1).getCode();
                            taxationRadioGroup.check(R.id.taxation_yes);
                            break;
                    }
                }
            }

            @Override
            public void onFail(Request request, IOException exception) {

            }
        });
    }

    /*로그아웃*/
    private void LogoutCheckDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        // AlertDialog 셋팅
        alertDialogBuilder
                .setMessage("로그아웃 하시겠습니까?")
                .setCancelable(false)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        NetworkManager.getInstance().getLogout(new NetworkManager.OnResultListener<UserSignupResult>() {
                            @Override
                            public void onSuccess(Request request, UserSignupResult result) {
                                if (result.getResult() == -1)
                                    Toast.makeText(NomalUserInfoEditActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                                else {
                                    // 로그아웃한다
                                    PropertyManager.getInstance().setAuthorizationToken("");
                                    Intent intent = new Intent(NomalUserInfoEditActivity.this, LoginActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                }
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

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) { // 백 버튼
            SaveDialog();
        }
        return true;
    }

    /*회원탈퇴 다이얼로그*/
    private void UserOut() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        // AlertDialog 셋팅
        alertDialogBuilder
                .setMessage("회원탈퇴 하시겠습니까?")
                .setCancelable(false)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        NetworkManager.getInstance().getUserDelete(new NetworkManager.OnResultListener<CommonResult>() {
                            @Override
                            public void onSuccess(Request request, CommonResult result) {
                                if (result.getResult() == -1)
                                    Toast.makeText(NomalUserInfoEditActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                                else {
                                    PropertyManager.getInstance().setAuthorizationToken("");
                                    Intent intent = new Intent(NomalUserInfoEditActivity.this, LoginActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                }
                            }

                            @Override
                            public void onFail(Request request, IOException exception) {

                            }
                        });
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int arg1) {
                        dialog.cancel();
                    }
                });
        // 다이얼로그 보여주기
        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.show();
    }


    private void SaveDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder
                .setMessage("회원정보를 수정하시겠습니까?")
                .setCancelable(false)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setPush();
                        NetworkManager.getInstance().getPersonalInfoModify(name.getText().toString(), phone.getText().toString(), marketType1_3, marketType1_4, new NetworkManager.OnResultListener<UserInfoModifyResult>() {
                            @Override
                            public void onSuccess(Request request, UserInfoModifyResult result) {
                                if (result.getResult() == -1) {
                                    Toast.makeText(NomalUserInfoEditActivity.this, "개인정보 수정중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(NomalUserInfoEditActivity.this, "개인정보 수정이 완료됐습니다.", Toast.LENGTH_SHORT).show();
                                    PropertyManager.getInstance().setAuthorizationToken(result.getToken());
                                    finish();
                                }
                            }

                            @Override
                            public void onFail(Request request, IOException exception) {

                            }
                        });
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        finish();
                    }
                });

        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            SaveDialog();
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            if (TextUtils.isEmpty(name.getText().toString())) {
                Toast.makeText(NomalUserInfoEditActivity.this, "이름을 입력하세요", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(phone.getText().toString())) {
                Toast.makeText(NomalUserInfoEditActivity.this, "번호를 입력하세요", Toast.LENGTH_SHORT).show();
            } else
                SaveDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
