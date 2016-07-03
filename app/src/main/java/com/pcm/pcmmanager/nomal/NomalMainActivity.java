package com.pcm.pcmmanager.nomal;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.common.ask.AskActivity;
import com.pcm.pcmmanager.common.notice.event.NoticeEventActivity;
import com.pcm.pcmmanager.common.notice.text.NoticeListActivity;
import com.pcm.pcmmanager.common.use_way.UseWayActivity;
import com.pcm.pcmmanager.data.CommonResult;
import com.pcm.pcmmanager.data.UserSignupResult;
import com.pcm.pcmmanager.login.LoginActivity;
import com.pcm.pcmmanager.manager.NetworkManager;
import com.pcm.pcmmanager.manager.PropertyManager;
import com.pcm.pcmmanager.nomal.drawer.NomalNavigationDrawer;
import com.pcm.pcmmanager.nomal.estimate_list.MyEstimateListActivity;
import com.pcm.pcmmanager.nomal.qna_list.MyQnaListActivity;

import java.io.IOException;

import okhttp3.Request;

public class NomalMainActivity extends AppCompatActivity implements NomalNavigationDrawer.OnMenuClickListener {
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new NomalUserParentFragment())
                    .commit();
        }
    }

    @Override
    public void onMenuClick(int menuId) {
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void onNavClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.nomal_drawer_ask:
                intent = new Intent(this, AskActivity.class);
                startActivity(intent);
                break;
            case R.id.nomal_drawer_notice:
                intent = new Intent(this, NoticeListActivity.class);
                startActivity(intent);
                break;
            case R.id.nomal_drawer_event:
                intent = new Intent(this, NoticeEventActivity.class);
                startActivity(intent);
                break;
            case R.id.nomal_drawer_my_estimate:
                intent = new Intent(this, MyEstimateListActivity.class);
                startActivity(intent);
                break;
            case R.id.nomal_drawer_use_way:
                intent = new Intent(this, UseWayActivity.class);
                startActivity(intent);
                break;
            case R.id.nomal_drawer_my_qna:
                intent = new Intent(this, MyQnaListActivity.class);
                startActivity(intent);
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

    }

    //로그아웃 및 회원탈퇴
    public void onLogout(View v) {
        switch (v.getId()) {
            case R.id.nomal_drawer_logout_btn:
                LogoutCheckDialog();
                break;
            case R.id.nomal_drawer_user_out_btn:
                UserOut();

        }
    }

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
                                    Toast.makeText(NomalMainActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                                else {
                                    // 로그아웃한다
                                    PropertyManager.getInstance().setAuthorizationToken("");
                                    Intent intent = new Intent(NomalMainActivity.this, LoginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
                                    Toast.makeText(NomalMainActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                                else {
                                    PropertyManager.getInstance().setAuthorizationToken("");
                                    Intent intent = new Intent(NomalMainActivity.this, LoginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
}
