package fr.alainmuller.gdksandbox.app.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;

import com.google.android.glass.media.Sounds;
import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;

import fr.alainmuller.gdksandbox.app.R;

public class MainActivity extends Activity {

    private static final String LOG_TAG = "MainActivity";

    /**
     * Audio manager used to play system sound effects.
     */
    private AudioManager mAudioManager;

    /**
     * Gesture detector used to present the options menu.
     */
    private GestureDetector mGestureDetector;

    /**
     * Handler used to post requests to start new activities so that the menu closing animation
     * works properly.
     */
    private final Handler mHandler = new Handler();

    /**
     * Listener that displays the options menu when the touchpad is tapped.
     */
    private final GestureDetector.BaseListener mBaseListener = new GestureDetector.BaseListener() {
        @Override
        public boolean onGesture(Gesture gesture) {
            if (gesture == Gesture.TAP) {
                mAudioManager.playSoundEffect(Sounds.TAP);
                openOptionsMenu();
                return true;
            } else {
                return false;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mGestureDetector = new GestureDetector(this).setBaseListener(mBaseListener);
    }

    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        return mGestureDetector.onMotionEvent(event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * The act of starting an activity here is wrapped inside a posted {@code Runnable} to avoid
     * animation problems between the closing menu and the new activity. The post ensures that the
     * menu gets the chance to slide down off the screen before the activity is started.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The startAnotherActivity() methods start a new activity, and if we call them directly here then
        // the new activity will start without giving the menu a chance to slide back down first.
        // By posting the calls to a handler instead, they will be processed on an upcoming pass
        // through the message queue, after the animation has completed, which results in a
        // smoother transition between activities.
        switch (item.getItemId()) {
            case R.id.action_phone_typing:
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        openScrollCardActivity();
                    }
                });
                return true;
            case R.id.action_phone_call:
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        openPhoneConnectionActivity();
                    }
                });
                return true;

            // etc...

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
