package com.zqf.skinplug.attr;

import android.content.Context;
import android.util.AttributeSet;

import com.zqf.skinplug.config.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zqf on 2018/3/21.
 */

public class SkinAttrSupport {

    public static List<SkinAttr> getSkinAttrs(AttributeSet attrs, Context context) {

        List<SkinAttr> mSkinAttrsList = new ArrayList<>();
        SkinAttr skinAttr = null;
        SkinAttrType attrType = null;
        for (int i = 0, n = attrs.getAttributeCount(); i < n; i++) {
            String attrName = attrs.getAttributeName(i);
            String attrVal = attrs.getAttributeValue(i);
            if (attrVal.startsWith("@")) {
                int id = -1;
                try {
                    id = Integer.parseInt(attrVal.substring(1));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (id == -1) {
                    continue;
                }
                String resName = context.getResources().getResourceEntryName(id);
                if (resName.startsWith(Constant.SKIN_PREFIX)) {
                    attrType = getSupportAttrType(attrName);
                    if (attrType == null) continue;
                    skinAttr = new SkinAttr(resName, attrType);
                    mSkinAttrsList.add(skinAttr);
                }
            }
        }
        return mSkinAttrsList;
    }


    private static SkinAttrType getSupportAttrType(String attrName) {
        for (SkinAttrType attrType : SkinAttrType.values()) {
            if (attrType.getResType().equals(attrName)) {
                return attrType;
            }
        }
        return null;
    }

}
