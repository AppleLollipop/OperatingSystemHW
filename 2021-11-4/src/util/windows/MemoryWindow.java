package util.windows;

import bean.MemoryData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class MemoryWindow extends JFrame{
    JPanel jp1;
    JTextArea jare1;

    public MemoryWindow(){

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
        this.setTitle("内存块使用情况");
//        this.setLocationRelativeTo(null);//居中
        this.setLocation(10,200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
    }
    public void print(List list){
//        String str = jare1.getText();
        String str = "内存块号    内存大小    使用情况    占用进程\n";
        for (int i = 0; i < list.size(); i++){
            MemoryData memoryData = (MemoryData) list.get(i);
            str +=  i+"                  "+
                    memoryData.getSpace()+"                  "+
                    memoryData.getState()+"                  "+
                    memoryData.getPcbname()+"                  "+
                    "\n";
        }
        jare1.setText(str);
    }

    public static void main(String[] args) {
        MemoryWindow windows = new MemoryWindow();
    }
}
