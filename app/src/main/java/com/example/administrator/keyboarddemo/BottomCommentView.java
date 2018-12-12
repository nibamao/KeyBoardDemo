package com.example.administrator.keyboarddemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import butterknife.BindView;
import butterknife.ButterKnife;

public class BottomCommentView extends LinearLayout implements View.OnClickListener {

    @BindView(R.id.tv_comment)
    TextView tvComment;
    @BindView(R.id.iv_comment_and_go_top)
    ImageView ivCommentAndGoTop;
    @BindView(R.id.iv_good)
    ImageView ivGood;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.tv_comment_num)
    TextView tvCommentNum;

    private boolean goTopFlag = false;
    private int commentNum;

    public BottomCommentView(Context context) {
        this(context, null);
    }

    public BottomCommentView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomCommentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.bottom_comment_view_layout, this);
        ButterKnife.bind(this, view);
        setChildViewOnclick();
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public void setGoTop() {
        // TODO: 2018/12/7 变更为回到顶部
        ivCommentAndGoTop.setImageResource(R.drawable.top);
        tvCommentNum.setVisibility(GONE);
        goTopFlag = true;
    }

    public void setComment() {
        if (commentNum > 0){
            // TODO: 2018/12/7 变更为评论
            ivCommentAndGoTop.setImageResource(R.drawable.comment);
            tvCommentNum.setVisibility(VISIBLE);
            tvCommentNum.setText(commentNum+"");
        }
        goTopFlag = false;
    }

    private void setChildViewOnclick() {
        tvComment.setOnClickListener(this);
        ivCommentAndGoTop.setOnClickListener(this);
        ivGood.setOnClickListener(this);
        ivShare.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_comment) {
            listener.onCommentClick();
        }
        if (v.getId() == R.id.iv_comment_and_go_top) {
            if (goTopFlag)
                listener.onGoTopClick();
            else listener.onCommentListClick();
        }
        if (v.getId() == R.id.iv_good) {
            listener.onGoodClick();
        }
        if (v.getId() == R.id.iv_share) {
            listener.onShareClick();
        }
    }

    private OnItemClickListener listener;

    public BottomCommentView setListener(OnItemClickListener listener) {
        this.listener = listener;
        return this;
    }

    public interface OnItemClickListener {
        void onCommentClick();

        void onGoTopClick();

        void onGoodClick();

        void onShareClick();

        void onCommentListClick();
    }
}
