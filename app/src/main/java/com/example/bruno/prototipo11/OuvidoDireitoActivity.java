package com.example.bruno.prototipo11;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

/**
 * Created by Guilherme on 18/05/16.
 */
public class OuvidoDireitoActivity extends Activity {

    Thread t;
    boolean isRunning = true;
    int flagOuviu = 0;
    int duration = 3; //segundos
    int sr = 8000;
    int buffsize = duration * sr;
    String valor1000Hz;
    int dB = 7;
    int i1 = 0, j1 = 0;
    int i2 = 0, j2 = 0;
    int i3 = 0, j3 = 0;
    int i4 = 0, j4 = 0;
    int i5 = 0, j5 = 0;
    int i6 = 0, j6 = 0;
    int i7 = 0, j7 = 0;
    int i8 = 0, j8 = 0;
    int i9 = 0, j9 = 0;
    int i10 = 0, j10 = 0;

    public int randomTime(){
        int time;
        double num = Math.random();
        if (num < 0.2){
            time = 2000;
        } else if (num < 0.4 && num >= 0.2){
            time = 2500;
        }else if(num < 0.6 && num >= 0.4){
            time = 3000;
        }else if (num < 0.8 && num >= 0.6) {
            time = 3500;
        }else {
            time = 4000;
        }
        return time;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ouvidodireito);
        t = new Thread() {
            public void run() {
                // set process priority
                setPriority(Thread.MAX_PRIORITY);
                // create an audiotrack object
                AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                        sr, AudioFormat.CHANNEL_OUT_MONO,
                        AudioFormat.ENCODING_PCM_16BIT, buffsize,
                        AudioTrack.MODE_STREAM);
                audioTrack.setStereoVolume(0,1);

                short samples[] = new short[buffsize];
                double amp = 100;
                double twopi = 8.*Math.atan(1.);
                double fr = 440.f;
                double ph = 0.0;

                // start audio
                audioTrack.play();

                // synthesis loop
                while (isRunning) {
                    if (amp <= 0) {
                        isRunning = false;
                        valor1000Hz = "-10dB";
                        mandarResultado();
                    }else if(amp >= 11000){
                        isRunning = false;
                        valor1000Hz = "80dB";
                        mandarResultado();
                    }
                    for (int i = 0; i < buffsize; i++) {
                        samples[i] = (short) (amp * Math.sin(ph));
                        ph += twopi * fr / sr;
                        if(flagOuviu != 0){
                            i = buffsize;
                        }
                    }
                    audioTrack.write(samples, 0, buffsize);

                    try {
                        Thread.sleep(randomTime());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                    switch (dB){
                        case 1:
                            if (flagOuviu != 0) {
                                i1 += 1;
                                flagOuviu = 0;
                                if (j1 != 0) {
                                    i1 += 1;
                                    j1 = 0;
                                }else if(i1 >= 2) {
                                    finalErro();
                                }
                            }else {
                                dB = 2;
                                i1 -= 1;
                                j1 += 1;
                                amp = 0.001;
                            }
                            break;
                        case 2:
                            if (flagOuviu != 0) {
                                i2 += 1;
                                flagOuviu = 0;
                                if (j2 != 0) {
                                    i2 += 1;
                                    j2 = 0;
                                }else if(j1 != 0) {
                                    dB = 1;
                                    j1 -= 2;
                                    amp = 0.0001;
                                }else if(i2 >= 2) {
                                    mandarResultado();
                                }else{
                                    dB = 1;
                                    amp = 0.0001;
                                }
                            }else {
                                dB = 3;
                                i2 -= 1;
                                j2 += 1;
                                amp = 0.01;
                            }
                            break;
                        case 3:
                            if (flagOuviu != 0) {
                                i3 += 1;
                                flagOuviu = 0;
                                if (j3 != 0) {
                                    i3 += 1;
                                    j3 = 0;
                                }else if(j2 != 0) {
                                    dB = 2;
                                    j2 -= 2;
                                    amp = 0.001;
                                }else if(i3 >= 2) {
                                    mandarResultado();
                                }else{
                                    dB = 2;
                                    amp = 0.001;
                                }
                            }else {
                                dB = 4;
                                i3 -= 1;
                                j3 += 1;
                                amp = 0.1;
                            }
                            break;
                        case 4:
                            if (flagOuviu != 0) {
                                i4 += 1;
                                flagOuviu = 0;
                                if (j4 != 0) {
                                    i4 += 1;
                                    j4 = 0;
                                }else if(j3 != 0) {
                                    dB = 3;
                                    j3 -= 2;
                                    amp = 0.01;
                                }else if(i4 >= 2) {
                                    mandarResultado();
                                }else{
                                    dB = 3;
                                    amp = 0.01;
                                }
                            }else {
                                dB = 5;
                                i4 -= 1;
                                j4 += 1;
                                amp = 1;
                            }
                            break;
                        case 5:
                            if (flagOuviu != 0) {
                                i5 += 1;
                                flagOuviu = 0;
                                if (j5 != 0) {
                                    i5 += 1;
                                    j5 = 0;
                                }else if(j4 != 0) {
                                    flagOuviu = 0;
                                    dB = 4;
                                    j4 -= 2;
                                    amp = 0.1;
                                }else if(i5 >= 2) {
                                    mandarResultado();
                                }else{
                                    dB = 4;
                                    amp = 0.1;
                                }
                            }else {
                                dB = 6;
                                i5 -= 1;
                                j5 += 1;
                                amp = 10;
                            }
                            break;
                        case 6:
                            if (flagOuviu != 0) {
                                i6 += 1;
                                flagOuviu = 0;
                                if (j6 != 0) {
                                    i6 += 1;
                                    j6 = 0;
                                }else if(j5 != 0) {
                                    dB = 5;
                                    j5 -= 2;
                                    amp = 1;
                                }else if(i6 >= 2) {
                                    mandarResultado();
                                }else{
                                    dB = 5;
                                    amp = 1;
                                }
                            }else {
                                dB = 7;
                                i6 -= 1;
                                j6 += 1;
                                amp = 100;
                            }
                            break;
                        case 7:
                            if (flagOuviu != 0) {
                                i7 += 1;
                                flagOuviu = 0;
                                if (j7 != 0) {
                                    i7 += 1;
                                    j7 = 0;
                                }else if(j6 != 0) {
                                    dB = 6;
                                    j6 -= 2;
                                    amp = 10;
                                }else if(i7 >= 2) {
                                    mandarResultado();
                                }else{
                                    dB = 6;
                                    amp = 10;
                                }
                            }else {
                                dB = 8;
                                i7 -= 1;
                                j7 += 1;
                                amp = 1000;
                            }
                            break;
                        case 8:
                            if (flagOuviu != 0) {
                                i8 += 1;
                                flagOuviu = 0;
                                if (j8 != 0) {
                                    i8 += 1;
                                    j8 = 0;
                                }else if(j7 != 0) {
                                    dB = 7;
                                    j7 -= 2;
                                    amp = 100;
                                }else if(i8 >= 2) {
                                    mandarResultado();
                                }else{
                                    dB = 7;
                                    amp = 100;
                                }
                            }else {
                                dB = 9;
                                i8 -= 1;
                                j8 += 1;
                                amp = 10000;
                            }
                            break;
                        case 9:
                            if (flagOuviu != 0) {
                                i9 += 1;
                                flagOuviu = 0;
                                if (j9 != 0) {
                                    i9 += 1;
                                    j9 = 1;
                                }else if(j8 != 0) {
                                    dB = 8;
                                    j8 -= 2;
                                    amp = 1000;
                                }else if(i9 >= 2) {
                                    mandarResultado();
                                }else{
                                    dB = 8;
                                    amp = 1000;
                                }
                            }else {
                                dB = 10;
                                i9 -= 1;
                                j9 += 1;
                                amp = 100000;
                            }
                            break;
                        case 10:
                            if (flagOuviu != 0) {
                                i10 += 1;
                                if(j9 != 0) {
                                    flagOuviu = 0;
                                    dB = 3;
                                    j9 -= 2;
                                    amp = 10000;
                                }else if(i10 >= 2) {
                                    mandarResultado();
                                }else{
                                    flagOuviu = 0;
                                    dB = 9;
                                    amp = 10000;
                                }
                            }else {
                                i10 -= 1;
                                j10 += 1;
                                if (j10 >= 3){
                                    finalErro();
                                }
                            }
                            break;
                    }

                }

                audioTrack.stop();
                audioTrack.release();
            }
        };
        t.start();
    }


    public void onPause(){
        super.onPause();
        isRunning = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isRunning = true;
    }

    public void onDestroy(){
        super.onDestroy();
        isRunning = false;
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t = null;
    }

    public void ouviu(View view) {
        flagOuviu = 1;
    }

    private void mandarResultado(){
        Intent intent = new Intent(this,Fim3Activity.class);
        switch (dB){
            case 1:
                intent.putExtra("result1", "-10 dB");
                break;
            case 2:
                intent.putExtra("result1", "0 dB");
                break;
            case 3:
                intent.putExtra("result1", "10 dB");
                break;
            case 4:
                intent.putExtra("result1", "20 dB");
                break;
            case 5:
                intent.putExtra("result1", "30 dB");
                break;
            case 6:
                intent.putExtra("result1", "40 dB");
                break;
            case 7:
                intent.putExtra("result1", "50 dB");
                break;
            case 8:
                intent.putExtra("result1", "60 dB");
                break;
            case 9:
                intent.putExtra("result1", "70 dB");
                break;
            case 10:
                intent.putExtra("result1", "80 dB");
                break;

        }
        startActivity(intent);
    }

    private void finalErro(){
        Intent intent = new Intent(this,Fim2Activity.class);
        startActivity(intent);
    }


}


