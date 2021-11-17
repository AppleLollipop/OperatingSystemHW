package z_test;

import java.net.Socket;
import java.io.BufferedOutputStream;
import java.io.BufferedInputStream;
import java.util.Scanner;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

class ClientWindows extends JFrame{
    //定义组件
    JPanel jp1,jp2;//定义两个面板
    JButton jb1,jb2,jb3;//定义按钮
    JLabel jl1,jl2;
    JTextField jt1;
    JTextArea jare1,jare2;

    String[] emoij = {"w(?Д?)w","(??????)??","Σ( ° △ °|||)︴","凸(艹皿艹 )","(°ー°〃)","(ˉ▽￣～) 切~~","┑(￣Д ￣)┍","━┳━　━┳━","o( =?ω?= )m","ヽ(*。>Д<)o゜","━━(￣ー￣*|||━━",
            "X﹏X","⊙▽⊙","Σ(｀д′*ノ)ノ","Ｏ(≧口≦)Ｏ","o(*////▽////*)q","ヾ(￣▽￣)Bye~Bye~","?▽?","(?Д?*)?","(￣▽￣)～■干杯□～(￣▽￣)"};

    //构造函数
    ClientWindows()
    {
        ImageIcon im = new ImageIcon("timg2.gif");
        Image image = im.getImage();
        Image smallImage = image.getScaledInstance(150,400,Image.SCALE_FAST);
        ImageIcon img = new ImageIcon(smallImage);
        jl1 = new JLabel(img);
        this.add(jl1);

        jp1=new JPanel();
        jp2=new JPanel();
        jare1 = new JTextArea(28,50);
        JScrollPane scrollPane_1 = new JScrollPane(jare1);
        scrollPane_1.setSize(10,10);
        scrollPane_1.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);//滚动条一直显示
        jare1.setEditable(false);//只读
        jare1.setLineWrap(true);//行过长自动换行
        jare1.setWrapStyleWord(true);//单词换行
        this.add(scrollPane_1);

        //jb1 = new JButton("文件");
        //this.add(jb1);
        jb2 = new JButton("表情");
        jb2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame jfame = new JFrame("表情");
                jfame.setSize(500,500);
                jfame.setLayout(new FlowLayout());
                jfame.setLocationRelativeTo(null);
                jfame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                jfame.setResizable(false);
                jfame.setVisible(true);
                JTextArea jare4 = new JTextArea(30,48);
                ;

                for(int i = 0;i<emoij.length;i++){
                    jare4.append(emoij[i]);
                    if(i<emoij.length-1){
                        jare4.append("         ");
                    }
                }

                jare4.setEditable(false);//只读
                jare4.setLineWrap(true);//行过长自动换行
                jare4.setWrapStyleWord(true);//单词换行
                jfame.add(jare4);

            }
        });
        this.add(jb2);
        jb3 = new JButton("发送");

        this.add(jb3);
        add(new JLabel("          "));//添加空白标签来实现换行

        jare2 = new JTextArea(5,65);//输入
        JScrollPane scrollPane_2 = new JScrollPane(jare2);
        scrollPane_2.setSize(10,10);
        scrollPane_2.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);//滚动条一直显示
        jare2.setLineWrap(true);
        jare2.setWrapStyleWord(true);
        jp1.add(scrollPane_2);
        this.add(jp1);

        //设置窗口属性
        this.setLayout(new FlowLayout());
        this.setSize(700,700);
        this.setTitle("聊天窗口");
        this.setLocationRelativeTo(null);//居中
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
    }
}

class initClient extends ClientWindows
{
    Socket socket;
    final int wordslong = 128;
    String address = "47.102.213.160";
    final int host = 1234;
    String name;

    initClient()
    {
        super();
        //System.out.println("input your name");
        //Scanner nameread = new Scanner(System.in);
        //name = nameread.nextLine();
        name = JOptionPane.showInputDialog("input your name");

        try{
            socket = new Socket(address, host);
        }
        catch(Exception e)
        {
            //System.out.println(e.toString());
            out(e.toString());
        }
        receive();

        Scanner read = new Scanner(System.in);

        try
        {
            BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());
            String tellall = name+"join in";
            out.write(tellall.getBytes("GB2312"));
            out.flush();

            jb3.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try{
                        String str = jare2.getText();
                        jare2.setText("");
                        str = name+"says: "+str;
                        out.write(str.getBytes("GB2312"));
                        out.flush();
                    }
                    catch(Exception e1)
                    {
                        out(e1.toString());
                    }
                }
            });
        }
        catch(Exception e)
        {
            //System.out.println(e.toString());
            out(e.toString());
        }
    }

    //线程
    void receive()
    {
        new Thread(new Runnable()
        {
            public void run()
            {
                while(true)
                {
                    try
                    {
                        BufferedInputStream in = new BufferedInputStream(socket.getInputStream());
                        byte[] b = new byte[wordslong];
                        in.read(b);
                        String str = new String(b, "GB2312");
                        //System.out.println(str);
                        out(str);
                    }
                    catch(Exception e)
                    {
                        //System.out.println(e.toString());
                        out(e.toString());
                        break;
                    }
                }
            }
        }).start();
    }

    void out(String str)
    {
        jare1.append(str);
        jare1.append("\n");
    }
}

public class test01
{
    public static void main(String[] args)
    {
        initClient socket = new initClient();
    }
}
