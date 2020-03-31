package com.example.appeggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
boolean timeS=false;
//True=You can change the seekbar
SeekBar seek;
int defaultProgress=30;
int maxT=600;
TextView time;
Button btn;
int currentProgress=30;
CountDownTimer timer;
MediaPlayer media;
boolean sound;
    public void setDisplay(int prog){
        String text = String.format( "%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(prog*1000) % 60,
                TimeUnit.MILLISECONDS.toSeconds(prog*1000) % 60);
        time.setText(text);
    }

    public void click(View view){


        if(timeS==true)
        {   if(sound==true)
        {
            media.start();
        }
        sound=false;
            timer.cancel();

            btn.setText("Start!");
            seek.setEnabled(true);

            seek.setProgress(defaultProgress);
            setDisplay(defaultProgress);

        timeS=false;
        }
        else{
            btn.setText("Stop!");
            seek.setEnabled(false);
            timer =new CountDownTimer(currentProgress*1000+100,1000)
            {
                @Override
                public void onTick(long millisUntilFinished) {
                    Log.i("Info",String.valueOf(millisUntilFinished/1000));
                    setDisplay((int)millisUntilFinished/1000);
                }

                @Override
                public void onFinish() {
                    sound=true;
                    timeS=true;
                    click(btn);
                }
            };
            timer.start();

            timeS=true;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        media= MediaPlayer.create(this,R.raw.alarm);
        setContentView(R.layout.activity_main);
        time=(TextView)findViewById(R.id.time);

        btn=(Button)findViewById(R.id.ready);
        setDisplay(defaultProgress);
        seek=(SeekBar)findViewById(R.id.seek);
        seek.setMax(maxT);
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currentProgress=progress;
                setDisplay(currentProgress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}
