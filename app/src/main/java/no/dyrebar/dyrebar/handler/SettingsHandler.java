package no.dyrebar.dyrebar.handler;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SettingsHandler
{
    private Context context;

    /**
     * Requires Context to be able to access shared preference
     *
     * @param context Any type of valid context
     */
    public SettingsHandler(Context context)
    {
        this.context = context;
    }

    /**
     * Setting String value to shared preferences
     *
     * @param key   Name of Setting
     * @param value Value of Setting
     */
    public void setStringSetting(String key, String value)
    {
        SharedPreferences sp = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        SharedPreferences.Editor spe = sp.edit();
        spe.putString(key, value);
        spe.apply();
    }

    /**
     * Setting Boolean value to shared preference
     *
     * @param key   Name of Setting
     * @param value Value of Setting
     */
    public void setBooleanSetting(String key, Boolean value)
    {
        SharedPreferences sp = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        SharedPreferences.Editor spe = sp.edit();
        spe.putBoolean(key, value);
        spe.apply();
    }

    /**
     * Setting Integer value to shared preference
     *
     * @param key   Name of Setting
     * @param value Value of Setting
     */
    public void setIntegerSetting(String key, int value)
    {
        SharedPreferences sp = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        SharedPreferences.Editor spe = sp.edit();
        spe.putInt(key, value);
        spe.apply();
    }

    /**
     * Gets Integer value from shared preferences
     *
     * @param key Name of setting
     * @return Returns value or 0 if no value or not set
     */
    public int getIntegerSetting(String key)
    {
        if (context == null)
            return 0;
        SharedPreferences sp = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        return sp.getInt(key, 0);
    }

    /**
     * Gets setting from shared preference
     *
     * @param key Name of setting
     * @return Returns null if not exists
     */
    public String getStringSetting(String key)
    {
        if (context == null)
            return null;
        SharedPreferences sp = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        return sp.getString(key, null);
    }

    /**
     * Gets setting from shared preference
     *
     * @param key          Name of setting
     * @param defaultValue Desired value of setting if not exists
     * @return returns True or False
     */
    public Boolean getBooleanSetting(String key, boolean defaultValue)
    {
        SharedPreferences sp = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defaultValue);
    }

    /**
     * Checks whether the settings exists or not
     *
     * @param key Name of Settings
     * @return returns True if settings exists
     */
    public Boolean hasSetting(String key)
    {
        SharedPreferences sp = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * Deletes Setting from shared preference
     *
     * @param key Name of Setting
     */
    public void deleteSetting(String key)
    {
        SharedPreferences sp = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        SharedPreferences.Editor spe = sp.edit();
        spe.remove(key);
        spe.apply();
    }

    public void setMultilineSetting(String key, ArrayList<Pair<String, ?>> alp)
    {
        SharedPreferences sp = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        SharedPreferences.Editor spe = sp.edit();
        for (Pair<String, ?> p : alp)
        {
            if (p.second instanceof String)
                spe.putString(p.first, (String) p.second);
            else if (p.second instanceof Boolean)
                spe.putBoolean(p.first, (Boolean) p.second);
            else if (p.second instanceof Integer)
                spe.putInt(p.first, (Integer) p.second);
            else if (p.second instanceof Float)
                spe.putFloat(p.first, (Float) p.second);
            else if (p.second instanceof Long)
                spe.putLong(p.first, (Long) p.second);
            else
                Log.e(getClass().getName(), "Key: " + p.first + ", does not contain a supported type");
        }
        spe.apply();
    }
    public Map<String, ?> getMultilineSetting(String key)
    {
        SharedPreferences sp = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        return sp.getAll();
    }


}