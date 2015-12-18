package dev.laz.qkeycounter;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * QKey preferences manager.
 */
public class QKeyPreferencesManager {

    /**
     * Stores in the shared preferences the number of QKeys.
     */
    public static void storeNumberOfQKeys(Context ctx, int num) {

        SharedPreferences.Editor editor = ctx.getSharedPreferences(Values.PREFS_NAME, 0).edit();
        editor.putInt(Values.NUMBER_OF_QKEYS, num);
        editor.apply();
    }

    /**
     * Returns the number of QKeys stored.
     *
     * @return Number of QKeys.
     */
    public static int getNumberOfQKeysStored(Context ctx) {

        SharedPreferences prefs = ctx.getSharedPreferences(Values.PREFS_NAME, 0);
        return prefs.getInt(Values.NUMBER_OF_QKEYS, 0);
    }
}
