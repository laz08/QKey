package dev.laz.qkeycounter.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import dev.laz.qkeycounter.QKeyPreferencesManager;
import dev.laz.qkeycounter.R;

/**
 * Widget info provider.
 */
public class WidgetInfoProvider extends AppWidgetProvider {

    private static final String TAG = "WidgetInfoProvider";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        Log.v(TAG, "onUpdate");
        Log.v(TAG, "appWidgetsId size: " + appWidgetIds.length);

        for (int appWidgetID : appWidgetIds) {

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.widget_small);

            int numQKeys = QKeyPreferencesManager.getNumberOfQKeysStored(context);
            Log.v(TAG, "NumQKeys: " + numQKeys + ", appWidgetId: " + appWidgetID);

            remoteViews.setTextViewText(R.id.number_label, String.valueOf(numQKeys));
            remoteViews.setOnClickPendingIntent(R.id.widget_layout, getPendingSelfIntent(context, WIDGET_ON_CLICK));

            appWidgetManager.updateAppWidget(appWidgetID, remoteViews);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {

        super.onDeleted(context, appWidgetIds);

    }

    private PendingIntent getPendingSelfIntent(Context ctx, String action) {

        Intent intent = new Intent(ctx, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(ctx, 0, intent, 0);
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        super.onReceive(context, intent);
        Log.v(TAG, "onReceived");
        if (WIDGET_ON_CLICK.equals(intent.getAction())) {

            Log.v(TAG, "On click!");
            updateWidget(context);
        }
    }

    /**
     * Updates widget.
     *
     * @param ctx Context.
     */
    private void updateWidget(Context ctx) {

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(ctx);

        ComponentName componentName =
                new ComponentName(ctx.getPackageName(), WidgetInfoProvider.class.getName());
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
                componentName);
        Log.v(TAG, "UpdateWidget, Component name: " + componentName.getClassName());
        onUpdate(ctx, appWidgetManager, appWidgetIds);
    }

    public static final String WIDGET_ON_CLICK = "widgetOnClick";
}
