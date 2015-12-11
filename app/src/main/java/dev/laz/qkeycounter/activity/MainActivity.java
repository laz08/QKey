package dev.laz.qkeycounter.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import dev.laz.qkeycounter.R;
import info.hoang8f.widget.FButton;

/**
 * Main Activity.
 */
public class MainActivity extends AppCompatActivity {

    public static final String NUMBER_OF_QKEYS = "numberOfQkeys";

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

        mQKeysNumber = getNumberOfQKeysStored();
        refreshNumberOfQKeysShown();

        mButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                addQKey();
            }
        });

    }

    /**
     * Refreshes number of QKeys shown.
     */
    private void refreshNumberOfQKeysShown() {

        mQKey.setText(String.valueOf(mQKeysNumber));
    }

    /**
     * Adds QKey.
     */
    private void addQKey() {

        mQKeysNumber++;
        refreshNumberOfQKeysShown();
    }

    @Override
    protected void onStop() {

        super.onStop();
        storeNumberOfQKeys();
    }

    /**
     * Stores in the shared preferences the number of QKeys.
     */
    private void storeNumberOfQKeys() {

        SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
        editor.putInt(NUMBER_OF_QKEYS, mQKeysNumber);
        editor.apply();
    }

    /**
     * Returns the number of QKeys stored.
     *
     * @return Number of QKeys.
     */
    private int getNumberOfQKeysStored() {

        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        return prefs.getInt(NUMBER_OF_QKEYS, 0);
    }
}
