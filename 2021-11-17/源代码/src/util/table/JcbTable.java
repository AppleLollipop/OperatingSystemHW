package util.table;

import bean.JCB;
import dao.JcbDao;
import org.omg.CORBA.portable.ValueOutputStream;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * @author xzy
 * @create 2021/11/2 14:50
 */
public class JcbTable extends JFrame {
    List list;
    private static final long serialVersionUID = 1L;
    protected JTable table;
    protected Object[][] data;
    protected String[] colname = {"id","name","needSpace","priority","type","time"};
    public JcbTable(){
        setTitle("作业列表");
        Container pane = getContentPane();
        pane.setLayout(new BorderLayout());
        createTableData();  //创建表格所需数据
        table = getSimpleTable();
        JScrollPane jsPane = new JScrollPane(table);
        pane.add(jsPane,BorderLayout.CENTER);
    }
    public void createTableData(){
        list = new JcbDao().select();
        data = new Object[list.size()][colname.length];
        for(int i = 0; i < list.size(); i++){
            JCB jcb = (JCB) list.get(i);
            data[i] = new Object[]{jcb.getId(),jcb.getName(),jcb.getNeedSpace(),jcb.getPriority(),jcb.getType(),jcb.getNeedtime(),jcb.getArrivetime()};
        }
    }
    public static void main(String[] args){
        new JcbTable().init();
    }
    public List init(){
        JcbTable stt= new JcbTable();
        stt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        stt.setSize(400, 200);
//        stt.setVisible(true);

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
