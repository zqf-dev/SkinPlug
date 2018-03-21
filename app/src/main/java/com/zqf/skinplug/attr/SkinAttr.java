package com.zqf.skinplug.attr;

import android.view.View;

/**
 * Created by zqf on 2018/3/21.
 */

public class SkinAttr {

    private String mResName;
    private SkinAttrType mType;

    public SkinAttr(String mResName, SkinAttrType mType) {
        this.mResName = mResName;
        this.mType = mType;
    }

    public String getmResName() {
        return mResName;
    }

    public void setmResName(String mResName) {
        this.mResName = mResName;
    }

    public SkinAttrType getmType() {
        return mType;
    }

    public void setmType(SkinAttrType mType) {
        this.mType = mType;
    }

    public void apply(View mView) {
        mType.apply(mView, mResName);
    }
}
