package dev.laz.qkeycounter.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import dev.laz.qkeycounter.R;

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
            views.setTextViewText(R.id.numb_text, "Hika");

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
