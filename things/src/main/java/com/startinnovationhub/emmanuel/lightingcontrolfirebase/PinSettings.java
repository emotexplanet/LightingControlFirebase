package com.startinnovationhub.emmanuel.lightingcontrolfirebase;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManagerService;

import java.io.IOException;

/**
 * Created by Emmanuel on 05/11/2017.
 */

public class PinSettings implements AutoCloseable {

    private static final String TAG = PinSettings.class.getSimpleName();

    private Gpio pin;

    public  enum setState{
        ON(Gpio.DIRECTION_OUT_INITIALLY_HIGH), OFF(Gpio.DIRECTION_OUT_INITIALLY_LOW);
        final int state;
        setState(int state){
            this.state = state;
        }

        public int getState(){
            return this.state;
        }

    }


    public PinSettings(String pinName, setState set){
        PeripheralManagerService service = new PeripheralManagerService();

        try {
            Gpio pin = service.openGpio(pinName);
            this.pin = pin;
            this.pin.setDirection(set.state);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  boolean getState(){
        boolean value = false;
        try{
          value  =  pin.getValue();
        }catch (IOException e){
            Log.d(TAG, "Error in reading pin value" + e.getMessage(), e);
        }

        return  value;
    }


    public void turnOnOff(boolean turn, TextView textView){
        try{
            pin.setValue(turn);
            boolean value = getState();
            if(value){
                textView.setText("NO");
            }else {
                textView.setText("OFF");
            }
        }catch (IOException e){
            Log.d(TAG, "Error in setting pin value" + e.getMessage(), e);
        }
    }


    @Override
    public void close() throws Exception {
        if(pin != null){
            try {
                pin.close();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                pin = null;
            }

        }
    }
}
