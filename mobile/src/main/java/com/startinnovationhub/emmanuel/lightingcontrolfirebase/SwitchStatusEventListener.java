package com.startinnovationhub.emmanuel.lightingcontrolfirebase;

import android.widget.Switch;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Emmanuel on 06/11/2017.
 */

public class SwitchStatusEventListener implements ValueEventListener {
    private  final Switch aSwitch;

    SwitchStatusEventListener(Switch aSwitch){
        this.aSwitch = aSwitch;
    }
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        Boolean status = (Boolean) dataSnapshot.getValue();
        aSwitch.setChecked(status == null ? false : status);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
