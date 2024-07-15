package io.github.haoyiwen.uikit.powerfulrecyclerview;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class PowerfulRecyclerView extends RecyclerView {
    public PowerfulRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public PowerfulRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PowerfulRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
