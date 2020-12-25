package com.example.my_planegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Gameover extends AppCompatActivity {
    Button bt_begin, bt_rank, bt_set, bt_exit;
    TextView tv_score;
    byte[] buffer = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); //隐藏标题栏
        setContentView(R.layout.activity_gameover);
        Intent intent = getIntent();

        tv_score = findViewById(R.id.tv_score);
        bt_begin = findViewById(R.id.bt_begin);
        bt_rank = findViewById(R.id.bt_rank);
        bt_set = findViewById(R.id.bt_set);
        bt_exit = findViewById(R.id.bt_exit);
        //得分
        String score  = intent.getStringExtra("Score");
        tv_score.setText("您的得分为："+score);
//        String s = score+" - "+getTime();
//        saveScore(s);
        //排行榜
        bt_rank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRank();
            }
        });
        //重新开始游戏
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
        //退出程序
        bt_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveTaskToBack(true);
                Gameover.this.finish();
                System.exit(0);
            }
        });
    }
    public void startSet(){
        Intent intent = new Intent(this,SetActivity.class);
        startActivity(intent);
    }
    public void startRank(){
        Intent intent = new Intent(this,RankActivity.class);
        startActivity(intent);
    }
    public void startGame () {
        Intent intent = new Intent(this, Game_Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        if(MusicService.isplay==true)
            startService(new Intent(this,MusicService.class));
        super.onStart();
    }
    //使用io流存储分数（有闪退问题）
    //    public String getTime(){
//        SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss"); //设置时间格式
//        formatter.setTimeZone(TimeZone.getTimeZone("GMT+08")); //设置时区
//        Date curDate = new Date(System.currentTimeMillis()); //获取当前时间
//        String createDate = formatter.format(curDate);   //格式转换
//        return createDate;
//    }
//    public void saveScore(String s){
//        FileOutputStream fos = null;
//        try {
//            fos = openFileOutput("score",MODE_PRIVATE);
//            fos.write(s.getBytes());
//            fos.flush();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//            if (fos!= null){
//                try {
//                    fos.close();
//                    Toast.makeText(this, "得分已经自动保存", Toast.LENGTH_SHORT).show();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//    public String getScore(){
//        FileInputStream fis = null;
//        try {
//            fis = openFileInput("score");
//            buffer = new byte[fis.available()];
//            fis.read(buffer);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//            if(fis!=null) {
//                try {
//                    fis.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return  new String(buffer);
//    }
}