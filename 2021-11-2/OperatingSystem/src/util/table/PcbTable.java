package util.table;

import bean.JCB;
import bean.PCB;
import dao.JcbDao;
import dao.PcbDao;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * @author xzy
 * @create 2021/11/2 14:50
 */
public class PcbTable extends JFrame {
    List list;
    private static final long serialVersionUID = 1L;
    protected JTable table;
    protected Object[][] data;
    protected String[] colname = {"id","name","needSpace","priority","type","time"};
    public PcbTable(){
        setTitle("进程列表");
        Container pane = getContentPane();
        pane.setLayout(new BorderLayout());
        createTableData();  //创建表格所需数据
        table = getSimpleTable();
        JScrollPane jsPane = new JScrollPane(table);
        pane.add(jsPane,BorderLayout.CENTER);
    }
    public void createTableData(){
        list = new PcbDao().select();
        data = new Object[list.size()][colname.length];
        for(int i = 0; i < list.size(); i++){
            PCB pcb = (PCB) list.get(i);
            data[i] = new Object[]{pcb.getId(),pcb.getName(),pcb.getNeedSpace(),pcb.getPriority(),pcb.getType(),pcb.getTime()};
        }
    }
    public static void main(String[] args){
        new PcbTable().init();
    }
    public List init(){
        PcbTable stt= new PcbTable();
        stt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        stt.setSize(400, 200);
        stt.setVisible(true);

        return list;
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
