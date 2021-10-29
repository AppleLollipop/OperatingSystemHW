package util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * @author xzy
 * @create 2021/10/27 23:31
 */
public class Windows extends JFrame{
    JPanel jp1,jp2;//定义两个面板
    JButton jb1,jb2,jb3;//定义按钮
    JLabel jl1,jl2;
    JTextField jt1;
    JTextArea jare1,jare2;

    Windows(){
        jl1 = new JLabel("显示区");
        this.add(jl1);

        jp1=new JPanel();
        jp2=new JPanel();
        jare1 = new JTextArea(20,50);
        JScrollPane scrollPane_1 = new JScrollPane(jare1);
        scrollPane_1.setSize(10,10);
        scrollPane_1.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);//滚动条一直显示
        jare1.setEditable(false);//只读
        jare1.setLineWrap(true);//行过长自动换行
        jare1.setWrapStyleWord(true);//单词换行
        this.add(scrollPane_1);

        jl1 = new JLabel("输入区");
        this.add(jl1);

        jare2 = new JTextArea(10,50);//输入
        jare2.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if((char)e.getKeyChar()==KeyEvent.VK_ENTER) {
                    print(jare2.getText());
                    jare2.setText("");
                }
            }
        });

        JScrollPane scrollPane_2 = new JScrollPane(jare2);
        scrollPane_2.setSize(10,10);
        scrollPane_2.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);//滚动条一直显示
        jare2.setLineWrap(true);
        jare2.setWrapStyleWord(true);
        jp1.add(scrollPane_2);

        this.add(jp1);

        //设置窗口属性
        this.setLayout(new FlowLayout());
        this.setSize(600,600);
        this.setTitle("微系统");
        this.setLocationRelativeTo(null);//居中
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
    }
    public String print(String data){
        String str = jare1.getText();
        jare1.setText(str+data);

        return data;
    }

    public static void main(String[] args) {
        Windows windows = new Windows();
        Win2 win2 = new Win2();
    }
}

class Win2 extends JFrame{
    Win2(){
        setTitle("Win2");
        setBounds(100,100,400,400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Container c=getContentPane();
        c.setLayout(new GridLayout(9,1,10,10));

        c.add(new JLabel("进程数"));
        c.add(new JTextField());
        c.add(new JLabel("任务数"));
        c.add(new JTextField());
        c.add(new JLabel("内存大小"));
        c.add(new JTextField());
        c.add(new JLabel("管存大小"));
        c.add(new JTextField());

        JButton jButton = new JButton("确定");
        c.add(jButton);

        setVisible(true);
    }


}

class demo extends JFrame {
    JProgressBar pro3;//成员变量，自定义线程中需要使用
    JLabel  jLabel;

    public demo(){
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
        jLabel = new JLabel ("任务加载:");
        c.add(jLabel);

        pro3=new JProgressBar();
        pro3.setStringPainted(true);//信息被显示
        c.add(pro3);
        MyThread progress=new MyThread();//线程对象
//        progress.start();//启动线程


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
        demo frame=new demo();
        frame.setVisible(true);
    }
}
