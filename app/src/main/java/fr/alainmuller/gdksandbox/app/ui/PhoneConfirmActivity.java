package fr.alainmuller.gdksandbox.app.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.TextView;

import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;

import fr.alainmuller.gdksandbox.app.R;
import fr.alainmuller.gdksandbox.app.config.Config;

public class PhoneConfirmActivity extends Activity {

    private static final String LOG_TAG = "PhoneConfirmActivity";

    // For tap events
    private GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_confirm);

        // For gesture handling.
        mGestureDetector = createGestureDetector(this);

        // Get and display phone number
        Intent intent = getIntent();
        String phoneNumber = intent.getStringExtra(Config.EXTRA_PHONE);

        TextView tvPhone = (TextView) findViewById(R.id.tv_phone_confirm);
        tvPhone.setText(tvPhone.getText().toString() + " " + phoneNumber);
    }

    // //////////////// //
    // Gesture handling //
    // //////////////// //

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
                    Log.d(LOG_TAG, "Phone confirmed!");
                    finish();
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
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
