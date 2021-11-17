package util.table;

import bean.PCB;
import dao.PcbDao;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * @author xzy
 * @create 2021/11/2 14:50
 */
public class PcbTable extends JFrame {
    private List list;
    private Container pane;
    private static final long serialVersionUID = 1L;
    protected JTable table;
    protected Object[][] data;
    protected String[] colname = {"id","name","needSpace","priority","type","needTime","arriveTime","runTime","state"};
    public PcbTable(){
        setTitle("进程列表");
        pane = getContentPane();
        pane.setLayout(new BorderLayout());
    }
    public void createTableData(List list){
        //list = new PcbDao().select();
        data = new Object[list.size()][colname.length];
        for(int i = 0; i < list.size(); i++){
            PCB pcb = (PCB) list.get(i);
            data[i] = new Object[]{pcb.getId(),pcb.getName(),pcb.getNeedSpace(),pcb.getPriority(),pcb.getType(),pcb.getNeedtime(),pcb.getArrivetime(),pcb.getRunTime(),pcb.getState()};
        }
    }
    public static void main(String[] args){
        List list = new PcbDao().select();
        new PcbTable().init(list);
    }
    public List init(List list){
//        PcbTable stt = new PcbTable();
        createTableData(list);  //创建表格所需数据
        table = getSimpleTable();
        JScrollPane jsPane = new JScrollPane(table);
        pane.add(jsPane,BorderLayout.CENTER);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 200);
        this.setVisible(true);

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
