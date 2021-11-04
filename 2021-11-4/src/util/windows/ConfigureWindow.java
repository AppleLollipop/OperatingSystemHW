package util.windows;

import bean.Configure;
import bean.MemoryData;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * @author xzy
 * @create 2021/11/4 13:11
 */
public class ConfigureWindow extends JFrame{
    JPanel jp1;
    JTextArea jare1;

    public ConfigureWindow(){

        jp1=new JPanel();
        jare1 = new JTextArea(33,50);
        JScrollPane scrollPane_1 = new JScrollPane(jare1);
        scrollPane_1.setSize(10,10);
        scrollPane_1.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);//滚动条一直显示
        jare1.setEditable(false);//只读
        jare1.setLineWrap(true);//行过长自动换行
        jare1.setWrapStyleWord(true);//单词换行
        this.add(scrollPane_1);

        this.add(jp1);

        //设置窗口属性
        this.setLayout(new FlowLayout());
        this.setSize(600,600);
        this.setTitle("资源使用情况");
//        this.setLocationRelativeTo(null);//居中
        this.setLocation(1300,200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
    }
    public void print(Configure configure, String pdata){
//        String str = jare1.getText();
        String str = configure.toString();
        str+="\n\n\n\n"+pdata;
        jare1.setText(str);
    }

    public static void main(String[] args) {
        ConfigureWindow windows = new ConfigureWindow();
    }
}
