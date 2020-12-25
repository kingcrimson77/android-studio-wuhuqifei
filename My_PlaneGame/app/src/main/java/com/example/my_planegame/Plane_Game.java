package com.example.my_planegame;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class Plane_Game extends View {

    private Paint paint=new Paint();//画笔
    private float click_x,click_y;//按下屏幕时的坐标
    private float click_my_plane_x,click_my_plane_y;//按下屏幕时我的飞机的坐标
    Boss boss1,boss2 ;

    public Plane_Game(Context context) {
        super(context);
        control_myPlane();//控制我的飞机
        loading_img();//加载所有图片
        new Thread(new repaint()).start();//新建一个线程 让画布自动重绘
        new Thread(new load_enemyPlane()).start();//新建一个 加载敌人的线程
        new Thread(new load_buff()).start();//新建一个 加载buff的线程
        new Thread(new load_boss()).start();//新建一个 加载boss的线程

    }

    @Override
    //绘制屏幕上的所有物体
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(int i=0;i<Static_data.paint_list.size();i++){//我们把所有的飞行物都添加到了my.list这个集合里
            Father_Object fly = Static_data.paint_list.get(i);           //然后在这里用一个for循环画出来
            canvas.drawBitmap(fly.img,null,fly.rf,paint);
        }
        paint.setColor(Color.RED);
        canvas.drawText("得分："+Static_data.score,0,50,paint);
        if (Static_data.my_Plane.hp<0)Static_data.my_Plane.hp=0;
        canvas.drawText("生命："+Static_data.my_Plane.hp,Static_data.Screen_w/2,Static_data.Screen_h-50,paint);
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {//获取屏幕宽高

        super.onSizeChanged(w, h, oldw, oldh);
        Static_data.Screen_w=w;//获取宽
        Static_data.Screen_h=h;//高
        Static_data.scale = (float) (Math.sqrt(Static_data.Screen_w * Static_data.Screen_h)/ Math.sqrt(1920 * 1080));
        paint.setTextSize(50*Static_data.scale);//设置字体大小，“击杀”的大小
        Static_data.bg_a = new Background_a();
        Static_data.bg_b = new Background_b();
        Static_data.my_Plane=new My_plane();//初始化 我的灰机

    }
    //加载图片的方法
    public void loading_img(){
        Static_data.my_plane1 = BitmapFactory.decodeResource(getResources(),R.mipmap.my_plane1);//加载我的飞机图片
        Static_data.my_plane2 = BitmapFactory.decodeResource(getResources(),R.mipmap.my_plane2);
        Static_data.my_plane3 = BitmapFactory.decodeResource(getResources(),R.mipmap.my_plane3);

        Static_data.enemy_plane1 = BitmapFactory.decodeResource(getResources(),R.mipmap.enemy_plane1);//加载敌人的飞机图片
        Static_data.enemy_plane2 = BitmapFactory.decodeResource(getResources(),R.mipmap.enemy_plane2);
        Static_data.enemy_plane3 = BitmapFactory.decodeResource(getResources(),R.mipmap.enemy_plane3);

        Static_data.my_shell1 = BitmapFactory.decodeResource(getResources(),R.mipmap.my_shell1);//加载我的炮弹
        Static_data.my_shell2 = BitmapFactory.decodeResource(getResources(),R.mipmap.my_shell2);
        Static_data.my_shell3 = BitmapFactory.decodeResource(getResources(),R.mipmap.my_shell3);
        Static_data.my_shell4 = BitmapFactory.decodeResource(getResources(),R.mipmap.my_shell4);

        Static_data.enemy_shell1 = BitmapFactory.decodeResource(getResources(),R.mipmap.enemy_shell1);//加载敌机的炮弹
        Static_data.enemy_shell2 = BitmapFactory.decodeResource(getResources(),R.mipmap.enemy_shell2);
        Static_data.enemy_shell3 = BitmapFactory.decodeResource(getResources(),R.mipmap.enemy_shell3);

        Static_data.background1 = BitmapFactory.decodeResource(getResources(), R.mipmap.bg1);//加载背景图片
        Static_data.background2 = BitmapFactory.decodeResource(getResources(), R.mipmap.bg2);
        Static_data.background3 = BitmapFactory.decodeResource(getResources(), R.mipmap.bg3);

        Static_data.buff1 = BitmapFactory.decodeResource(getResources(), R.mipmap.buff1);//加载buff图片
        Static_data.buff2 = BitmapFactory.decodeResource(getResources(), R.mipmap.buff2);

        Static_data.boss1 = BitmapFactory.decodeResource(getResources(), R.mipmap.boss1);
        Static_data.boss2 = BitmapFactory.decodeResource(getResources(), R.mipmap.boss2);

        Static_data.boss_shell1 = BitmapFactory.decodeResource(getResources(), R.mipmap.boss_shell1);
        Static_data.boss_shell2 = BitmapFactory.decodeResource(getResources(), R.mipmap.boss_shell2);
        Static_data.boss_shell3 = BitmapFactory.decodeResource(getResources(), R.mipmap.boss_shell3);

    }
    //控制我的飞机的方法
    public void control_myPlane(){
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent e) {
                if(e.getAction()==MotionEvent.ACTION_DOWN){//ACTION_DOWN：按下时
                    click_x=e.getX();
                    click_y=e.getY();
                    click_my_plane_x=Static_data.my_Plane.rf.left;
                    click_my_plane_y=Static_data.my_Plane.rf.top;
                }
                float move_x=click_my_plane_x+e.getX()-click_x;
                float move_y=click_my_plane_y+e.getY()-click_y;
                //我的飞机不能飞出屏幕
                move_x=move_x<Static_data.Screen_w-Static_data.my_Plane.w/2?move_x:Static_data.Screen_w-Static_data.my_Plane.w/2;
                move_x=move_x>-Static_data.my_Plane.w/2?move_x:-Static_data.my_Plane.w/2;
                move_y=move_y<Static_data.Screen_h-Static_data.my_Plane.h/2?move_y:Static_data.Screen_h-Static_data.my_Plane.h/2;
                move_y=move_y>-Static_data.my_Plane.h/2?move_y:-Static_data.my_Plane.h/2;
                Static_data.my_Plane.setX(move_x);
                Static_data.my_Plane.setY(move_y);
                return true;
            }
        });
    }
    //重画
    private class repaint implements Runnable {
        @Override
        public void run() {
            //每10ms刷新一次界面
            while(true){
                try { Thread.sleep(1);} catch (InterruptedException e) {e.printStackTrace();}
                postInvalidate();//刷新画布
            }
        }
    }
    //加载敌人的飞机的类
    private class load_enemyPlane implements Runnable{
        @Override
        public void run() {

            while(true){
                //每1.5s刷一个敌人
                try {Thread.sleep(1500);} catch (InterruptedException e) {e.printStackTrace();}
                try {
                    new Enemy_plane();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //加载buff的类
    private class load_buff implements Runnable{
        public void run() {
            while(true){
                //每10s刷一个buff
                try {Thread.sleep(5000);} catch (InterruptedException e) {e.printStackTrace();}
                try {
                    new Buff();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //加载boss的类
    private class load_boss implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boss1 = new Boss(1);//游戏开始15s后boss1出场
            while (true) {
                if (boss1.hp <= 0) {//boss1死后boss登场
                    try {
                        Thread.sleep(15000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    boss2 = new Boss(2);
                    break;
                }
            }
        }
    }
}

