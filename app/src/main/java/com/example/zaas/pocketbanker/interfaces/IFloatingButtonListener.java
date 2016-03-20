package com.example.zaas.pocketbanker.interfaces;

import android.content.Context;
import android.graphics.drawable.Drawable;

/**
 * Created by akhil on 3/20/16.
 */
public interface IFloatingButtonListener {
    Drawable getFloatingButtonDrawable(Context context);
    void onFloatingButtonPressed();
}
