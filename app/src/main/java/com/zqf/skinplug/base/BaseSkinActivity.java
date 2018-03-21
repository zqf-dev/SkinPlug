package com.zqf.skinplug.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;

import com.zqf.skinplug.SkinManager;
import com.zqf.skinplug.attr.SkinAttr;
import com.zqf.skinplug.attr.SkinAttrSupport;
import com.zqf.skinplug.attr.SkinView;
import com.zqf.skinplug.callback.ISkinChangedListener;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * class from
 * Created by zqf
 * Time 2018/3/21 11:17
 */

public class BaseSkinActivity extends AppCompatActivity implements ISkinChangedListener {
    private final Object[] mConstructorArgs = new Object[2];
    private static final Map<String, Constructor<? extends View>> sConstructorMap = new ArrayMap<>();
    private static final Class<?>[] sConstructorSignature = new Class[]{
            Context.class, AttributeSet.class};
    private static final String[] sClassPrefixList = {
            "android.widget.",
            "android.view.",
            "android.webkit."
    };
    private Method mCreateViewMethod = null;
    static final Class<?>[] mCreateViewSignature = new Class[]{View.class, String.class, Context.class, AttributeSet.class};
    private final Object[] mCreateViewArgs = new Object[4];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        //注册Activity，避免内存泄露
        SkinManager.getsInstance().registListener(this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LayoutInflaterCompat.setFactory(inflater, new LayoutInflaterFactory() {
            @Override
            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {

                //判断系统是否使用
                //完成AppCompat factory的工作
                AppCompatDelegate delegate = getDelegate();
                View mView = null;
                List<SkinAttr> skinAttrs = null;

                try {
                    if (mCreateViewMethod == null) {
                        mCreateViewMethod = delegate.getClass().getMethod("createView", mCreateViewSignature);
                    }
                    mCreateViewArgs[0] = parent;
                    mCreateViewArgs[1] = name;
                    mCreateViewArgs[2] = context;
                    mCreateViewArgs[3] = attrs;
                    mView = (View) mCreateViewMethod.invoke(delegate, mCreateViewArgs);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (mView == null) {
                    mView = createViewFromTag(context, name, attrs);
                }
                skinAttrs = SkinAttrSupport.getSkinAttrs(attrs, context);
                if (skinAttrs.isEmpty()) {
                    return null;
                }
                if (mView != null) {
                    injectSkin(mView, skinAttrs);
                }
                return mView;
            }
        });
        super.onCreate(savedInstanceState);
    }

    //
    private void injectSkin(View mView, List<SkinAttr> skinAttrs) {
        List<SkinView> skinViews = SkinManager.getsInstance().getSkinViews(this);
        if (skinViews == null) {
            skinViews = new ArrayList<>();
            SkinManager.getsInstance().addSkinView(this, skinViews);
        }
        skinViews.add(new SkinView(mView, skinAttrs));

        //当前是否需要自动换肤，


    }

    private View createView(Context context, String name, String prefix)
            throws ClassNotFoundException, InflateException {
        Constructor<? extends View> constructor = sConstructorMap.get(name);

        try {
            if (constructor == null) {
                // Class not found in the cache, see if it's real, and try to add it
                Class<? extends View> clazz = context.getClassLoader().loadClass(
                        prefix != null ? (prefix + name) : name).asSubclass(View.class);

                constructor = clazz.getConstructor(sConstructorSignature);
                sConstructorMap.put(name, constructor);
            }
            constructor.setAccessible(true);
            return constructor.newInstance(mConstructorArgs);
        } catch (Exception e) {
            // We do not want to catch these, lets return null and let the actual LayoutInflater
            // try
            return null;
        }
    }

    private View createViewFromTag(Context context, String name, AttributeSet attrs) {
        if (name.equals("view")) {
            name = attrs.getAttributeValue(null, "class");
        }
        try {
            mConstructorArgs[0] = context;
            mConstructorArgs[1] = attrs;
            if (-1 == name.indexOf('.')) {
                for (int i = 0; i < sClassPrefixList.length; i++) {
                    final View view = createView(context, name, sClassPrefixList[i]);
                    if (view != null) {
                        return view;
                    }
                }
                return null;
            } else {
                return createView(context, name, null);
            }
        } catch (Exception e) {
            // We do not want to catch these, lets return null and let the actual LayoutInflater
            // try
            return null;
        } finally {
            // Don't retain references on context.
            mConstructorArgs[0] = null;
            mConstructorArgs[1] = null;
        }
    }

    @Override
    public void onSkinChanged() {

    }

    @Override
    protected void onDestroy() {
        SkinManager.getsInstance().unregistListener(this);
        super.onDestroy();
    }
}
