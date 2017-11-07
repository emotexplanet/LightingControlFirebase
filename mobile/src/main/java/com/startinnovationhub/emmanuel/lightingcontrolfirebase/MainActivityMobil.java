package com.startinnovationhub.emmanuel.lightingcontrolfirebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivityMobil extends AppCompatActivity {

    private  Button allOnButton ;
    private Button allOffButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_mobil);

        Switch lamp_1_switch = (Switch) findViewById(R.id.Lamp_1_switch);
        Switch lamp_2_switch = (Switch) findViewById(R.id.Lamp_2_switch);
        Switch lamp_3_switch = (Switch) findViewById(R.id.Lamp_3_switch);
        Switch lamp_4_switch = (Switch) findViewById(R.id.Lamp_4_switch);


        final FirebaseDatabase fbDatabase = FirebaseDatabase.getInstance();

        final DatabaseReference dbRef = fbDatabase.getReference("lamp_control");
        final DatabaseReference lamp_1_Ref = dbRef.child("lamp_1");
        final DatabaseReference lamp_2_Ref = dbRef.child("lamp_2");
        final DatabaseReference lamp_3_Ref = dbRef.child("lamp_3");
        final DatabaseReference lamp_4_Ref = dbRef.child("lamp_4");

        lamp_1_Ref.addValueEventListener(new SwitchStatusEventListener(lamp_1_switch));
        lamp_2_Ref.addValueEventListener(new SwitchStatusEventListener(lamp_2_switch));
        lamp_3_Ref.addValueEventListener(new SwitchStatusEventListener(lamp_3_switch));
        lamp_4_Ref.addValueEventListener(new SwitchStatusEventListener(lamp_4_switch));

        lamp_1_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                lamp_1_Ref.setValue(isChecked);
            }
        });

        lamp_2_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                lamp_2_Ref.setValue(isChecked);
            }
        });
        lamp_3_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                lamp_3_Ref.setValue(isChecked);
            }
        });
        lamp_4_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                lamp_4_Ref.setValue(isChecked);
            }
        });

        allOnButton = (Button) findViewById(R.id.all_on_btn);
        allOffButton = (Button) findViewById(R.id.all_off_btn);

        allOffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turnAllOnOff(dbRef, false);
            }

        });

        allOnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turnAllOnOff(dbRef, true);
            }
        });

    }

    /*
      Turn on or off all switch
     */
    private void turnAllOnOff(final DatabaseReference reference, final boolean state) {
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/lamp_1", state);
        childUpdates.put("/lamp_2", state);
        childUpdates.put("/lamp_3", state);
        childUpdates.put("/lamp_4", state);
        reference.updateChildren(childUpdates);

        if(state){
            allOnButton.setClickable(false);
            allOffButton.setClickable(true);
        }else {
            allOnButton.setClickable(true);
            allOffButton.setClickable(false);
        }


    }
}
