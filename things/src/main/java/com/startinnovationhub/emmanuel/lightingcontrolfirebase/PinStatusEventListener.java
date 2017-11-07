package com.startinnovationhub.emmanuel.lightingcontrolfirebase;

import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

/**
 * Created by Emmanuel on 05/11/2017.
 */

public class PinStatusEventListener implements ValueEventListener {

    private static final String TAG = PinStatusEventListener.class.getSimpleName();



    private final PinSettings pin;
    private  final TextView textView;

    PinStatusEventListener(PinSettings pin, TextView textView){
        this.pin = pin;
        this.textView = textView;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        Boolean value = (Boolean) dataSnapshot.getValue();
        Log.d(TAG, "Pin Value: " + value);
        pin.turnOnOff(value == null ? false : value, textView);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        Log.d(TAG, "Error: Failed to read value", databaseError.toException());

    }

}
