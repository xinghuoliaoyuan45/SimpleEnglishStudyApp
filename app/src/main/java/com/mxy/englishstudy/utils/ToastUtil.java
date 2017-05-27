package com.mxy.englishstudy.utils;

import android.widget.Toast;

import com.mxy.englishstudy.app.SimpleEnglishStudyApplication;



public class ToastUtil {

    private static Toast mToast;

    public static void showToast(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(SimpleEnglishStudyApplication.getInstance(), text, Toast.LENGTH_SHORT);
        }
        mToast.setText(text);
        mToast.show();
    }
}
