package util;

import javax.swing.*;

/**
 * @author xzy
 * @create 2021/11/4 12:27
 */
public class Warning {
    public void init(String data){
//        JOptionPane.showMessageDialog(null, data, "年龄请输入数字", JOptionPane.ERROR_MESSAGE);
        int res = JOptionPane.showConfirmDialog(null, data ,"是否继续", JOptionPane.YES_NO_OPTION);
        if(res==JOptionPane.YES_OPTION){
//            System.out.println("选择是后执行的代码");    //点击“是”后执行这个代码块
            return;
        }else{
//            System.out.println("选择否后执行的代码");    //点击“否”后执行这个代码块
            return;
        }
    }

    public static void main(String[] args) {
        new Warning().init("warning");
    }
}
