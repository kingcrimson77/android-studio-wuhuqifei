package com.example.my_planegame;


public class Background extends Father_Object{

}
class Background_a extends Background implements Runnable {
    public Background_a(){
        w=Static_data.Screen_w;
        h=Static_data.Screen_h+10;//背景的高是 屏幕高的两倍
        choose_background();
        setX(0);
        setY(-5);
        Static_data.paint_list.add(this);
        new Thread(this).start();//启动滚动背景图片的线程
    }
public void choose_background(){
        if(Static_data.level==1)
            img=Static_data.background1;
        else if (Static_data.level==2)
            img=Static_data.background2;
        else img=Static_data.background3;
}
    @Override
    public void run() {

        //控制背景一直向下移,实现飞机前进的效果
        while(true){
            choose_background();
            try {Thread.sleep(10);} catch (InterruptedException e) {e.printStackTrace();}
            if(rf.top<=Static_data.Screen_h){
                setY(rf.top+2);
            }else{
                setY(-Static_data.Screen_h-5);
            }
        }
    }
}
class Background_b extends Background implements Runnable {

    public Background_b(){
        w=Static_data.Screen_w;
        h=Static_data.Screen_h+10;//背景的高是 屏幕高的两倍
        choose_background();
        setX(0);
        setY(-Static_data.Screen_h-5);
        Static_data.paint_list.add(this);
        new Thread(this).start();//启动滚动背景图片的线程
    }
    public void choose_background(){
        if(Static_data.level==1)
            img=Static_data.background1;
        else if (Static_data.level==2)
            img=Static_data.background2;
        else img=Static_data.background3;
    }
    @Override
    public void run() {
        //控制背景一直向下移,实现飞机前进的效果
        while(true){
            choose_background();
            try {Thread.sleep(10);} catch (InterruptedException e) {e.printStackTrace();}
                if(rf.top<=Static_data.Screen_h){
                    setY(rf.top+2);
            }else{
                setY(-Static_data.Screen_h-5);
            }
        }
    }
}