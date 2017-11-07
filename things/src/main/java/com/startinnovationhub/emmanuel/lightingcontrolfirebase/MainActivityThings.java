package com.startinnovationhub.emmanuel.lightingcontrolfirebase;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

/**
 * Skeleton of an Android Things activity.
 * <p>
 * Android Things peripheral APIs are accessible through the class
 * PeripheralManagerService. For example, the snippet below will open a GPIO pin and
 * set it to HIGH:
 * <p>
 * <pre>{@code
 * PeripheralManagerService service = new PeripheralManagerService();
 * mLedGpio = service.openGpio("BCM6");
 * mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
 * mLedGpio.setValue(true);
 * }</pre>
 * <p>
 * For more complex peripherals, look for an existing user-space driver, or implement one if none
 * is available.
 *
 * @see <a href="https://github.com/androidthings/contrib-drivers#readme">https://github.com/androidthings/contrib-drivers#readme</a>
 */
public class MainActivityThings extends Activity {

    private static final String TAG = MainActivityThings.class.getSimpleName();

    private static final String PIN_1 = "GPIO_32";
    private static final String PIN_2 = "GPIO_33";
    private static final String PIN_3 = "GPIO_34";
    private static final String PIN_4 = "GPIO_37";

    private PinSettings LAMP_1;
    private PinSettings LAMP_2;
    private PinSettings LAMP_3;
    private PinSettings LAMP_4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_things);
        Log.d(TAG, "Oncreate... ");
        try {
            Log.d(TAG, "set output pin... ");
            LAMP_1 = new PinSettings(PIN_1, PinSettings.setState.OFF);
            LAMP_2 = new PinSettings(PIN_2, PinSettings.setState.OFF);
            LAMP_3 = new PinSettings(PIN_3, PinSettings.setState.OFF);
            LAMP_4 = new PinSettings(PIN_4, PinSettings.setState.OFF);

        } catch (Exception e) {
            Log.e(TAG, "Error: Opening the lamp pin " + e.getMessage(), e);
        }

        TextView textView1 = findViewById(R.id.lamp1_state);
        TextView textView2 = findViewById(R.id.lamp2_state);
        TextView textView3 = findViewById(R.id.lamp3_state);
        TextView textView4 = findViewById(R.id.lamp4_state);
        try {
            final FirebaseDatabase fbDatabase = FirebaseDatabase.getInstance();

            final DatabaseReference dbRef = fbDatabase.getReference("lamp_control");
            final DatabaseReference lamp_1_Ref = dbRef.child("lamp_1");
            final DatabaseReference lamp_2_Ref = dbRef.child("lamp_2");
            final DatabaseReference lamp_3_Ref = dbRef.child("lamp_3");
            final DatabaseReference lamp_4_Ref = dbRef.child("lamp_4");
            Log.d(TAG, "Listening to database... ");
            lamp_1_Ref.addValueEventListener(new PinStatusEventListener(LAMP_1, textView1));
            lamp_2_Ref.addValueEventListener(new PinStatusEventListener(LAMP_2, textView2));
            lamp_3_Ref.addValueEventListener(new PinStatusEventListener(LAMP_3, textView3));
            lamp_4_Ref.addValueEventListener(new PinStatusEventListener(LAMP_4, textView4));

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        try {
            LAMP_1.close();
        } catch (Exception e) {
            LAMP_1 = null;
        }

        try {
            LAMP_2.close();
        } catch (Exception e) {
            LAMP_2 = null;
        }

        try {
            LAMP_3.close();
        } catch (Exception e) {
            LAMP_3 = null;
        }

        try {
            LAMP_4.close();
        } catch (Exception e) {
            LAMP_4 = null;
        }


    }


}

