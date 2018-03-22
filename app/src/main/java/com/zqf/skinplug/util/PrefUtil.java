package com.zqf.skinplug.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.zqf.skinplug.config.Constant;

/**
 * class from 保存工具类
 * Created by zqf
 * Time 2018/3/22 10:53
 */

public class PrefUtil {

    private Context mContext;

    public PrefUtil(Context context) {
        this.mContext = context;
    }

    //保存插件包路径
    public void saveSkinPlug(String path) {
        SharedPreferences sp = mContext.getSharedPreferences(Constant.SKIN_PLUG_PREF_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(Constant.KEY_SKIN_PLUG_PATH, path).apply();
    }

    //得到插件包路径
    public String getSkinPlug() {
        SharedPreferences sp = mContext.getSharedPreferences(Constant.SKIN_PLUG_PREF_NAME, Context.MODE_PRIVATE);
        return sp.getString(Constant.KEY_SKIN_PLUG_PATH, "");
    }

    //保存插件包名
    public void saveSkinPkg(String pkg) {
        SharedPreferences sp = mContext.getSharedPreferences(Constant.SKIN_PLUG_PREF_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(Constant.KEY_SKIN_PLUG_PKG, pkg).apply();
    }

    //得到插件包名
    public String getSkinPkg() {
        SharedPreferences sp = mContext.getSharedPreferences(Constant.SKIN_PLUG_PREF_NAME, Context.MODE_PRIVATE);
        return sp.getString(Constant.KEY_SKIN_PLUG_PKG, "");
    }

    //保存应用内换肤
    public void saveSuffix(String suffix) {
        SharedPreferences sp = mContext.getSharedPreferences(Constant.SKIN_PLUG_PREF_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(Constant.KEY_LOCAL_PLUG, suffix).apply();
    }

    //得到应用内换肤
    public String getSuffix() {
        SharedPreferences sp = mContext.getSharedPreferences(Constant.SKIN_PLUG_PREF_NAME, Context.MODE_PRIVATE);
        return sp.getString(Constant.KEY_LOCAL_PLUG, "");
    }

    public void clear() {
        SharedPreferences sp = mContext.getSharedPreferences(Constant.SKIN_PLUG_PREF_NAME, Context.MODE_PRIVATE);
        sp.edit().clear().apply();
    }
}
