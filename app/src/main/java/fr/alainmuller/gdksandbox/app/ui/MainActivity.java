package fr.alainmuller.gdksandbox.app.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;

import fr.alainmuller.gdksandbox.app.R;

public class MainActivity extends Activity {

    private static final String LOG_TAG = "MainActivity";

    // For tap events
    private GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // For gesture handling.
        mGestureDetector = createGestureDetector(this);

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
                Log.d(LOG_TAG, "Gesture = " + gesture);
                if (gesture == Gesture.TAP) {
                    openScrollCardActivity();
                    return true;
                } else {
                    // etc...
                }
                return false;
            }
        });
        return gestureDetector;
    }

    private void openScrollCardActivity() {
        Intent intent = new Intent(this, PhoneTypingActivity.class);
        startActivity(intent);
    }

}
