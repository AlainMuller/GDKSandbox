package fr.alainmuller.gdksandbox.app.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.glass.app.Card;
import com.google.android.glass.widget.CardScrollAdapter;

/**
 * Custom adapter for PhoneTypingActivity
 * Created by Alain MULLER on 04/06/2014.
 */
public class DigitsCardScrollAdapter extends CardScrollAdapter {

    private Context context;

    private static final String LOG_TAG = "DigitsCardScrollAdapter";

    public DigitsCardScrollAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getPosition(Object item) {
        return 0;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return Card.getViewTypeCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            // Return the digit in a TextView
            TextView tv = new TextView(context);
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(60);

            tv.setText(String.valueOf(position % 10));
            convertView = tv;
        }
        return convertView;
    }
}
