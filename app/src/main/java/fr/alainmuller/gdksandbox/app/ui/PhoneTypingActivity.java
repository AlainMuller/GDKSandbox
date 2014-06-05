package fr.alainmuller.gdksandbox.app.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.google.android.glass.media.Sounds;
import com.google.android.glass.widget.CardScrollView;

import fr.alainmuller.gdksandbox.app.R;
import fr.alainmuller.gdksandbox.app.adapter.DigitsCardScrollAdapter;
import fr.alainmuller.gdksandbox.app.config.Config;
import fr.alainmuller.gdksandbox.app.tools.StringUtils;

/**
 * Play with a ScrollCardView to enter a phone number
 */
public class PhoneTypingActivity extends Activity {

    private static final String LOG_TAG = "PhoneTypingActivity";

    // Audio manager used to play system sound effects
    private AudioManager mAudioManager;

    private String mPhoneNumber = "";
    private TextView mTvPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_typing);

        mTvPhoneNumber = (TextView) findViewById(R.id.scrollcard_phone);
        CardScrollView mCardScrollView = (CardScrollView) findViewById(R.id.scrollcard_scrollview);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        // init adapter and set it to the scrollview
        DigitsCardScrollAdapter adapter = new DigitsCardScrollAdapter(getApplicationContext());
        mCardScrollView.setAdapter(adapter);
        mCardScrollView.activate();

        // click listener to add a digit
        mCardScrollView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mAudioManager.playSoundEffect(Sounds.TAP);

                // Concat the digit to phone number
                mPhoneNumber += String.valueOf(position % 10);
                displayPhoneNumber();

                // If 10 digits entered => Validate number
                if (mPhoneNumber.length() == 10) {
                    Log.d(LOG_TAG, "Phone entered : " + mPhoneNumber);
                    openConfirmActivity();
                }
            }
        });

        // long click listener to cancel last digit
        mCardScrollView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mAudioManager.playSoundEffect(Sounds.DISALLOWED);
                if (mPhoneNumber.length() > 0) {
                    // Revert last digit
                    mPhoneNumber = mPhoneNumber.substring(0, mPhoneNumber.length() - 1);
                    displayPhoneNumber();
                    return true;
                }
                return false;
            }
        });
    }

    private void displayPhoneNumber() {
        String displayedPhone = StringUtils.getFormattedNumber(mPhoneNumber);
        mTvPhoneNumber.setText(displayedPhone);
    }

    private void openConfirmActivity() {
        Intent intent = new Intent(this, PhoneConfirmActivity.class);
        intent.putExtra(Config.EXTRA_PHONE, mPhoneNumber);
        startActivity(intent);
        finish();
    }
}
