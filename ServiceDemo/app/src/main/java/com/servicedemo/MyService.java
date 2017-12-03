package com.servicedemo;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by knroy on 12/3/2017.
 * Don't modify without proper privileges
 */

public class MyService extends Service {
    CountDownTimer cdt;
    Handler handler;
    Runnable runnableCode;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Let it continue running until it is stopped.
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
       /* cdt = new CountDownTimer(30000, 5000){
            public void onTick(long millisUntilFinished){
                //System.out.println(String.valueOf(millisUntilFinished/1000));
                Toast.makeText(MyService.this,String.valueOf(millisUntilFinished/1000), Toast.LENGTH_SHORT).show();
            }
            public  void onFinish(){
                //System.out.println("FINISH!!");
                Toast.makeText(MyService.this,"FINISH!", Toast.LENGTH_SHORT).show();
                onDestroy();
            }
        }.start();*/
        handler = new Handler();
        runnableCode = new Runnable() {
            @Override
            public void run() {
                // Do something here on the main thread
                //Log.d("Handlers", "Called on main thread");
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss a");
                final String strDate = simpleDateFormat.format(calendar.getTime());

                //show the toast
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(getApplicationContext(), strDate, duration);
                toast.show();
                // Repeat this the same runnable code block again another 2 seconds
                handler.postDelayed(runnableCode, 5000);
            }
        };
        handler.postDelayed(runnableCode, 5000);
        return START_STICKY;
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
        //cdt.cancel();
        handler.removeCallbacks(runnableCode);
    }
}
