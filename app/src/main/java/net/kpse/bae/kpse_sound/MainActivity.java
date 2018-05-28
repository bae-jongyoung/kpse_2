package net.kpse.bae.kpse_sound;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    static MediaPlayer mp;
    Button start_1,start_2,start_3,start_4,start_5,start_6,stop;
    Button start_11,start_12,start_13,start_14;
    String musicURI;
    String path_url;

    static TextView textView1, textView2;
    static ProgressBar seekBar1;
    int total_time = 0;

    private static Handler seekHandler = new Handler();


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 화면꺼짐 방지
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        mp = new MediaPlayer();
        //getAbsolutePath
        path_url = Environment.getExternalStorageDirectory().getPath()+"/Music/";

        textView2 = (TextView) findViewById(R.id.extra_path);
        textView2.setText(path_url);

        textView1 = (TextView) findViewById(R.id.total_time);
        seekBar1= (ProgressBar) findViewById(R.id.seek_bar);

        stop = (Button)findViewById(R.id.button0);
        start_1 = (Button)findViewById(R.id.button1);
        start_2 = (Button)findViewById(R.id.button2);
        start_3 = (Button)findViewById(R.id.button3);
        start_4 = (Button)findViewById(R.id.button4);
        start_5 = (Button)findViewById(R.id.button5);
        start_6 = (Button)findViewById(R.id.button6);
        start_11 = (Button)findViewById(R.id.button11);
        start_12 = (Button)findViewById(R.id.button12);
        start_13 = (Button)findViewById(R.id.button13);
        start_14 = (Button)findViewById(R.id.button14);

        start_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicURI = "1.mp3";
                playMusic(musicURI);
            }
        });

        start_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicURI = "2.mp3";
                playMusic(musicURI);
            }
        });

        start_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicURI = "3.mp3";
                playMusic(musicURI);
            }
        });

        start_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicURI = "4.mp3";
                playMusic(musicURI);
            }
        });

        start_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicURI = "withjesus.mp3";
                playMusic(musicURI);
            }
        });

        start_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicURI = "spirit_song.mp3";
                playMusic(musicURI);
            }
        });

        start_11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicURI = path_url+"song01.mp3";
                playMusic2(musicURI);
            }
        });

        start_12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicURI = path_url+"song02.mp3";
                playMusic2(musicURI);
            }
        });

        start_13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicURI = path_url+"song03.mp3";
                playMusic2(musicURI);
            }
        });

        start_14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicURI = path_url+"song04.mp3";
                playMusic2(musicURI);
            }
        });


        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.reset();
            }
        });
    }
    //출처: http://ensider.tistory.com/entry/Android-Programming-간단한-Music-Player-만들기-1 []
    public void playMusic2(String uri) {
        try {
            mp.reset();

            // 직접 폴더로 접근할떄
            mp.setDataSource(this, Uri.parse(uri));
            mp.prepare();

            total_time = mp.getDuration(); // 총 재생시간
            total_time = total_time / 1000;
            textView1.setText( String.valueOf("0 / "+total_time));

            mp.start();

            seekHandler.postDelayed(run, 1000);

            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    // TODO
                    // Do something when playing is completed
                }
            });

        } catch (IOException e) {
            Log.v("SimplePlayer", e.getMessage());
        }
    }

    public void playMusic(String uri) {
        try {
            mp.reset();

            AssetFileDescriptor descriptor = getAssets().openFd(uri);
            mp.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
            descriptor.close();

            // 직접 폴더로 접근할떄
            //mp.setDataSource(this, Uri.parse(uri));
            mp.prepare();

            total_time = mp.getDuration(); // 총 재생시간
            total_time = total_time / 1000;
            textView1.setText( String.valueOf("0 / "+total_time));

            mp.start();

            seekHandler.postDelayed(run, 1000);

            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    // TODO
                    // Do something when playing is completed
                }
            });

        } catch (IOException e) {
            Log.v("SimplePlayer", e.getMessage());
        }
    }

        private static Runnable run = new Runnable() {

            public void run() {
                if(mp.isPlaying()){

                    int mediaPos_new = mp.getCurrentPosition();
                    int mediaMax_new = mp.getDuration();

                    //int seek_persent = (mediaMax_new / 100  ) * mediaPos_new;
                    int seek_persent = mediaPos_new / (mediaMax_new / 100) ;
                    // seek_persent = Math.round(seek_persent);

                    //  Log.e("baemp",seek_persent+"<="+mediaMax_new +" - "+mediaPos_new );

                    mediaPos_new = mediaPos_new / 1000;
                    mediaMax_new = mediaMax_new / 1000;
                    textView1.setText( String.valueOf( mediaPos_new +" / "+mediaMax_new));

                    // seekBar1.setMax(mediaMax_new);
                    seekBar1.setProgress(seek_persent);


                    seekHandler.postDelayed(run, 1000); //Looping the thread after 0.1 second // seconds
                }
            }
        };




}
