package com.pcm.pcmmanager.expert.info;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pcm.pcmmanager.BaseActivity;
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

public class ExpertInfoEditActivity extends BaseActivity {

    EditText name, phone;
    TextView email, personl_out;
    Button logout;
    SwitchCompat pushSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_info_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        name = (EditText) findViewById(R.id.expert_personal_info_name);
        phone = (EditText) findViewById(R.id.expert_personal_info_phone);
        email = (TextView) findViewById(R.id.expert_personal_info_email);
        personl_out = (TextView) findViewById(R.id.expert_personal_info_out);
        logout = (Button) findViewById(R.id.expert_personal_info_password);
        pushSwitch = (SwitchCompat) findViewById(R.id.expert_personal_info_push_switch);


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
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setPush();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        pushSwitch.setChecked(PropertyManager.getInstance().getPushYN());
        getData();

    }

    public void getData() {
        NetworkManager.getInstance().getPersonalInfoSearch(new NetworkManager.OnResultListener<PersonalInfoSearchResult>() {
            @Override
            public void onSuccess(Request request, PersonalInfoSearchResult result) {
                if (result.getResult() == -1) {
                    Toast.makeText(ExpertInfoEditActivity.this, "개인정보 조회중 에러가 발생했습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    PersonalInfoSearch pSearch = result.getPersonalInfoSearch();
                    name.setText(pSearch.getUserName());
                    email.setText(pSearch.getEmail());
                    phone.setText(pSearch.getPhone());
                    name.setSelection(name.length());
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
                                    Toast.makeText(ExpertInfoEditActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                                else {
                                    // 로그아웃한다
                                    PropertyManager.getInstance().setAuthorizationToken("");
                                    Intent intent = new Intent(ExpertInfoEditActivity.this, LoginActivity.class);
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
                                    Toast.makeText(ExpertInfoEditActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                                else {
                                    PropertyManager.getInstance().setAuthorizationToken("");
                                    Intent intent = new Intent(ExpertInfoEditActivity.this, LoginActivity.class);
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
                        NetworkManager.getInstance().getPersonalInfoModify(name.getText().toString(), phone.getText().toString(), "", "", new NetworkManager.OnResultListener<UserInfoModifyResult>() {
                            @Override
                            public void onSuccess(Request request, UserInfoModifyResult result) {
                                if (result.getResult() == -1) {
                                    Toast.makeText(ExpertInfoEditActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                                } else {
                                    PropertyManager.getInstance().setAuthorizationToken(result.getToken());
                                    Toast.makeText(ExpertInfoEditActivity.this, "개인정보 수정이 완료됐습니다.", Toast.LENGTH_SHORT).show();
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

    public void setPush() {
        NetworkManager.getInstance().getPushSetting(String.valueOf(pushSwitch.isChecked()), new NetworkManager.OnResultListener<RefreshTokenResult>() {
            @Override
            public void onSuccess(Request request, RefreshTokenResult result) {
                if (result.getResult() == -1) {
                    Toast.makeText(ExpertInfoEditActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
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
            setPush();
            finish();
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            if (TextUtils.isEmpty(name.getText().toString())) {
                Toast.makeText(ExpertInfoEditActivity.this, "이름을 입력하세요", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(phone.getText().toString())) {
                Toast.makeText(ExpertInfoEditActivity.this, "번호를 입력하세요", Toast.LENGTH_SHORT).show();
            } else
                SaveDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
