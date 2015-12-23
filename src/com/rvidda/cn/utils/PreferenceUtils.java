package com.rvidda.cn.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class PreferenceUtils
{

    private SharedPreferences preferences;

    private Context ctx;

    public static PreferenceUtils preferenceUtils;

    public PreferenceUtils(Context context, String name)
    {

        this.ctx = context;
        preferences = ctx.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public PreferenceUtils(Context context)
    {

        this.ctx = context;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static PreferenceUtils getInstance(Context context)
    {
        if (preferenceUtils == null)
        {
            preferenceUtils = new PreferenceUtils(context);
        }
        return preferenceUtils;
    }

    public static PreferenceUtils getInstance(Context context, String name)
    {
        if (preferenceUtils == null)
        {
            preferenceUtils = new PreferenceUtils(context, name);
        }
        return preferenceUtils;
    }

    public void put(String key, Object value)
    {
        Editor editor = preferences.edit();

        if (value.getClass() == String.class)
        {
            editor.putString(key, (String) value);
        }
        else if (value.getClass() == Integer.class)
        {
            editor.putInt(key, ((Integer) value).intValue());
        }
        else if (value.getClass() == Float.class)
        {
            editor.putFloat(key, ((Float) value).floatValue());
        }
        else if (value.getClass() == Long.class)
        {
            editor.putLong(key, ((Long) value).longValue());
        }
        else if (value.getClass() == Boolean.class)
        {
            editor.putBoolean(key, (Boolean) value);
        }
        editor.commit();
    }

    public String getString(String key, String defValue)
    {
        return preferences.getString(key, defValue);
    }

    public int getInt(String key, int defValue)
    {
        return preferences.getInt(key, defValue);
    }

    public float getFloat(String key, float defValue)
    {
        return preferences.getFloat(key, defValue);
    }

    public long getLong(String key, long defValue)
    {
        return preferences.getLong(key, defValue);
    }

    public boolean getBoolean(String key, boolean defValue)
    {
        return preferences.getBoolean(key, defValue);
    }

    public void clearPreference()
    {
        Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }
}
