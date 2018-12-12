package com.example.administrator.keyboarddemo;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class MyScrollview extends ScrollView {
    private ScrollViewListener scrollViewListener = null;

    public MyScrollview(Context context) {
        super(context);
    }

    public MyScrollview(Context context, AttributeSet attrs,
                        int defStyle) {
        super(context, attrs, defStyle);
    }

    public MyScrollview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }
    public interface ScrollViewListener {
        void onScrollChanged(MyScrollview scrollView, int x, int y, int oldx, int oldy);
    }
}
