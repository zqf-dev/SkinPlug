package com.zqf.skinplug.attr;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zqf.skinplug.ResourcesManage;
import com.zqf.skinplug.SkinManager;

/**
 * Created by zqf on 2018/3/21.
 * 采用枚举类型
 */

public enum SkinAttrType {

    BACKGROUND("background") {
        @Override
        public void apply(View mView, String mResName) {
            Drawable drawable = getResourceManager().getDrawableByResName(mResName);
            if (drawable != null)
                mView.setBackgroundDrawable(drawable);
        }
    }, SRC("src") {
        @Override
        public void apply(View mView, String mResName) {
            Drawable drawable = getResourceManager().getDrawableByResName(mResName);
            if (mView instanceof ImageView) {
                ImageView imageView = (ImageView) mView;
                if (drawable != null)
                    imageView.setImageDrawable(drawable);
            }
        }
    }, TEXT_COLOR("textColor") {
        @Override
        public void apply(View mView, String mResName) {
            ColorStateList colorStateList = getResourceManager().getColorByResName(mResName);
            if (mView instanceof TextView) {
                TextView textView = (TextView) mView;
                if (colorStateList != null)
                    textView.setTextColor(colorStateList);
            }
        }
    };

    String resType;

    public String getResType() {
        return resType;
    }

    SkinAttrType(String type) {
        resType = type;
    }

    public abstract void apply(View mView, String mResName);

    public ResourcesManage getResourceManager() {
        return SkinManager.getsInstance().getResourcesManage();
    }
}
