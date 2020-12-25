package com.example.my_planegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class SetActivity extends AppCompatActivity {

    ImageView iv_music,iv_plane1,iv_plane2,iv_plane3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); //隐藏标题栏
        setContentView(R.layout.activity_set);
        final Intent intent = new Intent(this,MusicService.class);
        iv_music = findViewById(R.id.iv_music);
        iv_plane1 = findViewById(R.id.iv_pane1);
        iv_plane2 = findViewById(R.id.iv_pane2);
        iv_plane3 = findViewById(R.id.iv_pane3);

        iv_plane1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Static_data.plane=1;
                Toast.makeText(getApplicationContext(),"您已经选中飞机1",Toast.LENGTH_SHORT).show();
            }
        });
        iv_plane2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Static_data.plane=2;
                Toast.makeText(getApplicationContext(),"您已经选中飞机2",Toast.LENGTH_SHORT).show();
            }
        });
        iv_plane3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Static_data.plane=3;
                Toast.makeText(getApplicationContext(),"您已经选中飞机3",Toast.LENGTH_SHORT).show();
            }
        });

        iv_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //启动或停止MusicService
                if(MusicService.isplay==false){
                    startService(intent);
                    ((ImageView)v).setImageDrawable(getResources().getDrawable(R.drawable.sound,null));
                }else {
                    stopService(intent);
                    ((ImageView)v).setImageDrawable(getResources().getDrawable(R.drawable.sound_close,null));
                }
            }
        });
    }
}
