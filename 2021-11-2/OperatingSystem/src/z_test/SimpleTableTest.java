package z_test;

import java.awt.*;
import javax.swing.*;
public class SimpleTableTest extends JFrame{

    private static final long serialVersionUID = 1L;
    protected JTable table;
    protected Object[][] data;
    protected String[] colname = {"编号","书名","作者","主角","类别","选择"};
    public SimpleTableTest(){
        Container pane = getContentPane();
        pane.setLayout(new BorderLayout());
        createTableData();  //创建表格所需数据
        table = getSimpleTable();
        JScrollPane jsPane = new JScrollPane(table);
        pane.add(jsPane,BorderLayout.CENTER);
    }
    public void createTableData(){
        data = new Object[10][6];
        int i =0;
        data[i++] = new Object[]{"01","射雕英雄传","金庸","郭靖","武侠",true};
        data[i++] = new Object[]{"02","神雕侠侣"  ,"金庸","杨过","武侠",false};
        data[i++] = new Object[]{"03","笑傲江湖"  ,"金庸","令狐冲","武侠",true};
        data[i++] = new Object[]{"04","鹿鼎记"  ,"金庸","韦小宝","武侠",false};
        data[i++] = new Object[]{"05","大旗英雄传"  ,"古龙","铁中棠","武侠",false};
        data[i++] = new Object[]{"06","陆小凤传奇"  ,"古龙","陆小凤","武侠",false};
        data[i++] = new Object[]{"07","多情剑客无情剑"  ,"古龙","李寻欢","武侠",false};
        data[i++] = new Object[]{"08","三国演义"  ,"罗贯中","无","古典名著",false};
        data[i++] = new Object[]{"09","封神演义"  ,"陈仲琳","无","古典名著",false};
        data[i++] = new Object[]{"10","绿野仙踪"  ,"李百川","冷于冰","古典名著",false};
    }
    public static void main(String[] args){
        SimpleTableTest stt= new SimpleTableTest();
        stt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        stt.setSize(400, 200);
        stt.setVisible(true);
    }
    /**
     * 返回一个简单的表格
     * @return
     */
    public JTable getSimpleTable(){
        table = new JTable(data,colname);
        return table;
    }
}