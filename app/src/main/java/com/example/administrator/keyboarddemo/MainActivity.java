package com.example.administrator.keyboarddemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.dreamtobe.kpswitch.util.KPSwitchConflictUtil;
import cn.dreamtobe.kpswitch.util.KeyboardUtil;
import cn.dreamtobe.kpswitch.widget.KPSwitchPanelLinearLayout;

public class MainActivity extends AppCompatActivity implements BottomCommentView.OnItemClickListener {

    private static final String TAG = "MainActivity";
    @BindView(R.id.ll_title_back)
    LinearLayout llTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right_text)
    TextView tvRightText;
    @BindView(R.id.iv_right_image)
    ImageView ivRightImage;
    @BindView(R.id.iv_line_bottom)
    ImageView ivLineBottom;
    @BindView(R.id.sv_content)
    MyScrollview svContent;
    @BindView(R.id.iv_emoji)
    ImageView ivEmoji;
    @BindView(R.id.tv_send)
    TextView tvSend;
    @BindView(R.id.edit_comment)
    EditText editComment;
    @BindView(R.id.panel_root)
    KPSwitchPanelLinearLayout panelRoot;
    @BindView(R.id.bottom_comment_view)
    BottomCommentView bottomCommentView;
    @BindView(R.id.ly_comment_edit)
    LinearLayout lyComment;


    private boolean editFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setKeyBoard();
        initListener();
    }

    private void initListener() {
        svContent.setScrollViewListener((scrollView, x, y, oldx, oldy) -> {
            View childAt = scrollView.getChildAt(0);
            int childHeight = childAt.getHeight();//获取子控件高度
            int height = scrollView.getHeight();//获取Scrollview的控件高度
            if (y + height == childHeight) {//判断条件 当子控件高度=Scrollview的控件高度+x的时候控件到达底部
                bottomCommentView.setGoTop();
            } else bottomCommentView.setComment();

        });
        tvSend.setOnClickListener(v -> {
            KPSwitchConflictUtil.hidePanelAndKeyboard(panelRoot);
            lyComment.setVisibility(View.GONE);
            bottomCommentView.setVisibility(View.VISIBLE);
            editFlag = false;
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setKeyBoard() {
        bottomCommentView.setCommentNum(22);
        bottomCommentView.setListener(this);
        KeyboardUtil.attach(this, panelRoot,
                isShowing -> Log.d(TAG, String.format("Keyboard is %s", isShowing
                        ? "showing" : "hiding")));

        KPSwitchConflictUtil.attach(panelRoot, ivEmoji, editComment,
                switchToPanel -> {
                    if (switchToPanel) {
                        editComment.clearFocus();
                    } else {
                        editComment.requestFocus();
                    }
                });
        svContent.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                KPSwitchConflictUtil.hidePanelAndKeyboard(panelRoot);
                lyComment.setVisibility(View.GONE);
                bottomCommentView.setVisibility(View.VISIBLE);
            }
            return false;
        });

    }

    @Override
    public void onBackPressed() {
        if (editFlag) {
            lyComment.setVisibility(View.GONE);
            bottomCommentView.setVisibility(View.VISIBLE);
            editFlag = false;
            KPSwitchConflictUtil.hidePanelAndKeyboard(panelRoot);
        } else
            super.onBackPressed();
    }

    @Override
    public void onCommentClick() {
        bottomCommentView.setVisibility(View.GONE);
        lyComment.setVisibility(View.VISIBLE);
        editComment.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editComment, 0);
        editFlag = true;
    }

    @Override
    public void onGoTopClick() {
        svContent.fullScroll(View.FOCUS_UP);
    }

    @Override
    public void onGoodClick() {

    }

    @Override
    public void onShareClick() {

    }

    @Override
    public void onCommentListClick() {

    }
}
