package fr.alainmuller.gdksandbox.app.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.TextView;

import com.google.android.glass.media.Sounds;
import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;

import fr.alainmuller.gdksandbox.app.R;
import fr.alainmuller.gdksandbox.app.config.Config;
import fr.alainmuller.gdksandbox.app.tools.StringUtils;

public class PhoneConfirmActivity extends Activity {

    private static final String LOG_TAG = "PhoneConfirmActivity";

    // For tap events
    private GestureDetector mGestureDetector;

    // Audio manager used to play system sound effects
    private AudioManager mAudioManager;

    // Start new activities in a handler to keep smooth closing animations
    private final Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_confirm);

        mGestureDetector = createGestureDetector(this);
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        // Get and display phone number
        Intent intent = getIntent();
        String phoneNumber = intent.getStringExtra(Config.EXTRA_PHONE);

        TextView tvPhone = (TextView) findViewById(R.id.tv_phone_confirm);
        tvPhone.setText(tvPhone.getText().toString() + " " + StringUtils.getFormattedNumber(phoneNumber));
    }

    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        if (mGestureDetector != null) {
            return mGestureDetector.onMotionEvent(event);
        }
        return super.onGenericMotionEvent(event);
    }

    private GestureDetector createGestureDetector(Context context) {
        GestureDetector gestureDetector = new GestureDetector(context);
        // Create a base listener for generic gestures
        gestureDetector.setBaseListener(new GestureDetector.BaseListener() {
            @Override
            public boolean onGesture(Gesture gesture) {
                if (gesture == Gesture.TAP) {
                    mAudioManager.playSoundEffect(Sounds.TAP);
                    openOptionsMenu();
                    return true;
                }
                return false;
            }
        });
        return gestureDetector;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.phone_confirm, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {

            case R.id.action_phone_confirm:
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mAudioManager.playSoundEffect(Sounds.SUCCESS);
                        Log.d(LOG_TAG, "Phone confirmed!");
                        openPhoneConnectionActivity();
                    }
                });
                finish();
                return true;
            case R.id.action_phone_type:
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mAudioManager.playSoundEffect(Sounds.DISMISSED);
                        Log.d(LOG_TAG, "Phone not confirmed!");
                        openScrollCardActivity();
                    }
                });
                finish();
                return true;
            default:
                return false;
        }
    }

    private void openScrollCardActivity() {
        Intent intent = new Intent(this, PhoneTypingActivity.class);
        startActivity(intent);
    }

    private void openPhoneConnectionActivity() {
        Intent intent = new Intent(this, PhoneConnectionActivity.class);
        startActivity(intent);
    }
}
