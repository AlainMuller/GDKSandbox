package fr.alainmuller.gdksandbox.app.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.widget.TextView;

import com.google.android.glass.media.Sounds;
import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;

import fr.alainmuller.gdksandbox.app.R;
import fr.alainmuller.gdksandbox.app.config.Config;

public class PhoneConnectionActivity extends Activity {

    public static final String LOG_TAG = "PhoneConnectionActivity";

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
                if (phoneCallAvailable()) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mAudioManager.playSoundEffect(Sounds.TAP);
                        makePhoneCall();
                    }
                });
                finish();
                } else {
                    mAudioManager.playSoundEffect(Sounds.DISALLOWED);
                }
                return true;
            } else {
                return false;
            }
        }
    };

    private TextView mStatusTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_connection);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mGestureDetector = new GestureDetector(this).setBaseListener(mBaseListener);

        mStatusTextView = (TextView) findViewById(R.id.tv_phone_connection_status);


/*
        // Test Phone connection
        if (((TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE)).getPhoneType()
                == TelephonyManager.PHONE_TYPE_NONE) {
            // no phone
            displayStatusKO();
        }
      if (((TelephonyManager)getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE)).getLine1Number()
                == null)
        {
            // no phone
            displayStatusKO();
        }
*/
        if (!phoneCallAvailable())
            displayStatusKO();
        else
            displayStatusOK();
    }

    /**
     * Use the phone to make a phone call
     */
    private void makePhoneCall() {
        Intent intent = new Intent();
        intent.putExtra("com.google.glass.extra.PHONE_NUMBER", Config.PHONE_NUMBER);
        intent.setAction("com.google.glass.extra.CALL_DIAL");
        sendBroadcast(intent);
    }

    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        return mGestureDetector.onMotionEvent(event);
    }

    private void displayStatusOK() {
        mStatusTextView.setTextColor(getResources().getColor(android.R.color.holo_green_light));
        mStatusTextView.setText("Connecté");
    }

    private void displayStatusKO() {
        mStatusTextView.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        mStatusTextView.setText("Non Connecté");
    }

    /**
     * Test if Glass is connected to a phone and can make a phone call.
     *
     * @return true if the Phone can handle CALL_DIAL action
     */
    private boolean phoneCallAvailable() {
//        final PackageManager packageManager = getApplicationContext().getPackageManager();
//        final Intent intent = new Intent("com.google.glass.extra.CALL_DIAL");
//        intent.putExtra("com.google.glass.extra.PHONE_NUMBER", Config.PHONE_NUMBER);

        /** TEST 1 : resolveActivity*/
//        Log.d(LOG_TAG, "resolveActivity > " + (intent.resolveActivity(packageManager) != null));

        /** TEST 2 : list activities via packageManager */
//        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
//        for (ResolveInfo info : list) {
//            Log.d(LOG_TAG, info.resolvePackageName + " :: " + info.toString());
//        }
//        Log.d(LOG_TAG, "queryIntentActivities > " + (!list.isEmpty()));

        /** TEST 3 : testing TELEPHONY_SERVICE but no such service on Glass so always false :'( */
//        Log.d(LOG_TAG, "TELEPHONY_SERVICE > " + (((TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE)).getLine1Number() != null));

        // FIXME : No way to test phone connectivity in this version :'(
        return false;
    }

}
