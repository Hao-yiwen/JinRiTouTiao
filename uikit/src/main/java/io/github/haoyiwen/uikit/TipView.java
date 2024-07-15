package io.github.haoyiwen.uikit;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class TipView extends LinearLayout {


    public TipView(Context context) {
        this(context, null);
    }

    public TipView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TipView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
