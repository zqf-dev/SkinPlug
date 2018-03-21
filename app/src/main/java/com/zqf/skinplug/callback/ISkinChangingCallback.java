package com.zqf.skinplug.callback;

/**
 * Created by zqf on 2018/3/21.
 */

public interface ISkinChangingCallback {

    void onStart();

    void onError(Exception e);

    void onComplete();

    public static DefaultSkinChangeCallback DEFAULT_SKIN_CHANGE_CALLBACK = new DefaultSkinChangeCallback();

    public class DefaultSkinChangeCallback implements ISkinChangingCallback {

        @Override
        public void onStart() {

        }

        @Override
        public void onError(Exception e) {

        }

        @Override
        public void onComplete() {

        }
    }
}
