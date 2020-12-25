package com.example.my_planegame;

import android.graphics.Bitmap;
import android.graphics.RectF;

public class Father_Object {
    public RectF rf=new RectF();//获取物体所在的矩形用于碰撞检测
    public int hp;//血量
    public int hurt;
    public float w,h;//物体的宽高
    public Bitmap img;//图片
    //用set方法设置该物体的位置
    public void setX(float x){
        rf.left=x;
        rf.right=x+w;
    }
    public void setY(float y){
        rf.top=y;
        rf.bottom=y+h;
    }
    //碰撞检测
    public boolean collision(Father_Object obj_z,Father_Object obj_bz,float px){//传入主动撞与被动撞两个对象，以及忽略像素
        px*=Static_data.scale;
        if(obj_z.rf.left-obj_bz.rf.left+px<=obj_bz.w&&obj_bz.rf.left-obj_z.rf.left+px<=obj_z.w){
            if(obj_z.rf.top-obj_bz.rf.top+px<=obj_bz.h&&obj_bz.rf.top-obj_z.rf.top+px<=obj_z.h){
                return true;
            }
        }
        return false;
    }
}