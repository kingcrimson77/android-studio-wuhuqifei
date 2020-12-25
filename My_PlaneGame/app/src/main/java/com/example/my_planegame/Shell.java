package com.example.my_planegame;

public class Shell extends Father_Object{
    public float shell_speed;
}
//我的炮弹
class My_shell extends Shell implements Runnable{

    public My_shell(Plane my_plane,int buff,double pos) {
        w = 60 * Static_data.scale;//凡是涉及到像素的 都乘一下分辨率比例
        h = 180 * Static_data.scale;
        choose(buff);//选择buff
        shell_speed = 6 * Static_data.scale;//速度=6
        //设在玩家中心的偏上一点
        setX((float) (my_plane.rf.left + my_plane.w / 2 - w / 2+pos*w));//通过pos来控制子弹的位置
        setY(my_plane.rf.top - h / 2);
        Static_data.paint_list.add(this);//添加到集合里 这样才能被画出来
        new Thread(this).start();//新建一个炮弹向上移动的线程
    }
    @Override
    public void run() {
        boolean flag = false;//一个标记 用来跳出嵌套循环
        while (true) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setY(rf.top - shell_speed);//向上移shell_speed个像素，shell_speed=6
            try {
                //这里判断有没有和集合里的敌人发生碰撞
                for (int i = 0; i < Static_data.enemy_list.size(); i++) {
                    Father_Object ep = Static_data.enemy_list.get(i);
                    if (collision(this,ep,10)) {//判断碰撞
                        ep.hp -= hurt;//敌人生命-子弹伤害
                        flag = true;//标记炮弹击中了敌飞机 用来跳出嵌套循环
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
            if (flag || rf.bottom <= 0) break;//如果子弹击中过敌人 或者超出屏幕范围 跳出循环
        }
        Static_data.paint_list.remove(this);//从集合删除
    }
    public void choose(int buff){
        if (buff==0){
            img = Static_data.my_shell1;
            hurt = 10;
        }
        if (buff==1){
            img = Static_data.my_shell2;
            hurt = 15;
        }
        if (buff==2){
            img = Static_data.my_shell3;
            hurt = 20;
        }
        if (buff==3){
            img = Static_data.my_shell4;
            hurt = 90;
            w=buff*60 * Static_data.scale;;
        }
    }
}
//敌人的炮弹
class Enemy_shell extends Shell implements Runnable{

    private float direction =(float)(Math.random()*2)-1;//x轴的方向，可以让子弹斜的飞
    private int p = 7;//子弹的偏转程度

    public Enemy_shell(Enemy_plane enemy_plane){
        w = 40*Static_data.scale;//乘比例
        h = 40*Static_data.scale;
        //根据三种不同的飞机 生成三种不同的子弹
        choose_enemyShell(enemy_plane);
        shell_speed=5*Static_data.scale;
        //放在敌人的飞机中间的位置
        setX(enemy_plane.rf.left+enemy_plane.w/2-w/2);
        setY(enemy_plane.rf.top+h/2);
        Static_data.paint_list.add(this);//放进这个集合里才能被画出来
        Static_data.hurt_myPlane.add(this);
        new Thread(this).start();//让子弹飞！！！
    }
    public void choose_enemyShell(Enemy_plane enemy_plane){
        if (enemy_plane.property==1){
            img = Static_data.enemy_shell1;
            hurt=20;
        }
        if(enemy_plane.property==2){
            img = Static_data.enemy_shell2;
            hurt=40;
        }
        if(enemy_plane.property==3){
            img = Static_data.enemy_shell3;
            hurt=50;
        }
    }
    @Override
    public void run() {
        while (true){
            try { Thread.sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }
            setX(rf.left+direction*shell_speed*p);
            setY(rf.bottom + shell_speed);
//            try {
//                //这里判断有没有和我的飞机发生碰撞
//                    Father_Object mp = Static_data.my_Plane;
//                    if (collision(this,mp,10)) {//判断碰撞
//                        mp.hp -= hurt;//我的飞机的生命-子弹伤害
//                        mark = true;//标记炮弹击中了我的飞机
//                        break;
//                    }
//            } catch (Exception e) {
//                e.printStackTrace();
//                break;
//            }
            //如果子弹击中我 或者超出屏幕范围跳出循环
            if (rf.top>=Static_data.Screen_h||rf.left>Static_data.Screen_w||rf.right<=0) break;
        }
        Static_data.paint_list.remove(this);//从集合删除
        }
    }

class boss_shell extends Shell implements  Runnable{
    private int p = 6;//子弹的偏转程度
    private float direction1 =(float)(Math.random()*2)-1;//x轴的方向，可以让子弹斜的飞
    private float direction2 =(float)(Math.random()*2)-1;//x轴的方向，可以让子弹斜的飞
    public boss_shell(Boss boss,int shell){
        w = 100*Static_data.scale;//乘比例
        h = 100*Static_data.scale;
        choose_shell(shell);
        shell_speed=5*Static_data.scale;
        hurt = 20;
        //放在boss最中间的位置
        setX(boss.rf.left+boss.w/2-w/2);
        setY(boss.rf.top+boss.h/2-h/2);
        Static_data.paint_list.add(this);//放进这个集合里才能被画出来
        Static_data.hurt_myPlane.add(this);
        new Thread(this).start();
    }
    public void choose_shell(int shell){
        if (shell==1)
            img = Static_data.boss_shell1;
        else if(shell==2)
            img = Static_data.boss_shell2;
        else
            img = Static_data.boss_shell3;
    }
    @Override
    public void run() {
        while(true) {
            try { Thread.sleep(20); } catch (InterruptedException e) { e.printStackTrace(); }
            setX((float) (rf.left+ shell_speed*direction1*3));
            setY((float) (rf.top+ shell_speed*direction2*2));
//            try {
//                //这里判断有没有和我的飞机发生碰撞
//                Father_Object mp = Static_data.my_Plane;
//                if (collision(this,mp,10)) {//判断碰撞
//                    mp.hp -= hurt;//我的飞机的生命-子弹伤害
//                    mark = true;//标记炮弹击中了我的飞机
//                    break;
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                break;
//            }
            if (rf.top>=Static_data.Screen_h||rf.left>Static_data.Screen_w||rf.right<=0||rf.bottom<0) break;
        }
        Static_data.paint_list.remove(this);//从集合删除
    }
}
