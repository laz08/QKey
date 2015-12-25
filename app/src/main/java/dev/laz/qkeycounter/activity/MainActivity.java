package dev.laz.qkeycounter.activity;

import android.app.Application;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import dev.laz.qkeycounter.QKeyPreferencesManager;
import dev.laz.qkeycounter.R;
import dev.laz.qkeycounter.dialog.DialogHelper;
import dev.laz.qkeycounter.dialog.ResetListener;
import dev.laz.qkeycounter.entities.ImageType;
import dev.laz.qkeycounter.widget.WidgetInfoProvider;
import info.hoang8f.widget.FButton;

/**
 * Main Activity.
 */
public class MainActivity extends AppCompatActivity implements ResetListener {

    @Bind(R.id.changeable_img)
    ImageView mChangeableImgView;

    @Bind(R.id.qKey_numb)
    TextView mQKey;

    @Bind(R.id.add_qkey_button)
    FButton mButton;

    @Bind(R.id.reset_button)
    FButton mResetButton;

    private int mQKeysNumber;
    private ImageType mImageShown;
    private int mNumberOfClicksLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        initialize(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
        outState.putInt(BUNDLE_IMAGE_SHOWN, mImageShown.getCode());
        outState.putInt(BUNDLE_CLICKS_LEFT, mNumberOfClicksLeft);
    }

    /**
     * Initializes main activity.
     */
    private void initialize(Bundle savedInstanceState) {

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

        mResetButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                DialogHelper.showResetDialog(getSupportFragmentManager());
            }
        });

        configureImageShown(savedInstanceState);
    }

    /**
     * Configures image shown.
     *
     * @param savedInstanceState Saved instance state.
     */
    private void configureImageShown(Bundle savedInstanceState) {

        if (savedInstanceState != null) {

            int code = savedInstanceState.getInt(BUNDLE_IMAGE_SHOWN);
            mImageShown = ImageType.from(code);
            mNumberOfClicksLeft = savedInstanceState.getInt(BUNDLE_CLICKS_LEFT);
        } else {

            mImageShown = ImageType.COOKIE;
            mNumberOfClicksLeft = DEFAULT_CLICKS;
        }
        mChangeableImgView.setImageResource(mImageShown.getImageResource());

        mChangeableImgView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                toggleImageShown();
            }
        });
    }

    /**
     * Toggles image shown.
     */
    private void toggleImageShown() {

        mNumberOfClicksLeft--;
        if (mNumberOfClicksLeft == 0) {

            int newCode = mImageShown.getCode() + 1;
            mImageShown = ImageType.from(newCode);
            mChangeableImgView.setImageResource(mImageShown.getImageResource());
            mNumberOfClicksLeft = DEFAULT_CLICKS;
        }
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

    @Override
    public void resetNumberOfQKeys(Context ctx) {

        mQKeysNumber = 0;
        QKeyPreferencesManager.resetNumberOfQKeys(getApplicationContext());
        refreshNumberOfQKeysShown();
    }

    public static final int DEFAULT_CLICKS = 5;

    private static final String BUNDLE_IMAGE_SHOWN = "imageShown";
    private static final String BUNDLE_CLICKS_LEFT = "clicksLeft";
}
