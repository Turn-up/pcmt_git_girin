package com.pcm.pcmmanager.expert.info;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.data.PersonalInfoModifyResult;
import com.pcm.pcmmanager.data.PersonalInfoSearch;
import com.pcm.pcmmanager.data.PersonalInfoSearchResult;
import com.pcm.pcmmanager.manager.NetworkManager;

import java.io.IOException;

import okhttp3.Request;

public class ExpertInfoEditActivity extends AppCompatActivity {

    EditText name, phone;
    TextView email;

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
                    name.setHint(pSearch.getUserName());
                    email.setText(pSearch.getEmail());
                    phone.setHint(pSearch.getPhone());
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
            finish();
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            SaveDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void SaveDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder
                .setMessage("회원정보를 수정하시겠습니까?")
                .setCancelable(false)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NetworkManager.getInstance().getPersonalInfoModify(name.getText().toString(), phone.getText().toString(),new NetworkManager.OnResultListener<PersonalInfoModifyResult>() {
                            @Override
                            public void onSuccess(Request request, PersonalInfoModifyResult result) {
                                if(result.getResult() == -1){
                                    Toast.makeText(ExpertInfoEditActivity.this, "개인정보 수정중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                                }else{
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
                    }
                });

        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.show();
    }
}
