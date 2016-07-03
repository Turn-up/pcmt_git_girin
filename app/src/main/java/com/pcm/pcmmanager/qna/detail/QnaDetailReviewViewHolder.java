package com.pcm.pcmmanager.qna.detail;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pcm.pcmmanager.R;
import com.pcm.pcmmanager.data.CommonResult;
import com.pcm.pcmmanager.data.QnaDetailReviewList;
import com.pcm.pcmmanager.data.UserProfileResult;
import com.pcm.pcmmanager.manager.NetworkManager;

import java.io.IOException;

import okhttp3.Request;

/**
 * Created by LG on 2016-06-28.
 */
public class QnaDetailReviewViewHolder extends RecyclerView.ViewHolder {
    TextView name, mainContent, officeName, career, comment,delete;
    ImageView photo;
    CheckBox checkBox;
    QnaDetailReviewList mList;

    public interface OnItemClickListener {
        public void onItemClick(View view, QnaDetailReviewList mList);
    }

    OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public QnaDetailReviewViewHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.qna_detail_expert_name);
        mainContent = (TextView) itemView.findViewById(R.id.qna_detail_expert_content);
        officeName = (TextView) itemView.findViewById(R.id.qna_detail_expert_office);
        career = (TextView) itemView.findViewById(R.id.qna_detail_expert_career);
        comment = (TextView) itemView.findViewById(R.id.qna_detail_expert_comment);
        photo = (ImageView) itemView.findViewById(R.id.qna_detail_expert_image);
        checkBox = (CheckBox) itemView.findViewById(R.id.qna_detail_review_like_checkBox);
        delete = (TextView)itemView.findViewById(R.id.qna_detail_review_delete);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(v, mList);
                }
            }
        });
    }

    public void setReviewData(QnaDetailReviewList qnaDetailReviewList, final String qnasn) {
        mList = qnaDetailReviewList;
        Glide.with(photo.getContext()).load(qnaDetailReviewList.getPhoto()).into(photo);
        name.setText(qnaDetailReviewList.getExpertname());
        mainContent.setText(qnaDetailReviewList.getMainintroduce());
        officeName.setText(qnaDetailReviewList.getOfficename());
        career.setText(qnaDetailReviewList.getAge() + "세, 경력 " + qnaDetailReviewList.getCareer() + "년");
        comment.setText(qnaDetailReviewList.getContent());
        checkBox.setText(String.valueOf(qnaDetailReviewList.getLikecount()));
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
                NetworkManager.getInstance().getQnaReviewLike(mList.get_id(), qnasn, new NetworkManager.OnResultListener<CommonResult>() {
                    @Override
                    public void onSuccess(Request request, CommonResult result) {
                        if (result.getResult() == -1) {
                            Toast.makeText(itemView.getContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            if (isChecked) {
                                checkBox.setText(String.valueOf(Integer.valueOf(checkBox.getText().toString()) + 1));
                            } else
                                checkBox.setText(String.valueOf(Integer.valueOf(checkBox.getText().toString()) - 1));
                        }
                    }

                    @Override
                    public void onFail(Request request, IOException exception) {

                    }
                });
            }
        });
        NetworkManager.getInstance().getUserProfile(new NetworkManager.OnResultListener<UserProfileResult>() {
            @Override
            public void onSuccess(Request request, UserProfileResult result) {
                if(result.getResult() == -1) {
                    Toast.makeText(itemView.getContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
                }else{
                    if(mList.getExpertsn()==result.getUserProfile().getExpertsn()){
                        delete.setVisibility(View.VISIBLE);
                        delete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DeleteDialog(qnasn);
                            }
                        });
                    }
                }
            }

            @Override
            public void onFail(Request request, IOException exception) {

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
                        NetworkManager.getInstance().getQnaReviewDelete(qnasn,mList.get_id(),new NetworkManager.OnResultListener<CommonResult>() {
                            @Override
                            public void onSuccess(Request request, CommonResult result) {
                                if (result.getResult() == -1)
                                    Toast.makeText(itemView.getContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
                                else {
                                    Toast.makeText(itemView.getContext(), "삭제됐습니다.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(itemView.getContext(), QnaDetailActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
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
