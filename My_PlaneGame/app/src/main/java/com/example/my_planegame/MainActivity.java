package com.example.my_planegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private long time;//用于检测按两次 "再按一次退出游戏"
    Button bt_begin,bt_rank,bt_set,bt_exit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); //隐藏标题栏
        setContentView(R.layout.begin);

        bt_begin =findViewById(R.id.bt_begin);
        bt_rank = findViewById(R.id.bt_rank);
        bt_set = findViewById(R.id.bt_set);
        bt_exit = findViewById(R.id.bt_exit);
        //开始游戏
        bt_begin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });
        //设置
        bt_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSet();
            }
        });
        //结束游戏
        bt_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.finish();
                System.exit(0);
            }
        });

    }
    public void startSet(){
        Intent intent = new Intent(this,SetActivity.class);
        startActivity(intent);
    }
    public void startGame() {
        Intent intent = new Intent(this, Game_Activity.class);
        startActivity(intent);
    }
    //点击两次返回键退出程序的事件
    public boolean onKeyDown(int keyCode, KeyEvent event) { //返回键
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
            long t=System.currentTimeMillis();//获取系统时间
            if(t-time<=500){
                exit(); //如果500毫秒内按下两次返回键则退出游戏
            }else{
                time=t;
                Toast.makeText(getApplicationContext(),"再按一次退出游戏",Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return false;
    }
    //退出
    public void exit(){
        MainActivity.this.finish();
        new Thread(new Runnable(){
            @Override
            public void run() {
                try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
                System.exit(0);
            }
        }).start();
    }
    @Override
    protected void onStart() {
        if(MusicService.isplay==true)
            startService(new Intent(this,MusicService.class));
        super.onStart();
    }
}