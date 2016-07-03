package com.pcm.pcmmanager.qna.detail;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.data.CommonResult;
import com.pcm.pcmmanager.data.QnaDetail;
import com.pcm.pcmmanager.manager.NetworkManager;
import com.pcm.pcmmanager.nomal.qna_list.MyQnaListActivity;
import com.pcm.pcmmanager.qna.ask.QnaAskActivity;

import java.io.IOException;

import okhttp3.Request;

/**
 * Created by LG on 2016-06-28.
 */
public class QnaDetailContentViewHolder extends RecyclerView.ViewHolder {
    TextView title, content, delete, edit;
    public QnaDetailContentViewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.qna_detail_title);
        content = (TextView) itemView.findViewById(R.id.qna_detail_content);
        delete = (TextView) itemView.findViewById(R.id.qna_detail_delete);
        edit = (TextView) itemView.findViewById(R.id.qna_detail_edit);

    }

    public void setContentData(QnaDetail qnaDetail) {
        title.setText(qnaDetail.getTitle());
        content.setText(qnaDetail.getContent());
    }

    public void setContentData(final QnaDetail qnaDetail, final String qnasn, String myList) {
        title.setText(qnaDetail.getTitle());
        content.setText(qnaDetail.getContent());
        delete.setVisibility(View.VISIBLE);
        edit.setVisibility(View.VISIBLE);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteDialog(qnasn);
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(itemView.getContext(), QnaAskActivity.class);
                intent.putExtra("title",title.getText().toString());
                intent.putExtra("content",content.getText().toString());
                intent.putExtra("secret",qnaDetail.isSecretyn());
                intent.putExtra("qnaSn",qnasn);
                itemView.getContext().startActivity(intent);
            }
        });
    }
    private void DeleteDialog(final String qnasn) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(itemView.getContext());
        // AlertDialog 셋팅
        alertDialogBuilder
                .setMessage("삭제하시겠습니까?")
                .setCancelable(false)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        NetworkManager.getInstance().getQnaDelte(qnasn,new NetworkManager.OnResultListener<CommonResult>() {
                            @Override
                            public void onSuccess(Request request, CommonResult result) {
                                if (result.getResult() == -1)
                                    Toast.makeText(itemView.getContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
                                else {
                                    Toast.makeText(itemView.getContext(), "삭제됐습니다.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(itemView.getContext(), MyQnaListActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    itemView.getContext().startActivity(intent);
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
}
