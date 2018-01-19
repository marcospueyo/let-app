package com.mph.letapp.presentation.view.component;

import android.view.MotionEvent;
import android.view.View;


public class SwipeDetector implements View.OnTouchListener {

    public static final String TAG = SwipeDetector.class.getSimpleName();

    private final int minDistance;

    private final SwipeListener listener;

    private float downX, upX;

    public interface SwipeListener {
        void onSwipeLeft();
        void onSwipeRight();
    }

    public SwipeDetector(int minDistance, SwipeListener listener) {
        this.minDistance = minDistance;
        this.listener = listener;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                downX = motionEvent.getX();
                return true;
            }
            case MotionEvent.ACTION_UP: {
                upX = motionEvent.getX();

                float deltaX = downX - upX;
                if (Math.abs(deltaX) > minDistance) {
                    if (deltaX < 0)
                        listener.onSwipeLeft();
                    else if (deltaX > 0)
                        listener.onSwipeRight();
                }
                return true;
            }
        }
        return false;
    }
}
