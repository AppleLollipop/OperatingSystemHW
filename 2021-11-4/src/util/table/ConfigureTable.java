package util.table;

import bean.Configure;
import bean.JCB;
import com.sun.org.apache.xml.internal.security.Init;
import dao.ConfigureDao;
import dao.JcbDao;
import org.omg.CORBA.portable.ValueOutputStream;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * @author xzy
 * @create 2021/11/2 14:50
 */
public class ConfigureTable extends JFrame {
    List list;
    private static final long serialVersionUID = 1L;
    protected JTable table;
    protected Object[][] data;
    protected String[] colname = {"id","系统最大进程数","系统最大优先级","系统最大资源种类数","系统最大作业数","系统内存大小","系统内存块大小","内存块数量","系统指令大小","系统资源种类数"};
    public ConfigureTable(){
        setTitle("系统配置信息");
        Container pane = getContentPane();
        pane.setLayout(new BorderLayout());
        createTableData();  //创建表格所需数据
        table = getSimpleTable();
        JScrollPane jsPane = new JScrollPane(table);
        pane.add(jsPane,BorderLayout.CENTER);
    }
    public void createTableData(){
        list = new ConfigureDao().select();
        data = new Object[list.size()][colname.length];
        for(int i = 0; i < list.size(); i++){
            Configure configure = (Configure) list.get(i);
            data[i] = new Object[]{configure.getId(),configure.getMaxProcess(),configure.getMaxPriority(),
                    configure.getMaxType(),configure.getMaxWork(),configure.getMemory(),configure.getMemoryBlock(),
                    configure.getMemoryNumber(),configure.getInstructions(),configure.getType()};
        }
    }
    public static void main(String[] args){
        new ConfigureTable().init();
    }
    public Configure init(){
        ConfigureTable stt= new ConfigureTable();
        stt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        stt.setSize(1000, 100);
//        stt.setVisible(true);

        return (Configure) list.get(0);
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
