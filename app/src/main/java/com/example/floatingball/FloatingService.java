
package com.example.floatingball;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.*;
import android.widget.Button;

public class FloatingService extends Service {

    WindowManager wm;
    View view;

    @Override
    public IBinder onBind(Intent intent) { return null; }

    @Override
    public void onCreate() {
        super.onCreate();

        wm = (WindowManager) getSystemService(WINDOW_SERVICE);

        Button btn = new Button(this);
        btn.setText("C5X");

        view = btn;

        WindowManager.LayoutParams p = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
        );

        p.x = 100;
        p.y = 200;

        btn.setOnTouchListener(new View.OnTouchListener() {
            int lastX, lastY;
            float downX, downY;

            public boolean onTouch(View v, MotionEvent e) {
                switch (e.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        lastX = p.x;
                        lastY = p.y;
                        downX = e.getRawX();
                        downY = e.getRawY();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        p.x = lastX + (int)(e.getRawX() - downX);
                        p.y = lastY + (int)(e.getRawY() - downY);
                        wm.updateViewLayout(view, p);
                        return true;
                }
                return false;
            }
        });

        wm.addView(view, p);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (view != null) wm.removeView(view);
    }
}
