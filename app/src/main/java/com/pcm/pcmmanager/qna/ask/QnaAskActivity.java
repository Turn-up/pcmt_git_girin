package com.pcm.pcmmanager.qna.ask;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.pcm.pcmmanager.BaseActivity;
import com.pcm.pcmmanager.MyApplication;
import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.data.CommonResult;
import com.pcm.pcmmanager.expert.ExpertMainActivity;
import com.pcm.pcmmanager.manager.NetworkManager;
import com.pcm.pcmmanager.nomal.NomalMainActivity;

import java.io.IOException;

import okhttp3.Request;

public class QnaAskActivity extends BaseActivity {

    EditText title, content;
    Boolean secretyn;
    CheckBox secretCheckBox;
    ImageButton do_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qna_ask);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        secretCheckBox = (CheckBox) findViewById(R.id.qna_ask_check_box);
        secretyn = false;
        title = (EditText) findViewById(R.id.qna_ask_title);
        content = (EditText) findViewById(R.id.qna_ask_content);
        do_btn = (ImageButton) findViewById(R.id.qna_ask_do_btn);
        if (!TextUtils.isEmpty(getIntent().getStringExtra("title"))) {
            do_btn.setImageResource(R.drawable.estimate_modify_btn);
            title.setText(getIntent().getStringExtra("title"));
            content.setText(getIntent().getStringExtra("content"));
            secretCheckBox.setChecked(getIntent().getBooleanExtra("secret", false));
            secretyn = getIntent().getBooleanExtra("secret", false);
        }
        secretCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                secretyn = isChecked;
            }
        });
        do_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(title.getText().toString()))
                    Toast.makeText(QnaAskActivity.this, "제목을 입력해주세요", Toast.LENGTH_SHORT).show();
                else if (TextUtils.isEmpty(content.getText().toString()))
                    Toast.makeText(QnaAskActivity.this, "내용을 입력해주세요", Toast.LENGTH_SHORT).show();
                else if (!TextUtils.isEmpty(getIntent().getStringExtra("title"))) {
                    EditConfirmDialog();
                } else
                    AskConfirmDialog();
            }
        });
    }

    private void AskConfirmDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        // AlertDialog 셋팅
        alertDialogBuilder
                .setMessage("질문을 등록하시겠습니까?")
                .setCancelable(false)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        NetworkManager.getInstance().getQnaAsk(title.getText().toString(), content.getText().toString(),
                                secretyn.booleanValue(), new NetworkManager.OnResultListener<CommonResult>() {
                                    @Override
                                    public void onSuccess(Request request, CommonResult result) {
                                        if (result.getResult() == -1)
                                            Toast.makeText(QnaAskActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                                        else {
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

    private void EditConfirmDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(QnaAskActivity.this);
        // AlertDialog 셋팅
        alertDialogBuilder
                .setMessage("수정하시겠습니까?")
                .setCancelable(false)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String qnasn = getIntent().getStringExtra("qnaSn");
                        NetworkManager.getInstance().getQnaEdit(qnasn, title.getText().toString(), content.getText().toString(),
                                secretyn, new NetworkManager.OnResultListener<CommonResult>() {
                                    @Override
                                    public void onSuccess(Request request, CommonResult result) {
                                        if (result.getResult() == -1)
                                            Toast.makeText(QnaAskActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                                        else {
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.expert_main, menu);
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
        if (id == R.id.action_home) {
            if (MyApplication.getUserType().equals("Users")) {
                Intent intent = new Intent(this, NomalMainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            } else if (MyApplication.getUserType().equals("Experts")) {
                Intent intent = new Intent(this, ExpertMainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}