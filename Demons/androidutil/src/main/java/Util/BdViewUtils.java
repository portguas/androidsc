package Util;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;

/**
 * Created by wangbole on 2014/6/5.
 * 
 * 提供View的相关公共方法
 * 
 */
public class BdViewUtils {

    public static OnTouchListener createOnTouchListener() {
        return new BdViewOnTouchListener();
    }

    static class BdViewOnTouchListener implements OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    AlphaAnimation alpha = new AlphaAnimation(1, 0.7f);
                    alpha.setDuration(0);
                    alpha.setFillAfter(true);
                    v.startAnimation(alpha);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    AlphaAnimation a = new AlphaAnimation(0.7f, 1);
                    a.setDuration(0);
                    v.startAnimation(a);
                    break;
                default:
                    break;
            }

            return false;
        }
    }

    public static void removeFromParent(View v) {
        if (v != null && v.getParent() != null) {
            ((ViewGroup) v.getParent()).removeView(v);
        }
    }
}
