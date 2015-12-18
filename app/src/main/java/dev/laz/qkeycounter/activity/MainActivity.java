package dev.laz.qkeycounter.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import dev.laz.qkeycounter.QKeyPreferencesManager;
import dev.laz.qkeycounter.R;
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
        QKeyPreferencesManager.storeNumberOfQKeys(getApplicationContext(), mQKeysNumber);
        refreshNumberOfQKeysShown();
    }

}
