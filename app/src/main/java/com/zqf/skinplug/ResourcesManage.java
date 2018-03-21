package com.zqf.skinplug;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

/**
 * class from
 * Created by zqf
 * Time 2018/3/2 11:06
 */
public class ResourcesManage {

    private Resources mResources;
    private String mPkgName;

    public ResourcesManage(Resources resources, String mpkgname) {
        this.mResources = resources;
        this.mPkgName = mpkgname;
    }

    public Drawable getDrawableByResName(String name) {
        try {
            return mResources.getDrawable(mResources.getIdentifier(name, "mipmap", mPkgName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ColorStateList getColorByResName(String name) {
        try {
            return mResources.getColorStateList(mResources.getIdentifier(name, "color", mPkgName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
