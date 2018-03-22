package com.zqf.skinplug;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

/**
 * class from
 * Created by zqf
 * Time 2018/3/2 11:06
 */
public class ResourcesManage {

    private Resources mResources;
    private String mPkgName;
    private String mSuffix;

    public ResourcesManage(Resources resources, String mpkgname, String suffix) {
        this.mResources = resources;
        this.mPkgName = mpkgname;
        if (suffix == null) {
            suffix = "";
        }
        mSuffix = suffix;
    }

    public Drawable getDrawableByResName(String name) {
        try {
            name = appendSuffix(name);
            return mResources.getDrawable(mResources.getIdentifier(name, "mipmap", mPkgName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ColorStateList getColorByResName(String name) {
        try {
            name = appendSuffix(name);
            return mResources.getColorStateList(mResources.getIdentifier(name, "color", mPkgName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //应用内换肤资源文件add
    private String appendSuffix(String name) {
        if (!TextUtils.isEmpty(mSuffix)) {
            name += "_" + mSuffix;
        }
        return name;
    }
}
