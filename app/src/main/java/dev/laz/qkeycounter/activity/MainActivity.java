package dev.laz.qkeycounter.activity;

import android.app.Application;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import dev.laz.qkeycounter.QKeyPreferencesManager;
import dev.laz.qkeycounter.R;
import dev.laz.qkeycounter.widget.WidgetInfoProvider;
import info.hoang8f.widget.FButton;

/**
 * Main Activity.
 */
public class MainActivity extends AppCompatActivity {

    private int mQKeysNumber;

    @Bind(R.id.qKey_numb)
    TextView mQKey;

    @Bind(R.id.comments_button)
    FButton mButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        initialize();
    }

    /**
     * Initializes main activity.
     */
    private void initialize() {

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mQKeysNumber = QKeyPreferencesManager.getNumberOfQKeysStored(getApplicationContext());
        refreshNumberOfQKeysShown();

        mButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                addQKey();
            }
        });

    }

    /**
     * Adds QKey.
     */
    private void addQKey() {

        mQKeysNumber++;
        QKeyPreferencesManager.storeNumberOfQKeys(getApplicationContext(), mQKeysNumber);
        refreshNumberOfQKeysShown();
    }

    /**
     * Refreshes number of QKeys shown.
     */
    private void refreshNumberOfQKeysShown() {

        mQKey.setText(String.valueOf(mQKeysNumber));
        updateAllWidgets();
    }

    /**
     * Updates all widgets.
     */
    private void updateAllWidgets() {

        Intent i = new Intent(this, WidgetInfoProvider.class);
        i.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        Application app = getApplication();
        int[] appWidgetIds = AppWidgetManager.getInstance(app).getAppWidgetIds(new ComponentName(app, WidgetInfoProvider.class));
        i.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
        sendBroadcast(i);
    }
}
