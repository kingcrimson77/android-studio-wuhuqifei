package com.example.my_planegame;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class RankActivity extends AppCompatActivity {

    TextView tv_rank;
    byte[] buffer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        tv_rank = findViewById(R.id.tv_rank);
//        tv_rank.setText(getScore());

    }
    public String getScore(){
        FileInputStream fis = null;
        try {
            fis = openFileInput("score");
            buffer = new byte[fis.available()];
            fis.read(buffer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fis!=null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return  new String(buffer);
    }
}
