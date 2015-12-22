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

        super.onUpdate(context, appWidgetManager, appWidgetIds);
        for (int appWidgetID : appWidgetIds) {

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.widget_small);

            int numQKeys = QKeyPreferencesManager.getNumberOfQKeysStored(context);
            Log.v(TAG, "NumQKeys: " + numQKeys + ", appWidgetId: " + appWidgetID);

            remoteViews.setTextViewText(R.id.number_label, String.valueOf(numQKeys));
            remoteViews.setOnClickPendingIntent(R.id.widget_layout, getPendingSelfIntent(context, WIDGET_ON_CLICK));

            appWidgetManager.updateAppWidget(appWidgetID, remoteViews);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {

        super.onDeleted(context, appWidgetIds);
    }

    /**
     * Returns self pending intent.
     *
     * @param ctx    Context.
     * @param action Action.
     * @return Self pending intent.
     */
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
            IncrementAndUpdateWidget(context);
        }
    }

    /**
     * Updates widget.
     *
     * @param ctx Context.
     */
    private void IncrementAndUpdateWidget(Context ctx) {

        QKeyPreferencesManager.incrementNumberOfQKeys(ctx);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(ctx);
        ComponentName componentName = new ComponentName(ctx.getPackageName(), WidgetInfoProvider.class.getName());
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(componentName);
        onUpdate(ctx, appWidgetManager, appWidgetIds);
    }

    public static final String WIDGET_ON_CLICK = "widgetOnClick";
}
