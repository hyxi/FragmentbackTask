package com.mikepenz.iconics.icon_typeface;

import android.content.Context;
import android.graphics.Typeface;

import com.mikepenz.iconics.typeface.IIcon;
import com.mikepenz.iconics.typeface.ITypeface;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by huangyuxi on 2018/12/11
 * Title: icon 处理
 */
public class Entypo implements ITypeface {

    private static final String TTF_FILE = "iconfont.ttf";
    private static Typeface typeface = null;
    private static HashMap<String, Character> mChars;

    @Override
    public IIcon getIcon(String key) {
        return Icon.valueOf(key);
    }

    @Override
    public HashMap<String, Character> getCharacters() {
        if (mChars == null) {
            HashMap<String, Character> aChars = new HashMap<>();
            for (Icon v : Icon.values()) {
                aChars.put(v.name(), v.character);
            }
            mChars = aChars;
        }
        return mChars;
    }

    @Override
    public String getMappingPrefix() {
        return "ico";
    }

    @Override
    public String getFontName() {
        return null;
    }

    @Override
    public String getVersion() {
        return null;
    }

    @Override
    public int getIconCount() {
        return  mChars.size();
    }

    @Override
    public Collection<String> getIcons() {
        Collection<String> icons = new LinkedList<>();
        for (Icon value : Icon.values()) {
            icons.add(value.name());
        }
        return icons;
    }

    @Override
    public String getAuthor() {
        return null;
    }

    @Override
    public String getUrl() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String getLicense() {
        return null;
    }

    @Override
    public String getLicenseUrl() {
        return null;
    }

    @Override
    public Typeface getTypeface(Context context) {
        if (typeface == null) {
            try {
                typeface = Typeface.createFromAsset(context.getAssets(), "fonts/" + TTF_FILE);
            } catch (Exception e) {
                return null;
            }
        }
        return typeface;
    }

    public enum Icon implements IIcon {
        icon_weib('\ue64b'),
        icon_weixin('\ue60b'),
        icon_gongnengdingy('\ueb62'),
        icon_open_folder('\ue72c'),
        icon_quanxianshenpi('\ueb65');

        char character;

        Icon(char character) {
            this.character = character;
        }

        public String getFormattedName() {
            return "{" + name() + "}";
        }

        public char getCharacter() {
            return character;
        }

        public String getName() {
            return name();
        }

        private static ITypeface typeface;

        @Override
        public ITypeface getTypeface() {
            if (typeface == null) {
                typeface = new Entypo();
            }
            return typeface;
        }
    }
}
