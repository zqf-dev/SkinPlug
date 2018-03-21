package com.zqf.skinplug.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.zqf.skinplug.R;

/**
 * class from
 * Created by zqf
 * Time 2018/3/21 11:17
 */

public class BaseSkinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LayoutInflaterCompat.setFactory(inflater, new LayoutInflaterFactory() {
            @Override
            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
                Log.e("Tag", name);
                for (int i = 0, n = attrs.getAttributeCount(); i < n; i++) {
                    String attrs_name = attrs.getAttributeName(i);
                    String attrs_value = attrs.getAttributeValue(i);
                    Log.e("Tag", attrs_name + "--" + attrs_value);
                }
                if (name.equals("TextView")) {
                    return new EditText(context, attrs);
                }
                return null;
            }
        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);
    }
}
