package com.example.my_planegame;

public class Buff extends Father_Object implements Runnable {
    public int property;
    public int add_hp;
    private float direction =(float)(Math.random()*2)-1;//方向
    public Buff(){
        property = (int) (Math.random()*2+1);//随机生成1-2的2个整数
        w = 100*Static_data.scale;//乘比例
        h = 100*Static_data.scale;
        if(property==1){
            img = Static_data.buff1;//炮弹加成
        }
        if (property==2){
            img = Static_data.buff2;//生命加成
            add_hp = 50;
        }
        setX((float)( Math.random() * (Static_data.Screen_w - w)));//x是随机的
        setY(-h);
        Static_data.paint_list.add(this);//放进这个集合里才能被画出来
        new Thread(this).start();
    }
    @Override
    public void run() {
        boolean mark = false;//标记buff有没有碰到我
        while (true){
            try { Thread.sleep(10); } catch (InterruptedException e) { e.printStackTrace(); }
            //如果碰到两边的墙壁，取反方向
            if (rf.left<=0||rf.right>=Static_data.Screen_w)
            direction=-direction;
            setX(rf.left+direction*10*Static_data.scale);
            setY(rf.top+2*Static_data.scale);
            try {
                //这里判断有没有和我的飞机发生碰撞
                My_plane mp = Static_data.my_Plane;
                if (collision(this,mp,10)) {//判断碰撞
                    if(property==2)
                        mp.hp += add_hp;//我的飞机的生命+add buff
                    else mp.shell_buff++;
                    mark = true;//标记炮弹击中了我的飞机
                    Static_data.score+=50;
                    break;
                }
                if(mp.hp>200)//判断我的生命不超过200
                    mp.hp=200;
                if (mp.shell_buff>3)//判断子弹加成不超过3
                    mp.shell_buff=3;
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
            //如果子弹击中我 或者超出屏幕范围跳出循环
            if (mark || rf.top>=Static_data.Screen_h) break;
        }
        Static_data.paint_list.remove(this);//从集合删除
    }

    }

