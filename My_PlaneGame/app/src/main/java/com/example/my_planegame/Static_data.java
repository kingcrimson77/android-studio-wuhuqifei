package com.example.my_planegame;

import android.graphics.Bitmap;

import java.util.Vector;

public class Static_data {
    public static int plane=1;
    public static int level=1;//关卡
    public static int score=0;//击杀数
    public static int Screen_w,Screen_h;//屏幕的宽高
    public static float scale;//比例，用于适应不同屏幕
    // 所有要画的集合,添加进这个集合才能被画出来
    public static Vector<Father_Object> paint_list=new Vector<Father_Object>();
    //敌人飞机的集合，添加进这个集合才能被我的炮弹打中
    public static Vector<Father_Object> enemy_list=new Vector<Father_Object>();
    //可以打到我的集合
    public static Vector<Father_Object> hurt_myPlane=new Vector<Father_Object>();
    //图片：我的飞机 敌机 我的炮弹 敌人的炮弹背景
    public static Bitmap my_plane1,my_plane2,my_plane3;
    public static Bitmap my_shell1,my_shell2,my_shell3,my_shell4;
    public static Bitmap enemy_plane1,enemy_plane2,enemy_plane3;
    public static Bitmap enemy_shell1,enemy_shell2,enemy_shell3;
    public static Bitmap background1,background2,background3;
    public static Bitmap buff1,buff2;
    public static Bitmap boss1,boss2;
    public static Bitmap boss_shell1,boss_shell2,boss_shell3;
    public static Bitmap explosion;
    public static My_plane my_Plane;//我的灰机
    public static Background_a bg_a;//背景
    public static Background_b bg_b;//背景
//    public static Boss boss;
}
