package dev.laz.qkeycounter.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import dev.laz.qkeycounter.R;
import dev.laz.qkeycounter.Values;

/**
 * Widget info provider.
 */
public class WidgetInfoProvider extends AppWidgetProvider {

    private static final String TAG = "WidgetInfoProvider";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        int numberOfWidgets = appWidgetIds.length;
        for (int i = 0; i < numberOfWidgets; i++) {

            int widgetId = appWidgetIds[i];
            Log.v(TAG, "widgetId: " + widgetId);
            Toast.makeText(context, "Widget id: " + widgetId, Toast.LENGTH_SHORT).show();

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_small);
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            int numQKeys = prefs.getInt(Values.NUMBER_OF_QKEYS, 0);
            Log.v(TAG, "Stored number of QKeys:" + numQKeys);
            views.setTextViewText(R.id.number_label, String.valueOf(numQKeys));

            Intent intent = new Intent(context, WidgetInfoProvider.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            views.setOnClickPendingIntent(R.id.widget_layout, pendingIntent);

            appWidgetManager.updateAppWidget(widgetId, views);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {

        super.onDeleted(context, appWidgetIds);
    }
}
