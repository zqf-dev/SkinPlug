package com.zqf.skinplug;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.zqf.skinplug.base.BaseSkinActivity;
import com.zqf.skinplug.callback.ISkinChangingCallback;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseSkinActivity {

    @Bind(R.id.list_view)
    ListView listView;
    @Bind(R.id.plug_skin_btn)
    Button plugSkinBtn;
    @Bind(R.id.apply_skin_btn)
    Button applySkinBtn;
    @Bind(R.id.default_btn)
    Button defaultBtn;
    //皮肤插件包的存放路径
    private String skin_plugin_apk_path = Environment.getExternalStorageDirectory()
            + File.separator + "test_skin_plugin.apk";
    //皮肤插件包的包名
    private String skin_plugin_pkgname = "com.zqf.testskinplugdemo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @OnClick({R.id.plug_skin_btn, R.id.apply_skin_btn, R.id.default_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.plug_skin_btn:
                File SkinPlugApk_file = new File(skin_plugin_apk_path);
                if (!SkinPlugApk_file.exists()) {
                    Log.e("Tag", "插件包不存在");
                    return;
                }
                SkinManager.getsInstance().changeSkin(skin_plugin_apk_path, skin_plugin_pkgname, new ISkinChangingCallback() {
                    @Override
                    public void onStart() {
                        Log.e("Tag", "开始");
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e("Tag", "操作失败");
                    }

                    @Override
                    public void onComplete() {
                        Log.e("Tag", "完成");
                    }
                });
                break;
            case R.id.apply_skin_btn:
                SkinManager.getsInstance().changeSkin("blue");
                break;
            case R.id.default_btn:
                SkinManager.getsInstance().changeSkin("");
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
