package com.zqf.skinplug.attr;

import android.view.View;

import java.util.List;

/**
 * Created by zqf on 2018/3/21.
 */

public class SkinView {

    private View mView;
    private List<SkinAttr> mAttrs;

    public SkinView(View view, List<SkinAttr> attrs) {
        this.mView = view;
        this.mAttrs = attrs;
    }

    public View getmView() {
        return mView;
    }

    public void setmView(View mView) {
        this.mView = mView;
    }

    public List<SkinAttr> getmAttrs() {
        return mAttrs;
    }

    public void setmAttrs(List<SkinAttr> mAttrs) {
        this.mAttrs = mAttrs;
    }

    public void apply() {
        for (SkinAttr skinAttr : mAttrs) {
            skinAttr.apply(mView);
        }
    }
}
