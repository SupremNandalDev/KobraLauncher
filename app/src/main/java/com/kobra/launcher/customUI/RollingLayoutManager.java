/*
 * Code Author : Suprem Nandal (suprem.nandal@gmail.com, +919560787007)
 */

package com.kobra.launcher.customUI;

import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

public class RollingLayoutManager extends GridLayoutManager {


    public RollingLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public RollingLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public RollingLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        updateViewScale();
        super.onLayoutChildren(recycler, state);
    }

    @SuppressWarnings("IntegerDivisionInFloatingPointContext")
    private void updateViewScale() {
        float height = getHeight();
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            assert view != null;
            int childHeight = view.getHeight();
            float rotationTresholdPersent = 1f - (childHeight / height);
            int thresholdPx = (int) (height * (rotationTresholdPersent));
            float scale;
            int viewTop = getDecoratedTop(view);
            if (viewTop >= thresholdPx) {
                int delta = viewTop - thresholdPx;
                scale = (childHeight - delta) / (float) childHeight;
                scale = Math.max(scale, 0);
                view.setAlpha(0.1f + 0.9f * scale);
                view.setPivotX(view.getWidth() / 2);
                view.setPivotY(-view.getHeight() / 12);
                view.setRotationX(-90 * (1 - scale));
            } else {
                view.setRotationX(0);
                view.setAlpha(1f);
            }
        }
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        if (position >= getItemCount()) {
            return;
        }

        LinearSmoothScroller scroller = new LinearSmoothScroller(recyclerView.getContext()) {
            @Override
            public PointF computeScrollVectorForPosition(int targetPosition) {
                return RollingLayoutManager.this.computeScrollVectorForPosition(targetPosition);
            }

            @Override
            protected int getVerticalSnapPreference() {
                return SNAP_TO_START;
            }
        };
        scroller.setTargetPosition(position);
        startSmoothScroll(scroller);
    }

    public PointF computeScrollVectorForPosition(int targetPosition) {
        if (getChildCount() == 0) {
            return null;
        }
        final int firstChildPos = getPosition(Objects.requireNonNull(getChildAt(0)));
        final int direction = targetPosition < firstChildPos ? -1 : 1;
        return new PointF(0, direction);
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        updateViewScale();
        return super.scrollVerticallyBy(dy, recycler, state);
    }
}