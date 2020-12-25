package com.example.my_planegame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

public class Game_Activity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new Plane_Game(this));
        new Thread(new Runnable(){
            @Override
            public void run() {
                try {Thread.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}
                while (true){
                    if(Static_data.my_Plane.life==false){
                        String score = Static_data.score+"";
                        gameOver(score);
                        System.exit(0);
                    }
                }
            }
        }).start();

}
    public void gameOver(String score) {
        Intent intent = new Intent(this, Gameover.class);
        intent.putExtra("Score",score);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ){
                Game_Activity.this.finish();
                System.exit(0);
                Toast.makeText(getApplicationContext(),"再按一次退出游戏",Toast.LENGTH_SHORT).show();
                return true;
            }
        return false;
    }

}