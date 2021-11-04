package util.windows;

import javax.swing.*;
import java.awt.*;

public class Resources extends JFrame {
    JProgressBar pro3;//成员变量，自定义线程中需要使用
    JLabel  jLabel;

    public Resources(){
        setTitle("资源状态");
        setBounds(100,100,400,400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Container c=getContentPane();
        c.setLayout(new GridLayout(6,1,10,10));
        //-----------------------------------------
        jLabel = new JLabel ("运行状态:");
        c.add(jLabel);

        JProgressBar pro1=new JProgressBar();
        pro1.setIndeterminate(true);//不确定进度条
        pro1.setStringPainted(true);//显示信息
        pro1.setString("运行中");//提示信息
        c.add(pro1);

        //-----------------------------------------
        jLabel = new JLabel ("进程占用:");
        c.add(jLabel);

        JProgressBar pro2=new JProgressBar();
        pro2.setIndeterminate(false);//确定的进度条
        pro2.setValue(20);//当前进度
        pro2.setStringPainted(true);//信息被显示，20%
        c.add(pro2);

        //-----------------------------------------
        jLabel = new JLabel ("内存占用:");
        c.add(jLabel);

        pro3=new JProgressBar();
        pro3.setStringPainted(true);//信息被显示
        c.add(pro3);
        MyThread progress=new MyThread();//线程对象
//        progress.start();//启动线程

        setVisible(true);
    }
    class MyThread extends Thread{//自定义线程，实现进度的不断变化
        @Override
        public void run() {
            for (int i=0;i<=100;i++){
                pro3.setValue(i);//让进度不断变化
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Resources frame=new Resources();
        frame.setVisible(true);
    }
}