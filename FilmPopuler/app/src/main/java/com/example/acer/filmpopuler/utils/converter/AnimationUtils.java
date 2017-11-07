package com.example.acer.filmpopuler.utils.converter;

import android.view.View;
import android.view.animation.AlphaAnimation;

/**
 * Created by ACER on 24/10/2017.
 */

public class AnimationUtils {
    public static void startAlphaAnimation(View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }
}
