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

        // Loop for every App Widget instance that belongs to this provider.
        // Noting, that is, a user might have multiple instances of the same
        // widget on
        // their home screen.
        for (int appWidgetID : appWidgetIds) {

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.widget_small);

            int numQKeys = QKeyPreferencesManager.getNumberOfQKeysStored(context);
            Log.v(TAG, "NumQKeys: " + numQKeys);

            remoteViews.setTextViewText(R.id.number_label, String.valueOf(numQKeys));
            remoteViews.setOnClickPendingIntent(R.id.widget_layout, getPendingSelfIntent(context, WIDGET_ON_CLICK));

            appWidgetManager.updateAppWidget(appWidgetID, remoteViews);
        }
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
        if (WIDGET_ON_CLICK.equals(intent.getAction())) {

            Log.v(TAG, "On click!");
            updateWidget(context);
        }
    }

    private void updateWidget(Context ctx) {

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(ctx);

        // Uses getClass().getName() rather than MyWidget.class.getName() for
        // portability into any App Widget Provider Class
        ComponentName thisAppWidgetComponentName =
                new ComponentName(ctx.getPackageName(), getClass().getName());
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
                thisAppWidgetComponentName);
        onUpdate(ctx, appWidgetManager, appWidgetIds);
    }


    /*
        public void onReceive(Context ctx, Intent i) {
        }
    */
    public static final String WIDGET_ON_CLICK = "widgetOnClick";
}
