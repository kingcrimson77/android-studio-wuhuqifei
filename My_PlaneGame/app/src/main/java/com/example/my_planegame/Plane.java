package com.example.my_planegame;

public class Plane extends Father_Object {

}
//敌机
class Enemy_plane extends Plane implements Runnable{
    //生成一个[10,20)的随机数 用来控制敌机速度 敌机速度是不一样的
    private long enemy_speed=(long) (Math.random()*10)+10;
    public int property;
    public boolean flog = false;

    public Enemy_plane() {
        property = (int) (Math.random()*3+1);//随机生成1-3的3个整数
        //敌机的宽、高
        w = 120 * Static_data.scale;//凡是涉及到像素的 都乘一下分辨率比例
        h = 80 * Static_data.scale;
        //敌机刷出来的位置
        setX((float) (Math.random() * (Static_data.Screen_w - w)));//x是随机的
        setY(-h);//在屏幕外 刚好看不到的位置
        hurt = 50;
        //随机加载3中不同的飞机
        choose_enemyPlane(property);
        Static_data.paint_list.add(this);//添加到要画的集合 这样才能被画出来
        Static_data.enemy_list.add(this);//添加到飞机的集合 添加进这个集合炮弹才打得到
        Static_data.hurt_myPlane.add(this);
        new Thread(this).start();//起飞！
    }

    public void choose_enemyPlane(int property){
        if (property==1){
            img = Static_data.enemy_plane1;
            hp = 300;
        }
        if(property==2){
            img = Static_data.enemy_plane2;
            hp =200;
        }
        if(property==3){
            img = Static_data.enemy_plane3;
            hp  = 150;
        }
    }

    @Override
    public void run() {

        int num=0;//子弹的计数器
        while(hp>0){//如果生命>0 没有死 就继续向前飞
            try {Thread.sleep(enemy_speed);} catch (InterruptedException e) {e.printStackTrace();}
            setY(rf.top+2*Static_data.scale);
            //走到屏幕的某个位置发射子弹
            if(rf.top>Static_data.Screen_h*0.1&&num<4){
                new Enemy_shell(this);
                num++;
            }
            if(rf.top>=Static_data.Screen_h)break;//敌人飞出屏幕 跳出循环
            if(hp<=0){
                new Enemy_shell(this);//死了之后再发射一个炮弹，此次可以加爆炸动画或者加成物品
                new Enemy_shell(this);
                new Enemy_shell(this);
                flog = true;
                Static_data.score+=100;//得分
            }
        }
        if(flog||rf.top>=Static_data.Screen_h){
            //从集合删除
            Static_data.paint_list.remove(this);
            Static_data.enemy_list.remove(this);
        }
    }
}
//我的飞机
class My_plane extends Plane implements Runnable{
    public boolean life = true;
    public int shell_buff=0;
    public My_plane(){
        w=120*Static_data.scale;//凡是涉及到像素的 都乘一下分辨率比例
        h=80*Static_data.scale;
        //设置初始位置
        setX(Static_data.Screen_w/2-w/2);
        setY(Static_data.Screen_h-2*w);
        if (Static_data.plane==1)
            img=Static_data.my_plane1;//初始化图片
        else if(Static_data.plane==2)
            img=Static_data.my_plane2;//初始化图片
        else img=Static_data.my_plane3;//初始化图片
        hp=200;//我的血量
        Static_data.paint_list.add(this);//添加到集合里 这样才能被画出来
        new Thread(this).start();//发射炮弹的线程
    }
    @Override
    public void run() {
        while(hp>0){
            //100毫秒发射一发炮弹
            try {Thread.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}
            if (shell_buff==0)
                new My_shell(this,0,0);
            else if (shell_buff==1){
                new My_shell(this,1,0.5);
                new My_shell(this,1,-1);
            }
            else if (shell_buff==2){
                new My_shell(this,2,0);
                new My_shell(this,2,1);
                new My_shell(this,2,-1);
            }
            else new My_shell(this,3,0);
            try {
                for (int i = 0; i < Static_data.hurt_myPlane.size(); i++) {
                    Father_Object hmp = Static_data.hurt_myPlane.get(i);
                    if (collision(this,hmp,20)){
                        hp-=hmp.hurt;
                        Static_data.hurt_myPlane.remove(hmp);
                        Static_data.paint_list.remove(hmp);
                    }
                }
            }catch (Exception e) { e.printStackTrace();break;}
        }
        life = false;
        Static_data.paint_list.remove(this);//生命值小于0，从集合移除，游戏结束
    }
}
class Boss extends  Plane implements Runnable{
    public Boss(int boss){
            My_plane mp = Static_data.my_Plane;
            if (collision(this,mp,30))
                mp.hp-=5;
            //boss的宽、高
            w = 600 * Static_data.scale;//凡是涉及到像素的 都乘一下分辨率比例
            h = 500 * Static_data.scale;
            choose_boss(boss);
            hp = 2000;
            //boss刷出来的位置
            setX((Static_data.Screen_w/2-w/2));//x是随机的
            setY(-h);//在屏幕外 刚好看不到的位置
            Static_data.paint_list.add(this);//添加到要画的集合 这样才能被画出来
            Static_data.enemy_list.add(this);//添加到飞机的集合 添加进这个集合我的炮弹才打得到
            new Thread(this).start();//起飞！
        }
        public void choose_boss(int boss){
            if (boss==1)
                img = Static_data.boss1;
            else img = Static_data.boss2;
        }
        @Override
        public void run() {
            while(hp>0){//如果生命>0 没有死
                try {Thread.sleep(30);} catch (InterruptedException e) {e.printStackTrace();}
               if(rf.top<0){
                   setY(rf.top+2);
               }else{
                   try {Thread.sleep(50);} catch (InterruptedException e) {e.printStackTrace();}//炮弹的发射频率
                   new boss_shell(this,3);
                   new boss_shell(this,1);
                   new boss_shell(this,2);
               }
                if(hp<=0){
                    Static_data.score+=1000;//得分
                    Static_data.level++;//关卡+1
                }
            }
            //从集合删除
            Static_data.paint_list.remove(this);
            Static_data.enemy_list.remove(this);
        }
}