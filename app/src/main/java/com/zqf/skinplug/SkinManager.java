package com.zqf.skinplug;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.AsyncTask;

import com.zqf.skinplug.attr.SkinView;
import com.zqf.skinplug.callback.ISkinChangedListener;
import com.zqf.skinplug.callback.ISkinChangingCallback;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zqf on 2018/3/21.
 */

public class SkinManager {

    private static SkinManager sInstance = null;
    private Context mContext;
    private ResourcesManage mResourcesManage;
    private Map<ISkinChangedListener, List<SkinView>> mSkinViewMaps = new HashMap<>();
    private List<ISkinChangedListener> mSkinListeners = new ArrayList<>();

    public SkinManager() {

    }

    public static SkinManager getsInstance() {
        if (sInstance == null) {
            synchronized (SkinManager.class) {
                if (sInstance == null) {
                    sInstance = new SkinManager();
                }
            }
        }
        return sInstance;
    }

    public void init(Context context) {
        mContext = context.getApplicationContext();
    }

    public ResourcesManage getResourcesManage() {
        return mResourcesManage;
    }

    private void loadskinpkg(String skin_plugin_apk_path, String skin_plugin_pkgname) {
        try {
            //AssetManager
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPathMethod = assetManager.getClass().getMethod("addAssetPath", String.class);
            addAssetPathMethod.invoke(assetManager, skin_plugin_apk_path);
            Resources superResources = mContext.getResources();
            Resources resources = new Resources(assetManager,
                    superResources.getDisplayMetrics(), superResources.getConfiguration());
            mResourcesManage = new ResourcesManage(resources, skin_plugin_pkgname);

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    public List<SkinView> getSkinViews(ISkinChangedListener listener) {
        return mSkinViewMaps.get(listener);
    }

    public void addSkinView(ISkinChangedListener listener, List<SkinView> views) {
        mSkinViewMaps.put(listener, views);
    }

    public void registListener(ISkinChangedListener listener) {
        mSkinListeners.add(listener);
    }

    public void unregistListener(ISkinChangedListener listener) {
        mSkinListeners.remove(listener);
        mSkinViewMaps.remove(listener);
    }

    public void changeSkin(final String skin_plugin_apk_path, final String skin_plugin_pkgname, ISkinChangingCallback iSkinChangingCallback) {
        if (iSkinChangingCallback == null) {
            iSkinChangingCallback = ISkinChangingCallback.DEFAULT_SKIN_CHANGE_CALLBACK;
        }

        final ISkinChangingCallback callback = iSkinChangingCallback;
        callback.onStart();
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    loadskinpkg(skin_plugin_apk_path, skin_plugin_pkgname);
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onError(e);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                try {
                    notifyChangedListener();
                    callback.onComplete();
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onError(e);
                }

            }
        }.execute();
    }

    private void notifyChangedListener() {
        for (ISkinChangedListener listener : mSkinListeners) {
            skinChanged(listener);
            listener.onSkinChanged();
        }
    }

    private void skinChanged(ISkinChangedListener listener) {
        List<SkinView> skinViews = mSkinViewMaps.get(listener);
        for (SkinView skinView : skinViews) {
            skinView.apply();
        }
    }
}
