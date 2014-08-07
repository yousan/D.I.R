package com.example.yousan.myapplication17;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.util.ArrayList;


public class MyActivity extends Activity {

    VideoView videoView = null;
    CircularArrayList<String> files = new CircularArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        init();
    }

    private void init() {
        videoView = (VideoView)findViewById(R.id.main_player);
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                updateFiles();
                play();
            }
        });
        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                updateFiles();
                play();
                return false;
            }
        });
        play();
    }

    private void updateFiles(){
        File targetDirectory = new File("/mnt/storage/Download");
        files = (CircularArrayList<String>) findFiles(targetDirectory);
    }

    private ArrayList<String> findFiles(File targetDirectory) {
        ArrayList<String> dirFiles;
        dirFiles = new ArrayList<String>();
        File[] listFiles = targetDirectory.listFiles();
        for (File file : listFiles) {
            if (file.isFile()) {
                dirFiles.add(file.getAbsolutePath());
            } else if (file.isDirectory()) {
                ArrayList<String> gotFiles = findFiles(file);
                for (String filename : gotFiles) {
                    dirFiles.add(filename);
                }
            }
        }
        return dirFiles;
    }

    private void play() {
        if (files.size() > 0) {
            videoView.setVideoPath(files.next());
            videoView.start();
        } else {
            Toast.makeText(this, "ファイルが見つかりませんでした。", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
